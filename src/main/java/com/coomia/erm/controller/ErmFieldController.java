package com.coomia.erm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmFieldEntity;
import com.coomia.erm.service.ErmFieldService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;



/**
 * 指标定义
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
@Api(value = "ErmField-api", description = "有关于指标定义的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermfield")
public class ErmFieldController {
  @Autowired
  private ErmFieldService ermFieldService;
  @Autowired
  ErmSchoolFundedService ermSchoolFundedService;

  /**
   * 列表
   */
  @ApiOperation(value = "获取指标定义列表", notes = "获取指标定义列表")
  @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
    UserContext user = (UserContext) token.getPrincipal();
    params.put("schId", user.getSchoolId());
    // 查询列表数据
    Query query = new Query(params);

    List<ErmFieldEntity> ermFieldList = ermFieldService.queryList(query);
    int total = ermFieldService.queryTotal(query);

    PageUtils pageUtil = new PageUtils(ermFieldList, total, query.getLimit(), query.getPage());

    return R.ok().put("page", pageUtil);
  }


  /**
   * 信息
   */
  @ApiOperation(value = "根据ID获取指标定义", notes = "根据ID获取指标定义")
  @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
  @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
  public R info(@PathVariable("id") Integer id) {
    ErmFieldEntity ermField = ermFieldService.queryObject(id);
    return R.ok().put("ermField", ermField);
  }

  /**
   * 保存
   */
  @ApiOperation(value = "新增指标定义", notes = "新增指标定义")
  @ApiImplicitParam(name = "ErmField", value = "指标定义对象", required = true, dataType = "ErmField")
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public R save(@RequestBody ErmFieldEntity ermField, JwtAuthenticationToken token) {
    if (null == ermField.getFieldName() || ermField.getFieldName().isEmpty())
      return R.error("指标名称不能为空！");
    ermFieldService.save(ermField);
    return R.ok().put("fieldId", ermField.getId());

  }

  /**
   * 修改
   */
  @ApiOperation(value = "修改指标定义", notes = "修改指标定义")
  @ApiImplicitParam(name = "ErmField", value = "指标定义对象", required = true, dataType = "ErmField")
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public R update(@RequestBody ErmFieldEntity ermField) {
    ermFieldService.update(ermField);
    return R.ok();
  }

  /**
   * 删除
   */
  @ApiOperation(value = "删除指标定义", notes = "删除指标定义")
  @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public R delete(@RequestBody Integer[] ids) {
    ermFieldService.deleteBatch(ids);

    return R.ok();
  }

}
