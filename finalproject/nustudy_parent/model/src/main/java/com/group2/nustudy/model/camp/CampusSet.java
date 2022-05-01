package com.group2.nustudy.model.camp;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Campus Settings")
@TableName("campus_set")
public class CampusSet extends BaseEntity {
	public String getSignKey() {
		return signKey;
	}

	public String getCampuscode() {
		return campuscode;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "campus name")
	@TableField("campusname")
	private String campusname;

	@ApiModelProperty(value = "campus code")
	@TableField("campuscode")
	private String campuscode;

	@ApiModelProperty(value = "api_url")
	@TableField("api_url")
	private String apiUrl;

	@ApiModelProperty(value = "sign Key")
	@TableField("sign_key")
	private String signKey;

	@ApiModelProperty(value = "contacts Name")
	@TableField("contacts_name")
	private String contactsName;

	@ApiModelProperty(value = "contacts Phone")
	@TableField("contacts_phone")
	private String contactsPhone;

	@ApiModelProperty(value = "status")
	@TableField("status")
	private Integer status;

	public String getCampusname() {
		return campusname;
	}

	public void setCampusname(String campusname) {
		this.campusname = campusname;
	}

	public String getApiUrl() {
		return apiUrl;
	}
}

