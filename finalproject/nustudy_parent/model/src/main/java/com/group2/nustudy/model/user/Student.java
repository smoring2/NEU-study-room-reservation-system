package com.group2.nustudy.model.user;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author qy
 */
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

	@ApiModelProperty(value = "证件类型")
	@TableField("certificates_type")
	private String certificatesType;

	@ApiModelProperty(value = "certificatesNo")
	@TableField("certificates_no")
	private String certificatesNo;

	@ApiModelProperty(value = "email")
	@TableField("email")
	private String email;

	@ApiModelProperty(value = "省code")
	@TableField("province_code")
	private String provinceCode;

	@ApiModelProperty(value = "市code")
	@TableField("city_code")
	private String cityCode;

	@ApiModelProperty(value = "区code")
	@TableField("district_code")
	private String districtCode;

	@ApiModelProperty(value = "详情地址")
	@TableField("address")
	private String address;

//	@ApiModelProperty(value = "联系人姓名")
//	@TableField("contacts_name")
//	private String contactsName;
//
//	@ApiModelProperty(value = "联系人证件类型")
//	@TableField("contacts_certificates_type")
//	private String contactsCertificatesType;

//	@ApiModelProperty(value = "联系人证件号")
//	@TableField("contacts_certificates_no")
//	private String contactsCertificatesNo;
//
//	@ApiModelProperty(value = "联系人手机")
//	@TableField("contacts_phone")
//	private String contactsPhone;

//	@ApiModelProperty(value = "就诊卡")
//	@TableField("card_no")
//	private String cardNo;
//
//	@ApiModelProperty(value = "状态（0：默认 1：已认证）")
//	@TableField("status")
//	private String status;
}

