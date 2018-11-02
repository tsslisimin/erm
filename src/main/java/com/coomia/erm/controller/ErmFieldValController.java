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

import com.coomia.erm.entity.ErmFieldValEntity;
import com.coomia.erm.service.ErmFieldValService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 指标值
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:53
 */
@Api(value = "ErmFieldVal-api", description = "有关于指标值的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermfieldval")
public class ErmFieldValController {
	@Autowired
	private ErmFieldValService ermFieldValService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取指标值列表", notes = "获取指标值列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmFieldValEntity> ermFieldValList = ermFieldValService.queryList(query);
		int total = ermFieldValService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermFieldValList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取指标值", notes = "根据ID获取指标值")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmFieldValEntity ermFieldVal = ermFieldValService.queryObject(id);
		return R.ok().put("ermFieldVal", ermFieldVal);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增指标值", notes = "新增指标值")
    @ApiImplicitParam(name = "ErmFieldVal", value = "指标值对象", required = true, dataType = "ErmFieldVal")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmFieldValEntity ermFieldVal){
		ermFieldValService.save(ermFieldVal);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改指标值", notes = "修改指标值")
    @ApiImplicitParam(name = "ErmFieldVal", value = "指标值对象", required = true, dataType = "ErmFieldVal")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmFieldValEntity ermFieldVal){
		ermFieldValService.update(ermFieldVal);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除指标值", notes = "删除指标值")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermFieldValService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
