package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.SystemConstants;

/**
 * 管理员信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
public class ErmAdminEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	//
	private String telphone;
	//
	private String name;
	//
	private String username;
	//
	private String password;
	//
	private Integer disabled;
	// 管理员类型
	private Integer roleId;
	// 角色名称
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

	public void setSchool(ErmSchoolEntity school) {
		this.school = school;
		this.schoolName = school.getName();
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

  /**
   * @return the school
   */
  public ErmSchoolEntity getSchool() {
    return school;
  }

}
