package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 报表信息表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-17 00:14:22
 */
public class ErmReportEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//
	private String reportName;
	//
	private String reportDesc;
	//
	private Integer schId;
	//
	private Integer reportType;
	//报表年份
	private Integer reportYear;
	//
	private Integer creatorId;
	//
	private Integer fundId;
	//
	private Date createDate;
	//
	private String creator;
	
	private ErmSchoolEntity school;
	
	private String schoolName;

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
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	/**
	 * 获取：
	 */
	public String getReportName() {
		return reportName;
	}
	/**
	 * 设置：
	 */
	public void setReportDesc(String reportDesc) {
		this.reportDesc = reportDesc;
	}
	/**
	 * 获取：
	 */
	public String getReportDesc() {
		return reportDesc;
	}
	/**
	 * 设置：
	 */
	public void setSchId(Integer schId) {
		this.schId = schId;
	}
	/**
	 * 获取：
	 */
	public Integer getSchId() {
		return schId;
	}
	/**
	 * 设置：
	 */
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	/**
	 * 获取：
	 */
	public Integer getReportType() {
		return reportType;
	}
	/**
	 * 设置：报表年份
	 */
	public void setReportYear(Integer reportYear) {
		this.reportYear = reportYear;
	}
	/**
	 * 获取：报表年份
	 */
	public Integer getReportYear() {
		return reportYear;
	}
	/**
	 * 设置：
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	/**
	 * 获取：
	 */
	public Integer getCreatorId() {
		return creatorId;
	}
	/**
	 * 设置：
	 */
	public void setFundId(Integer fundId) {
		this.fundId = fundId;
	}
	/**
	 * 获取：
	 */
	public Integer getFundId() {
		return fundId;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
	public Date getCreateDate() {
		return createDate;
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
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public void setSchool(ErmSchoolEntity school) {
		this.school = school;
		this.schoolName = school.getName();
	}
	
	
	
}
