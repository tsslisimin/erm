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


import com.coomia.erm.entity.ErmEduburEntity;
import com.coomia.erm.service.ErmEduburService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 教育局信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:08
 */
@Api(value = "ErmEdubur-api", description = "有关于教育局信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermedubur")
public class ErmEduburController {
	@Autowired
	private ErmEduburService ermEduburService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取教育局信息列表", notes = "获取教育局信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmEduburEntity> ermEduburList = ermEduburService.queryList(query);
		int total = ermEduburService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermEduburList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取教育局信息", notes = "根据ID获取教育局信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmEduburEntity ermEdubur = ermEduburService.queryObject(id);
		return R.ok().put("ermEdubur", ermEdubur);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增教育局信息", notes = "新增教育局信息")
    @ApiImplicitParam(name = "ErmEdubur", value = "教育局信息对象", required = true, dataType = "ErmEdubur")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmEduburEntity ermEdubur,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermEdubur.setCreator(user.getUsername());
		ermEduburService.save(ermEdubur);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改教育局信息", notes = "修改教育局信息")
    @ApiImplicitParam(name = "ErmEdubur", value = "教育局信息对象", required = true, dataType = "ErmEdubur")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmEduburEntity ermEdubur,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermEdubur.setUpdator(user.getUsername());
		ermEduburService.update(ermEdubur);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除教育局信息", notes = "删除教育局信息")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermEduburService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
