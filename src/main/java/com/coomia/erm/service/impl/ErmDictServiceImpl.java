package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.dao.ErmDictDao;
import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.service.ErmDictService;
import com.coomia.erm.util.BusinessUtil;
import com.coomia.erm.util.Query;



@Service("ermDictService")
public class ErmDictServiceImpl implements ErmDictService {
	@Autowired
	private ErmDictDao ermDictDao;
	
	@Override
	public ErmDictEntity queryObject(Integer id){
		return ermDictDao.queryObject(id);
	}
	
	@Override
	public List<ErmDictEntity> queryList(Map<String, Object> map){
		return ermDictDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermDictDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmDictEntity ermDict){
		
		ermDictDao.save(ermDict);
	}
	
	@Override
	public void update(ErmDictEntity ermDict){
		ermDictDao.update(ermDict);
	}
	
	@Override
	public void delete(Integer id){
		ermDictDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermDictDao.deleteBatch(ids);
	}

	@Override
	public Map<String, Object> querystuDict(Query query) {
		List<ErmDictEntity> dict = this.ermDictDao.queryList(query);
		Map<String,Object> stuDict = null;
		if(dict != null && dict.size()>0) {
			stuDict = new HashMap<String,Object>();
			int type = 0;
			List<ErmDictEntity> subDict = new ArrayList<ErmDictEntity>();
			for(int i=0;i<dict.size();i++) {
				ErmDictEntity entity = dict.get(i);
				if(type != entity.getType()) {
					subDict = new ArrayList<ErmDictEntity>();
					subDict.add(entity);
					type = entity.getType();
					if(type != 0) {
						stuDict.put(String.valueOf(type), subDict);
					}
				}else {
					subDict.add(entity);
				}
			}
		}
		return stuDict;
	}

	@Override
	public List<String> createSemester(String years) {
		if(StringUtils.isBlank(years)) {
			return null;
		}
		String [] yearArr = years.split(",");
		List<String> semesterList = new ArrayList<String>();
		for(int i=0;i<yearArr.length;i++) {
			String springKey = BusinessUtil.getCodeUUID();
			String autumnKey = BusinessUtil.getCodeUUID();
			String year = yearArr[i];
			String springYear = year+"年春季";
			String autumnYear = year+"年秋季";
			ErmDictEntity springEntity = new ErmDictEntity(DictConstants.SEMESTER_TYPE,springKey,springYear);
			ErmDictEntity autumnEntity = new ErmDictEntity(DictConstants.SEMESTER_TYPE,autumnKey,autumnYear);
			this.ermDictDao.save(springEntity);
			this.ermDictDao.save(autumnEntity);
			semesterList.add(springYear);
			semesterList.add(autumnYear);
		}
		return semesterList;
	}

	@Override
	public Map<String, Object> getSemesterMap() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("type", DictConstants.SEMESTER_TYPE);
		List<ErmDictEntity> semesterList = this.ermDictDao.queryList(param);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for(ErmDictEntity entity:semesterList) {
			resultMap.put(entity.getDictCode(), entity.getDictName());
		}
		return resultMap;
	}

	@Override
	public String getDictNameByCode(String code) {
		return this.ermDictDao.getDictNameByCode(code);
	}

	@Override
	public ErmDictEntity getDictNameByName(String dictName, String type) {
		return ermDictDao.getDictNameByName(dictName,type);
	}

	@Override
	public List<ErmDictEntity> getDictByType(String type) {
		return ermDictDao.getDictByType(type);
	}

}
