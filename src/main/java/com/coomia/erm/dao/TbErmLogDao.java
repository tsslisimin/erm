package com.coomia.erm.dao;

import com.coomia.erm.entity.TbErmLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbErmLogDao extends BaseDao<TbErmLog>{



    int deleteByPrimaryKey(Long id);

    int insert(TbErmLog record);

    int insertSelective(TbErmLog record);

    TbErmLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbErmLog record);

    int updateByPrimaryKeyWithBLOBs(TbErmLog record);

    int updateByPrimaryKey(TbErmLog record);
}