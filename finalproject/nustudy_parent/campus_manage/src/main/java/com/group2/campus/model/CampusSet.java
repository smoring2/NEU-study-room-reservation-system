package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qy
 */
@Data
@ApiModel(description = "CampusSet")
@TableName("campus_set")
public class CampusSet extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "医院编号")
	private String campuscode;

	@ApiModelProperty(value = "签名秘钥")
	private String signKey;

	@ApiModelProperty(value = "api基础路径")
	private String apiUrl;

}

