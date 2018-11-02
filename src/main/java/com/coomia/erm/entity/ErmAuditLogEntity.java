package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 学生资助审批日志表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public class ErmAuditLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//审批状态
	private Integer status;
	//困难等级
	private String level;
	//
	private Integer adminId;
	//审批时间
	@JSONField(format = "yyyy年MM月dd日  ")
	private Date auditDate;
	//审批备注
	private String auditRemark;
	//
	private Integer fundedId;
	//
	private String creator;
	//
	@JSONField(format = "yyyy年MM月dd日  ")
	private Date createTime;
	//
	private String updator;
	//
	@JSONField(format = "yyyy年MM月dd日  ")
	private Date updateTime;

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
	 * 设置：审批状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：审批状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：困难等级
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 获取：困难等级
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * 设置：
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	/**
	 * 获取：
	 */
	public Integer getAdminId() {
		return adminId;
	}
	/**
	 * 设置：审批时间
	 */
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	/**
	 * 获取：审批时间
	 */
	public Date getAuditDate() {
		return auditDate;
	}
	/**
	 * 设置：审批备注
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	/**
	 * 获取：审批备注
	 */
	public String getAuditRemark() {
		return auditRemark;
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
}
