package com.group2.nustudy.model.camp;

import com.alibaba.fastjson2.JSONObject;
import com.group2.nustudy.model.base.BaseMongoEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Schema(description = "Campus")
@Document("Campus")
public class Campus extends BaseMongoEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Schema(description = "campus code")
	@Indexed(unique = true)
	private String campuscode;

	@Schema(description = "campus name")
	@Indexed
	private String campusname;

	@Schema(description = "campus type")
	private String campustype;

	@Schema(description = "state Code")
	private String stateCode;

	@Schema(description = "city Code")
	private String cityCode;

	@Schema(description = "district Code")
	private String districtCode;

	@Schema(description = "address")
	private String address;

	@Schema(description = "logo Data")
	private String logoData;

	@Schema(description = "intro")
	private String intro;

	@Schema(description = "route")
	private String route;

	@Schema(description = "status 0:offline 1:online")
	private Integer status;

	@Schema(description = "bookingRule")
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

	public String getStateCode() {
		return stateCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public String getAddress() {
		return address;
	}

	public String getLogoData() {
		return logoData;
	}

	public String getIntro() {
		return intro;
	}

	public String getRoute() {
		return route;
	}

	public Integer getStatus() {
		return status;
	}

	public BookingRule getBookingRule() {
		return bookingRule;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

