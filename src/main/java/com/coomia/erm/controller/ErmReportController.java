package com.coomia.erm.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.Constants;
import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmExportCloumn;
import com.coomia.erm.entity.ErmReportEntity;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.entity.UserType;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.service.ErmFundedService;
import com.coomia.erm.service.ErmReportService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.service.ErmStudentService;
import com.coomia.erm.service.UserService;
import com.coomia.erm.util.BusinessUtil;
import com.coomia.erm.util.ExcelUtil;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author leequn
 * @date: 2017年11月16日 下午2:27:57
 * @version 1.0
 * @since JDK 1.8
 */

@RestController
@RequestMapping(value = "/erm/api/ermreport")
public class ErmReportController {

	@Autowired
	private ErmFundedService ermFundedSevice;

	@Autowired
	private UserService userService;

	@Autowired
	private ErmStudentService ermStudentService;

	@Autowired
	private ErmFundedInfoService ermFundedInfoService;

	@Autowired
	private ErmReportService ermReportService;
	
	@Autowired
	private ErmSchoolFundedService ermSchoolFundedService;

	@ApiOperation(value = "报表首页-资助概况", notes = "报表首页-资助概况")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/reportOverview", method = RequestMethod.GET)
	public R reportOverview(JwtAuthenticationToken token, @RequestParam Map<String, Object> params) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("ebId", user.getEbId());
		params.put("schoolId", user.getSchoolId());
		// 查询列表数据
		Query query = new Query(params);

