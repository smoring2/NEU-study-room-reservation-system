package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "Schedule")
public class ScheduleQueryVo {
	
	@ApiModelProperty(value = "campus code")
	private String campuscode;

	@ApiModelProperty(value = "department code")
	private String depcode;

	@ApiModelProperty(value = "doccode")
	private String doccode;

	@ApiModelProperty(value = "workDate")
	private Date workDate;

	@ApiModelProperty(value = "workTime")
	private Integer workTime;

	public void setCampuscode(String campuscode) {
		this.campuscode = campuscode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}
}

