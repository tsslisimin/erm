package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.dao.ErmAdminDao;
import com.coomia.erm.dao.ErmSchoolDao;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.service.ErmAdminService;
import com.coomia.erm.util.BusinessUtil;
import com.coomia.erm.util.Query;

@Service("ermAdminService")
public class ErmAdminServiceImpl implements ErmAdminService {
	@Autowired
	private ErmAdminDao ermAdminDao;
	
	@Autowired
	private ErmSchoolDao ermSchoolDao;

	@Override
	public ErmAdminEntity queryObject(Integer id) {
		return ermAdminDao.queryObject(id);
	}

	@Override
	public List<ErmAdminEntity> queryList(Map<String, Object> map) {
		return ermAdminDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return ermAdminDao.queryTotal(map);
	}

	@Override
	public void save(ErmAdminEntity ermAdmin) {
		// 密码加密
		if (ermAdmin != null && StringUtils.isNotBlank(ermAdmin.getPassword())) {
			String password = ermAdmin.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(password.length());
			ermAdmin.setPassword(encoder.encode(password));
		}
		ermAdmin.setCreateTime(new Date());
		ermAdminDao.save(ermAdmin);
	}

	@Override
	public void update(ErmAdminEntity ermAdmin) {
		ermAdmin.setUpdateTime(new Date());
		//修改密码
		if(StringUtils.isNotBlank(ermAdmin.getPassword())) {
			String password = ermAdmin.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(password.length());
			ermAdmin.setPassword(encoder.encode(password));
		}
		ermAdminDao.update(ermAdmin);
	}

	@Override
	public void delete(Integer id) {
		ermAdminDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids) {
		ermAdminDao.deleteBatch(ids);
	}

	@Override
	public List<ErmAdminEntity> createSchoolAccount(Query query) {

		List<ErmSchoolEntity> schoolEntity = this.ermSchoolDao.queryList(query);
		if(schoolEntity == null) {
			return null;
		}
		List<ErmAdminEntity> adminList = new ArrayList<ErmAdminEntity>();
		for(int i=0;i<schoolEntity.size();i++) {
			ErmSchoolEntity school = schoolEntity.get(i);

			List<ErmAdminEntity> ermAdminEntities = ermAdminDao.queryBySchoolId(school.getId());
			if (ermAdminEntities.size()>0){
				continue;
			}
			Integer ebId = (Integer)query.get("ebId");
			ErmAdminEntity adminOper = getAdminEntity(ebId, school);
			adminOper.setUsername(school.getCode()+"s");
			adminOper.setRoleId(DictConstants.ROLE_OPER);
			ErmAdminEntity adminSch = getAdminEntity(ebId, school);
			adminSch.setUsername(school.getCode()+"p");
			adminSch.setRoleId(DictConstants.ROLE_SHCOOL);
			this.save(adminOper);
			this.save(adminSch);
			adminList.add(adminOper);
			adminList.add(adminSch);
		}
		return adminList;

	}

	@Override
	public void createOneSchoolAdmin(ErmSchoolEntity school) {
		ErmAdminEntity adminOper = getAdminEntity(school.getEbId(), school);
		adminOper.setUsername(school.getCode()+"s");
		adminOper.setRoleId(DictConstants.ROLE_OPER);
		ErmAdminEntity adminSch = getAdminEntity(school.getEbId(), school);
		adminSch.setUsername(school.getCode()+"p");
		adminSch.setRoleId(DictConstants.ROLE_SHCOOL);
		this.save(adminOper);
		this.save(adminSch);
	}

	@Override
	public List<ErmAdminEntity> createSchoolAdmin(Query query) {
		List<ErmSchoolEntity> schoolEntity = this.ermSchoolDao.queryList(query);
		if(schoolEntity == null) {
			return null;
		}
		List<ErmAdminEntity> adminList = new ArrayList<ErmAdminEntity>();
		for(int i=0;i<schoolEntity.size();i++) {
			ErmSchoolEntity school = schoolEntity.get(i);
			Integer ebId = (Integer)query.get("ebId");
			ErmAdminEntity adminOper = getAdminEntity(ebId, school);
			adminOper.setUsername("oper-"+BusinessUtil.getRandCode(5));
			adminOper.setRoleId(DictConstants.ROLE_OPER);
			ErmAdminEntity adminSch = getAdminEntity(ebId, school);
			adminSch.setUsername("sch-"+BusinessUtil.getRandCode(5));
			adminSch.setRoleId(DictConstants.ROLE_SHCOOL);
			this.save(adminOper);
			this.save(adminSch);
			adminList.add(adminOper);
			adminList.add(adminSch);
		}
		return adminList;
	}


	private ErmAdminEntity getAdminEntity(Integer ebId,ErmSchoolEntity school) {
		ErmAdminEntity admin = new ErmAdminEntity();
		admin.setEbId(ebId);
		admin.setPassword(DictConstants.DEFAULT_PASSWORD);
		admin.setSchoolId(school.getId());
		admin.setSchoolName(school.getName());
		return admin;
	}

}
