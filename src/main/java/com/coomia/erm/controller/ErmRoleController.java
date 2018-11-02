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

import com.coomia.erm.entity.ErmRoleEntity;
import com.coomia.erm.service.ErmRoleService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 角色信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@Api(value = "ErmRole-api", description = "有关于角色信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermrole")
public class ErmRoleController {
	@Autowired
	private ErmRoleService ermRoleService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取角色信息列表", notes = "获取角色信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ErmRoleEntity> ermRoleList = ermRoleService.queryList(query);
		int total = ermRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermRoleList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取角色信息", notes = "根据ID获取角色信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmRoleEntity ermRole = ermRoleService.queryObject(id);
		
		return R.ok().put("ermRole", ermRole);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    @ApiImplicitParam(name = "ErmRole", value = "角色信息对象", required = true, dataType = "ErmRole")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmRoleEntity ermRole){
		ermRoleService.save(ermRole);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改角色信息", notes = "修改角色信息")
    @ApiImplicitParam(name = "ErmRole", value = "角色信息对象", required = true, dataType = "ErmRole")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmRoleEntity ermRole){
		ermRoleService.update(ermRole);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除角色信息", notes = "删除角色信息")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermRoleService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
