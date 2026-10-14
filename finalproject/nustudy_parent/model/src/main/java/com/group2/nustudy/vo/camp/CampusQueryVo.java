package com.group2.nustudy.vo.camp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Campus")
public class CampusQueryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Schema(description = "campus code")
	private String campuscode;

	@Schema(description = "campus name")
	private String campusname;

	@Schema(description = "campus type")
	private String campustype;

	@Schema(description = "state Code")
	private String stateCode;

	@Schema(description = "city Code")
	private String cityCode;

	@Schema(description = "district Code")
	private String districtCode;

	@Schema(description = "status")
	private Integer status;
}

