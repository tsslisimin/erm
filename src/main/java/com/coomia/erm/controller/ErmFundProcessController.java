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
package com.coomia.erm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.entity.*;
import com.coomia.erm.service.*;
import com.coomia.erm.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author spancer date: 2017年11月5日 下午9:13:59 <br/>
 */

@Api(value = "Fund Process", description = "资助流程相关的API", position = 5)
@RestController
@RequestMapping("/erm/api/process/fund")
public class ErmFundProcessController {

    private static final String BATCH_AUDIT_SUCCESS = "批量审核-通过";
    private static final String BATCH_AUDIT_FAILURE = "批量审核-不通过";

    // TODO 是否是测试发送短信，如果是，则不会调用发送短信的代码
    private static final boolean IS_TEST_SENDING_MSG = true;
    @Autowired
    private ErmFundedService ermFundedService;

    @Autowired
    private ErmFundedInfoService ermFundedInfoService;

    @Autowired
    private ErmAuditLogService ermAuditLogService;

    @Autowired
    private ErmSchoolFundedService ermSchoolFundedService;

    @Autowired
    private ErmSmsService ermSmsService;

    @Autowired
    private ErmStudentService ermStudentService;

    @Autowired
    private ErmFundProcessService ermFundProcessService;

    @Autowired
    private UserService userService;
    @Autowired
    private ErmDictService ermDictService;

    @Autowired
    private ErmLogService ermLogService;
    @Autowired
    private ErmSchoolService ermSchoolService;
    @Autowired
    private ErmFunedNoticeService ermFunedNoticeService;

