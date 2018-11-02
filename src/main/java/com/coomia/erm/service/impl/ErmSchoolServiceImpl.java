package com.coomia.erm.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coomia.erm.service.ErmAdminService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jplus.hyberbin.excel.service.ImportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.ExcelConstants;
import com.coomia.erm.dao.ErmFundedDao;
import com.coomia.erm.dao.ErmSchoolDao;
import com.coomia.erm.dao.ErmStudentDao;
import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.entity.excel.ErmSchoolExcelEntity;
import com.coomia.erm.service.ErmSchoolService;
import com.coomia.erm.util.ExcelUtil;
import com.coomia.erm.util.Query;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service("ermSchoolService")
public class ErmSchoolServiceImpl implements ErmSchoolService {
  @Autowired
  private ErmSchoolDao ermSchoolDao;

  @Autowired
  private ErmFundedDao ermFundedDao;
  
  @Autowired
  private ErmStudentDao ermStudentDao;
  @Autowired
  private ErmAdminService ermAdminService;

  @Override
  public ErmSchoolEntity queryObject(Integer id) {
    return ermSchoolDao.queryObject(id);
  }

  @Override
  public List<ErmSchoolEntity> queryList(Map<String, Object> map) {
    return ermSchoolDao.queryList(map);
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
    return ermSchoolDao.queryTotal(map);
  }

  @Override
  public boolean save(ErmSchoolEntity ermSchool) {
    boolean flag = false;
    try {
        ErmSchoolEntity ermSchoolEntity = ermSchoolDao.queryObjectBySchCode(ermSchool.getCode());
        if (ermSchoolEntity!=null){
            return flag;
        }
        ermSchool.setCreateTime(new Date());
        ermSchoolDao.save(ermSchool);
        ermAdminService.createOneSchoolAdmin(ermSchool);
      flag = true;
      return flag;
    } catch (Exception e) {
      return flag;
    }

  }

  @Override
  public void update(ErmSchoolEntity ermSchool) {
    ermSchool.setUpdateTime(new Date());
    ermSchoolDao.update(ermSchool);
  }

  @Override
  public void delete(Integer id) {
    ermSchoolDao.delete(id);
  }

  @Override
  public void deleteBatch(Integer[] ids) {
    ermSchoolDao.deleteBatch(ids);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public Map<String, Object> importSchoolInfo(InputStream input, Integer ebId) {
    Workbook workbook = null;
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      workbook = new HSSFWorkbook(input);
      Sheet sheet = workbook.getSheet(ExcelConstants.SCHOOL_SHEET_NAME);
      ImportExcelService service = new ImportExcelService(ErmSchoolExcelEntity.class, sheet);
      ExcelUtil.addImportExportDicts(service, ExcelConstants.SCHOOL_TYPE_CODE,
          DictConstants.SCHOOL_TYPE_MAP);// 设置数据字典
      List<ErmSchoolExcelEntity> list = service.doImport();
      if (list != null && list.size() > 0) {
        for (int i = 0; i < list.size(); i++) {
          ErmSchoolExcelEntity excelEntity = list.get(i);
          ErmSchoolEntity schoolEntity = new ErmSchoolEntity();
          BeanUtils.copyProperties(schoolEntity, excelEntity);

          if (schoolEntity.getCode()==null){
              continue;
          }
            ErmSchoolEntity ermSchoolEntity = ermSchoolDao.queryObjectBySchCode(schoolEntity.getCode());
          if (ermSchoolEntity!=null){
              continue;
          }
          schoolEntity.setId(null);
          schoolEntity.setEbId(ebId);
          ermSchoolDao.save(schoolEntity);
          ermAdminService.createOneSchoolAdmin(schoolEntity);

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

  @Override
  public List<ErmSchoolEntity> queryListByFundedId(Query query) {

    return this.ermSchoolDao.queryListByFundedId(query);
  }

  @Override
  public ErmSchoolEntity queryObjectBySchCode(String schCode) {

    return ermSchoolDao.queryObjectBySchCode(schCode);
  }

  @Override
  public List<ErmSchoolEntity> queryInitSchoolsByFundId(Integer fundId) {
    ErmFundedEntity fund = ermFundedDao.queryObject(fundId);
    if (null == fund)
      return null;
    else {
      String subType = fund.getSubtype();
      List<String> schCodeList = DictConstants.SUBTYPENEW_SCHOOL_LIST.get(subType);
      List<ErmSchoolEntity> schools = ermSchoolDao.selectBatchType(schCodeList.toArray());
      return schools;
    }

  }

	@Override
	public List<Map<String, Object>> querySchoolClazzs(Map<String, Object> params) {
		List<ErmStudentEntity> clazzStudent = this.ermStudentDao.queryStudentGroupByClazz(params);
		List<Map<String,Object>> clazzs = Lists.newArrayList();
		if(clazzStudent == null || clazzStudent.size() <= 0) {
			return clazzs;
		}
		for(ErmStudentEntity student : clazzStudent) {
			Map<String,Object> clazz = Maps.newHashMap();
			if(StringUtils.isNotBlank(student.getGrade())) {
				clazz.put("grade", student.getGrade());
			}
			if(StringUtils.isNotBlank(student.getClazz())) {
				clazz.put("clazz", student.getClazz());
			}
			clazzs.add(clazz);
		}
		return clazzs;
	}

	@Override
	public Map<String, Set<String>> querySchGradeClazzList(Map<String, Object> params) {
		List<ErmStudentEntity> clazzStudent = this.ermStudentDao.queryStudentGroupByClazz(params);
		Map<String,Set<String>> gradeClazzList = Maps.newHashMap();
		if(clazzStudent == null || clazzStudent.size() <= 0) {
			return gradeClazzList;
		}
		Set<String> grades = Sets.newHashSet();
		Set<String> clazzs = Sets.newHashSet();
		for(ErmStudentEntity student : clazzStudent) {
			if(StringUtils.isNotBlank(student.getGrade())) {
				grades.add(student.getGrade());
			}
			if(StringUtils.isNotBlank(student.getClazz())) {
				clazzs.add(student.getClazz());
			}
		}
		gradeClazzList.put("grade", grades);
		gradeClazzList.put("clazz", clazzs);
		return gradeClazzList;
	}
}
