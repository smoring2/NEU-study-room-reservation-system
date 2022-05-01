package com.group2.nustudy.vo.camp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * RegisterRule
 */
@Data
@ApiModel(description = "Booking Schedule Rule Vo")
public class BookingScheduleRuleVo {
	
	@ApiModelProperty(value = "workDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date workDate;

	@ApiModelProperty(value = "workDateMd")
	@JsonFormat(pattern = "MM-dd")
	private Date workDateMd;

	@ApiModelProperty(value = "dayOfWeek")
	private String dayOfWeek;

	@ApiModelProperty(value = "docCount")
	private Integer docCount;

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Date getWorkDateMd() {
		return workDateMd;
	}

	public void setWorkDateMd(Date workDateMd) {
		this.workDateMd = workDateMd;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getDocCount() {
		return docCount;
	}

	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}

	public Integer getReservedNumber() {
		return reservedNumber;
	}

	public void setReservedNumber(Integer reservedNumber) {
		this.reservedNumber = reservedNumber;
	}

	public Integer getAvailableNumber() {
		return availableNumber;
	}

	public void setAvailableNumber(Integer availableNumber) {
		this.availableNumber = availableNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ApiModelProperty(value = "reservedNumber")
	private Integer reservedNumber;

	@ApiModelProperty(value = "availableNumber")
	private Integer availableNumber;

	@ApiModelProperty(value = "status")
	private Integer status;
}

