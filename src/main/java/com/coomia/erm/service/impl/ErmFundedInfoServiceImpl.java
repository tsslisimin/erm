package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coomia.erm.dao.ErmStudentDao;
import com.coomia.erm.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.Constants;
import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.StuDictConstants;
import com.coomia.erm.dao.ErmFieldDao;
import com.coomia.erm.dao.ErmFundedInfoDao;
import com.coomia.erm.dao.ErmSchoolFundedDao;
import com.coomia.erm.service.ErmDictService;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.util.Query;

import io.jsonwebtoken.lang.Assert;

@Service("ermFundedInfoService")
public class ErmFundedInfoServiceImpl implements ErmFundedInfoService {
	@Autowired
	private ErmFundedInfoDao ermFundedInfoDao;

	@Autowired
	private ErmSchoolFundedDao ermSchooFundedDao;

	@Autowired
	private ErmFieldDao ermFieldDao;
	
	@Autowired
	private ErmDictService ermDictService;
	@Autowired
	private ErmStudentDao ermStudentDao;

	@Override
	public ErmFundedInfoEntity queryObject(Integer id) {
		return ermFundedInfoDao.queryObject(id);
	}

	@Override
	public List<ErmFundedInfoEntity> queryList(Map<String, Object> map) {
		return ermFundedInfoDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermFundedInfoDao.queryTotal(map);
	}

	@Override
	public void save(ErmFundedInfoEntity ermFundedInfo) {
		ermFundedInfo.setCreateTime(new Date());
		ErmSchoolFundedEntity schEntity = this.ermSchooFundedDao.queryObject(ermFundedInfo.getFundedId());
		if (schEntity != null && schEntity.getTotalMoney() != null && schEntity.getCount() != 0) {
			ermFundedInfo.setMoney(schEntity.getTotalMoney() / schEntity.getCount());
		}
		ermFundedInfoDao.save(ermFundedInfo);
	}

	@Override
	public void update(ErmFundedInfoEntity ermFundedInfo) {
		ermFundedInfo.setUpdateTime(new Date());
		ermFundedInfoDao.update(ermFundedInfo);
	}

	@Override
	public void delete(Integer id) {
		ermFundedInfoDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		ermFundedInfoDao.deleteBatch(ids);
	}

	@Override
	public List<Map<String, Object>> queryMapList(Query query) {

		return ermFundedInfoDao.queryMapList(query);
	}

	@Override
	public int queryMapListTotal(Query query) {
		return this.ermFundedInfoDao.queryMapListTotal(query);
	}

