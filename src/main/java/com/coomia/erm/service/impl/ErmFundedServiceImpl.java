package com.coomia.erm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.dao.ErmSchoolFundedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.dao.ErmFundedDao;
import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmDictService;
import com.coomia.erm.service.ErmFundedService;
import com.coomia.erm.service.ErmSchoolFundedService;
import com.coomia.erm.service.ErmSchoolService;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.StringUtil;

@Service("ermFundedService")
public class ErmFundedServiceImpl implements ErmFundedService {
  @Autowired
  private ErmFundedDao ermFundedDao;

  @Autowired
  private ErmDictService ermDictService;

  @Autowired
  private ErmSchoolService ermSchoolService;

  @Autowired
  private ErmSchoolFundedService ermSchoolFundedService;

  @Autowired
  private ErmSchoolFundedDao ermSchoolFundedDao;


  @Override
  public ErmFundedEntity queryObject(Integer id) {
    Map<String, Object> semesterMap = this.ermDictService.getSemesterMap();
    ErmFundedEntity entity = ermFundedDao.queryObject(id);
    entity.setSemester((String) semesterMap.get(entity.getSemester()));
    return entity;
  }
  
  public ErmFundedEntity queryObjectOriginal(Integer id) {
    ErmFundedEntity entity = ermFundedDao.queryObject(id);
    return entity;
  }

  @Override
  public List<ErmFundedEntity> queryList(Map<String, Object> map) {
    /*
     * if (map.containsKey("countryType")) { Integer countryType = Integer.parseInt((String)
     * map.get("countryType")); String fundName = DictConstants.COUNTRY_TYPE_MAP.get(countryType);
     * if (StringUtils.isNotBlank(fundName)) { map.put("type", 1); map.put("fundName", fundName); }
     * else { return null; } }
     */
    List<ErmFundedEntity> ermFundedList = ermFundedDao.queryList(map);
    Map<String, Object> semesterMap = this.ermDictService.getSemesterMap();
    for (ErmFundedEntity entity : ermFundedList) {
      entity.setSemester((String) semesterMap.get(entity.getSemester()));
      entity.setName(entity.getSemester() + "-" + entity.getName());
    }
    return ermFundedList;
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
    return ermFundedDao.queryTotal(map);
  }

  @Override
  public boolean save(ErmFundedEntity ermFunded) {
    Map<String, Object> query = new HashMap<String, Object>();
    query.put("type", ermFunded.getType());
    query.put("ebId", ermFunded.getEbId());
    query.put("name", ermFunded.getName());
    List<ErmFundedEntity> data = ermFundedDao.queryList(query);
    if (data.isEmpty()) {
      ermFunded.setCreateTime(new Date());
      ermFunded.setStatus(FundStatus.NEW.getCode());
      if(null == ermFunded.getYear() || ermFunded.getYear() ==0)
      {
        String semester = ermFunded.getSemester();
        if(null != semester && !semester.isEmpty())
        {
          Map<String, Object> semesterMap = this.ermDictService.getSemesterMap();
          ermFunded.setYear( Integer.parseInt(semesterMap.get(semester).toString().substring(0,4)));
        }
        
      }
      ermFundedDao.save(ermFunded);
      return true;
    }
    return false;
  }

  @Override
  public boolean saveShcFund(ErmFundedEntity ermFunded,UserContext user) {
    ermFunded.setEbId(user.getEbId());
    Map<String, Object> query = new HashMap<String, Object>();
    query.put("type", ermFunded.getType());
    query.put("ebId", ermFunded.getEbId());
    query.put("name", ermFunded.getName());
    List<ErmFundedEntity> data = ermFundedDao.queryList(query);
    if (data.isEmpty()) {
      ermFunded.setCreateTime(new Date());
      ermFunded.setStatus(FundStatus.NEW.getCode());
      if(null == ermFunded.getYear() || ermFunded.getYear() ==0)
      {
        String semester = ermFunded.getSemester();
        if(null != semester && !semester.isEmpty())
        {
          Map<String, Object> semesterMap = this.ermDictService.getSemesterMap();
          ermFunded.setYear( Integer.parseInt(semesterMap.get(semester).toString().substring(0,4)));
        }

      }
      ermFundedDao.save(ermFunded);
      ErmSchoolFundedEntity ermSchoolFundedEntity=new ErmSchoolFundedEntity();
      ermSchoolFundedEntity.setStatus(ermFunded.getStatus());
      ermSchoolFundedEntity.setSemester(ermFunded.getSemester());
      ermSchoolFundedEntity.setTotalMoney(ermFunded.getTotalMoney());
      ermSchoolFundedEntity.setCount(ermFunded.getCount());
      ermSchoolFundedEntity.setPublicStatus(0);
      ermSchoolFundedEntity.setYear(new Date().getYear());
      ermSchoolFundedEntity.setSchoolId(user.getSchoolId());
      ermSchoolFundedEntity.setCreateTime(new Date());
      ermSchoolFundedEntity.setFundedId(ermFunded.getId());
      ermSchoolFundedEntity.setCreator(user.getUsername());
      ermSchoolFundedDao.save(ermSchoolFundedEntity);

      return true;
    }
    return false;
  }

  @Override
  public void update(ErmFundedEntity ermFunded) {
    ermFunded.setUpdateTime(new Date());
    ermFundedDao.update(ermFunded);
  }

  @Override
  public void delete(Integer id) {
    ermFundedDao.delete(id);
  }

