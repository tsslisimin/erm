package com.coomia.erm.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.TbErmLog;
import com.coomia.erm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmFieldEntity;
import com.coomia.erm.entity.ErmSchFieldEntity;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 学校指标配置 指标按学校统一配置的。 项目中统一使用
 * 
 * @author spancer
 *
 */
@Api(value = "ErmSchoolKPIController", description = "有关于学校指标的操作", position = 5)
@RestController
@RequestMapping("/erm/api/schoolKpi")
public class ErmSchoolKPIController {

  @Autowired
  private ErmSchFieldService ermSchFieldService;

  @Autowired
  private ErmFieldService ermFieldService;

  @Autowired
  private ErmStuValueService ermStuValueService;
  @Autowired
  private ErmStudentService ermStudentService;
  @Autowired
  private ErmLogService ermLogService;

  /**
   * 列表
   */
  @ApiOperation(value = "获取学校指标列表", notes = "获取学校指标列表")
  @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
    // 查询学校指标列表
    // 1. 构建查询参数(schoolId)
    UserContext user = (UserContext) token.getPrincipal();
    params.put("schId", user.getSchoolId());
    // 查询列表数据， 并没有加flag， 因此查询的是所有的指标，包括已经删除的指标（状态为0的）。
    Query query = new Query(params);

    List<ErmSchFieldEntity> ermSchfundFieldList = ermSchFieldService.queryList(query);
    int total = ermSchFieldService.queryTotal(query);

    PageUtils pageUtil =
        new PageUtils(ermSchfundFieldList, total, query.getLimit(), query.getPage());

    return R.ok().put("page", pageUtil);
  }


  /**
   * 新增
   */
  @ApiOperation(value = "新增一个学校指标及指标项", notes = "新增一个学校指标及指标项")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public R save(@RequestBody ErmFieldEntity field, JwtAuthenticationToken token) {
    // 查询学校指标列表
    // 1. 构建查询参数(schoolId)
    UserContext user = (UserContext) token.getPrincipal();
    boolean flag = ermSchFieldService.doAddSchField(user.getSchoolId(), field);
    // 2.一旦指标修改了, 需要改变学生表里的flag字段的值为1
    ermStudentService.doSubscrib(user.getSchoolId());
    if (flag){
      return R.ok("添加成功");}
    else{

      TbErmLog tbErmLog=new TbErmLog();
      tbErmLog.setTitle("新增一个学校指标及指标项异常");
      tbErmLog.setCreateId(user.getUserId());
      tbErmLog.setMethod("importStudentPicInfo");
      tbErmLog.setServiceId("importStudentPicInfo");
      tbErmLog.setMsg( "新增一个学校指标及指标项异常"+field.getFieldName());
      tbErmLog.setCreateTime(new Date());
      ermLogService.save(tbErmLog);
      return R.error("添加失败");}
  }


  /**
   * 修改
   */
  @ApiOperation(value = "修改学校的指标定义", notes = "修改学校指标定义")
  @ApiImplicitParam(name = "ErmFieldEntity", value = "指标定义对象", required = true,
      dataType = "ErmFieldEntity")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public R update(@RequestBody ErmSchFieldEntity field, JwtAuthenticationToken token) {
    ermSchFieldService.update(field);
    UserContext user = (UserContext) token.getPrincipal();
    // 2.一旦指标修改了, 需要改变学生表里的flag字段的值为1
    ermStudentService.doSubscrib(user.getSchoolId());
    return R.ok();
  }

  /**
   * 删除
   */
  @ApiOperation(value = "删除指标定义", notes = "删除指标定义")
  @ApiImplicitParam(name = "ids", value = "id数组", required = true, dataType = "array")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public R delete(@RequestBody Integer[] ids) {
    ermSchFieldService.deleteBatch(ids);
    return R.ok();
  }

  /**
   * 启用
   */
  @ApiOperation(value = "启用指标", notes = "启用指标")
  @ApiImplicitParam(name = "ids", value = "id数组", required = true, dataType = "array")
  @RequestMapping(value = "/active", method = RequestMethod.POST)
  public R active(@RequestBody Integer[] ids) {
    ermSchFieldService.activeBatch(ids);
    return R.ok();
  }

  /**
   * 列表
   */
  @ApiOperation(value = "获取指标", notes = "获取学校指标")
  @RequestMapping(value = "/forUpdate", method = RequestMethod.GET)
  public R forUpdate(Integer id, JwtAuthenticationToken token) {
    ErmSchFieldEntity fieldEntity = ermSchFieldService.queryObject(id);
    return R.ok().put("ermSchFieldEntity", fieldEntity);
  }


}