	@Override
	public int queryFundCount(int schoolId, int fundId, int status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("schoolId", schoolId);
		param.put("fundId", fundId);
		return ermFundedInfoDao.queryFundCountByFund(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.coomia.erm.service.ErmFundedInfoService#queryFundCountByStuFund(int,
	 * int, int)
	 */
	@Override
	public int queryAuditSuccCountByStuFund(int stuFundId, int status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("stuFundId", stuFundId);
		return ermFundedInfoDao.queryAuditSuccCountByStuFund(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmFundedInfoService#queryRemainCountByStuFund(int,
	 * int)
	 */
	@Override
	public int queryRemainCountByStuFund(int stuFundId, int status, int schId, int schFundId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("stuFundId", stuFundId);
		param.put("schId", schId);
		param.put("schFundId", schFundId);
		return ermFundedInfoDao.queryRemainCoutByStuFund(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmFundedInfoService#queryStuFundBySchFund(java.lang.
	 * Integer, java.lang.Integer)
	 */
	@Override
	public ErmFundedInfoEntity queryStuFundBySchFund(Integer schFundId, Integer stuId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stuId", stuId);
		param.put("schFundId", schFundId);
		return ermFundedInfoDao.queryStuFundByMap(param);
	}

	@Override
	public List<Map<String, Object>> queryFundedInfoMapList(Query query, Map<String, Map<Integer, String>> dict) {
		String studentType = (String) query.get("studentType");
		List<Map<String, Object>> fundedInfo = null;
		// 如果是查询学生基本信息
		if (StringUtils.isNotBlank(studentType) && "base".equals(studentType)) {
			fundedInfo = this.ermFundedInfoDao.queryBaseStudentMapList(query);
		} else {
			if ("diff".equals(studentType) && query.get("status") == null) {
				query.put("diffLevel", "all");
				query.put("headTeachCheck", "1");
			}
			fundedInfo = this.ermFundedInfoDao.queryFundedInfoMapList(query);
		}
		if (fundedInfo == null || fundedInfo.size() <= 0) {
			return null;
		}
		operateReport(fundedInfo, dict);
		String extendField = (String) query.get("extendField");
		if ("extend".equals(extendField)) {
			for (Map<String, Object> stuFuned : fundedInfo) {
				Integer stuId = (Integer) stuFuned.get("stu_id");
				List<Map<String, Object>> stuField = this.ermFieldDao.queryStuFields(stuId);
				if (stuField != null && stuField.size() > 0) {
					for (Map<String, Object> stu : stuField) {
						stuFuned.put((String) stu.get("field_code"), stu.get("field_val_val"));
					}
				}
			}
		}
		return fundedInfo;
	}

	@Override
	public int queryFundedInfoMapListTotal(Query query) {
		String studentType = (String) query.get("studentType");
		// 如果是查询学生基本信息
		if (StringUtils.isNotBlank(studentType) && "base".equals(studentType)) {
			return this.ermFundedInfoDao.queryBaseStudentMapListTotal(query);
		} else {
			if ("diff".equals(studentType)) {
				query.put("status", "all");
			}
			return this.ermFundedInfoDao.queryFundedInfoMapListTotal(query);
		}
	}

	@Override
	public List<Map<String, Object>> queryfundedSchoolReport(Query query, Map<String, Map<Integer, String>> dict) {
		List<Map<String, Object>> schoolReport = this.ermFundedInfoDao.queryfundedSchoolReport(query);
		if (schoolReport == null || schoolReport.size() <= 0) {
			return null;
		}
		operateReport(schoolReport, dict);
		return schoolReport;
	}

	private void operateReport(List<Map<String, Object>> reports, Map<String, Map<Integer, String>> dict) {
	  Map<String, Object> semesterMap = this.ermDictService.getSemesterMap();
		if (dict != null) {
			for (int i = 0; i < reports.size(); i++) {
				Map<String, Object> report = reports.get(i);
				report.put("id", i + 1);
				Set<String> keys = report.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					String key = it.next();
					if (dict.containsKey(key)) {
						Map<Integer, String> dictMap = dict.get(key);
						report.put(key, dictMap.get(Integer.parseInt((String) report.get(key))));
						
					}
					if ("is_poor".equals(key) || "schzone".equals(key) || "level".equals(key)) {
					  if(null != StuDictConstants.STU_DICT_MAP.get((String) report.get(key)))
					      report.put(key, StuDictConstants.STU_DICT_MAP.get((String) report.get(key)));
					}
					else if ("semester".equals(key) && semesterMap.get((String) report.get(key)) != null) {
					  report.put(key, semesterMap.get((String) report.get(key)));
					}
					if ("global_status".equals(key)) {
						Integer code = (Integer) report.get(key);
						report.put(key, FundStatus.getCNName(code));
					}
				}
			}
		}
	}

	@Override
	public int queryfundedSchoolReportTotal(Query query) {
		return this.ermFundedInfoDao.queryfundedSchoolReportTotal(query);
	}

	@Override
	public List<Map<String, Object>> queryfundedTypeReport(Query query, Map<String, Map<Integer, String>> dict) {
		List<Map<String, Object>> fundedTypeReport = this.ermFundedInfoDao.queryfundedTypeReport(query);
		if (fundedTypeReport == null || fundedTypeReport.size() <= 0) {
			return null;
		}
		List<Map<String, Object>> removeList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> fundedType : fundedTypeReport) {
			if (!fundedType.containsKey("sum_money") && (Long) fundedType.get("stu_count") == 0) {
				removeList.add(fundedType);
			}
		}
		fundedTypeReport.removeAll(removeList);
		operateReport(fundedTypeReport, dict);
		return fundedTypeReport;
	}

	@Override
	public int queryfundedTypeReportTotal(Query query) {
		return this.ermFundedInfoDao.queryfundedTypeReportTotal(query);
	}

	@Override
	public Map<String, Object> getEchartsDataMap(List<Map<String, Object>> ermFunedInfoListMap, String xKey,
			String yKey) {
		if (ermFunedInfoListMap == null || ermFunedInfoListMap.size() <= 0) {
			return null;
		}
		List<String> xData = new ArrayList<String>();
		List<Object> yData = new ArrayList<Object>();
		for (Map<String, Object> map : ermFunedInfoListMap) {
			if (map.containsKey(xKey)) {
				xData.add((String) map.get(xKey));
			}
			if (map.containsKey(yKey)) {
				yData.add(map.get(yKey));
			}
		}
		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("xData", xData);
		resultData.put("yData", yData);
		return resultData;
	}

	@Override
	public List<Map<String, Object>> getEchartsPieDataListMap(List<Map<String, Object>> ermFunedInfoListMap,
			String xKey, String yKey) {
		if (ermFunedInfoListMap == null || ermFunedInfoListMap.size() <= 0) {
			return null;
		}
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : ermFunedInfoListMap) {
			Map<String, Object> data = null;
			if (map.containsKey(xKey)) {
				data = new HashMap<String, Object>();
				data.put("name", map.get(xKey));
			}
			if (map.containsKey(yKey)) {
				if (null == data)
					data = new HashMap<String, Object>();
				if (null != map.get(yKey) && !map.get(yKey).equals("0"))
					data.put("value", map.get(yKey));
			}
			if (data.containsKey("name") && data.containsKey("value")) {
				dataList.add(data);
			}
		}
		return dataList;
	}

