package com.coomia.erm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmFundStatus;
import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.ErmSchFundExt;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmFundedService;
import com.coomia.erm.service.ErmSchFieldService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 项目资助
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:05
 */
@Api(value = "ErmSchoolFunded-api", description = "有关于项目资助的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermschoolfunded")
public class ErmSchoolFundedController {
  @Autowired
  private ErmSchoolFundedService ermSchoolFundedService;
  @Autowired
  private ErmSchFieldService ermSchFieldService;
  @Autowired
  private ErmFundedService ermFundedService;


  /**
   * 列表
   */
  @ApiOperation(value = "获取项目状态列表", notes = "获取项目状态列表")
  @RequestMapping(value = "/fundStatus", method = RequestMethod.GET)
  public R fundStatusList(JwtAuthenticationToken token) {

    List<ErmFundStatus> list = new ArrayList<ErmFundStatus>();
    for (FundStatus fundStatus : FundStatus.values()) {
      if (fundStatus == FundStatus.NEW || fundStatus == FundStatus.CONFIG
          || fundStatus == FundStatus.APPLY || fundStatus == FundStatus.FDCONFIRM
          || fundStatus == FundStatus.OPERAUDIT || fundStatus == FundStatus.SCHAUDIT
          || fundStatus == FundStatus.EBAUDIT || fundStatus == FundStatus.CARDCOMPARE
          || fundStatus == FundStatus.CLOSE) {
        ErmFundStatus fs = new ErmFundStatus(fundStatus.getCode(), fundStatus.getName());
        list.add(fs);
      }

    }
    return R.ok().put("fundStatus", list);
  }

  /**
   * 列表
   */
  @ApiOperation(value = "获取项目资助列表", notes = "获取项目资助列表")
  @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
  @RequestMapping(value = "/fundList", method = RequestMethod.GET)
  public R fundList(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
    // 查询列表数据
    UserContext user = (UserContext) token.getPrincipal();
    if (null != user.getSchoolId() && user.getSchoolId() != 0)
      params.put("schoolId", user.getSchoolId());
    Query query = new Query(params);
    List<ErmSchoolFundedEntity> ermSchoolFundedList =
        ermSchoolFundedService.querySchFundList(query);
    int total = ermSchoolFundedService.querySchFundListTotal(query);

    PageUtils pageUtil =
        new PageUtils(ermSchoolFundedList, total, query.getLimit(), query.getPage());

    return R.ok().put("page", pageUtil);
  }

  /**
   * 列表
   */
  @ApiOperation(value = "获取项目资助列表", notes = "获取项目资助列表")
  @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
  @RequestMapping(value = "/fundListSimple", method = RequestMethod.GET)
  public R fundListSimple(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
    // 查询列表数据
    UserContext user = (UserContext) token.getPrincipal();
    if (null != user.getSchoolId() && user.getSchoolId() != 0)
      params.put("schoolId", user.getSchoolId());
    Query query = new Query(params);
    List<ErmSchoolFundedEntity> ermSchoolFundedList =
        ermSchoolFundedService.querySchFundSimpleList(query);
    int total = ermSchoolFundedService.querySchFundListTotal(query);

    PageUtils pageUtil =
        new PageUtils(ermSchoolFundedList, total, query.getLimit(), query.getPage());

    return R.ok().put("page", pageUtil);
  }

  /**
   * 列表
   */
  @ApiOperation(value = "获取项目资助列表", notes = "获取项目资助列表")
  @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
    // 查询列表数据
    UserContext user = (UserContext) token.getPrincipal();
    if (null != user.getSchoolId() && user.getSchoolId() != 0)
      params.put("schoolId", user.getSchoolId());
    Query query = new Query(params);
    List<ErmSchoolFundedEntity> ermSchoolFundedList = ermSchoolFundedService.queryList(query);
    int total = ermSchoolFundedService.queryTotal(query);

    PageUtils pageUtil =
        new PageUtils(ermSchoolFundedList, total, query.getLimit(), query.getPage());

