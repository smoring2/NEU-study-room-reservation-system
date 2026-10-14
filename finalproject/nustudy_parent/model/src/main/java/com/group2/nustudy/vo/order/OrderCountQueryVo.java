package com.group2.nustudy.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "OrderCountQueryVo")
public class OrderCountQueryVo {
	
	@Schema(description = "campus code")
	private String campuscode;

	@Schema(description = "campus name")
	private String campusname;

	@Schema(description = "reserveDateBegin")
	private String reserveDateBegin;

	@Schema(description = "reserveDateEnd")
	private String reserveDateEnd;

}

