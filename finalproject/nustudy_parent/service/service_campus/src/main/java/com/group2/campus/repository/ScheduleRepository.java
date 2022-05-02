package com.group2.campus.repository;
import com.group2.nustudy.model.camp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    //Query by campus number and student number
    Schedule getScheduleByCampuscodeAndHosScheduleId(String campuscode, String hosScheduleId);

    //According to the campus number, department number and working date, check the details of the schedule
    List<Schedule> findScheduleByCampuscodeAndDepcodeAndWorkDate(String campuscode, String depcode, Date toDate);
    Schedule findScheduleByHosScheduleId(String scheduleId);
}
