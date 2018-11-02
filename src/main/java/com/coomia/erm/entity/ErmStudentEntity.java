package com.coomia.erm.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 学生信息
 *
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-12 17:33:56
 */

public class ErmStudentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Integer id;
    //
    private Integer familyId;
    //
    private Integer schoolId;
    //
    private String name;
    //
    private Integer gender;
    //
    private String birth;
    // 学号
    private String stuno;
    // 身份证件类型
    private String cardType;
    // 身份证件号
    private String idcard;
    //
    private String major;
    // 学制
    private String schSystem;
    //
    private String pinyin;
    //
    private String engname;
    // 入学年份
    private String startYear;
    //
    private Integer age;
    // 校区ID
    private String zone;
    // 年级
    private String grade;
    // 学习形式
    private String studyType;
    //
    private String stuType;
    // 班级
    private String clazz;
    //
    private String telphone;
    //
    private String address;
    //
    private String divisionCode;
    //
    private String residenceType;
    //
    private String nature;
    //
    private String birthPlace;
    //
    private String cityCode;
    //
    private String marriageStatus;
    //
    private String healthStatus;
    //
    private String politicalStatus;
    //
    private String nation;
    //
    private String isForeign;
    // 就读方式
    private String attendingType;
    // 统一招生考试/普通入学
    private String learnType;
    // 是否低保
    private String lowInsurance;
    // 残疾人
    private String handicapped;
    // 学生消费情况
    private String consumeInfo;
    // 默认卡号
    private String defaultBankcard;
    // 居住地类型
    private String addressType;
    // 户口类型
    private String accountType;
    // 毕业学校
    private String schoolName;
    //自行录入的学校
    private String school;
    //
    private String isPoor;
    // 学生来源
    private String studentFrom;
    // 实际卡号
    private String actualBankcard;
    //
    private String creator;
    //
    @JSONField(format = "yyyy年MM月dd日  ")
    private Date createTime;
    //
    private String updator;

    @JSONField(format = "yyyy年MM月dd日  ")
    private Date updateTime;

    // 学生学校特殊指标信息
    private List<ErmStuValueEntity> stuValues;
    // 学校项目Id
    private Integer schoolFundedId;
    // 所属派出所
    private String policeStation;
    // 乘火车区间
    private String trainRegion;
    // 招生对象
    private String studentObj;
    // 教学点
    private String studyPlace;
    // 是否随迁子女
    private String isMove;
    // 跨省招生
    private String transProvincial;
    // 联招合作类型
    private String cooperationType;
    // 分段培养方式
    private String subTeach;
    // 监护人姓名
    private String parentName;
    // 发放资金
    private Double money;
    // 照片地址
    private String photoUrl;
    // 申请表照片地址
    private String applicationPhotoUrl;
    // 学期
    private String semester;

    private String recruitType;

    private String birthDivisionCode;

    private String registeredDivisionCode;

    // 是否有头像照片， 取决于photoUrl是否为NULL
    private int hasAvatar;
    // 地址-镇
    private String addressTown;
    // 地址-村
    private String addressTownship;
    // 地址-组
    private String addressGroup;

    private String addressProvince;

    private String addressCity;

    private String addressArea;
    // 困难等级
    private Integer diffLevel;
    //困难等级字符串
    private String diffLevelStr;
    // 班主任审批
    private Integer headTeacheCheck = 0;
    // 班主任签名
    private String headTeacheName;


    private Integer familyNum;

    // 有新指标增加时，该字段的值会变化，默认为0，有变化时置为1
    private Integer flag = 0;
    //调整困难等级时的文字, 只记录最后的备注.
    private String note;

    //是否已经毕业 0：在读，1：已毕业
    private Integer isGraduation = 0;
    //是否有申请表
    private String hasApplyInfo;

    @JSONField(format = "yyyy年MM月dd日  ")
    private Date operDate;

    @JSONField(format = "yyyy年MM月dd日  ")
    private Date schDate;

    //是否寄宿
    private String lodging;


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
    //帮付人
    private String helper;
    // '帮扶人单位'
    private String helperWorkPlace;
    //'帮扶人职位'
    private String helperPosition;
    //'帮扶人联系电话'
    private String helperTel;


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public Date getSchDate() {
        return schDate;
    }

    public void setSchDate(Date schDate) {
        this.schDate = schDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    private double score;

    public Integer getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum(Integer familyNum) {
        this.familyNum = familyNum;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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
    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    /**
     * 获取：
     */
    public Integer getFamilyId() {
        return familyId;
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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：
     */
    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 设置：
     */
    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * 获取：
     */
    public String getBirth() {
        return birth;
    }

    /**
     * 设置：学号
     */
    public void setStuno(String stuno) {
        this.stuno = stuno;
    }

    /**
     * 获取：学号
     */
    public String getStuno() {
        return stuno;
    }

    /**
     * 设置：身份证件类型
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取：身份证件类型
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * 设置：身份证件号
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * 获取：身份证件号
     */
    public String getIdcard() {
        return idcard;
    }

    /**
     * 设置：
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * 获取：
     */
    public String getMajor() {
        return major;
    }

    /**
     * 设置：学制
     */
    public void setSchSystem(String schSystem) {
        this.schSystem = schSystem;
    }

    /**
     * 获取：学制
     */
    public String getSchSystem() {
        return schSystem;
    }

    /**
     * 设置：
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * 获取：
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置：
     */
    public void setEngname(String engname) {
        this.engname = engname;
    }

    /**
     * 获取：
     */
    public String getEngname() {
        return engname;
    }

    /**
     * 设置：入学年份
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    /**
     * 获取：入学年份
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * 设置：
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取：
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置：校区ID
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

    /**
     * 获取：校区ID
     */
    public String getZone() {
        return zone;
    }

    /**
     * 设置：年级
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * 获取：年级
     */
    public String getGrade() {
        return grade;
    }

    /**
     * 设置：学习形式
     */
    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    /**
     * 获取：学习形式
     */
    public String getStudyType() {
        return studyType;
    }

    /**
     * 设置：
     */
    public void setStuType(String stuType) {
        this.stuType = stuType;
    }

    /**
     * 获取：
     */
    public String getStuType() {
        return stuType;
    }

    /**
     * 设置：班级
     */
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /**
     * 获取：班级
     */
    public String getClazz() {
        return clazz;
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
     * 设置：
     */
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }

    /**
     * 获取：
     */
    public String getDivisionCode() {
        return divisionCode;
    }

    /**
     * 设置：
     */
    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    /**
     * 获取：
     */
    public String getResidenceType() {
        return residenceType;
    }

    /**
     * 设置：
     */
    public void setNature(String nature) {
        this.nature = nature;
    }

    /**
     * 获取：
     */
    public String getNature() {
        return nature;
    }

    /**
     * 设置：
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * 获取：
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * 设置：
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取：
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置：
     */
    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    /**
     * 获取：
     */
    public String getMarriageStatus() {
        return marriageStatus;
    }

    /**
     * 设置：
     */
    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    /**
     * 获取：
     */
    public String getHealthStatus() {
        return healthStatus;
    }

    /**
     * 设置：
     */
    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    /**
     * 获取：
     */
    public String getPoliticalStatus() {
        return politicalStatus;
    }

    /**
     * 设置：
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 获取：
     */
    public String getNation() {
        return nation;
    }

    /**
     * 设置：
     */
    public void setIsForeign(String isForeign) {
        this.isForeign = isForeign;
    }

    /**
     * 获取：
     */
    public String getIsForeign() {
        return isForeign;
    }

    /**
     * 设置：就读方式
     */
    public void setAttendingType(String attendingType) {
        this.attendingType = attendingType;
    }

    /**
     * 获取：就读方式
     */
    public String getAttendingType() {
        return attendingType;
    }

    /**
     * 设置：统一招生考试/普通入学
     */
    public void setLearnType(String learnType) {
        this.learnType = learnType;
    }

    /**
     * 获取：统一招生考试/普通入学
     */
    public String getLearnType() {
        return learnType;
    }

    /**
     * 设置：是否低保
     */
    public void setLowInsurance(String lowInsurance) {
        this.lowInsurance = lowInsurance;
    }

    /**
     * 获取：是否低保
     */
    public String getLowInsurance() {
        return lowInsurance;
    }

    /**
     * 设置：残疾人
     */
    public void setHandicapped(String handicapped) {
        this.handicapped = handicapped;
    }

    /**
     * 获取：残疾人
     */
    public String getHandicapped() {
        return handicapped;
    }

    /**
     * 设置：学生消费情况
     */
    public void setConsumeInfo(String consumeInfo) {
        this.consumeInfo = consumeInfo;
    }

    /**
     * 获取：学生消费情况
     */
    public String getConsumeInfo() {
        return consumeInfo;
    }

    /**
     * 设置：默认卡号
     */
    public void setDefaultBankcard(String defaultBankcard) {
        this.defaultBankcard = defaultBankcard;
    }

    /**
     * 获取：默认卡号
     */
    public String getDefaultBankcard() {
        return defaultBankcard;
    }

    /**
     * 设置：居住地类型
     */
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    /**
     * 获取：居住地类型
     */
    public String getAddressType() {
        return addressType;
    }

    /**
     * 设置：户口类型
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    /**
     * 获取：户口类型
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * 设置：毕业学校
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * 获取：毕业学校
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置：
     */
    public void setIsPoor(String isPoor) {
        this.isPoor = isPoor;
    }

    /**
     * 获取：
     */
    public String getIsPoor() {
        return isPoor;
    }

    /**
     * 设置：实际卡号
     */
    public void setActualBankcard(String actualBankcard) {
        this.actualBankcard = actualBankcard;
    }

    /**
     * 获取：实际卡号
     */
    public String getActualBankcard() {
        return actualBankcard;
    }

    public String getStudentFrom() {
        return studentFrom;
    }

    public void setStudentFrom(String studentFrom) {
        this.studentFrom = studentFrom;
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

    public List<ErmStuValueEntity> getStuValues() {
        return stuValues;
    }

    public void setStuValues(List<ErmStuValueEntity> stuValues) {
        this.stuValues = stuValues;
    }

    public Integer getSchoolFundedId() {
        return schoolFundedId;
    }

    public void setSchoolFundedId(Integer schoolFundedId) {
        this.schoolFundedId = schoolFundedId;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getTrainRegion() {
        return trainRegion;
    }

    public void setTrainRegion(String trainRegion) {
        this.trainRegion = trainRegion;
    }

    public String getStudentObj() {
        return studentObj;
    }

    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    public String getStudyPlace() {
        return studyPlace;
    }

    public void setStudyPlace(String studyPlace) {
        this.studyPlace = studyPlace;
    }

    public String getIsMove() {
        return isMove;
    }

    public void setIsMove(String isMove) {
        this.isMove = isMove;
    }

    public String getTransProvincial() {
        return transProvincial;
    }

    public void setTransProvincial(String transProvincial) {
        this.transProvincial = transProvincial;
    }

    public String getCooperationType() {
        return cooperationType;
    }

    public void setCooperationType(String cooperationType) {
        this.cooperationType = cooperationType;
    }

    public String getSubTeach() {
        return subTeach;
    }

    public void setSubTeach(String subTeach) {
        this.subTeach = subTeach;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getApplicationPhotoUrl() {
        return applicationPhotoUrl;
    }

    public void setApplicationPhotoUrl(String applicationPhotoUrl) {
        this.applicationPhotoUrl = applicationPhotoUrl;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRecruitType() {
        return recruitType;
    }

    public void setRecruitType(String recruitType) {
        this.recruitType = recruitType;
    }

    public String getBirthDivisionCode() {
        return birthDivisionCode;
    }

    public void setBirthDivisionCode(String birthDivisionCode) {
        this.birthDivisionCode = birthDivisionCode;
    }

    public String getRegisteredDivisionCode() {
        return registeredDivisionCode;
    }

    public void setRegisteredDivisionCode(String registeredDivisionCode) {
        this.registeredDivisionCode = registeredDivisionCode;
    }

    public int getHasAvatar() {
        return hasAvatar = StringUtils.isBlank(this.getPhotoUrl()) ? 0 : 1;
    }

    public void setHasAvatar(int hasAvatar) {
        this.hasAvatar = hasAvatar;
    }

    public String getAddressTown() {
        return addressTown;
    }

    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    public String getAddressTownship() {
        return addressTownship;
    }

    public void setAddressTownship(String addressTownship) {
        this.addressTownship = addressTownship;
    }

    public String getAddressGroup() {
        return addressGroup;
    }

    public void setAddressGroup(String addressGroup) {
        this.addressGroup = addressGroup;
    }

    public Integer getDiffLevel() {
        return diffLevel;
    }

    public void setDiffLevel(Integer diffLevel) {
        this.diffLevel = diffLevel;
    }

    public Integer getHeadTeacheCheck() {
        return headTeacheCheck;
    }

    public void setHeadTeacheCheck(Integer headTeacheCheck) {
        this.headTeacheCheck = headTeacheCheck;
    }

    public String getHeadTeacheName() {
        return headTeacheName;
    }

    public void setHeadTeacheName(String headTeacheName) {
        this.headTeacheName = headTeacheName;
    }

    public Integer getIsGraduation() {
        return isGraduation;
    }

    public void setIsGraduation(Integer isGraduation) {
        this.isGraduation = isGraduation;
    }

    public String getDiffLevelStr() {
        return diffLevelStr;
    }

    public void setDiffLevelStr(String diffLevelStr) {
        this.diffLevelStr = diffLevelStr;
    }

    public String getHasApplyInfo() {
        return hasApplyInfo;
    }

    public void setHasApplyInfo(String hasApplyInfo) {
        this.hasApplyInfo = hasApplyInfo;
    }

    public String getAddressProvince() {
        return addressProvince;
    }

    public void setAddressProvince(String addressProvince) {
        this.addressProvince = addressProvince;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }

    public String getHelper() {
        return helper;
    }

    public void setHelper(String helper) {
        this.helper = helper;
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
