package com.coomia.erm.entity.excel;

import java.io.Serializable;
import java.util.Date;

import org.jplus.hyberbin.excel.annotation.ExcelVoConfig;
import org.jplus.hyberbin.excel.annotation.Lang;
import org.jplus.hyberbin.excel.bean.BaseExcelVo;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.SystemConstants;

/**
 * 管理员信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
@ExcelVoConfig
public class ErmAdminExcelEntity extends BaseExcelVo  implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	@Lang(value = "序号")
	private Integer No;
	@Lang(value = "ID")
	private Integer id;
	//
	private String telphone;
	//
	private String name;
	//
	@Lang(value = "账号")
	private String username;
	//
	@Lang(value = "密码")
	private String password;
	//
	private Integer disabled;
	// 管理员类型
	private Integer roleId;
	//账号类型
	@Lang(value = "账号类型")
	private String roleName;
	//
	private String email;
	//
	private Integer schoolId;
	
	private Integer ebId = SystemConstants.ebId;
	//
	private String creator;
	//
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;
	@Lang(value = "学校名称")
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
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	/**
	 * 获取：
	 */
	public String getTelphone() {
		return telphone;
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
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置：
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置：
	 */
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	/**
	 * 获取：
	 */
	public Integer getDisabled() {
		return disabled;
	}


	public Integer getNo() {
		return No;
	}

	public void setNo(Integer no) {
		No = no;
	}

	/**
	 * 设置：管理员类型
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
		this.roleName = DictConstants.ROLE_MAP.get(roleId);
	}

	/**
	 * 获取：管理员类型
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * 设置：
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：
	 */
	public String getEmail() {
		return email;
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

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

  /**
   * @return the ebId
   */
  public Integer getEbId() {
    return ebId;
  }

  /**
   * @param ebId the ebId to set
   */
  public void setEbId(Integer ebId) {
    this.ebId = ebId;
  }

	@Override
	public int getHashVal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}
