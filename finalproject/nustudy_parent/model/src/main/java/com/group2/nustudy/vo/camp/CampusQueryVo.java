package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Hospital")
public class CampusQueryVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "医院编号")
	private String campuscode;

	@ApiModelProperty(value = "医院名称")
	private String campusname;

	@ApiModelProperty(value = "医院类型")
	private String campustype;

	@ApiModelProperty(value = "省code")
	private String provinceCode;

	@ApiModelProperty(value = "市code")
	private String cityCode;

	@ApiModelProperty(value = "区code")
	private String districtCode;

	@ApiModelProperty(value = "状态")
	private Integer status;
}

