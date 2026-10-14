package com.group2.nustudy.vo.order;

import com.group2.nustudy.vo.msm.MsmVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "OrderMqVo")
public class OrderMqVo {

	@Schema(description = "reservedNumber")
	private Integer reservedNumber;

	@Schema(description = "availableNumber")
	private Integer availableNumber;

	@Schema(description = "scheduleId")
	private String scheduleId;

	@Schema(description = "msmVo")
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