  @Override
  public void deleteBatch(Integer[] ids) {
    ermFundedDao.deleteBatch(ids);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.coomia.erm.service.ErmFundedService#doStartFund(java.lang.Integer)
   */
  @Override
  public boolean doStartFund(Integer id) {
    return updateStatus(id, FundStatus.RUNNING.getCode());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.coomia.erm.service.ErmFundedService#getFundStatus(java.lang.Integer)
   */
  @Override
  public int getFundStatus(Integer id) {
    return queryObject(id).getStatus();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.coomia.erm.service.ErmFundedService#doStopFund(java.lang.Integer)
   */
  @Override
  public boolean doStopFund(Integer id) {
    return updateStatus(id, FundStatus.CLOSE.getCode());
  }

  protected boolean updateStatus(Integer id, int status) {
    ErmFundedEntity ermFunded = new ErmFundedEntity();
    ermFunded.setId(id);
    ermFunded.setUpdateTime(new Date());
    ermFunded.setStatus(status);
    int n = ermFundedDao.update(ermFunded);
    return n >= 0;
  }

  @Override
  public List<ErmFundedEntity> queryListBySchoolId(Map<String, Object> map) {
    List<ErmFundedEntity> entitys = this.ermFundedDao.queryListBySchoolId(map);
    if (entitys != null && entitys.size() > 0) {
      for (ErmFundedEntity entity : entitys) {
        String semester = entity.getSemester();
        String dictName = this.ermDictService.getDictNameByCode(semester);
        String fundName = dictName + "-" + entity.getName();
        if (null != entity.getSchzone() && entity.getSchzone().length() > 0)
          fundName = dictName + "-" + entity.getName() + "-"
              + ermDictService.getDictNameByCode(entity.getSchzone());
        else if (null != entity.getLevel() && entity.getLevel().length() > 0)
          fundName = dictName + "-" + entity.getName() + "-"
              + ermDictService.getDictNameByCode(entity.getLevel());
        entity.setName(fundName);
      }
    }
    return entitys;
  }

  @Override
  public List<Map<String, Object>> queryTotalReportMapList(Query query) {
    List<Map<String, Object>> fundedTotalReportList =
        this.ermFundedDao.queryTotalReportMapList(query);
    if (fundedTotalReportList == null || fundedTotalReportList.size() <= 0) {
      return fundedTotalReportList;
    }
    for (int i = 0; i < fundedTotalReportList.size(); i++) {
      Map<String, Object> fundedReport = fundedTotalReportList.get(i);
      String fundedInfo = (String) fundedReport.get("schoolFunded");
      if (fundedInfo != null) {
        String[] infos = fundedInfo.split(",");
        for (int j = 0; j < infos.length; j++) {
          String[] detailInfo = infos[j].split(":");
          String schoolType = "school_" + detailInfo[0];
          String schoolFunded = detailInfo[1];
          String schoolMoney = detailInfo[2];
          fundedReport.put(schoolType + "_stus", schoolFunded);
          fundedReport.put(schoolType + "_money", schoolMoney);
        }
      }
    }
    return fundedTotalReportList;
  }

  @Override
  public int queryTotalReportMapListTotal(Query query) {
    return this.ermFundedDao.queryTotalReportMapListTotal(query);
  }

  @Override
  public Map<String, Object> getReportOverview(Query query) {

    return this.ermFundedDao.getReportOverview(query);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.coomia.erm.service.ErmFundedService#queryFundList(java.util.Map)
   */
  @Override
  public List<ErmFundedEntity> queryFundList(Map<String, Object> map) {
    return this.ermFundedDao.queryFundList(map);
  }

  public int queryFundListTotal(Map<String, Object> map) {
    return this.ermFundedDao.queryFundListTotal(map);
  }

  @Override
  public ErmFundedEntity queryObjectByMap(Map<String, Object> map) {
    List<ErmFundedEntity> data = ermFundedDao.queryList(map);
    if (!data.isEmpty())
      return data.get(0);
    else
      return null;
  }

  public boolean doCheckSchFunds(ErmFundedEntity ermFundedEntity) {
    List<String> schCodeList =
        DictConstants.FUND_CODE_SCH_RELA.get(ermFundedEntity.getFundDictCode());
    for (String schCode : schCodeList) {
      ErmSchoolEntity school = ermSchoolService.queryObjectBySchCode(schCode);
      ErmSchoolFundedEntity toSave = new ErmSchoolFundedEntity();
      toSave.setSchoolId(school.getId());
      toSave.setFundedId(ermFundedEntity.getId());
      toSave.setSemester(ermFundedEntity.getSemester());
      toSave.setYear(
          ermFundedEntity.getYear() == null ? StringUtil.getYear(ermFundedEntity.getSemesterName())
              : ermFundedEntity.getYear());
      toSave.setStatus(FundStatus.RUNNING.getCode());
      ErmSchoolFundedEntity inDb = ermSchoolFundedService.queryObjectByFundId(toSave.getFundedId(),
          toSave.getSchoolId(), toSave.getYear(), toSave.getSemester());
      if (null == inDb)
        ermSchoolFundedService.save(toSave);
    }
    return true;

  }

  public boolean doConfigFund(ErmFundedEntity ermFundedEntity) {
    ermFundedEntity.setYear(
        ermFundedEntity.getYear() == null ? StringUtil.getYear(ermFundedEntity.getSemesterName())
            : ermFundedEntity.getYear());
    ermFundedEntity.setCreateTime(new Date());
    ermFundedEntity.setStatus(FundStatus.RUNNING.getCode());
    ermFundedDao.save(ermFundedEntity);
    return true;
  }

  @Override
  public boolean doChangeFundStatud(int fundId, FundStatus targetStatus) {
    
    return updateStatus(fundId, targetStatus.getCode());
  }

  @Override
  public ErmFundedEntity selectByNameAndSemester(String semester, String name) {
    return ermFundedDao.selectByNameAndSemester(semester,name);
  }
}
