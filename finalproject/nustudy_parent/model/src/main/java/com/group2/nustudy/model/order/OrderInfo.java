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

/**
 * Order
 */
@Data
@ApiModel(description = "Order")
@TableName("order_info")
public class OrderInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "userId")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "trade id")
	@TableField("out_trade_no")
	private String outTradeNo;

	@ApiModelProperty(value = "campus code")
	@TableField("campuscode")
	private String campuscode;

	@ApiModelProperty(value = "campus name")
	@TableField("campusname")
	private String campusname;

	@ApiModelProperty(value = "department code")
	@TableField("depcode")
	private String depcode;

	@ApiModelProperty(value = "department name")
	@TableField("depname")
	private String depname;

	@ApiModelProperty(value = "schedule Id")
	@TableField("schedule_id")
	private String scheduleId;

	@ApiModelProperty(value = "title")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "reserveDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("reserve_date")
	private Date reserveDate;

	@ApiModelProperty(value = "reserveTime(0: morning, 1: afternoon")
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

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public void setCampuscode(String campuscode) {
		this.campuscode = campuscode;
	}

	public void setCampusname(String campusname) {
		this.campusname = campusname;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}

	public void setDepname(String depname) {
		this.depname = depname;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setStudentEmail(String studentEmail) {
		this.studentEmail = studentEmail;
	}

	public void setCampusRecordId(String campusRecordId) {
		this.campusRecordId = campusRecordId;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setFetchTime(String fetchTime) {
		this.fetchTime = fetchTime;
	}

	public void setFetchAddress(String fetchAddress) {
		this.fetchAddress = fetchAddress;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getUserId() {
		return userId;
	}


	public String getCampuscode() {
		return campuscode;
	}

	public String getCampusname() {
		return campusname;
	}

	public String getDepcode() {
		return depcode;
	}

	public String getDepname() {
		return depname;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public String getTitle() {
		return title;
	}

	public Date getReserveDate() {
		return reserveDate;
	}

	public Integer getReserveTime() {
		return reserveTime;
	}


	public Integer getNumber() {
		return number;
	}


	public BigDecimal getAmount() {
		return amount;
	}

	public Date getQuitTime() {
		return quitTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}
}

