package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 报表订阅
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-17 00:14:21
 */
public class ErmReportSubEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private Integer reportId;
	//
	private Integer subUser;

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
	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}
	/**
	 * 获取：
	 */
	public Integer getReportId() {
		return reportId;
	}
	/**
	 * 设置：
	 */
	public void setSubUser(Integer subUser) {
		this.subUser = subUser;
	}
	/**
	 * 获取：
	 */
	public Integer getSubUser() {
		return subUser;
	}
}
