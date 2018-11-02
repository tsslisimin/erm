package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 短信发送表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-23 21:21:48
 */
public class ErmSmsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//手机号码
	private String telephone;
	//手机验证码
	private String code;
	//状态
	private Integer status;
	//创建时间
	private Date createDate;
	//修改时间
	private Date updateDate;

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
	 * 设置：手机号码
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 获取：手机号码
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 设置：手机验证码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：手机验证码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
}
