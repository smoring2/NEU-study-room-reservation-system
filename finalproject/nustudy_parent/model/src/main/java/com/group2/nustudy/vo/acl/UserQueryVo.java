package com.group2.nustudy.vo.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * UserQueryVo
 */
@Data
@ApiModel(description = "UserQueryVo")
public class UserQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "username")
	private String username;

	@ApiModelProperty(value = "nickName")
	private String nickName;

}

