package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Role
 */
@Data
@Schema(description = "Role")
@TableName("acl_role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "roleName")
	@TableField("role_name")
	private String roleName;

	@Schema(description = "remark")
	@TableField("remark")
	private String remark;

}

