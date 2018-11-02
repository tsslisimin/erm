package com.coomia.erm.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jplus.hyberbin.excel.service.ExportExcelService;
import org.jplus.hyberbin.excel.service.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.dao.ErmDictDao;
import com.coomia.erm.dao.ErmFundedDao;
import com.coomia.erm.dao.ErmReportDao;
import com.coomia.erm.dao.ErmSchoolDao;
import com.coomia.erm.dao.ErmSchoolFundedDao;
import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.ErmReportEntity;
import com.coomia.erm.entity.ErmSchFundExt;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.entity.excel.ErmSchoolFundedExcelEntity;
import com.coomia.erm.entity.excel.ErmSchoolFundedExcelEntityHigh;
import com.coomia.erm.entity.excel.ErmSchoolFundedExcelEntityMiddle;
import com.coomia.erm.service.ErmFieldService;
import com.coomia.erm.service.ErmSchFieldService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.service.ErmSchoolService;
import com.coomia.erm.util.ExcelUtil;
import com.coomia.erm.util.Query;

@Service("ermSchoolFundedService")
public class ErmSchoolFundedServiceImpl implements ErmSchoolFundedService {
	@Autowired
	private ErmSchoolFundedDao ermSchoolFundedDao;

	@Autowired
	private ErmSchoolDao ermSchoolDao;

	@Autowired
	private ErmSchoolService ermSchoolService;

	@Autowired
	private ErmFundedDao ermFundedDao;

	@Autowired
	private ErmReportDao ermReportDao;

	@Autowired
	private ErmFieldService ermFieldService;

	@Autowired
	private ErmSchFieldService ermSchFieldService;

	@Autowired
	private ErmDictDao ermDictDao;

	@Override
	public ErmSchoolFundedEntity queryObject(Integer id) {
		return ermSchoolFundedDao.queryObject(id);
	}

