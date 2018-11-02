package com.coomia.erm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coomia.erm.entity.ErmAdminEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:22
 */
@Mapper
public interface ErmAdminDao extends BaseDao<ErmAdminEntity> {
	/**
	 * 根据用户名查找用户
	 * @param username
	 * String username, Integer ebId
	 * @return
	 */
	ErmAdminEntity queryObjectByName(Map<String, Object> param);

	List<ErmAdminEntity> queryBySchoolId(@Param("schoolId")Integer schoolId);
}
