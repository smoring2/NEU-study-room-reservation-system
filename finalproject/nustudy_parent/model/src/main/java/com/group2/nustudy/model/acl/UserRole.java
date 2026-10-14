package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * user role
 */
@Data
@Schema(description = "user role")
@TableName("acl_user_role")
public class UserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "role Id")
	@TableField("role_id")
	private Long roleId;

	@Schema(description = "user Id")
	@TableField("user_id")
	private Long userId;

}

