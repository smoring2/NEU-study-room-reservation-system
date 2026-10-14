package com.group2.nustudy.vo.acl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * RoleQueryVo
 */
@Data
@Schema(description = "RoleQueryVo")
public class RoleQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "role Name")
	private String roleName;

}

