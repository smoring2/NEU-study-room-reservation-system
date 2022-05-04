package com.group2.campus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.group2.campus.repository.DepartmentRepository;
import com.group2.campus.service.DepartmentService;
import com.group2.nustudy.model.camp.Department;
import com.group2.nustudy.vo.camp.DepartmentQueryVo;
import com.group2.nustudy.vo.camp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    //Upload department interface
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap convert to department object
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString,Department.class);

        // lookup based on the combo of campus code and department code
        Department departmentExist = departmentRepository.
                getDepartmentByCampuscodeAndDepcode(department.getCampuscode(),department.getDepcode());

        if(departmentExist!=null) { //update
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);
        } else { //add
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo) {
        // Create Pageable object，set the current page and page limitation
        // 0 is the first page
        Pageable pageable = PageRequest.of(page-1,limit);

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
        department.setIsDeleted(0);

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // fuzzy query
            .withIgnoreCase(true);
        Example<Department> example = Example.of(department,matcher);

        Page<Department> all = departmentRepository.findAll(example, pageable);

        return all;
    }

    //Delete department interface
    @Override
    public void remove(String campuscode, String depcode) {
        //Search by campus number and department number
        Department department = departmentRepository.getDepartmentByCampuscodeAndDepcode(campuscode, depcode);
        if(department != null) {
            //call delete method
            departmentRepository.deleteById(department.getId());
        }
    }

    //According to the campus number, query the list of all departments in the campus
    @Override
    public List<DepartmentVo> findDeptTree(String campuscode) {
        //Create a list collection for final data encapsulation
        List<DepartmentVo> result = new ArrayList<>();

        //According to the campus number, query the information of all departments in the campus
        Department departmentQuery = new Department(); // using mongoDB
        departmentQuery.setCampuscode(campuscode);
        Example example = Example.of(departmentQuery);
        //departmentList
        List<Department> departmentList = departmentRepository.findAll(example);

        // DEBUG TRANSLATE bigcode-> building number
        //According to the big code grouping of the big code, get the lower-level sub-departments in each big department
        Map<String, List<Department>> deparmentMap =
                departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));
        //Iterate over the map collection deparmentMap
        for(Map.Entry<String,List<Department>> entry : deparmentMap.entrySet()) {
            //Big department number
            String bigcode = entry.getKey();
            //Global data corresponding to the big department number
            List<Department> deparment1List = entry.getValue();
            //Packaging big department
            DepartmentVo departmentVo1 = new DepartmentVo();
            departmentVo1.setDepcode(bigcode);
            departmentVo1.setDepname(deparment1List.get(0).getBigname());

            //Packaging children department
            List<DepartmentVo> children = new ArrayList<>();
            for(Department department: deparment1List) {
                DepartmentVo departmentVo2 =  new DepartmentVo();
                departmentVo2.setDepcode(department.getDepcode());
                departmentVo2.setDepname(department.getDepname());
                //Packaging to list
                children.add(departmentVo2);
            }
            //put list of subdepartment into children of big department
            departmentVo1.setChildren(children);
            //put into result
            result.add(departmentVo1);
        }
        return result;
    }

    //According to the department number and campus number, query the department name
    @Override
    public String getDepName(String campuscode, String depcode) {
        Department department = departmentRepository.getDepartmentByCampuscodeAndDepcode(campuscode, depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }

    @Override
    public Department getDepartment(String campuscode, String depcode) {
        return departmentRepository.getDepartmentByCampuscodeAndDepcode(campuscode, depcode);
    }

}
