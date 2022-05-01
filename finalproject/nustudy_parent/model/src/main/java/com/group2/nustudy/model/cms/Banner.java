package com.group2.nustudy.model.cms;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Banner of main page
 */
@Data
@ApiModel(description = "Banner")
@TableName("banner")
public class Banner extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "title")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "image Url")
	@TableField("image_url")
	private String imageUrl;

	@ApiModelProperty(value = "link Url")
	@TableField("link_url")
	private String linkUrl;

	@ApiModelProperty(value = "sort")
	@TableField("sort")
	private Integer sort;

}

