package com.group2.nustudy.model.camp;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Campus Settings")
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

	@Schema(description = "campus name")
	@TableField("campusname")
	private String campusname;

	@Schema(description = "campus code")
	@TableField("campuscode")
	private String campuscode;

	@Schema(description = "api_url")
	@TableField("api_url")
	private String apiUrl;

	@Schema(description = "sign Key")
	@TableField("sign_key")
	private String signKey;

	@Schema(description = "contacts Name")
	@TableField("contacts_name")
	private String contactsName;

	@Schema(description = "contacts Phone")
	@TableField("contacts_phone")
	private String contactsPhone;

	@Schema(description = "status")
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

