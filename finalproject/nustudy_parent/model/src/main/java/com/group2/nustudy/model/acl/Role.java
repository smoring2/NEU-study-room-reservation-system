package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Role
 */
@Data
@ApiModel(description = "Role")
@TableName("acl_role")
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "roleName")
	@TableField("role_name")
	private String roleName;

	@ApiModelProperty(value = "remark")
	@TableField("remark")
	private String remark;

}

