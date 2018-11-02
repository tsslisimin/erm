package com.coomia.erm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.SystemConstants;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmFunedNoticeEntity;
import com.coomia.erm.entity.ErmToDoItem;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.entity.UserType;
import com.coomia.erm.entity.excel.ErmAdminExcelEntity;
import com.coomia.erm.service.ErmAdminService;
import com.coomia.erm.service.ErmFunedNoticeService;
import com.coomia.erm.service.UserService;
import com.coomia.erm.util.ExcelUtil;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 管理员信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:09
 */
@Api(value = "ErmAdmin-api", description = "有关于管理员信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermadmin")
public class ErmAdminController {
	@Autowired
	private ErmAdminService ermAdminService;

	@Autowired
	private UserService userService;

	@Autowired
	private ErmFunedNoticeService ermFunedNoticeService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取管理员信息列表", notes = "获取管理员信息列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		params.put("ebId", SystemConstants.ebId);
		Query query = new Query(params);

		List<ErmAdminEntity> ermAdminList = ermAdminService.queryList(query);
		int total = ermAdminService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(ermAdminList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取管理员信息", notes = "根据ID获取管理员信息")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id) {
		ErmAdminEntity ermAdmin = ermAdminService.queryObject(id);
		return R.ok().put("ermAdmin", ermAdmin);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "新增管理员信息", notes = "新增管理员信息")
	@ApiImplicitParam(name = "ErmAdmin", value = "管理员信息对象", required = true, dataType = "ErmAdmin")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody ErmAdminEntity ermAdmin, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		ermAdmin.setCreator(user.getUsername());
		ermAdminService.save(ermAdmin);
		return R.ok();
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "批量新增管理员信息", notes = "批量新增管理员信息")
	@ApiImplicitParam(name = "ErmAdmin", value = "批量新增管理员信息", required = true, dataType = "list")
	@RequestMapping(value = "/saveAdmins", method = RequestMethod.POST)
	public R saveAdmins(@RequestBody List<ErmAdminEntity> ermAdmins, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		if (ermAdmins != null && ermAdmins.size() > 0) {
			for (ErmAdminEntity ermAdmin : ermAdmins) {
				ermAdmin.setCreator(user.getUsername());
				ermAdminService.save(ermAdmin);
			}
		}
		return R.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改管理员信息", notes = "修改管理员信息")
	@ApiImplicitParam(name = "ErmAdmin", value = "管理员信息对象", required = true, dataType = "ErmAdmin")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public R update(@RequestBody ErmAdminEntity ermAdmin, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
	//	ermAdmin.setId(user.getUserId());
		ermAdmin.setUpdator(user.getUsername());
		ermAdminService.update(ermAdmin);
		return R.ok();
	}
	/**
	 * 修改
	 */
	@ApiOperation(value = "修改管理员信息", notes = "修改管理员信息")
	@ApiImplicitParam(name = "ErmAdmin", value = "管理员信息对象", required = true, dataType = "ErmAdmin")
	@RequestMapping(value = "/updatePassWord", method = RequestMethod.POST)
	public R updatePassWord(@RequestBody ErmAdminEntity ermAdmin, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		ermAdmin.setId(user.getUserId());
		ermAdmin.setUpdator(user.getUsername());
		ermAdminService.update(ermAdmin);
		return R.ok();
	}
	/**
	 * 删除
	 */
	@ApiOperation(value = "删除管理员信息", notes = "删除管理员信息")
	@ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids) {
		ermAdminService.deleteBatch(ids);

		return R.ok();
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取我的通知信息列表", notes = "获取我的通知信息列表")
	@RequestMapping(value = "/notices/to/me", method = RequestMethod.GET)
	public R noticesToMe(JwtAuthenticationToken token) {
		List<ErmFunedNoticeEntity> notices = ermFunedNoticeService.queryNoticesToUser(token.getName());
		return R.ok().put("notices", notices);
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取我创建的通知信息列表", notes = "获取我创建的通知信息列表")
	@RequestMapping(value = "/notices/from/me", method = RequestMethod.GET)
	public R noticesFromMe(JwtAuthenticationToken token) {
		List<ErmFunedNoticeEntity> notices = ermFunedNoticeService.queryNoticesByCreator(token.getName());
		return R.ok().put("notices", notices);
	}

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取我的待办信息列表", notes = "获取我的待办信息列表")
	@RequestMapping(value = "/todo/to/me", method = RequestMethod.GET)
	public R toDoListToMe(JwtAuthenticationToken token) {
		// TODO
		int status = FundStatus.APPLY.getCode();
		ErmAdminEntity admin = userService.getByUser(token.getName());
		int roleId = admin.getRoleId();
		if (roleId == UserType.OPER.getCode())
			status = FundStatus.OPERAUDIT.getCode();
		else if (roleId == UserType.SCH.getCode())
			status = FundStatus.SCHAUDIT.getCode();
		else if (roleId == UserType.EB.getCode())
			status = FundStatus.EBAUDIT.getCode();
		else
			status = FundStatus.CARDCOMPARE.getCode();
		List<ErmToDoItem> todos = new ArrayList<ErmToDoItem>();
		return R.ok().put("todos", todos);
	}

	@ApiOperation(value = "导出账号信息", notes = "导出账号信息")
	@RequestMapping(value = "exportAdminInfo", method = RequestMethod.POST)
	public void exportSchoolInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("ebId", user.getEbId());
		Query query = new Query(params);
		List<ErmAdminEntity> ermAdminList = ermAdminService.queryList(query);
		List<ErmAdminExcelEntity> adminExcelList = ExcelUtil.convertAdminExcelList(ermAdminList);
		String fileName = "账号信息" + System.currentTimeMillis() + ".xls";
		ExcelUtil.downLoadAdminExcelForVO(request, response, fileName, adminExcelList);
	} 
	
	@ApiOperation(value = "自动生成学校账号", notes = "自动生成学校账号")
	@RequestMapping(value = "exportCreateSchoolAdmin", method = RequestMethod.POST)
	public void createSchoolAdmin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("ebId", user.getEbId());
		Query query = new Query(params);
		List<ErmAdminEntity> ermAdminList = ermAdminService.createSchoolAdmin(query);
		List<ErmAdminExcelEntity> adminExcelList = ExcelUtil.convertAdminExcelList(ermAdminList);
		String fileName = "账号信息" + System.currentTimeMillis() + ".xls";
		ExcelUtil.downLoadAdminExcelForVO(request, response, fileName, adminExcelList);
	} 
	
	@ApiOperation(value = "修改密码", notes = "修改密码号")
	@RequestMapping(value = "updateAdminPassword", method = RequestMethod.POST)
	public void updateAdminPassword(@RequestBody Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("id", user.getUserId());
		params.put("username", user.getUsername());
		
	} 

}
