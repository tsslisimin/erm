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


import com.coomia.erm.entity.ErmAuditLogEntity;
import com.coomia.erm.service.ErmAuditLogService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 学生资助审批日志表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:08
 */
@Api(value = "ErmAuditLog-api", description = "有关于学生资助审批日志表的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermauditlog")
public class ErmAuditLogController {
	@Autowired
	private ErmAuditLogService ermAuditLogService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取学生资助审批日志表列表", notes = "获取学生资助审批日志表列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmAuditLogEntity> ermAuditLogList = ermAuditLogService.queryList(query);
		int total = ermAuditLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermAuditLogList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取学生资助审批日志表", notes = "根据ID获取学生资助审批日志表")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmAuditLogEntity ermAuditLog = ermAuditLogService.queryObject(id);
		return R.ok().put("ermAuditLog", ermAuditLog);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增学生资助审批日志表", notes = "新增学生资助审批日志表")
    @ApiImplicitParam(name = "ErmAuditLog", value = "学生资助审批日志表对象", required = true, dataType = "ErmAuditLog")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmAuditLogEntity ermAuditLog,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermAuditLog.setCreator(user.getUsername());
		ermAuditLogService.save(ermAuditLog);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改学生资助审批日志表", notes = "修改学生资助审批日志表")
    @ApiImplicitParam(name = "ErmAuditLog", value = "学生资助审批日志表对象", required = true, dataType = "ErmAuditLog")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmAuditLogEntity ermAuditLog,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermAuditLog.setUpdator(user.getUsername());
		ermAuditLogService.update(ermAuditLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除学生资助审批日志表", notes = "删除学生资助审批日志表")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermAuditLogService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
