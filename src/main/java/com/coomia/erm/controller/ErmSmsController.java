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


import com.coomia.erm.entity.ErmSmsEntity;
import com.coomia.erm.service.ErmSmsService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 短信发送表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-23 21:21:48
 */
@Api(value = "ErmSms-api", description = "有关于短信发送表的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermsms")
public class ErmSmsController {
	@Autowired
	private ErmSmsService ermSmsService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取短信发送表列表", notes = "获取短信发送表列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmSmsEntity> ermSmsList = ermSmsService.queryList(query);
		int total = ermSmsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermSmsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取短信发送表", notes = "根据ID获取短信发送表")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmSmsEntity ermSms = ermSmsService.queryObject(id);
		return R.ok().put("ermSms", ermSms);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增短信发送表", notes = "新增短信发送表")
    @ApiImplicitParam(name = "ErmSms", value = "短信发送表对象", required = true, dataType = "ErmSms")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmSmsEntity ermSms,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermSmsService.save(ermSms);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改短信发送表", notes = "修改短信发送表")
    @ApiImplicitParam(name = "ErmSms", value = "短信发送表对象", required = true, dataType = "ErmSms")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmSmsEntity ermSms,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermSmsService.update(ermSms);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除短信发送表", notes = "删除短信发送表")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermSmsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
