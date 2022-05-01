package com.group2.nustudy.model.user;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UserInfo
 */
@Data
@ApiModel(description = "UserInfo")
@TableName("user_info")
public class UserInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "nickName")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "email")
	@TableField("email")
	private String email;

	@ApiModelProperty(value = "name")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "certificates Type")
	@TableField("certificates_type")
	private String certificatesType;

	@ApiModelProperty(value = "certificates No")
	@TableField("certificates_no")
	private String certificatesNo;

	@ApiModelProperty(value = "certificates Url")
	@TableField("certificates_url")
	private String certificatesUrl;

	@ApiModelProperty(value = "authStatus")
	@TableField("auth_status")
	private Integer authStatus;

	@ApiModelProperty(value = "status")
	@TableField("status")
	private Integer status;


	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertificatesType() {
		return certificatesType;
	}

	public void setCertificatesType(String certificatesType) {
		this.certificatesType = certificatesType;
	}

	public String getCertificatesNo() {
		return certificatesNo;
	}

	public void setCertificatesNo(String certificatesNo) {
		this.certificatesNo = certificatesNo;
	}

	public String getCertificatesUrl() {
		return certificatesUrl;
	}

	public void setCertificatesUrl(String certificatesUrl) {
		this.certificatesUrl = certificatesUrl;
	}

	public Integer getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