		Map<String, Object> reportOverview = ermFundedSevice.getReportOverview(query);
		return R.ok().put("overview", reportOverview);
	}

	@ApiOperation(value = "统计报表", notes = "统计报表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/totalReport", method = RequestMethod.GET)
	public R totalReport(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("schoolId", user.getSchoolId());
		// 查询列表数据
		Query query = new Query(params);

		List<Map<String, Object>> ermFunedInfoListMap = ermFundedSevice.queryTotalReportMapList(query);
		int total = ermFundedSevice.queryTotalReportMapListTotal(query);

		PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());

		return R.ok().put("header", BusinessUtil.getTableHeader(Constants.TOTAL_REPORT_KEYS,
				Constants.TOTAL_REPORT_NAMES, Constants.TOTAL_REPORT_VALIDS)).put("page", pageUtil);
	}

	@ApiOperation(value = "export", notes = "export")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/exportTotalReport", method = RequestMethod.POST)
	public void exportTotalReport(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response, JwtAuthenticationToken token) {
		if (token != null && params.get("schoolId") == null ) {
			UserContext user = (UserContext) token.getPrincipal();
			params.put("schoolId", user.getSchoolId());
		}
		// 默认当年
//		if (params.get("year") == null) {
//			params.put("year", Calendar.getInstance().get(Calendar.YEAR));
//		}
		String reportType = Constants.REPORT_TYPE_SHCOOL;
		if (params.get("reportType") != null) {
			reportType = (String) params.get("reportType");
		}
		// 查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> reportListMap = this.ermFundedInfoService.queryReportExportListMap(query, reportType);
		Map<String, Object> dynamicInfo = null;
		if (params.get("cloumnFields") != null) {
			String cloumnFields = (String) params.get("cloumnFields");
			dynamicInfo = this.ermFundedInfoService.getDynamicInfo(cloumnFields, reportListMap);
		}
		String studentType = (String) params.get("studentType");
		ExcelUtil.downLoadExcelForMap(request, response, reportListMap, reportType, dynamicInfo, studentType,null);
	}
	
	/**
	 * 导出学生公示信息
	 * @param params
	 * @param request
	 * @param response
	 * @param token
	 */
	@ApiOperation(value = "exportPublicStu", notes = "export")
	@RequestMapping(value = "/exportPublicStuInfo", method = RequestMethod.POST)
	public void exportPublicStuInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response, JwtAuthenticationToken token) {
		if (token != null && params.get("schoolId") == null ) {
			UserContext user = (UserContext) token.getPrincipal();
			params.put("schoolId", user.getSchoolId());
		}
		if (params.get("schFundId") == null) {
			return ;
		}
		Integer schFundId = (Integer) params.get("schFundId");
		ErmSchoolFundedEntity entity = this.ermSchoolFundedService.queryObject(schFundId);
		//第一次下载公示信息
		if(entity.getPublicDate() == null) {
//			entity.setPublicDate(new Date());
//			entity.setStatus(FundStatus.SCHANNOUNCEMENT.getCode());
			//公示不做真正的5天限制
			entity.setPublicDate(new Date());
			entity.setStatus(FundStatus.EBAUDIT.getCode());
			//修改公示状态为可提交
			entity.setPublicStatus(1);
			this.ermSchoolFundedService.update(entity);
		}
		String reportType = Constants.REPORT_TYPE_STUDENT;
		params.put("studentType", "diff");
		params.put("status", FundStatus.SCHANNOUNCEMENT.getCode());
		// 查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> reportListMap = this.ermFundedInfoService.queryReportExportListMap(query, reportType);
		Map<String, Object> dynamicInfo = null;
		if (params.get("cloumnFields") != null) {
			String cloumnFields = (String) params.get("cloumnFields");
			dynamicInfo = this.ermFundedInfoService.getDynamicInfo(cloumnFields, reportListMap);
		}
		String studentType = "publicStu";
		ExcelUtil.downLoadExcelForMap(request, response, reportListMap, reportType, dynamicInfo, studentType,null);
	}

	@ApiOperation(value = "统计报表-人员列表", notes = "统计报表-人员列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/fundedStudentReport", method = RequestMethod.GET)
	public R fundedStudentReport(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		String schoolIdStr = (String) params.get("schoolId");
		if (StringUtils.isBlank(schoolIdStr)) {
			params.put("schoolId", user.getSchoolId());
		}
		// 查询列表数据
		Query query = new Query(params);
		Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
		dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
		dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
		List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryFundedInfoMapList(query, dict);
		int total = ermFundedInfoService.queryFundedInfoMapListTotal(query);
		PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());
		String studentType = (String) params.get("studentType");
		return R.ok().put("header", getTableHeader(studentType)).put("page", pageUtil);
	}
	
	@ApiOperation(value = "获取学生自定义指标信息", notes = "获取学生自定义指标信息")
	@RequestMapping(value = "/fundedStudentById", method = RequestMethod.GET)
	public R fundedStudentById(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		String schoolIdStr = (String) params.get("schoolId");
		if (StringUtils.isBlank(schoolIdStr)) {
			params.put("schoolId", user.getSchoolId());
		}
		// 查询列表数据
		List<Map<String, Object>> ermFunedInfo = ermFundedInfoService.fundedStudentById(params);
		return R.ok().put("info",ermFunedInfo);
	}
	
	@ApiOperation(value = "获取学生自定义申请表图片", notes = "获取学生自定义申请表图片")
	@RequestMapping(value = "/fundedStudentImgById", method = RequestMethod.GET)
	public R fundedStudentImgById(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		String schoolIdStr = (String) params.get("schoolId");
		if (StringUtils.isBlank(schoolIdStr)) {
			params.put("schoolId", user.getSchoolId());
		}
		// 查询列表数据
		List<Map<String, Object>> ermFunedInfo = ermFundedInfoService.fundedStudentImgById(params);
		return R.ok().put("info",ermFunedInfo);
	}

	@ApiOperation(value = "统计报表-人员列表包括自定义字段", notes = "统计报表-员列表包括自定义字段")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/fundedStudentInfo", method = RequestMethod.GET)
	public R fundedStudentInfo(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		String schoolIdStr = (String) params.get("schoolId");
		if (StringUtils.isBlank(schoolIdStr)) {
			params.put("schoolId", user.getSchoolId());
		}
		// 查询列表数据
		Query query = new Query(params);
		Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
		dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
		dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
		query.put("extendField", "extend");
		query.put("studentType", "diff");
//		query.put("hasApplyImg", "0");
		String studentType = (String) params.get("studentType");
		
		List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryFundedInfoMapList(query, dict);
		List<ColumnHeader> diffStudentHeader = ermFundedInfoService.querydiffStudentHeader(getTableHeader(studentType),
		    schoolIdStr);
		int total = ermFundedInfoService.queryFundedInfoMapListTotal(query);
		PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());
		return R.ok().put("header", diffStudentHeader).put("page", pageUtil);
	}
	
	@ApiOperation(value = "统计报表-人员列表包括自定义字段", notes = "统计报表-员列表包括自定义字段")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/exportFundedStudentInfo", method = RequestMethod.POST)
	public void exportFundedStudentInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response, JwtAuthenticationToken token) {
			UserContext user = (UserContext) token.getPrincipal();
			params.put("schoolId", user.getSchoolId());
		// 默认当年
//		if (params.get("year") == null) {
//			params.put("year", Calendar.getInstance().get(Calendar.YEAR));
//		}
		String reportType = Constants.REPORT_TYPE_STUDENT;
		
		// 查询列表数据
		Query query = new Query(params);
		query.put("extendField", "extend");
		query.put("studentType", "diff");
		List<Map<String, Object>> reportListMap = this.ermFundedInfoService.queryReportExportListMap(query, reportType);
		Map<String, Object> dynamicInfo = null;
		if (params.get("cloumnFields") != null) {
			String cloumnFields = (String) params.get("cloumnFields");
			dynamicInfo = this.ermFundedInfoService.getDynamicInfo(cloumnFields, reportListMap);
		}
		String studentType = (String) query.get("studentType");
		List<ColumnHeader> diffStudentHeader = ermFundedInfoService.querydiffStudentHeader(null,
		    ""+user.getSchoolId());
		ExcelUtil.downLoadExcelForMap(request, response, reportListMap, reportType, dynamicInfo, studentType,diffStudentHeader);
	}

	/**
	 * 根据学生类型获取不同的动态表头
	 * 
	 * @param studentType
	 * @return
	 */
	private List<ColumnHeader> getTableHeader(String studentType) {
		if (StringUtils.isNotBlank(studentType)) {
			if ("base".equals(studentType)) {
				return BusinessUtil.getTableHeader(Constants.BASE_STUDENT_REPORT_KEYS,
						Constants.BASE_STUDENT_REPORT_NAMES, Constants.BASE_STUDENT_REPORT_VALIDS);
			} else if ("diff".equals(studentType)) {
				return BusinessUtil.getTableHeader(Constants.DIFF_STUDENT_REPORT_KEYS,
						Constants.DIFF_STUDENT_REPORT_NAMES, Constants.DIFF_STUDENT_REPORT_VALIDS);
			} else if ("funding".equals(studentType)) {
				return BusinessUtil.getTableHeader(Constants.FUNDING_STUDENT_REPORT_KEYS,
						Constants.FUNDING_STUDENT_REPORT_NAMES, Constants.FUNDING_STUDENT_REPORT_VALIDS);
			}
		}
		return BusinessUtil.getTableHeader(Constants.STUDENT_REPORT_KEYS, Constants.STUDENT_REPORT_NAMES,
				Constants.STUDENT_REPORT_VALIDS);
	}

	@ApiOperation(value = "统计报表-按资助学校统计", notes = "统计报表-按资助学校统计")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/fundedSchoolReport", method = RequestMethod.GET)
	public R fundedSchoolReport(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		if (params.get("schoolId") == null) {
			params.put("schoolId", user.getSchoolId());
		}
		if (params.get("year") == null) {
			params.put("year", Calendar.getInstance().get(Calendar.YEAR));
		}
		String groupByField = (String) params.get("groupByField");
		if (StringUtils.isBlank(groupByField)) {
			params.put("groupByField", "id");
		}
		// 查询列表数据
		Query query = new Query(params);
		Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
		dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
		List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryfundedSchoolReport(query, dict);
		int total = ermFundedInfoService.queryfundedSchoolReportTotal(query);

		PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());
		if ("type".equals(groupByField)) {
			return R.ok()
					.put("header",
							BusinessUtil.getTableHeader(Constants.SCHOOL_TYPE_REPORT_KEYS,
									Constants.SCHOOL_TYPE_REPORT_NAMES, Constants.SCHOOL_TYPE_REPORT_VALIDS))
					.put("page", pageUtil).put("echarts", this.ermFundedInfoService
							.getEchartsDataMap(ermFunedInfoListMap, "school_type", "sum_money"));
		}
		return R.ok().put("header", BusinessUtil.getTableHeader(Constants.SCHOOL_REPORT_KEYS,
				Constants.SCHOOL_REPORT_NAMES, Constants.SCHOOL_REPORT_VALIDS)).put("page", pageUtil);
	}

	@ApiOperation(value = "统计报表-按资助类型统计", notes = "统计报表-按资类型校统计")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/fundedTypeReport", method = RequestMethod.GET)
	public R fundedTypeReport(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("schoolId", user.getSchoolId());
//		if (params.get("year") == null) {
//			params.put("year", Calendar.getInstance().get(Calendar.YEAR));
//		}
		String groupByField = (String) params.get("groupByField");
		if (StringUtils.isBlank(groupByField)) {
			params.put("groupByField", "type");
		}
		// 查询列表数据
		Query query = new Query(params);
		Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
		dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
		List<Map<String, Object>> ermFunedInfoListMap = ermFundedInfoService.queryfundedTypeReport(query, dict);
		int total = ermFundedInfoService.queryfundedTypeReportTotal(query);

		PageUtils pageUtil = new PageUtils(ermFunedInfoListMap, total, query.getLimit(), query.getPage());
		if ("funded".equals(groupByField)) {
			return R.ok()
					.put("header",
							BusinessUtil.getTableHeader(Constants.FUNDED_REPORT_KEYS, Constants.FUNDED_REPORT_NAMES,
									Constants.FUNDED_REPORT_VALIDS))
					.put("page", pageUtil)
					.put("echart",
							this.ermFundedInfoService.getEchartsDataMap(ermFunedInfoListMap, "funded_type",
									"sum_money"))
					.put("echartPie", this.ermFundedInfoService.getEchartsPieDataListMap(ermFunedInfoListMap,
							"funded_type", "stu_count"));
		}
		return R.ok()
				.put("header",
						BusinessUtil.getTableHeader(Constants.FUNDED_TYPE_REPORT_KEYS,
								Constants.FUNDED_TYPE_REPORT_NAMES, Constants.FUNDED_TYPE_REPORT_VALIDS))
				.put("page", pageUtil)
				.put("echart",
						this.ermFundedInfoService.getEchartsDataMap(ermFunedInfoListMap, "funded_type", "sum_money"))
				.put("echartPie", this.ermFundedInfoService.getEchartsPieDataListMap(ermFunedInfoListMap, "funded_type",
						"stu_count"));
	}

	@ApiOperation(value = "资助概况", notes = "资助概况")
	@RequestMapping(value = "/generalView", method = RequestMethod.GET)
	public R fundGeneralReView(JwtAuthenticationToken token) {
		Map<String, Object> viewData = new HashMap<String, Object>();
		UserContext user = (UserContext) token.getPrincipal();
		ErmAdminEntity admin = userService.getByUser(user.getUsername());
		int roleId = admin.getRoleId();
		if (roleId == UserType.OPER.getCode() || roleId == UserType.SCH.getCode()) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("schId", user.getSchoolId());
			int stuCount = ermStudentService.queryTotal(param);
			int stuPoorCount = ermFundedInfoService.queryAppliedStudentsCount(user.getSchoolId(),
					Calendar.getInstance().get(Calendar.YEAR));
			int stuFundedCount = ermFundedInfoService.queryFundSuccessStudentCount(user.getSchoolId(),
					Calendar.getInstance().get(Calendar.YEAR));
			// 该学校 总人数
			viewData.put("sch-total-student", stuCount);
			// 该学校 总申请人数
			viewData.put("sch-poor-student", stuPoorCount);
			// 该学校 总受助人数
			viewData.put("sch-funded-student", stuFundedCount);
		} else if (roleId == UserType.EB.getCode()) {
			// 资助总项目数
			// 资助总人数
			viewData = this.ermFundedInfoService.queryEBViewData(user.getEbId());
		}
		return R.ok().put("viewData", viewData);
	}

	@RequestMapping(value = "/queryReportOverViewList", method = RequestMethod.GET)
	public R queryReportOverViewList(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("ebId", user.getEbId());
		params.put("reportTypes", "1,2,3");
		params.put("group", "sch_id,report_year");
		String type = "eb";
		if (user.getSchoolId() != null) {
			params.put("schoolId", user.getSchoolId());
			params.put("reportTypes", "4,5,6");
			params.put("group", "report_year,fund_id");
			type = "school";
		}
		Query query = new Query(params);
		List<ErmReportEntity> reportOverViewList = this.ermReportService.queryList(query);
		int total = ermReportService.queryTotal(query);
		List<Integer> years = null;
		if (StringUtils.isBlank((String) params.get("year"))) {
			years = this.ermFundedInfoService.getReportYears(params);
		} else {
			years = new ArrayList<Integer>();
			years.add(Integer.parseInt((String) params.get("year")));
		}
		List<ErmReportEntity> defaultReportOverviewList = this.ermReportService.queryDefaultOverViewList(years,
				reportOverViewList, type);
		PageUtils pageUtil = new PageUtils(defaultReportOverviewList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}

	@RequestMapping(value = "/getReportYears", method = RequestMethod.GET)
	public R getReportYears(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("ebId", user.getEbId());
		List<Integer> years = this.ermFundedInfoService.getReportYears(params);
		return R.ok().put("years", years);
	}

	/**
	 * 获取导出动态字段
	 * 
	 * @param params
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/getExportColumn", method = RequestMethod.GET)
	public R getExportColumn(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		List<ErmExportCloumn> listCloumn = DictConstants.CLOUMN_ENTITY;
		return R.ok().put("columns", listCloumn);
	}

}
