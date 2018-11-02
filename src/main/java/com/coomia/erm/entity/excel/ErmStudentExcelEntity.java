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
 * 学生信息
 *
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@ExcelVoConfig
public class ErmStudentExcelEntity extends BaseExcelVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    @Lang(value = "序号")
    private Integer No;
    @Lang(value = "ID")
    private Integer id;
    //
    private Integer familyId;
    //
    private Integer schoolId;
    //
    @Lang(value = "姓名")
    private String name;
    //
    @Lang(value = "性别")
    @InputDicConfig(dicCode = "1") // Excel导入的配置
    @OutputDicConfig(dicCode = "1") // Excel导出的配置
    @DicValidateConfig(dicCode = "1") // 如果要导出下拉框就加这个
    private Integer gender;
    //
    @Lang(value = "生日")
    private String birth;
    // 学号
    @Lang(value = "学号")
    private String stuno;
    // 身份证件类型
    @Lang(value = "身份证件类型")
    private String cardType;
    // 身份证件号
    @Lang(value = "身份证件号")
    private String idcard;
    //
    @Lang(value = "专业简称")
    private String major;
    // 学制
    @Lang(value = "学制")
    @InputDicConfig(dicCode = "14") // Excel导入的配置
    @OutputDicConfig(dicCode = "14") // Excel导出的配置
    @DicValidateConfig(dicCode = "14") // 如果要导出下拉框就加这个
    private String schSystem;
    //
    @Lang(value = "姓名拼音")
    private String pinyin;
    //
    @Lang(value = "英文姓名")
    private String engname;
    // 入学年份
    @Lang(value = "学期")
    private Integer startYear;
    //
    @Lang(value = "年龄")
    private Integer age;
    // 校区ID
    @Lang(value = "校区")
    private String zone;
    // 年级
    @Lang(value = "年级")
    private String grade;
    // 学习形式
    @Lang(value = "学习形式")
    @InputDicConfig(dicCode = "20") // Excel导入的配置
    @OutputDicConfig(dicCode = "20") // Excel导出的配置
    @DicValidateConfig(dicCode = "20") // 如果要导出下拉框就加这个
    private String studyType;
    //
    @Lang(value = "学生类别")
    @InputDicConfig(dicCode = "15") // Excel导入的配置
    @OutputDicConfig(dicCode = "15") // Excel导出的配置
    @DicValidateConfig(dicCode = "15") // 如果要导出下拉框就加这个
    private String stuType;
    // 班级
    @Lang(value = "班级名称")
    private String clazz;
    //
    @Lang("监护人姓名")
    private String parentName;
    @Lang(value = "监护人联系电话")
    private String telphone;
    //
    @Lang(value = "家庭现地址")
    private String address;
    //
    @Lang(value = "户口所在地行政区划码")
    @InputDicConfig(dicCode = "9") // Excel导入的配置
    @OutputDicConfig(dicCode = "9") // Excel导出的配置
    @DicValidateConfig(dicCode = "9") // 如果要导出下拉框就加这个
    private String divisionCode;
    //
    private String residenceType;
    //
    @Lang(value = "民族")
    @InputDicConfig(dicCode = "2") // Excel导入的配置
    @OutputDicConfig(dicCode = "2") // Excel导出的配置
    @DicValidateConfig(dicCode = "2") // 如果要导出下拉框就加这个
    private String nature;
    //
    @Lang(value = "户口所在地区县以下详细地址")
    private String birthPlace;
    //
    @Lang(value = "邮政编码")
    private String cityCode;
    //
    @Lang(value = "婚姻状况")
    @InputDicConfig(dicCode = "16") // Excel导入的配置
    @OutputDicConfig(dicCode = "16") // Excel导出的配置
    @DicValidateConfig(dicCode = "16") // 如果要导出下拉框就加这个
    private String marriageStatus;
    //
    @Lang(value = "健康状况")
    @InputDicConfig(dicCode = "3") // Excel导入的配置
    @OutputDicConfig(dicCode = "3") // Excel导出的配置
    @DicValidateConfig(dicCode = "3") // 如果要导出下拉框就加这个
    private String healthStatus;
    //
    @Lang(value = "政治面貌")
    @InputDicConfig(dicCode = "4") // Excel导入的配置
    @OutputDicConfig(dicCode = "4") // Excel导出的配置
    @DicValidateConfig(dicCode = "4") // 如果要导出下拉框就加这个
    private String politicalStatus;

    // 居住地类型
    @Lang(value = "居住地类型")
    @InputDicConfig(dicCode = "6") // Excel导入的配置
    @OutputDicConfig(dicCode = "6") // Excel导出的配置
    @DicValidateConfig(dicCode = "6") // 如果要导出下拉框就加这个
    private String addresstype;
    // 户口类型
    @Lang(value = "户口性质")
    @InputDicConfig(dicCode = "5") // Excel导入的配置
    @OutputDicConfig(dicCode = "5") // Excel导出的配置
    @DicValidateConfig(dicCode = "5") // 如果要导出下拉框就加这个
    private String accountType;
    // 毕业学校
    @Lang(value = "毕业学校")
    private String schoolName;// 毕业学校
    @Lang(value = "学校名称")
    private String school;
    //
    @Lang(value = "贫困类型")
    @InputDicConfig(dicCode = "8") // Excel导入的配置
    @OutputDicConfig(dicCode = "8") // Excel导出的配置
    @DicValidateConfig(dicCode = "8") // 如果要导出下拉框就加这个
    private String isPoor;

    @Lang(value = "学生来源")
    @InputDicConfig(dicCode = "7") // Excel导入的配置
    @OutputDicConfig(dicCode = "7") // Excel导出的配置
    @DicValidateConfig(dicCode = "7") // 如果要导出下拉框就加这个
    private String studentFrom;
    //
    @Lang(value = "港澳台侨外")
    @InputDicConfig(dicCode = "10") // Excel导入的配置
    @OutputDicConfig(dicCode = "10") // Excel导出的配置
    @DicValidateConfig(dicCode = "10") // 如果要导出下拉框就加这个
    private String nation;
    //
    private Integer isForeign;
    // 就读方式
    @Lang(value = "就读方式")
    @InputDicConfig(dicCode = "12") // Excel导入的配置
    @OutputDicConfig(dicCode = "12") // Excel导出的配置
    @DicValidateConfig(dicCode = "12") // 如果要导出下拉框就加这个
    private String attendingType;
    // 统一招生考试/普通入学
    @Lang(value = "入学方式")
    @InputDicConfig(dicCode = "11") // Excel导入的配置
    @OutputDicConfig(dicCode = "11") // Excel导出的配置
    @DicValidateConfig(dicCode = "11") // 如果要导出下拉框就加这个
    private String learnType;
    // 是否低保
    @Lang(value = "是否低保")
    @InputDicConfig(dicCode = "SFDB") // Excel导入的配置
    @OutputDicConfig(dicCode = "SFDB") // Excel导出的配置
    private Integer lowInsurance;
    // 残疾人
    @Lang(value = "是否残疾")
    @InputDicConfig(dicCode = "SFCJ") // Excel导入的配置
    @OutputDicConfig(dicCode = "SFCJ") // Excel导出的配置
    private Integer handicapped;
    // 学生消费情况

    private String consumeInfo;
    // 默认卡号
    @Lang(value = "默认卡号")
    private String defaultBankcard;
    // 实际卡号
    @Lang(value = "银行卡号")
    private String actualBankcard;
    //
    private String creator;
    //
    private Date createTime;
    //
    private String updator;
    //
    private Date updateTime;

    // 所属派出所
    @Lang(value = "所属派出所")
    private String policeStation;
    // 乘火车区间
    @Lang(value = "乘火车区间")
    private String trainRegion;
    // 招生对象
    @Lang(value = "招生对象")
    @InputDicConfig(dicCode = "17") // Excel导入的配置
    @OutputDicConfig(dicCode = "17") // Excel导出的配置
    @DicValidateConfig(dicCode = "17") // 如果要导出下拉框就加这个
    private String studentObj;
    // 教学点
    @Lang(value = "教学点")
    private String studyPlace;
    // 是否随迁子女
    @Lang(value = "是否随迁子女")
    @InputDicConfig(dicCode = "19") // Excel导入的配置
    @OutputDicConfig(dicCode = "19") // Excel导出的配置
    @DicValidateConfig(dicCode = "19") // 如果要导出下拉框就加这个
    private String isMove;
    // 跨省招生
    @Lang(value = "跨省招生")
    @InputDicConfig(dicCode = "19") // Excel导入的配置
    @OutputDicConfig(dicCode = "19") // Excel导出的配置
    @DicValidateConfig(dicCode = "19") // 如果要导出下拉框就加这个
    private String transProvincial;
    // 联招合作类型
    @Lang(value = "联招合作类型")
    @InputDicConfig(dicCode = "18") // Excel导入的配置
    @OutputDicConfig(dicCode = "18") // Excel导出的配置
    @DicValidateConfig(dicCode = "18") // 如果要导出下拉框就加这个
    private String cooperationType;
    // 分段培养方式
    @Lang(value = "分段培养方式")
    @InputDicConfig(dicCode = "13") // Excel导入的配置
    @OutputDicConfig(dicCode = "13") // Excel导出的配置
    @DicValidateConfig(dicCode = "13") // 如果要导出下拉框就加这个
    private String subTeach;

    // 地址-镇
    @Lang(value = "乡镇")
    private String addressTown;
    // 地址-乡
    @Lang(value = "村（居委会）")
    private String addressTownship;
    // 地址-组
    @Lang(value = "组（号）")
    private String addressGroup;
    @Lang(value = "省")
    private String addressProvince;
    @Lang(value = "市")
    private String addressCity;
    @Lang(value = "县")
    private String addressArea;
    @Lang(value = "是否寄宿")
    private String lodging;

    // 建档立卡人姓名
    @Lang(value = "湖南省扶贫补贴明白折（建档立卡）人姓名")
    private String archiveName;
    // 建档立卡人身份证
    @Lang(value = "湖南省扶贫补贴明白折（建档立卡）人身份证号")
    private String archiveIdcard;
