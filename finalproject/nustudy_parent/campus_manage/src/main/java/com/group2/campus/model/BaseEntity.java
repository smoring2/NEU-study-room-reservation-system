package com.group2.campus.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "creation time")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update time")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "Tombstone (1: deleted, 0: not deleted)")
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "Other parameters")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();
}
