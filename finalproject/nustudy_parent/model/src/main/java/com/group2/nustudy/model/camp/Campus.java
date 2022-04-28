package com.group2.nustudy.model.camp;

import com.alibaba.fastjson.JSONObject;
import com.group2.nustudy.model.base.BaseMongoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ApiModel(description = "Campus")
@Document("Campus")
public class Campus extends BaseMongoEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "campus code")
	@Indexed(unique = true)
	private String campuscode;

	@ApiModelProperty(value = "campus name")
	@Indexed
	private String campusname;

	@ApiModelProperty(value = "campus type")
	private String campustype;

	@ApiModelProperty(value = "province Code")
	private String provinceCode;

	@ApiModelProperty(value = "city Code")
	private String cityCode;

	@ApiModelProperty(value = "district Code")
	private String districtCode;

	@ApiModelProperty(value = "address")
	private String address;

	@ApiModelProperty(value = "logo Data")
	private String logoData;

	@ApiModelProperty(value = "intro")
	private String intro;

	@ApiModelProperty(value = "route")
	private String route;

	@ApiModelProperty(value = "status 0:offline 1:online")
	private Integer status;

	//预约规则
	@ApiModelProperty(value = "bookingRule")
	private BookingRule bookingRule;


	public String getCampuscode() {
		return campuscode;
	}

	public String getCampusname() {
		return campusname;
	}

	public String getCampustype() {
		return campustype;
	}

	public void setBookingRule(String bookingRule) {
		this.bookingRule = JSONObject.parseObject(bookingRule, BookingRule.class);
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
}

