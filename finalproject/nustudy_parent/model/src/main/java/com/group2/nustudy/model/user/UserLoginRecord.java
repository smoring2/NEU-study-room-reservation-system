package com.group2.nustudy.model.user;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * user login record
 */
@Data
@ApiModel(description = "user login record")
@TableName("user_login_record")
public class UserLoginRecord extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "user Id")
	@TableField("user_id")
	private Long userId;

	@ApiModelProperty(value = "ip")
	@TableField("ip")
	private String ip;

}

