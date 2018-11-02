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


import com.coomia.erm.entity.ErmReportSubEntity;
import com.coomia.erm.service.ErmReportSubService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 报表订阅
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-17 00:14:21
 */
@Api(value = "ErmReportSub-api", description = "有关于报表订阅的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermreportsub")
public class ErmReportSubController {
	@Autowired
	private ErmReportSubService ermReportSubService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取报表订阅列表", notes = "获取报表订阅列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmReportSubEntity> ermReportSubList = ermReportSubService.queryList(query);
		int total = ermReportSubService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermReportSubList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取报表订阅", notes = "根据ID获取报表订阅")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmReportSubEntity ermReportSub = ermReportSubService.queryObject(id);
		return R.ok().put("ermReportSub", ermReportSub);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增报表订阅", notes = "新增报表订阅")
    @ApiImplicitParam(name = "ErmReportSub", value = "报表订阅对象", required = true, dataType = "ErmReportSub")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmReportSubEntity ermReportSub,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermReportSubService.save(ermReportSub);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改报表订阅", notes = "修改报表订阅")
    @ApiImplicitParam(name = "ErmReportSub", value = "报表订阅对象", required = true, dataType = "ErmReportSub")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmReportSubEntity ermReportSub,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermReportSubService.update(ermReportSub);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除报表订阅", notes = "删除报表订阅")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermReportSubService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
