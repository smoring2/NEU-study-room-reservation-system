package com.group2.nustudy.vo.cmn;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * <p>
 * Dict
 * </p>
 *
 * @author qy
 */
@Data
public class DictEeVo {

	@ExcelProperty(value = "id" ,index = 0)
	private Long id;

	@ExcelProperty(value = "student_id" ,index = 1)
	private Long parentId;

	@ExcelProperty(value = "name" ,index = 2)
	private String name;

	@ExcelProperty(value = "value" ,index = 3)
	private String value;

	@ExcelProperty(value = "dict_code" ,index = 4)
	private String dictCode;

}

