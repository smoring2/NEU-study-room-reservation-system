package com.group2.nustudy.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Order")
public class OrderQueryVo {


	@ApiModelProperty(value = "userId")
	private Long userId;
	
	@ApiModelProperty(value = "outTradeNo")
	private String outTradeNo;

	@ApiModelProperty(value = "studentId")
	private Long studentId;

	@ApiModelProperty(value = "studentName")
	private String studentName;

	@ApiModelProperty(value = "keyword")
	private String keyword;

	@ApiModelProperty(value = "orderStatus")
	private String orderStatus;

	@ApiModelProperty(value = "reserveDate")
	private String reserveDate;

	@ApiModelProperty(value = "createTimeBegin")
	private String createTimeBegin;
	@ApiModelProperty(value = "createTimeEnd")
	private String createTimeEnd;
}