    return R.ok().put("page", pageUtil);
  }

  /**
   * 信息
   */
  @ApiOperation(value = "根据ID获取项目资助", notes = "根据ID获取项目资助")
  @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
  @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
  public R info(@PathVariable("id") Integer id) {
    ErmSchoolFundedEntity ermSchoolFunded = ermSchoolFundedService.queryObject(id);
    return R.ok().put("ermSchoolFunded", ermSchoolFunded);
  }

  /**
   * 保存
   */
  @ApiOperation(value = "新增项目资助", notes = "新增项目资助")
  @ApiImplicitParam(name = "ErmSchoolFunded", value = "项目资助对象", required = true,
      dataType = "ErmSchoolFunded")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public R save(@RequestBody ErmSchoolFundedEntity ermSchoolFunded, JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    ermSchoolFunded.setCreator(user.getUsername());
    if (null == ermSchoolFunded.getSemester() || ermSchoolFunded.getSemester().isEmpty()) {
      ErmFundedEntity funded = ermFundedService.queryObjectOriginal(ermSchoolFunded.getFundedId());
      if (null != funded) {
        ermSchoolFunded.setYear(funded.getYear());
        ermSchoolFunded.setSemester(funded.getSemester());
      }
    }
    if (StringUtils.isBlank(ermSchoolFunded.getSchoolIds())) {
      ermSchoolFunded.setSchoolIds(String.valueOf(ermSchoolFunded.getSchoolId()));
    }
    String[] schoolArr = ermSchoolFunded.getSchoolIds().split(",");
    for (int i = 0; i < schoolArr.length; i++) {
      Integer schoolId = Integer.parseInt(schoolArr[i]);
      ermSchoolFunded.setSchoolId(schoolId);
      ErmSchoolFundedEntity ermSchoolFundedModel = this.ermSchoolFundedService
          .queryObjectByFundId(ermSchoolFunded.getFundedId(), ermSchoolFunded.getSchoolId());
      // 如果学校没分配过
      if (ermSchoolFundedModel == null) {
        ermSchoolFunded.setStatus(FundStatus.CONFIG.getCode());
        ermSchoolFundedService.save(ermSchoolFunded);
        // return R.error(500, "该学校已分配过此资助");
      }
      if (!ermSchFieldService.hasInited(schoolId))
        ermSchFieldService.doInitSchFieldsConfig(schoolId);
    }
    return R.ok();
  }

  /**
   * 保存
   */
  @ApiOperation(value = "新增项目资助", notes = "新增项目资助")
  @ApiImplicitParam(name = "ErmSchoolFunded", value = "项目资助对象", required = true,
      dataType = "ErmSchoolFunded")
  @RequestMapping(value = "/saveExt", method = RequestMethod.POST)
  public R saveExt(@RequestBody ErmSchoolFundedEntity ermSchoolFunded,
      JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    ermSchoolFunded.setCreator(user.getUsername());
    if (ermSchoolFunded.getSchIdList().isEmpty()) {
      ermSchoolFunded.setSchoolIds(String.valueOf(ermSchoolFunded.getSchoolId()));
    }
    String[] schoolArr = ermSchoolFunded.getSchoolIds().split(",");
    for (int i = 0; i < schoolArr.length; i++) {
      Integer schoolId = Integer.parseInt(schoolArr[i]);
      ermSchoolFunded.setSchoolId(schoolId);
      ErmSchoolFundedEntity ermSchoolFundedModel = this.ermSchoolFundedService
          .queryObjectByFundId(ermSchoolFunded.getFundedId(), ermSchoolFunded.getSchoolId());
      // 如果学校没分配过
      if (ermSchoolFundedModel == null) {
        ermSchoolFunded.setStatus(FundStatus.CONFIG.getCode());
        ermSchoolFundedService.save(ermSchoolFunded);
        // return R.error(500, "该学校已分配过此资助");
      }
      if (!ermSchFieldService.hasInited(schoolId))
        ermSchFieldService.doInitSchFieldsConfig(schoolId);
    }
    return R.ok();
  }

  /**
   * 批量设置国家资助
   */
  @ApiOperation(value = "批量设置国家资助", notes = "批量设置国家资助")
  @RequestMapping(value = "/batchUpdateFunded", method = RequestMethod.POST)
  @PreAuthorize("hasAnyRole('EB', 'OPER','SCH')")
  public R batchUpdateFunded(@RequestBody ErmSchoolFundedEntity ermSchoolFunded,
      JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    ermSchoolFunded.setCreator(user.getUsername());
    Map<String, Object> result = this.ermSchoolFundedService.batchUpdateFunded(ermSchoolFunded);
    return R.ok().put("resultData", result);
  }

  /**
   * 修改
   */
  @ApiOperation(value = "修改项目资助", notes = "修改项目资助")
  @ApiImplicitParam(name = "ErmSchoolFunded", value = "项目资助对象", required = true,
      dataType = "ErmSchoolFunded")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  @PreAuthorize("hasAnyRole('EB', 'OPER','SCH')")
  public R update(@RequestBody ErmSchoolFundedEntity schoolFundedEntity,
      JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    schoolFundedEntity.setUpdator(user.getUsername());
    ErmSchoolFundedEntity tmp = this.ermSchoolFundedService.queryObject(schoolFundedEntity.getId());
    if (tmp != null && (tmp.getStatus() == FundStatus.CONFIG.getCode()
        || tmp.getStatus() == FundStatus.RUNNING.getCode())) {
      schoolFundedEntity.setStatus(FundStatus.CONFIG.getCode());
      ermSchoolFundedService.update(schoolFundedEntity);
    } else if (tmp != null && tmp.getStatus() == FundStatus.CLOSE.getCode()
        && (null != schoolFundedEntity.getPublicUrl()
            && !schoolFundedEntity.getPublicUrl().isEmpty())) {
      ermSchoolFundedService.update(schoolFundedEntity);
    }
    return R.ok();
  }

  /**
   * 修改
   */
  @ApiOperation(value = "修改项目资助", notes = "修改项目资助")
  @ApiImplicitParam(name = "ErmSchoolFunded", value = "项目资助对象", required = true,
      dataType = "ErmSchoolFunded")
  @RequestMapping(value = "/updatePublicImg", method = RequestMethod.POST)
  public R updatePublicImg(@RequestBody ErmSchoolFundedEntity schoolFundedEntity,
      JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    schoolFundedEntity.setUpdator(user.getUsername());
    ermSchoolFundedService.update(schoolFundedEntity);
    return R.ok();
  }

  /**
   * 删除
   */
  @ApiOperation(value = "删除项目资助", notes = "删除项目资助")
  @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public R delete(@RequestBody Integer[] ids) {
    ermSchoolFundedService.deleteBatch(ids);

    return R.ok();
  }

  @ApiOperation(value = "导入分配资助名额信息", notes = "导入分配资助名额信息")
  @ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
  @RequestMapping(value = "importSchoolFundedInfo", method = RequestMethod.POST)
  public R importSchoolFundedInfo(@RequestParam(value = "file") MultipartFile file,
      JwtAuthenticationToken token, Integer fundedId) {
    UserContext user = (UserContext) token.getPrincipal();
    Integer ebId = user.getEbId();
    if (file.isEmpty()) {
      return R.error("上传文件不存在");
    }
    String fileName = file.getOriginalFilename();
    String fileSuffixName = fileName.substring(fileName.lastIndexOf("."));
    if (!".xls".equals(fileSuffixName) && !".xlsx".equals(fileSuffixName)) {
      return R.error("文件格式不正确");
    }
    Map<String, Object> importInfo = null;
    try {
      importInfo =
          ermSchoolFundedService.importSchoolFundedInfo(file.getInputStream(), ebId, fundedId);
    } catch (IOException e) {
      return R.error(e.getMessage());
    }
    return R.ok().put("resultData", importInfo);
  }

  @ApiOperation(value = "导出分配名额模板", notes = "导出分配名额模板")
  @RequestMapping(value = "exportSchoolFundedTemplate", method = RequestMethod.GET)
  public R exportSchoolFundedTemplate(HttpServletRequest request, HttpServletResponse response,
      @RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    params.put("ebId", user.getEbId());
    String fileName = "分配资助名额" + System.currentTimeMillis() + ".xls";
    this.ermSchoolFundedService.downLoadSchoolFundedTemplate(fileName, params);
    return R.ok().put("fileName", fileName);
  }


  @ApiOperation(value = "项目资助配置", notes = "项目资助配置")
  @RequestMapping(value = "/saveSchFundNew", method = RequestMethod.POST)
  @PreAuthorize("hasAnyRole('EB')")
  public R saveSchFundNew(@RequestBody ErmSchFundExt ermSchFundExt, JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    ermSchFundExt.setCreator(user.getUsername());
    ermSchFundExt.setUpdator(user.getUsername());
    boolean flag = this.ermSchoolFundedService.saveOrUpdateSchFundExt(ermSchFundExt);
    List<Integer> schIds = ermSchFundExt.getSchIdList();
    if (null != schIds && !schIds.isEmpty()) {
      for (Integer schoolId : schIds) {
        if (!ermSchFieldService.hasInited(schoolId))
          ermSchFieldService.doInitSchFieldsConfig(schoolId);
      }
    }
    if (flag)
      return R.ok().put("resultData", flag);
    else
      return R.error();
  }

}
