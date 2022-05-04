package com.group2.campus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.group2.campus.repository.ScheduleRepository;
import com.group2.campus.service.DepartmentService;
import com.group2.campus.service.CampusService;
import com.group2.campus.service.ScheduleService;
import com.group2.nustudy.common.exception.NustudyException;
import com.group2.nustudy.common.result.ResultCodeEnum;
import com.group2.nustudy.model.camp.BookingRule;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.model.camp.Department;
import com.group2.nustudy.model.camp.Schedule;
import com.group2.nustudy.vo.camp.BookingScheduleRuleVo;
import com.group2.nustudy.vo.camp.ScheduleOrderVo;
import com.group2.nustudy.vo.camp.ScheduleQueryVo;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
//public class ScheduleServiceImpl extends
//        ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CampusService campusService;

    @Autowired
    private DepartmentService departmentService;

    //Upload schedule interface
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap Convert the department object
        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString, Schedule.class);

        //Query by campus number and shift number
        Schedule scheduleExist = scheduleRepository.
                getScheduleByCampuscodeAndHosScheduleId(schedule.getCampuscode(), schedule.getHosScheduleId());
        //judge
        if (scheduleExist != null) {
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleExist.setStatus(1);
            scheduleRepository.save(scheduleExist);
        } else {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }
    }

    //Query scheduling interface
    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        // Create a Pageable object, set the current page and records per page
        //0 is the first page
        Pageable pageable = PageRequest.of(page - 1, limit);
        // create Example object
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo, schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);

        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }

    //delete schedule
    @Override
    public void remove(String campuscode, String hosScheduleId) {
        //Query information based on campus number and shift number
        Schedule schedule = scheduleRepository.getScheduleByCampuscodeAndHosScheduleId(campuscode, hosScheduleId);
        if (schedule != null) {
            scheduleRepository.deleteById(schedule.getId());
        }
    }

    //According to the campus number and department number, query the scheduling rule data
    @Override
    public Map<String, Object> getRuleSchedule(long page, long limit, String campuscode, String depcode) {
        //1 Search by campus number and department number
        Criteria criteria = Criteria.where("campuscode").is(campuscode).and("depcode").is(depcode);


        //2 Group by weekday workDate period
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),//match condition
                Aggregation.group("workDate")//group field
                        .first("workDate").as("workDate")
                        //3 Number of statistical number sources
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber")
        );
        //Invoke the method and finally execute
        AggregationResults<BookingScheduleRuleVo> aggResults =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);
        System.out.println("aggResults" + aggResults);
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = aggResults.getMappedResults();

        //The total number of records for group query
        Aggregation totalAgg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate")
        );
        AggregationResults<BookingScheduleRuleVo> totalAggResults =
                mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalAggResults.getMappedResults().size();

        //Get the date corresponding to the week
        for (BookingScheduleRuleVo bookingScheduleRuleVo : bookingScheduleRuleVoList) {
            Date workDate = bookingScheduleRuleVo.getWorkDate();
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);
        }

        //Set final data and return
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", bookingScheduleRuleVoList);
        result.put("total", total);

        //get campus name
        String campusname = campusService.getCampusName(campuscode);
        //other params
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("campusname", campusname);
        result.put("baseMap", baseMap);

        return result;
    }

    //According to the campus number, department number and working date, check the details of the schedule
    @Override
    public List<Schedule> getDetailSchedule(String campuscode, String depcode, String workDate) {
        //Query by parameters mongodb
        List<Schedule> scheduleList =
                scheduleRepository.findScheduleByCampuscodeAndDepcodeAndWorkDate(campuscode, depcode, new DateTime(workDate).toDate());
        //Traverse the obtained list set and set other values: campus name, department name, date corresponding to the week
        scheduleList.stream().forEach(item -> {
            this.packageSchedule(item);
        });
        return scheduleList;
    }

    //Get the schedule data that can be booked
    @Override
    public Map<String, Object> getBookingScheduleRule(int page, int limit, String campuscode, String depcode) {
        Map<String, Object> result = new HashMap<>();
        //Get the reservation rules
        //Get the appointment rule according to the campus number
        Campus campus = campusService.getByCampuscode(campuscode);
        if (campus == null) {
            throw new NustudyException(ResultCodeEnum.DATA_ERROR);
        }
        BookingRule bookingRule = campus.getBookingRule();
        bookingRule.setReleaseTime("00:00");
        bookingRule.setStopTime("23:59");
        //Get data on available dates (paginated）
        IPage iPage = this.getListDate(page, limit, bookingRule);
        //Current available dates
        List<Date> dateList = iPage.getRecords();

        //Get the number of remaining appointments in the department on the available date
        Criteria criteria = Criteria.where("campuscode").is(campuscode).and("depcode").is(depcode)
                .and("workDate").in(dateList);

        System.out.println("datalist: " + dateList.size());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("workDate").first("workDate").as("workDate")
                        .count().as("docCount")
                        .sum("availableNumber").as("availableNumber")
                        .sum("reservedNumber").as("reservedNumber")
        );

        System.out.println("agg: " + agg);
        AggregationResults<BookingScheduleRuleVo> aggregateResult =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> scheduleVoList = aggregateResult.getMappedResults();
        System.out.println("scheduleVoList:" + scheduleVoList);
        //Merge data map set key date value reservation rule and remaining quantity, etc.
        Map<Date, BookingScheduleRuleVo> scheduleVoMap = new HashMap<>();
        System.out.println("scheduleVoMap:" +  scheduleVoMap);
        if (!CollectionUtils.isEmpty(scheduleVoList)) {
            scheduleVoMap = scheduleVoList.stream().
                    collect(
                            Collectors.toMap(BookingScheduleRuleVo::getWorkDate,
                                    BookingScheduleRuleVo -> BookingScheduleRuleVo));
        }

        //Get Scheduleable Schedule Rules
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = new ArrayList<>();
        for (int i = 0, len = dateList.size(); i < len; i++) {
            Date date = dateList.get(i);
            //Get the value from the map collection based on the key date
            BookingScheduleRuleVo bookingScheduleRuleVo = scheduleVoMap.get(date);
            //If there is no scheduled doctor on the day
            if (bookingScheduleRuleVo == null) {
                bookingScheduleRuleVo = new BookingScheduleRuleVo();
                //Number of visiting doctors
                bookingScheduleRuleVo.setDocCount(0);
                //The number of remaining appointments in the department -1 means no number
                bookingScheduleRuleVo.setAvailableNumber(-1);
            }
            bookingScheduleRuleVo.setWorkDate(date);
            bookingScheduleRuleVo.setWorkDateMd(date);
            //Calculate the week corresponding to the current appointment date
            String dayOfWeek = this.getDayOfWeek(new DateTime(date));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);

            //The last record on the last page is about reservation status 0: normal 1: about to be released -1: the registration has been stopped on the day
            if (i == len - 1 && page == iPage.getPages()) {
                bookingScheduleRuleVo.setStatus(1);
            } else {
                bookingScheduleRuleVo.setStatus(0);
            }
            //If you make an appointment on the same day after the stop time, you cannot make an appointment
            if (i == 0 && page == 1) {
                DateTime stopTime = this.getDateTime(new Date(), bookingRule.getStopTime());
                if (stopTime.isBeforeNow()) {
                    //stop booking
                    bookingScheduleRuleVo.setStatus(-1);
                }
            }
            bookingScheduleRuleVoList.add(bookingScheduleRuleVo);
        }

        //Scheduleable date rule data
        result.put("bookingScheduleList", bookingScheduleRuleVoList);
        result.put("total", iPage.getTotal());

        //Other basic data
        Map<String, String> baseMap = new HashMap<>();
        //campus name
        baseMap.put("campusname", campusService.getCampusName(campuscode));
        //Department
        Department department = departmentService.getDepartment(campuscode, depcode);
        //Big department name
        baseMap.put("bigname", department.getBigname());
        //department name
        baseMap.put("depname", department.getDepname());
