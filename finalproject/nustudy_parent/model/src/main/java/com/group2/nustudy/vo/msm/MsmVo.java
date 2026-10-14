package com.group2.nustudy.vo.msm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


import java.util.Map;

/**
 * for further plan
 */
@Data
@Schema(description = "message")
public class MsmVo {

    @Schema(description = "phone")
    private String phone;

    @Schema(description = "templateCode")
    private String templateCode;

    @Schema(description = "param")
    private Map<String,Object> param;
}
