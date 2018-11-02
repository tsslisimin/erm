package com.coomia.erm.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ErmExportCloumn;

/**
 * 字典常量类
 * 
 * @author Administrator
 *
 */
public final class DictConstants {

  /**
   * 角色常量
   */
  public static Map<Integer, String> ROLE_MAP = new HashMap<Integer, String>() {
    private static final long serialVersionUID = 1L;
    {
      put(1, "管理员");
      put(2, "教育局");
      put(3, "校长");
      put(4, "操作员");
      put(5, "学生");
      put(6, "家长");

    }
  };
  /**
   * 困难学生类别
   */
  public static Map<Integer, String> DIFF_LEVEL = new HashMap<Integer, String>() {
	  private static final long serialVersionUID = 1L;
	  {
		  put(0, "不困难");
		  put(1, "困难");
		  put(2, "一般困难");
		  put(3, "特别困难");
	  }
  };
  /**
   * 班级评议小组认定状态
   */
  public static Map<Integer, String> HEAD_TEACH_CONFIRM = new HashMap<Integer, String>() {
	  private static final long serialVersionUID = 1L;
	  {
		  put(0, "未经过班级评议小组认定");
		  put(1, "已经班级评定小组认定");
		  put(2, "班级评定小组认定不通过");
	  }
  };

  public static Integer ROLE_OPER = 4;

  public static Integer ROLE_SHCOOL = 3;

  public static String DEFAULT_PASSWORD = "123456";

  /**
   * 学校类型
   */
  public static Map<Integer, String> SCHOOL_TYPE_MAP = new HashMap<Integer, String>() {
    private static final long serialVersionUID = 1L;

    {
      put(1, "学前");
      put(2, "小学");
      put(3, "初中");
      put(4, "普高");
      put(5, "中职");
    }
  };

  public static Map<Integer, String> COUNTRY_TYPE_MAP = new HashMap<Integer, String>() {
    private static final long serialVersionUID = 1L;

    {
      put(11, "学前教育资助");
      put(12, "义务教育资助");
      put(13, "普高教育资助");
      put(14, "中职教育资助");
      put(15, "高等教育资助");
    }
  };



