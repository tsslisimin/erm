package com.coomia.erm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmApplyEntity;
import com.coomia.erm.entity.ErmSchFieldEntity;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.service.ErmFamilyService;
import com.coomia.erm.service.ErmSchFieldService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.service.ErmStudentService;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 学校指标
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
@Api(value = "ErmSchfundField-api", description = "有关于学校指标的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermH5StudentInfo")
public class ErmH5StudentInfoController {
	@Autowired
	private ErmSchFieldService ermSchFieldService;

	@Autowired
	private ErmStudentService ermStudentService;

	@Autowired
	private ErmSchoolFundedService ermSchoolFundedService;

	@Autowired
	private ErmFamilyService familyService;

	// @Autowired
	// private ErmSmsService ermSmsService;

	/**
	 *  *page=1&limit=10&name=&gender=&grade=&clazz=&headTeacheCheck=1&idcard=
             以及schFundId
	 * list:(这里用一句话描述这个方法的作用). 
	 * TODO(这里描述这个方法适用条件 – 可选).
	 * TODO(这里描述这个方法的执行流程 – 可选).
	 * TODO(这里描述这个方法的使用方法 – 可选).
	 * TODO(这里描述这个方法的注意事项 – 可选).
	 *
	 * @author spancer
	 * @param params
	 * @param token
	 * @return
	 * @since JDK 1.6
	 */
	@ApiOperation(value = "获取学校指标列表", notes = "获取学校指标列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
      //查询学校指标列表
      //1. 构建查询参数(schoolId)
      UserContext user = (UserContext) token.getPrincipal();
      params.put("schId", user.getSchoolId());
		// 查询列表数据
		Query query = new Query(params);
		List<ErmSchFieldEntity> ermSchfundFieldList = ermSchFieldService.queryList(query);
		int total = ermSchFieldService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(ermSchfundFieldList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "新增学生信息", notes = "新增学生信息")
	@ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody ErmStudentEntity ermStudent, Integer schId) {
		// 验证验证码是否存在
		/*
		 * ErmSmsEntity sms = this.ermSmsService.queryObjectByCode(telephone, code); if
		 * (sms == null) { return R.error("验证码错误"); }
		 */
		// if (ermStudent.getSchoolFundedId() == null) {
		// return R.error("学校项目不存在");
		// }
		if (schId != null) {
			ermStudent.setSchoolId(schId);
		}
		schId = ermStudent.getSchoolId();
		ErmStudentEntity student = this.ermStudentService.queryStudentByIdCard(schId, null, ermStudent.getIdcard());
		if(student == null) {
			return R.error("该学生不存在");
		}
		ermStudentService.save(ermStudent, ermStudent.getSchoolFundedId());
		return R.ok();
	}
	
	@ApiOperation(value = "查询学校指标", notes = "查询学校指标")
	@ApiImplicitParam(name = "schoolId", value = "学校Id", required = true, dataType = "Map")
	@RequestMapping(value = "/querySchField", method = RequestMethod.GET)
	public R querySchField(Integer schId) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("schId", schId);
		param.put("flag", 1);
		List<ErmSchFieldEntity> schField = this.ermSchFieldService.queryList(param);
		return R.ok().put("page", schField);
	}

	@ApiOperation(value = "新增申请信息", notes = "新增申请信息")
	@ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
	@RequestMapping(value = "/saveApply", method = RequestMethod.POST)
	public R saveApply(@RequestBody ErmApplyEntity apply) {
		if (apply == null) {
			return R.error("对象不存在");
		}
		// 保存申请表内容
		this.familyService.saveApplyObj(apply);
		return R.ok();
	}

	@ApiOperation(value = "根据班级获取待审批的学生", notes = "根据班级获取待审批的学生")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/queryByClazz", method = RequestMethod.GET)
	public R queryByClazz(String clazz,String grade) {
		Map<String,Object> params = Maps.newHashMap();
		params.put("clazz", clazz);
		params.put("grade", grade);
		params.put("headTeacheCheck", 0);//班主任确认，0：未确认，1：通过，2：不通过
		List<ErmStudentEntity> ermStudents = ermStudentService.queryList(params);
		return R.ok().put("ermStudents", ermStudents);
	}
	
	@ApiOperation(value = "根据学生id获取学生信息", notes = "根据学生id获取学生信息")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/getStuInfoByStuId", method = RequestMethod.GET)
	public R getStuInfoByStuId(Integer stuId) {
		Map<String,Object> ermStudentApplyInfo = ermStudentService.queryStudentApplyInfoById(stuId,null,null);
		return R.ok().put("ermStudentApplyInfo", ermStudentApplyInfo);
	}
	
	@ApiOperation(value = "班主任认定", notes = "班主任认定")
	@RequestMapping(value = "/headTeachConfirm", method = {RequestMethod.POST,RequestMethod.GET})
	public R headTeachConfirm(@RequestBody Map<String, Object> params) {
	    Integer stuId = Integer.parseInt(""+params.get("stuId")); 
	    String headTeachName = params.get("headTeachName").toString();
	    Integer isOk =Integer.parseInt(""+params.get("isOk")); 
		Map<String,Object> headTeachConfirmResult = ermStudentService.headTeachConfirm(stuId,headTeachName,isOk);
		return R.ok().put("headTeachConfirmResult", headTeachConfirmResult);
	}

}
