package com.group2.nustudy.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="RegisterVo")
public class RegisterVo {

    @Schema(description = "mobile")
    private String mobile;

    @Schema(description = "password")
    private String password;

    @Schema(description = "code")
    private String code;
}
