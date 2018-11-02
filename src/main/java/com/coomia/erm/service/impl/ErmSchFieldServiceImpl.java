package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.Constants;
import com.coomia.erm.dao.ErmFieldDao;
import com.coomia.erm.dao.ErmFieldValDao;
import com.coomia.erm.dao.ErmSchFieldDao;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmFieldEntity;
import com.coomia.erm.entity.ErmFieldValEntity;
import com.coomia.erm.entity.ErmSchFieldEntity;
import com.coomia.erm.service.ErmSchFieldService;
import com.coomia.erm.util.StringUtil;

/**
 * 学校的指标配置操作服务类
 * 
 * @author spancer
 *
 */
@Service("ermSchFieldService")
public class ErmSchFieldServiceImpl implements ErmSchFieldService {
  @Autowired
  private ErmSchFieldDao ermSchFieldDao;

  @Autowired
  private ErmFieldValDao ermFieldValDao;

  @Autowired
  private ErmFieldDao ermFieldDao;

  @Override
  public ErmSchFieldEntity queryObject(Integer id) {
    return ermSchFieldDao.queryObject(id);
  }

  @Override
  public List<ErmSchFieldEntity> queryList(Map<String, Object> map) {
    List<ErmSchFieldEntity> schFieldList = ermSchFieldDao.queryList(map);
    Map<Integer, Set<String>> fieldValMap = null;
    if (schFieldList != null && schFieldList.size() > 0) {
      List<Map<String, Object>> stuField = null;
      if (map.get("stuId") != null) {
        Integer stuId = Integer.parseInt((String) map.get("stuId"));
        stuField = this.ermFieldDao.queryStuFields(stuId);
        fieldValMap = convertData(stuField);
      }
      for (int i = 0; i < schFieldList.size(); i++) {
        ErmSchFieldEntity entity = schFieldList.get(i);
        ErmFieldEntity field = this.ermFieldDao.queryObject(entity.getFieldId());
        field.setValue(generateValueSet(fieldValMap, field));
        entity.setErmFieldEntity(field);
      }
    }
    return schFieldList;
  }

  private Map<Integer, Set<String>> convertData(List<Map<String, Object>> stuField) {
    Map<Integer, Set<String>> fieldValMap = new HashMap<Integer, Set<String>>();
    for (Map<String, Object> ff : stuField) {
      Object key = ff.get("field_id");
      if (null != key) {
        Set<String> vals = fieldValMap.get(Integer.parseInt(key.toString()));
        if (vals == null)
          vals = new HashSet<String>();
        fieldValMap.put(Integer.parseInt(key.toString()), vals);
        vals.add(ff.get("field_val_key").toString());
      }
    }
    return fieldValMap;
  }

  private Object generateValueSet(Map<Integer, Set<String>> fieldValMap, ErmFieldEntity field) {
    if(null == fieldValMap)
      fieldValMap = new HashMap<Integer, Set<String>>();
    Set<String> vals = fieldValMap.get(field.getId());
    if (field.getType() == 4)
    {
      if(null == vals)
        vals = new HashSet<String>();
      return vals;
    }
    else {
      if (null != vals && !vals.isEmpty()) {
        for (String string : vals) {
          return string;
        }
        return "";
      } else
        return "";
    }
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
    return ermSchFieldDao.queryTotal(map);
  }

  @Override
  public void save(ErmSchFieldEntity ermSchField) {
    ermSchFieldDao.save(ermSchField);
  }

  @Override
  public void update(ErmSchFieldEntity ermSchField) {
    // 1. 判断要修改的是改什么，如果不是改权重，要新建一个指标。
    if (null == ermSchField.getFieldId() || ermSchField.getFieldId() == 0)// 没有fieldID
    {
      // 说明这个指标是新增的
      ErmFieldEntity schField = new ErmFieldEntity();
      schField.setFieldCode(StringUtil.generateShortUuid());
      schField.setFieldName(ermSchField.getErmFieldEntity().getFieldName());
      schField.setFlag(ermSchField.getSchId());
      schField.setWeight(ermSchField.getErmFieldEntity().getWeight());
      schField.setType(ermSchField.getErmFieldEntity().getType());
      ermFieldDao.save(schField);

      // val新增
      List<ErmFieldValEntity> vals = ermSchField.getErmFieldEntity().getFieldVal();
      for (ErmFieldValEntity val : vals) {
        ErmFieldValEntity valToSave = new ErmFieldValEntity();
        valToSave.setFieldId(schField.getId());
        valToSave.setValKey(StringUtil.generateShortUuid());
        valToSave.setValVal(val.getValVal());
        valToSave.setValWeight(val.getValWeight());
        valToSave.setFlag(ermSchField.getSchId());
        ermFieldValDao.save(valToSave);
      }

      // 建立field sch 关系
      ErmSchFieldEntity toSave = new ErmSchFieldEntity();
      toSave.setFlag(1);// 显示
      toSave.setWeight(schField.getWeight());
      toSave.setFieldId(schField.getId());
      toSave.setSchId(ermSchField.getSchId());
      ermSchFieldDao.save(toSave);
    } else {
      // 有field ID,有可能是field里的weight或val被改了
      ErmFieldEntity toUpdate = ermSchField.getErmFieldEntity();
      toUpdate.setId(ermSchField.getFieldId());
      ermFieldDao.update(toUpdate);

      // 看看val是否有新增或修改的
      List<ErmFieldValEntity> vals = ermSchField.getErmFieldEntity().getFieldVal();
      for (ErmFieldValEntity val : vals) {
        // 如果val没有code或ID，就要新增
        if (val.getValKey() == null) {
          ErmFieldValEntity valToSave = new ErmFieldValEntity();
          valToSave.setFieldId(ermSchField.getFieldId());
          valToSave.setValKey(StringUtil.generateShortUuid());
          valToSave.setValVal(val.getValVal());
          valToSave.setValWeight(val.getValWeight());
          valToSave.setFlag(ermSchField.getSchId());
          ermFieldValDao.save(valToSave);
        } else {
          ermFieldValDao.update(val);
        }
      }
    }
  }