//	// 上传人员证明 0:爷爷奶奶，1：爸爸妈妈，2：兄弟姐妹
//	private Integer archiveUplode;

    //'湖南省扶贫补贴明白折（建档立卡）人关系'
    @Lang(value = "湖南省扶贫补贴明白折（建档立卡）人关系")
    private String archiveRelation;
    //'湖南省扶贫补贴明白折（建档立卡）人账号'
    @Lang(value = "湖南省扶贫补贴明白折（建档立卡）人账号")
    private String archiveAcount;
    //'学生资助卡姓名'
    @Lang(value = "学生资助卡姓名")
    private String supportName;

    //'学生资助卡银行账号'
    @Lang(value = "学生资助卡银行账号")
    private String supportBankCard;
    //帮付人
    @Lang(value = "帮扶人姓名")
    private String helper;
    // '帮扶人单位'
    @Lang(value = "帮扶人单位")
    private String helperWorkPlace;
    //'帮扶人职位'
    @Lang(value = "帮扶人职位")
    private String helperPosition;
    //'帮扶人联系电话'
    @Lang(value = "帮扶人联系电话")
    private String helperTel;

    @Lang(value = "家庭人口数")
    private Integer familyNum;


    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum(Integer familyNum) {
        this.familyNum = familyNum;
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

    /**
     * 设置：
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取：
     */
    public Integer getGender() {
        return gender;
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
    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    /**
     * 获取：入学年份
     */
    public Integer getStartYear() {
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
    public void setIsForeign(Integer isForeign) {
        this.isForeign = isForeign;
    }

    /**
     * 获取：
     */
    public Integer getIsForeign() {
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
    public void setLowInsurance(Integer lowInsurance) {
        this.lowInsurance = lowInsurance;
    }

    /**
     * 获取：是否低保
     */
    public Integer getLowInsurance() {
        return lowInsurance;
    }

    /**
     * 设置：残疾人
     */
    public void setHandicapped(Integer handicapped) {
        this.handicapped = handicapped;
    }

    /**
     * 获取：残疾人
     */
    public Integer getHandicapped() {
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

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getIsPoor() {
        return isPoor;
    }

    public void setIsPoor(String isPoor) {
        this.isPoor = isPoor;
    }

    public String getStudentFrom() {
        return studentFrom;
    }

    public void setStudentFrom(String studentFrom) {
        this.studentFrom = studentFrom;
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

    @Override
    public int getHashVal() {
        return 0;
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

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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
