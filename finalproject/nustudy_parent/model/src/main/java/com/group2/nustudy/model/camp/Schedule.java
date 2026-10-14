package com.group2.nustudy.model.camp;

import com.group2.nustudy.model.base.BaseMongoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Schedule
 */
@Data
@Schema(description = "Schedule")
@Document("Schedule")
public class Schedule extends BaseMongoEntity {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "campus code")
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

	@Schema(description = "department code")
	@Indexed
	private String depcode;

	@Schema(description = "title")
	private String title;

	@Schema(description = "docname")
	private String docname;

	@Schema(description = "skill")
	private String skill;

	@Schema(description = "workDate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date workDate;

	@Schema(description = "workTime(0: morning 1: afternoon)")
	private Integer workTime;

	@Schema(description = "reservedNumber")
	private Integer reservedNumber;

	@Schema(description = "availableNumber")
	private Integer availableNumber;

	@Schema(description = "amount")
	private BigDecimal amount;

	@Schema(description = "status (-1: stop 0: full 1: available）")
	private Integer status;

	@Schema(description = "hosScheduleId")
	@Indexed
	private String hosScheduleId;

}

