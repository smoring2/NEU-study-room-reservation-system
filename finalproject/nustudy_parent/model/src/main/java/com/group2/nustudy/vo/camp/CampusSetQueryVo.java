package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CampusSetQueryVo {

    @ApiModelProperty(value = "医院名称")
    private String campusname;

    @ApiModelProperty(value = "医院编号")
    private String campuscode;

    public String getCampusname() {
        return campusname;
    }

    public String getCampuscode() {
        return campuscode;
    }
}
