package com.group2.nustudy.vo.camp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Department")
public class DepartmentQueryVo {
	
	@Schema(description = "campus code")
	private String campuscode;

	@Schema(description = "department code")
	private String depcode;

	@Schema(description = "department name")
	private String depname;

	@Schema(description = "bigcode")
	private String bigcode;

	@Schema(description = "bigname")
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

