package com.coomia.erm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.dao.ErmFieldDao;
import com.coomia.erm.dao.ErmFieldValDao;
import com.coomia.erm.dao.ErmSchFieldDao;
import com.coomia.erm.entity.ErmFieldEntity;
import com.coomia.erm.entity.ErmFieldValEntity;
import com.coomia.erm.entity.ErmSchFieldEntity;
import com.coomia.erm.service.ErmFieldService;
import com.coomia.erm.util.BusinessUtil;

@Service("ermFieldService")
public class ErmFieldServiceImpl implements ErmFieldService {
	@Autowired
	private ErmFieldDao ermFieldDao;

	@Autowired
	private ErmFieldValDao ermFielValdDao;
	
	@Autowired
	private ErmSchFieldDao ermSchFieldDao;

	@Override
	public ErmFieldEntity queryObject(Integer id) {
		return ermFieldDao.queryObject(id);
	}

	@Override
	public List<ErmFieldEntity> queryList(Map<String, Object> map) {
		return ermFieldDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermFieldDao.queryTotal(map);
	}

	@Override
	public void save(ErmFieldEntity ermField) {
		ermField.setFieldCode(BusinessUtil.getUUId());
		ermFieldDao.save(ermField);
		Integer id = ermField.getId();
		List<ErmFieldValEntity> vals = ermField.getFieldVal();
		if (vals != null && vals.size() > 0) {
			for (int i = 0; i < vals.size(); i++) {
				ErmFieldValEntity val = vals.get(i);
				val.setFieldId(id);
				val.setValKey(BusinessUtil.getUUId());
				ermFielValdDao.save(val);
			}
		}
	}

	@Override
	public void update(ErmFieldEntity ermField) {
		ermFieldDao.update(ermField);
		List<ErmFieldValEntity> vals = ermField.getFieldVal();
		if (vals != null && vals.size() > 0) {
			for (int i = 0; i < vals.size(); i++) {
				ErmFieldValEntity val = vals.get(i);
				ermFielValdDao.update(val);
			}
		}
	}

	@Override
	public void delete(Integer id) {
		ermFieldDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		for(int i=0;i<ids.length;i++) {
			Integer id = ids[i];
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("fieldId", id);
			List<ErmSchFieldEntity> entitys = this.ermSchFieldDao.queryList(param);
			if(entitys != null) {
				for(ErmSchFieldEntity entity:entitys) {
					this.ermSchFieldDao.delete(entity.getId());
				}
			}
		}
		ermFieldDao.deleteBatch(ids);
	}

}
