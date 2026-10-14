package com.group2.nustudy.model.order;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Order
 */
@Data
@Schema(description = "Order")
@TableName("order_info")
public class OrderInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "userId")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "trade id")
	@TableField("out_trade_no")
	private String outTradeNo;

	@Schema(description = "campus code")
	@TableField("campuscode")
	private String campuscode;

	@Schema(description = "campus name")
	@TableField("campusname")
	private String campusname;

	@Schema(description = "department code")
	@TableField("depcode")
	private String depcode;

	@Schema(description = "department name")
	@TableField("depname")
	private String depname;

	@Schema(description = "schedule Id")
	@TableField("schedule_id")
	private String scheduleId;

	@Schema(description = "title")
	@TableField("title")
	private String title;

	@Schema(description = "reserveDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@TableField("reserve_date")
	private Date reserveDate;

	@Schema(description = "reserveTime(0: morning, 1: afternoon")
	@TableField("reserve_time")
	private Integer reserveTime;

	@Schema(description = "studentId")
	@TableField("student_id")
	private Long studentId;

	@Schema(description = "studentName")
	@TableField("student_name")
	private String studentName;

	@Schema(description = "studentEmail")
	@TableField("student_email")
	private String studentEmail;

	@Schema(description = "campusRecordId")
	@TableField("campus_record_id")
	private String campusRecordId;

	@Schema(description = "number")
	@TableField("number")
	private Integer number;

	@Schema(description = "fetchTime")
	@TableField("fetch_time")
	private String fetchTime;

	@Schema(description = "fetchAddress")
	@TableField("fetch_address")
	private String fetchAddress;

	@Schema(description = "amount")
	@TableField("amount")
	private BigDecimal amount;

	@Schema(description = "quitTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@TableField("quit_time")
	private Date quitTime;

	@Schema(description = "orderStatus")
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

