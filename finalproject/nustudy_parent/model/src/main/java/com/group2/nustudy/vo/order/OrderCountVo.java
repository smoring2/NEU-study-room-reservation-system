package com.group2.nustudy.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderCountVo")
public class OrderCountVo {
	
	@Schema(description = "reserveDate")
	private String reserveDate;

	@Schema(description = "count")
	private Integer count;

}

