package com.group2.nustudy.vo.camp;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * RegisterRule
 */
@Data
@Schema(description = "Booking Schedule Rule Vo")
public class BookingScheduleRuleVo {
	
	@Schema(description = "workDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date workDate;

	@Schema(description = "workDateMd")
	@JsonFormat(pattern = "MM-dd")
	private Date workDateMd;

	@Schema(description = "dayOfWeek")
	private String dayOfWeek;

	@Schema(description = "docCount")
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

	@Schema(description = "reservedNumber")
	private Integer reservedNumber;

	@Schema(description = "availableNumber")
	private Integer availableNumber;

	@Schema(description = "status")
	private Integer status;
}

