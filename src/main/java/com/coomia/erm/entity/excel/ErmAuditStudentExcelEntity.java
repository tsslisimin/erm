package com.coomia.erm.entity.excel;

import java.io.Serializable;
import java.util.List;

import org.jplus.hyberbin.excel.annotation.ExcelColumnGroup;
import org.jplus.hyberbin.excel.annotation.ExcelVoConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.bean.BaseExcelVo;

/**
 * 学生信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@ExcelVoConfig
public class ErmAuditStudentExcelEntity extends BaseExcelVo implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	@Lang(value = "ID")
	private Integer id;
	
	@Lang(value = "姓名")
	private String name;
	//
	@Lang(value = "性别")
	private Integer gender;
	// 学号
	@Lang(value = "学号")
	private String stuno;
	// 身份证件号
	@Lang(value = "身份证件号")
	private String idcard;
	
	@Lang(value = "年龄")
	private Integer age;
	
	@ExcelColumnGroup(type = ColumnHeaderExcel.class)
	private List<ColumnHeaderExcel> columnHeaders;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}

	
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * 获取：
	 */
	public Integer getGender() {
		return gender;
	}

	

	/**
	 * 设置：学号
	 */
	public void setStuno(String stuno) {
		this.stuno = stuno;
	}

	/**
	 * 获取：学号
	 */
	public String getStuno() {
		return stuno;
	}

	
	/**
	 * 设置：身份证件号
	 */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	/**
	 * 获取：身份证件号
	 */
	public String getIdcard() {
		return idcard;
	}
	
	
	
	/**
	 * 设置：
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 获取：
	 */
	public Integer getAge() {
		return age;
	}
	
	public List<ColumnHeaderExcel> getColumnHeaders() {
		return columnHeaders;
	}

	public void setColumnHeaders(List<ColumnHeaderExcel> columnHeaders) {
		this.columnHeaders = columnHeaders;
	}

	@Override
	public int getHashVal() {
		return 0;
	}
}
