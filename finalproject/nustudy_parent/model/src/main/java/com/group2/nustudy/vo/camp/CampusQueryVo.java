package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Campus")
public class CampusQueryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "campus code")
	private String campuscode;

	@ApiModelProperty(value = "campus name")
	private String campusname;

	@ApiModelProperty(value = "campus type")
	private String campustype;

	@ApiModelProperty(value = "state Code")
	private String stateCode;

	@ApiModelProperty(value = "city Code")
	private String cityCode;

	@ApiModelProperty(value = "district Code")
	private String districtCode;

	@ApiModelProperty(value = "status")
	private Integer status;
}

