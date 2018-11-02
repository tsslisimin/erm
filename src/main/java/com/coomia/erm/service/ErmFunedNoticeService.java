package com.coomia.erm.service;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ErmEBNoticeParam;
import com.coomia.erm.entity.ErmFunedNoticeEntity;

/**
 * 通知信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-02 23:26:21
 */
public interface ErmFunedNoticeService {
	
	ErmFunedNoticeEntity queryObject(Integer id);
	
	List<ErmFunedNoticeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ErmFunedNoticeEntity ermFunedNotice);
	
	void update(ErmFunedNoticeEntity ermFunedNotice);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	/**
     * 根据用户名查TO他的通知列表
     * queryNoticesToUser  TODO
     * @param username
     * @return
     */
    List<ErmFunedNoticeEntity> queryNoticesToUser(String username);
    
    /**
     * 根据用户名查他创建的通知列表
     * queryNoticesByCreator  TODO
     * @param username
     * @return
     */
    List<ErmFunedNoticeEntity> queryNoticesByCreator(String username);

    boolean saveExt(ErmEBNoticeParam ermEBNoticeParam);
}