	@Override
	public List<ErmSchoolFundedEntity> queryList(Map<String, Object> map) {
		List<ErmSchoolFundedEntity> list = ermSchoolFundedDao.queryList(map);
		if (null != list && !list.isEmpty()) {
			for (ErmSchoolFundedEntity ermSchoolFundedEntity : list) {
				if (null != ermSchoolFundedEntity.getSchzone())
					ermSchoolFundedEntity
							.setSchzoneName(ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSchzone()));
				if (null != ermSchoolFundedEntity.getLevel())
					ermSchoolFundedEntity.setLevelName(ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getLevel()));
				if (null != ermSchoolFundedEntity.getSemester())
					ermSchoolFundedEntity
							.setSemesterName(ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()));
			}
		}
		return list;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermSchoolFundedDao.queryTotal(map);
	}

	@Override
	public void save(ErmSchoolFundedEntity ermSchoolFunded) {
		ermSchoolFunded.setCreateTime(new Date());
		generateReport(ermSchoolFunded);
		ermSchoolFunded.setPublicStatus(0);
		ermSchoolFundedDao.save(ermSchoolFunded);
		boolean hasInited = ermSchFieldService.hasInited(ermSchoolFunded.getSchoolId());
		if (!hasInited)
			ermSchFieldService.doInitSchFieldsConfig(ermSchoolFunded.getSchoolId());
	}

	@Override
	public void update(ErmSchoolFundedEntity ermSchoolFunded) {
		ermSchoolFunded.setUpdateTime(new Date());
		ermSchoolFundedDao.update(ermSchoolFunded);
	}

	@Override
	public void delete(Integer id) {
		ermSchoolFundedDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		ermSchoolFundedDao.deleteBatch(ids);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmSchoolFundedService#queryObjectByStuFundId(java.
	 * lang.Integer)
	 */
	@Override
	public ErmSchoolFundedEntity queryObjectByStuFundId(Integer stuFundId) {
		return ermSchoolFundedDao.queryObjectByStuFundId(stuFundId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmSchoolFundedService#queryObjectBySchFundId(java.
	 * lang.Integer, java.lang.Integer)
	 */
	@Override
	public ErmSchoolFundedEntity queryObjectByFundId(Integer fundId, Integer schid) {
		return queryObjectByFundId(fundId, schid, null, null);
	}

	@Override
	public Map<String, Object> importSchoolFundedInfo(InputStream input, Integer ebId, Integer fundedId) {
		Workbook workbook = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			workbook = new HSSFWorkbook(input);
			Sheet sheet = workbook.getSheet("schoolFunded");
			ErmFundedEntity funded = this.ermFundedDao.queryObject(fundedId);

			ImportExcelService service = null;
			if ("112".equals(funded.getSubtype())) {
				service = new ImportExcelService(ErmSchoolFundedExcelEntityMiddle.class, sheet);
			} else if ("113".equals(funded.getSubtype())) {
				service = new ImportExcelService(ErmSchoolFundedExcelEntityHigh.class, sheet);
			} else {
				service = new ImportExcelService(ErmSchoolFundedExcelEntity.class, sheet);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ebId", ebId);
			List<ErmSchoolEntity> schoolEntitys = this.ermSchoolDao.queryList(params);
			List<ErmFundedEntity> fundedEntity = this.ermFundedDao.queryList(params);
			ExcelUtil.addImportExportDicts(service, "XXID", getSchoolDict(schoolEntitys));
			ExcelUtil.addImportExportDicts(service, "XMID", getFundedDict(fundedEntity));
			if (funded.getSubtype().equals("112")) {
				params.put("type", "25");
				List<ErmDictEntity> dictEntitys = this.ermDictDao.queryList(params);
				for (ErmDictEntity entity : dictEntitys) {
					service.addDic("XXLX", entity.getDictCode(), entity.getDictName());
				}
			} else if (funded.getSubtype().equals("113")) {
				params.put("type", "24");
				List<ErmDictEntity> dictEntitys = this.ermDictDao.queryList(params);
				for (ErmDictEntity entity : dictEntitys) {
					service.addDic("XXJB", entity.getDictCode(), entity.getDictName());
				}
			}
			List<Object> list = service.doImport();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ErmSchoolFundedEntity schoolFundedEntity = new ErmSchoolFundedEntity();
					if (funded.getSubtype().equals("112")) {
						ErmSchoolFundedExcelEntityMiddle excelEntity = (ErmSchoolFundedExcelEntityMiddle) list.get(i);
						BeanUtils.copyProperties(schoolFundedEntity, excelEntity);
					} else if (funded.getSubtype().equals("113")) {
						ErmSchoolFundedExcelEntityHigh excelEntity = (ErmSchoolFundedExcelEntityHigh) list.get(i);
						BeanUtils.copyProperties(schoolFundedEntity, excelEntity);
					} else {
						ErmSchoolFundedExcelEntity excelEntity = (ErmSchoolFundedExcelEntity) list.get(i);
						BeanUtils.copyProperties(schoolFundedEntity, excelEntity);
					}
					// 如果没有设置人数，跳过
					if (null == schoolFundedEntity.getCount() || schoolFundedEntity.getCount() <= 0) {
						continue;
					}
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("schoolId", schoolFundedEntity.getSchoolId());
					param.put("fundId", fundedId);
					param.put("level", schoolFundedEntity.getLevel());
					// TODO query might return more than 1 rows.
					// 需要判断一下，如果导入的文件中有这个档次/学区 的记录，判断一下状态，如果状态在 FundStatus.CONFIG
					// 状态，需要update 金额或名额，如果没有记录，则添加记录。
					ErmSchoolFundedEntity tmp = this.ermSchoolFundedDao.queryObjectByFundId(param);
					if (tmp != null) {
						if (tmp.getStatus() == FundStatus.CONFIG.getCode()
								|| tmp.getStatus() == FundStatus.RUNNING.getCode()) {
							schoolFundedEntity.setId(tmp.getId());
							schoolFundedEntity.setStatus(FundStatus.CONFIG.getCode());
							if (null == schoolFundedEntity.getSemester()) {
								schoolFundedEntity.setSemester(funded.getSemester());
								schoolFundedEntity.setYear(funded.getYear());
							}
							this.update(schoolFundedEntity);
							if (tmp.getStatus() == FundStatus.RUNNING.getCode()) {
								generateReport(schoolFundedEntity);
							}
						}
					} else {
						schoolFundedEntity.setStatus(FundStatus.CONFIG.getCode());
						if (null == schoolFundedEntity.getSemester()) {
							schoolFundedEntity.setSemester(funded.getSemester());
							schoolFundedEntity.setYear(funded.getYear());
						}
						this.save(schoolFundedEntity);
						schoolFundedEntity.setSchoolIds(String.valueOf(schoolFundedEntity.getSchoolId()));
						this.batchUpdateFunded(schoolFundedEntity);
					}
				}
			}
			result.put("successNum", list.size());
			result.put("failNum", service.getErrorList().size());
			System.out.println("成功导入：" + list.size() + "条数据");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private Map<Integer, String> getSchoolDict(List<ErmSchoolEntity> schools) {
		Map<Integer, String> dict = new HashMap<Integer, String>();
		for (ErmSchoolEntity entity : schools) {
			dict.put(entity.getId(), entity.getName());
		}
		return dict;
	}

	private Map<String, String> getDictMap(List<ErmDictEntity> dict) {
		Map<String, String> dictMap = new HashMap<String, String>();
		for (ErmDictEntity entity : dict) {
			dictMap.put(entity.getDictCode(), entity.getDictName());
		}
		return dictMap;
	}

	private Map<Integer, String> getFundedDict(List<ErmFundedEntity> fundeds) {
		Map<Integer, String> dict = new HashMap<Integer, String>();
		for (ErmFundedEntity entity : fundeds) {
			dict.put(entity.getId(), entity.getName());
		}
		return dict;
	}

	@Override
	public void downLoadSchoolFundedTemplate(String fileName, Map<String, Object> params) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("schoolFunded");
		ExportExcelService service = null;

		Integer fundedId = Integer.parseInt((String) params.get("type"));
		ErmFundedEntity funded = this.ermFundedDao.queryObject(fundedId);
		if (params.containsKey("type")) {
			Integer type = fundedId;
			params.put("fundedId", type);
			params.put("fundName", funded.getName());
			//params.put("status", 4);
			params.remove("type");
		}
		List<String> headerList = new ArrayList<String>();
		headerList.add("fundedId");
		headerList.add("schoolId");
		if (funded.getSubtype().equals("112")) {
			headerList.add("schzone");
		} else if (funded.getSubtype().equals("113")) {
			headerList.add("level");
		}
		headerList.add("count");
		headerList.add("totalMoney");

		List<ErmSchoolEntity> schoolEntitys = this.ermSchoolService.queryInitSchoolsByFundId(fundedId);
		List<ErmFundedEntity> fundedEntity = this.ermFundedDao.queryList(params);
		String[] headArr = new String[headerList.size()];
		headerList.toArray(headArr);
		List<ErmSchoolFundedEntity> entityList = this.queryList(params);
		List<Object> downList = new ArrayList<>();
		for (ErmSchoolEntity entity : schoolEntitys) {
			if (funded.getSubtype().equals("112")) {
				ErmSchoolFundedExcelEntityMiddle excelEntity = new ErmSchoolFundedExcelEntityMiddle();
				excelEntity.setFundedId(funded.getId());
				excelEntity.setSchoolId(entity.getId());
				downList.add(excelEntity);
			} else if (funded.getSubtype().equals("113")) {
				params.put("type", "24");
				List<ErmDictEntity> dictEntitys = this.ermDictDao.queryList(params);
				for (ErmDictEntity ermDictEntity : dictEntitys) {
					ErmSchoolFundedExcelEntityHigh excelEntity = new ErmSchoolFundedExcelEntityHigh();
					excelEntity.setFundedId(funded.getId());
					excelEntity.setSchoolId(entity.getId());
					excelEntity.setLevel(ermDictEntity.getDictName());
					downList.add(excelEntity);
				}
			} else {
				ErmSchoolFundedExcelEntity excelEntity = new ErmSchoolFundedExcelEntity();
				excelEntity.setFundedId(funded.getId());
				excelEntity.setSchoolId(entity.getId());
				downList.add(excelEntity);
			}
		}
		try {
			service = new ExportExcelService(downList, sheet, headArr, "分配资助名额");
			ExcelUtil.addExportDicts(service, "XXID", getSchoolDict(schoolEntitys));
			ExcelUtil.addExportDicts(service, "XMID", getFundedDict(fundedEntity));
			if (funded.getSubtype().equals("112")) {
				params.put("type", "25");
				List<ErmDictEntity> dictEntitys = this.ermDictDao.queryList(params);
				for (ErmDictEntity entity : dictEntitys) {
					service.addDic("XXLX", entity.getDictCode(), entity.getDictName());
				}
			} else if (funded.getSubtype().equals("113")) {
//				params.put("type", "24");
//				List<ErmDictEntity> dictEntitys = this.ermDictDao.queryList(params);
//				for (ErmDictEntity entity : dictEntitys) {
//					service.addDic("XXJB", entity.getDictCode(), entity.getDictName());
//				}
			}
			service.doExport();
			service.exportTemplate();
			String filePath = "/public/img/work/Tomcat/localhost/ROOT/" + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmSchoolFundedService#querySchFundList(java.util.Map)
	 */
	@Override
	public List<ErmSchoolFundedEntity> querySchFundList(Map<String, Object> map) {

		List<ErmSchoolFundedEntity> list = ermSchoolFundedDao.querySchFundList(map);
		if (null != list && !list.isEmpty()) {
			for (ErmSchoolFundedEntity ermSchoolFundedEntity : list) {
				if (null != ermSchoolFundedEntity.getStatus())
					ermSchoolFundedEntity.setStatusName(FundStatus.getCNName(ermSchoolFundedEntity.getStatus()));
				Map<String, Object> aa = new HashMap<String, Object>();
				aa.put("schId", ermSchoolFundedEntity.getSchoolId());
				aa.put("flag", 1); // 可用的指标.
				ermSchoolFundedEntity.setSchFundFields(ermSchFieldService.queryList(aa));
				String fundName = ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()) + "-"
						+ ermSchoolFundedEntity.getFundedName();
				if (null != ermSchoolFundedEntity.getSchzone() && ermSchoolFundedEntity.getSchzone().length() > 0)
					fundName = ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()) + "-"
							+ ermSchoolFundedEntity.getFundedName() + "-"
							+ ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSchzone());
				else if (null != ermSchoolFundedEntity.getLevel() && ermSchoolFundedEntity.getLevel().length() > 0)
					fundName = ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()) + "-"
							+ ermSchoolFundedEntity.getFundedName() + "-"
							+ ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getLevel());
				ermSchoolFundedEntity.setFundedName(fundName);

			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.coomia.erm.service.ErmSchoolFundedService#querySchFundListTotal(java.util
	 * .Map)
	 */
	@Override
	public int querySchFundListTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return ermSchoolFundedDao.querySchFundListTotal(map);
	}

	@Override
	public Map<String, Object> batchUpdateFunded(ErmSchoolFundedEntity ermSchoolFunded) {
		Map<String, Object> operResultMap = new HashMap<String, Object>();
		int counter = 0;
		ErmFundedEntity funded = ermFundedDao.queryObject(ermSchoolFunded.getFundedId());
		if (ermSchoolFunded.isAll()) {
			List<ErmSchoolEntity> fundSchools = ermSchoolService
					.queryInitSchoolsByFundId(ermSchoolFunded.getFundedId());
			if (fundSchools != null && fundSchools.size() > 0) {
				StringBuffer schoolIds = new StringBuffer();
				for (ErmSchoolEntity entity : fundSchools) {
					schoolIds.append(entity.getId()).append(",");
				}
				ermSchoolFunded.setSchoolIds(schoolIds.toString());
			}
		}
		if (StringUtils.isBlank(ermSchoolFunded.getSchoolIds())) {
			List<Integer> schIdList = ermSchoolFunded.getSchIdList();
			if (schIdList != null && schIdList.size() > 0) {
				StringBuffer schoolIds = new StringBuffer();
				for (Integer schId : schIdList) {
					schoolIds.append(schId).append(",");
				}
				ermSchoolFunded.setSchoolIds(schoolIds.toString());
			}

		}
		String[] schoolArr = ermSchoolFunded.getSchoolIds().split(",");
		for (int i = 0; i < schoolArr.length; i++) {
			Integer schoolId = Integer.parseInt(schoolArr[i]);
			if (!ermSchFieldService.hasInited(schoolId))
				ermSchFieldService.doInitSchFieldsConfig(schoolId);
			ermSchoolFunded.setSchoolId(schoolId);
			ErmSchoolFundedEntity tmpEntity = this.queryObjectByFundId(ermSchoolFunded.getFundedId(), schoolId,
					ermSchoolFunded.getYear(), ermSchoolFunded.getSemester(), ermSchoolFunded.getSchzone(),
					ermSchoolFunded.getLevel());
			// 如果数据库已存在该项目配置，则该项目不能修改名额配置及状态。
			if (null != tmpEntity) {
				if (tmpEntity.getStatus() == FundStatus.CONFIG.getCode()
						|| tmpEntity.getStatus() == FundStatus.RUNNING.getCode()) {
					ermSchoolFunded.setId(tmpEntity.getId());
					ermSchoolFunded.setStatus(FundStatus.CONFIG.getCode());
					if (null == ermSchoolFunded.getSemester()) {
						ermSchoolFunded.setSemester(funded.getSemester());
						ermSchoolFunded.setYear(funded.getYear());
					}
					this.update(ermSchoolFunded);
					if (tmpEntity.getStatus() == FundStatus.RUNNING.getCode()) {
						generateReport(ermSchoolFunded);
					}
				}
			}
			// 不存在名额
			else {
				ermSchoolFunded.setStatus(FundStatus.CONFIG.getCode());
				if (null == ermSchoolFunded.getSemester()) {
					ermSchoolFunded.setSemester(funded.getSemester());
					ermSchoolFunded.setYear(funded.getYear());
				}
				this.save(ermSchoolFunded);

			}

		}
		operResultMap.put("successNum", counter);
		return operResultMap;
	}

	private void generateReport(ErmSchoolFundedEntity ermSchoolFunded) {
		ErmReportEntity report = new ErmReportEntity();
		report.setReportDesc("按资助学校统计");
		report.setReportName("按资助学校统计");
		report.setSchId(ermSchoolFunded.getSchoolId());
		report.setReportYear(ermSchoolFunded.getYear());
		report.setReportType(1);
		ermReportDao.save(report);
		ErmFundedEntity funded = this.ermFundedDao.queryObject(ermSchoolFunded.getFundedId());
		ErmReportEntity report2 = new ErmReportEntity();
		report2.setReportDesc(funded.getName() + "项目资助情况表");
		report2.setReportName(funded.getName() + "项目资助情况表");
		report2.setSchId(ermSchoolFunded.getSchoolId());
		report2.setReportYear(ermSchoolFunded.getYear());
		report2.setFundId(funded.getId());
		report2.setReportType(5);
		ermReportDao.save(report2);
		ErmReportEntity report3 = new ErmReportEntity();
		report3.setReportDesc(funded.getName() + "项目学生受助名单");
		report3.setReportName(funded.getName() + "项目学生受助名单");
		report3.setSchId(ermSchoolFunded.getSchoolId());
		report3.setReportYear(ermSchoolFunded.getYear());
		report3.setFundId(funded.getId());
		report3.setReportType(6);
		ermReportDao.save(report3);
	}

	@Override
	public ErmSchoolFundedEntity queryObjectByFundId(Integer fundId, Integer schid, Integer year, String semester,
			String schZone, String level) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schoolId", schid);
		param.put("fundId", fundId);
		if (null != year)
			param.put("year", year);
		if (null != semester)
			param.put("semester", semester);
		if (null != schZone)
			param.put("schzone", schZone);
		if (null != level)
			param.put("level", level);
		return ermSchoolFundedDao.queryObjectByFundId(param);
	}

	public ErmSchoolFundedEntity queryObjectByFundId(Integer fundId, Integer schid, Integer year, String semester) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("schoolId", schid);
		param.put("fundId", fundId);
		if (null != year)
			param.put("year", year);
		if (null != semester)
			param.put("semester", semester);
		return ermSchoolFundedDao.queryObjectByFundId(param);
	}

	@Override
	public List<ErmSchoolFundedEntity> querySchFundSimpleList(Query query) {

		List<ErmSchoolFundedEntity> list = ermSchoolFundedDao.querySchFundList(query);
		if (null != list && !list.isEmpty()) {
			for (ErmSchoolFundedEntity ermSchoolFundedEntity : list) {
				String fundName = ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()) + "-"
						+ ermSchoolFundedEntity.getFundedName();
				if (null != ermSchoolFundedEntity.getStatus())
					ermSchoolFundedEntity.setStatusName(FundStatus.getCNName(ermSchoolFundedEntity.getStatus()));
				if (null != ermSchoolFundedEntity.getSchzone() && ermSchoolFundedEntity.getSchzone().length() > 0)
					fundName = ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()) + "-"
							+ ermSchoolFundedEntity.getFundedName() + "-"
							+ ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSchzone());
				else if (null != ermSchoolFundedEntity.getLevel() && ermSchoolFundedEntity.getLevel().length() > 0)
					fundName = ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getSemester()) + "-"
							+ ermSchoolFundedEntity.getFundedName() + "-"
							+ ermDictDao.getDictNameByCode(ermSchoolFundedEntity.getLevel());
				ermSchoolFundedEntity.setFundedName(fundName);
			}
		}
		return list;
	}

	@Override
	public boolean saveOrUpdateSchFundExt(ErmSchFundExt ext) {
		ErmFundedEntity fund = ermFundedDao.queryObject(ext.getFundedId());
		// 是否是全部学校
		if (ext.isAll()) {
			List<ErmSchoolEntity> fundSchools = ermSchoolService.queryInitSchoolsByFundId(ext.getFundedId());
			if (fundSchools != null && fundSchools.size() > 0) {
				for (ErmSchoolEntity entity : fundSchools) {
					// 按学校等级saveOrUpdate学校项目配置
					saveOrUpdateSchFund(ext, fund, entity.getId());
				}

			}
		} else {
			if (null != ext.getSchIdList() && !ext.getSchIdList().isEmpty()) {
				for (int i = 0; i < ext.getSchIdList().size(); i++) {
					saveOrUpdateSchFund(ext, fund, ext.getSchIdList().get(i));
				}

				return true;
			}

		}

		return false;
	}

	private void saveOrUpdateSchFund(ErmSchFundExt ext, ErmFundedEntity fund, int schId) {
		if (null != ext.getLevelList() && !ext.getLevelList().isEmpty()) {
			for (int j = 0; j < ext.getLevelList().size(); j++) {
				ErmSchoolFundedEntity schFund = new ErmSchoolFundedEntity();
				schFund.setSchoolId(schId);
				schFund.setFundedId(ext.getFundedId());
				schFund.setSchzone(ext.getLevelList().get(j).getSchzone());
				schFund.setYear(fund.getYear());
				schFund.setSemester(fund.getSemester());
				schFund.setLevel(ext.getLevelList().get(j).getLevelCode());
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("schoolId", schId);
				queryMap.put("fundId", ext.getFundedId());
				queryMap.put("year", fund.getYear());
				queryMap.put("semester", fund.getSemester());
				queryMap.put("schzone", ext.getLevelList().get(j).getSchzone());
				queryMap.put("level", ext.getLevelList().get(j).getLevelCode());
				ErmSchoolFundedEntity exists = ermSchoolFundedDao.queryObjectByFundId(queryMap);
				if (null == exists) {
					schFund.setCount(ext.getLevelList().get(j).getLevelCount());
					schFund.setTotalMoney(ext.getLevelList().get(j).getLevelMoney());
					schFund.setStatus(FundStatus.CONFIG.getCode());
					schFund.setCreateTime(new Date());
					schFund.setCreator(ext.getCreator());
					schFund.setNote(ext.getNote());
					ermSchoolFundedDao.save(schFund);
				} else {
					schFund.setId(exists.getId());
					schFund.setCount(ext.getLevelList().get(j).getLevelCount());
					schFund.setTotalMoney(ext.getLevelList().get(j).getLevelMoney());
					schFund.setStatus(FundStatus.CONFIG.getCode());
					schFund.setUpdateTime(new Date());
					schFund.setUpdator(ext.getUpdator());
					schFund.setNote(ext.getNote());
					ermSchoolFundedDao.update(schFund);
				}
			}
		}
	}

	protected boolean updateStatus(Integer id, int status) {
		ErmSchoolFundedEntity ermFunded = new ErmSchoolFundedEntity();
		ermFunded.setId(id);
		ermFunded.setUpdateTime(new Date());
		ermFunded.setStatus(status);
		int n = ermSchoolFundedDao.update(ermFunded);
		return n >= 0;
	}

	@Override
	public boolean doChangeSchFundStatud(int schFundId, FundStatus targetStatus) {

		return updateStatus(schFundId, targetStatus.getCode());
	}

}
