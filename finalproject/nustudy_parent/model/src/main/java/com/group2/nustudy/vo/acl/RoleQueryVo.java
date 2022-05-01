package com.group2.nustudy.vo.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * RoleQueryVo
 */
@Data
@ApiModel(description = "RoleQueryVo")
public class RoleQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "role Name")
	private String roleName;

}

