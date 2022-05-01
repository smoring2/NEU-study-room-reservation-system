package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * role permission
 */
@Data
@ApiModel(description = "role permission")
@TableName("acl_role_permission")
public class RolePermission extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "role id")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "permission Id")
	@TableField("permission_id")
	private Long permissionId;

}

