package com.group2.nustudy.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="UserInfoQueryVo")
public class UserInfoQueryVo {

    @Schema(description = "keyword")
    private String keyword;

    @Schema(description = "status")
    private Integer status;

    @Schema(description = "authStatus")
    private Integer authStatus;

    @Schema(description = "createTimeBegin")
    private String createTimeBegin;

    @Schema(description = "createTimeEnd")
    private String createTimeEnd;

}
