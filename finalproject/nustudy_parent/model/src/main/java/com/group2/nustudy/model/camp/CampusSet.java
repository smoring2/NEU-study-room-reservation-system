package com.group2.nustudy.model.camp;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Campus Settings")
@TableName("hospital_set")
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

	@ApiModelProperty(value = "医院名称")
	@TableField("campusname")
	private String campusname;

	@ApiModelProperty(value = "医院编号")
	@TableField("campuscode")
	private String campuscode;

	@ApiModelProperty(value = "api基础路径")
	@TableField("api_url")
	private String apiUrl;

	@ApiModelProperty(value = "签名秘钥")
	@TableField("sign_key")
	private String signKey;

	@ApiModelProperty(value = "联系人姓名")
	@TableField("contacts_name")
	private String contactsName;

	@ApiModelProperty(value = "联系人手机")
	@TableField("contacts_phone")
	private String contactsPhone;

	@ApiModelProperty(value = "状态")
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

