package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "scheduleId")
	@TableField("schedule_id")
	private Long scheduleId;

	@ApiModelProperty(value = "studentId")
	@TableField("student_id")
	private Long studentId;

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

	@ApiModelProperty(value = "payTime")
	@TableField("pay_time")
	private Date payTime;

	@ApiModelProperty(value = "quitTime")
	@TableField("quit_time")
	private Date quitTime;

	@ApiModelProperty(value = "orderStatus")
	@TableField("order_status")
	private Integer orderStatus;

	public Long getScheduleId() {
		return scheduleId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public Integer getNumber() {
		return number;
	}

	public String getFetchTime() {
		return fetchTime;
	}

	public String getFetchAddress() {
		return fetchAddress;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getPayTime() {
		return payTime;
	}

	public Date getQuitTime() {
		return quitTime;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public void setQuitTime(Date quitTime) {
		this.quitTime = quitTime;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
}

