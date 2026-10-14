package com.group2.nustudy.model.user;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Student")
@TableName("student")
public class Student extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "userId")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "name")
	@TableField("name")
	private String name;

	@Schema(description = "certificates Type")
	@TableField("certificates_type")
	private String certificatesType;

	@Schema(description = "certificatesNo")
	@TableField("certificates_no")
	private String certificatesNo;

	@Schema(description = "email")
	@TableField("email")
	private String email;

	@Schema(description = "state Code")
	@TableField("state_code")
	private String stateCode;

	@Schema(description = "city Code")
	@TableField("city_code")
	private String cityCode;

	@Schema(description = "district Code")
	@TableField("district_code")
	private String districtCode;

	@Schema(description = "address")
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

