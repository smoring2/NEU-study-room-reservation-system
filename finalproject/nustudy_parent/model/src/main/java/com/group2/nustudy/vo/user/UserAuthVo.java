package com.group2.nustudy.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description="UserAuthVo")
public class UserAuthVo {

    @Schema(description = "name")
    @TableField("name")
    private String name;

    @Schema(description = "certificatesType")
    @TableField("certificates_type")
    private String certificatesType;

    @Schema(description = "certificatesNo")
    @TableField("certificates_no")
    private String certificatesNo;

    @Schema(description = "certificatesUrl")
    @TableField("certificates_url")
    private String certificatesUrl;

}
