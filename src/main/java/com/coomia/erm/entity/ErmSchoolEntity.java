package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

import com.coomia.erm.constants.DictConstants;

/**
 * 学校信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public class ErmSchoolEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	//
	private Integer ebId;

	private String type;

	private String typeStr;

	public String getTypeStr() {
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	private String code;

	private String name;
	
	//印章地址，需要由操作员上传
	private String seal;
	//
	private String creator;
	//
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;

	public ErmSchoolEntity(Integer id, String type, String code, String name) {
		this.id = id;
		this.type = type;
		this.code = code;
		this.name = name;
	}

	public ErmSchoolEntity() {

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
		this.typeStr = DictConstants.SCHOOL_TYPE_MAP.get(Integer.parseInt(type));
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

	
	
	public String getSeal() {
    return seal;
  }

  public void setSeal(String seal) {
    this.seal = seal;
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