  @Override
  public void delete(Integer id) {
    ermSchFieldDao.delete(id);
  }

  @Override
  public void deleteBatch(Integer[] ids) {
    ermSchFieldDao.deleteBatch(ids);
  }

  @Override
  public List<ColumnHeader> querySchoolFieldHeader(Map<String, Object> params) {
    List<ErmSchFieldEntity> fields = this.queryList(params);
    if (fields == null || fields.size() <= 0) {
      return null;
    }
    List<ColumnHeader> headers = new ArrayList<ColumnHeader>();
    for (int i = 0; i < fields.size(); i++) {
      ErmSchFieldEntity schfield = fields.get(i);
      ErmFieldEntity field = schfield.getErmFieldEntity();
      ColumnHeader header =
          new ColumnHeader(String.valueOf(field.getId()), field.getFieldName(), true);
      if (field.getType() == Constants.FIELD_TYPE_SELECT) {
        header.setSelect(true);
        List<ErmFieldValEntity> fieldVals = field.getFieldVal();
        if (fieldVals.size() > 0) {
          List<String> vals = new ArrayList<String>();
          for (ErmFieldValEntity val : fieldVals) {
            vals.add(val.getValVal());
          }
          header.setSelectInfo(vals);
        }
      }
      headers.add(header);
    }
    return headers;
  }

  @Override
  public boolean doInitSchFieldsConfig(int schId) {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("flag", 0);// 学校公共的指标项，取状态0
    // 查询都有哪些字段可供配置
    List<ErmFieldEntity> fields = ermFieldDao.queryList(map);
    for (ErmFieldEntity ermFieldEntity : fields) {
      // 复制字段
      ErmFieldEntity schField = new ErmFieldEntity();
      schField.setFieldCode(StringUtil.generateShortUuid());
      schField.setFieldName(ermFieldEntity.getFieldName());
      schField.setFlag(schId);
      schField.setWeight(ermFieldEntity.getWeight());
      schField.setType(ermFieldEntity.getType());
      ermFieldDao.save(schField);
      // 复制val
      List<ErmFieldValEntity> vals = ermFieldValDao.queryValListByField(ermFieldEntity.getId(), 0);
      for (ErmFieldValEntity val : vals) {
        ErmFieldValEntity valToSave = new ErmFieldValEntity();
        valToSave.setFieldId(schField.getId());
        valToSave.setValKey(StringUtil.generateShortUuid());
        valToSave.setValVal(val.getValVal());
        valToSave.setValWeight(val.getValWeight());
        valToSave.setFlag(schId);
        ermFieldValDao.save(valToSave);
      }
      // 建立field sch 关系
      ErmSchFieldEntity toSave = new ErmSchFieldEntity();
      toSave.setFlag(1);// 显示
      toSave.setWeight(schField.getWeight());
      toSave.setFieldId(schField.getId());
      toSave.setSchId(schId);
      ermSchFieldDao.save(toSave);
    }
    return true;
  }

  @Override
  public boolean doAddSchField(int schId, ErmFieldEntity field) {
    // 1.存field表，
    field.setFlag(schId);
    field.setFieldCode(StringUtil.generateShortUuid());
    ermFieldDao.save(field);

    // 2. 存field_val表
    List<ErmFieldValEntity> vals = field.getFieldVal();
    for (ErmFieldValEntity ermFieldValEntity : vals) {
      ermFieldValEntity.setFieldId(field.getId());
      // 生成属性的key code
      ermFieldValEntity.setValKey(StringUtil.generateShortUuid());
      ermFieldValEntity.setFlag(schId);
      ermFieldValDao.save(ermFieldValEntity);
    }
    // 3. 存 sch_field表,且flag =1显示
    ErmSchFieldEntity t = new ErmSchFieldEntity();
    t.setFieldId(field.getId());
    t.setFlag(1);
    t.setWeight(field.getWeight());
    t.setSchId(schId);
    ermSchFieldDao.save(t);
    return true;
  }

  @Override
  public void activeBatch(Integer[] ids) {

    ermSchFieldDao.activeBatch(ids);
  }

  @Override
  public boolean hasInited(int schId) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("schId", schId);
    int cc = ermSchFieldDao.queryCount(param);
    return cc > 0;
  }

}
