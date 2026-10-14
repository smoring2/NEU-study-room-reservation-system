package com.group2.nustudy.model.cms;

import com.group2.nustudy.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Banner of main page
 */
@Data
@Schema(description = "Banner")
@TableName("banner")
public class Banner extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "title")
	@TableField("title")
	private String title;

	@Schema(description = "image Url")
	@TableField("image_url")
	private String imageUrl;

	@Schema(description = "link Url")
	@TableField("link_url")
	private String linkUrl;

	@Schema(description = "sort")
	@TableField("sort")
	private Integer sort;

}

