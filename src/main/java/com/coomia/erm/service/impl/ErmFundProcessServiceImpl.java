/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd. All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold. Use or reproduction of this software by any unauthorized
 * individual or entity is strictly prohibited. This software is the confidential and proprietary
 * information of Coomia Network Technology Co., Ltd. Disclosure of such confidential information
 * and shall use it only in accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Coomia Network
 * Technology Co., Ltd. SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.coomia.erm.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmAdjustParam;
import com.coomia.erm.entity.ErmAuditLogEntity;
import com.coomia.erm.entity.ErmFundedInfoEntity;
import com.coomia.erm.entity.ErmQueryObject;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmAuditLogService;
import com.coomia.erm.service.ErmFundProcessService;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.util.PageUtils;

/**
 * @author spancer date: 2017年11月9日 下午4:38:34 <br/>
 */
@Service("ermFundProcessService")
public class ErmFundProcessServiceImpl implements ErmFundProcessService {

    @Autowired
    private ErmFundedInfoService ermFundedInfoService;

    @Autowired
    private ErmSchoolFundedDao ermSchoolFundedDao;
    /**
     * 该DAO里有查询学生审核名单的方法
     */
    @Autowired
    private ErmStuValueDao ermStuValueDao;

    @Autowired
    private ErmAuditLogService ermAuditLogService;

    @Autowired
    private ErmSchFieldDao ermSchFieldDao;

    @Autowired
    private ErmStudentDao ermStudentDao;

    @Autowired
    private ErmFieldValDao ermFieldValDao;

    @Autowired
    private ErmAuditLogDao ermAuditLogDao;


    /**
     * FundStatus.AIMATCH 阶段 这个阶段主要做两件事情， 1. 根据操作员录入的受助人员信息（fund_info表），结合规则给学生自动评分，
     * 流程状态变更为FundStatus.FDOFFER 2. 生成audit log 记录 状态为OPERAUDIT。 executeAutoMatch TODO
     *
     * @param schFunId TODO
     * @param stuId    TODO
     */
    @Override
    public void executeAutoMatch(int schFunId, int stuId, int weightTotal) {
        // 1. 查询这个学生的考核信息
        ErmFundedInfoEntity stuFund = ermFundedInfoService.queryStuFundBySchFund(schFunId, stuId);
        Map<String, Object> param = new HashMap<>();
        param.put("stuId", stuId);
        param.put("schFundId", schFunId);
        List<Map<String, Object>> stuVals = ermStuValueDao.queryStuVals(param);
        ErmFundedInfoEntity toUpdate = new ErmFundedInfoEntity();
        double score = score(stuVals, weightTotal);
        toUpdate.setScore(score);
        toUpdate.setGlobalStatus(FundStatus.OPERAUDIT.getCode());
        toUpdate.setId(stuFund.getId());
        // update fund info
        ermFundedInfoService.update(toUpdate);

        int remain = ermFundedInfoService.queryRemainCountBySchFund(stuFund.getFundedId(),
                FundStatus.APPLY.getCode());
        if (remain == 0) {
            ErmSchoolFundedEntity schFund = ermSchoolFundedDao.queryObject(stuFund.getFundedId());
            schFund.setStatus(FundStatus.OPERAUDIT.getCode());
            schFund.setUpdator("AI");
            schFund.setUpdateTime(new Date());
            ermSchoolFundedDao.update(schFund);
        }

        ErmAuditLogEntity log = new ErmAuditLogEntity();
        log.setAuditDate(new Date());
        log.setCreateTime(new Date());
        log.setCreator("AI");
        log.setFundedId(stuFund.getId());
        log.setStatus(FundStatus.OPERAUDIT.getCode());
        // save log
        ermAuditLogService.save(log);
    }

