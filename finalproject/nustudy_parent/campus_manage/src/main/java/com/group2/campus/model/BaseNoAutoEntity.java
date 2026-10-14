package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseNoAutoEntity implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "creation time")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "update time")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "Tombstone (1: deleted, 0: not deleted)")
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @Schema(description = "Other parameters")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();
}
