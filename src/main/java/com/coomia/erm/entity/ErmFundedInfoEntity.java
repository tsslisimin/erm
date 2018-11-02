package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生资助信息表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public class ErmFundedInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	//
	private Integer stuId;
	//
	private Integer fundedId;
	// 资助当前状态
	private Integer globalStatus;
	// 资助金额
	private Double money;
	// 发放时间
	private Date fundedTime;
	// 评分
	private Double score;
	//
	private String creator;
	//
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;
	// 校长意见
	private String suggestion;
	// 申请表URL
	private String applypicurl;
	// 困难等级
	private Integer diffLevel;
	//班主任审批
	private Integer headTeacheCheck;

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
	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	/**
	 * 获取：
	 */
	public Integer getStuId() {
		return stuId;
	}

	/**
	 * 设置：
	 */
	public void setFundedId(Integer fundedId) {
		this.fundedId = fundedId;
	}

	/**
	 * 获取：
	 */
	public Integer getFundedId() {
		return fundedId;
	}

	/**
	 * 设置：资助当前状态
	 */
	public void setGlobalStatus(Integer globalStatus) {
		this.globalStatus = globalStatus;
	}

	/**
	 * 获取：资助当前状态
	 */
	public Integer getGlobalStatus() {
		return globalStatus;
	}

	/**
	 * 设置：资助金额
	 */
	public void setMoney(Double money) {
		this.money = money;
	}

	/**
	 * 获取：资助金额
	 */
	public Double getMoney() {
		return money;
	}

	/**
	 * 设置：发放时间
	 */
	public void setFundedTime(Date fundedTime) {
		this.fundedTime = fundedTime;
	}

	/**
	 * 获取：发放时间
	 */
	public Date getFundedTime() {
		return fundedTime;
	}

	/**
	 * 设置：评分
	 */
	public void setScore(Double score) {
		this.score = score;
	}

	/**
	 * 获取：评分
	 */
	public Double getScore() {
		return score;
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

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getApplypicurl() {
		return applypicurl;
	}

	public void setApplypicurl(String applypicurl) {
		this.applypicurl = applypicurl;
	}

	public Integer getDiffLevel() {
		return diffLevel;
	}

	public void setDiffLevel(Integer diffLevel) {
		this.diffLevel = diffLevel;
	}

	public Integer getHeadTeacheCheck() {
		return headTeacheCheck;
	}

	public void setHeadTeacheCheck(Integer headTeacheCheck) {
		this.headTeacheCheck = headTeacheCheck;
	}
	
	
}
