package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "CampusSet")
@TableName("campus_set")
public class CampusSet extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Schema(description = "campus code")
	private String campuscode;

	@Schema(description = "sign key")
	private String signKey;

	@Schema(description = "api url")
	private String apiUrl;

}

