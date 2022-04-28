package com.group2.nustudy.model.order;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "Order")
@TableName("order_info")
public class OrderInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "userId")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "订单交易号")
	@TableField("out_trade_no")
	private String outTradeNo;

	@ApiModelProperty(value = "医院编号")
	@TableField("campuscode")
	private String campuscode;

	@ApiModelProperty(value = "医院名称")
	@TableField("campusname")
	private String campusname;

	@ApiModelProperty(value = "科室编号")
	@TableField("depcode")
	private String depcode;

	@ApiModelProperty(value = "科室名称")
	@TableField("depname")
	private String depname;

	@ApiModelProperty(value = "排班id")
	@TableField("schedule_id")
	private String scheduleId;

	@ApiModelProperty(value = "医生职称")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "安排日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("reserve_date")
	private Date reserveDate;

	@ApiModelProperty(value = "安排时间（0：上午 1：下午）")
	@TableField("reserve_time")
	private Integer reserveTime;

	@ApiModelProperty(value = "studentId")
	@TableField("student_id")
	private Long studentId;

	@ApiModelProperty(value = "studentName")
	@TableField("student_name")
	private String studentName;

	@ApiModelProperty(value = "studentEmail")
	@TableField("student_email")
	private String studentEmail;

	@ApiModelProperty(value = "campusRecordId")
	@TableField("campus_record_id")
	private String campusRecordId;

	@ApiModelProperty(value = "number")
	@TableField("number")
	private Integer number;

	@ApiModelProperty(value = "fetchTime")
	@TableField("fetch_time")
	private String fetchTime;

	@ApiModelProperty(value = "fetchAddress")
	@TableField("fetch_address")
	private String fetchAddress;

	@ApiModelProperty(value = "amount")
	@TableField("amount")
	private BigDecimal amount;

	@ApiModelProperty(value = "quitTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@TableField("quit_time")
	private Date quitTime;

	@ApiModelProperty(value = "orderStatus")
	@TableField("order_status")
	private Integer orderStatus;

}

