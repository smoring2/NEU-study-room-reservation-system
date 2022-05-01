package com.group2.nustudy.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Login Vo")
public class LoginVo {

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "ip")
    private String ip;
}
