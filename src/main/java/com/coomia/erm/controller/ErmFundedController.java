package com.coomia.erm.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.TbErmLog;
import com.coomia.erm.service.ErmLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmFundedService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.SystemConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;




/**
 * 资助信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:07
 */
@Api(value = "ErmFunded-api", description = "有关于资助信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermfunded")
public class ErmFundedController {
	@Autowired
	private ErmFundedService ermFundedService;
	@Autowired
	private ErmLogService ermLogService;
	
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取资助信息列表", notes = "获取资助信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        query.put("ebId", SystemConstants.ebId);
		List<ErmFundedEntity> ermFundedList = ermFundedService.queryList(query);
		int total = ermFundedService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermFundedList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	/**
	 * 列表
	 */
	@ApiOperation(value = "获取学校资助信息列表", notes = "获取学校资助信息列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/schoolFundedlist",method = RequestMethod.GET)
	public R querySchoolFundedlist(@RequestParam Map<String, Object> params,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		//教育局传学校id
		if(params.get("schoolId") == null) {
			params.put("schoolId", user.getSchoolId());
		}
		params.put("ebId", user.getEbId());
		//查询列表数据
		Query query = new Query(params);
		
		List<ErmFundedEntity> ermFundedList = ermFundedService.queryListBySchoolId(query);
		int total = ermFundedService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(ermFundedList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取资助信息", notes = "根据ID获取资助信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id){
		ErmFundedEntity ermFunded = ermFundedService.queryObject(id);
		return R.ok().put("ermFunded", ermFunded);
	}
	
	/**
	 * 保存
	 */
	@ApiOperation(value = "新增资助信息", notes = "新增资助信息")
    @ApiImplicitParam(name = "ErmFunded", value = "资助信息对象", required = true, dataType = "ErmFunded")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public R save(@RequestBody ErmFundedEntity ermFunded,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermFunded.setCreator(user.getUsername());
		ermFunded.setStatus(FundStatus.NEW.getCode());
		boolean flag = ermFundedService.save(ermFunded);
		if(flag){
			return R.ok();
		}

		else{
			TbErmLog tbErmLog=new TbErmLog();
			tbErmLog.setTitle("新增资助信息异常");
			tbErmLog.setCreateId(user.getUserId());
			tbErmLog.setMethod("ermfunded");
			tbErmLog.setServiceId("ermfunded");
			tbErmLog.setMsg( "新增资助信息异常"+ermFunded.getDescrib());
			tbErmLog.setCreateTime(new Date());
			ermLogService.save(tbErmLog);

			return R.error("添加失败, 记录已存在！");
		}

	}


	/**
	 * 保存
	 */
	@ApiOperation(value = "新增资助信息", notes = "新增资助信息")
	@ApiImplicitParam(name = "ErmFunded", value = "资助信息对象", required = true, dataType = "ErmFunded")
	@RequestMapping(value = "/saveShcFund",method = RequestMethod.POST)
	public R saveShcFund(@RequestBody ErmFundedEntity ermFunded,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermFunded.setCreator(user.getUsername());
		ermFunded.setStatus(FundStatus.NEW.getCode());
		boolean flag = ermFundedService.saveShcFund(ermFunded,user);
		if(flag){
			return R.ok();
		}

		else{
			TbErmLog tbErmLog=new TbErmLog();
			tbErmLog.setTitle("新增资助信息异常");
			tbErmLog.setCreateId(user.getUserId());
			tbErmLog.setMethod("ermfunded");
			tbErmLog.setServiceId("ermfunded");
			tbErmLog.setMsg( "新增资助信息异常"+ermFunded.getDescrib());
			tbErmLog.setCreateTime(new Date());
			ermLogService.save(tbErmLog);

			return R.error("添加失败, 记录已存在！");
		}

	}

	
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改资助信息", notes = "修改资助信息")
    @ApiImplicitParam(name = "ErmFunded", value = "资助信息对象", required = true, dataType = "ErmFunded")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public R update(@RequestBody ErmFundedEntity ermFunded,JwtAuthenticationToken token){
		UserContext user = (UserContext) token.getPrincipal();
		ermFunded.setUpdator(user.getUsername());
		ermFundedService.update(ermFunded);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除资助信息", notes = "删除资助信息")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids){
		ermFundedService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
