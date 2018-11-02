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


import com.coomia.erm.entity.ErmAdminNoticeEntity;
import com.coomia.erm.service.ErmAdminNoticeService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 用户消息订阅表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:09
 */
@Api(value = "ErmAdminNotice-api", description = "有关于用户消息订阅表的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermadminnotice")
public class ErmAdminNoticeController {
	@Autowired
	private ErmAdminNoticeService ermAdminNoticeService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取用户消息订阅表列表", notes = "获取用户消息订阅表列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmAdminNoticeEntity> ermAdminNoticeList = ermAdminNoticeService.queryList(query);
		int total = ermAdminNoticeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermAdminNoticeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取用户消息订阅表", notes = "根据ID获取用户消息订阅表")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmAdminNoticeEntity ermAdminNotice = ermAdminNoticeService.queryObject(id);
		return R.ok().put("ermAdminNotice", ermAdminNotice);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增用户消息订阅表", notes = "新增用户消息订阅表")
    @ApiImplicitParam(name = "ErmAdminNotice", value = "用户消息订阅表对象", required = true, dataType = "ErmAdminNotice")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmAdminNoticeEntity ermAdminNotice,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermAdminNotice.setCreator(user.getUsername());
		ermAdminNoticeService.save(ermAdminNotice);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改用户消息订阅表", notes = "修改用户消息订阅表")
    @ApiImplicitParam(name = "ErmAdminNotice", value = "用户消息订阅表对象", required = true, dataType = "ErmAdminNotice")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmAdminNoticeEntity ermAdminNotice,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermAdminNotice.setUpdator(user.getUsername());
		ermAdminNoticeService.update(ermAdminNotice);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除用户消息订阅表", notes = "删除用户消息订阅表")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermAdminNoticeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
