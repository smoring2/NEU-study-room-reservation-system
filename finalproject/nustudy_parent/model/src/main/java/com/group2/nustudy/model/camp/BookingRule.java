package com.group2.nustudy.model.camp;

import com.alibaba.fastjson2.JSONArray;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.ObjectUtils;

import java.util.List;
@Data
@Schema(description = "BookingRule")
@Document("BookingRule")
public class BookingRule {
	
	@Schema(description = "booking cycle")
	private Integer cycle;

	@Schema(description = "releaseTime")
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

	@Schema(description = "stopTime")
	private String stopTime;

	@Schema(description = "quitDay")
	private Integer quitDay;

	@Schema(description = "quitTime")
	private String quitTime;

	@Schema(description = "rule")
	private List<String> rule;

	/**
	 *
	 * @param rule
	 */
	public void setRule(String rule) {
		if(!ObjectUtils.isEmpty(rule)) {
			this.rule = JSONArray.parseArray(rule, String.class);
		}
	}

}

