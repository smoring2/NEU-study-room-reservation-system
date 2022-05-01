package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "Department")
public class DepartmentVo {
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

	public List<DepartmentVo> getChildren() {
		return children;
	}

	public void setChildren(List<DepartmentVo> children) {
		this.children = children;
	}

	@ApiModelProperty(value = "department code")
	private String depcode;

	@ApiModelProperty(value = "department name")
	private String depname;

	@ApiModelProperty(value = "children")
	private List<DepartmentVo> children;

}

