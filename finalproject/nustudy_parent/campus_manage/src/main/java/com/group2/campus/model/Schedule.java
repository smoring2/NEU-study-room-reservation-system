package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * Schedule
 * </p>
 *
 * @author qy
 */
@Data
@ApiModel(description = "Schedule")
@TableName("schedule")
public class Schedule extends BaseNoAutoEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "campus code")
	@TableField("campuscode")
	private String campuscode;

	@ApiModelProperty(value = "department code")
	@TableField("depcode")
	private String depcode;

	@ApiModelProperty(value = "title")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "doc name")
	@TableField("docname")
	private String docname;

	@ApiModelProperty(value = "skill")
	@TableField("skill")
	private String skill;

	@ApiModelProperty(value = "work date")
	@TableField("work_date")
	private String workDate;

	@ApiModelProperty(value = "work time（0：AM 1：PM）")
	@TableField("work_time")
	private Integer workTime;

	@ApiModelProperty(value = "reserved number")
	@TableField("reserved_number")
	private Integer reservedNumber;

	@ApiModelProperty(value = "available number")
	@TableField("available_number")
	private Integer availableNumber;

	@ApiModelProperty(value = "amount")
	@TableField("amount")
	private String amount;

	@ApiModelProperty(value = "status（-1：finish reservation 0：unavailable 1:available）")
	@TableField("status")
	private Integer status;
}

