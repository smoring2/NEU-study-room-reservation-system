package com.group2.nustudy.model.user;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * user login record
 */
@Data
@Schema(description = "user login record")
@TableName("user_login_record")
public class UserLoginRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "user Id")
	@TableField("user_id")
	private Long userId;

	@Schema(description = "ip")
	@TableField("ip")
	private String ip;

}