//month
        baseMap.put("workDateString", new DateTime().toString("yyyy-MM"));
//release time
        baseMap.put("releaseTime", bookingRule.getReleaseTime());
//stop time
        baseMap.put("stopTime", bookingRule.getStopTime());
        result.put("baseMap", baseMap);
        return result;
    }

    ////Get shift id Get shift data
    @Override
    public Schedule getScheduleId(String scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleByHosScheduleId(scheduleId);
        return this.packageSchedule(schedule);
    }

    //Get reservation order data based on shift id
    @Override
    public ScheduleOrderVo getScheduleOrderVo(String scheduleId) {
        ScheduleOrderVo scheduleOrderVo = new ScheduleOrderVo();
        //Get shift information
        Schedule schedule = this.getScheduleId(scheduleId);
        //Schedule schedule = baseMapper.selectById(scheduleId);
        if (schedule == null) {
            throw new NustudyException(ResultCodeEnum.PARAM_ERROR);
        }
        //Get reservation rule information
        Campus campus = campusService.getByCampuscode(schedule.getCampuscode());
        if (campus == null) {
            throw new NustudyException(ResultCodeEnum.PARAM_ERROR);
        }
        BookingRule bookingRule = campus.getBookingRule();
        if (bookingRule == null) {
            throw new NustudyException(ResultCodeEnum.PARAM_ERROR);
        }

        //Set the fetch data to scheduleOrderVo
        scheduleOrderVo.setCampuscode(schedule.getCampuscode());
        scheduleOrderVo.setCampusname(campusService.getCampusName(schedule.getCampuscode()));
        scheduleOrderVo.setDepcode(schedule.getDepcode());
        scheduleOrderVo.setDepname(departmentService.getDepName(schedule.getCampuscode(), schedule.getDepcode()));
        scheduleOrderVo.setHosScheduleId(schedule.getHosScheduleId());
        scheduleOrderVo.setAvailableNumber(schedule.getAvailableNumber());
        scheduleOrderVo.setTitle(schedule.getTitle());
        scheduleOrderVo.setReserveDate(schedule.getWorkDate());
        scheduleOrderVo.setReserveTime(schedule.getWorkTime());
        scheduleOrderVo.setAmount(schedule.getAmount());

        //The number of days to cancel the account
        int quitDay = bookingRule.getQuitDay();
        DateTime quitTime = this.getDateTime(new DateTime(schedule.getWorkDate()).plusDays(quitDay).toDate(), bookingRule.getQuitTime());
        scheduleOrderVo.setQuitTime(quitTime.toDate());

        //Appointment start time
        DateTime startTime = this.getDateTime(new Date(), bookingRule.getReleaseTime());
        scheduleOrderVo.setStartTime(startTime.toDate());

        //Appointment Deadline
        DateTime endTime = this.getDateTime(new DateTime().plusDays(bookingRule.getCycle()).toDate(), bookingRule.getStopTime());
        scheduleOrderVo.setEndTime(endTime.toDate());

        //Stop registration time on the day
        DateTime stopTime = this.getDateTime(new Date(), bookingRule.getStopTime());
        scheduleOrderVo.setStartTime(startTime.toDate());
        return scheduleOrderVo;
    }

    //Update shift information for mp
    @Override
    public void update(Schedule schedule) {
        schedule.setUpdateTime(new Date());
        scheduleRepository.save(schedule);
    }

    //Get bookable log paging data
    private IPage getListDate(int page, int limit, BookingRule bookingRule) {
        //Get the date when the number is released year month day hour minute
        DateTime releaseTime = this.getDateTime(new Date(), bookingRule.getReleaseTime());
        //Get appointment cycle
        Integer cycle = bookingRule.getCycle();
        //If the time for assigning the number on the day has passed, the reservation period will be calculated from the next day, and the period will be +1
        if (releaseTime.isBeforeNow()) {
            cycle += 1;
        }
        //Get all the dates that can be reserved, the last day shows the upcoming number
        List<Date> dateList = new ArrayList<>();
        for (int i = 0; i < cycle; i++) {
            DateTime curDateTime = new DateTime().plusDays(i);
            String dateString = curDateTime.toString("yyyy-MM-dd");
            dateList.add(new DateTime(dateString).toDate());
        }
        //Because the reservation cycle is different, each page displays up to 7 days of data, and more than 7 days are paginated
        List<Date> pageDateList = new ArrayList<>();
        int start = (page - 1) * limit;
        int end = (page - 1) * limit + limit;
        //If the data that can be displayed is less than 7, display it directly
        if (end > dateList.size()) {
            end = dateList.size();
        }
        for (int i = start; i < end; i++) {
            pageDateList.add(dateList.get(i));
        }
        //If you can display data greater than 7, paginate
        IPage<Date> iPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, 7, dateList.size());
        iPage.setRecords(pageDateList);
        return iPage;
    }

    /**
     * Convert Date (yyyy-MM-dd HH:mm) to DateTime
     */
    private DateTime getDateTime(Date date, String timeString) {
        String dateTimeString = new DateTime(date).toString("yyyy-MM-dd") + " " + timeString;
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").parseDateTime(dateTimeString);
        return dateTime;
    }

    //Package schedule details Other values ​​campus name, department name, date corresponding to the week
    private Schedule packageSchedule(Schedule schedule) {
        //set campus name
        schedule.getParam().put("campusname", campusService.getCampusName(schedule.getCampuscode()));
        //Set department name
        schedule.getParam().put("depname", departmentService.getDepName(schedule.getCampuscode(), schedule.getDepcode()));
        //Set the date to correspond to the day of the week
        schedule.getParam().put("dayOfWeek", this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
        return schedule;
    }

    /**
     * Util class
     * Get day of week data based on date
     *
     * @param dateTime
     * @return
     */
    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "Sun";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "Mon";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "Tue";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "Wed";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "Thu";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "Fri";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "Sat";
            default:
                break;
        }
        return dayOfWeek;
    }
}
