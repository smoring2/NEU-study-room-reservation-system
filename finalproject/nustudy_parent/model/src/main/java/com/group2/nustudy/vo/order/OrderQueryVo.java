package com.group2.nustudy.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Order")
public class OrderQueryVo {


	@Schema(description = "userId")
	private Long userId;
	
	@Schema(description = "outTradeNo")
	private String outTradeNo;

	@Schema(description = "studentId")
	private Long studentId;

	@Schema(description = "studentName")
	private String studentName;

	@Schema(description = "keyword")
	private String keyword;

	@Schema(description = "orderStatus")
	private String orderStatus;

	@Schema(description = "reserveDate")
	private String reserveDate;

	@Schema(description = "createTimeBegin")
	private String createTimeBegin;
	@Schema(description = "createTimeEnd")
	private String createTimeEnd;
}

