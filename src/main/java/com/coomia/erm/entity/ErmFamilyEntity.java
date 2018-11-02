package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 家庭信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public class ErmFamilyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Integer id;
	//
	private String name;
	//
	private String address;
	// 联系人
	private String contact;
	// 联系人电话
	private String phone;
	// 家庭困难等级
	private String level;
	// 户主
	private String headman;
	// 家庭描述
	private String describ;
	//
	private String father;
	//
	private String mother;
	//
	private String fatherJob;
	//
	private String motherJob;
	//
	private String fatherAblity;
	//
	private String motherAblity;
	// 家庭其它人劳动能力
	private String otherAblity;
	//
	private String houseInfo;
	//
	private String medicineOutcome;
	//
	private String disasterInfo;
	// 政策性优抚
	private String policyCare;
	// 就学人口
	private Integer studentsCount;
	//
	private String familyChange;
	//
	private String creator;
	//
	private Date createTime;
	//
	private String updator;
	//
	private Date updateTime;
	// 父亲年龄
	private Integer fatherAge;
	// 母亲年龄
	private Integer motherAge;
	// 家庭收入情况
	private String familyIncome;
	// 申报资助理由
	private String applyReason;
	// 证明材料地址，多个逗号隔开
	private String evidenceUrls;
	// 是否建档
	private Integer isArchives;
	// 建档立卡人姓名
	private String archiveName;
	// 建档立卡人身份证
	private String archiveIdcard;
	// 上传人员证明 0:爷爷奶奶，1：爸爸妈妈，2：兄弟姐妹
	private Integer archiveUplode;

	//'湖南省扶贫补贴明白折（建档立卡）人关系'
	private String archiveRelation;
	//'湖南省扶贫补贴明白折（建档立卡）人账号'
	private String archiveAcount;
	//'学生资助卡姓名'
	private String supportName;

	//'学生资助卡银行账号'
	private String supportBankCard;
	//'帮扶人姓名'
	private String helperName;
	// '帮扶人单位'
	private String helperWorkPlace;
			//'帮扶人职位'
	private String helperPosition;
	//'帮扶人联系电话'
	private String helperTel;
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
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取：
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置：联系人
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * 获取：联系人
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * 设置：联系人电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取：联系人电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置：家庭困难等级
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 获取：家庭困难等级
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * 设置：户主
	 */
	public void setHeadman(String headman) {
		this.headman = headman;
	}

	/**
	 * 获取：户主
	 */
	public String getHeadman() {
		return headman;
	}

	/**
	 * 设置：家庭描述
	 */
	public void setDescrib(String describ) {
		this.describ = describ;
	}

	/**
	 * 获取：家庭描述
	 */
	public String getDescrib() {
		return describ;
	}

	/**
	 * 设置：
	 */
	public void setFather(String father) {
		this.father = father;
	}

	/**
	 * 获取：
	 */
	public String getFather() {
		return father;
	}

	/**
	 * 设置：
	 */
	public void setMother(String mother) {
		this.mother = mother;
	}

	/**
	 * 获取：
	 */
	public String getMother() {
		return mother;
	}

	/**
	 * 设置：
	 */
	public void setFatherJob(String fatherJob) {
		this.fatherJob = fatherJob;
	}

	/**
	 * 获取：
	 */
	public String getFatherJob() {
		return fatherJob;
	}

	/**
	 * 设置：
	 */
	public void setMotherJob(String motherJob) {
		this.motherJob = motherJob;
	}

	/**
	 * 获取：
	 */
	public String getMotherJob() {
		return motherJob;
	}

	/**
	 * 设置：
	 */
	public void setFatherAblity(String fatherAblity) {
		this.fatherAblity = fatherAblity;
	}

	/**
	 * 获取：
	 */
	public String getFatherAblity() {
		return fatherAblity;
	}

	/**
	 * 设置：
	 */
	public void setMotherAblity(String motherAblity) {
		this.motherAblity = motherAblity;
	}

	/**
	 * 获取：
	 */
	public String getMotherAblity() {
		return motherAblity;
	}

	/**
	 * 设置：家庭其它人劳动能力
	 */
	public void setOtherAblity(String otherAblity) {
		this.otherAblity = otherAblity;
	}

	/**
	 * 获取：家庭其它人劳动能力
	 */
	public String getOtherAblity() {
		return otherAblity;
	}

	/**
	 * 设置：
	 */
	public void setHouseInfo(String houseInfo) {
		this.houseInfo = houseInfo;
	}

	/**
	 * 获取：
	 */
	public String getHouseInfo() {
		return houseInfo;
	}

	/**
	 * 设置：
	 */
	public void setMedicineOutcome(String medicineOutcome) {
		this.medicineOutcome = medicineOutcome;
	}

	/**
	 * 获取：
	 */
	public String getMedicineOutcome() {
		return medicineOutcome;
	}

	/**
	 * 设置：
	 */
	public void setDisasterInfo(String disasterInfo) {
		this.disasterInfo = disasterInfo;
	}

	/**
	 * 获取：
	 */
	public String getDisasterInfo() {
		return disasterInfo;
	}

	/**
	 * 设置：政策性优抚
	 */
	public void setPolicyCare(String policyCare) {
		this.policyCare = policyCare;
	}

	/**
	 * 获取：政策性优抚
	 */
	public String getPolicyCare() {
		return policyCare;
	}

	/**
	 * 设置：就学人口
	 */
	public void setStudentsCount(Integer studentsCount) {
		this.studentsCount = studentsCount;
	}

	/**
	 * 获取：就学人口
	 */
	public Integer getStudentsCount() {
		return studentsCount;
	}

	/**
	 * 设置：
	 */
	public void setFamilyChange(String familyChange) {
		this.familyChange = familyChange;
	}

	/**
	 * 获取：
	 */
	public String getFamilyChange() {
		return familyChange;
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

	public Integer getFatherAge() {
		return fatherAge;
	}

	public void setFatherAge(Integer fatherAge) {
		this.fatherAge = fatherAge;
	}

	public Integer getMotherAge() {
		return motherAge;
	}

	public void setMotherAge(Integer motherAge) {
		this.motherAge = motherAge;
	}

	public String getFamilyIncome() {
		return familyIncome;
	}

	public void setFamilyIncome(String familyIncome) {
		this.familyIncome = familyIncome;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public String getEvidenceUrls() {
		return evidenceUrls;
	}

	public void setEvidenceUrls(String evidenceUrls) {
		this.evidenceUrls = evidenceUrls;
	}

	public Integer getIsArchives() {
		return isArchives;
	}

	public void setIsArchives(Integer isArchives) {
		this.isArchives = isArchives;
	}

	public String getArchiveName() {
		return archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getArchiveIdcard() {
		return archiveIdcard;
	}

	public void setArchiveIdcard(String archiveIdcard) {
		this.archiveIdcard = archiveIdcard;
	}

	public Integer getArchiveUplode() {
		return archiveUplode;
	}

	public void setArchiveUplode(Integer archiveUplode) {
		this.archiveUplode = archiveUplode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getArchiveRelation() {
		return archiveRelation;
	}

	public void setArchiveRelation(String archiveRelation) {
		this.archiveRelation = archiveRelation;
	}

	public String getArchiveAcount() {
		return archiveAcount;
	}

	public void setArchiveAcount(String archiveAcount) {
		this.archiveAcount = archiveAcount;
	}

	public String getSupportName() {
		return supportName;
	}

	public void setSupportName(String supportName) {
		this.supportName = supportName;
	}

	public String getSupportBankCard() {
		return supportBankCard;
	}

	public void setSupportBankCard(String supportBankCard) {
		this.supportBankCard = supportBankCard;
	}

	public String getHelperName() {
		return helperName;
	}

	public void setHelperName(String helperName) {
		this.helperName = helperName;
	}

	public String getHelperWorkPlace() {
		return helperWorkPlace;
	}

	public void setHelperWorkPlace(String helperWorkPlace) {
		this.helperWorkPlace = helperWorkPlace;
	}

	public String getHelperPosition() {
		return helperPosition;
	}

	public void setHelperPosition(String helperPosition) {
		this.helperPosition = helperPosition;
	}

	public String getHelperTel() {
		return helperTel;
	}

	public void setHelperTel(String helperTel) {
		this.helperTel = helperTel;
	}
}
