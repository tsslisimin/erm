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


import com.coomia.erm.entity.ErmFamilyEntity;
import com.coomia.erm.service.ErmFamilyService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 家庭信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:07
 */
@Api(value = "ErmFamily-api", description = "有关于家庭信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermfamily")
public class ErmFamilyController {
	@Autowired
	private ErmFamilyService ermFamilyService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取家庭信息列表", notes = "获取家庭信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmFamilyEntity> ermFamilyList = ermFamilyService.queryList(query);
		int total = ermFamilyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermFamilyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取家庭信息", notes = "根据ID获取家庭信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmFamilyEntity ermFamily = ermFamilyService.queryObject(id);
		return R.ok().put("ermFamily", ermFamily);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增家庭信息", notes = "新增家庭信息")
    @ApiImplicitParam(name = "ErmFamily", value = "家庭信息对象", required = true, dataType = "ErmFamily")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmFamilyEntity ermFamily,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermFamily.setCreator(user.getUsername());
		ermFamilyService.save(ermFamily);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改家庭信息", notes = "修改家庭信息")
    @ApiImplicitParam(name = "ErmFamily", value = "家庭信息对象", required = true, dataType = "ErmFamily")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmFamilyEntity ermFamily,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermFamily.setUpdator(user.getUsername());
		ermFamilyService.update(ermFamily);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除家庭信息", notes = "删除家庭信息")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermFamilyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
