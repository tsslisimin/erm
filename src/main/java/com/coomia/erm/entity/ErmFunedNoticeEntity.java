package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 通知信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public class ErmFunedNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	//
	private String title;
	// 通知URL
	private String url;
	//
	private String describ;
	// 通知类型（自建或审核流程通知）
	private String type;
	//
	private String creator;
	//
	@JSONField(format = "yyyy-MM-dd HH:mm")
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;
	// 通知学校id串
	private String schools;

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
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置：通知URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：通知URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置：
	 */
	public void setDescrib(String describ) {
		this.describ = describ;
	}

	/**
	 * 获取：
	 */
	public String getDescrib() {
		return describ;
	}

	/**
	 * 设置：通知类型（自建或审核流程通知）
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取：通知类型（自建或审核流程通知）
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置：
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 获取：
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置：
	 */
	public void setUpdator(String updator) {
		this.updator = updator;
	}

	/**
	 * 获取：
	 */
	public String getUpdator() {
		return updator;
	}

	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	public String getSchools() {
		return schools;
	}

	public void setSchools(String schools) {
		this.schools = schools;
	}

}
