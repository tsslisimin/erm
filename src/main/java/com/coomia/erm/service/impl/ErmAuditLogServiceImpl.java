package com.coomia.erm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.dao.ErmAuditLogDao;
import com.coomia.erm.entity.ErmAuditLogEntity;
import com.coomia.erm.service.ErmAuditLogService;



@Service("ermAuditLogService")
public class ErmAuditLogServiceImpl implements ErmAuditLogService {
  @Autowired
  private ErmAuditLogDao ermAuditLogDao;

  @Override
  public ErmAuditLogEntity queryObject(Integer id) {
    return ermAuditLogDao.queryObject(id);
  }

  @Override
  public List<ErmAuditLogEntity> queryList(Map<String, Object> map) {
    return ermAuditLogDao.queryList(map);
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
    return ermAuditLogDao.queryTotal(map);
  }

  @Override
  public void save(ErmAuditLogEntity ermAuditLog) {
    ermAuditLog.setCreateTime(new Date());
    Map<String, Object> p5 = new HashMap<String, Object>();
    p5.put("stuFundId", ermAuditLog.getFundedId());
    p5.put("status", ermAuditLog.getStatus());
    int existedCount = ermAuditLogDao.queryLog(p5);
    if (existedCount == 0)
      ermAuditLogDao.save(ermAuditLog);
  }

  @Override
  public void update(ErmAuditLogEntity ermAuditLog) {
    ermAuditLog.setUpdateTime(new Date());
    ermAuditLogDao.update(ermAuditLog);
  }

  @Override
  public void delete(Integer id) {
    ermAuditLogDao.delete(id);
  }

  @Override
  public void deleteBatch(Integer[] ids) {
    ermAuditLogDao.deleteBatch(ids);
  }

}
