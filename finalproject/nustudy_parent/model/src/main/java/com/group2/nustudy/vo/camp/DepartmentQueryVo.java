package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Department")
public class DepartmentQueryVo {
	
	@ApiModelProperty(value = "campus code")
	private String campuscode;

	@ApiModelProperty(value = "department code")
	private String depcode;

	@ApiModelProperty(value = "department name")
	private String depname;

	@ApiModelProperty(value = "bigcode")
	private String bigcode;

	@ApiModelProperty(value = "bigname")
	private String bigname;

	public void setCampuscode(String campuscode) {
		this.campuscode = campuscode;
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

}

