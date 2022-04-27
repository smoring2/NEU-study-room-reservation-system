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

	public String getHoscode() {
		return hoscode;
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
	@TableField("hosname")
	private String hosname;

	@ApiModelProperty(value = "医院编号")
	@TableField("hoscode")
	private String hoscode;

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

	public String getHosname() {
		return hosname;
	}

	public void setHosname(String hosname) {
		this.hosname = hosname;
	}

	public void setHoscode(String hoscode) {
		this.hoscode = hoscode;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getContactsName() {
		return contactsName;
	}

	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}
}

