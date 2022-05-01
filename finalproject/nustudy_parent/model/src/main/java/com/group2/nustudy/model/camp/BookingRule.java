package com.group2.nustudy.model.camp;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

import java.util.List;
@Data
@ApiModel(description = "BookingRule")
@Document("BookingRule")
public class BookingRule {
	
	@ApiModelProperty(value = "booking cycle")
	private Integer cycle;

	@ApiModelProperty(value = "releaseTime")
	private String releaseTime;

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public Integer getQuitDay() {
		return quitDay;
	}

	public void setQuitDay(Integer quitDay) {
		this.quitDay = quitDay;
	}

	public String getQuitTime() {
		return quitTime;
	}

	public void setQuitTime(String quitTime) {
		this.quitTime = quitTime;
	}

	public List<String> getRule() {
		return rule;
	}

	public void setRule(List<String> rule) {
		this.rule = rule;
	}

	@ApiModelProperty(value = "stopTime")
	private String stopTime;

	@ApiModelProperty(value = "quitDay")
	private Integer quitDay;

	@ApiModelProperty(value = "quitTime")
	private String quitTime;

	@ApiModelProperty(value = "rule")
	private List<String> rule;

	/**
	 *
	 * @param rule
	 */
	public void setRule(String rule) {
		if(!StringUtils.isEmpty(rule)) {
			this.rule = JSONArray.parseArray(rule, String.class);
		}
	}

}

