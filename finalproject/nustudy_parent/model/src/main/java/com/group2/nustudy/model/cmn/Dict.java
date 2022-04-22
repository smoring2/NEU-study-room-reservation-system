package com.group2.nustudy.model.cmn;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.group2.nustudy.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// DEBUG TRANSLATE
@Data
@ApiModel(description = "dict data")
@TableName("dict")
public class Dict {
    public Long getId() {
        return id;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "is_deleted(1:deleted，0:not deleted)")
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "others")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();

    @ApiModelProperty(value = "parent_id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty(value = "name")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "value")
    @TableField("value")
    private String value;

    @ApiModelProperty(value = "dict_code")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty(value = "hasChildren")
    @TableField(exist = false)
    private boolean hasChildren;

}