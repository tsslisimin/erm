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

import com.coomia.erm.entity.ErmStuValueEntity;
import com.coomia.erm.service.ErmStuValueService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 学生指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
@Api(value = "ErmStuValue-api", description = "有关于学生指标值的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermstuvalue")
public class ErmStuValueController {
	@Autowired
	private ErmStuValueService ermStuValueService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取学生指标值列表", notes = "获取学生指标值列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmStuValueEntity> ermStuValueList = ermStuValueService.queryList(query);
		int total = ermStuValueService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermStuValueList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取学生指标值", notes = "根据ID获取学生指标值")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmStuValueEntity ermStuValue = ermStuValueService.queryObject(id);
		return R.ok().put("ermStuValue", ermStuValue);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增学生指标值", notes = "新增学生指标值")
    @ApiImplicitParam(name = "ErmStuValue", value = "学生指标值对象", required = true, dataType = "ErmStuValue")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmStuValueEntity ermStuValue){
		ermStuValueService.save(ermStuValue);
		return R.ok();
	}
	
	/**
	 * 批量保存
	 */
	@ApiOperation(value = "批量新增学生指标值", notes = "批量新增学生指标值")
	@ApiImplicitParam(name = "ErmStuValue", value = "学生指标值对象", required = true, dataType = "ErmStuValue")
	@RequestMapping(value = "/batchSave",method = RequestMethod.POST)
	public R batchSave(@RequestBody List<ErmStuValueEntity> ermStuValues){
		if(ermStuValues != null && ermStuValues.size()>0) {
			for(int i=0;i<ermStuValues.size();i++) {
				ErmStuValueEntity ermStuValue = ermStuValues.get(i);
				ermStuValueService.save(ermStuValue);
			}
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改学生指标值", notes = "修改学生指标值")
    @ApiImplicitParam(name = "ErmStuValue", value = "学生指标值对象", required = true, dataType = "ErmStuValue")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmStuValueEntity ermStuValue){
		ermStuValueService.update(ermStuValue);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除学生指标值", notes = "删除学生指标值")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermStuValueService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
