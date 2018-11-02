package com.coomia.erm.entity.excel;

import java.io.Serializable;
import java.util.Date;

import org.jplus.hyberbin.excel.annotation.ExcelVoConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.annotation.input.InputDicConfig;
import org.jplus.hyberbin.excel.annotation.output.OutputDicConfig;
import org.jplus.hyberbin.excel.annotation.validate.DicValidateConfig;
import org.jplus.hyberbin.excel.bean.BaseExcelVo;

import com.coomia.erm.constants.DictConstants;

/**
 * 学校信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@ExcelVoConfig
public class ErmSchoolExcelEntity extends BaseExcelVo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Lang(value = "序号")
	private Integer No;
	@Lang(value = "ID")
	private Integer id;
	//
	private Integer ebId;

	@InputDicConfig(dicCode = "XXLX") // Excel导入的配置
	@OutputDicConfig(dicCode = "XXLX") // Excel导出的配置
	@DicValidateConfig(dicCode = "XXLX") // 如果要导出下拉框就加这个
	@Lang(value = "学校类型") // Excel导出的配置
	private String type;

	private String typeStr;

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	@Lang(value = "学校code") // Excel导出的配置
	private String code;

	@Lang(value = "学校名称") // Excel导出的配置
	private String name;
	//
	private String creator;
	//
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;


	public Integer getNo() {
		return No;
	}

	public void setNo(Integer no) {
		No = no;
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
	public void setEbId(Integer ebId) {
		this.ebId = ebId;
	}

	/**
	 * 获取：
	 */
	public Integer getEbId() {
		return ebId;
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
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取：
	 */
	public String getCode() {
		return code;
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

	@Override
	public int getHashVal() {
		return 0;
	}
}
