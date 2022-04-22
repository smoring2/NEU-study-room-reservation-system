package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CampusSetQueryVo {

    @ApiModelProperty(value = "医院名称")
    private String hosname;

    @ApiModelProperty(value = "医院编号")
    private String hoscode;

    public String getHosname() {
        return hosname;
    }

    public String getHoscode() {
        return hoscode;
    }
}
