package com.group2.nustudy.vo.acl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * UserQueryVo
 */
@Data
@Schema(description = "UserQueryVo")
public class UserQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "username")
	private String username;

	@Schema(description = "nickName")
	private String nickName;

}

