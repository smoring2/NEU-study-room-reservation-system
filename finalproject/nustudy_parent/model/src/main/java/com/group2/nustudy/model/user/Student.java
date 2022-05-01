package com.group2.nustudy.model.user;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "Student")
@TableName("student")
public class Student extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "userId")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "name")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "certificates Type")
	@TableField("certificates_type")
	private String certificatesType;

	@ApiModelProperty(value = "certificatesNo")
	@TableField("certificates_no")
	private String certificatesNo;

	@ApiModelProperty(value = "email")
	@TableField("email")
	private String email;

	@ApiModelProperty(value = "state Code")
	@TableField("state_code")
	private String stateCode;

	@ApiModelProperty(value = "city Code")
	@TableField("city_code")
	private String cityCode;

	@ApiModelProperty(value = "district Code")
	@TableField("district_code")
	private String districtCode;

	@ApiModelProperty(value = "address")
	@TableField("address")
	private String address;

	public Long getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getCertificatesType() {
		return certificatesType;
	}

	public String getCertificatesNo() {
		return certificatesNo;
	}

	public String getEmail() {
		return email;
	}

	public String getStateCode() {
		return stateCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public String getAddress() {
		return address;
	}
}

