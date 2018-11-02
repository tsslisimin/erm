package com.coomia.platform.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jplus.hyberbin.excel.bean.CellBean;
import org.jplus.hyberbin.excel.bean.GroupConfig;
import org.jplus.hyberbin.excel.bean.TableBean;
import org.jplus.hyberbin.excel.language.ILanguage;
import org.jplus.hyberbin.excel.service.ExportExcelService;
import org.jplus.hyberbin.excel.service.ExportTableService;
import org.jplus.hyberbin.excel.service.ImportExcelService;
import org.jplus.hyberbin.excel.service.ImportTableService;
import org.jplus.hyberbin.excel.service.SimpleExportService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.excel.ErmStudentExcelEntity;

public class TestExcel {
	private Workbook workbook;

	@BeforeClass
	public static void setUpClass() {
	}

	@Before
	public void setUp() {
		workbook = new HSSFWorkbook();

	}

	private static Map buildMap(String id, String kcmc, String kclx) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("kcmc", kcmc);
		map.put("kclx", kclx);
		return map;
	}

	private static List<SchoolCourse> getList() {
		List<SchoolCourse> list = new ArrayList<SchoolCourse>();
		list.add(new SchoolCourse("1", "", "1"));
		list.add(new SchoolCourse("2", "数学", "1"));
		list.add(new SchoolCourse("3", "英语", "1"));
		list.add(new SchoolCourse("4", "政治", "2"));
		list.add(new SchoolCourse("5", "历史", "2"));
		return list;
	}
	
	private static List<ErmStudentExcelEntity> getSchoolList() {
		List<ErmStudentExcelEntity> list = new ArrayList<ErmStudentExcelEntity>();
		list.add(new ErmStudentExcelEntity());
//		list.add(new ErmSchoolEntity(2, "2", "002", "虹桥初中"));
//		list.add(new ErmSchoolEntity(3, "3", "003", "虹桥高中"));
//		list.add(new SchoolCourse("2", "数学", "1"));
//		list.add(new SchoolCourse("3", "英语", "1"));
//		list.add(new SchoolCourse("4", "政治", "2"));
//		list.add(new SchoolCourse("5", "历史", "2"));
		return list;
	}

	private static List<Map> getMapList() {
		List<Map> list = new ArrayList<Map>();
		list.add(buildMap("1", "语文", "1"));
		list.add(buildMap("2", "数学", "1"));
		list.add(buildMap("3", "英语", "1"));
		list.add(buildMap("4", "政治", "2"));
		list.add(buildMap("5", "历史", "2"));
		return list;
	}

	/**
	 * 从List<Map>中导出
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSimpleMapExport() throws Exception {
		Sheet sheet = workbook.createSheet("testSimpleMapExport");
		SimpleExportService service = new SimpleExportService(sheet, getMapList(),
				new String[] { "id", "kcmc", "kclx" }, "学校课程");
		// 如果要表头可以像下面这样设置,不要表头可以不写
		service.setLanguage(new ILanguage() {
			@Override
			public String translate(Object key, Object... args) {
				if ("id".equals(key)) {
					return "序号";
				} else if ("kcmc".equals(key)) {
					return "课程名称";
				} else if ("kclx".equals(key)) {
					return "课程类型";
				}
				return key + "";
			}
		});
		service.setDic("kclx", "kclx").addDic("kclx", "1", "国家课程").addDic("kclx", "2", "学校课程");// 设置数据字典
//		service.setDic("kclx", "kclx");
		service.doExport();
		FileOutputStream fos = new FileOutputStream("D:/test.xls");
        workbook.write(fos);
        if(null != fos){
            fos.close();
        }
	}

	/**
	 * 从List<Vo>中导出
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSimpleVoExport()  {
		Sheet sheet = workbook.createSheet("testSimpleVoExport");
		// ExportExcelService service = new ExportExcelService(list, sheet, "学校课程");
		ExportExcelService service = null;
		try {
			service = new ExportExcelService(getSchoolList(), sheet,
					new String[] {"name","stuno","idcard","major","schSystem","pinyin","engname","startYear","age","zone","grade","studyType","stuType","clazz","telphone","address","divisionCode","nature","birthPlace","cityCode","marriageStatus","healthStatus","politicalStatus","nation","attendingType","learnType","lowInsurance","handicapped","defaultBankcard","actualBankcard"}, "学生信息");
			service.doExport();
			FileOutputStream fos = new FileOutputStream("D:/test5.xls");
	        workbook.write(fos);
	        if(null != fos){
	            fos.close();
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		service.addDic("XXLX", "1", "小学").addDic("XXLX", "2", "初中").addDic("XXLX", "3","高中");// 设置数据字典
	
	}

	/**
	 * 从Excel中直接导入
	 */
	@Test
	public void testSimpleImport() throws Exception {
		testTableExport();// 这一步仅仅是想得到一个已有的Excel,用户可以通过其他形式生成一个已有数据的workbook
		Sheet sheet = workbook.getSheet("testTableExport");
		ImportTableService tableService = new ImportTableService(sheet);
		tableService.doImport();
		// 直接读取到List中,泛型可以是Map也可以是PO
		// 第一个参数是从表格第0列开始依次读取内容放到哪些字段中
		List<Map> read = tableService.read(new String[] { "a", "b", "c" }, Map.class);
		System.out.print(read);
	}

	/**
	 * 从List<Vo>，vo中还有简单循环节中导出
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVoHasListExport() throws Exception {
		List<String> strings = new ArrayList<String>();
		List<SchoolCourse> list = getList();
		for (int i = 0; i < 10; i++) {
			strings.add("key"+i);
		}
		for (SchoolCourse course : list) {
			course.setBaseArray(strings);
		}
		Sheet sheet = workbook.createSheet("testVoHasListExport");
		ExportExcelService service = new ExportExcelService(list, sheet,
				new String[] { "id", "courseName", "type", "baseArray" }, "学校课程");
		service.addDic("KCLX", "1", "国家课程").addDic("KCLX", "2", "学校课程");// 设置数据字典
		service.setGroupConfig("baseArray", new GroupConfig(10) {

			@Override
			public String getLangName(int innerIndex, int index) {
				return "我是第" + index + "1个循环字段";
			}
		});
		service.doExport();
		service.exportTemplate();// 生成下拉框
		FileOutputStream fos = new FileOutputStream("D:/test4.xls");
        workbook.write(fos);
        if(null != fos){
            fos.close();
        }
	}

	/**
	 * 从List<Vo>，vo中还有复杂循环节中导出
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVoHasListVoExport() throws Exception {
		List<SchoolCourse> list = getList();
		Class vo = UpdateAnnTest.ReadTest().toClass();
		for (SchoolCourse course : list) {
			List<InnerVo> innerVos = new ArrayList<InnerVo>();
			for (int i = 0; i < 10; i++) {
				System.out.println(vo);
				InnerVo ss = (InnerVo) vo.newInstance();
				ss.setKey("key1");
				ss.setValue("value1");
				innerVos.add(ss);
			}
			course.setInnerVoArray(innerVos);
		}
		Sheet sheet = workbook.createSheet("testVoHasListVoExport");
		ExportExcelService service = new ExportExcelService(list, sheet,
				new String[] { "id", "courseName", "type", "innerVoArray" }, "学校课程");
		
		service.addDic("yy", "key1", "zzzzz").addDic("yy", "key2", "yyyyy");// 设置数据字典
		service.addDic("KCLX", "1", "国家课程").addDic("KCLX", "2", "学校课程");// 设置数据字典
		service.setGroupConfig("innerVoArray", new GroupConfig(10) {
			@Override
			public String getLangName(int innerIndex, int index) {
				return "我是第" + index + "个循环字段";
			}
		});
		service.doExport();
		service.exportTemplate();
		FileOutputStream fos = new FileOutputStream("D:/test2.xls");
        workbook.write(fos);
        if(null != fos){
            fos.close();
        }
	}

	/**
	 * 导出一个纵表（课程表之类的）
	 * 
	 * @throws Exception
	 */
	@Test
	public void testTableExport() throws Exception {
		Sheet sheet = workbook.createSheet("testTableExport");
		TableBean tableBean = new TableBean(3, 3);
		Collection<CellBean> cellBeans = new HashSet<CellBean>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				CellBean cellBean = new CellBean(i * 3 + j + "", i, j);
				cellBeans.add(cellBean);
			}
		}
		tableBean.setCellBeans(cellBeans);
		ExportTableService tableService = new ExportTableService(sheet, tableBean);
		tableService.doExport();
		FileOutputStream fos = new FileOutputStream("D:/test3.xls");
        workbook.write(fos);
        if(null != fos){
            fos.close();
        }
	}

	/**
	 * 从List<Vo>中入
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSimpleVoImport() throws Exception {
		testSimpleVoExport();// 这一步仅仅是想得到一个已有的Excel,用户可以通过其他形式生成一个已有数据的workbook
		Sheet sheet = workbook.getSheet("testSimpleVoExport");
		ImportExcelService service = new ImportExcelService(SchoolCourse.class, sheet);
		service.addDic("KCLX", "1", "国家课程").addDic("KCLX", "2", "学校课程");// 设置数据字典
		List list = service.doImport();
		System.out.println("成功导入：" + list.size() + "条数据");
	}

	/**
	 * 从List<Vo>，vo中还有简单循环节中导入
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVoHasListImport() throws Exception {
		testVoHasListExport();// 这一步仅仅是想得到一个已有的Excel,用户可以通过其他形式生成一个已有数据的workbook
		Sheet sheet = workbook.getSheet("testVoHasListExport");
		ImportExcelService service = new ImportExcelService(SchoolCourse.class, sheet);
		service.addDic("KCLX", "1", "国家课程").addDic("KCLX", "2", "学校课程");// 设置数据字典
		List list = service.doImport();
		System.out.println("成功导入：" + list.size() + "条数据");
	}

	/**
	 * 从List<Vo>，vo中还有复杂循环节中导入
	 * 
	 * @throws Exception
	 */
	@Test
	public void testVoHasListVoImport() throws Exception {
		testVoHasListVoExport();// 这一步仅仅是想得到一个已有的Excel,用户可以通过其他形式生成一个已有数据的workbook
		Sheet sheet = workbook.getSheet("testVoHasListVoExport");
		ImportExcelService service = new ImportExcelService(SchoolCourse.class, sheet);
		service.addDic("KCLX", "1", "国家课程").addDic("KCLX", "2", "学校课程");// 设置数据字典
		List list = service.doImport();
		System.out.println(list.toString());
		System.out.println("成功导入：" + list.size() + "条数据");
	}
	
	@Test
	public void testImportFromFile() {
		File file = new File("D://test5.xls");
		InputStream input = null;
		Workbook workbook = null;
		try {
			input = new FileInputStream(file);
			workbook = new HSSFWorkbook(input);
			Sheet sheet = workbook.getSheet("testSimpleVoExport");
			ImportExcelService service = new ImportExcelService(ErmSchoolEntity.class, sheet);
			service.addDic("XXLX", "1", "小学").addDic("XXLX", "2", "初中").addDic("XXLX", "3","高中");// 设置数据字典
			List list = service.doImport();
			System.out.println("成功导入：" + list.size() + "条数据");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
