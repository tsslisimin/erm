package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 指标定义
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
public class ErmFieldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	//
	private String fieldCode;
	//
	private String fieldName;
	// 1文本 2 数字 3 选择器
	private Integer type;
	// 字段权重
	private Double weight;
	// 类型：-1 不显示（逻辑删除用） 0：公共的，且显示的，其它：学校自定义的
	private Integer flag;

	private List<ErmFieldValEntity> fieldVal;
 

	private Object value;

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
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	/**
	 * 获取：
	 */
	public String getFieldCode() {
		return fieldCode;
	}

	/**
	 * 设置：
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 获取：
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置：1文本 2 数字 3 选择器
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 获取：1文本 2 数字 3 选择器
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 设置：字段权重
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * 获取：字段权重
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * 设置：是否分值越大权重越高
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	/**
	 * 获取：是否分值越大权重越高
	 */
	public Integer getFlag() {
		return flag;
	}

	public List<ErmFieldValEntity> getFieldVal() {
		return fieldVal;
	}

	public void setFieldVal(List<ErmFieldValEntity> fieldVal) {
		this.fieldVal = fieldVal;
	}

	 
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
