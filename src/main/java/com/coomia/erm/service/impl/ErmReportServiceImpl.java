package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.dao.ErmReportDao;
import com.coomia.erm.entity.ErmReportEntity;
import com.coomia.erm.service.ErmReportService;

@Service("ermReportService")
public class ErmReportServiceImpl implements ErmReportService {
	@Autowired
	private ErmReportDao ermReportDao;

	@Override
	public ErmReportEntity queryObject(Integer id) {
		return ermReportDao.queryObject(id);
	}

	@Override
	public List<ErmReportEntity> queryList(Map<String, Object> map) {
		return ermReportDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermReportDao.queryTotal(map);
	}

	@Override
	public void save(ErmReportEntity ermReport) {
		ermReportDao.save(ermReport);
	}

	@Override
	public void update(ErmReportEntity ermReport) {
		ermReportDao.update(ermReport);
	}

	@Override
	public void delete(Integer id) {
		ermReportDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		ermReportDao.deleteBatch(ids);
	}

	/**
	 * SELECT fund.type type, COUNT(info.id) fundCount
	 */
	@Override
	public Map<String, Integer> queryFundGroupbyType(Integer ebId, Integer year) {
		// TODO Auto-generated method stub
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("ebId", ebId);
		p.put("year", year);
		return ermReportDao.queryFundGroupbyType(p);
	}

	/**
	 * SELECT schoolFund.school_id schId, COUNT(info.id) fundCount
	 */
	@Override
	public Map<String, Integer> queryFundGroupbySchool(Integer ebId, Integer year) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("ebId", ebId);
		p.put("year", year);
		return ermReportDao.queryFundGroupbySchool(p);
	}

	@Override
	public List<ErmReportEntity> queryDefaultOverViewList(List<Integer> years, List<ErmReportEntity> reportOverViewList,String type) {
		if(years == null) {
			return null;
		}
		List<ErmReportEntity> defaultList = new ArrayList<>();
		for(int i=0;i<years.size();i++) {
			
			if("eb".equals(type)) {
				ErmReportEntity entity3 = new ErmReportEntity();
				entity3.setReportYear(years.get(i));
				entity3.setSchoolName("所有学校");
				entity3.setReportDesc("按资助项目类型统计");
				entity3.setReportName("按资助项目类型统计");
				entity3.setId(i+1);
				entity3.setReportType(3);
				defaultList.add(entity3);
				ErmReportEntity entity2 = new ErmReportEntity();
				entity2.setReportYear(years.get(i));
				entity2.setSchoolName("所有学校");
				entity2.setReportDesc("按资助学区类型统计");
				entity2.setReportName("按资助学区类型统计");
				entity2.setId(i+2);
				entity2.setReportType(2);
//				ErmReportEntity entity1 = new ErmReportEntity();
//				entity1.setReportYear(years.get(i));
//				entity1.setSchoolName("所有学校");
//				entity1.setReportDesc("按资助学校统计");
//				entity1.setReportName("按资助学校统计");
//				entity1.setReportType(1);
				defaultList.add(entity2);
//				entity1.setId(i+3);
//				defaultList.add(entity1);
			}else {
				ErmReportEntity entity3 = new ErmReportEntity();
				entity3.setReportYear(years.get(i));
				entity3.setSchoolName("所有项目");
				entity3.setReportDesc("按资助项目类型统计");
				entity3.setReportName("按资助项目类型统计");
				entity3.setId(i+1);
				entity3.setReportType(4);
				defaultList.add(entity3);
			}
			
		}
		for(int i=0;i<reportOverViewList.size();i++) {
			ErmReportEntity entity = reportOverViewList.get(i);
			entity.setId(defaultList.size()+i+1);
		}
		defaultList.addAll(reportOverViewList);
		return defaultList;
	}

}