    /**
     * 修改
     */
    @ApiOperation(value = "修改学生资助金额信息表", notes = "修改学生资助金额信息表")
    @ApiImplicitParam(name = "ErmFundedInfo", value = "学生资助信息表对象", required = true, dataType = "ErmFundedInfo")
    @RequestMapping(value = "/updateStudentMoney", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER')")
    public R updateStudentMoney(@RequestBody ErmFundedInfoEntity ermFundedInfo, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ErmFundedInfoEntity dbEntity = ermFundedInfoService.queryObject(ermFundedInfo.getId());
        if (dbEntity.getGlobalStatus() == FundStatus.OPERAUDIT.getCode()) {
            ermFundedInfo.setUpdator(user.getUsername());
            ermFundedInfoService.update(ermFundedInfo);
            return R.ok("您的修改已生效！");
        } else
            return R.error("您无权修改金额！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改项目资助金额", notes = "修改项目资助金额")
    @ApiImplicitParam(name = "ErmSchoolFunded", value = "项目资助对象", required = true, dataType = "ErmSchoolFunded")
    @RequestMapping(value = "/updateFundMoney", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER')")
    public R updateFundMoney(@RequestBody ErmSchoolFundedEntity ermSchoolFunded, JwtAuthenticationToken token) {
        ErmSchoolFundedEntity dbEntity = ermSchoolFundedService.queryObject(ermSchoolFunded.getId());
        if (dbEntity.getStatus() == FundStatus.FDCONFIRM.getCode() && null != ermSchoolFunded.getTotalMoney()) {
            UserContext user = (UserContext) token.getPrincipal();
            ermSchoolFunded.setUpdator(user.getUsername());
            ermSchoolFunded.setStatus(FundStatus.APPLY.getCode());
            ermSchoolFundedService.update(ermSchoolFunded);
            return R.ok("资助金额将按资助标准智能分配给各受助人！");
        } else
            return R.error("您无权修改金额！");
    }

    @ApiOperation(value = "启动非国家资助项目", notes = "启动非国家资助项目")
    @ApiImplicitParam(name = "fundid", value = "项目ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/start/{fundid}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB', 'SCH')")
    public R start(@PathVariable("fundid") Integer fundid, JwtAuthenticationToken token) {
        int fundStatus = ermFundedService.getFundStatus(fundid);
        if (fundStatus == FundStatus.NEW.getCode()) {
            boolean flag = ermFundedService.doStartFund(fundid);
            if (flag)
                return R.ok().put("statusName", FundStatus.RUNNING.getName());
            else
                return R.error(200, "启动失败").put("statusName", FundStatus.getCNName(fundStatus));
        } else
            return R.ok("项目已启动").put("statusName", FundStatus.getCNName(fundStatus));

    }

    @ApiOperation(value = "启动国家资助项目", notes = "启动国家资助项目")
    @ApiImplicitParam(name = "ErmFundedEntity", value = "资助项目对象", required = true, dataType = "ErmFundedEntity")
    @RequestMapping(value = "/startCountryFund", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB', 'SCH')")
    public R startCountryFund(@RequestBody ErmFundedEntity ermFundedEntity, JwtAuthenticationToken token) {

        // TODO 需要做2件事， 1. 生成fund表数据（项目ID、季度时间等）
        ErmDictEntity dictNameByName = ermDictService.getDictNameByName(ermFundedEntity.getSemesterName(), DictConstants.SEMESTER_TYPE + "");
        if (dictNameByName != null) {
            ermFundedEntity.setSemester(dictNameByName.getDictCode());
        } else {
            ErmDictEntity springEntity = new ErmDictEntity(DictConstants.SEMESTER_TYPE, BusinessUtil.getCodeUUID(), ermFundedEntity.getSemesterName());
            ermDictService.save(springEntity);
            ermFundedEntity.setSemester(springEntity.getDictCode());
        }
        UserContext user = (UserContext) token.getPrincipal();
        ermFundedEntity.setEbId(user.getEbId());
        ermFundedEntity.setSchoolId(user.getSchoolId());
        ermFundedEntity.setStatus(FundStatus.RUNNING.getCode());
        // 先查询一下， 项目是否存在，不能已经存在了还能插入，已经存在的，就提醒用户
        Map<String, Object> queryObj = new HashMap<String, Object>();
        queryObj.put("type", ermFundedEntity.getType());
        queryObj.put("subtype", ermFundedEntity.getSubtype());

        queryObj.put("semester", ermFundedEntity.getSemester());
        queryObj.put("ebId", ermFundedEntity.getEbId());
        queryObj.put("name", ermFundedEntity.getName());
        ErmFundedEntity dbData = ermFundedService.queryObjectByMap(queryObj);
        if (null != dbData) {
            return R.ok("操作成功，项目已经在运行了!").put("statusName", FundStatus.getCNName(dbData.getStatus()));
        } else {
            boolean flag = ermFundedService.doConfigFund(ermFundedEntity);
            if (flag)
                return R.ok("操作成功").put("statusName", FundStatus.getCNName(ermFundedEntity.getStatus()));
            else
                return R.ok("操作失败，项目启动过程中出了问题!");
        }

    }

    @ApiOperation(value = "启动国家资助项目", notes = "启动国家资助项目")
    @ApiImplicitParam(name = "ErmFundedEntity", value = "资助项目对象", required = true, dataType = "ErmFundedEntity")
    @RequestMapping(value = "/startCountryFundBak", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB', 'SCH')")
    @Deprecated
    public R startCountryFundBak(@RequestBody ErmFundedEntity ermFundedEntity, JwtAuthenticationToken token) {

        // TODO 需要做2件事， 1. 生成fund表数据（项目ID、季度时间等） 2. 将学校与fund的关系数据初始化 3.
        // 将fund的自定义指标初始化。4.改fund状态
        UserContext user = (UserContext) token.getPrincipal();
        ermFundedEntity.setEbId(user.getEbId());
        ermFundedEntity.setSchoolId(user.getSchoolId());
        ermFundedEntity.setStatus(FundStatus.RUNNING.getCode());
        // 先查询一下， 项目是否存在，不能已经存在了还能插入，已经存在的，就提醒用户
        Map<String, Object> queryObj = new HashMap<String, Object>();
        queryObj.put("type", ermFundedEntity.getType());
        queryObj.put("subtype", ermFundedEntity.getSubtype());
        queryObj.put("semester", ermFundedEntity.getSemester());
        queryObj.put("ebId", ermFundedEntity.getEbId());
        queryObj.put("name", ermFundedEntity.getName());
        ErmFundedEntity dbData = ermFundedService.queryObjectByMap(queryObj);
        if (null != dbData) {
            // 存在 fund 不一定存在 sch fund
            ermFundedEntity.setId(dbData.getId());
            ermFundedService.doCheckSchFunds(ermFundedEntity);
            return R.ok("操作成功，项目已经在运行了!").put("statusName", FundStatus.getCNName(dbData.getStatus()));
        } else {
            boolean flag = ermFundedService.doConfigFund(ermFundedEntity);
            if (flag)
                return R.ok("操作成功").put("statusName", FundStatus.getCNName(ermFundedEntity.getStatus()));
            else
                return R.ok("操作失败，项目启动过程中出了问题!");
        }

    }

    @ApiOperation(value = "启动资助项目", notes = "启动资助项目")
    @ApiImplicitParam(name = "id", value = "项目ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/schFundStart/{schFundId}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER')")
    public R schFundStart(@PathVariable("schFundId") Integer schFundId) {
        ErmSchoolFundedEntity schFund = ermSchoolFundedService.queryObject(schFundId);
        if (schFund.getStatus() != FundStatus.CONFIG.getCode())
            return R.error("启动失败, 该项目状态异常！");
        ErmSchoolFundedEntity ermSchoolFunded = new ErmSchoolFundedEntity();
        ermSchoolFunded.setId(schFund.getId());
        ermSchoolFunded.setStatus(FundStatus.FDCONFIRM.getCode());
        ermSchoolFundedService.update(ermSchoolFunded);
        return R.ok().put("statusName", FundStatus.FDCONFIRM.getName());
    }

    @ApiOperation(value = "结束项目", notes = "结束资助项目")
    @ApiImplicitParam(name = "id", value = "项目ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/finish/{schFundId}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB', 'SCH')")
    public R finish(@PathVariable("schFundId") Integer schFundId, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        int remain = ermFundedInfoService.queryRemainCountBySchFund(schFundId, FundStatus.NOTICE.getCode());
        if (remain == 0) {
            ErmSchoolFundedEntity ermSchoolFunded = new ErmSchoolFundedEntity();
            ermSchoolFunded.setId(schFundId);
            ermSchoolFunded.setStatus(FundStatus.CLOSE.getCode());
            ermSchoolFundedService.update(ermSchoolFunded);
            return R.ok("项目成功结束");
        } else {
            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("结束项目异常");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("finish");
            tbErmLog.setServiceId("finish");
            tbErmLog.setMsg("还存在【" + remain + "】位用户未确认，项目暂时不能结束！");
            tbErmLog.setCreateTime(new Date());
            ermLogService.save(tbErmLog);
            return R.error("还存在【" + remain + "】位用户未确认，项目暂时不能结束！");
        }

    }

    @ApiOperation(value = "结束项目", notes = "结束资助项目")
    @ApiImplicitParam(name = "id", value = "项目ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/archive/{schFundId}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB', 'SCH')")
    public R archive(@PathVariable("schFundId") Integer schFundId, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        int remain = ermFundedInfoService.queryRemainCountBySchFund(schFundId, FundStatus.NOTICE.getCode());
        if (remain == 0) {
            ErmSchoolFundedEntity ermSchoolFunded = new ErmSchoolFundedEntity();
            ermSchoolFunded.setId(schFundId);
            ermSchoolFunded.setStatus(FundStatus.CLOSE.getCode());
            ermSchoolFundedService.update(ermSchoolFunded);
            return R.ok("项目成功归档");
        } else {

            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("结束资助项目异常");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("finish");
            tbErmLog.setServiceId("finish");
            tbErmLog.setMsg("项目未结束，不能归档！");
            tbErmLog.setCreateTime(new Date());
            ermLogService.save(tbErmLog);
            return R.error("项目未结束，不能归档！");
        }

    }

    @ApiOperation(value = "停止资助项目", notes = "停止资助项目")
    @ApiImplicitParam(name = "id", value = "项目ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/stop/{fundid}", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB', 'SCH')")
    public R stop(@PathVariable("fundid") Integer id, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        int fundStatus = ermFundedService.getFundStatus(id);
        // 在运行中的项目不能随便停止
        if (fundStatus == FundStatus.NEW.getCode() || fundStatus == FundStatus.RUNNING.getCode()
                || fundStatus == FundStatus.CONFIG.getCode() || fundStatus == FundStatus.NOTICE.getCode()) {
            boolean flag = ermFundedService.doStopFund(id);
            if (flag)
                return R.ok("项目已停止").put("statusName", FundStatus.CLOSE.getName());
            else
                return R.error(200, "停止失败").put("statusName", FundStatus.getCNName(fundStatus));
        }

        TbErmLog tbErmLog = new TbErmLog();
        tbErmLog.setTitle("停止资助项目异常");
        tbErmLog.setCreateId(user.getUserId());
        tbErmLog.setMethod("finish");
        tbErmLog.setServiceId("finish");
        tbErmLog.setMsg("不能停止！" + FundStatus.getCNName(fundStatus));
        tbErmLog.setCreateTime(new Date());
        ermLogService.save(tbErmLog);
        return R.error(200, "不能停止").put("statusName", FundStatus.getCNName(fundStatus));

    }

    @ApiOperation(value = "查询资助项目状态", notes = "查询资助项目状态")
    @ApiImplicitParam(name = "id", value = "项目ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/status/{fundid}", method = RequestMethod.GET)
    public R rightNowStatus(@PathVariable("fundid") Integer fundid, JwtAuthenticationToken token) {
        // 1. eb的时候，传的是fundid, 2. 非EB的时候，传的是schFundid
        UserContext user = (UserContext) token.getPrincipal();
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        int roleId = admin.getRoleId();
        if (roleId == UserType.EB.getCode()) {
            Map<String, FundFlowGraph> schFlow = new HashMap<String, FundFlowGraph>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fundedId", fundid);
            List<ErmSchoolFundedEntity> fundList = ermSchoolFundedService.queryList(map);
            for (ErmSchoolFundedEntity ermSchoolFundedEntity : fundList) {
                String key = ermSchoolFundedEntity.getSchoolName();
                if (null != ermSchoolFundedEntity.getSchzoneName())
                    key = ermSchoolFundedEntity.getSchoolName() + "-" + ermSchoolFundedEntity.getSchzoneName();
                else if (null != ermSchoolFundedEntity.getLevelName())
                    key = ermSchoolFundedEntity.getSchoolName() + "-" + ermSchoolFundedEntity.getLevelName();
                schFlow.put(key, FundFlowGraph.schFlow(ermSchoolFundedEntity.getStatus()));
            }
            return R.ok().put("fundFlowMap", schFlow);
        } else {
            ErmSchoolFundedEntity obj = ermSchoolFundedService.queryObject(fundid);
            if (null != obj) {
                int fundStatus = obj.getStatus();
                FundFlowGraph flow = FundFlowGraph.schFlow(fundStatus);
                return R.ok().put("fundFlowGraph", flow);
            } else {
                return R.ok("项目不存在或项目未启动！");
            }
        }

    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取学校资助信息列表", notes = "获取学校资助信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R ebFundList(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        // 教育局传学校id
        if (params.get("schoolId") == null) {
            params.put("schoolId", user.getSchoolId());
        }
        params.put("ebId", user.getEbId());
        params.put("status", FundStatus.EBAUDIT.getCode());
        // 查询列表数据
        Query query = new Query(params);
        List<ErmFundedEntity> ermFundedList = ermFundedService.queryFundList(query);
        int total = ermFundedService.queryFundListTotal(query);

        PageUtils pageUtil = new PageUtils(ermFundedList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    @ApiOperation(value = "审核资助名单", notes = "审核资助名单")
    @ApiImplicitParam(name = "ermAuditParamExt", value = "ErmAuditParamExt", required = true, dataType = "ErmAuditLogEntity")
    @RequestMapping(value = "/auditByFundBackup", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB')")
    public R auditByFundBackup(@RequestBody ErmAuditParamExt ermAuditParamExt, JwtAuthenticationToken token) {
        // 查询用户
        UserContext user = (UserContext) token.getPrincipal();
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        int roleId = admin.getRoleId();
        int fundStatus = FundStatus.EBAUDIT.getCode();
        if (roleId == UserType.EB.getCode())
            fundStatus = FundStatus.EBAUDIT.getCode();

        ErmSchoolFundedEntity schFund = ermSchoolFundedService.queryObject(ermAuditParamExt.getSchFundId());
        // 1. 取个数
        int remainCount = ermFundedInfoService.queryRemainCountBySchFund(ermAuditParamExt.getSchFundId(), fundStatus);
        // oper or school
        if (null != user.getSchoolId() && user.getSchoolId() != 0 && schFund.getSchoolId() != user.getSchoolId())
            return R.error("您没有权限审批非本校的项目！");
        // 审批
        if (remainCount > 0) {
            // 取待审核列表
            List<Map<String, Object>> users = ermFundProcessService.queryAuditableUser(ermAuditParamExt.getSchFundId(),
                    fundStatus);
            if (users.size() > remainCount && ermAuditParamExt.getAuditStatus() == 1)
                return R.error("教育局分配的名额小于待审批的名额,不能进行审批！");
            for (Map<String, Object> map : users) {
                // 写log
                int stuFundId = Integer.parseInt(map.get("id").toString());
                ErmAuditLogEntity toSave = new ErmAuditLogEntity();
                toSave.setCreator(((UserContext) token.getPrincipal()).getUsername());
                toSave.setCreateTime(new Date());
                toSave.setAuditDate(new Date());
                if (ermAuditParamExt.getAuditStatus() == 1)// 通过
                    toSave.setStatus(FundStatus.EBAUDITSUCCESS.getCode());
                else
                    toSave.setStatus(FundStatus.EBAUDITFAIL.getCode());
                toSave.setFundedId(stuFundId);
                ermAuditLogService.save(toSave);

                // 改状态
                ErmFundedInfoEntity ee = new ErmFundedInfoEntity();
                if (ermAuditParamExt.getAuditStatus() != 1) {
                    if (roleId == UserType.OPER.getCode())
                        ee.setGlobalStatus(FundStatus.OPERAUDITFAIL.getCode());
                    else if (roleId == UserType.SCH.getCode())
                        ee.setGlobalStatus(FundStatus.SCHAUDITFAIL.getCode());
                    else if (roleId == UserType.EB.getCode())
                        ee.setGlobalStatus(FundStatus.EBAUDITFAIL.getCode());
                } else {

                    if (roleId == UserType.OPER.getCode())
                        ee.setGlobalStatus(FundStatus.SCHAUDIT.getCode());
                    else if (roleId == UserType.SCH.getCode())
                        ee.setGlobalStatus(FundStatus.EBAUDIT.getCode());
                    else if (roleId == UserType.EB.getCode())
                        ee.setGlobalStatus(FundStatus.CARDCOMPARE.getCode());
                }
                ee.setId(stuFundId);
                ee.setUpdateTime(new Date());
                ee.setUpdator(((UserContext) token.getPrincipal()).getUsername());
                ermFundedInfoService.update(ee);
                // 发送短信
                ErmStudentEntity student = ermStudentService.queryStudentByStuFundId(stuFundId);
                if (null != student.getTelphone() && !student.getTelphone().isEmpty())
                    ermSmsService.sendMsg(student.getTelphone(), student.getName(), ermAuditParamExt.getAuditStatus(),
                            IS_TEST_SENDING_MSG);
            }
            // 改项目整体的状态， 再查一次还有多少指标名额，没有的话，就改状态至下一环节
            remainCount = ermFundedInfoService.queryRemainCountBySchFund(ermAuditParamExt.getSchFundId(),
                    FundStatus.EBAUDITSUCCESS.getCode());
            if (remainCount <= 0) {
                schFund.setStatus(FundStatus.CARDCOMPARE.getCode());
                ermSchoolFundedService.update(schFund);

            } else {
                schFund.setStatus(FundStatus.EBAUDIT.getCode());
                ermSchoolFundedService.update(schFund);
            }
        } else {

            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("审核资助名单");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("importStudentPicInfo");
            tbErmLog.setServiceId("importStudentPicInfo");
            tbErmLog.setMsg("审核资助名单异常" + schFund.getFundedName());
            tbErmLog.setCreateTime(new Date());
            ermLogService.save(tbErmLog);
            return R.error("教育局分配的名额小于待审批的名额,不能进行审批！");
        }
        return R.ok("审核完成");
    }

    @Deprecated
    @ApiOperation(value = "操作员提交审核资助名单", notes = "操作员提交审核资助名单")
    @ApiImplicitParam(name = "ermAuditParam", value = "ErmAuditParam", required = true, dataType = "ErmAuditLogEntity")
    @RequestMapping(value = "/operSingleSubmit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER')")
    public R operSingleSubmit(@RequestBody ErmAuditParam auditParam, JwtAuthenticationToken token) {
        // 1.先查一下当前学生的流程状态，满足条件才能走审核逻辑。 TODO 先不加
        // 2. 根据审核数据进行审核日志保存，并更新流程的状态。
        // 2.1 保存日志
        ErmAuditLogEntity toSave = new ErmAuditLogEntity();
        toSave.setCreator(((UserContext) token.getPrincipal()).getUsername());
        toSave.setCreateTime(new Date());
        toSave.setAuditDate(new Date());
        toSave.setAuditRemark(auditParam.getNote());
        if (auditParam.getAuditStatus() == 1) // 通过
            toSave.setStatus(FundStatus.OPERAUDITSUCCESS.getCode());
        else
            toSave.setStatus(FundStatus.OPERAUDITFAIL.getCode());
        toSave.setFundedId(auditParam.getStuFundId());
        ermAuditLogService.save(toSave);
        // 2.2 更新fund_info表的状态
        ErmFundedInfoEntity ee = new ErmFundedInfoEntity();
        if (auditParam.getAuditStatus() != 1) // 不通过
        {
            ee.setGlobalStatus(FundStatus.OPERAUDITFAIL.getCode());
        } else {
            ee.setGlobalStatus(FundStatus.SCHAUDIT.getCode());
        }
        ee.setId(auditParam.getStuFundId());
        ee.setUpdateTime(new Date());
        ee.setUpdator(((UserContext) token.getPrincipal()).getUsername());
        ermFundedInfoService.update(ee);

        return R.ok("审核完成");
    }

    @Deprecated
    @ApiOperation(value = "操作员批量提交审核资助名单", notes = "操作员批量提交审核资助名单")
    @RequestMapping(value = "/operBatchSubmit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER')")
    public R operBatchSubmit(@RequestBody Map<String, Object> stuFundInfo, JwtAuthenticationToken token) {
        // 1.按道理应该提交名单的时候，注意提交的数量不应该超过教育局分配的名额数，不过，操作员的权限大小待定，先不限制
        // 有多少满足条件的，就提交多少
        // 2. 根据审核数据进行审核日志保存，并更新流程的状态。
        // 2.1 保存日志
        int status = FundStatus.OPERAUDITSUCCESS.getCode();
        String auditNote = BATCH_AUDIT_SUCCESS;
        @SuppressWarnings("unchecked")
        List<Integer> stuFundIds = (List<Integer>) stuFundInfo.get("stuFundIds");
        Integer auditStatus = (Integer) stuFundInfo.get("auditStatus");
        if (auditStatus != 1) // 不通过
        {
            status = FundStatus.OPERAUDITFAIL.getCode();
            auditNote = BATCH_AUDIT_FAILURE;
        }

        for (Integer id : stuFundIds) {
            ErmAuditLogEntity toSave = new ErmAuditLogEntity();
            toSave.setCreator(((UserContext) token.getPrincipal()).getUsername());
            toSave.setCreateTime(new Date());
            toSave.setAuditDate(new Date());
            toSave.setAuditRemark(auditNote);
            toSave.setStatus(status);
            toSave.setFundedId(id);
            ermAuditLogService.save(toSave);

            ErmFundedInfoEntity ee = new ErmFundedInfoEntity();
            if (auditStatus != 1) // 不通过
                ee.setGlobalStatus(FundStatus.OPERAUDITFAIL.getCode());
            else
                ee.setGlobalStatus(FundStatus.SCHAUDIT.getCode());

            ee.setUpdateTime(new Date());
            ee.setUpdator(((UserContext) token.getPrincipal()).getUsername());
            ee.setId(id);
            ermFundedInfoService.update(ee);

        }
        return R.ok("审核完成");
    }

    @Deprecated
    @ApiOperation(value = "校长提交审核资助名单", notes = "校长提交审核资助名单")
    @ApiImplicitParam(name = "ermAuditParam", value = "ErmAuditParam", required = true, dataType = "ErmAuditParam")
    @RequestMapping(value = "/schSingleSubmit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SCH')")
    public R schSingleSubmit(@RequestBody ErmAuditParam auditParam, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        // 1.1 先查一下还有多少可提交审核的名单
        // 根据stufundId查询学校针对该项目的名额(stuFunid & status = FundStatus.SCHAUDITSUCCESS
        ErmSchoolFundedEntity schFund = ermSchoolFundedService.queryObjectByStuFundId(auditParam.getStuFundId());
        int remainCount = ermFundedInfoService.queryRemainCountByStuFund(auditParam.getStuFundId(),
                FundStatus.SCHAUDITSUCCESS.getCode(), user.getSchoolId(), schFund.getId());
        if (remainCount <= 0 && auditParam.getAuditStatus() == 1) {
            schFund.setStatus(FundStatus.EBAUDIT.getCode());
            ermSchoolFundedService.update(schFund);
            return R.ok("教育局分配的名额已用完！");
        } else {
            // 2. 根据审核数据进行审核日志保存，并更新流程的状态。
            // 2.1 保存日志
            ErmAuditLogEntity toSave = new ErmAuditLogEntity();
            toSave.setCreator(user.getUsername());
            toSave.setCreateTime(new Date());
            toSave.setAuditDate(new Date());
            toSave.setAuditRemark(auditParam.getNote());
            if (auditParam.getAuditStatus() == 1) // 通过
                toSave.setStatus(FundStatus.SCHAUDITSUCCESS.getCode());
            else
                toSave.setStatus(FundStatus.SCHAUDITFAIL.getCode());
            toSave.setFundedId(auditParam.getStuFundId());
            ermAuditLogService.save(toSave);
            // 2.2 更新fund_info表的状态

            ErmFundedInfoEntity ee = new ErmFundedInfoEntity();
            if (auditParam.getAuditStatus() != 1) // 不通过
                ee.setGlobalStatus(FundStatus.SCHAUDITFAIL.getCode());
            else
                ee.setGlobalStatus(FundStatus.EBAUDIT.getCode());
            ee.setId(auditParam.getStuFundId());
            ee.setUpdateTime(new Date());
            ee.setUpdator(user.getUsername());
            ermFundedInfoService.update(ee);
            remainCount = ermFundedInfoService.queryRemainCountByStuFund(auditParam.getStuFundId(),
                    FundStatus.SCHAUDITSUCCESS.getCode(), user.getSchoolId(), schFund.getId());

            if (remainCount > 0) {
                schFund.setStatus(FundStatus.SCHAUDIT.getCode());
                ermSchoolFundedService.update(schFund);
            } else {
                schFund.setStatus(FundStatus.EBAUDIT.getCode());
                ermSchoolFundedService.update(schFund);
            }

            return R.ok("审核完成");
        }

    }

    @Deprecated
    @ApiOperation(value = "校长批量提交审核资助名单", notes = "校长批量提交审核资助名单")
    @RequestMapping(value = "/schBatchSubmit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('SCH')")
    public R schBatchSubmit(@RequestBody Map<String, Object> stuFundInfo, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        // 1.先查一下还有多少可提交审核的名单,如果批量的名单超额了， 直接提示校长减少提提交名额
        @SuppressWarnings("unchecked")
        List<Integer> stuFundIds = (List<Integer>) stuFundInfo.get("stuFundIds");
        Integer auditStatus = (Integer) stuFundInfo.get("auditStatus");
        int submitCount = stuFundIds.size();
        ErmSchoolFundedEntity schFund = ermSchoolFundedService.queryObjectByStuFundId(stuFundIds.get(0));
        int remainCount = ermFundedInfoService.queryRemainCountByStuFund(stuFundIds.get(0),
                FundStatus.SCHAUDITSUCCESS.getCode(), user.getSchoolId(), schFund.getId());

        if (remainCount < submitCount && auditStatus == 1) {
            return R.ok("教育局分配的名额不足,剩下[" + remainCount + "]个,但是您提交了[" + submitCount + "]个!");

        }
        // 2. 根据审核数据进行审核日志保存，并更新流程的状态。
        // 2.1 保存日志
        int status = FundStatus.SCHAUDITSUCCESS.getCode();
        String auditNote = BATCH_AUDIT_SUCCESS;
        if (auditStatus != 1) // 不通过
        {
            status = FundStatus.SCHAUDITFAIL.getCode();
            auditNote = BATCH_AUDIT_FAILURE;
        }

        for (Integer id : stuFundIds) {
            ErmAuditLogEntity toSave = new ErmAuditLogEntity();
            toSave.setCreator(user.getUsername());
            toSave.setCreateTime(new Date());
            toSave.setAuditDate(new Date());
            toSave.setAuditRemark(auditNote);
            toSave.setStatus(status);
            toSave.setFundedId(id);
            ermAuditLogService.save(toSave);

            // 改状态

            ErmFundedInfoEntity ee = new ErmFundedInfoEntity();
            if (auditStatus != 1) // 不通过
                ee.setGlobalStatus(FundStatus.SCHAUDITFAIL.getCode());
            else
                ee.setGlobalStatus(FundStatus.EBAUDIT.getCode());

            ee.setId(id);
            ee.setUpdateTime(new Date());
            ee.setUpdator(user.getUsername());
            ermFundedInfoService.update(ee);
        }
        remainCount = ermFundedInfoService.queryRemainCountByStuFund(stuFundIds.get(0),
                FundStatus.SCHAUDITSUCCESS.getCode(), user.getSchoolId(), schFund.getId());
        if (remainCount > 0) {
            schFund.setStatus(FundStatus.SCHAUDIT.getCode());
            ermSchoolFundedService.update(schFund);
        } else {
            schFund.setStatus(FundStatus.EBAUDIT.getCode());
            ermSchoolFundedService.update(schFund);
        }
        return R.ok("审核完成");
    }

    @Deprecated
    @ApiOperation(value = "教育局审核资助项目", notes = "教育局审核资助项目")
    @ApiImplicitParam(name = "ermAuditParam", value = "ErmAuditParam", required = true, dataType = "ErmAuditParam")
    @RequestMapping(value = "/ebSingleAudit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB')")
    public R ebSingleAudit(@RequestBody ErmAuditParam auditParam, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ErmSchoolFundedEntity schoolFunded = ermSchoolFundedService.queryObjectByStuFundId(auditParam.getStuFundId());
        ErmSchoolFundedEntity schFund = ermSchoolFundedService.queryObjectByStuFundId(auditParam.getStuFundId());
        int remainCount = ermFundedInfoService.queryRemainCountByStuFund(auditParam.getStuFundId(),
                FundStatus.EBAUDITSUCCESS.getCode(), schoolFunded.getSchoolId(), schFund.getId());

        if (remainCount <= 0 && auditParam.getAuditStatus() == 1) {

            schFund.setStatus(FundStatus.CARDCOMPARE.getCode());
            ermSchoolFundedService.update(schFund);
            return R.ok("该学校名额已用完,不能再进行审批！ ");

        } else {
            ErmFundedInfoEntity ermFundedInfoEntity = ermFundedInfoService.queryObject(auditParam.getStuFundId());
            // 只有在教育局审批状态下，才能审批
            if (ermFundedInfoEntity.getGlobalStatus() == FundStatus.EBAUDIT.getCode()) {
                ErmAuditLogEntity toSave = new ErmAuditLogEntity();
                toSave.setCreator(user.getUsername());
                toSave.setCreateTime(new Date());
                toSave.setAuditDate(new Date());
                toSave.setAuditRemark(auditParam.getNote());
                if (auditParam.getAuditStatus() == 1) // 通过
                    toSave.setStatus(FundStatus.EBAUDITSUCCESS.getCode());
                else
                    toSave.setStatus(FundStatus.EBAUDITFAIL.getCode());
                toSave.setFundedId(auditParam.getStuFundId());
                ermAuditLogService.save(toSave);
                // 2.2 更新fund_info表的状态
                ErmFundedInfoEntity ee = new ErmFundedInfoEntity();

                if (auditParam.getAuditStatus() != 1) // 不通过
                    ee.setGlobalStatus(FundStatus.EBAUDITFAIL.getCode());
                else
                    ee.setGlobalStatus(FundStatus.CARDCOMPARE.getCode());

                ee.setId(auditParam.getStuFundId());
                ermFundedInfoService.update(ee);

                remainCount = ermFundedInfoService.queryRemainCountByStuFund(auditParam.getStuFundId(),
                        FundStatus.EBAUDITSUCCESS.getCode(), schoolFunded.getSchoolId(), schFund.getId());
                if (remainCount > 0) {
                    schFund.setStatus(FundStatus.EBAUDIT.getCode());
                    ermSchoolFundedService.update(schFund);
                } else {
                    schFund.setStatus(FundStatus.CARDCOMPARE.getCode());
                    ermSchoolFundedService.update(schFund);
                }

                ErmStudentEntity student = ermStudentService.queryStudentByStuFundId(auditParam.getStuFundId());
                if (null != student.getTelphone() && !student.getTelphone().isEmpty())
                    ermSmsService.sendMsg(student.getTelphone(), student.getName(), auditParam.getAuditStatus(),
                            IS_TEST_SENDING_MSG);
                return R.ok("审核完成");
            } else
                return R.ok("该条记录流程状态异常, 流程状态为[" + FundStatus.getName(ermFundedInfoEntity.getGlobalStatus()) + "]");
        }

    }

    /**
     * 前提是页面上显示的都是同一个学校的 ebBatchSubmit TODO
     *
     * @param token
     * @return
     */
    @Deprecated
    @ApiOperation(value = "教育局批量审核资助项目", notes = "教育局批量审核资助项目")
    @RequestMapping(value = "/ebBatchSubmit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB')")
    public R ebBatchSubmit(@RequestBody Map<String, Object> stuFundInfo, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        @SuppressWarnings("unchecked")
        List<Integer> stuFundIds = (List<Integer>) stuFundInfo.get("stuFundIds");
        Integer auditStatus = (Integer) stuFundInfo.get("auditStatus");
        ErmSchoolFundedEntity schoolFunded = ermSchoolFundedService.queryObjectByStuFundId(stuFundIds.get(0));
        // 1.先查一下还有多少可提交审核的名单,如果批量的名单超额了， 直接提示校长减少提提交名额
        int submitCount = stuFundIds.size();
        int remainCount = ermFundedInfoService.queryRemainCountByStuFund(stuFundIds.get(0),
                FundStatus.EBAUDITSUCCESS.getCode(), schoolFunded.getSchoolId(), schoolFunded.getId());
        if (remainCount < submitCount && auditStatus == 1) {

            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("教育局批量审核资助项目");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("importStudentPicInfo");
            tbErmLog.setServiceId("importStudentPicInfo");
            tbErmLog.setMsg(schoolFunded.getSchoolName() + "该学校剩余名额不足,剩下[" + remainCount + "]个,但是您提交了[" + submitCount + "]个!");
            tbErmLog.setCreateTime(new Date());
            ermLogService.save(tbErmLog);
            return R.ok("该学校剩余名额不足,剩下[" + remainCount + "]个,但是您提交了[" + submitCount + "]个!");
        }
        // 2. 根据审核数据进行审核日志保存，并更新流程的状态。
        // 2.1 保存日志
        int status = FundStatus.EBAUDITSUCCESS.getCode();
        String auditNote = BATCH_AUDIT_SUCCESS;
        if (auditStatus != 1) // 不通过
        {
            status = FundStatus.EBAUDITFAIL.getCode();
            auditNote = BATCH_AUDIT_FAILURE;
        }
        int counter = 0;
        for (Integer id : stuFundIds) {
            ErmFundedInfoEntity ermFundedInfoEntity = ermFundedInfoService.queryObject(id);
            // 只有在教育局审批状态下，才能审批
            if (ermFundedInfoEntity.getGlobalStatus() == FundStatus.EBAUDIT.getCode()) {
                ErmAuditLogEntity toSave = new ErmAuditLogEntity();
                toSave.setCreator(user.getUsername());
                toSave.setCreateTime(new Date());
                toSave.setAuditDate(new Date());
                toSave.setAuditRemark(auditNote);
                toSave.setStatus(status);
                toSave.setFundedId(id);
                ermAuditLogService.save(toSave);

                // 改状态
                ErmFundedInfoEntity ee = new ErmFundedInfoEntity();
                if (auditStatus != 1) // 不通过
                    ee.setGlobalStatus(FundStatus.EBAUDITFAIL.getCode());
                else
                    ee.setGlobalStatus(FundStatus.CARDCOMPARE.getCode());
                ee.setId(id);
                ee.setUpdateTime(new Date());
                ee.setUpdator(user.getUsername());
                ermFundedInfoService.update(ee);

                ErmStudentEntity student = ermStudentService.queryStudentByStuFundId(id);
                if (null != student.getTelphone() && !student.getTelphone().isEmpty())
                    ermSmsService.sendMsg(student.getTelphone(), student.getName(), auditStatus, IS_TEST_SENDING_MSG);
            } else {
                counter++;
            }

        }

        remainCount = ermFundedInfoService.queryRemainCountByStuFund(stuFundIds.get(0),
                FundStatus.EBAUDITSUCCESS.getCode(), schoolFunded.getSchoolId(), schoolFunded.getId());
        if (remainCount > 0) {
            schoolFunded.setStatus(FundStatus.EBAUDIT.getCode());
            ermSchoolFundedService.update(schoolFunded);
        } else {
            schoolFunded.setStatus(FundStatus.CARDCOMPARE.getCode());
            ermSchoolFundedService.update(schoolFunded);
        }
        return R.ok("批量审核完成, 成功审核[" + (submitCount - counter) + "]个, 有[" + counter + "]个流程状态异常，不能审批!");

    }

    @ApiOperation(value = "查询学校待审核名单", notes = "查询学校待审核名单")
    @RequestMapping(value = "/students/list", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyRole('EB', 'SCH', 'OPER')")
    public R toAuditList(@RequestBody ErmQueryObject ermQueryObject, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        if (null == ermQueryObject.getSchId())
            ermQueryObject.setSchId(user.getSchoolId());
        // 审核的名单，一定是班主任认定后的名单。
        ermQueryObject.setTeacheThecked(1);
        // 如果没有传 schFundId, 可能是在教育局端的查询。选择的是大项目，大项目下有一堆 小项目，可能需要
        // 通过 fundId schId schzone level等查出schFundId
        if (null == ermQueryObject.getSchFundId() || ermQueryObject.getSchFundId() == 0) {
            if (null == ermQueryObject.getFundId() || ermQueryObject.getFundId() == 0)
                return R.error("请先选择一个项目！");
            ErmFundedEntity funded = ermFundedService.queryObjectOriginal(ermQueryObject.getFundId());
            ermQueryObject.setSemester(funded.getSemester());
            ermQueryObject.setYear(funded.getYear());
        }
        List<ColumnHeader> header = ermFundProcessService.queryToAuditUsersHeader(ermQueryObject.getSchId(),
                ermQueryObject.getFundId());
        PageUtils pa = ermFundProcessService.queryToAuditUsers(ermQueryObject);
        return R.ok().put("page", pa).put("header", header);

    }

    @ApiOperation(value = "查询已提交的名单", notes = "查询已提交的名单")
    @RequestMapping(value = "/students/audited", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyRole('EB', 'SCH', 'OPER')")
    public R audited(@RequestBody ErmQueryObject ermQueryObject, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        Set<Integer> statusSet = null;
        int roleId = admin.getRoleId();
        // 审核的名单，一定是班主任认定后的名单。
        ermQueryObject.setTeacheThecked(1);
        if (roleId == UserType.OPER.getCode()) {
            statusSet = new HashSet<Integer>();
            statusSet.add(FundStatus.SCHAUDIT.getCode());
            statusSet.add(FundStatus.SCHANNOUNCEMENT.getCode());
            statusSet.add(FundStatus.EBAUDIT.getCode());
            statusSet.add(FundStatus.CARDCOMPARE.getCode());
            statusSet.add(FundStatus.OFFERRED.getCode());
            statusSet.add(FundStatus.NOTICE.getCode());
            statusSet.add(FundStatus.CLOSE.getCode());
            statusSet.add(FundStatus.OPERAUDITFAIL.getCode());
            statusSet.add(FundStatus.SCHAUDITFAIL.getCode());
            statusSet.add(FundStatus.EBAUDITFAIL.getCode());
            statusSet.add(FundStatus.SCHAUDITSUCCESS.getCode());
            statusSet.add(FundStatus.EBAUDITSUCCESS.getCode());
            statusSet.add(FundStatus.CCFAIL.getCode());
            statusSet.add(FundStatus.CCSUCCESS.getCode());
            statusSet.add(FundStatus.OFFERRING.getCode());
            ermQueryObject.setStatusSet(statusSet);
        } else if (roleId == UserType.SCH.getCode()) {
            statusSet = new HashSet<Integer>();
            statusSet.add(FundStatus.SCHANNOUNCEMENT.getCode());
            statusSet.add(FundStatus.EBAUDIT.getCode());
            statusSet.add(FundStatus.CARDCOMPARE.getCode());
            statusSet.add(FundStatus.OFFERRED.getCode());
            statusSet.add(FundStatus.NOTICE.getCode());
            statusSet.add(FundStatus.CLOSE.getCode());
            statusSet.add(FundStatus.SCHAUDITFAIL.getCode());
            statusSet.add(FundStatus.EBAUDITFAIL.getCode());
            statusSet.add(FundStatus.SCHAUDITSUCCESS.getCode());
            statusSet.add(FundStatus.EBAUDITSUCCESS.getCode());
            statusSet.add(FundStatus.CCFAIL.getCode());
            statusSet.add(FundStatus.CCSUCCESS.getCode());
            statusSet.add(FundStatus.OFFERRING.getCode());
            ermQueryObject.setStatusSet(statusSet);
        } else if (roleId == UserType.EB.getCode()) {
            statusSet = new HashSet<Integer>();
            statusSet.add(FundStatus.CARDCOMPARE.getCode());
            statusSet.add(FundStatus.OFFERRED.getCode());
            statusSet.add(FundStatus.NOTICE.getCode());
            statusSet.add(FundStatus.CLOSE.getCode());
            statusSet.add(FundStatus.EBAUDITFAIL.getCode());
            statusSet.add(FundStatus.EBAUDITSUCCESS.getCode());
            statusSet.add(FundStatus.CCFAIL.getCode());
            statusSet.add(FundStatus.CCSUCCESS.getCode());
            statusSet.add(FundStatus.OFFERRING.getCode());
            ermQueryObject.setStatusSet(statusSet);
        }
        if (null == ermQueryObject.getSchId())
            ermQueryObject.setSchId(user.getSchoolId());
        if (null != ermQueryObject.getSchId()) {
            List<ColumnHeader> header = ermFundProcessService.queryToAuditUsersHeader(ermQueryObject.getSchId(),
                    ermQueryObject.getFundId());
            PageUtils pa = ermFundProcessService.queryToAuditUsers(ermQueryObject);
            return R.ok().put("page", pa).put("header", header);
        }
        return R.error("请先选择一个学校！");

    }

    @ApiOperation(value = "查询申报对象名单、资助对象名单", notes = "查询申报对象名单、资助对象名单")
    @RequestMapping(value = "/students/queryApply", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyRole('EB', 'SCH', 'OPER')")
    public R queryApply(@RequestBody ErmQueryObject ermQueryObject, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        // 审核的名单，一定是班主任认定后的名单。
        ermQueryObject.setTeacheThecked(1);

        if (null == ermQueryObject.getSchId())
            ermQueryObject.setSchId(user.getSchoolId());
        if (null != ermQueryObject.getSchId()) {
            List<ColumnHeader> header = ermFundProcessService.queryToAuditUsersHeader(ermQueryObject.getSchId(),
                    ermQueryObject.getFundId());
            PageUtils pa = ermFundProcessService.queryApply(ermQueryObject);
            return R.ok().put("page", pa).put("header", header);
        }
        return R.error("请先选择一个学校！");

    }

    @ApiOperation(value = "删除申报对象名单、资助对象名单", notes = "删除申报对象名单、资助对象名单")
    @PostMapping(value = "/students/deleteApply/{id}")
    @PreAuthorize("hasAnyRole('EB', 'SCH', 'OPER')")
    public R deleteApply(@PathVariable long id, JwtAuthenticationToken token) {

        ermFundProcessService.deleteApply(id);

        return R.ok();

    }

    @ApiOperation(value = "导出申报对象名单、资助对象名单", notes = "导出申报对象名单、资助对象名单")
    @RequestMapping(value = "/students/exportStudentApply", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyRole('EB', 'SCH', 'OPER')")
    public void exportStudentApply(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody ErmQueryObject ermQueryObject, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        // 审核的名单，一定是班主任认定后的名单。
        ermQueryObject.setTeacheThecked(1);
        if (null == ermQueryObject.getSchId())
            ermQueryObject.setSchId(user.getSchoolId());
        if (null != ermQueryObject.getSchId()) {
            ermQueryObject.setLimit(0);
            PageUtils pa = ermFundProcessService.queryApply(ermQueryObject);
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> resultData = (List<Map<String, Object>>) pa.getList();
            int i = 1;
            for (Map<String, Object> map : resultData) {
                map.put("No", i);
                i++;
            }
            if (ermQueryObject.getStatus() == 27) {
                ExcelUtil.downLoadExcelForMap(request, response, resultData, Constants.APPLY_STUDENT, null, null, null);
            }
            if (ermQueryObject.getStatus() == 31) {
                ExcelUtil.downLoadExcelForMap(request, response, resultData, Constants.FUNDING_STUDENT, null, null, null);
            }

        }

    }

    @ApiOperation(value = "查询学校待审核名单", notes = "查询学校待审核名单")
    @RequestMapping(value = "/students/list/my", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyRole('EB', 'SCH', 'OPER')")
    public R myToAuditList(@RequestBody ErmQueryObject ermQueryObject, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        if (null == ermQueryObject.getSchId())
            ermQueryObject.setSchId(user.getSchoolId());
        if (null == ermQueryObject.getSchId())
            return R.error("请先选择一个学校！");
        // 审核的名单，一定是班主任认定后的名单。
        ermQueryObject.setTeacheThecked(1);
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        int roleId = admin.getRoleId();
        if (roleId == UserType.OPER.getCode())
            ermQueryObject.setStatus(FundStatus.OPERAUDIT.getCode());
        else if (roleId == UserType.SCH.getCode())
            ermQueryObject.setStatus(FundStatus.SCHAUDIT.getCode());
        else if (roleId == UserType.EB.getCode())
            ermQueryObject.setStatus(FundStatus.EBAUDIT.getCode());
        List<ColumnHeader> header = ermFundProcessService.queryToAuditUsersHeader(ermQueryObject.getSchId(),
                ermQueryObject.getFundId());
        PageUtils pa = ermFundProcessService.queryToAuditUsers(ermQueryObject);
        return R.ok().put("page", pa).put("header", header);
    }

    @ApiOperation(value = "剩余名额", notes = "剩余名额")
    @RequestMapping(value = "/remainCount", method = {RequestMethod.GET, RequestMethod.POST})
    @PreAuthorize("hasAnyRole('OPER', 'EB', 'SCH')")
    public R remainCount(@RequestBody ErmQueryObject param, JwtAuthenticationToken token) {
        int remainCount = 0;
        // 传了学校项目ID的话，就按这个ID来查。
        if (null != param.getSchFundId() && param.getSchFundId() != 0) {
            remainCount = ermFundedInfoService.queryRemainCountBySchFund(param.getSchFundId(), param.getStatus());
        } else {
            // 没有传schFundId, 就要按学校ID/项目ID/年份/学期/level等来查询
            ErmFundedEntity funded = ermFundedService.queryObjectOriginal(param.getFundId());
            ErmSchoolFundedEntity schFunded = ermSchoolFundedService.queryObjectByFundId(param.getFundId(),
                    param.getSchId(), funded.getYear(), funded.getSemester(), param.getSchzone(), param.getLevel());
            remainCount = ermFundedInfoService.queryRemainCountBySchFund(schFunded.getId(), param.getStatus());
        }
        return R.ok().put("remainCount", remainCount);
    }

    @ApiOperation(value = "资助名单拟定／审核　通用接口", notes = "资助名单拟定／审核　通用接口")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER', 'EB', 'SCH')")
    public R audit(@RequestBody ErmToFundParam ermToFundParam, JwtAuthenticationToken token) {


        ErmSchoolFundedEntity ermSchoolFundedEntity = ermSchoolFundedService.queryObject(ermToFundParam.getSchFundId());
        if (ermSchoolFundedEntity.getStatus() > 14) {
            return R.ok("项目已结束不能添加");
        }
        return auditStudents(ermToFundParam, token);
    }

    private R auditStudents(ErmToFundParam ermToFundParam, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        int roleId = admin.getRoleId();
        FundStatus currentSucceedStatus = FundStatus.OPERAUDITSUCCESS;
        FundStatus currentFailStatus = FundStatus.OPERAUDITFAIL;
        FundStatus nextStatus = FundStatus.SCHAUDIT;
        FundStatus current = FundStatus.OPERAUDIT;
        if (roleId == UserType.SCH.getCode()) {
            if (ermToFundParam.isToPublic()) // 公示页的话，下一环节是给教育局审批．
            {
                currentSucceedStatus = FundStatus.SCHANNOUNCEMENTSUCCESS;
                currentFailStatus = FundStatus.SCHANNOUNCEMENTFAIL;
                nextStatus = FundStatus.EBAUDIT;
                current = FundStatus.SCHANNOUNCEMENT;
            } else {
                currentSucceedStatus = FundStatus.SCHAUDITSUCCESS;
                currentFailStatus = FundStatus.SCHAUDITFAIL;
                nextStatus = FundStatus.SCHANNOUNCEMENT;
                current = FundStatus.SCHAUDIT;
            }

        } else if (roleId == UserType.EB.getCode()) {
            if (ermToFundParam.isToOffer()) {
                currentSucceedStatus = FundStatus.OFFERRING;
                currentFailStatus = FundStatus.OFFERREDFAIL;
                nextStatus = FundStatus.NOTICE;
                current = FundStatus.EBAUDIT;
            } else {
                currentSucceedStatus = FundStatus.EBAUDITSUCCESS;
                currentFailStatus = FundStatus.EBAUDITFAIL;
                nextStatus = FundStatus.NOTICE;
                current = FundStatus.EBAUDIT;
            }

        }
        if (null == ermToFundParam.getStuIds() || ermToFundParam.getStuIds().isEmpty())
            return R.error("请先选择一个学生！");
        // 1.先查一下还有多少可提交审核的名单,如果批量的名单超额了， 直接提示校长减少提提交名额
        int submitCount = ermToFundParam.getStuIds().size();
        if (roleId == UserType.EB.getCode()) {
            int remainCount = ermFundedInfoService.queryRemainCountBySchFund(ermToFundParam.getSchFundId(),
                    currentSucceedStatus.getCode());
            if (remainCount < submitCount && ermToFundParam.isPass()) {

                TbErmLog tbErmLog = new TbErmLog();
                tbErmLog.setTitle("资助名单拟定");
                tbErmLog.setCreateId(user.getUserId());
                tbErmLog.setMethod("importStudentPicInfo");
                tbErmLog.setServiceId("importStudentPicInfo");
                tbErmLog.setMsg(ermToFundParam.getSchFundId() + "该项目剩余名额不足,剩下[" + remainCount + "]个,但是您提交了[" + submitCount + "]个!");
                tbErmLog.setCreateTime(new Date());
                ermLogService.save(tbErmLog);
                return R.ok("该项目剩余名额不足,剩下[" + remainCount + "]个,但是您提交了[" + submitCount + "]个!").put("wyStatus", 1);
            } else if (remainCount == submitCount && ermToFundParam.isPass()) {
                ermFundProcessService.doAuditStus(ermToFundParam.getSchFundId(), ermToFundParam.getStuIds(),
                        ermToFundParam.isPass() ? currentSucceedStatus : currentFailStatus,
                        ermToFundParam.isPass() ? nextStatus : current, user.getUsername(),
                        ermToFundParam.isPass() ? (remainCount - submitCount) : remainCount, ermToFundParam.isPass(),
                        ermToFundParam.getRemark());
                //项目完成
                if (ermToFundParam.isToOffer()) {
                    currentSucceedStatus = FundStatus.OFFERREDSUCCESS;
                    currentFailStatus = FundStatus.OFFERREDFAIL;
                    nextStatus = FundStatus.CLOSE;
                    current = FundStatus.EBAUDIT;
                } else {
                    currentSucceedStatus = FundStatus.OFFERREDSUCCESS;
                    currentFailStatus = FundStatus.EBAUDITFAIL;
                    nextStatus = FundStatus.CLOSE;
                    current = FundStatus.EBAUDIT;
                }
                List<Map<String, Object>> users = ermFundProcessService.queryAuditableUser(ermToFundParam.getSchFundId(),
                        FundStatus.NOTICE.getCode());
                ErmToFundParam p = new ErmToFundParam();
                p.setPass(true);
                p.setRemark(FundStatus.getCNName(FundStatus.EBAUDITSUCCESS.getCode()));
                p.setSchFundId(ermToFundParam.getSchFundId());
                p.setStuIds(extractStuIds(users));
                ermFundProcessService.doAuditStus(p.getSchFundId(), p.getStuIds(),
                        currentSucceedStatus,
                        nextStatus, user.getUsername(),
                        (remainCount - submitCount), p.isPass(),
                        p.getRemark());
                ErmSchoolFundedEntity ermSchoolFundedEntity = ermSchoolFundedService.queryObject(p.getSchFundId());
                ErmFundedEntity ermFundedEntity = ermFundedService.queryObjectOriginal(ermSchoolFundedEntity.getFundedId());
                ErmSchoolEntity ermSchoolEntity = ermSchoolService.queryObject(ermSchoolFundedEntity.getSchoolId());

                ErmFunedNoticeEntity ermFunedNoticeEntity = new ErmFunedNoticeEntity();
                ermFunedNoticeEntity.setTitle("资助已发放");
                ermFunedNoticeEntity.setDescrib(ermFundedEntity.getName() + "的资助款项已发放！");
                ermFunedNoticeEntity.setType(1 + "");
                ermFunedNoticeEntity.setSchools(ermSchoolEntity.getId() + "");
                ermFunedNoticeService.save(ermFunedNoticeEntity);

            } else {
                ermFundProcessService.doAuditStus(ermToFundParam.getSchFundId(), ermToFundParam.getStuIds(),
                        ermToFundParam.isPass() ? currentSucceedStatus : currentFailStatus,
                        ermToFundParam.isPass() ? nextStatus : current, user.getUsername(),
                        ermToFundParam.isPass() ? (remainCount - submitCount) : remainCount, ermToFundParam.isPass(),
                        ermToFundParam.getRemark());
            }
        } else {
            ermFundProcessService.doAuditStus(ermToFundParam.getSchFundId(), ermToFundParam.getStuIds(),
                    ermToFundParam.isPass() ? currentSucceedStatus : currentFailStatus,
                    ermToFundParam.isPass() ? nextStatus : current, user.getUsername(),
                    submitCount, ermToFundParam.isPass(),
                    ermToFundParam.getRemark());
        }

        return R.ok("操作成功!");
    }

    @ApiOperation(value = "审核资助名单", notes = "审核资助名单")
    @ApiImplicitParam(name = "ermAuditParamExt", value = "ErmAuditParamExt", required = true, dataType = "ErmAuditLogEntity")
    @RequestMapping(value = "/auditByFund", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('EB')")
    public R auditByFund(@RequestBody ErmAuditParamExt ermAuditParamExt, JwtAuthenticationToken token) {
        List<Map<String, Object>> users = ermFundProcessService.queryAuditableUser(ermAuditParamExt.getSchFundId(),
                FundStatus.EBAUDIT.getCode());
        ErmToFundParam p = new ErmToFundParam();
        if (ermAuditParamExt.getAuditStatus() == 1) {
            p.setPass(true);
            p.setRemark(FundStatus.getCNName(FundStatus.EBAUDITSUCCESS.getCode()));


        } else {
            p.setPass(false);
            p.setRemark(FundStatus.getCNName(FundStatus.EBAUDITFAIL.getCode()));
        }
        p.setSchFundId(ermAuditParamExt.getSchFundId());
        p.setStuIds(extractStuIds(users));
        R r = auditStudents(p, token);
        return r;


    }

    private List<Integer> extractStuIds(List<Map<String, Object>> users) {
        List<Integer> stus = new ArrayList<Integer>();
        if (null != users && !users.isEmpty()) {
            for (Map<String, Object> stu : users) {
                stus.add(Integer.parseInt(stu.get("stu_id").toString()));
            }
        }

        return stus;
    }

    @ApiOperation(value = "调整学生困难等级", notes = "整学生困难等级")
    @ApiImplicitParam(name = "ermAdjustParam", value = "ErmAdjustParam", required = true, dataType = "ErmAdjustParam")
    @RequestMapping(value = "/adjustDiffLevel", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('OPER','SCH', 'EB')")
    public R adjustDiffLevel(@RequestBody ErmAdjustParam ermAdjustParam, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        int schId = user.getSchoolId();
        ermAdjustParam.setSchId(schId);
        List<Integer> stuIds = ermAdjustParam.getStuIds();
        if (null != stuIds && !stuIds.isEmpty()) {
            ermFundProcessService.doAdjustDiffLevel(ermAdjustParam);
            return R.ok("操作成功!");
        } else
            return R.error("请选择至少一行记录!");
    }

}
