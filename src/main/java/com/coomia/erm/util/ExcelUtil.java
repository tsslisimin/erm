package com.coomia.erm.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jplus.hyberbin.excel.bean.BaseExcelVo;
import org.jplus.hyberbin.excel.language.ILanguage;
import org.jplus.hyberbin.excel.service.ExportExcelService;
import org.jplus.hyberbin.excel.service.ImportExcelService;
import org.jplus.hyberbin.excel.service.SimpleExportService;

import com.coomia.erm.constants.Constants;
import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.ExcelConstants;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.entity.excel.ErmAdminExcelEntity;
import com.coomia.erm.entity.excel.ErmSchoolExcelEntity;
import com.coomia.erm.entity.excel.ErmStudentExcelEntity;

/**
 * Excel工具类
 * 
 * @author Leequn
 *
 */
public class ExcelUtil {
	/**
	 * 单sheet页 Map数据
	 * 
	 * @param request
	 * @param response
	 * @param filename
	 *            excel名称
	 * @param downList
	 *            需要下载的数据
	 * @param headerList
	 *            第一行中文名称
	 * @param fieldNameList
	 *            第一行字段名称
	 */
	public static void downLoadExcelForMap(HttpServletRequest request, HttpServletResponse response, String filename,
			List<Map<String, Object>> downList, String[] headerList, String[] headerNameList, boolean[] headerValids,
			String title, String sheetName) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			setDownLoadResponseHeader(request, response, filename);
			downExcelForListMap(out, downList, headerList, headerNameList, headerValids, title, sheetName);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 下载List<Map> 数据
	 * 
	 * @param out
	 * @param downList
	 * @param headerList
	 * @param title
	 * @param sheetName
	 * @author leequn
	 * @date 2017年11月16日 下午4:59:11
	 */
	private static void downExcelForListMap(OutputStream out, List<Map<String, Object>> downList, String[] headerList,
			String[] headerNameList, boolean[] headerValids, String title, String sheetName) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		SimpleExportService service = new SimpleExportService(sheet, downList, headerList, title);
		// 如果要表头可以像下面这样设置,不要表头可以不写
		service.setLanguage(new ILanguage() {
			@Override
			public String translate(Object key, Object... args) {
				for (int i = 0; i < headerList.length; i++) {
					if (headerList[i].equals(key)) {
						return headerNameList[i];
					}
				}
				return key + "";
			}
		});
		service.doExport();
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void downLoadExcelForVO(HttpServletRequest request, HttpServletResponse response, String filename,
			List<? extends BaseExcelVo> downList, List<String> headerList, String title, String sheetName) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			setDownLoadResponseHeader(request, response, filename);
			downExcelForMap(out, downList, headerList, title, sheetName);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void downExcelForMap(OutputStream out, List<? extends BaseExcelVo> downList, List<String> headerList,
			String fileName, String sheetName) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		ExportExcelService service = null;
		String[] headArr = new String[headerList.size()];
		headerList.toArray(headArr);
		try {
			service = new ExportExcelService(downList, sheet, headArr, fileName);
			addExportDicts(service, ExcelConstants.SCHOOL_TYPE_CODE, DictConstants.SCHOOL_TYPE_MAP);
			service.doExport();
			service.exportTemplate();
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void setDownLoadResponseHeader(HttpServletRequest request, HttpServletResponse response,
			String filename) throws UnsupportedEncodingException {
		String agent = request.getHeader("user-agent");
		if (agent.contains("MSIE") || agent.contains("Trident")) {
			filename = URLEncoder.encode(filename, "UTF-8");
		} else {
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		}
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("application/msexcel;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	}

	public static void addExportDicts(ExportExcelService<? extends BaseExcelVo> excelService, String dictConfigName,
			Map<Integer, String> dict) {
		if (excelService == null) {
			throw new RuntimeException("excelService不存在");
		}
		Set<Integer> keys = dict.keySet();
		Iterator<Integer> it = keys.iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			excelService.addDic(dictConfigName, String.valueOf(key), dict.get(key));
		}

	}

	public static void addImportExportDicts(ImportExcelService<? extends BaseExcelVo> excelService,
			String dictConfigName, Map<Integer, String> dict) {
		if (excelService == null) {
			throw new RuntimeException("excelService不存在");
		}
		Set<Integer> keys = dict.keySet();
		Iterator<Integer> it = keys.iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			excelService.addDic(dictConfigName, String.valueOf(key), dict.get(key));
		}

	}

	public static List<ErmSchoolExcelEntity> convertSchoolExcelList(List<ErmSchoolEntity> ermSchoolList) {
		if (ermSchoolList == null || ermSchoolList.size() <= 0) {
			return null;
		}
		List<ErmSchoolExcelEntity> excelEntityList = new ArrayList<ErmSchoolExcelEntity>();
		int i=1;
		for (ErmSchoolEntity school : ermSchoolList) {
			try {
				ErmSchoolExcelEntity excelEntity = new ErmSchoolExcelEntity();
				excelEntity.setNo(i);
				BeanUtils.copyProperties(excelEntity, school);
				excelEntityList.add(excelEntity);
				i++;
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return excelEntityList;
	}

	public static void downStudentModelXLS(HttpServletRequest request, HttpServletResponse response, String fileName,
			List<ErmStudentExcelEntity> stuLists, List<String> titleNames, String title, String sheetName,
			List<ErmDictEntity> dictList) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			setDownLoadResponseHeader(request, response, fileName);
			downStudentXLSModel(out, stuLists, titleNames, sheetName, dictList);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void downStudentXLSModel(OutputStream out, List<ErmStudentExcelEntity> stuLists,
			List<String> titleNames, String sheetName, List<ErmDictEntity> dictList) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		ExportExcelService service = null;
		// List<ErmStudentExcelEntity> list = new ArrayList<ErmStudentExcelEntity>();
		// list.add(new ErmStudentExcelEntity());
		String[] headArr = new String[titleNames.size()];
		titleNames.toArray(headArr);
		try {
			service = new ExportExcelService(stuLists, sheet, headArr, ExcelConstants.STUDENT_TITLE);

			// for (int i = 0; i < dictList.size(); i++) {
			// ErmDictEntity dict = dictList.get(i);
			// service.addDic(String.valueOf(dict.getType()), dict.getDictCode(),
			// dict.getDictName());
			// }
			service.doExport();
			service.exportTemplate();
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<ErmStudentExcelEntity> convertStudentEntity(List<ErmStudentEntity> stuLists) {
		if (stuLists == null || stuLists.size() <= 0) {
			return null;
		}
		List<ErmStudentExcelEntity> excelEntityList = new ArrayList<ErmStudentExcelEntity>();
		int i=1;
		for (ErmStudentEntity student : stuLists) {
			try {
				ErmStudentExcelEntity excelEntity = new ErmStudentExcelEntity();
				excelEntity.setNo(i);
				BeanUtils.copyProperties(excelEntity, student);
				excelEntityList.add(excelEntity);
				i++;
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return excelEntityList;
	}

	/**
	 * 根据类型导出报表
	 * 
	 * @param request
	 * @param response
	 * @param fileName
	 * @param reportListMap
	 * @param reportType
	 * @param dynamicInfo
	 * @param studentType
	 * @param diffStudentHeader
	 */
	public static void downLoadExcelForMap(HttpServletRequest request, HttpServletResponse response,
			List<Map<String, Object>> reportListMap, String reportType, Map<String, Object> dynamicInfo,
			String studentType, List<ColumnHeader> diffStudentHeader) {
		String suffix = ".xls";
		if (Constants.REPORT_TYPE_STUDENT.equals(reportType)) {
			if (dynamicInfo == null) {
				if (StringUtils.isNotBlank(studentType)) {
					if ("base".equals(studentType)) {
						downLoadExcelForMap(request, response,
								ExcelConstants.REPORT_NAME_BASE_STUDENT + System.currentTimeMillis() + suffix,
								reportListMap, Constants.BASE_STUDENT_REPORT_KEYS, Constants.BASE_STUDENT_REPORT_NAMES,
								Constants.BASE_STUDENT_REPORT_VALIDS, ExcelConstants.REPORT_NAME_BASE_STUDENT,
								ExcelConstants.REPORT_SHEET_STUDENT);
					} else if ("diff".equals(studentType)) {
						List<String> keys = new ArrayList<String>();
						keys.addAll(Arrays.asList(Constants.DIFF_STUDENT_REPORT_KEYS));
						List<String> names = new ArrayList<String>();
						names.addAll(Arrays.asList(Constants.DIFF_STUDENT_REPORT_NAMES));
						if (diffStudentHeader != null && diffStudentHeader.size() > 0) {
							for (ColumnHeader header : diffStudentHeader) {
								keys.add(header.getColCode());
								names.add(header.getColName());
							}
							String[] reportKeys = new String[keys.size()];
							reportKeys = keys.toArray(reportKeys);
							String[] reportValues = new String[names.size()];
							reportValues = names.toArray(reportValues);
							downLoadExcelForMap(request, response,
									ExcelConstants.REPORT_NAME_DIFF_STUDENT + System.currentTimeMillis() + suffix,
									reportListMap, reportKeys, reportValues, Constants.DIFF_STUDENT_REPORT_VALIDS,
									ExcelConstants.REPORT_NAME_DIFF_STUDENT, ExcelConstants.REPORT_SHEET_STUDENT);
						} else {
							downLoadExcelForMap(request, response,
									ExcelConstants.REPORT_NAME_DIFF_STUDENT + System.currentTimeMillis() + suffix,
									reportListMap, Constants.DIFF_STUDENT_REPORT_KEYS,
									Constants.DIFF_STUDENT_REPORT_NAMES, Constants.DIFF_STUDENT_REPORT_VALIDS,
									ExcelConstants.REPORT_NAME_DIFF_STUDENT, ExcelConstants.REPORT_SHEET_STUDENT);
						}
					} else if ("publicStu".equals(studentType)) {
						downLoadExcelForMap(request, response,
								ExcelConstants.REPORT_NAME_PUBLIC_STUDENT + System.currentTimeMillis() + suffix,
								reportListMap, Constants.PUBLIC_STUDENT_REPORT_KEYS,
								Constants.PUBLIC_STUDENT_REPORT_NAMES, Constants.PUBLIC_STUDENT_REPORT_VALIDS,
								ExcelConstants.REPORT_NAME_PUBLIC_STUDENT, ExcelConstants.REPORT_SHEET_STUDENT);
					} else if ("funding".equals(studentType)) {
						downLoadExcelForMap(request, response,
								ExcelConstants.REPORT_NAME_FUNDING_STUDENT + System.currentTimeMillis() + suffix,
								reportListMap, Constants.FUNDING_STUDENT_REPORT_KEYS,
								Constants.FUNDING_STUDENT_REPORT_NAMES, Constants.FUNDING_STUDENT_REPORT_VALIDS,
								ExcelConstants.REPORT_NAME_FUNDING_STUDENT, ExcelConstants.REPORT_SHEET_STUDENT);
					}
				} else {
					downLoadExcelForMap(request, response,
							ExcelConstants.REPORT_NAME_STUDENT + System.currentTimeMillis() + suffix, reportListMap,
							Constants.STUDENT_REPORT_KEYS, Constants.STUDENT_REPORT_NAMES,
							Constants.STUDENT_REPORT_VALIDS, ExcelConstants.REPORT_NAME_STUDENT,
							ExcelConstants.REPORT_SHEET_STUDENT);
				}
			} else {
				List<String> keys = (List<String>) dynamicInfo.get("keys");
				List<String> values = (List<String>) dynamicInfo.get("values");
				List<Map<String, Object>> data = (List<Map<String, Object>>) dynamicInfo.get("data");
				String[] reportKeys = new String[keys.size()];
				reportKeys = keys.toArray(reportKeys);
				String[] reportValues = new String[values.size()];
				reportValues = values.toArray(reportValues);
				downLoadExcelForMap(request, response,
						ExcelConstants.REPORT_NAME_STUDENT + System.currentTimeMillis() + suffix, data, reportKeys,
						reportValues, null, ExcelConstants.REPORT_NAME_STUDENT, ExcelConstants.REPORT_SHEET_STUDENT);
			}

		}
		if (Constants.REPORT_TYPE_SHCOOL.equals(reportType)) {
			downLoadExcelForMap(request, response,
					ExcelConstants.REPORT_NAME_SCHOOL + System.currentTimeMillis() + suffix, reportListMap,
					Constants.SCHOOL_REPORT_KEYS, Constants.SCHOOL_REPORT_NAMES, Constants.SCHOOL_REPORT_VALIDS,
					ExcelConstants.REPORT_NAME_SCHOOL, ExcelConstants.REPORT_SHEET_SCHOOL);
		}
		if (Constants.REPORT_TYPE_SHCOOL_TYPE.equals(reportType)) {
			downLoadExcelForMap(request, response,
					ExcelConstants.REPORT_NAME_SCHOOL_TYPE + System.currentTimeMillis() + suffix, reportListMap,
					Constants.SCHOOL_TYPE_REPORT_KEYS, Constants.SCHOOL_TYPE_REPORT_NAMES,
					Constants.SCHOOL_TYPE_REPORT_VALIDS, ExcelConstants.REPORT_NAME_SCHOOL_TYPE,
					ExcelConstants.REPORT_SHEET_SCHOOL_TYPE);
		}

		if (Constants.REPORT_TYPE_FUNDED_TYPE.equals(reportType)) {
			downLoadExcelForMap(request, response,
					ExcelConstants.REPORT_NAME_FUNDED + System.currentTimeMillis() + suffix, reportListMap,
					Constants.FUNDED_TYPE_REPORT_KEYS, Constants.FUNDED_TYPE_REPORT_NAMES,
					Constants.FUNDED_TYPE_REPORT_VALIDS, ExcelConstants.REPORT_NAME_FUNDED,
					ExcelConstants.REPORT_SHEET_FUNDED);
		}
		if (Constants.REPORT_TYPE_FUNDED.equals(reportType)) {
			downLoadExcelForMap(request, response,
					ExcelConstants.REPORT_NAME_FUNDED + System.currentTimeMillis() + suffix, reportListMap,
					Constants.FUNDED_REPORT_KEYS, Constants.FUNDED_REPORT_NAMES, Constants.FUNDED_REPORT_VALIDS,
					ExcelConstants.REPORT_NAME_FUNDED, ExcelConstants.REPORT_SHEET_FUNDED);
		}
		if (Constants.APPLY_STUDENT.equals(reportType)) {
			downLoadExcelForMap(request, response,
					ExcelConstants.APPLY_STUDENT_NAME + System.currentTimeMillis() + suffix, reportListMap,
					Constants.APPLY_STUDENT_REPORT_KEYS, Constants.APPLY_STUDENT_REPORT_NAMES,
					Constants.APPLY_STUDENT_REPORT_VALIDS, ExcelConstants.APPLY_STUDENT_NAME,
					ExcelConstants.APPLY_STUDENT_SHEET);
		}
		if (Constants.FUNDING_STUDENT.equals(reportType)) {
			downLoadExcelForMap(request, response,
					ExcelConstants.FUNDING_STUDENT_NAME + System.currentTimeMillis() + suffix, reportListMap,
					Constants.APPLY_STUDENT_REPORT_KEYS, Constants.APPLY_STUDENT_REPORT_NAMES,
					Constants.APPLY_STUDENT_REPORT_VALIDS, ExcelConstants.FUNDING_STUDENT_NAME,
					ExcelConstants.FUNDING_STUDENT_SHEET);
		}
		if (Constants.INFO_QUERY_TYPE.equals(reportType)) {
			if (dynamicInfo == null) {
				downLoadExcelForMap(request, response,
						ExcelConstants.INFO_QUERY_NAME + System.currentTimeMillis() + suffix, reportListMap,
						Constants.INFO_QUERY_KEYS, Constants.INFO_QUERY_NAMES, Constants.INFO_QUERY_VALIDS,
						ExcelConstants.INFO_QUERY_NAME, ExcelConstants.INFO_QUERY_SHEET);
			} else {
				List<String> keys = (List<String>) dynamicInfo.get("keys");
				List<String> values = (List<String>) dynamicInfo.get("values");
				List<Map<String, Object>> data = (List<Map<String, Object>>) dynamicInfo.get("data");
				String[] reportKeys = new String[keys.size()];
				reportKeys = keys.toArray(reportKeys);
				String[] reportValues = new String[values.size()];
				reportValues = values.toArray(reportValues);
				downLoadExcelForMap(request, response,
						ExcelConstants.INFO_QUERY_NAME + System.currentTimeMillis() + suffix, data, reportKeys,
						reportValues, null, ExcelConstants.INFO_QUERY_NAME, ExcelConstants.INFO_QUERY_SHEET);
			}
		}

	}

	/**
	 * 获取下载模板下拉框数据
	 * 
	 * @param schoolFieldHeader
	 * @return
	 */
	public static Map<String, Object> getDownData(List<ColumnHeader> headers) {

		List<String> selectIndex = new ArrayList<String>();
		List<String[]> downData = new ArrayList<String[]>();
		List<String> titles = new ArrayList<String>();
		// titles.add(0, "银行卡号");
		// titles.add(0, "本次发放资金");
		// titles.add(0, "填报日期");
		// titles.add(0, "学号");
//		titles.add(0, "贫困类型");
		titles.add(0, "身份证");
		titles.add(0, "姓名");

		int initIndex = titles.size();
		Map<String, Object> result = new HashMap<String, Object>();
		if (headers != null && headers.size() > 0) {
			for (int i = 0; i < headers.size(); i++) {
				ColumnHeader header = headers.get(i);
				titles.add(header.getColName());
				if (header.isSelect() && header.getSelectInfo() != null) {
					String[] valArr = new String[header.getSelectInfo().size()];
					valArr = header.getSelectInfo().toArray(valArr);
					selectIndex.add(String.valueOf(i + initIndex));
					downData.add(valArr);
				}
			}
		}
	//	titles.add("帮扶负责人");
		if (selectIndex.size() > 0) {
			String[] indexArr = new String[selectIndex.size()];
			indexArr = selectIndex.toArray(indexArr);
			result.put("downRows", indexArr);

		}
		String[] titleArr = new String[titles.size()];

		titleArr = titles.toArray(titleArr);
		result.put("titles", titleArr);
		result.put("downData", downData);
		return result;
	}

	public static List<ErmAdminExcelEntity> convertAdminExcelList(List<ErmAdminEntity> ermAdminList) {
		if (ermAdminList == null || ermAdminList.size() <= 0) {
			return null;
		}
		List<ErmAdminExcelEntity> excelEntityList = new ArrayList<ErmAdminExcelEntity>();
		int i=1;
		for (ErmAdminEntity admin : ermAdminList) {
			try {
				ErmAdminExcelEntity excelEntity = new ErmAdminExcelEntity();
				excelEntity.setNo(i);
				BeanUtils.copyProperties(excelEntity, admin);
				excelEntityList.add(excelEntity);
				i++;
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return excelEntityList;
	}

	public static void downLoadAdminExcelForVO(HttpServletRequest request, HttpServletResponse response,
			String fileName, List<ErmAdminExcelEntity> adminExcelList) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			setDownLoadResponseHeader(request, response, fileName);
			downAdminExcelForListMap(out, adminExcelList);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void downAdminExcelForListMap(OutputStream out, List<ErmAdminExcelEntity> adminExcelList) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("adminSheet");
		ExportExcelService service = null;
		List<String> headerList = new ArrayList<String>();
		headerList.add("No");
		headerList.add("schoolName");
		headerList.add("username");
		headerList.add("password");
		headerList.add("roleName");
		String[] headArr = new String[headerList.size()];
		headerList.toArray(headArr);
		try {
			service = new ExportExcelService(adminExcelList, sheet, headArr, "账号信息列表(初始密码123456，请及时修改密码)");
			service.doExport();
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 替换Excel模板文件内容
	 * @param item 文档数据
	 * @param sourceFilePath Excel模板文件路径
	 * @param targetFilePath Excel生成文件路径
	 */
	public static boolean replaceModel(Map item, String sourceFilePath, String targetFilePath) {
		boolean bool = true;
		try {

			POIFSFileSystem fs  =new POIFSFileSystem(new FileInputStream(sourceFilePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			while(rows.hasNext()){
				HSSFRow row = (HSSFRow) rows.next();
				if(row!=null) {
					int num = row.getLastCellNum();
					for(int i=0;i<num;i++) {
						HSSFCell cell=  row.getCell(i);
						if(cell!=null) {
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						}
						if(cell==null || cell.getStringCellValue()==null) {
							continue;
						}
						String value= cell.getStringCellValue();
						if(!"".equals(value)) {
							Set<String> keySet = item.keySet();
							Iterator<String> it = keySet.iterator();
							while (it.hasNext()) {
								String text = it.next();
								if(value.equalsIgnoreCase(text)) {
									cell.setCellValue((String)item.get(text));
									break;
								}
							}
						} else {
							cell.setCellValue("");
						}
					}
				}
			}

			// 输出文件
			FileOutputStream fileOut = new FileOutputStream(targetFilePath);
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			bool = false;
			e.printStackTrace();
		}
		return bool;
	}
}
