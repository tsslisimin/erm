package com.coomia.erm.entity.excel;

import java.io.Serializable;
import java.util.Date;

import org.jplus.hyberbin.excel.annotation.ExcelVoConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;
import org.jplus.hyberbin.excel.annotation.output.OutputDicConfig;
import org.jplus.hyberbin.excel.annotation.validate.DicValidateConfig;
import org.jplus.hyberbin.excel.bean.BaseExcelVo;

/**
 * 项目资助
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
@ExcelVoConfig
public class ErmSchoolFundedExcelEntityMiddle extends BaseExcelVo  implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private String creator;
	//
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;
	//
	@Lang(value = "ID")
	private Integer id;
	//
	@InputDicConfig(dicCode = "XMID") // Excel导入的配置
	@OutputDicConfig(dicCode = "XMID") // Excel导出的配置
	@DicValidateConfig(dicCode = "XMID") // 如果要导出下拉框就加这个
	@Lang(value = "项目") // Excel导出的配置
	private Integer fundedId;
	//
	@InputDicConfig(dicCode = "XXID") // Excel导入的配置
	@OutputDicConfig(dicCode = "XXID") // Excel导出的配置
	@DicValidateConfig(dicCode = "XXID") // 如果要导出下拉框就加这个
	@Lang(value = "学校") // Excel导出的配置
	private Integer schoolId;
	@InputDicConfig(dicCode = "XXLX") // Excel导入的配置
	@OutputDicConfig(dicCode = "XXLX") // Excel导出的配置
	@DicValidateConfig(dicCode = "XXLX") // 如果要导出下拉框就加这个
	@Lang(value = "学校类型") // Excel导出的配置
	private String schzone;
	// 资助名额个数
	@Lang(value = "分配名额") // Excel导出的配置
	private Integer count;
	// 项目|资助年份
	private Integer year;
	// 学期
	private String semester;
	//
	private Integer status;
	//
	private Date startdate;
	//
	private Date enddate;
	
	@Lang(value = "资助总金额") // Excel导出的配置
	private Double totalMoney;

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
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * 获取：
	 */
	public Integer getSchoolId() {
		return schoolId;
	}

	/**
	 * 设置：资助名额个数
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 获取：资助名额个数
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * 设置：项目|资助年份
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * 获取：项目|资助年份
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * 设置：学期
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * 获取：学期
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置：
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * 获取：
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * 设置：
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
	 * 获取：
	 */
	public Date getEnddate() {
		return enddate;
	}

	@Override
	public int getHashVal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getSchzone() {
		return schzone;
	}

	public void setSchzone(String schzone) {
		this.schzone = schzone;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

}
