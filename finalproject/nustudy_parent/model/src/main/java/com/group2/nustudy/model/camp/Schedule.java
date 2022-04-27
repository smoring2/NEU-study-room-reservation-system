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
 * <p>
 * Schedule
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "Schedule")
@Document("Schedule")
public class Schedule extends BaseMongoEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "医院编号")
	@Indexed //普通索引
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

	@ApiModelProperty(value = "科室编号")
	@Indexed //普通索引
	private String depcode;

	@ApiModelProperty(value = "职称")
	private String title;

	@ApiModelProperty(value = "医生名称")
	private String docname;

	@ApiModelProperty(value = "擅长技能")
	private String skill;

	@ApiModelProperty(value = "排班日期")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date workDate;

	@ApiModelProperty(value = "排班时间（0：上午 1：下午）")
	private Integer workTime;

	@ApiModelProperty(value = "可预约数")
	private Integer reservedNumber;

	@ApiModelProperty(value = "剩余预约数")
	private Integer availableNumber;

	@ApiModelProperty(value = "挂号费")
	private BigDecimal amount;

	@ApiModelProperty(value = "排班状态（-1：停诊 0：停约 1：可约）")
	private Integer status;

	@ApiModelProperty(value = "排班编号（医院自己的排班主键）")
	@Indexed //普通索引
	private String hosScheduleId;

}

