package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * User
 */
@Data
@ApiModel(description = "User")
@TableName("acl_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "username")
	@TableField("username")
	private String username;

	@ApiModelProperty(value = "password")
	@TableField("password")
	private String password;

	@ApiModelProperty(value = "nickName")
	@TableField("nick_name")
	private String nickName;

	@ApiModelProperty(value = "salt")
	@TableField("salt")
	private String salt;

	@ApiModelProperty(value = "token")
	@TableField("token")
	private String token;

}



