package com.group2.nustudy.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "OrderCountQueryVo")
public class OrderCountQueryVo {
	
	@ApiModelProperty(value = "campus code")
	private String campuscode;

	@ApiModelProperty(value = "campus name")
	private String campusname;

	@ApiModelProperty(value = "reserveDateBegin")
	private String reserveDateBegin;

	@ApiModelProperty(value = "reserveDateEnd")
	private String reserveDateEnd;

}

