package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "CampusSet")
@TableName("campus_set")
public class CampusSet extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "campus code")
	private String campuscode;

	@ApiModelProperty(value = "sign key")
	private String signKey;

	@ApiModelProperty(value = "api url")
	private String apiUrl;

}

