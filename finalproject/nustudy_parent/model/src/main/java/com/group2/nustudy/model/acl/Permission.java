package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *  Permission
 */
@Data
@ApiModel(description = "Permission")
@TableName("acl_permission")
public class Permission extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "parent id")
	@TableField("pid")
	private Long pid;

	@ApiModelProperty(value = "name")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "type(1:menu, 2: button)")
	@TableField("type")
	private Integer type;

	@ApiModelProperty(value = "permission value")
	@TableField("permission_value")
	private String permissionValue;

	@ApiModelProperty(value = "path")
	@TableField("path")
	private String path;

	@ApiModelProperty(value = "component")
	@TableField("component")
	private String component;

	@ApiModelProperty(value = "icon")
	@TableField("icon")
	private String icon;

	@ApiModelProperty(value = "status(0:forbidden,1:normal)")
	@TableField("status")
	private Integer status;

	@ApiModelProperty(value = "level")
	@TableField(exist = false)
	private Integer level;

	@ApiModelProperty(value = "children")
	@TableField(exist = false)
	private List<Permission> children;

	@ApiModelProperty(value = "is Selected")
	@TableField(exist = false)
	private boolean isSelect;

}

