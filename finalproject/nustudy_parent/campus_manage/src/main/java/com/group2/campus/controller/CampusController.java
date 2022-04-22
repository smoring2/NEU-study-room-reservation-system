package com.group2.campus.controller;

import com.group2.campus.service.ApiService;
import com.group2.campus.service.CampusService;
import com.group2.campus.util.HttpRequestHelper;
import com.group2.campus.util.Result;
import com.group2.campus.util.ResultCodeEnum;
import com.group2.campus.util.NustudyException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author qy
 *
 */
@Api(tags = "医院管理接口")
@RestController
public class CampusController {

	@Autowired
	private CampusService campusService;

	@Autowired
	private ApiService apiService;

	/**
	 * 预约下单
	 * @param request
	 * @return
	 */
	@PostMapping("/order/submitOrder")
	public Result AgreeAccountLendProject(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
			if(!HttpRequestHelper.isSignEquals(paramMap, apiService.getSignKey())) {
				throw new NustudyException(ResultCodeEnum.SIGN_ERROR);
			}

			Map<String, Object> resultMap = campusService.submitOrder(paramMap);
			return Result.ok(resultMap);
		} catch (NustudyException e) {
			return Result.fail().message(e.getMessage());
		}
	}

	/**
	 * 更新支付状态
	 * @param request
	 * @return
	 */
	@PostMapping("/order/updatePayStatus")
	public Result updatePayStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
			if(!HttpRequestHelper.isSignEquals(paramMap, apiService.getSignKey())) {
				throw new NustudyException(ResultCodeEnum.SIGN_ERROR);
			}

			campusService.updatePayStatus(paramMap);
			return Result.ok();
		} catch (NustudyException e) {
			return Result.fail().message(e.getMessage());
		}
	}

	/**
	 * 更新取消预约状态
	 * @param request
	 * @return
	 */
	@PostMapping("/order/updateCancelStatus")
	public Result updateCancelStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
			if(!HttpRequestHelper.isSignEquals(paramMap, apiService.getSignKey())) {
				throw new NustudyException(ResultCodeEnum.SIGN_ERROR);
			}

			campusService.updateCancelStatus(paramMap);
			return Result.ok();
		} catch (NustudyException e) {
			return Result.fail().message(e.getMessage());
		}
	}
}

