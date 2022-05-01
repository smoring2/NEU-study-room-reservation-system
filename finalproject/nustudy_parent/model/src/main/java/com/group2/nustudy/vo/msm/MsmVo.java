package com.group2.nustudy.vo.msm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Map;

/**
 * for further plan
 */
@Data
@ApiModel(description = "message")
public class MsmVo {

    @ApiModelProperty(value = "phone")
    private String phone;

    @ApiModelProperty(value = "templateCode")
    private String templateCode;

    @ApiModelProperty(value = "param")
    private Map<String,Object> param;
}
