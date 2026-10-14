package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * role permission
 */
@Data
@Schema(description = "role permission")
@TableName("acl_role_permission")
public class RolePermission extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "role id")
	@TableField("role_id")
	private Long roleId;

	@Schema(description = "permission Id")
	@TableField("permission_id")
	private Long permissionId;

}

