package com.group2.nustudy.model.cmn;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.group2.nustudy.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Dict
 */
@Data
@Schema(description = "dict data")
@TableName("dict")
public class Dict {
    public Long getId() {
        return id;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    private Long id;

    @Schema(description = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;

    @Schema(description = "update_time")
    @TableField("update_time")
    private Date updateTime;

    @Schema(description = "is_deleted(1:deleted，0:not deleted)")
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @Schema(description = "others")
    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();

    @Schema(description = "student_id")
    @TableField("student_id")
    private Long parentId;

    @Schema(description = "name")
    @TableField("name")
    private String name;

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    @Schema(description = "value")
    @TableField("value")
    private String value;

    @Schema(description = "dict_code")
    @TableField("dict_code")
    private String dictCode;

    @Schema(description = "hasChildren")
    @TableField(exist = false)
    private boolean hasChildren;

}