    /**
     * score TODO
     *
     * @param stuVals
     * @return
     */
    public double score(List<Map<String, Object>> stuVals, int weightTotal) {
        double score = 0;
        for (Map<String, Object> map : stuVals) {
            Object weight = map.get("weight");
            double fieldWeight = 10;
            if (null != weight) {
                fieldWeight = Double.parseDouble(weight.toString());
            }
            Object valWeight = map.get("val_weight");
            if (null == valWeight) {
                Object intVal = map.get("field_val_val");
                if (null != intVal && StringUtils.isNumeric(intVal.toString()))
                    score += Double.parseDouble(intVal.toString()) / fieldWeight;
            } else {
                score += Double.parseDouble(valWeight.toString()) / fieldWeight;
            }
        }
        if (score != 0)
            score = score * 100 / stuVals.size();
        return score;
    }

    @Deprecated
    public PageUtils queryToAuditUsers20180317backup(ErmQueryObject queryObj) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 参考 ermStuValueDao 里的 queryToAuditStudents
        Map<String, Object> p2 = new HashMap<String, Object>();
        p2.put("schFundId", queryObj.getSchFundId());
        // 查询这个学校的项目下的学生KPI信息
        List<Map<String, Object>> stuVals = ermStuValueDao.queryStuVals(p2);
        // 将数据转化成Map<stu_id, Map<field, value>>格式
        Map<String, Map<String, Object>> stuValsToMap = stuValsToMap(stuVals);
        // 这里查询还可以加参数
        Map<String, Object> p3 = new HashMap<String, Object>();
        p3.put("schFundId", queryObj.getSchFundId());
        p3.put("status", queryObj.getStatus());
        p3.put("page", queryObj.getPage());
        p3.put("limit", queryObj.getLimit());
        p3.put("offset", (queryObj.getPage() - 1) * queryObj.getLimit());
        p3.put("stuName", queryObj.getStuName());
        p3.put("idCard", queryObj.getIdCard());
        p3.put("stuno", queryObj.getStuno());
        p3.put("schId", queryObj.getSchId());
        List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryToAuditStudents(p3);
        int total = ermStuValueDao.queryToAuditStudentsCount(p3);
        Map<String, Map<String, Object>> fixedValsToMap = fixedValsToMap(studentsFixedData);
        for (String stuId : fixedValsToMap.keySet()) {
            Map<String, Object> fixed = fixedValsToMap.get(stuId);
            if (!stuValsToMap.isEmpty()) {
                if (null != stuValsToMap.get(stuId))
                    fixed.putAll(stuValsToMap.get(stuId));
            }
            result.add(fixed);
        }
        PageUtils pageUtil = new PageUtils(result, total, queryObj.getLimit(), queryObj.getPage());
        return pageUtil;
    }

    /**
     * SELECT val.stu_id,val.field_id, ff.field_code,ff.field_name,val.field_val_val,ff.weight,
     * fv.val_weight FROM tb_erm_stu_value val LEFT JOIN tb_erm_field ff ON val.field_id=ff.id LEFT
     * JOIN tb_erm_field_val fv ON ff.id=fv.field_id AND val.field_val_key=fv.val_key WHERE val.stu_id
     * =3 AND val.schfund_id =4
     */
    @Override
    public PageUtils queryToAuditUsers(ErmQueryObject queryObj) {
        Map<String, Object> p3 = new HashMap<String, Object>();
        p3.put("fundId", queryObj.getFundId());
        p3.put("level", queryObj.getLevel());
        p3.put("schzone", queryObj.getSchzone());
        p3.put("schFundId", queryObj.getSchFundId());
        p3.put("year", queryObj.getYear());
        p3.put("semester", queryObj.getSemester());
        p3.put("status", queryObj.getStatus());
        p3.put("statusSet", queryObj.getStatusSet());
        p3.put("page", queryObj.getPage());
        p3.put("limit", queryObj.getLimit());
        p3.put("offset", (queryObj.getPage() - 1) * queryObj.getLimit());
        p3.put("stuName", queryObj.getStuName());
        p3.put("idCard", queryObj.getIdCard());
        p3.put("grade", queryObj.getGrade());
        p3.put("clazz", queryObj.getClazz());
        p3.put("stuno", queryObj.getStuno());
        p3.put("schId", queryObj.getSchId());
        p3.put("diffLevel", queryObj.getDiffLevel());
        p3.put("flag", queryObj.getFlag());
        p3.put("isGraduated", queryObj.getIsGraduated());
        p3.put("teacheThecked", queryObj.getTeacheThecked());
        List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryToAuditStudents(p3);
        int total = ermStuValueDao.queryToAuditStudentsCount(p3);

        PageUtils pageUtil =
                new PageUtils(studentsFixedData, total, queryObj.getLimit(), queryObj.getPage());
        return pageUtil;
    }

    // TODO 这里是备份之前的函数。 未改设计时的备份
    @Deprecated
    public PageUtils queryToAuditUsers201801Bakup(ErmQueryObject queryObj) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 参考 ermStuValueDao 里的 queryToAuditStudents
        Map<String, Object> p1 = new HashMap<String, Object>();
        p1.put("schoolId", queryObj.getSchId());
        p1.put("fundId", queryObj.getFundId());
        ErmSchoolFundedEntity schFund = ermSchoolFundedDao.queryObjectByFundId(p1);
        Map<String, Object> p2 = new HashMap<String, Object>();
        p2.put("schFundId", schFund.getId());
        // 查询这个学校的项目下的学生KPI信息
        List<Map<String, Object>> stuVals = ermStuValueDao.queryStuVals(p2);
        // 将数据转化成Map<stu_id, Map<field, value>>格式
        Map<String, Map<String, Object>> stuValsToMap = stuValsToMap(stuVals);
        // 这里查询还可以加参数
        Map<String, Object> p3 = new HashMap<String, Object>();
        p3.put("schFundId", schFund.getId());
        p3.put("status", queryObj.getStatus());
        p3.put("page", queryObj.getPage());
        p3.put("limit", queryObj.getLimit());
        p3.put("offset", (queryObj.getPage() - 1) * queryObj.getLimit());
        p3.put("stuName", queryObj.getStuName());
        p3.put("idCard", queryObj.getIdCard());
        p3.put("stuno", queryObj.getStuno());
        List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryToAuditStudents(p3);
        int total = ermStuValueDao.queryToAuditStudentsCount(p3);
        Map<String, Map<String, Object>> fixedValsToMap = fixedValsToMap(studentsFixedData);
        for (String stuId : fixedValsToMap.keySet()) {
            Map<String, Object> fixed = fixedValsToMap.get(stuId);
            if (!stuValsToMap.isEmpty()) {
                if (null != stuValsToMap.get(stuId))
                    fixed.putAll(stuValsToMap.get(stuId));
            }
            result.add(fixed);
        }
        PageUtils pageUtil = new PageUtils(result, total, queryObj.getLimit(), queryObj.getPage());
        return pageUtil;
    }

    /**
     * fixedValsToMap TODO
     *
     * @param studentsFixedData
     * @return
     */
    private Map<String, Map<String, Object>> fixedValsToMap(
            List<Map<String, Object>> studentsFixedData) {
        Map<String, Map<String, Object>> result = new LinkedHashMap<String, Map<String, Object>>();
        for (Map<String, Object> item : studentsFixedData) {
            result.put(item.get("stu_id").toString(), item);
        }
        return result;
    }

    /**
     * stuValsToMap TODO
     *
     * @param stuVals
     * @return
     */
    private Map<String, Map<String, Object>> stuValsToMap(List<Map<String, Object>> stuVals) {
        Map<String, Map<String, Object>> result = new LinkedHashMap<String, Map<String, Object>>();
        for (Map<String, Object> item : stuVals) {
            Map<String, Object> vMap = result.get(item.get("stu_id").toString());
            if (null == vMap)
                vMap = new HashMap<String, Object>();
            vMap.put(item.get("field_code").toString(), item.get("field_val_val"));
            result.put(item.get("stu_id").toString(), vMap);

        }
        return result;
    }

    @Deprecated
    public List<ColumnHeader> queryToAuditUsersHeaderBackup(Integer schId, Integer fundId) {
        List<ColumnHeader> header = new ArrayList<ColumnHeader>();
        // 配置一些必要的基础字段
        // 参考 ermStuValueDao 里的 queryToAuditStudents
        header.add(new ColumnHeader("id", "排名", true));
        header.add(new ColumnHeader("stuName", "姓名", false));
        header.add(new ColumnHeader("gender", "性别", false));
        header.add(new ColumnHeader("age", "年龄", false));
        header.add(new ColumnHeader("stuno", "学号", false));
        header.add(new ColumnHeader("grade", "年级", false));
        header.add(new ColumnHeader("clazz", "班级", false));
        header.add(new ColumnHeader("zone", "学区", false));
        header.add(new ColumnHeader("diff_level", "困难等级", false));
        header.add(new ColumnHeader("score", "智能得分", true));
        /**
         * header.add(new ColumnHeader("birth", "出生日期", true)); header.add(new ColumnHeader("major",
         * "专业简称", true)); header.add(new ColumnHeader("sch_system", "学制", true)); header.add(new
         * ColumnHeader("start_year", "入学年月", true)); header.add(new ColumnHeader("pinyin", "姓名拼音",
         * true)); header.add(new ColumnHeader("clazz", "班级名称", true)); header.add(new
         * ColumnHeader("stu_type", "学生类别", true)); header.add(new ColumnHeader("study_type", "学习形式",
         * true)); header.add(new ColumnHeader("learn_type", "入学方式", true)); header.add(new
         * ColumnHeader("attending_type", "就读方式", true)); header.add(new ColumnHeader("nation", "港澳台侨外",
         * true)); header.add(new ColumnHeader("nature", "民族", true)); header.add(new
         * ColumnHeader("political_status", "政治面貌", true)); header.add(new ColumnHeader("health_status",
         * "健康状况", true)); header.add(new ColumnHeader("marriage_status", "婚姻状况", true)); header.add(new
         * ColumnHeader("city_code", "籍贯地行政区划码", true)); header.add(new ColumnHeader("birth_place",
         * "出生地行政区划码", true)); header.add(new ColumnHeader("acount_type", "户口性质", true)); header.add(new
         * ColumnHeader("addressType", "学生居住地类型", true)); header.add(new ColumnHeader("division_code",
         * "户口所在地行政区划码", true)); header.add(new ColumnHeader("address", "户口所在地区县以下详细地址", true));
         * header.add(new ColumnHeader("telphone", "联系电话", true)); header.add(new
         * ColumnHeader("student_from", "学生来源", true)); header.add(new ColumnHeader("is_poor",
         * "是否建档立卡贫困户", true));
         *
         **/

        // 配置一些操作员设置的字段

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("schId", schId);
        List<Map<String, Object>> fields = ermSchFieldDao.querySchFields(param);
        for (Map<String, Object> map : fields) {
            header.add(new ColumnHeader(map.get("field_code").toString(),
                    map.get("field_name").toString(), true));
        }
        return header;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.coomia.erm.service.ErmFundProcessService#queryToAuditUsersHeader(int, int, int)
     */
    @Override
    public List<ColumnHeader> queryToAuditUsersHeader(Integer schId, Integer fundId) {
        List<ColumnHeader> header = new ArrayList<ColumnHeader>();
        // 配置一些必要的基础字段
        // 参考 ermStuValueDao 里的 queryToAuditStudents
        header.add(new ColumnHeader("id", "排名", true));
        header.add(new ColumnHeader("stuName", "姓名", false));
        header.add(new ColumnHeader("gender", "性别", false));
        header.add(new ColumnHeader("age", "年龄", false));
        header.add(new ColumnHeader("stuno", "学号", false));
        header.add(new ColumnHeader("school", "学校名称", false));
        header.add(new ColumnHeader("grade", "年级", false));
        header.add(new ColumnHeader("clazz", "班级", false));
        header.add(new ColumnHeader("zone", "学区", false));
        header.add(new ColumnHeader("diffLevelName", "困难等级", false));
        header.add(new ColumnHeader("score", "智能得分", true));
        header.add(new ColumnHeader("offeredStatus", "资助状态", true));
        /**
         * header.add(new ColumnHeader("birth", "出生日期", true)); header.add(new ColumnHeader("major",
         * "专业简称", true)); header.add(new ColumnHeader("sch_system", "学制", true)); header.add(new
         * ColumnHeader("start_year", "入学年月", true)); header.add(new ColumnHeader("pinyin", "姓名拼音",
         * true)); header.add(new ColumnHeader("clazz", "班级名称", true)); header.add(new
         * ColumnHeader("stu_type", "学生类别", true)); header.add(new ColumnHeader("study_type", "学习形式",
         * true)); header.add(new ColumnHeader("learn_type", "入学方式", true)); header.add(new
         * ColumnHeader("attending_type", "就读方式", true)); header.add(new ColumnHeader("nation", "港澳台侨外",
         * true)); header.add(new ColumnHeader("nature", "民族", true)); header.add(new
         * ColumnHeader("political_status", "政治面貌", true)); header.add(new ColumnHeader("health_status",
         * "健康状况", true)); header.add(new ColumnHeader("marriage_status", "婚姻状况", true)); header.add(new
         * ColumnHeader("city_code", "籍贯地行政区划码", true)); header.add(new ColumnHeader("birth_place",
         * "出生地行政区划码", true)); header.add(new ColumnHeader("acount_type", "户口性质", true)); header.add(new
         * ColumnHeader("addressType", "学生居住地类型", true)); header.add(new ColumnHeader("division_code",
         * "户口所在地行政区划码", true)); header.add(new ColumnHeader("address", "户口所在地区县以下详细地址", true));
         * header.add(new ColumnHeader("telphone", "联系电话", true)); header.add(new
         * ColumnHeader("student_from", "学生来源", true)); header.add(new ColumnHeader("is_poor",
         * "是否建档立卡贫困户", true));
         *
         **/

        // 配置一些操作员设置的字段
        /**
         * Map<String, Object> param = new HashMap<String, Object>(); param.put("schId", schId);
         * List<Map<String, Object>> fields = ermSchFieldDao.querySchFields(param); for (Map<String,
         * Object> map : fields) { header.add(new ColumnHeader(map.get("field_code").toString(),
         * map.get("field_name").toString(), true)); }
         **/
        return header;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.coomia.erm.service.ErmFundProcessService#queryAuditableUser(int, int)
     */
    @Override
    public List<Map<String, Object>> queryAuditableUser(int schFundId, int status) {
        Map<String, Object> p3 = new HashMap<String, Object>();
        p3.put("schFundId", schFundId);
        p3.put("status", status);
        List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryToAuditStudents(p3);
        return studentsFixedData;
    }

    /**
     * 查询待拟定的学生名单 TODO 简单描述该方法的实现功能（可选）.
     *
     * @see com.coomia.erm.service.ErmFundProcessService#queryToFundStudents(com.coomia.erm.entity.ErmQueryObject)
     */
    public PageUtils queryToFundStudents(ErmQueryObject queryObj) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        // 参考 ermStuValueDao 里的 queryToAuditStudents
        Map<String, Object> p2 = new HashMap<String, Object>();
        p2.put("schFundId", queryObj.getSchFundId());
        // 查询这个学校的项目下的学生KPI信息
        List<Map<String, Object>> stuVals = ermStuValueDao.queryStuVals(p2);
        // 将数据转化成Map<stu_id, Map<field, value>>格式
        Map<String, Map<String, Object>> stuValsToMap = stuValsToMap(stuVals);
        // 这里查询还可以加参数
        Map<String, Object> p3 = new HashMap<String, Object>();
        p3.put("schFundId", queryObj.getSchFundId());
        p3.put("status", queryObj.getStatus());
        p3.put("page", queryObj.getPage());
        p3.put("limit", queryObj.getLimit());
        p3.put("offset", (queryObj.getPage() - 1) * queryObj.getLimit());
        p3.put("stuName", queryObj.getStuName());
        p3.put("idCard", queryObj.getIdCard());
        p3.put("stuno", queryObj.getStuno());
        p3.put("schId", queryObj.getSchId());
        List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryToAuditStudents(p3);
        int total = ermStuValueDao.queryToAuditStudentsCount(p3);
        Map<String, Map<String, Object>> fixedValsToMap = fixedValsToMap(studentsFixedData);
        for (String stuId : fixedValsToMap.keySet()) {
            Map<String, Object> fixed = fixedValsToMap.get(stuId);
            if (!stuValsToMap.isEmpty()) {
                if (null != stuValsToMap.get(stuId))
                    fixed.putAll(stuValsToMap.get(stuId));
            }
            result.add(fixed);
        }
        PageUtils pageUtil = new PageUtils(result, total, queryObj.getLimit(), queryObj.getPage());
        return pageUtil;
    }

    public int querySchKPISize(int schId) {
        int total = 1;
        List<Map<String, Object>> fieldScores = ermFieldValDao.queryDefaultScore(schId);
        if (fieldScores.size() != 0)
            total = fieldScores.size();
        return total;
    }

    /**
     * queryDefaultWeightTotal:(这里用一句话描述这个方法的作用).
     * TODO(这里描述这个方法适用条件 – 可选).
     * TODO(这里描述这个方法的执行流程 – 可选).
     * TODO(这里描述这个方法的使用方法 – 可选).
     * TODO(这里描述这个方法的注意事项 – 可选).
     *
     * @param schId
     * @return
     * @author spancer
     * @since JDK 1.6
     */
    public int queryDefaultWeightTotal(int schId) {

        int total = 0;
        List<Map<String, Object>> fieldScores = ermFieldValDao.queryDefaultScore(schId);
        for (Map<String, Object> map : fieldScores) {
            total += Double.parseDouble(map.get("fieldScore").toString());
        }
        if (total == 0)
            total = 100;
        return total;
    }

    @Override
    public double score(int stuId, int weightTotal) {

        // 根据学生ID查询出学生的指标值
        Map<String, Object> p2 = new HashMap<String, Object>();
        p2.put("stuId", stuId);
        // 查询这个学校的项目下的学生KPI信息
        List<Map<String, Object>> stuVals = ermStuValueDao.queryStuVals(p2);
        double score = score(stuVals, weightTotal);
        if (score != 0)
            score = Math.ceil(score);
        return score;
    }

    @Override
    public double doScoreAndUpdateStudent(int stuId, int weightTotal) {
        double score = score(stuId, weightTotal);
        ErmStudentEntity t = new ErmStudentEntity();
        t.setId(stuId);
        t.setScore(score);
        if (score < 40)
            t.setDiffLevel(0);
        else if (score >= 40 && score < 60)
            t.setDiffLevel(1);
        else if (score >= 60 && score < 80)
            t.setDiffLevel(2);
        else if (score >= 80)
            t.setDiffLevel(3);
        ermStudentDao.update(t);
        return score;
    }

    @Override
    public boolean delDiffStudent(int stuId) {

        ErmStudentEntity toDel = new ErmStudentEntity();
        toDel.setId(stuId);
        toDel.setScore(0);
        toDel.setDiffLevel(0);
        ermStudentDao.update(toDel);
        return true;
    }

    @Override
    public boolean doTagStudentAsNeedReview(int schId) {
        ermStudentDao.doTagStudentAsNeedReview(schId);
        return true;
    }

    @Override
    public boolean updateDiffStudent(int stuId) {
        // TODO
        return false;
    }

    @Override
    public boolean doAdjustDiffLevel(ErmAdjustParam adjustParam) {
        ermStudentDao.doAdjustBatch(adjustParam);
        return true;
    }

    /**
     * TODO 简单描述该方法的实现功能（可选）.
     *
     * @see com.coomia.erm.service.ErmFundProcessService#doConfirmStudents(int, java.util.List)
     */
    @Override
    public void doAuditStus(int schFundId, List<Integer> stuIds, FundStatus currentStatusSucceed,
                            FundStatus nextStatus, String operator, int remainCount, boolean isPass, String remark) {
        // 保存数据 流程改了后，基本不存在审批不通过的情况了, 除了教育局是可以拒绝的．
        for (Integer stuId : stuIds) {
            ErmFundedInfoEntity info = ermFundedInfoService.queryStuFundBySchFund(schFundId, stuId);
            ErmStudentEntity student = ermStudentDao.queryObject(stuId);
            if (null != info) {
                info.setUpdator(operator);
                info.setScore(student.getScore());
                info.setSuggestion(remark);
                if (isPass)
                    info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                else {
                    // 拒绝的地方，只有教育局有。
                    if (currentStatusSucceed.getCode() == FundStatus.EBAUDITFAIL.getCode() && info.getGlobalStatus() != FundStatus.CLOSE.getCode())
                        info.setGlobalStatus(currentStatusSucceed.getCode());
                }
                ermFundedInfoService.update(info);

            } else {
                info = new ErmFundedInfoEntity();
                info.setCreateTime(new Date());
                info.setCreator(operator);
                info.setFundedId(schFundId);
                info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                info.setStuId(stuId);
                info.setUpdator(operator);
                info.setScore(student.getScore());
                info.setSuggestion(student.getNote());// 学生的这个字段值是会变化的．所以一定要带到这里来，供显示学生详情的时候用．
                ermFundedInfoService.save(info);
            }
            Map<String, Object> pa = new HashMap<>();
            pa.put("stuFundId", info.getId());
            pa.put("status", currentStatusSucceed.getCode());
            int countLog = ermAuditLogDao.queryLog(pa);
            // 往log表插数据
            if (countLog == 0) {
                ErmAuditLogEntity toSave = new ErmAuditLogEntity();
                toSave.setCreator(operator);
                toSave.setCreateTime(new Date());
                toSave.setAuditDate(new Date());
                toSave.setStatus(currentStatusSucceed.getCode());
                if (isPass)
                    toSave.setAuditRemark(FundStatus.getCNName(currentStatusSucceed.getCode()));
                else
                    toSave.setAuditRemark(remark);
                toSave.setFundedId(info.getId());
                ermAuditLogService.save(toSave);
            }


//      if (nextStatus.getCode()==FundStatus.CLOSE.getCode()&&isPass){
//        ErmAuditLogEntity toSave1 = new ErmAuditLogEntity();
//        toSave1.setCreator(operator);
//        toSave1.setCreateTime(new Date());
//        toSave1.setAuditDate(new Date());
//        toSave1.setStatus(FundStatus.SCHANNOUNCEMENTSUCCESS.getCode());
//        toSave1.setAuditRemark(FundStatus.getCNName(FundStatus.SCHANNOUNCEMENTSUCCESS.getCode()));
//        toSave1.setFundedId(info.getId());
//        ermAuditLogService.save(toSave1);
//      }
        }
        // 如果该项目的名额都用完了，那这个项目就流转到下一个环节．
        if (remainCount == 0) {
            ErmSchoolFundedEntity schFund = new ErmSchoolFundedEntity();
            schFund.setId(schFundId);
            schFund.setStatus(nextStatus.getCode());
            ermSchoolFundedDao.update(schFund);
        }
    }

    /**
     * TODO 简单描述该方法的实现功能（可选）.
     *
     * @see com.coomia.erm.service.ErmFundProcessService#doConfirmStudents(int, java.util.List)
     */
    @Override
    public void doEbAuditStus(int schFundId, List<Integer> stuIds, FundStatus currentStatusSucceed,
                              FundStatus nextStatus, String operator, int remainCount, boolean isPass, String remark) {
        // 保存数据 流程改了后，基本不存在审批不通过的情况了, 除了教育局是可以拒绝的．
        for (Integer stuId : stuIds) {
            ErmFundedInfoEntity info = ermFundedInfoService.queryStuFundBySchFund(schFundId, stuId);
            ErmStudentEntity student = ermStudentDao.queryObject(stuId);
            if (null != info) {
                info.setUpdator(operator);
                info.setScore(student.getScore());
                info.setSuggestion(remark);
                if (isPass)
                    info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                else {
                    // 拒绝的地方，只有教育局有。
                    if (currentStatusSucceed.getCode() == FundStatus.EBAUDITFAIL.getCode())
                        info.setGlobalStatus(currentStatusSucceed.getCode());
                }
                ermFundedInfoService.update(info);

            } else {
                info = new ErmFundedInfoEntity();
                info.setCreateTime(new Date());
                info.setCreator(operator);
                info.setFundedId(schFundId);
                info.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
                info.setStuId(stuId);
                info.setUpdator(operator);
                info.setScore(student.getScore());
                info.setSuggestion(student.getNote());// 学生的这个字段值是会变化的．所以一定要带到这里来，供显示学生详情的时候用．
                ermFundedInfoService.save(info);
            }
            // 往log表插数据
            ErmAuditLogEntity toSave = new ErmAuditLogEntity();
            toSave.setCreator(operator);
            toSave.setCreateTime(new Date());
            toSave.setAuditDate(new Date());
            toSave.setStatus(currentStatusSucceed.getCode());
            if (isPass)
                toSave.setAuditRemark(FundStatus.getCNName(currentStatusSucceed.getCode()));
            else
                toSave.setAuditRemark(remark);
            toSave.setFundedId(info.getId());
            ermAuditLogService.save(toSave);
        }
        // 如果该项目的名额都用完了，那这个项目就流转到下一个环节．
        if (remainCount == 0) {
            ErmSchoolFundedEntity schFund = new ErmSchoolFundedEntity();
            schFund.setId(schFundId);
            schFund.setStatus(nextStatus.getCode());
            ermSchoolFundedDao.update(schFund);
        }
//    title: '资助已发放',
//            describ: `${_item.name}的资助款项已发放！`,
//    type: 1,
//            schools: this.schoolName


    }


    @Override
    public PageUtils queryApply(ErmQueryObject ermQueryObject) {
        Map<String, Object> p3 = new HashMap<String, Object>();
        p3.put("fundId", ermQueryObject.getFundId());
        p3.put("level", ermQueryObject.getLevel());
        p3.put("schzone", ermQueryObject.getSchzone());
        p3.put("schFundId", ermQueryObject.getSchFundId());
        p3.put("year", ermQueryObject.getYear());
        p3.put("semester", ermQueryObject.getSemester());
        p3.put("status", ermQueryObject.getStatus());
        p3.put("statusSet", ermQueryObject.getStatusSet());
        p3.put("school", ermQueryObject.getSchool());
        p3.put("page", ermQueryObject.getPage());
        if (ermQueryObject.getLimit() != 0) {
            p3.put("limit", ermQueryObject.getLimit());
            p3.put("offset", (ermQueryObject.getPage() - 1) * ermQueryObject.getLimit());
        }
        p3.put("stuName", ermQueryObject.getStuName());
        p3.put("idCard", ermQueryObject.getIdCard());
        p3.put("grade", ermQueryObject.getGrade());
        p3.put("clazz", ermQueryObject.getClazz());
        p3.put("stuno", ermQueryObject.getStuno());
        p3.put("schId", ermQueryObject.getSchId());
        p3.put("diffLevel", ermQueryObject.getDiffLevel());
        p3.put("flag", ermQueryObject.getFlag());
        p3.put("isGraduated", ermQueryObject.getIsGraduated());
        p3.put("teacheThecked", ermQueryObject.getTeacheThecked());
        p3.put("gender", ermQueryObject.getGender());
        p3.put("minAge", ermQueryObject.getMinAge());
        p3.put("maxAge", ermQueryObject.getMaxAge());
        p3.put("hasApplyImg", ermQueryObject.getHasApplyImg());
        p3.put("isPoor", ermQueryObject.getIsPoor());

        p3.put("address", ermQueryObject.getAddress());
        List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryApply(p3);
        int total = ermStuValueDao.queryApplyCount(p3);

        PageUtils pageUtil = new PageUtils(studentsFixedData, total, ermQueryObject.getLimit(),
                ermQueryObject.getPage());
        return pageUtil;
    }

    @Override
    public void deleteApply(long id) {
        ermFundedInfoService.delete((int) id);

    }
}
