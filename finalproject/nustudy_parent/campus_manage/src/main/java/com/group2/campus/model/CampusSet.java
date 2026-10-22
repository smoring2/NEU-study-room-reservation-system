package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
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
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String signKey;

	@ApiModelProperty(value = "api url")
	private String apiUrl;

}
