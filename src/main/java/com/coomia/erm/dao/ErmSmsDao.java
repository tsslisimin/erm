package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmSmsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 短信发送表
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-23 21:21:48
 */
@Mapper
public interface ErmSmsDao extends BaseDao<ErmSmsEntity> {

	ErmSmsEntity queryObjectByCode(@Param("telephone") String telephone, @Param("code") String code);

}
