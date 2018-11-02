package com.coomia.erm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.Constants;
import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.entity.ErmFundedInfoEntity;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.util.BusinessUtil;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 学生资助信息表
 *
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:07
 */
@Api(value = "ErmFundedInfo-api", description = "有关于学生资助信息表的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermfundedinfo")
public class ErmFundedInfoController {
    @Autowired
    private ErmFundedInfoService ermFundedInfoService;

    @Autowired
    private ErmSchoolFundedService ermSchoolFundedService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取学生资助信息表列表", notes = "获取学生资助信息表列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);

        // List<ErmFundedInfoEntity> ermFundedInfoList =
        // ermFundedInfoService.queryList(query);
        List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryMapList(query);
        int total = ermFundedInfoService.queryMapListTotal(query);

        PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 信息查询列表
     */
    @ApiOperation(value = "信息查询列表", notes = "信息查询列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/fundedInfoList", method = RequestMethod.GET)
    public R fundedInfoList(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        if (null == params.get("schoolId")) {
            if (null != user.getSchoolId() && user.getSchoolId() != 0)
                params.put("schoolId", user.getSchoolId());
        }
        // 默认查询在读学生
        if (null == params.get("isGraduation")) {
            params.put("isGraduation", 0);
        }
        // 查询列表数据
        Query query = new Query(params);
        Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
        dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
        dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
        List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryFundedInfoMapList(query, dict);
        int total = ermFundedInfoService.queryFundedInfoMapListTotal(query);

        PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());
        return R.ok().put("header", BusinessUtil.getTableHeaderByDynamicCloumn(DictConstants.CLOUMN_ENTITY)).put("page",
                pageUtil);
    }

    /**
     * 查询学生资助信息
     */
    @ApiOperation(value = "查询学生资助信息", notes = "查询学生资助信息")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/studentFundinfo", method = RequestMethod.GET)
    public R studentFundinfo(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        // 查询列表数据
        Query query = new Query(params);
        Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
        dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
        dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
        List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryStudentFundinfoList(query, dict);
        int total = ermFundedInfoService.queryStudentFundinfoListTotal(query);

        PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());
        return R.ok().put("header", BusinessUtil.getTableHeader(Constants.STUDENT_FUND_KEYS,
                Constants.STUDENT_FUND_NAMES, Constants.STUDENT_FUND_VALIDS)).put("page", pageUtil);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据ID获取学生资助信息表", notes = "根据ID获取学生资助信息表")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public R info(@PathVariable("id") Integer id) {
        ErmFundedInfoEntity ermFundedInfo = ermFundedInfoService.queryObject(id);
        return R.ok().put("ermFundedInfo", ermFundedInfo);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增学生资助信息表", notes = "新增学生资助信息表")
    @ApiImplicitParam(name = "ErmFundedInfo", value = "学生资助信息表对象", required = true, dataType = "ErmFundedInfo")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(@RequestBody ErmFundedInfoEntity ermFundedInfo, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();

        ErmFundedInfoEntity dbEntity = ermFundedInfoService.queryStuFundBySchFund(ermFundedInfo.getFundedId(),
                ermFundedInfo.getStuId());
        ErmSchoolFundedEntity schoolFunded = ermSchoolFundedService.queryObject(ermFundedInfo.getFundedId());
        if (null != dbEntity) {
            if (dbEntity.getGlobalStatus() == FundStatus.OPERAUDITFAIL.getCode()
                    || dbEntity.getGlobalStatus() == FundStatus.SCHAUDITFAIL.getCode()
                    || dbEntity.getGlobalStatus() == FundStatus.OPERAUDIT.getCode()
                    || dbEntity.getGlobalStatus() == FundStatus.APPLY.getCode()) {
                dbEntity.setGlobalStatus(FundStatus.SCHAUDIT.getCode());
                ermFundedInfoService.update(dbEntity);
            }
        } else {
            ermFundedInfo.setCreator(user.getUsername());
            ermFundedInfo.setGlobalStatus(FundStatus.SCHAUDIT.getCode());
            if (schoolFunded.getTotalMoney() != null && schoolFunded.getCount() != 0) {
                ermFundedInfo.setMoney(schoolFunded.getTotalMoney() / schoolFunded.getCount());
            }
            ermFundedInfoService.save(ermFundedInfo);
        }

        return R.ok("认定成功");
    }

    /**
     * 保存
     */
    @ApiOperation(value = "批量新增学生资助信息表", notes = "批量新增学生资助信息表")
    @ApiImplicitParam(name = "ErmFundedInfo", value = "学生资助信息表对象", required = true, dataType = "ErmFundedInfo")
    @RequestMapping(value = "/batchSave", method = RequestMethod.POST)
    public R batchSave(@RequestBody Map<String, Object> info, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        if (info == null) {
            R.error("数据为空");
        }

        String stuIds = (String) info.get("stuIds");
        int count = 0;
        if (StringUtils.isNotBlank(stuIds)) {
            String[] stuArr = stuIds.split(",");
            Integer fundedId = (Integer) info.get("fundedId");
            String suggestion = (String) info.get("suggestion");
            ErmSchoolFundedEntity schoolFunded = ermSchoolFundedService.queryObject(fundedId);
            for (int i = 0; i < stuArr.length; i++) {
                ErmFundedInfoEntity dbEntity = ermFundedInfoService.queryStuFundBySchFund(fundedId,
                        Integer.parseInt(stuArr[i]));
                if (null != dbEntity) {
                    if (dbEntity.getGlobalStatus() == FundStatus.OPERAUDITFAIL.getCode()
                            || dbEntity.getGlobalStatus() == FundStatus.SCHAUDITFAIL.getCode()
                            || dbEntity.getGlobalStatus() == FundStatus.OPERAUDIT.getCode()
                            || dbEntity.getGlobalStatus() == FundStatus.APPLY.getCode()) {
                        dbEntity.setGlobalStatus(FundStatus.SCHAUDIT.getCode());
                        dbEntity.setSuggestion(suggestion);
                        ermFundedInfoService.update(dbEntity);
                        count++;
                    }

                } else {
                    ErmFundedInfoEntity ermFundedInfo = new ErmFundedInfoEntity();
                    ermFundedInfo.setStuId(Integer.parseInt(stuArr[i]));
                    ermFundedInfo.setFundedId(fundedId);
                    ermFundedInfo.setSuggestion(suggestion);
                    if (schoolFunded.getTotalMoney() != null && schoolFunded.getCount() != 0) {
                        ermFundedInfo.setMoney(schoolFunded.getTotalMoney() / schoolFunded.getCount());
                    }
                    ermFundedInfo.setCreator(user.getUsername());
                    ermFundedInfo.setGlobalStatus(FundStatus.SCHAUDIT.getCode());
                    ermFundedInfoService.save(ermFundedInfo);
                    count++;
                }

            }
        }
        if (count > 0) {
            return R.ok("批量认定 " + count + " 个学生成功");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改学生资助信息表", notes = "修改学生资助信息表")
    @ApiImplicitParam(name = "ErmFundedInfo", value = "学生资助信息表对象", required = true, dataType = "ErmFundedInfo")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody ErmFundedInfoEntity ermFundedInfo, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ermFundedInfo.setUpdator(user.getUsername());
        ermFundedInfoService.update(ermFundedInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除学生资助信息表", notes = "删除学生资助信息表")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(@RequestBody Integer[] ids) {
        ermFundedInfoService.deleteBatch(ids);

        return R.ok();
    }

}
