package com.coomia.erm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coomia.erm.constants.Constants;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.entity.ErmFieldEntity;
import com.coomia.erm.entity.ErmFieldValEntity;
import com.coomia.erm.entity.ErmSchFieldEntity;
import com.coomia.erm.service.ErmDictService;
import com.coomia.erm.service.ErmSchFieldService;
import com.coomia.erm.service.ErmSchfundFieldService;



@Service("ermSchfundFieldService")
public class ErmSchfundFieldServiceImpl implements ErmSchfundFieldService {
	
	@Autowired
	private ErmSchFieldService ermSchFieldService;
	
	@Autowired
	private ErmDictService ermDictService;

	@Override
	public List<ColumnHeader> querySchoolFieldHeader(Map<String, Object> params) {
		List<ErmSchFieldEntity> fields = this.ermSchFieldService.queryList(params);
		if(fields == null || fields.size()<= 0) {
			return null;
		}
		List<ColumnHeader> headers = new ArrayList<ColumnHeader>();
		for(int i=0;i<fields.size();i++) {
			ErmSchFieldEntity schfield = fields.get(i);
			ErmFieldEntity field = schfield.getErmFieldEntity();
			ColumnHeader header = new ColumnHeader(String.valueOf(field.getId()), field.getFieldName(), true);
			if(field.getType() == Constants.FIELD_TYPE_SELECT) {
				header.setSelect(true);
				List<ErmFieldValEntity> fieldVals = field.getFieldVal();
				if(fieldVals.size()>0) {
					List<String> vals = new ArrayList<String>();
					for(ErmFieldValEntity val:fieldVals) {
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
	public List<ColumnHeader> querySchoolFieldHeader(Map<String, Object> params, List<Map<String,Object>> dicts) {
		if (dicts == null || dicts.size() <= 0) {
			return querySchoolFieldHeader(params);
		}
		List<ColumnHeader> headers = this.querySchoolFieldHeader(params);
		for(int i=0;i<dicts.size();i++) {
			Map<String,Object> param = dicts.get(i);
			List<ErmDictEntity> dictList =  this.ermDictService.queryList(param);
			if(dictList != null && dictList.size() > 0) {
				ColumnHeader header = new ColumnHeader((String)param.get("type"),(String)param.get("name") , true);
				header.setSelect(true); 
				List<String> vals = new ArrayList<String>();
				for(ErmDictEntity val:dictList) {
					vals.add(val.getDictName());
				}
				header.setSelectInfo(vals);
				headers.add(0,header);
			}
		}
		return headers;
	}
	
}
