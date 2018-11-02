package com.coomia.erm.service.impl;

import com.coomia.erm.dao.TbErmLogDao;
import com.coomia.erm.entity.TbErmLog;
import com.coomia.erm.service.ErmLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: wangying
 * @Description:
 * @Date: Created in  2018/7/12
 */
@Service
public class ErmLogServiceImpl implements ErmLogService {


    @Autowired
    private TbErmLogDao tbErmLogDao;
    @Override
    public TbErmLog queryObject(Long id) {
        return tbErmLogDao.selectByPrimaryKey(id);
    }

    @Override
    public List<TbErmLog> queryList(Map<String, Object> map) {
        return tbErmLogDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return tbErmLogDao.queryTotal(map);
    }

    @Override
    public void save(TbErmLog tbErmLog) {
    tbErmLogDao.insert(tbErmLog);
    }

    @Override
    public void update(TbErmLog tbErmLog) {
    tbErmLogDao.updateByPrimaryKeySelective(tbErmLog);
    }

    @Override
    public void delete(Long id) {
     tbErmLogDao.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
     tbErmLogDao.deleteBatch(ids);
    }
}
