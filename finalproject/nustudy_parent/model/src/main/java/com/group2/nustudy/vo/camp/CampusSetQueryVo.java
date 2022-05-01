package com.group2.nustudy.vo.camp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CampusSetQueryVo {

    @ApiModelProperty(value = "campus name")
    private String campusname;

    @ApiModelProperty(value = "campus code")
    private String campuscode;

    public String getCampusname() {
        return campusname;
    }

    public String getCampuscode() {
        return campuscode;
    }
}
