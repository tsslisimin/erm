package com.coomia.erm.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.coomia.erm.dao.*;
import com.coomia.erm.dto.StudentEnterDTO;
import com.coomia.erm.entity.*;
import com.coomia.erm.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.jplus.hyberbin.excel.service.ImportExcelService;
import org.jplus.hyberbin.excel.service.ImportTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.Constants;
import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.ExcelConstants;
import com.coomia.erm.constants.StuDictConstants;
import com.coomia.erm.entity.excel.ErmStudentExcelEntity;
import com.coomia.erm.service.ErmFundProcessService;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.service.ErmStudentService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service("ermStudentService")
@Transactional
public class ErmStudentServiceImpl implements ErmStudentService {
    @Autowired
    private ErmStudentDao ermStudentDao;

    @Autowired
    private ErmStuValueDao ermStuValueDao;

    @Autowired
    private ErmFieldValDao ermFieldValDao;

    @Autowired
    private ErmFundedInfoDao ermFundedInfoDao;

    @Autowired
    private ErmDictDao ermDictDao;

    @Autowired
    private ErmFieldDao ermFieldDao;

    @Autowired
    private ErmFundProcessService ermFundProcessService;

    @Autowired
    private ErmSchoolFundedDao ermSchoolFundedDao;

    @Autowired
    private ErmSchoolDao ermSchoolDao;

    @Autowired
    private ErmFundedInfoService ermFundedInfoService;

    @Autowired
    private ErmFamilyDao ermFamilyDao;

    @Autowired
    private ErmAuditLogDao ermAuditLogDao;

    @Autowired
    private ErmFundedDao ermFundedDao;
    @Autowired
    private TbErmLogDao tbErmLogDao;

