package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Student")
@TableName("student")
public class Student extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "user id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "name")
	@TableField("name")
	private String name;

	@Schema(description = "certificates type")
	@TableField("certificates_type")
	private String certificatesType;

	@Schema(description = "certificates number")
	@TableField("certificates_no")
	private String certificatesNo;

	@Schema(description = "email")
	@TableField("email")
	private String email;

	@Schema(description = "state code")
	@TableField("state_code")
	private String stateCode;

	@Schema(description = "city code")
	@TableField("city_code")
	private String cityCode;

	@Schema(description = "district code")
	@TableField("district_code")
	private String districtCode;

	@Schema(description = "specific address")
	@TableField("address")
	private String address;


}

