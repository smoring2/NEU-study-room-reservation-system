package com.group2.campus.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.group2.campus.mapper.CampusSetMapper;
import com.group2.campus.mapper.ScheduleMapper;
import com.group2.campus.model.CampusSet;
import com.group2.campus.model.Schedule;
import com.group2.campus.service.ApiService;
import com.group2.campus.util.BeanUtils;
import com.group2.campus.util.HttpRequestHelper;
import com.group2.campus.util.MD5;
import com.group2.campus.util.NustudyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private CampusSetMapper campusSetMapper;

    @Autowired
    private ApiService apiService;

    @Value("classpath:campus.json")
    private Resource campusResource;

    @Override
    public String getCampuscode() {
        CampusSet campusSet = campusSetMapper.selectById(1);
        return campusSet.getCampuscode();
    }

    @Override
    public String getSignKey() {
        CampusSet campusSet = campusSetMapper.selectById(1);
        return campusSet.getSignKey();
    }

    private String getApiUrl() {
        CampusSet campusSet = campusSetMapper.selectById(1);
        return campusSet.getApiUrl();
    }

    @Override
    public JSONObject getCampus() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode",this.getCampuscode());
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));
        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/campus/show");
        System.out.println(respone.toJSONString());
        if(null != respone && 200 == respone.getIntValue("code")) {
            JSONObject jsonObject = respone.getJSONObject("data");
            return jsonObject;
        }
        return null;
    }

    @Override
    public boolean saveCampus(String data) {
        JSONObject jsonObject = JSONObject.parseObject(data);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode",this.getCampuscode());
        paramMap.put("campusname",jsonObject.getString("campusname"));
        paramMap.put("campustype",jsonObject.getString("campustype"));
        paramMap.put("stateCode",jsonObject.getString("stateCode"));
        paramMap.put("cityCode", jsonObject.getString("cityCode"));
        paramMap.put("districtCode",jsonObject.getString("districtCode"));
        paramMap.put("address",jsonObject.getString("address"));
        paramMap.put("intro",jsonObject.getString("intro"));
        paramMap.put("route",jsonObject.getString("route"));
        //picture
        paramMap.put("logoData", jsonObject.getString("logoData"));

        JSONObject bookingRule = jsonObject.getJSONObject("bookingRule");
        paramMap.put("bookingRule",bookingRule.toJSONString());

        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));


        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/saveCampus");
        System.out.println(respone.toJSONString());

        if(null != respone && 200 == respone.getIntValue("code")) {
            return true;
        } else {
            throw new NustudyException(respone.getString("message"), 201);
        }
    }

    @Override
    public Map<String, Object> findDepartment(int pageNum, int pageSize) {
        Map<String, Object> result = new HashMap();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode",this.getCampuscode());

        paramMap.put("page",pageNum);
        paramMap.put("limit",pageSize);
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));
        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/department/list");
        if(null != respone && 200 == respone.getIntValue("code")) {
            JSONObject jsonObject = respone.getJSONObject("data");

            result.put("total", jsonObject.getLong("totalElements"));
            result.put("pageNum", pageNum);
            result.put("list", jsonObject.getJSONArray("content"));
        } else {
            throw new NustudyException(respone.getString("message"), 201);
        }
        return result;
    }

    @Override
    public boolean saveDepartment(String data) {
        JSONArray jsonArray = new JSONArray();
        if(!data.startsWith("[")) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            jsonArray.add(jsonObject);
        } else {
            jsonArray = JSONArray.parseArray(data);
        }

        for(int i=0, len=jsonArray.size(); i<len; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("campuscode",this.getCampuscode());
            paramMap.put("depcode",jsonObject.getString("depcode"));
            paramMap.put("depname",jsonObject.getString("depname"));
            paramMap.put("intro",jsonObject.getString("intro"));
            paramMap.put("bigcode", jsonObject.getString("bigcode"));
            paramMap.put("bigname",jsonObject.getString("bigname"));

            paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
            paramMap.put("sign", MD5.encrypt(this.getSignKey()));

            JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/saveDepartment");
            System.out.println(respone.toJSONString());

            if(null == respone || 200 != respone.getIntValue("code")) {
                throw new NustudyException(respone.getString("message"), 201);
            }
        }
        return true;
    }

    @Override
    public boolean removeDepartment(String depcode) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode",this.getCampuscode());
        paramMap.put("depcode",depcode);
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));
        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/department/remove");
        System.out.println(respone.toJSONString());
        if(null != respone && 200 == respone.getIntValue("code")) {
            return true;
        } else {
            throw new NustudyException(respone.getString("message"), 201);
        }
    }

    @Override
    public Map<String, Object> findSchedule(int pageNum, int pageSize) {
        Map<String, Object> result = new HashMap();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode",this.getCampuscode());

        paramMap.put("page",pageNum);
        paramMap.put("limit",pageSize);
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));

        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/schedule/list");
        System.out.println(respone.toJSONString());
        if(null != respone && 200 == respone.getIntValue("code")) {
            JSONObject jsonObject = respone.getJSONObject("data");

            result.put("total", jsonObject.getLong("totalElements"));
            result.put("pageNum", pageNum);
            result.put("list", jsonObject.getJSONArray("content"));
        } else {
            throw new NustudyException(respone.getString("message"), 201);
        }
        return result;
    }

    @Override
    public boolean saveSchedule(String data) {
        JSONArray jsonArray = new JSONArray();
        if(!data.startsWith("[")) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            jsonArray.add(jsonObject);
        } else {
            jsonArray = JSONArray.parseArray(data);
        }

        for(int i=0, len=jsonArray.size(); i<len; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Long id = jsonObject.getLong("hosScheduleId");
            Schedule schedule = new Schedule();
            schedule.setId(id);
            schedule.setCampuscode(this.getCampuscode());
            schedule.setDepcode(jsonObject.getString("depcode"));
            schedule.setTitle(jsonObject.getString("title"));
            schedule.setDocname(jsonObject.getString("docname"));
            schedule.setSkill(jsonObject.getString("skill"));
            schedule.setWorkDate(jsonObject.getString("workDate"));
            schedule.setWorkTime(jsonObject.getInteger("workTime"));
            schedule.setReservedNumber(jsonObject.getInteger("reservedNumber"));
            schedule.setAvailableNumber(jsonObject.getInteger("availableNumber"));
            schedule.setAmount(jsonObject.getString("amount"));
            schedule.setStatus(1);

            Schedule targetSchedule = scheduleMapper.selectById(id);
            if(null != targetSchedule) {
                //The copy value is not a null value, this method is a custom method
                BeanUtils.copyBean(schedule, targetSchedule, Schedule.class);
                scheduleMapper.updateById(targetSchedule);
            } else {
                scheduleMapper.insert(schedule);
            }

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("campuscode",schedule.getCampuscode());
            paramMap.put("depcode",schedule.getDepcode());
            paramMap.put("title",schedule.getTitle());
            paramMap.put("docname",schedule.getDocname());
            paramMap.put("skill", schedule.getSkill());
            paramMap.put("workDate",schedule.getWorkDate());
            paramMap.put("workTime", schedule.getWorkTime());
            paramMap.put("reservedNumber",schedule.getReservedNumber());
            paramMap.put("availableNumber",schedule.getAvailableNumber());
            paramMap.put("amount",schedule.getAmount());
            paramMap.put("status",schedule.getStatus());
            paramMap.put("hosScheduleId",schedule.getId());
            paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
            paramMap.put("sign", MD5.encrypt(this.getSignKey()));


            JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/saveSchedule");
            System.out.println(respone.toJSONString());
            if(null == respone || 200 != respone.getIntValue("code")) {
                throw new NustudyException(respone.getString("message"), 201);
            }
        }
        return false;
    }

    @Override
    public boolean removeSchedule(String hosScheduleId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode",this.getCampuscode());
        paramMap.put("hosScheduleId",hosScheduleId);
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));

        JSONObject respone = HttpRequestHelper.sendRequest(paramMap,this.getApiUrl()+"/api/campus/schedule/remove");
        System.out.println(respone.toJSONString());
        if(null != respone && 200 == respone.getIntValue("code")) {
            return true;
        } else {
            throw new NustudyException(respone.getString("message"), 201);
        }
    }

    @Override
    public void saveBatchCampus() throws IOException {
        File file = campusResource.getFile();
        String jsonData = this.jsonRead(file);
        JSONArray jsonArray = JSONArray.parseArray(jsonData);
        for(int i=1, len=jsonArray.size(); i<len; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("campuscode","1000_"+i);
            paramMap.put("campusname",jsonObject.getString("campusname"));
            paramMap.put("campustype",i % 3 == 0 ? 1 : i % 3 == 1 ? 2 :4);
            paramMap.put("stateCode","110000");
            paramMap.put("cityCode", "110100");
            if(i % 3 == 0) {
                paramMap.put("districtCode","110101");
            }
            if(i % 3 == 1) {
                paramMap.put("districtCode","110102");
            }
            if(i % 3 == 2) {
                paramMap.put("districtCode","110106");
            }

            paramMap.put("address","");
            String intro = "Northeastern University (NU or NEU) is a private research university with its main campus in Boston. Established in 1898, the university offers undergraduate and graduate programs on its main campus in Boston.\n" +
                    "\n" +
                    "In 2019, Northeastern purchased the New College of the in London, England. The university's enrollment is approximately 19,000 undergraduate students and 8,600 graduate students. It is classified among \"R1: Doctoral Universities – Very high research activity\".Northeastern faculty and alumni include Nobel Prize laureates, Rhodes, Truman, and Marshall scholars. Undergraduate admission to the university is categorized as \"most selective.\"\n" +
                    "\n" +
                    "Northeastern features a cooperative education program, more commonly known as \"co-op,\" that integrates classroom study with professional experience and includes over 3,100 partners across all seven continents.";
            paramMap.put("intro",intro);
            String route = "\n" +
                    "Northeastern owns satellite campuses in Charlotte, North Carolina; Seattle, Washington; San Jose, California; Oakland, California; Portland, Maine and Toronto and Vancouver in Canada.\n" +
                    "In 2019, Northeastern purchased the New College of the Humanities in London, England.\n" +
                    "\n";
            paramMap.put("route",route);


            Map<String, Object> bookingRuleMap = new HashMap<>();
            bookingRuleMap.put("cycle",10);
            bookingRuleMap.put("releaseTime",jsonObject.getString("releaseTime"));
            bookingRuleMap.put("stopTime","12:30");
            bookingRuleMap.put("quitDay",-1);
            bookingRuleMap.put("quitTime","15:30");
            bookingRuleMap.put("rule","[\"San Jose Campus address: 4 N. 2nd Street San Jose, CA 95113\"]");
            paramMap.put("bookingRule",JSONObject.toJSONString(bookingRuleMap));

            paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
            paramMap.put("sign", MD5.encrypt(this.getSignKey()));
//            paramMap.put("sign", HttpRequestHelper.getSign(paramMap, apiService.getSignKey()));

            JSONObject respone = HttpRequestHelper.sendRequest(paramMap,"http://localhost/api/campus/saveCampus");
            System.out.println(respone.toJSONString());
            if(null == respone || 200 != respone.getIntValue("code")) {
                throw new NustudyException(respone.getString("message"), 201);
            }
        }
    }

    private String jsonRead(File file){
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
        } catch (Exception e) {

        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return buffer.toString();
    }

    public static String getImgStr(String imgFile){
        byte[] data = getImageBytes(imgFile);
        return new String(Base64.encodeBase64(data));
    }

    public static byte[] getImageBytes(String imgUrl) {
        URL url = null;
        InputStream is = null;
        ByteArrayOutputStream outStream = null;
        HttpURLConnection httpUrl = null;
        try {
            url = new URL(imgUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            httpUrl.getInputStream();
            is = httpUrl.getInputStream();
            outStream = new ByteArrayOutputStream();
            //Create a Buffer string
            byte[] buffer = new byte[1024];

            int len = 0;
            //Read data from the buffer using an input stream
            while ((len = is.read(buffer)) != -1) {
                //Use the output stream to write data into the buffer, the intermediate parameter represents where to start reading, and len represents the length of the read
                outStream.write(buffer, 0, len);
            }
            // Base64 encode a byte array
            return outStream.toByteArray();
        } catch (ConnectException e) {
            log.error("Connection exception when getting pictures，url:{}",imgUrl,e);
        } catch (MalformedURLException e) {
            log.error("url exception，url:{}",imgUrl,e);
        } catch (IOException e) {
            log.error("Connection exception when reading pictures，url:{}",imgUrl,e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpUrl != null) {
                httpUrl.disconnect();
            }
        }
        return null;
    }
}
