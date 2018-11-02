package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.util.Query;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 学校信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@Mapper
public interface ErmSchoolDao extends BaseDao<ErmSchoolEntity> {

	List<ErmSchoolEntity> queryListByFundedId(Query query);

    ErmSchoolEntity queryObjectBySchCode(String schCode);
    
    List<ErmSchoolEntity> selectBatch(Object[] id);


    List<ErmSchoolEntity> selectBatchType(Object[] id);
	
}
