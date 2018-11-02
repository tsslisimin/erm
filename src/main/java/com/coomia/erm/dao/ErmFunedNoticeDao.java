package com.coomia.erm.dao;

import com.coomia.erm.entity.ErmFunedNoticeEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 通知信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
@Mapper
public interface ErmFunedNoticeDao extends BaseDao<ErmFunedNoticeEntity> {

  /**
   * queryNoticesByCreator  TODO
   * @param map
   * @return
   */
  List<ErmFunedNoticeEntity> queryNoticesByCreator(Map<String, Object> map);

  /**
   * queryNoticesToUser  TODO
   * @param username
   * @return
   */
  List<ErmFunedNoticeEntity> queryNoticesToUser(String username);
	
}
