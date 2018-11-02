package com.coomia.erm.entity;

import java.io.Serializable;

/**
 * 申请表对象
 * 
 * @author Administrator
 *
 */
public class ErmApplyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3122830490944853713L;

	private String name;

	private Integer gender;

	private Integer age;

	private Integer schoolId;

	private String birthDay;
	// 民族
	private String nation;
	// 学号
	private String stuno;
	// 年级
	private String grade;
	// 班级
	private String clazz;
	// 身份证件号
	private String idcard;
	// 家庭住址
	private String address;
	// 是否建档
	private Integer isArchives;
	// 建档立卡人姓名
	private String archiveName;
	// 建档立卡人身份证
	private String archiveIdcard;
	// 上传人员证明 0:爷爷奶奶，1：爸爸妈妈，2：兄弟姐妹
	private Integer archiveUplode;
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


//	// 是否建档
//	private Integer isArchives;
//	// 建档立卡人姓名
//	private String archiveName;
//	// 建档立卡人身份证
//	private String archiveIdcard;
//	// 上传人员证明 0:爷爷奶奶，1：爸爸妈妈，2：兄弟姐妹
//	private Integer archiveUplode;

	//'湖南省扶贫补贴明白折（建档立卡）人关系'
	private String archiveRelation;
	//'湖南省扶贫补贴明白折（建档立卡）人账号'
	private String archiveAcount;
	//'学生资助卡姓名'
	private String supportName;

	//'学生资助卡银行账号'
	private String supportBankCard;
	//帮付人
	private String helper;
	// '帮扶人单位'
	private String helperWorkPlace;
	//'帮扶人职位'
	private String helperPosition;
	//'帮扶人联系电话'
	private String helperTel;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getStuno() {
		return stuno;
	}

	public void setStuno(String stuno) {
		this.stuno = stuno;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getIsArchives() {
		return isArchives;
	}

	public void setIsArchives(Integer isArchives) {
		this.isArchives = isArchives;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getMother() {
		return mother;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}

	public String getFatherJob() {
		return fatherJob;
	}

	public void setFatherJob(String fatherJob) {
		this.fatherJob = fatherJob;
	}

	public String getMotherJob() {
		return motherJob;
	}

	public void setMotherJob(String motherJob) {
		this.motherJob = motherJob;
	}

	public String getFatherAblity() {
		return fatherAblity;
	}

	public void setFatherAblity(String fatherAblity) {
		this.fatherAblity = fatherAblity;
	}

	public String getMotherAblity() {
		return motherAblity;
	}

	public void setMotherAblity(String motherAblity) {
		this.motherAblity = motherAblity;
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

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
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

	public String getHelper() {
		return helper;
	}

	public void setHelper(String helper) {
		this.helper = helper;
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
