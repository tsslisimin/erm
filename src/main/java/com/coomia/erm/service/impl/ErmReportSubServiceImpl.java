package com.coomia.erm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coomia.erm.dao.ErmReportSubDao;
import com.coomia.erm.entity.ErmReportSubEntity;
import com.coomia.erm.service.ErmReportSubService;

@Service("ermReportSubService")
public class ErmReportSubServiceImpl implements ErmReportSubService {
	@Autowired
	private ErmReportSubDao ermReportSubDao;

	@Override
	public ErmReportSubEntity queryObject(Integer id) {
		return ermReportSubDao.queryObject(id);
	}

	@Override
	public List<ErmReportSubEntity> queryList(Map<String, Object> map) {
		return ermReportSubDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermReportSubDao.queryTotal(map);
	}

	@Override
	public void save(ErmReportSubEntity ermReportSub) {
		ermReportSubDao.save(ermReportSub);
	}

	@Override
	public void update(ErmReportSubEntity ermReportSub) {
		ermReportSubDao.update(ermReportSub);
	}

	@Override
	public void delete(Integer id) {
		ermReportSubDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		ermReportSubDao.deleteBatch(ids);
	}

}
