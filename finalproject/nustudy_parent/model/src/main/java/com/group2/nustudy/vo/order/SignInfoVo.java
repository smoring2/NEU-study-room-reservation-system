package com.group2.nustudy.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Sign Info")
public class SignInfoVo  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "apiUrl")
	private String apiUrl;

	@ApiModelProperty(value = "signKey")
	private String signKey;

	public String getApiUrl() {
		return apiUrl;
	}

	public String getSignKey() {
		return signKey;
	}
}

