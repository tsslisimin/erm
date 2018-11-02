package com.coomia.erm.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coomia.erm.dao.ErmFamilyDao;
import com.coomia.erm.dao.ErmStudentDao;
import com.coomia.erm.entity.ErmApplyEntity;
import com.coomia.erm.entity.ErmFamilyEntity;
import com.coomia.erm.entity.ErmStudentEntity;
import com.coomia.erm.service.ErmFamilyService;
import com.google.common.collect.Maps;



@Service("ermFamilyService")
public class ErmFamilyServiceImpl implements ErmFamilyService {
	@Autowired
	private ErmFamilyDao ermFamilyDao;
	
	@Autowired
	private ErmStudentDao ermStudentDao;
	
	@Override
	public ErmFamilyEntity queryObject(Integer id){
		return ermFamilyDao.queryObject(id);
	}
	
	@Override
	public List<ErmFamilyEntity> queryList(Map<String, Object> map){
		return ermFamilyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return ermFamilyDao.queryTotal(map);
	}
	
	@Override
	public void save(ErmFamilyEntity ermFamily){
		ermFamily.setCreateTime(new Date());
		ermFamilyDao.save(ermFamily);
	}
	
	@Override
	public void update(ErmFamilyEntity ermFamily){
		ermFamily.setUpdateTime(new Date());
		ermFamilyDao.update(ermFamily);
	}
	
	@Override
	public void delete(Integer id){
		ermFamilyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		ermFamilyDao.deleteBatch(ids);
	}

	@Override
	public void saveApplyObj(ErmApplyEntity apply) {
		Map<String,Object> param = Maps.newHashMap();
		param.put("schoolId", apply.getSchoolId());
		param.put("idcard", apply.getIdcard());
		param.put("isGraduation", 0);
		ErmStudentEntity student = this.ermStudentDao.queryObjectByMap(param);
		//如果学生信息不存在，则插入新的学生信息
		if(student == null) {
			student = new ErmStudentEntity();
			student.setName(apply.getName());
			student.setAge(apply.getAge());
			student.setBirth(apply.getBirthDay());
			student.setNation(apply.getNation());
			student.setStuno(apply.getStuno());
			student.setGrade(apply.getGrade());
			student.setClazz(apply.getClazz());
			student.setIdcard(apply.getIdcard());
			student.setAddress(apply.getAddress());
			student.setSchoolId(apply.getSchoolId());
			student.setIsArchives(apply.getIsArchives());
			student.setArchiveAcount(apply.getArchiveAcount());
			student.setArchiveIdcard(apply.getArchiveIdcard());
			student.setArchiveName(apply.getArchiveName());
			student.setArchiveRelation(apply.getArchiveRelation());
			student.setSupportName(apply.getSupportName());
			student.setSupportBankCard(apply.getSupportBankCard());
			student.setHelper(apply.getHelper());
			student.setHelperPosition(apply.getHelperPosition());
			student.setHelperTel(apply.getHelperTel());
			student.setHelperWorkPlace(apply.getHelperWorkPlace());
			this.ermStudentDao.save(student);
		}
		ErmFamilyEntity family = new ErmFamilyEntity();
		try {
			BeanUtils.copyProperties(family, apply);
			this.ermFamilyDao.save(family);
			student.setFamilyId(family.getId());
			student.setIsArchives(apply.getIsArchives());
			student.setArchiveAcount(apply.getArchiveAcount());
			student.setArchiveIdcard(apply.getArchiveIdcard());
			student.setArchiveName(apply.getArchiveName());
			student.setArchiveRelation(apply.getArchiveRelation());
			student.setSupportName(apply.getSupportName());
			student.setSupportBankCard(apply.getSupportBankCard());
			student.setHelper(apply.getHelper());
			student.setHelperPosition(apply.getHelperPosition());
			student.setHelperTel(apply.getHelperTel());
			student.setHelperWorkPlace(apply.getHelperWorkPlace());
			this.ermStudentDao.update(student);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
