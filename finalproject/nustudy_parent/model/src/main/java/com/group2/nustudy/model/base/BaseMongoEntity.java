package com.group2.nustudy.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseMongoEntity implements Serializable {

    @Schema(description = "id")
    @Id
    private String id;

    @Schema(description = "create_time")
    private Date createTime;

    @Schema(description = "update_time")
    private Date updateTime;

    @Schema(description = "is_deleted(1:deleted，0:not deleted)")
    private Integer isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Schema(description = "others")
    @Transient
    private Map<String,Object> param = new HashMap<>();

    public Date getCreateTime() {
        return createTime;
    }
}
