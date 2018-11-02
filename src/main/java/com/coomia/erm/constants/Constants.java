package com.coomia.erm.constants;

/**
 * 普通常量类
 * 
 * @author Administrator
 *
 */
public final class Constants {

	/**
	 * 字段类型-下拉
	 */
	public static Integer FIELD_TYPE_SELECT = 3;
	/**
	 * 字段类型-数字
	 */
	public static Integer FIELD_TYPE_NUM = 2;
	/**
	 * 字段类型-文本
	 */
	public static Integer FIELD_TYPE_TEXT = 1;

	/**
	 * 信息查询表头key
	 */
	public static String[] INFO_QUERY_KEYS = new String[] { "id", "name", "age", "stuno", "gender", "birth", "idcard",
			"funded_name", "funded_id", "global_status", "money", "fund_count", "sum_money" };
	/**
	 * 信息查询表头名称
	 */
	public static String[] INFO_QUERY_NAMES = new String[] { "序号", "姓名", "年龄", "学号", "性别", "出生日期", "身份证号", "项目名称",
			"项目Id", "状态", "项目金额", "项目总数", "项目总金额" };
	/**
	 * 信息查询表头是否可用
	 */
	public static boolean[] INFO_QUERY_VALIDS = new boolean[] { true, true, true, true, true, true, true, true, true,
			true, true, true, true };
	/**
	 * 统计报表-查询总的项目信息表头key
	 */
	public static String[] TOTAL_REPORT_KEYS = new String[] { "id", "name", "total_stus", "school_1_stus",
			"school_2_stus", "school_3_stus", "school_4_stus", "school_5_stus", "school_6_stus", "total_money",
			"school_1_money", "school_2_money", "school_3_money", "school_4_money", "school_5_money",
			"school_6_money" };
	/**
	 * 信息查询表头名称
	 */
	public static String[] TOTAL_REPORT_NAMES = new String[] { "序号", "项目名称", "所有学生", "学前数量", "小学数量", "初中数量", "普高数量",
			"本专生数量", "研究生数量", "所有金额", "学前金额", "小学金额", "初中金额", "普高金额", "本专生金额", "研究生金额", };
	/**
	 * 信息查询表头是否可用
	 */
	public static boolean[] TOTAL_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true };

	/**
	 * 统计报表-学生信息表头key
	 */
	public static String[] STUDENT_REPORT_KEYS = new String[] { "id", "name", "school_name", "idcard", "year", "money",
			"funded_type", "funded_name" };
	/**
	 * 统计报表-学生信息表头名称
	 */
	public static String[] STUDENT_REPORT_NAMES = new String[] { "序号", "姓名", "学校名称", "身份证号", "受助年份", "受助金额", "项目类型",
			"项目名称" };
	/**
	 * 统计报表-学生信息表头是否可用
	 */
	public static boolean[] STUDENT_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true, true };
	/**
	 * 统计报表-学生资助信息表头key
	 */
	public static String[] STUDENT_FUND_KEYS = new String[] { "id", "semester", "name", "funded_type", "money",
			"global_status" };
	/**
	 * 统计报表-学生资助信息表头名称
	 */
	public static String[] STUDENT_FUND_NAMES = new String[] { "序号", "学期", "资助名称", "资助类型", "资助金额", "资助状态" };
	/**
	 * 统计报表-学生资助信息表头是否可用
	 */
	public static boolean[] STUDENT_FUND_VALIDS = new boolean[] { true, true, true, true, true, true };
	/**
	 * 统计报表-资助学校信息表头key
	 */
	public static String[] SCHOOL_REPORT_KEYS = new String[] { "id", "school_type", "school_name", "year", "stu_count",
			"sum_money" };
	/**
	 * 统计报表-资助学校信息表头名称
	 */
	public static String[] SCHOOL_REPORT_NAMES = new String[] { "序号", "学校类型", "学校名称", "年份", "资助总人数", "资助总金额" };
	/**
	 * 统计报表-资助学校信息表头是否可用
	 */
	public static boolean[] SCHOOL_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true };
	/**
	 * 统计报表-资助学区信息表头key
	 */
	public static String[] SCHOOL_TYPE_REPORT_KEYS = new String[] { "id", "school_type", "year", "stu_count",
			"sum_money" };
	/**
	 * 统计报表-资助学区信息表头名称
	 */
	public static String[] SCHOOL_TYPE_REPORT_NAMES = new String[] { "序号", "学校类型", "年份", "资助总人数", "资助总金额" };
	/**
	 * 统计报表-资助学区信息表头是否可用
	 */
	public static boolean[] SCHOOL_TYPE_REPORT_VALIDS = new boolean[] { true, true, true, true, true };
	/**
	 * 统计报表-资助类型信息表头key
	 */
	public static String[] FUNDED_TYPE_REPORT_KEYS = new String[] { "id", "funded_type", "year", "stu_count",
			"sum_money" };
	/**
	 * 统计报表-资助类型信息表头名称
	 */
	public static String[] FUNDED_TYPE_REPORT_NAMES = new String[] { "序号", "资助类型", "年份", "资助总人数", "资助总金额" };
	/**
	 * 统计报表-资助类型信息表头是否可用
	 */
	public static boolean[] FUNDED_TYPE_REPORT_VALIDS = new boolean[] { true, true, true, true, true };
	/**
	 * 统计报表-资助项目信息表头key
	 */
	public static String[] FUNDED_REPORT_KEYS = new String[] { "id", "funded_type", "funded_name", "year", "stu_count",
			"sum_money" };
	/**
	 * 统计报表-资助项目信息表头名称
	 */
	public static String[] FUNDED_REPORT_NAMES = new String[] { "序号", "资助类型", "项目名称", "年份", "资助总人数", "资助总金额" };
	/**
	 * 统计报表-资助项目信息表头是否可用
	 */
	public static boolean[] FUNDED_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true };
	/**
	 * 统计报表类型-学生
	 */
	public static String REPORT_TYPE_STUDENT = "student";
	/**
	 * 统计报表类型-学校
	 */
	public static String REPORT_TYPE_SHCOOL = "school";
	/**
	 * 统计报表类型-学区
	 */
	public static String REPORT_TYPE_SHCOOL_TYPE = "schoolType";
	/**
	 * 统计报表类型-项目类型
	 */
	public static String REPORT_TYPE_FUNDED_TYPE = "fundedType";
	/**
	 * 统计报表类型-项目
	 */
	public static String REPORT_TYPE_FUNDED = "funded";
	/**
	 * 统计报表类型-信息查询
	 */
	public static String INFO_QUERY_TYPE = "infoQuery";
	/**
	 * 申报对象名单
	 */
	public static String APPLY_STUDENT = "applyStudent";
	/**
	 * 申报对象名单
	 */
	public static String FUNDING_STUDENT = "fundingStudent";

	/**
	 * 申报对象名单、资助学生名单key
	 */
	public static String[] APPLY_STUDENT_REPORT_KEYS = new String[] { "id", "stuName", "gender", "age", "stuno",
			"grade", "clazz", "zone", "diffLevelName", "score", "offeredStatus" };
	/**
	 * 申报对象名单、资助学生名单表头名称
	 */
	public static String[] APPLY_STUDENT_REPORT_NAMES = new String[] { "排名", "姓名", "性别", "年龄", "学号", "年级", "班级", "学区",
			"困难等级", "智能得分", "资助状态" };
	/**
	 * 申报对象名单、资助学生名单表头是否可用
	 */
	public static boolean[] APPLY_STUDENT_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true,
			true, true, true, true };

	/**
	 * 统计报表-学生基本信息表头key
	 */
	public static String[] BASE_STUDENT_REPORT_KEYS = new String[] { "id", "name", "age", "gender", "idcard", "stuno",
			"school_name", "school_type", "grade", "clazz", "start_year", "parent_name", "telphone", "address",
			"addressTown", "addressTownship", "addressGroup" };
	/**
	 * 统计报表-学生基本信息表头名称
	 */
	public static String[] BASE_STUDENT_REPORT_NAMES = new String[] { "序号", "姓名", "年龄", "性别", "身份证号", "学号", "学校", "学区",
			"年级", "班级", "学期", "监护人姓名", "联系电话", "家庭住址", "乡镇", "村(居委会)", "组(号)" };
	/**
	 * 统计报表-学生基本信息表头是否可用
	 */
	public static boolean[] BASE_STUDENT_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true };

	/**
	 * 统计报表-困难学生信息列表头key
	 */
	public static String[] DIFF_STUDENT_REPORT_KEYS = new String[] { "id", "name", "age", "gender", "idcard", "stuno",
			"school_name", "schzone", "level", "grade", "clazz", "start_year", "parent_name", "telphone", "address",
			"addressTown", "addressTownship", "addressGroup", "year", "funded_type", "funded_name", "money",
			"hasApplyImg" };
	/**
	 * 统计报表-困难学生信息列表头名称
	 */
	public static String[] DIFF_STUDENT_REPORT_NAMES = new String[] { "序号", "姓名", "年龄", "性别", "身份证号", "学号", "学校", "学区",
			"资助等级", "年级", "班级", "学期", "监护人姓名", "联系电话", "家庭住址", "乡镇", "村(居委会)", "组(号)", "资助年份", "项目类型", "项目名称", "资助金额",
			"申请表图片" };
	/**
	 * 统计报表-困难学生信息列表头是否可用
	 */
	public static boolean[] DIFF_STUDENT_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true, true, true, true };

	/**
	 * 统计报表-公示学生信息列表头key
	 */
	public static String[] PUBLIC_STUDENT_REPORT_KEYS = new String[] { "id", "name", "age", "gender", "stuno", "grade",
			"clazz" };
	/**
	 * 统计报表-公示学生信息列表头名称
	 */
	public static String[] PUBLIC_STUDENT_REPORT_NAMES = new String[] { "序号", "姓名", "年龄", "性别", "学号", "年级", "班级" };
	/**
	 * 统计报表-公示学生信息列表头是否可用
	 */
	public static boolean[] PUBLIC_STUDENT_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true };

	/**
	 * 统计报表-受资助的学生信息列表头key
	 */
	public static String[] FUNDING_STUDENT_REPORT_KEYS = new String[] { "id", "name", "gender", "age", "school_name",
			"schzone", "level", "grade", "clazz", "address", "addressTown", "addressTownship", "addressGroup", "idcard",
			"telphone", "is_poor", "actual_bankcard", "money", "note" };
	/**
	 * 统计报表-受资助的学生信息列表头名称
	 */
	public static String[] FUNDING_STUDENT_REPORT_NAMES = new String[] { "序号", "姓名", "性别", "年龄", "学校", "学区", "资助等级",
			"年级", "班级", "家庭住址", "乡镇", "村(居委会)", "组(号)", "身份证号", "家长联系电话", "贫困类型", "银行卡号(中国银行)", "金额", "备注" };
	/**
	 * 统计报表-受资助的学生信息列表头是否可用
	 */
	public static boolean[] FUNDING_STUDENT_REPORT_VALIDS = new boolean[] { true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true };

}
