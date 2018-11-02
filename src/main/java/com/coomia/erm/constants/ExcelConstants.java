package com.coomia.erm.constants;

import java.util.ArrayList;
import java.util.List;

public final class ExcelConstants {

	/**
	 * 学校excel表头名称
	 */
	public static List<String> SCHOOL_TITLE_NAME = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{   add("No");
			add("id");
			add("type");
			add("code");
			add("name");
		}
	};

	/**
	 * 学生excel表头名称
	 */
	public static List<String> STUDENT_TITLE_NAME1 = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{   add("No");
			add("name");
			add("pinyin");
			add("engname");
			add("isPoor");
			add("idcard");
			add("stuno");
			add("grade");
			add("clazz");
			add("address");
			add("addressProvince");
			add("addressCity");
			add("addressArea");
			add("addressTown");
			add("addressTownship");
			add("addressGroup");
			add("parentName");
			add("telphone");
			add("actualBankcard");
			add("startYear");
			add("major");
			add("nature");
			add("accountType");
			add("addresstype");
			add("politicalStatus");
			add("healthStatus");
			add("studentFrom");
			add("schSystem");
			add("studyType");
			add("stuType");
			add("schoolName");
			add("divisionCode");
			add("birthPlace");
			add("cityCode");
			add("marriageStatus");
			add("nation");
			add("attendingType");
			add("learnType");
//			add("lowInsurance");
			add("policeStation");
			add("trainRegion");
			add("studentObj");
			add("studyPlace");
			add("isMove");
			add("transProvincial");
			add("cooperationType");
			add("subTeach");
			add("lodging");
//			 add("zone");
//
//			 add("handicapped");
//			 add("defaultBankcard");
//

		}
	};
	/**
	 * 学生excel表头名称
	 */
	public static List<String> STUDENT_TITLE_NAME = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{   add("No");
			add("name");
			//add("pinyin");
			//	add("engname");

			add("idcard");
			add("stuno");
			add("grade");
			add("clazz");
			add("isPoor");
			add("address");
			add("addressProvince");
			add("addressCity");
			add("addressArea");
			add("addressTown");
			add("addressTownship");
			add("addressGroup");
			add("parentName");
			add("telphone");
			add("actualBankcard");
			//	add("startYear");
			//	add("major");
			add("nature");
			add("accountType");
			add("addresstype");
			add("politicalStatus");
			add("healthStatus");
			//add("studentFrom");
			//	add("schSystem");
			//	add("studyType");
			//	add("stuType");
			//add("schoolName");
			add("divisionCode");
			//add("birthPlace");
			add("cityCode");
			//add("marriageStatus");
			//	add("nation");
			//	add("attendingType");
			//	add("learnType");
//			add("lowInsurance");
			//	add("policeStation");
			//	add("trainRegion");
			//	add("studentObj");
			//	add("studyPlace");
			add("isMove");
			add("transProvincial");
			//	add("cooperationType");
			//	add("subTeach");
			add("lodging");
			add("archiveName");
			add("archiveIdcard");
			add("archiveRelation");
			add("archiveAcount");
			add("supportName");
			add("supportBankCard");
			add("helper");
			add("helperWorkPlace");
			add("helperPosition");
			add("helperTel");

//			 add("zone");
//
//			 add("handicapped");
//			 add("defaultBankcard");
//

		}
	};
	/**
	 * sheet名称
	 */
	public static String SCHOOL_SHEET_NAME = "schoolInfoSheet";
	/**
	 * sheet名称
	 */
	public static String STUDENT_SHEET_NAME = "studentInfoSheet";
	/**
	 * 内页标题名称
	 */
	public static String SCHOOL_TITLE = "学校信息";
	/**
	 * 内页标题名称
	 */
	public static String STUDENT_TITLE = "学生信息";
	/**
	 * 学校类型
	 */
	public static String SCHOOL_TYPE_CODE = "XXLX";
	/**
	 * 是否低保
	 */
	public static String STUDENT_LOWINSURANCE = "SFDB";
	/**
	 * 是否残疾
	 */
	public static String STUDENT_HANDICAPPED = "SFCJ";
	/**
	 * 报表名称-学生信息列表
	 */
	public static String REPORT_NAME_STUDENT = "学校学生信息报表";
	/**
	 * 报表名称-学生基本信息列表
	 */
	public static String REPORT_NAME_BASE_STUDENT = "学校学生基本信息报表";
	/**
	 * 报表名称-学生困难信息列表
	 */
	public static String REPORT_NAME_DIFF_STUDENT = "学校困难学生信息报表";
	/**
	 * 报表名称-学生困难信息列表
	 */
	public static String REPORT_NAME_PUBLIC_STUDENT = "学校公示学生信息";
	/**
	 * 报表名称-学生资助信息列表
	 */
	public static String REPORT_NAME_FUNDING_STUDENT = "学校资助学生信息报表";
	/**
	 * 报表名称-学生信息sheet
	 */
	public static String REPORT_SHEET_STUDENT = "schoolStudentReport";
	/**
	 * 报表名称-按资助学校统计
	 */
	public static String REPORT_NAME_SCHOOL = "资助学校信息报表";
	/**
	 * 报表名称-按资助学校统计sheet
	 */
	public static String REPORT_SHEET_SCHOOL = "schoolReport";
	/**
	 * 报表名称-按资助学区统计
	 */
	public static String REPORT_NAME_SCHOOL_TYPE = "资助学区信息报表";
	/**
	 * 报表名称-按资助学区统计sheet
	 */
	public static String REPORT_SHEET_SCHOOL_TYPE = "schoolTypeReport";
	/**
	 * 报表名称-按资助项目统计
	 */
	public static String REPORT_NAME_FUNDED = "资助项目信息报表";
	/**
	 * 报表名称-按资助项目统计sheet
	 */
	public static String REPORT_SHEET_FUNDED = "fundedReport";
	/**
	 * 信息查询名称
	 */
	public static String INFO_QUERY_NAME = "信息查询数据";
	/**
	 * 信息查询名称sheet
	 */
	public static String INFO_QUERY_SHEET = "info_query_sheet";
	/**
	 * 申报对象名单
	 */
	public static String APPLY_STUDENT_NAME = "申报对象名单";
	/**
	 * 申报对象sheet
	 */
	public static String APPLY_STUDENT_SHEET = "applyStudent";
	/**
	 * 资助对象名单
	 */
	public static String FUNDING_STUDENT_NAME = "资助学生名单";
	/**
	 * 资助对象sheet
	 */
	public static String FUNDING_STUDENT_SHEET = "applyStudent";

}
