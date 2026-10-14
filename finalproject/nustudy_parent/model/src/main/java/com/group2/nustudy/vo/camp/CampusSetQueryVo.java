package com.group2.nustudy.vo.camp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusSetQueryVo {

    @Schema(description = "campus name")
    private String campusname;

    @Schema(description = "campus code")
    private String campuscode;

    public String getCampusname() {
        return campusname;
    }

    public String getCampuscode() {
        return campuscode;
    }
}
