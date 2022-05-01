package com.group2.nustudy.model.camp;

import com.group2.nustudy.model.base.BaseMongoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Schedule
 */
@Data
@ApiModel(description = "Schedule")
@Document("Schedule")
public class Schedule extends BaseMongoEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "campus code")
	@Indexed
	private String campuscode;

	public String getCampuscode() {
		return campuscode;
	}

	public void setCampuscode(String campuscode) {
		this.campuscode = campuscode;
	}

	public String getDepcode() {
		return depcode;
	}

	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public Integer getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getHosScheduleId() {
		return hosScheduleId;
	}

	public void setHosScheduleId(String hosScheduleId) {
		this.hosScheduleId = hosScheduleId;
	}

	@ApiModelProperty(value = "department code")
	@Indexed
	private String depcode;

	@ApiModelProperty(value = "title")
	private String title;

	@ApiModelProperty(value = "docname")
	private String docname;

	@ApiModelProperty(value = "skill")
	private String skill;

	@ApiModelProperty(value = "workDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date workDate;

	@ApiModelProperty(value = "workTime(0: morning 1: afternoon)")
	private Integer workTime;

	@ApiModelProperty(value = "reservedNumber")
	private Integer reservedNumber;

	@ApiModelProperty(value = "availableNumber")
	private Integer availableNumber;

	@ApiModelProperty(value = "amount")
	private BigDecimal amount;

	@ApiModelProperty(value = "status (-1: stop 0: full 1: available）")
	private Integer status;

	@ApiModelProperty(value = "hosScheduleId")
	@Indexed
	private String hosScheduleId;

}