  // 学前教育资助-家庭经济困难幼儿园补助---430821101-430821140
  public static List<String> XQ_SCH_LISTNEW = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
    add("1");
    }
  };
  // 义务教育资助-农村义务教育阶段家庭经济困难寄宿生生活补助 430821101-430821141
  public static List<String> YWJY_SCH_LISTNEW = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("2");
      add("3");
    }
  };

  // 普高教育资助：①国家助学金②免除学杂费--430821201-430821204
  public static List<String> PGJY_SCH_LISTNEW = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("4");
    }
  };

  // 中职教育资助：①免除学杂费②国家助学金---430821301 430821302、430821303
  public static List<String> ZZJY_SCH_LISTNEW = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("5");
    }
  };

  // 高等教育资助：大学生生源地信用助学贷款
  public static List<String> GDJY_SCH_LISTNEW = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {add("5");
    }
  };

  // 学前教育资助-家庭经济困难幼儿园补助---430821101-430821140
  public static List<String> XQ_SCH_LIST = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      for (int i = 430821101; i <= 430821140; i++) {
        add("" + i+"00");
      }
    }
  };
  // 义务教育资助-农村义务教育阶段家庭经济困难寄宿生生活补助 430821101-430821141
  public static List<String> YWJY_SCH_LIST = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      for (int i = 430821101; i <= 430821141; i++) {
        add("" + i);
      }
    }
  };

  // 普高教育资助：①国家助学金②免除学杂费--430821201-430821204
  public static List<String> PGJY_SCH_LIST = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      for (int i = 430821201; i <= 430821204; i++) {
        add("" + i);
      }
    }
  };

  // 中职教育资助：①免除学杂费②国家助学金---430821301 430821302、430821303
  public static List<String> ZZJY_SCH_LIST = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("430821301");
      add("430821302");
      add("430821303");
    }
  };

  // 高等教育资助：大学生生源地信用助学贷款
  public static List<String> GDJY_SCH_LIST = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
    }
  };

  public static Map<String, List<String>> FUND_CODE_SCH_RELA = new HashMap<String, List<String>>() {
    private static final long serialVersionUID = 1L;
    {
      put("m000000406810974", XQ_SCH_LIST);
      put("u000000941578650", YWJY_SCH_LIST);
      put("n000001234465169", PGJY_SCH_LIST);
      put("l000001071251404", PGJY_SCH_LIST);
      put("h000000680246265", ZZJY_SCH_LIST);
      put("s000001582244986", ZZJY_SCH_LIST);
      put("w000000819230670", GDJY_SCH_LIST);

    }
  };

  public static Map<String, List<String>> SUBTYPE_SCHOOL_LIST =
      new HashMap<String, List<String>>() {
        private static final long serialVersionUID = 1L;
        {
          put("111", XQ_SCH_LIST);
          put("112", YWJY_SCH_LIST);
          put("113", PGJY_SCH_LIST);
          put("114", ZZJY_SCH_LIST);
          put("115", GDJY_SCH_LIST);

        }
      };


  public static Map<String, List<String>> SUBTYPENEW_SCHOOL_LIST =
          new HashMap<String, List<String>>() {
            private static final long serialVersionUID = 1L;
            {
              put("111", XQ_SCH_LISTNEW);
              put("112", YWJY_SCH_LISTNEW);
              put("113", PGJY_SCH_LISTNEW);
              put("114", ZZJY_SCH_LISTNEW);
              put("115", GDJY_SCH_LISTNEW);

            }
          };
  /**
   * 资助类型字典
   */
  public static Map<Integer, String> FUNDED_TYPE_MAP = new HashMap<Integer, String>() {
    private static final long serialVersionUID = 1L;
    {
      put(1, "国家资助");
      put(2, "地方政府资助");
      put(3, "学校资助");
      put(4, "社会资助");
    }
  };
  /**
   * 是否
   */
  public static Map<Integer, String> YES_OR_NO = new HashMap<Integer, String>() {
    private static final long serialVersionUID = 1L;
    {
      put(1, "是");
      put(2, "否");
    }
  };

  /**
   * 性别 男
   */
  public static Integer GENDER_MALE = 1;
  /**
   * 性别 女
   */
  public static Integer GENDER_FEMALE = 2;
  /**
   * 字母串
   */
  public static String CHARS = "abcdefghijklmnopqrstuvwxyz";
  /**
   * 身份证位数
   */
  public static Integer IDCARD_NUMS = 18;

  public static List<String> CHECK_CLOUMN = new ArrayList<String>() {
    {
      add("name");
      add("age");
      add("stuno");
      add("school_name");
      add("school_type");
      add("gender");
      add("birth");
      add("idcard");
    }
  };

  public static List<ErmExportCloumn> CLOUMN_ENTITY_BASE = new ArrayList<ErmExportCloumn>() {
    {
      add(new ErmExportCloumn("name", "姓名", true));
      add(new ErmExportCloumn("age", "年龄", true));
      add(new ErmExportCloumn("school_name", "学校", true));
      add(new ErmExportCloumn("gender", "性别", true));
      add(new ErmExportCloumn("birth", "出生日期", true));
      add(new ErmExportCloumn("idcard", "身份证号码", true));
      add(new ErmExportCloumn("fund_count", "受资助项目总数", false));
      add(new ErmExportCloumn("sum_money", "受资助项目总金额", false));
      add(new ErmExportCloumn("funded_type", "项目类型", false));
      add(new ErmExportCloumn("year", "资助年份", false));
      add(new ErmExportCloumn("semester", "学期", false));
      add(new ErmExportCloumn("funded_name", "项目名称", false));
      add(new ErmExportCloumn("level", "资助级别", false));
      add(new ErmExportCloumn("schzone", "学区", false));
      add(new ErmExportCloumn("money", "资助金额", false));

    }
  };
  
  public static List<ErmExportCloumn> CLOUMN_ENTITY = new ArrayList<ErmExportCloumn>(CLOUMN_ENTITY_BASE) {
    {
      /**
      add(new ErmExportCloumn("name", "姓名", true));
      add(new ErmExportCloumn("age", "年龄", true));
      add(new ErmExportCloumn("stuno", "学号", true));
      add(new ErmExportCloumn("school_name", "学校", true));
      add(new ErmExportCloumn("gender", "性别", true));
      add(new ErmExportCloumn("birth", "出生日期", true));
      add(new ErmExportCloumn("idcard", "身份证号码", true));
      add(new ErmExportCloumn("fund_count", "项目总数", false));
      add(new ErmExportCloumn("sum_money", "项目总金额", false));
      add(new ErmExportCloumn("year", "资助年份", false));
      add(new ErmExportCloumn("funded_type", "项目类型", false));
      add(new ErmExportCloumn("funded_name", "项目名称", false));
      add(new ErmExportCloumn("schzone", "学区", true));
      add(new ErmExportCloumn("level", "资助级别", false));
      add(new ErmExportCloumn("money", "资助金额", false));
      add(new ErmExportCloumn("create_time", "填报日期", false));
      add(new ErmExportCloumn("parent_name", "监护人姓名", false));
      add(new ErmExportCloumn("telphone", "联系电话", false));
      add(new ErmExportCloumn("address", "家庭住址", false));
      add(new ErmExportCloumn("addressTown", "乡镇", false));
      add(new ErmExportCloumn("addressTownship", "村（居委会）", false));
      add(new ErmExportCloumn("addressGroup", "组（号）", false));
      add(new ErmExportCloumn("grade", "年级", false));
      add(new ErmExportCloumn("clazz", "班级", false));
      add(new ErmExportCloumn("actual_bankcard", "卡号", false));
      add(new ErmExportCloumn("semester", "学期", false));
      **/
    }
  };


  public static Map<String, String> COLUMN_MAP = new HashMap<String, String>() {
    {
      put("name", "姓名");
      put("age", "年龄");
      put("stuno", "学号");
      put("school_name", "学校");
      put("schzone", "学区");
      put("gender", "性别");
      put("birth", "出生日期");
      put("idcard", "身份证号码");
      put("year", "资助年份");
      put("funded_type", "项目类型");
      put("funded_name", "项目名称");
      put("money", "资助金额");
      put("fund_count", "受资助项目总数");
      put("sum_money", "受资助项目总金额");
      put("level", "资助级别");
      put("create_time", "填报日期");
      put("parent_name", "监护人姓名");
      put("telphone", "联系电话");
      put("address", "家庭住址");
      put("addressTown", "乡镇");
      put("addressTownship", "村（居委会）");
      put("addressGroup", "组（号）");
      put("grade", "年级");
      put("clazz", "班级");
      put("actual_bankcard", "卡号");
      put("semester", "学期");
    }
  };

  // 学期字典类型22
  public static final Integer SEMESTER_TYPE = 22;

}
