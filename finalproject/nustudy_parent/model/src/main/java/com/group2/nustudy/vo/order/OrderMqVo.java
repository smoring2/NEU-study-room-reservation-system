package com.group2.nustudy.vo.order;

import com.group2.nustudy.vo.msm.MsmVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "OrderMqVo")
public class OrderMqVo {

	@ApiModelProperty(value = "reservedNumber")
	private Integer reservedNumber;

	@ApiModelProperty(value = "availableNumber")
	private Integer availableNumber;

	@ApiModelProperty(value = "scheduleId")
	private String scheduleId;

	@ApiModelProperty(value = "msmVo")
	private MsmVo msmVo;

	public void setReservedNumber(Integer reservedNumber) {
		this.reservedNumber = reservedNumber;
	}

	public void setAvailableNumber(Integer availableNumber) {
		this.availableNumber = availableNumber;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
}

