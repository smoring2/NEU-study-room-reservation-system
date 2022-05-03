package com.group2.campus.controller;

import com.group2.campus.mapper.CampusSetMapper;
import com.group2.campus.model.CampusSet;
import com.group2.campus.service.ApiService;
import com.group2.campus.util.NustudyException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
@Api(tags = "campus management interface")
@Controller
@RequestMapping
public class ApiController extends BaseController {

	@Autowired
	private ApiService apiService;

	@Autowired
	private CampusSetMapper campusSetMapper;

	@RequestMapping("/campusSet/index")
	public String getCampusSet(ModelMap model, RedirectAttributes redirectAttributes) {
		CampusSet campusSet = campusSetMapper.selectById(1);
		model.addAttribute("campusSet", campusSet);
		return "campusSet/index";
	}

	@RequestMapping(value="/campusSet/save")
	public String createCampusSet(ModelMap model, CampusSet campusSet) {
		campusSetMapper.updateById(campusSet);
		return "redirect:/campusSet/index";
	}

	@RequestMapping("/campus/index")
	public String getCampus(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			CampusSet campusSet = campusSetMapper.selectById(1);
			if(null == campusSet || StringUtils.isEmpty(campusSet.getCampuscode()) || StringUtils.isEmpty(campusSet.getSignKey())) {
				this.failureMessage("Please set the campus code and sign key", redirectAttributes);
				return "redirect:/campusSet/index";
			}

			model.addAttribute("campus", apiService.getCampus());
		} catch (NustudyException e) {
			this.failureMessage(e.getMessage(), request);
		} catch (Exception e) {
			this.failureMessage("Data exception", request);
		}
		return "campus/index";
	}

	@RequestMapping(value="/campus/create")
	public String createCampus(ModelMap model) {
		return "campus/create";
	}

	@RequestMapping(value="/campus/save",method=RequestMethod.POST)
	public String saveCampus(String data, HttpServletRequest request) {
		try {
			apiService.saveCampus(data);
		} catch (NustudyException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			return this.failurePage("Data exception",request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping("/department/list")
	public String findDepartment(ModelMap model,
								 @RequestParam(defaultValue = "1") int pageNum,
								 @RequestParam(defaultValue = "10") int pageSize,
								 HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			CampusSet campusSet = campusSetMapper.selectById(1);
			if(null == campusSet || StringUtils.isEmpty(campusSet.getCampuscode()) || StringUtils.isEmpty(campusSet.getSignKey())) {
				this.failureMessage("Please set the campus code and sign key", redirectAttributes);
				return "redirect:/campusSet/index";
			}

			model.addAllAttributes(apiService.findDepartment(pageNum, pageSize));
		} catch (NustudyException e) {
			this.failureMessage(e.getMessage(), request);
		} catch (Exception e) {
			this.failureMessage("Data exception", request);
		}
		return "department/index";
	}

	@RequestMapping(value="/department/create")
	public String create(ModelMap model) {
		return "department/create";
	}

	@RequestMapping(value="/department/save",method=RequestMethod.POST)
	public String save(String data, HttpServletRequest request) {
		try {
			apiService.saveDepartment(data);
		} catch (NustudyException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			return this.failurePage("Data exception",request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping("/schedule/list")
	public String findSchedule(ModelMap model,
								 @RequestParam(defaultValue = "1") int pageNum,
								 @RequestParam(defaultValue = "10") int pageSize,
							   HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			CampusSet campusSet = campusSetMapper.selectById(1);
			if(null == campusSet || StringUtils.isEmpty(campusSet.getCampuscode()) || StringUtils.isEmpty(campusSet.getSignKey())) {
				this.failureMessage("Please set the campus code and sign key", redirectAttributes);
				return "redirect:/campusSet/index";
			}

			model.addAllAttributes(apiService.findSchedule(pageNum, pageSize));
		} catch (NustudyException e) {
			this.failureMessage(e.getMessage(), request);
		} catch (Exception e) {
			this.failureMessage("Data exception", request);
		}
		return "schedule/index";
	}

	@RequestMapping(value="/schedule/create")
	public String createSchedule(ModelMap model) {
		return "schedule/create";
	}

	@RequestMapping(value="/schedule/save",method=RequestMethod.POST)
	public String saveSchedule(String data, HttpServletRequest request) {
		try {
			//data = data.replaceAll("\r\n", "").replace(" ", "");
			apiService.saveSchedule(data);
		} catch (NustudyException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			e.printStackTrace();
			return this.failurePage("Data exception："+e.getMessage(),request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping(value="/campus/createBatch")
	public String createCampusBatch(ModelMap model) {
		return "campus/createBatch";
	}

	@RequestMapping(value="/campus/saveBatch",method=RequestMethod.POST)
	public String saveBatchCampus(HttpServletRequest request) {
		try {
			apiService.saveBatchCampus();
		} catch (NustudyException e) {
			return this.failurePage(e.getMessage(),request);
		} catch (Exception e) {
			return this.failurePage("Data exception",request);
		}
		return this.successPage(null,request);
	}

	@RequestMapping(value="/department/remove/{depcode}",method=RequestMethod.GET)
	public String removeDepartment(ModelMap model, @PathVariable String depcode, RedirectAttributes redirectAttributes) {
		apiService.removeDepartment(depcode);

		this.successMessage(null, redirectAttributes);
		return "redirect:/department/list";
	}

	@RequestMapping(value="/schedule/remove/{hosScheduleId}",method=RequestMethod.GET)
	public String removeSchedule(ModelMap model, @PathVariable String hosScheduleId, RedirectAttributes redirectAttributes) {
		apiService.removeSchedule(hosScheduleId);

		this.successMessage(null, redirectAttributes);
		return "redirect:/schedule/list";
	}

}

