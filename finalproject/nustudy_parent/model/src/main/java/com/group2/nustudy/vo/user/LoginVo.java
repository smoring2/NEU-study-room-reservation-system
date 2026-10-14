package com.group2.nustudy.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="Login Vo")
public class LoginVo {

    @Schema(description = "email")
    private String email;

    @Schema(description = "code")
    private String code;

    @Schema(description = "ip")
    private String ip;
}
