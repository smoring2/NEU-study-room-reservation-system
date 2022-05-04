package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Student")
@TableName("student")
public class Student extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "user id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "name")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "certificates type")
	@TableField("certificates_type")
	private String certificatesType;

	@ApiModelProperty(value = "certificates number")
	@TableField("certificates_no")
	private String certificatesNo;

	@ApiModelProperty(value = "email")
	@TableField("email")
	private String email;

	@ApiModelProperty(value = "state code")
	@TableField("state_code")
	private String stateCode;

	@ApiModelProperty(value = "city code")
	@TableField("city_code")
	private String cityCode;

	@ApiModelProperty(value = "district code")
	@TableField("district_code")
	private String districtCode;

	@ApiModelProperty(value = "specific address")
	@TableField("address")
	private String address;


}

