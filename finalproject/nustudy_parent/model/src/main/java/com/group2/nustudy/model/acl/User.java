package com.group2.nustudy.model.acl;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * User
 */
@Data
@Schema(description = "User")
@TableName("acl_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Schema(description = "username")
	@TableField("username")
	private String username;

	@Schema(description = "password")
	@TableField("password")
	private String password;

	@Schema(description = "nickName")
	@TableField("nick_name")
	private String nickName;

	@Schema(description = "salt")
	@TableField("salt")
	private String salt;

	@Schema(description = "token")
	@TableField("token")
	private String token;

}



