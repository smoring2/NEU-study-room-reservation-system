package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Department")
public class DepartmentQueryVo {
	
	@ApiModelProperty(value = "医院编号")
	private String hoscode;

	public void setHoscode(String hoscode) {
		this.hoscode = hoscode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public void setBigcode(String bigcode) {
		this.bigcode = bigcode;
	}

	public void setBigname(String bigname) {
		this.bigname = bigname;
	}

	@ApiModelProperty(value = "科室编号")
	private String depcode;

	@ApiModelProperty(value = "科室名称")
	private String depname;

	@ApiModelProperty(value = "大科室编号")
	private String bigcode;

	@ApiModelProperty(value = "大科室名称")
	private String bigname;

}

