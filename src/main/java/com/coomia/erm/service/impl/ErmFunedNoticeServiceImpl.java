package com.coomia.erm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.SystemConstants;
import com.coomia.erm.dao.ErmAdminDao;
import com.coomia.erm.dao.ErmAdminNoticeDao;
import com.coomia.erm.dao.ErmAuditLogDao;
import com.coomia.erm.dao.ErmFunedNoticeDao;
import com.coomia.erm.dao.ErmSchoolFundedDao;
import com.coomia.erm.dao.ErmStuValueDao;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmAdminNoticeEntity;
import com.coomia.erm.entity.ErmAuditLogEntity;
import com.coomia.erm.entity.ErmEBNoticeParam;
import com.coomia.erm.entity.ErmFundedInfoEntity;
import com.coomia.erm.entity.ErmFunedNoticeEntity;
import com.coomia.erm.entity.ErmQueryObject;
import com.coomia.erm.entity.ErmSchoolFundedEntity;
import com.coomia.erm.entity.FundStatus;
import com.coomia.erm.service.ErmFundedInfoService;
import com.coomia.erm.service.ErmFunedNoticeService;



@Service("ermFunedNoticeService")
public class ErmFunedNoticeServiceImpl implements ErmFunedNoticeService {
  @Autowired
  private ErmFunedNoticeDao ermFunedNoticeDao;
  @Autowired
  private ErmAdminDao ermAdminDao;
  @Autowired
  private ErmAdminNoticeDao ermAdminNoticeDao;
  @Autowired
  private ErmStuValueDao ermStuValueDao;
  @Autowired
  private ErmFundedInfoService ermFundedInfoService;
  @Autowired
  private ErmAuditLogDao ermAuditLogDao;
  @Autowired
  private ErmSchoolFundedDao ermSchoolFundedDao;

  @Override
  public ErmFunedNoticeEntity queryObject(Integer id) {
    return ermFunedNoticeDao.queryObject(id);
  }

  @Override
  public List<ErmFunedNoticeEntity> queryList(Map<String, Object> map) {
    return ermFunedNoticeDao.queryList(map);
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
    return ermFunedNoticeDao.queryTotal(map);
  }

  @Override
  public void save(ErmFunedNoticeEntity ermFunedNotice) {
    ermFunedNotice.setCreateTime(new Date());
    ermFunedNoticeDao.save(ermFunedNotice);
    String schools = ermFunedNotice.getSchools();
    if (StringUtils.isNotBlank(schools)) {
      Map<String, Object> query = new HashMap<String, Object>();
      query.put("schools", schools);
      query.put("ebId", SystemConstants.ebId);
      List<ErmAdminEntity> schoolAdmin = this.ermAdminDao.queryList(query);
      if (schoolAdmin != null) {
        for (ErmAdminEntity admin : schoolAdmin) {
          ErmAdminNoticeEntity adminNotie = new ErmAdminNoticeEntity();
          adminNotie.setAdminId(admin.getId());
          adminNotie.setCreateTime(new Date());
          adminNotie.setNoticeId(ermFunedNotice.getId());
          ermAdminNoticeDao.save(adminNotie);
        }
      }
    }

  }

  @Override
  public void update(ErmFunedNoticeEntity ermFunedNotice) {
    ermFunedNotice.setUpdateTime(new Date());
    ermFunedNoticeDao.update(ermFunedNotice);
  }

  @Override
  public void delete(Integer id) {
    ermFunedNoticeDao.delete(id);
  }

  @Override
  public void deleteBatch(Integer[] ids) {
    ermFunedNoticeDao.deleteBatch(ids);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.coomia.erm.service.ErmFunedNoticeService#queryNoticesToUser(java.lang.String)
   */
  @Override
  public List<ErmFunedNoticeEntity> queryNoticesToUser(String username) {
    return ermFunedNoticeDao.queryNoticesToUser(username);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.coomia.erm.service.ErmFunedNoticeService#queryNoticesByCreator(java.lang.String)
   */
  @Override
  public List<ErmFunedNoticeEntity> queryNoticesByCreator(String username) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("creator", username);
    return ermFunedNoticeDao.queryNoticesByCreator(map);
  }

  @Override
  public boolean saveExt(ErmEBNoticeParam ermEBNoticeParam) {
    boolean flag = false;
    save(ermEBNoticeParam.getErmFunedNoticeEntity());
    ErmQueryObject queryObj = ermEBNoticeParam.getErmQueryObject();
    Map<String, Object> p3 = new HashMap<String, Object>();
    p3.put("fundId", queryObj.getFundId());
    p3.put("level", queryObj.getLevel());
    p3.put("schzone", queryObj.getSchzone());
    p3.put("schFundId", queryObj.getSchFundId());
    p3.put("year", queryObj.getYear());
    p3.put("semester", queryObj.getSemester());
    p3.put("status", queryObj.getStatus());
    p3.put("statusSet", queryObj.getStatusSet());
    p3.put("page", queryObj.getPage());
    p3.put("limit", queryObj.getLimit());
    p3.put("offset", (queryObj.getPage() - 1) * queryObj.getLimit());
    p3.put("stuName", queryObj.getStuName());
    p3.put("idCard", queryObj.getIdCard());
    p3.put("grade", queryObj.getGrade());
    p3.put("clazz", queryObj.getClazz());
    p3.put("stuno", queryObj.getStuno());
    p3.put("schId", queryObj.getSchId());
    p3.put("diffLevel", queryObj.getDiffLevel());
    p3.put("flag", queryObj.getFlag());
    p3.put("isGraduated", queryObj.getIsGraduated());
    p3.put("teacheThecked", queryObj.getTeacheThecked());
    List<Map<String, Object>> studentsFixedData = ermStuValueDao.queryToAuditStudents(p3);


    // 为每个学生加入流程信息，insert log, and update info global status
    FundStatus currentSucceedStatus = FundStatus.OFFERREDSUCCESS;
    FundStatus nextStatus = FundStatus.NOTICE;

    if (!studentsFixedData.isEmpty()) {
      // 凡是在OFFERRED状态的学生/项目都更改状态，并做log表数据的插入。
      for (Map<String, Object> info : studentsFixedData) {
        ErmFundedInfoEntity toUpdate = new ErmFundedInfoEntity();
        toUpdate.setId(Integer.parseInt("" + info.get("id")));
        toUpdate.setGlobalStatus(nextStatus.getCode());// 将学生的流程改为下一环节的流程
        ermFundedInfoService.update(toUpdate);
        ErmAuditLogEntity toSave = new ErmAuditLogEntity();
        toSave.setCreator("AI");
        toSave.setCreateTime(new Date());
        toSave.setAuditDate(new Date());
        toSave.setStatus(currentSucceedStatus.getCode());
        toSave.setAuditRemark(FundStatus.getCNName(currentSucceedStatus.getCode()));
        toSave.setFundedId(toUpdate.getId());
        ermAuditLogDao.save(toSave);
      }
      ErmSchoolFundedEntity schFund = new ErmSchoolFundedEntity();
      schFund.setId(Integer.parseInt("" + studentsFixedData.get(0).get("funded_id"))); // 这里有点偷懒了，按业务来讲，应该是这个ID
      schFund.setStatus(nextStatus.getCode());
      ermSchoolFundedDao.update(schFund);
    }

    flag = true;
    return flag;
  }

}
