package com.group2.nustudy.vo.camp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Schedule")
public class ScheduleQueryVo {
	
	@Schema(description = "campus code")
	private String campuscode;

	@Schema(description = "department code")
	private String depcode;

	@Schema(description = "doccode")
	private String doccode;

	@Schema(description = "workDate")
	private Date workDate;

	@Schema(description = "workTime")
	private Integer workTime;

	public void setCampuscode(String campuscode) {
		this.campuscode = campuscode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}
}