	@Override
	public List<Map<String, Object>> queryReportExportListMap(Query query, String reportType) {
		List<Map<String, Object>> reportListMap = null;
		if (Constants.REPORT_TYPE_STUDENT.equals(reportType) || (Constants.INFO_QUERY_TYPE.equals(reportType))) {
			Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
			dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
			dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
			reportListMap = this.queryFundedInfoMapList(query, dict);
		}
		if (Constants.REPORT_TYPE_SHCOOL.equals(reportType) || Constants.REPORT_TYPE_SHCOOL_TYPE.equals(reportType)) {
			Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
			dict.put("school_type", DictConstants.SCHOOL_TYPE_MAP);
			String groupByField = (String) query.get("groupByField");
			if (StringUtils.isBlank(groupByField)) {
				query.put("groupByField", "id");
			}
			reportListMap = this.queryfundedSchoolReport(query, dict);
		}
		if (Constants.REPORT_TYPE_FUNDED_TYPE.equals(reportType)) {
			Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
			dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
			String groupByField = (String) query.get("groupByField");
			if (StringUtils.isBlank(groupByField)) {
				query.put("groupByField", "funded");
			}
			reportListMap = this.queryfundedTypeReport(query, dict);
		}
		if (Constants.REPORT_TYPE_FUNDED.equals(reportType)) {
			Map<String, Map<Integer, String>> dict = new HashMap<String, Map<Integer, String>>();
			dict.put("funded_type", DictConstants.FUNDED_TYPE_MAP);
			String groupByField = (String) query.get("groupByField");
			if (StringUtils.isBlank(groupByField)) {
				query.put("groupByField", "type");
			}
			reportListMap = this.queryfundedTypeReport(query, dict);
		}
		return reportListMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmFundedInfoService#queryAppliedStudentsCount(java.
	 * lang.Integer, int)
	 */
	@Override
	public int queryAppliedStudentsCount(Integer schoolId, int year) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schoolId", schoolId);
		param.put("year", year);
		return ermFundedInfoDao.queryFundCountByFund(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmFundedInfoService#queryFundSuccessStudentCount(java
	 * .lang.Integer, int)
	 */
	@Override
	public int queryFundSuccessStudentCount(Integer schoolId, int year) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schoolId", schoolId);
		param.put("year", year);
		param.put("status", FundStatus.EBAUDITSUCCESS.getCode());
		return ermFundedInfoDao.queryFundCountByFund(param);
	}

	/**
	 * 
	 */
	public int queryToAuditUserCount(int year, int status, Integer schId, Integer fundId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schId", schId);
		param.put("year", year);
		param.put("status", status);
		param.put("fundId", fundId);
		return ermFundedInfoDao.queryToAuditUserCount(param);
	}

	@Override
	public List<Integer> getReportYears(Map<String, Object> params) {
		return this.ermFundedInfoDao.getReportYears(params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmFundedInfoService#queryRemainCountBySchFund(int)
	 */
	@Override
	public int queryRemainCountBySchFund(int schFundId, int status) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("status", status);
		param.put("schFundId", schFundId);
		Integer re = ermFundedInfoDao.queryRemainCoutBySchFund(param);
		return re == null ? 0 : re;
	}

	@Override
	public Map<String, Object> queryEBViewData(Integer ebId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> ebViewData = this.ermFundedInfoDao.queryEBViewData(ebId);
		Map<Integer, String> schoolTypeMap = DictConstants.SCHOOL_TYPE_MAP;
		Set<Integer> schoolType = schoolTypeMap.keySet();
		Iterator<Integer> it = schoolType.iterator();
		while (it.hasNext()) {
			Integer type = it.next();
			boolean flag = false;
			for (int i = 0; i < ebViewData.size(); i++) {
				Map<String, Object> data = ebViewData.get(i);
				if (String.valueOf(type).equals(data.get("type"))) {
					data.put("typeName", schoolTypeMap.get(type));
					flag = true;
					result.put(String.valueOf(type), data);
					break;
				}
			}
			if (!flag) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", type);
				map.put("typeName", schoolTypeMap.get(type));
				map.put("stuCount", 0);
				map.put("ct", 0);
				map.put("auditCt", 0);
				result.put(String.valueOf(type), map);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.coomia.erm.service.ErmFundedInfoService#queryRemainUndeltCount(int,
	 * int)
	 */
	@Override
	public int queryRemainUndeltCount(int schFundId, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schFundId", schFundId);
		map.put("status", status);
		return ermFundedInfoDao.queryRemainUndeltCount(map);
	}

	@Override
	public Map<String, Object> getDynamicInfo(String cloumnFields, List<Map<String, Object>> reportListMap) {
		if (cloumnFields == null || reportListMap == null) {
			return null;
		}
		String fields[] = cloumnFields.split(",");
		List<String> keys = Arrays.asList(fields);
		List<String> values = new ArrayList<String>();
		List<Map<String, Object>> dataListMap = new ArrayList<Map<String, Object>>();
		for (String key : keys) {
			values.add(DictConstants.COLUMN_MAP.get(key));
		}
		for (Map<String, Object> map : reportListMap) {
			Map<String, Object> data = new HashMap<String, Object>();
			for (String key : keys) {
				if (map.containsKey(key)) {
					data.put(key, map.get(key));
				}
			}
			dataListMap.add(data);
		}
		Map<String, Object> dynamicInfo = new HashMap<String, Object>();
		dynamicInfo.put("keys", keys);
		dynamicInfo.put("values", values);
		dynamicInfo.put("data", dataListMap);
		return dynamicInfo;
	}

	@Override
	public List<Map<String, Object>> queryStudentFundinfoList(Map<String, Object> params,
			Map<String, Map<Integer, String>> dict) {
		ErmStudentEntity studentId = ermStudentDao.queryObject(params.get("studentId"));
		params.put("idcard",studentId.getIdcard());
		params.put("name",studentId.getName());
		List<Map<String, Object>> fundedInfo = null;
		fundedInfo = this.ermFundedInfoDao.queryStudentFundinfoList(params);
		if (fundedInfo == null || fundedInfo.size() <= 0) {
			return null;
		}
		operateReport(fundedInfo, dict);
		return fundedInfo;
	}

	@Override
	public int queryStudentFundinfoListTotal(Map<String, Object> params) {
		return this.ermFundedInfoDao.queryStudentFundinfoListTotal(params);
	}

	@Override
	public List<ColumnHeader> querydiffStudentHeader(List<ColumnHeader> tableHeader, String schId) {
		if (tableHeader == null) {
			tableHeader = new ArrayList<ColumnHeader>();
		}
		List<ErmFieldEntity> fields = this.ermFieldDao.queryStuFieldList(schId);
		if (fields != null && fields.size() > 0) {
			for (ErmFieldEntity field : fields) {
				ColumnHeader header = new ColumnHeader(field.getFieldCode(), field.getFieldName(), true);
				tableHeader.add(header);
			}
		}
		return tableHeader;
	}

	@Override
	public List<Map<String, Object>> fundedStudentById(Map<String, Object> params) {
		Assert.notNull(params.get("studentId"));
		// Assert.notNull(params.get("schFundedId"));
		Integer stuId = Integer.parseInt((String) params.get("studentId"));
		List<Map<String, Object>> stuField = this.ermFieldDao.queryStuFields(stuId);
		List<Map<String, Object>> resultList = new ArrayList<>();
		// 获取学生自定义字段内容
		for (Map<String, Object> stu : stuField) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("key", stu.get("field_name"));
			result.put("value", stu.get("field_val_val"));
			resultList.add(result);
		}

		return resultList;
	}

	@Override
	public ErmFundedInfoEntity queryObject(Integer schFundId, String idcard, Integer schId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schFundId", schFundId);
		map.put("idcard", idcard);
		map.put("schId", schId);
		return ermFundedInfoDao.queryStuFundInfoByStudentMap(map);
	}

	@Override
	public List<Map<String, Object>> fundedStudentImgById(Map<String, Object> params) {
		Assert.notNull(params.get("studentId"));
		Assert.notNull(params.get("schFundedId"));
		Integer stuId = Integer.parseInt((String) params.get("studentId"));
		Integer schFundedId = Integer.parseInt((String) params.get("schFundedId"));
		List<Map<String, Object>> resultList = new ArrayList<>();
		// 学生申请表图片
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("stuId", stuId);
		param.put("schFundId", schFundedId);
		ErmFundedInfoEntity stuFundedInfo = this.ermFundedInfoDao.queryStuFundByMap(param);
		if (stuFundedInfo != null) {
			Map<String, Object> stuAppUrl = new HashMap<String, Object>();
			stuAppUrl.put("key", "applypicurl");
			stuAppUrl.put("value", stuFundedInfo.getApplypicurl() == null ? " " : stuFundedInfo.getApplypicurl());
			resultList.add(stuAppUrl);
		}
		// 公示图片
		ErmSchoolFundedEntity schFunded = this.ermSchooFundedDao.queryObject(schFundedId);
		if (schFunded != null) {
			Map<String, Object> publicUrl = new HashMap<String, Object>();
			publicUrl.put("key", "publicUrl");
			publicUrl.put("value", schFunded.getPublicUrl() == null ? " " : schFunded.getPublicUrl());
			resultList.add(publicUrl);
		}
		return resultList;
	}

}
