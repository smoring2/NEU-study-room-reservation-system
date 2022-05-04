package com.group2.campus.service;

import com.group2.nustudy.model.camp.Department;
import com.group2.nustudy.vo.camp.DepartmentQueryVo;
import com.group2.nustudy.vo.camp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    //Upload department interface
    void save(Map<String, Object> paramMap);

    //Query department interface
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

    //Delete department interface
    void remove(String campuscode, String depcode);

    //According to the campus number, query the list of all departments in the campus
    List<DepartmentVo> findDeptTree(String campuscode);

    //According to the department number and campus number, query the department name
    String getDepName(String campuscode, String depcode);

    //According to the department number and campus number, query the department
    Department getDepartment(String campuscode, String depcode);
}
