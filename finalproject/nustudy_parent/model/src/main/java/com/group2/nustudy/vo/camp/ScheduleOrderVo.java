package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "Schedule")
public class ScheduleOrderVo {

	@ApiModelProperty(value = "campus code")
	private String campuscode;

	@ApiModelProperty(value = "campus name")
	private String campusname;

	@ApiModelProperty(value = "department code")
	private String depcode;

	@ApiModelProperty(value = "department name")
	private String depname;

	@ApiModelProperty(value = "hosScheduleId")
	private String hosScheduleId;

	@ApiModelProperty(value = "title")
	private String title;

	@ApiModelProperty(value = "reserveDate")
	private Date reserveDate;

	@ApiModelProperty(value = "availableNumber")
	private Integer availableNumber;

	@ApiModelProperty(value = "reserveTime")
	private Integer reserveTime;

	@ApiModelProperty(value = "amount")
	private BigDecimal amount;

	@ApiModelProperty(value = "quitTime")
	private Date quitTime;

	@ApiModelProperty(value = "startTime")
	private Date startTime;

	@ApiModelProperty(value = "endTime")
	private Date endTime;

	@ApiModelProperty(value = "stopTime")
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