    @Override
    public ErmStudentEntity queryObject(Integer id, String type) {
        ErmStudentEntity entity = ermStudentDao.queryObject(id);
        Map<String, String> dictMap = StuDictConstants.STU_DICT_MAP;
        String loging = entity.getLodging();
        if ("update".equals(type)) {
            dictMap = StuDictConstants.DICT_STU_MAP;
        }
        Map<String, Object> stuMap = MapUtils.java2Map(entity);
        Set<String> keys = stuMap.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if ("selectForUpdate".equals(type)) {
                if (key.equals("isPoor"))
                    continue;
            }
            Object value = stuMap.get(key);
            if (value instanceof String && dictMap.containsKey(String.valueOf(value))) {
                stuMap.put(key, dictMap.get(String.valueOf(value)));
            }
        }
        entity = MapUtils.map2Java(entity, stuMap);
        Map<String, Object> params = Maps.newHashMap();
        params.put("stuId", entity.getId());
        params.put("flag", 1);
        List<ErmStuValueEntity> stuVals = this.ermStuValueDao.queryList(params);
        entity.setStuValues(stuVals);
        // 通过身份证获取生日、年龄和性别
        String idCard = entity.getIdcard();
        if (idCard != null && idCard.length() == DictConstants.IDCARD_NUMS) {
            Map<String, Object> cardBaseInfo = CardUtil.getCarInfo(entity.getIdcard());
            entity.setAge((Integer) cardBaseInfo.get("age"));
            entity.setBirth((String) cardBaseInfo.get("birth"));
            entity.setGender((Integer) cardBaseInfo.get("gender"));
        }
        entity.setLodging(loging);
        return entity;
    }

    @Override
    public List<ErmStudentEntity> queryList(Map<String, Object> map) {
        List<ErmStudentEntity> studentList = ermStudentDao.queryList(map);
        List<ErmStudentEntity> result = new ArrayList<ErmStudentEntity>();
        if (studentList != null) {
            for (ErmStudentEntity student : studentList) {
                student.setDiffLevelStr(DictConstants.DIFF_LEVEL.get(student.getDiffLevel()));
                if (student.getFamilyId() != null) {
                    student.setHasApplyInfo("有");
                } else {
                    student.setHasApplyInfo("无");
                }
                Map<String, Object> stuMap = MapUtils.java2Map(student);
                Set<String> keys = stuMap.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    Object value = stuMap.get(key);
                    if (value instanceof String
                            && StuDictConstants.STU_DICT_MAP.containsKey(String.valueOf(value))) {
                        stuMap.put(key, StuDictConstants.STU_DICT_MAP.get(String.valueOf(value)));
                    }
                }
                result.add(MapUtils.map2Java(student, stuMap));
            }
        }
        return result;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return ermStudentDao.queryTotal(map);
    }

    @Override
    public void save(ErmStudentEntity ermStudent, Integer fundedId) {
        boolean isStudentUpdated = false;
        String loging = ermStudent.getLodging();
        ermStudent.setCreateTime(new Date());
        String idCard = ermStudent.getIdcard();
        // 通过身份证获取生日、年龄和性别
        if (idCard != null && idCard.length() == DictConstants.IDCARD_NUMS) {
            Map<String, Object> cardBaseInfo = CardUtil.getCarInfo(ermStudent.getIdcard());
            ermStudent.setAge((Integer) cardBaseInfo.get("age"));
            ermStudent.setBirth((String) cardBaseInfo.get("birth"));
            ermStudent.setGender((Integer) cardBaseInfo.get("gender"));
        }
        // 拆分地址信息
        // operStudentAddress(ermStudent);
        // 拼凑地址信息
        concatAddress(ermStudent);
        List<ErmStuValueEntity> stuVals = ermStudent.getStuValues();
        // 判断该身份证的学生是否存在
        Map<String, Object> stuParam = new HashMap<>();
        stuParam.put("schoolId", ermStudent.getSchoolId());
        stuParam.put("idcard", ermStudent.getIdcard());
        ErmStudentEntity ermEntity = this.ermStudentDao.queryObjectByMap(stuParam);
        // 设置学生的学区
        ErmSchoolEntity school = this.ermSchoolDao.queryObject(ermStudent.getSchoolId());
        ermStudent.setZone(school.getTypeStr());


        if (StringUtils.isNotBlank(ermStudent.getArchiveName())) {
            ermStudent.setIsArchives(1);
        }
        // 贫困学生录入时，当贫困类型不为 ‘其它’时， 直接认定学生为困难生
        if (StringUtils.isNotBlank(ermStudent.getIsPoor())
                && !ermStudent.getIsPoor().equals("n000001571352444")) // n000001571352444
        // 参：StuDictConstants里的值
        {
            ermStudent.setScore(100);
            ermStudent.setDiffLevel(3);
            ermStudent.setHeadTeacheCheck(1);
            ermStudent.setDiffLevelStr(DictConstants.DIFF_LEVEL.get(ermStudent.getDiffLevel()));
        }
        if (ermEntity != null) {
            // 如果存在，更新学生基本信息
            ermStudent.setId(ermEntity.getId());
            ermStudent.setLodging(loging);
            this.ermStudentDao.update(ermStudent);
            isStudentUpdated = true;
        } else {
            stuParam.remove("schoolId");
            stuParam.put("isGraduation", 0);
            ErmStudentEntity graduationStu = this.ermStudentDao.queryObjectByMap(stuParam);
            // 如果存在身份证相同还在校的学生，则先将这个学生设成毕业或者转校
            if (graduationStu != null) {
                graduationStu.setIsGraduation(1);
                ermStudent.setLodging(loging);
                this.ermStudentDao.update(graduationStu);
            }
            // 如果不存在,则插入学生信息
            ermStudent.setLodging(loging);
            ermStudentDao.save(ermStudent);
        }
        // 是否存在自定义指标信息
        if (stuVals != null && stuVals.size() > 0) {
            for (int i = 0; i < stuVals.size(); i++) {
                ErmStuValueEntity stuVal = stuVals.get(i);
                fundedId = stuVal.getSchfundId();
                stuVal.setStuId(ermStudent.getId());

                Set<String> valKeyList = new HashSet<String>();
                if (StringUtils.isNotEmpty(stuVal.getFieldValKey()))
                    valKeyList.add(stuVal.getFieldValKey());
                if (null != stuVal.getFieldValKeyList() && !stuVal.getFieldValKeyList().isEmpty())
                    valKeyList.addAll(stuVal.getFieldValKeyList());
                ErmFieldEntity field = ermFieldDao.queryObject(stuVal.getFieldId());
                for (String valKey : valKeyList) {
                    ErmStuValueEntity containEntity = null;
                    if (field.getType() == 4)
                        containEntity = this.ermStuValueDao.queryObjectByStuValue(ermStudent.getId(),
                                stuVal.getFieldId(), valKey);
                    else
                        containEntity = this.ermStuValueDao.queryObjectByStuIdAndFieldId(ermStudent.getId(),
                                stuVal.getFieldId());
                    stuVal.setFieldValKey(valKey);
                    ErmFieldValEntity fieldVal = ermFieldValDao.queryObjectBykey(valKey);
                    if (fieldVal != null) {
                        stuVal.setFieldValVal(fieldVal.getValVal());
                    }
                    if (containEntity == null) {
                        if (stuVal.getFieldId() == null && fieldVal.getFieldId() != null) {
                            stuVal.setFieldId(fieldVal.getFieldId());
                        }
                        stuVal.setFlag(1);
                        this.ermStuValueDao.save(stuVal);
                    } else {
                        stuVal.setId(containEntity.getId());
                        stuVal.setFlag(1);
                        this.ermStuValueDao.update(stuVal);
                    }
                }
            }
        }
        // 如果updated过， 则再查一次最新的学生信息出来。
        if (isStudentUpdated)
            ermStudent = ermStudentDao.queryObject(ermStudent.getId());
        // 如果是不贫困，重新打下分。
        if (null == ermStudent.getDiffLevel() || ermStudent.getDiffLevel() == 0
                || StringUtils.isBlank(ermStudent.getDiffLevel().toString())) {
            int defaultWei = ermFundProcessService.querySchKPISize(ermStudent.getSchoolId());
            double score = this.ermFundProcessService.score(ermStudent.getId(), defaultWei);
            if (score < 40)
                ermStudent.setDiffLevel(0);
            else if (score >= 40 && score < 60)
                ermStudent.setDiffLevel(1);
            else if (score >= 60 && score < 80)
                ermStudent.setDiffLevel(2);
            else if (score >= 80)
                ermStudent.setDiffLevel(3);
            ermStudent.setScore(score);
            this.ermStudentDao.update(ermStudent);
        }

        // 设置学生的学区
        /**
         * removed on 2018/5/6, see row: 202 ErmSchoolEntity school =
         * this.ermSchoolDao.queryObject(ermStudent.getSchoolId());
         * ermStudent.setZone(school.getTypeStr()); this.ermStudentDao.update(ermStudent);
         **/
        // 如果存在学校项目Id
        if (fundedId != null) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("stuId", ermStudent.getId());
            param.put("schFundId", fundedId);
            // 判断学生是否在该项目上有过申请
            ErmFundedInfoEntity fundedInfo = this.ermFundedInfoDao.queryStuFundByMap(param);
            // 已有申请，但是没上传申请照片，则更新一下照片。
            if (null != fundedInfo && null == fundedInfo.getApplypicurl()) {
                fundedInfo.setApplypicurl(ermStudent.getApplicationPhotoUrl());
                ermFundedInfoDao.update(fundedInfo);
            }
            ErmSchoolFundedEntity schoolFunded = this.ermSchoolFundedDao.queryObject(fundedId);
            // 如果不存在,则插入一条
            if (fundedInfo == null && schoolFunded != null) {
                fundedInfo = new ErmFundedInfoEntity();
                fundedInfo.setCreateTime(new Date());
                fundedInfo.setCreator(ermStudent.getCreator());
                fundedInfo.setFundedId(fundedId);
                fundedInfo.setGlobalStatus(FundStatus.APPLY.getCode());
                fundedInfo.setStuId(ermStudent.getId());
                fundedInfo.setApplypicurl(ermStudent.getApplicationPhotoUrl());
                // 设置每个人的money
                if (schoolFunded.getTotalMoney() != null && schoolFunded.getCount() != 0) {
                    fundedInfo.setMoney(schoolFunded.getTotalMoney() / schoolFunded.getCount());
                }
                // 如果填了学生受助金额，则优先设置这个金额
                if (ermStudent.getMoney() != null) {
                    fundedInfo.setMoney(ermStudent.getMoney());
                }
                this.ermFundedInfoDao.save(fundedInfo);
            }
            // ermFundProcessService.executeAutoMatch(fundedId, ermStudent.getId());
        }
    }

    // 拼凑地址信息
    private void concatAddress(ErmStudentEntity ermStudent) {
        StringBuffer addTmp = new StringBuffer();
        if (StringUtils.isNotBlank(ermStudent.getAddressTown())) {
            addTmp.append(ermStudent.getAddressTown());
        }
        if (StringUtils.isNotBlank(ermStudent.getAddressTownship())) {
            addTmp.append(ermStudent.getAddressTownship());
        }
        if (StringUtils.isNotBlank(ermStudent.getAddressGroup())) {
            addTmp.append(ermStudent.getAddressGroup());
        }
        if (addTmp.length() > 0) {
            ermStudent.setAddress(addTmp.toString());
        }
    }

    @Override
    public void update(ErmStudentEntity ermStudent) {
        // 拆分地址信息
        concatAddress(ermStudent);
        ermStudent.setUpdateTime(new Date());
        List<ErmStuValueEntity> stuVals = ermStudent.getStuValues();
        // 是否存在自定义指标信息，如果存在先删除再新增
        if (stuVals != null && stuVals.size() > 0) {
            this.deleteDiffInfo(ermStudent.getId());
            for (int i = 0; i < stuVals.size(); i++) {
                ErmStuValueEntity stuVal = stuVals.get(i);
                stuVal.setStuId(ermStudent.getId());
                Set<String> valKeyList = new HashSet<String>();
                if (StringUtils.isNotEmpty(stuVal.getFieldValKey()))
                    valKeyList.add(stuVal.getFieldValKey());
                if (null != stuVal.getFieldValKeyList() && !stuVal.getFieldValKeyList().isEmpty())
                    valKeyList.addAll(stuVal.getFieldValKeyList());
                ErmFieldEntity field = ermFieldDao.queryObject(stuVal.getFieldId());
                for (String valKey : valKeyList) {
                    ErmStuValueEntity containEntity = null;
                    if (field.getType() == 4)
                        containEntity = this.ermStuValueDao.queryObjectByStuValue(ermStudent.getId(),
                                stuVal.getFieldId(), valKey);
                    else
                        containEntity = this.ermStuValueDao.queryObjectByStuIdAndFieldId(ermStudent.getId(),
                                stuVal.getFieldId());
                    stuVal.setFieldValKey(valKey);
                    ErmFieldValEntity fieldVal = ermFieldValDao.queryObjectBykey(valKey);
                    if (fieldVal != null) {
                        stuVal.setFieldValVal(fieldVal.getValVal());
                    }
                    if (containEntity == null) {
                        if (stuVal.getFieldId() == null && fieldVal.getFieldId() != null) {
                            stuVal.setFieldId(fieldVal.getFieldId());
                        }
                        stuVal.setFlag(1);
                        this.ermStuValueDao.save(stuVal);
                    } else {
                        stuVal.setId(containEntity.getId());
                        stuVal.setFlag(1);
                        this.ermStuValueDao.update(stuVal);
                    }
                }
            }
        }

        if (StringUtils.isNotBlank(ermStudent.getIsPoor())
                && !ermStudent.getIsPoor().equals("n000001571352444")) // n000001571352444
        // 参：StuDictConstants里的值
        {
            ermStudent.setScore(100);
            ermStudent.setDiffLevel(3);
            ermStudent.setHeadTeacheCheck(1);
            ermStudent.setDiffLevelStr(DictConstants.DIFF_LEVEL.get(ermStudent.getDiffLevel()));
        } else {
            int defaultWei = ermFundProcessService.querySchKPISize(ermStudent.getSchoolId());
            // 打分
            double score = this.ermFundProcessService.score(ermStudent.getId(), defaultWei);
            if (score < 40)
                ermStudent.setDiffLevel(0);
            else if (score >= 40 && score < 60)
                ermStudent.setDiffLevel(1);
            else if (score >= 60 && score < 80)
                ermStudent.setDiffLevel(2);
            else if (score >= 80)
                ermStudent.setDiffLevel(3);
            ermStudent.setScore(score);
        }

        ermStudentDao.update(ermStudent);
    }

    @Override
    public void delete(Integer id) {
        ermStudentDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        ermStudentDao.deleteBatch(ids);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> importStudentInfo(InputStream inputStream, Integer schoolId, Integer userId) {
        Workbook workbook = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(ExcelConstants.STUDENT_SHEET_NAME);
            ImportExcelService service = new ImportExcelService(ErmStudentExcelEntity.class, sheet);
            List<ErmDictEntity> dictList = this.ermDictDao.queryList(null);

            if (dictList != null) {
                for (int i = 0; i < dictList.size(); i++) {
                    ErmDictEntity dict = dictList.get(i);
                    service.addDic(String.valueOf(dict.getType()), dict.getDictCode(), dict.getDictName());
                }
            }

            List<ErmStudentExcelEntity> list = service.doImport();
            int count = 0;
            List<String> failList = Lists.newArrayList();
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ErmStudentExcelEntity excelEntity = list.get(i);
                    ErmStudentEntity studentEntity = new ErmStudentEntity();
                    BeanUtils.copyProperties(studentEntity, excelEntity);
                    studentEntity.setId(null);
                    studentEntity.setSchoolId(schoolId);
                    studentEntity.setSemester(studentEntity.getStartYear());
                    Map<String, Object> stuParam = new HashMap<String, Object>();
                    stuParam.put("schoolId", schoolId);
                    stuParam.put("idcard", studentEntity.getIdcard());
                    stuParam.put("isGraduation", 0);
                    ErmStudentEntity entity = this.ermStudentDao.queryObjectByMap(stuParam);

                    Map<String, Object> checkParam = new HashMap<String, Object>();
                    checkParam.put("name", studentEntity.getName());
                    checkParam.put("idcard", studentEntity.getIdcard());
                    checkParam.put("isGraduation", 0);
                    ErmStudentEntity checkEntity = this.ermStudentDao.queryObjectByMap(checkParam);
                    if (entity != null) {
                        studentEntity.setId(entity.getId());
                        this.update(studentEntity);
                        service.getErrorList().add(excelEntity);
                        failList.add(studentEntity.getName() + "学生数据导入异常,学生数据已存在");

                        TbErmLog tbErmLog = new TbErmLog();
                        tbErmLog.setTitle("学生数据导入异常");
                        tbErmLog.setCreateId(userId);
                        tbErmLog.setMethod("importStudentInfo");
                        tbErmLog.setServiceId("importStudentInfo");
                        tbErmLog.setMsg("第" + (i + 1) + "行" + studentEntity.getName() + "学生数据导入异常,学生数据已存在");
                        tbErmLog.setCreateTime(new Date());
                        tbErmLogDao.insert(tbErmLog);
                        // 如果存在则插入

                    } else if (entity == null && checkEntity != null) {
                        service.getErrorList().add(excelEntity);

                        ErmSchoolEntity ermSchoolEntity = ermSchoolDao.queryObject(checkEntity.getSchoolId());
                        failList.add(studentEntity.getName() + "该学生数据导入异常,学生数据已存在" + ermSchoolEntity.getName());

                        TbErmLog tbErmLog = new TbErmLog();
                        tbErmLog.setTitle("学生数据导入异常");
                        tbErmLog.setCreateId(userId);
                        tbErmLog.setMethod("importStudentInfo");
                        tbErmLog.setServiceId("importStudentInfo");
                        tbErmLog.setMsg("第" + (i + 1) + "行" + studentEntity.getName() + "学生数据导入异常,学生数据已存在" + ermSchoolEntity.getName());
                        tbErmLog.setCreateTime(new Date());
                        tbErmLogDao.insert(tbErmLog);
                    } else {

                        if (!studentEntity.getIsPoor().equals("n000001571352444")) //n000001571352444   参：StuDictConstants里的值
                        {
                            studentEntity.setScore(100);
                            studentEntity.setDiffLevel(3);
                            studentEntity.setHeadTeacheCheck(1);
                            studentEntity.setDiffLevelStr(DictConstants.DIFF_LEVEL.get(studentEntity.getDiffLevel()));
                        }
                        this.save(studentEntity, null);
                        count++;
                    }
                }
            }
            result.put("successNum", count);
            result.put("failNum", service.getErrorList().size());
            result.put("failList", failList);
            System.out.println("成功导入：" + count + "条数据");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.coomia.erm.service.ErmStudentService#queryStudentByStuFundId(int)
     */
    @Override
    public ErmStudentEntity queryStudentByStuFundId(int stuFundId) {
        return ermStudentDao.queryStudentByStuFundId(stuFundId);
    }

    @Override
    public Map<String, Object> importAuditStudentInfo(InputStream inputStream,
                                                      List<ColumnHeader> schoolFieldHeader, Integer fundedId, Integer schoolId, Integer userId) {
        if (schoolFieldHeader == null) {
            return null;
        }
        Workbook workbook = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("Sheet1");
            ImportTableService tableService = new ImportTableService(sheet);
            tableService.setStartRow(2);
            tableService.doImport();
            List<String> titles = new ArrayList<String>();
            // titles.add(0, "actualBankcard");
            // titles.add(0, "money");
            // titles.add(0, "createTime");
            // titles.add(0, "stuno");
            // titles.add(0, "isPoor");
            titles.add(0, "idcard");
            titles.add(0, "name");

            List<String> valTitles = new ArrayList<String>();
            for (int i = 0; i < schoolFieldHeader.size(); i++) {
                ColumnHeader header = schoolFieldHeader.get(i);
                valTitles.add(header.getColCode());
            }
            titles.addAll(valTitles);
            //  titles.add("helper");
            String titlesArr[] = new String[titles.size()];
            titlesArr = titles.toArray(titlesArr);
            List<Map> read = tableService.read(titlesArr, Map.class);
            if (read == null) {
                return null;
            }
            int num = 0;
            int failNum = 0;
            for (int i = 0; i < read.size(); i++) {
                Map<String, Object> entity = read.get(i);
                ErmStudentEntity student = new ErmStudentEntity();
                student.setName((String) entity.get("name"));
                // student.setStuno((String) entity.get("stuno"));
                student.setIdcard((String) entity.get("idcard"));
                //   student.setIsPoor(StuDictConstants.DICT_STU_MAP.get((String) entity.get("isPoor")));
                //  student.setHelper((String) entity.get("helper"));
                // student.setMoney(
                // Double.valueOf((String) entity.get("money") == null ? "0" : (String)
                // entity.get("money")));
                // student.setActualBankcard((String) entity.get("actualBankcard"));
                List<ErmStuValueEntity> stuVals = new ArrayList<ErmStuValueEntity>();
                // int schoolId = 0;

                for (String fieldIdStr : valTitles) {
                    if (entity.containsKey(fieldIdStr)) {
                        Integer fieldId = Integer.parseInt(fieldIdStr);
                        ErmStuValueEntity stuVal = new ErmStuValueEntity();
                        ErmFieldEntity field = this.ermFieldDao.queryObject(fieldId);
                        // schoolId = field.getSchId();
                        String val = (String) entity.get(fieldIdStr);
                        stuVal.setFieldValKey(field.getFieldCode());
                        stuVal.setFieldId(field.getId());
                        stuVal.setFieldValVal(val);
                        stuVal.setSchfundId(fundedId);
                        if (field.getType() == Constants.FIELD_TYPE_SELECT) {
                            // 根据fieldId和下拉值获取下拉key
                            ErmFieldValEntity fieldVal =
                                    this.ermFieldValDao.queryObjectByFieldIdAndVal(fieldId, val);
                            stuVal.setFieldValKey(fieldVal.getValKey());
                        }
                        stuVals.add(stuVal);
                    }
                }
                student.setStuValues(stuVals);
                student.setSchoolId(schoolId);
                if (schoolId != 0 && StringUtils.isNotBlank(student.getName())) {
                    this.save(student, fundedId);
                    num++;
                } else {
                    TbErmLog tbErmLog = new TbErmLog();
                    tbErmLog.setTitle("导入资助学生名单");
                    tbErmLog.setCreateId(userId);
                    tbErmLog.setMethod("importStudentInfo");
                    tbErmLog.setServiceId("importStudentInfo");
                    tbErmLog.setMsg("第" + (i + 1) + "行" + student.getName() + "学生数据导入异常,学生名字不存在");
                    tbErmLog.setCreateTime(new Date());
                    tbErmLogDao.insert(tbErmLog);
                }
            }
            List<String> failList = Lists.newArrayList();
            result.put("successNum", num);
            result.put("failNum", failNum);
            result.put("failList", failList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<ErmStudentEntity> importAuditStudentInfo(InputStream inputStream) {


        Workbook workbook = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {

            List<ErmStudentEntity> list = new ArrayList<>();
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet("学生信息比对");
            ImportTableService tableService = new ImportTableService(sheet);
            tableService.setStartRow(4);
            tableService.doImport();
            List<String> titles = new ArrayList<String>();


            titles.add("name");
            titles.add("idCard");
            String titlesArr[] = new String[titles.size()];
            titlesArr = titles.toArray(titlesArr);
            List<Map> read = tableService.read(titlesArr, Map.class);
            if (read == null) {
                return null;
            }

            for (int i = 0; i < read.size(); i++) {
                Map<String, Object> entity = read.get(i);
                Map<String, Object> checkParam = new HashMap<String, Object>();
                checkParam.put("name", entity.get("name"));
                checkParam.put("idcard", entity.get("idCard"));
                checkParam.put("isGraduation", 0);
                ErmStudentEntity checkEntity = this.ermStudentDao.queryObjectByMap(checkParam);
                if (checkEntity != null) {
                    list.add(checkEntity);
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void save(ErmStudentEntity ermStudent, UserContext user, Integer fundedId) {
        ermStudent.setCreator(user.getUsername());
        ermStudent.setSchoolId(user.getSchoolId());
        this.save(ermStudent, fundedId);
    }

    @Override
    public ErmStudentEntity queryStudentByIdCard(int schId, String name, String idCard) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("schoolId", schId);
        if (null != name)
            map.put("name", name);
        map.put("idcard", idCard);
        return ermStudentDao.queryObjectByMap(map);
    }

    @Override
    public void upgradeStudentInfo() {
        List<ErmStudentEntity> students = this.ermStudentDao.queryList(null);
        if (students == null || students.size() <= 0) {
            return;
        }
        for (ErmStudentEntity student : students) {
            Integer age = student.getAge();
            String grade = student.getGrade();
            int count = 0;
            // 年龄+1
            if (age != null) {
                age = age + 1;
                student.setAge(age);
                count++;
            }
            // 年级
            if (grade != null) {
                Integer schoolId = student.getSchoolId();
                grade = upgradeGrade(grade, schoolId);
                student.setGrade(grade);
                count++;
            }
            if (count > 0) {
                this.ermStudentDao.update(student);
            }
        }
    }

    /**
     * 升级年级
     *
     * @return
     */
    private String upgradeGrade(String grade, Integer schoolId) {
        String[] gradeArr = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] gradeArr2 = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        ErmSchoolEntity school = this.ermSchoolDao.queryObject(schoolId);
        if ("小学".equals(school.getTypeStr()) || "初中".equals(school.getTypeStr())) {
            for (int i = 0; i < gradeArr.length; i++) {
                if (grade.contains(gradeArr[i]) && i < gradeArr.length - 1) {
                    grade = grade.replace(gradeArr[i], gradeArr[i + 1]);
                    break;
                }
                if (grade.contains(gradeArr2[i]) && i < gradeArr.length - 1) {
                    grade = grade.replace(gradeArr2[i], gradeArr2[i + 1]);
                    break;
                }
                if (grade.contains(gradeArr2[i]) && i == gradeArr.length - 1) {
                    grade = "毕业";
                    break;
                }
            }
        }
        if ("普高".equals(school.getTypeStr())) {
            for (int i = 0; i < gradeArr.length; i++) {
                if (grade.contains(gradeArr[i]) && i < 3) {
                    grade = grade.replace(gradeArr[i], gradeArr[i + 1]);
                    break;
                }
                if (grade.contains(gradeArr2[i]) && i < 3) {
                    grade = grade.replace(gradeArr2[i], gradeArr2[i + 1]);
                    break;
                }
            }
        }
        return grade;
    }

    @Override
    public Map<String, Object> queryStudentApplyInfoById(Integer stuId, Integer schFundId,
                                                         Integer fundId) {
        Map<String, Object> applyInfo = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        params.put("studentId", String.valueOf(stuId));
        // 学生基本信息
        ErmStudentEntity student = this.queryObject(stuId, null);
        student.setStuValues(null);
        // 查询困难信息
        List<Map<String, Object>> ermFunedInfo = ermFundedInfoService.fundedStudentById(params);
        // 申请表信息
        ErmFamilyEntity family = new ErmFamilyEntity();
        if (student.getFamilyId() != null) {
            family = this.ermFamilyDao.queryObject(student.getFamilyId());
        }
        // 证明图片
        String proveImgs = null;
        if (family != null) {
            proveImgs = family.getEvidenceUrls() == null ? null : family.getEvidenceUrls();
        }
        // 公示照片
        String publicImg = null;
        // 认定状态列表
        List<String> confirmStatusStr = new ArrayList<String>();
        params.put("stuId", stuId);
        ErmSchoolFundedEntity schFunded = null;
        if (schFundId != null) {
            params.put("schFundId", schFundId);
            schFunded = this.ermSchoolFundedDao.queryObject(schFundId);
        } else {
            if (null != fundId) {
                params.put("schoolId", student.getSchoolId());
                params.put("fundId", fundId);
                schFunded = this.ermSchoolFundedDao.queryObjectByFundId(params);
                params.put("schfundId", schFunded.getId());
            }

        }
        if (schFunded != null) {
            publicImg = schFunded.getPublicImg();
        }
        ErmFundedInfoEntity stuFunded = this.ermFundedInfoDao.queryStuFundByMap(params);
        if (stuFunded != null) {
            params.put("fundedId", stuFunded.getId());
            List<ErmAuditLogEntity> auditLogs = this.ermAuditLogDao.queryList(params);
            if (auditLogs != null && auditLogs.size() > 0) {
                for (ErmAuditLogEntity entity : auditLogs) {
                    confirmStatusStr.add(entity.getAuditRemark());
                    if (entity.getStatus() == FundStatus.OPERAUDITSUCCESS.getCode())
                        student.setOperDate(entity.getAuditDate());
                    else if (entity.getStatus() == FundStatus.SCHAUDITSUCCESS.getCode())
                        student.setSchDate(entity.getAuditDate());
                }
            }
        }
        // 学校公章URL
        String sealUrl = null;
        if (student.getSchoolId() != null) {
            ErmSchoolEntity school = this.ermSchoolDao.queryObject(student.getSchoolId());
            sealUrl = school.getSeal();
        }
        applyInfo.put("baseInfo", student);
        applyInfo.put("fundInfo", ermFunedInfo);
        applyInfo.put("applyInfo", family);
        applyInfo.put("imgUrls", proveImgs == null ? new String() : proveImgs);
        applyInfo.put("confirmStr", confirmStatusStr);
        applyInfo.put("sealUrl", sealUrl);
        applyInfo.put("publicImg", publicImg);
        return applyInfo;
    }

    @Override
    public Map<String, Object> headTeachConfirm(Integer stuId, String headTeachName, Integer isOk) {
        ErmStudentEntity entity = this.ermStudentDao.queryObject(stuId);
        Map<String, Object> result = Maps.newHashMap();
        if (entity == null) {
            result.put("result", false);
            result.put("msg", "认定失败，学生不存在");
            return result;
        }
        // 默认不通过
        if (isOk == null) {
            isOk = 2;
        }
        entity.setHeadTeacheCheck(isOk);
        entity.setHeadTeacheName(headTeachName);
        /**
         * 以下过程和代码是给学生打分并评定他的困难等级．
         */
        // 根据学生ID查询出学生的指标值
        Map<String, Object> p2 = new HashMap<String, Object>();
        p2.put("stuId", stuId);
        // 查询这个学校的项目下的学生KPI信息
        List<Map<String, Object>> stuVals = ermStuValueDao.queryStuVals(p2);
        int defaultWei = ermFundProcessService.querySchKPISize(entity.getSchoolId());
        double score = ermFundProcessService.score(stuVals, defaultWei);
        if (score != 0)
            score = Math.ceil(score);
        entity.setScore(score);
        if (score < 40)
            entity.setDiffLevel(0);
        else if (score >= 40 && score < 60)
            entity.setDiffLevel(1);
        else if (score >= 60 && score < 80)
            entity.setDiffLevel(2);
        else if (score >= 80)
            entity.setDiffLevel(3);
        this.ermStudentDao.update(entity);
        result.put("result", true);
        result.put("msg", "认定成功");
        return result;
    }

    @Override
    public void doSubscrib(Integer schoolId) {
        ermStudentDao.doTagStudentAsNeedReview(schoolId);
    }

    @Override
    public void deleteDiffInfo(Integer stuId) {
        ErmStudentEntity student = this.ermStudentDao.queryObject(stuId);
        if (student != null) {
            student.setDiffLevel(0);
            student.setNote(null);
            this.ermStudentDao.update(student);
            this.ermStuValueDao.updateFlagByStuId(stuId, 0);
        }
    }

    @Override
    public Map<String, Object> compareIdcard(InputStream inputStream, Integer schoolId) {
        Workbook workbook = null;
        Map<String, Object> result = new HashMap<String, Object>();
        Integer schFundId = null;
        try {
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(ExcelConstants.STUDENT_SHEET_NAME);
            ImportExcelService service = new ImportExcelService(ErmStudentExcelEntity.class, sheet);

            List<ErmStudentExcelEntity> list = service.doImport();
            int count = 0;
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ErmStudentExcelEntity excelEntity = list.get(i);
                    ErmStudentEntity studentEntity = new ErmStudentEntity();
                    BeanUtils.copyProperties(studentEntity, excelEntity);
                    studentEntity.setId(null);
                    studentEntity.setSchoolId(schoolId);
                    studentEntity.setSemester(studentEntity.getStartYear());
                    studentEntity.setDefaultBankcard(studentEntity.getActualBankcard());
                    studentEntity.setActualBankcard(null);
                    studentEntity.setHeadTeacheCheck(null);
                    studentEntity.setDiffLevel(null);
                    Map<String, Object> stuParam = new HashMap<String, Object>();
                    stuParam.put("schoolId", schoolId);
                    stuParam.put("idcard", studentEntity.getIdcard());
                    ErmStudentEntity entity = this.ermStudentDao.queryObjectByMap(stuParam);
                    if (entity != null) {
                        // 如果存在则插入
                        studentEntity.setId(entity.getId());
                        this.update(studentEntity);
                        // 判断两个卡号
                        // 比对成功，需要将学生info表的状态变更为offered, log表插入数据
                        // 通过学生ID找所有info表里状态为compare状态的记录，
                        FundStatus currentSucceedStatus = FundStatus.CCSUCCESS;
                        FundStatus currentFailStatus = FundStatus.CCFAIL;
                        FundStatus nextStatus = FundStatus.OFFERRED;
                        FundStatus current = FundStatus.CARDCOMPARE;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("stuId", studentEntity.getId());
                        map.put("globalStatus", current.getCode());
                        List<ErmFundedInfoEntity> stuFunds = ermFundedInfoService.queryList(map);
                        if (!stuFunds.isEmpty()) {
                            // 凡是在cccompare状态的学生/项目都更改状态，并做log表数据的插入。
                            for (ErmFundedInfoEntity info : stuFunds) {
                                // 比对成功的学生
                                boolean flag =
                                        studentEntity.getDefaultBankcard().equals(entity.getActualBankcard());
                                if (flag)
                                    info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                                else
                                    info.setGlobalStatus(currentFailStatus.getCode());// 将学生的流程改为下一环节的流程
                                ermFundedInfoService.update(info);
                                ErmAuditLogEntity toSave = new ErmAuditLogEntity();
                                toSave.setCreator("AI");
                                toSave.setCreateTime(new Date());
                                toSave.setAuditDate(new Date());
                                toSave.setStatus(currentSucceedStatus.getCode());
                                if (flag)
                                    toSave.setAuditRemark(FundStatus.getCNName(currentSucceedStatus.getCode()));
                                else
                                    toSave.setAuditRemark(FundStatus.getCNName(currentFailStatus.getCode()));
                                toSave.setFundedId(info.getId());
                                ermAuditLogDao.save(toSave);
                            }
                            ErmSchoolFundedEntity schFund = new ErmSchoolFundedEntity();
                            schFund.setId(stuFunds.get(0).getFundedId()); // 这里有点偷懒了，按业务来讲，应该是这个ID
                            schFund.setStatus(nextStatus.getCode());
                            ermSchoolFundedDao.update(schFund);
                        }
                        count++;
                    } else {
                        service.getErrorList().add(excelEntity);
                    }
                }
            }

            result.put("successNum", list.size());
            result.put("failNum", service.getErrorList().size());
            result.put("success", true);
            System.out.println("成功导入：" + count + "条数据");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Object> importStudentBankCard(InputStream inputStream, Integer schoolId) {
        Workbook workbook = null;
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(ExcelConstants.STUDENT_SHEET_NAME);
            ImportExcelService service = new ImportExcelService(ErmStudentExcelEntity.class, sheet);
            List<ErmStudentExcelEntity> list = service.doImport();
            List<String> errorList = Lists.newArrayList();
            int count = 0;
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    ErmStudentExcelEntity excelEntity = list.get(i);
                    ErmStudentEntity studentEntity = new ErmStudentEntity();
                    BeanUtils.copyProperties(studentEntity, excelEntity);
                    studentEntity.setId(null);
                    studentEntity.setSchoolId(schoolId);
                    studentEntity.setSemester(studentEntity.getStartYear());
                    studentEntity.setHeadTeacheCheck(null);
                    studentEntity.setDiffLevel(null);
                    Map<String, Object> stuParam = new HashMap<String, Object>();
                    stuParam.put("schoolId", schoolId);
                    stuParam.put("idcard", studentEntity.getIdcard());
                    ErmStudentEntity entity = this.ermStudentDao.queryObjectByMap(stuParam);
                    if (entity != null) {
                        studentEntity.setId(entity.getId());
                        this.update(studentEntity);
                        // TODO 如果存在则插入
                        count++;
                        // 为每个学生加入流程信息，insert log, and update info global status
                        FundStatus currentSucceedStatus = FundStatus.CARDINPUTSUCCESS;
                        FundStatus currentFailStatus = FundStatus.CARDINPUTFAIL;
                        FundStatus nextStatus = FundStatus.CARDCOMPARE;
                        FundStatus current = FundStatus.CARDINPUT;
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("stuId", studentEntity.getId());
                        map.put("globalStatus", current.getCode());
                        List<ErmFundedInfoEntity> stuFunds = ermFundedInfoService.queryList(map);
                        if (!stuFunds.isEmpty()) {
                            // 凡是在CARDINPUT状态的学生/项目都更改状态，并做log表数据的插入。
                            for (ErmFundedInfoEntity info : stuFunds) {
                                // CARDINPUT成功的学生
                                boolean flag = StringUtils.isNotBlank(studentEntity.getActualBankcard());
                                if (flag)
                                    info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                                else
                                    info.setGlobalStatus(currentFailStatus.getCode());// 将学生的流程改为下一环节的流程
                                info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                                ermFundedInfoService.update(info);
                                ErmAuditLogEntity toSave = new ErmAuditLogEntity();
                                toSave.setCreator("AI");
                                toSave.setCreateTime(new Date());
                                toSave.setAuditDate(new Date());
                                toSave.setStatus(currentSucceedStatus.getCode());
                                if (flag)
                                    toSave.setAuditRemark(FundStatus.getCNName(currentSucceedStatus.getCode()));
                                else
                                    toSave.setAuditRemark(FundStatus.getCNName(currentFailStatus.getCode()));
                                toSave.setFundedId(info.getId());
                                ermAuditLogDao.save(toSave);
                            }
                            ErmSchoolFundedEntity schFund = new ErmSchoolFundedEntity();
                            schFund.setId(stuFunds.get(0).getFundedId()); // 这里有点偷懒了，按业务来讲，应该是这个ID
                            schFund.setStatus(nextStatus.getCode());
                            ermSchoolFundedDao.update(schFund);
                        }
                    } else {
                        service.getErrorList().add(excelEntity);
                        errorList
                                .add(excelEntity.getName() + " 银行卡" + excelEntity.getActualBankcard() + "导入异常");
                    }
                }
            }
            result.put("successNum", count);
            result.put("failNum", service.getErrorList().size());
            result.put("failList", errorList);
            result.put("success", true);
            System.out.println("成功导入：" + count + "条数据");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<ErmStudentVo> queryStudentsByParam(Map<String, Object> params) {
        return ermStudentDao.queryStudentsByParam(params);
    }

    @Override
    public List<ErmStudentEntity> queryStudentsBySchoolId(Map<String, Object> params) {
        return ermStudentDao.queryStudentsBySchoolId(params);
    }

    @Override
    public ErmStudentEntity queryObjectByMap(Map<String, Object> map) {
        return ermStudentDao.queryObjectByMap(map);
    }

    @Override
    public List<Map<String, Object>> queryStudentsByFundIdAndSchoolId(Integer fundId, Integer schoolId) {
        return ermStudentDao.queryStudentsByFundIdAndSchoolId(fundId, schoolId);
    }

    @Override
    public List<Map<String, Object>> queryStudentsByFundIdAndSchoolIdAndPage(Map<String, Object> params) {
        return ermStudentDao.queryStudentsByFundIdAndSchoolIdAndPage(params);
    }

    @Override
    public Integer queryStudentsByFundIdAndSchoolIdTotal(Map<String, Object> params) {
        return ermStudentDao.queryStudentsByFundIdAndSchoolIdTotal(params);
    }

    @Override
    public Object downloadItems(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = String.format("慈利县学生资助%d年秋季教育扶贫系统录入情况", Calendar.getInstance().get(Calendar.YEAR));
        String filename = String.format("attachment;filename=%s.xls", title);

        List<Map<String, Object>> data = new ArrayList<>();
        List<StudentEnterDTO> enter = ermStudentDao.sumStudentEnter();
        Map<String, Object> cell;
        for (int i = 0; i < enter.size(); i++) {
            cell = new HashMap<>();
            cell.put("index", i + 1);
            cell.put("school", enter.get(i).getSchool());
            cell.put("count", enter.get(i).getStudents().size());
            data.add(cell);
        }
        ExcelUtil.downLoadExcelForMap(request, response, filename,
                data, new String[]{"index", "school", "count"}, new String[]{"序号", "单位", "已录入数据"}, new boolean[1], title, "录入情况");

        return R.ok().put("fileName", filename);


    }

    @Override
    public Object exportStudentList(Query query, HttpServletRequest req, HttpServletResponse resp) {

        List<ErmStudentEntity> list = queryList(query);
        if (list.isEmpty()) {
            return R.ok();
        }
        String title = String.format("慈利县%d年学生信息", Calendar.getInstance().get(Calendar.YEAR));
        String filename = String.format("attachment;filename=%s.xls", title);

        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> cell = null;
        for (ErmStudentEntity entity : list) {
            cell = new HashMap<>();
            cell.put("name", entity.getName());
            cell.put("code", entity.getStuno());
            cell.put("age", entity.getAge());
            cell.put("gender", Objects.equals(entity.getGender(), 1) ? "男" : "女");
            cell.put("politicalStatus", entity.getPoliticalStatus());
            cell.put("grade", entity.getGrade());
            cell.put("telphone", entity.getTelphone());
            cell.put("nature", entity.getNature());
            cell.put("helper", entity.getHelper());
            cell.put("helperTel", entity.getHelperTel());
            cell.put("helperPosition", entity.getHelperPosition());
            cell.put("helperWorkPlace", entity.getHelperWorkPlace());
            cell.put("healthStatus", entity.getHealthStatus());
            cell.put("isMove", entity.getIsMove());
            cell.put("iscard", entity.getIdcard());
            cell.put("semester", entity.getSemester());
            cell.put("schoolName", entity.getSchoolName());
            cell.put("supportName", entity.getSupportName());
            cell.put("supportBankCard", entity.getSupportBankCard());
            cell.put("address", entity.getAddress());
            cell.put("transProvincial", entity.getTransProvincial());
            cell.put("archiveName", entity.getArchiveName());
            cell.put("familyNum", entity.getFamilyNum());
            cell.put("archiveIdcard", entity.getArchiveIdcard());
            cell.put("archiveAcount", entity.getArchiveAcount());
            cell.put("archiveRelation", entity.getArchiveRelation());

            data.add(cell);
        }
        ExcelUtil.downLoadExcelForMap(req, resp, filename,
                data, new String[]{"name",
                        "code",
                        "age",
                        "gender",
                        "politicalStatus",
                        "grade",
                        "telphone",
                        "nature",
                        "helper",
                        "helperTel",
                        "helperPosition",
                        "helperWorkPlace",
                        "healthStatus",
                        "isMove",
                        "iscard",
                        "semester",
                        "schoolName",
                        "supportName",
                        "supportBankCard",
                        "address",
                        "transProvincial",
                        "archiveName", "familyNum", "archiveIdcard", "archiveAcount", "archiveRelation"}, new String[]{"姓名", "学号", "年龄", "性别", "政治面貌", "年级", "监护人电话", "民族", "帮扶人姓名", "帮扶人电话", "帮扶人职位", "帮扶人单位",
                        "健康状况", "是否随迁子女", "身份证号", "学期", "毕业学校", "学生资助卡姓名", "学生资助卡银行账号", "家庭住址", "跨省招生", "家庭人口", "湖南省扶贫补贴明白折人姓名",
                        "湖南省扶贫补贴明白折人身份证号", "湖南省扶贫补贴明白折人账号", "湖南省扶贫补贴明白折人关系"}, new boolean[1], title, "学生信息");

        return R.ok().put("fileName", filename);

    }
}