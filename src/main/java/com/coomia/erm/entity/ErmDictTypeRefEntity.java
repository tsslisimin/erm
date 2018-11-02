package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 字典用途关系表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-12 15:53:43
 */
public class ErmDictTypeRefEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String type;
	//
	private Integer dictId;
	//
	private String description;

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
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}
	/**
	 * 获取：
	 */
	public Integer getDictId() {
		return dictId;
	}
	/**
	 * 设置：
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：
	 */
	public String getDescription() {
		return description;
	}
}
