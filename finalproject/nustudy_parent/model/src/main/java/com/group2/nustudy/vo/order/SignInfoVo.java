package com.group2.nustudy.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Sign Info")
public class SignInfoVo  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "apiUrl")
	private String apiUrl;

	@Schema(description = "signKey")
	private String signKey;

	public String getApiUrl() {
		return apiUrl;
	}

	public String getSignKey() {
		return signKey;
	}
}

