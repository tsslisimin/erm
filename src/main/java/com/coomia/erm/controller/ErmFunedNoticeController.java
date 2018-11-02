package com.coomia.erm.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmEBNoticeParam;
import com.coomia.erm.entity.ErmFunedNoticeEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.entity.UserType;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.service.ErmFunedNoticeService;
import com.coomia.erm.service.UserService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 通知信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:06
 */
@Api(value = "ErmFunedNotice-api", description = "有关于通知信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermfunednotice")
public class ErmFunedNoticeController {
	@Autowired
	private ErmFunedNoticeService ermFunedNoticeService;

	@Autowired
	private UserService userService;

	@Autowired
	private ErmFundedInfoService ermFundedInfoService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取通知信息列表", notes = "获取通知信息列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("adminId", user.getUserId());
		ErmAdminEntity admin = userService.getByUser(user.getUsername());
		int roleId = admin.getRoleId();
		List<Integer> typex = new ArrayList<Integer>();
		if (roleId == UserType.EB.getCode()) {
			typex.add(1);
			params.put("typex", typex);
		} else if (roleId == UserType.SCH.getCode()) {
			typex.add(1);
			typex.add(3);
			params.put("typex", typex);
		} else if (roleId == UserType.OPER.getCode()) {
			typex.add(1);
			typex.add(3);
			typex.add(4);
			params.put("typex", typex);
		}
		// 查询列表数据
		Query query = new Query(params);

		List<ErmFunedNoticeEntity> ermFunedNoticeList = ermFunedNoticeService.queryList(query);
		int total = ermFunedNoticeService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(ermFunedNoticeList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	@ApiOperation(value = "获取通知信息列表", notes = "获取通知信息列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/myNotice", method = RequestMethod.GET)
	public R myNotice(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("adminId", user.getUserId());
		ErmAdminEntity admin = userService.getByUser(user.getUsername());
		int roleId = admin.getRoleId();
		Integer schId = null;
		int status = FundStatus.OPERAUDIT.getCode();

		List<Integer> typex = new ArrayList<Integer>();
		if (roleId == UserType.EB.getCode()) {
			typex.add(1);
			params.put("typex", typex);
			status = FundStatus.EBAUDIT.getCode();
		} else if (roleId == UserType.SCH.getCode()) {
			typex.add(1);
			typex.add(3);
			params.put("typex", typex);
			status = FundStatus.SCHAUDIT.getCode();
			schId = user.getSchoolId();
		} else if (roleId == UserType.OPER.getCode()) {
			typex.add(1);
			typex.add(3);
			typex.add(4);
			params.put("typex", typex);
			status = FundStatus.OPERAUDIT.getCode();
			schId = user.getSchoolId();
		}
		// 查询列表数据
		Query query = new Query(params);

		List<ErmFunedNoticeEntity> ermFunedNoticeList = ermFunedNoticeService.queryList(query);
		int total = ermFunedNoticeService.queryTotal(query);

		int toAuditCount = ermFundedInfoService.queryToAuditUserCount(Calendar.getInstance().get(Calendar.YEAR), status,
				schId, null);

		if (toAuditCount > 0) {
			ErmFunedNoticeEntity processNotice = new ErmFunedNoticeEntity();
			processNotice.setCreateTime(new Date());
			processNotice.setCreator("System");
			processNotice.setDescrib("您有" + toAuditCount + "名学生待审核");
			processNotice.setTitle("您有" + toAuditCount + "名学生待审核");
			processNotice.setType("7");
			ermFunedNoticeList.add(processNotice);
			total += 1;
		}

		PageUtils pageUtil = new PageUtils(ermFunedNoticeList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取通知信息", notes = "根据ID获取通知信息")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id) {
		ErmFunedNoticeEntity ermFunedNotice = ermFunedNoticeService.queryObject(id);
		return R.ok().put("ermFunedNotice", ermFunedNotice);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "新增通知信息", notes = "新增通知信息")
	@ApiImplicitParam(name = "ErmFunedNotice", value = "通知信息对象", required = true, dataType = "ErmFunedNotice")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody ErmFunedNoticeEntity ermFunedNotice, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		ermFunedNotice.setCreator(user.getUsername());
		ermFunedNoticeService.save(ermFunedNotice);
		return R.ok();
	}
	
	/**
     * 保存并将流程流转至下一步
     */
    @ApiOperation(value = "新增通知信息", notes = "新增通知信息")
    @ApiImplicitParam(name = "ErmEBNoticeParam", value = "通知信息对象", required = true, dataType = "ErmEBNoticeParam")
    @RequestMapping(value = "/saveExt", method = RequestMethod.POST)
    public R saveExt(@RequestBody ErmEBNoticeParam ermEBNoticeParam, JwtAuthenticationToken token) {
        boolean flag = ermFunedNoticeService.saveExt(ermEBNoticeParam);
        return R.ok().put("flag", flag);
    }

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改通知信息", notes = "修改通知信息")
	@ApiImplicitParam(name = "ErmFunedNotice", value = "通知信息对象", required = true, dataType = "ErmFunedNotice")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public R update(@RequestBody ErmFunedNoticeEntity ermFunedNotice, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		ermFunedNotice.setUpdator(user.getUsername());
		ermFunedNoticeService.update(ermFunedNotice);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除通知信息", notes = "删除通知信息")
	@ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids) {
		ermFunedNoticeService.deleteBatch(ids);

		return R.ok();
	}

}
