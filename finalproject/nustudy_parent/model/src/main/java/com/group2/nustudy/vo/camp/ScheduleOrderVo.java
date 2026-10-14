package com.group2.nustudy.vo.camp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Schema(description = "Schedule")
public class ScheduleOrderVo {

	@Schema(description = "campus code")
	private String campuscode;

	@Schema(description = "campus name")
	private String campusname;

	@Schema(description = "department code")
	private String depcode;

	@Schema(description = "department name")
	private String depname;

	@Schema(description = "hosScheduleId")
	private String hosScheduleId;

	@Schema(description = "title")
	private String title;

	@Schema(description = "reserveDate")
	private Date reserveDate;

	@Schema(description = "availableNumber")
	private Integer availableNumber;

	@Schema(description = "reserveTime")
	private Integer reserveTime;

	@Schema(description = "amount")
	private BigDecimal amount;

	@Schema(description = "quitTime")
	private Date quitTime;

	@Schema(description = "startTime")
	private Date startTime;

	@Schema(description = "endTime")
	private Date endTime;

	@Schema(description = "stopTime")
	private Date stopTime;

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

	public String getHosScheduleId() {
		return hosScheduleId;
	}

	public String getTitle() {
		return title;
	}

	public Date getReserveDate() {
		return reserveDate;
	}

	public Integer getAvailableNumber() {
		return availableNumber;
	}

	public Integer getReserveTime() {
		return reserveTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getQuitTime() {
		return quitTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getStopTime() {
		return stopTime;
	}
}

