package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * user role
 */
@Data
@ApiModel(description = "user role")
@TableName("acl_user_role")
public class UserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "role Id")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "user Id")
	@TableField("user_id")
	private Long userId;

}

