package com.group2.nustudy.model.camp;

import com.group2.nustudy.model.base.BaseMongoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ApiModel(description = "Department")
@Document("Department")
public class Department extends BaseMongoEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "campus code")
	@Indexed
	private String campuscode;

	public String getCampuscode() {
		return campuscode;
	}

	public void setCampuscode(String campuscode) {
		this.campuscode = campuscode;
	}

	public String getDepcode() {
		return depcode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}

	public String getDepname() {
		return depname;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getBigcode() {
		return bigcode;
	}

	public void setBigcode(String bigcode) {
		this.bigcode = bigcode;
	}

	public String getBigname() {
		return bigname;
	}

	public void setBigname(String bigname) {
		this.bigname = bigname;
	}

	@ApiModelProperty(value = "department code")
	@Indexed(unique = true)
	private String depcode;

	@ApiModelProperty(value = "department name")
	private String depname;

	@ApiModelProperty(value = "intro")
	private String intro;

	@ApiModelProperty(value = "bigcode")
	private String bigcode;

	@ApiModelProperty(value = "bigname")
	private String bigname;

}

