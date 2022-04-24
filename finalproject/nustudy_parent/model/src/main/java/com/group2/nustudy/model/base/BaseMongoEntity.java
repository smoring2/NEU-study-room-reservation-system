package com.group2.nustudy.model.base;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseMongoEntity implements Serializable {

    @ApiModelProperty(value = "id")
    @Id
    private String id;

    @ApiModelProperty(value = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "update_time")
    private Date updateTime;

    @ApiModelProperty(value = "is_deleted(1:deleted，0:not deleted)")
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

    @ApiModelProperty(value = "others")
    @Transient //被该注解标注的，将不会被录入到数据库中。只作为普通的javaBean属性
    private Map<String,Object> param = new HashMap<>();
}
