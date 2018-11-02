package com.coomia.erm.controller;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmFamilyEntity;
import com.coomia.erm.entity.TbErmLog;
import com.coomia.erm.service.ErmFamilyService;
import com.coomia.erm.service.ErmLogService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 有关于日志信息的操作
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:07
 */
@Api(value = "Ermlog-api", description = "有关于日志信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/logs")
public class ErmLogController {
	@Autowired
	private ErmLogService ermLogService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取日志信息列表", notes = "获取日志信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		params.put("userId",user.getUserId());
		//查询列表数据
        Query query = new Query(params);

		List<TbErmLog> tbErmLogs = ermLogService.queryList(query);
		int total = ermLogService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tbErmLogs, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取日志信息", notes = "根据ID获取日志信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Long id){
		TbErmLog tbErmLog = ermLogService.queryObject(id);
		return R.ok().put("tbErmLog", tbErmLog);
	}
	

	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除家庭信息", notes = "删除家庭信息")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Long[] ids){
		ermLogService.deleteBatch(ids);
		return R.ok();
	}
	
}
