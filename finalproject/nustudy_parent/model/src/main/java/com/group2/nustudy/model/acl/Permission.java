package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 *  Permission
 */
@Data
@Schema(description = "Permission")
@TableName("acl_permission")
public class Permission extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "parent id")
	@TableField("pid")
	private Long pid;

	@Schema(description = "name")
	@TableField("name")
	private String name;

	@Schema(description = "type(1:menu, 2: button)")
	@TableField("type")
	private Integer type;

	@Schema(description = "permission value")
	@TableField("permission_value")
	private String permissionValue;

	@Schema(description = "path")
	@TableField("path")
	private String path;

	@Schema(description = "component")
	@TableField("component")
	private String component;

	@Schema(description = "icon")
	@TableField("icon")
	private String icon;

	@Schema(description = "status(0:forbidden,1:normal)")
	@TableField("status")
	private Integer status;

	@Schema(description = "level")
	@TableField(exist = false)
	private Integer level;

	@Schema(description = "children")
	@TableField(exist = false)
	private List<Permission> children;

	@Schema(description = "is Selected")
	@TableField(exist = false)
	private boolean isSelect;

}

