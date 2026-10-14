package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * Schedule
 */
@Data
@Schema(description = "Schedule")
@TableName("schedule")
public class Schedule extends BaseNoAutoEntity {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "campus code")
	@TableField("campuscode")
	private String campuscode;

	@Schema(description = "department code")
	@TableField("depcode")
	private String depcode;

	@Schema(description = "title")
	@TableField("title")
	private String title;

	@Schema(description = "doc name")
	@TableField("docname")
	private String docname;

	@Schema(description = "skill")
	@TableField("skill")
	private String skill;

	@Schema(description = "work date")
	@TableField("work_date")
	private String workDate;

	@Schema(description = "work time（0：AM 1：PM）")
	@TableField("work_time")
	private Integer workTime;

	@Schema(description = "reserved number")
	@TableField("reserved_number")
	private Integer reservedNumber;

	@Schema(description = "available number")
	@TableField("available_number")
	private Integer availableNumber;

	@Schema(description = "amount")
	@TableField("amount")
	private String amount;

	@Schema(description = "status（-1：finish reservation 0：unavailable 1:available）")
	@TableField("status")
	private Integer status;
}

