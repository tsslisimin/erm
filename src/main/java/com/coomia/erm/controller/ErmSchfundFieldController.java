package com.coomia.erm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ColumnHeader;
import com.coomia.erm.service.ErmSchfundFieldService;
import com.coomia.erm.util.ExcelTemplate;
import com.coomia.erm.util.ExcelUtil;
import com.coomia.erm.util.R;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;

/**
 * 学校指标
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-09 16:56:52
 */
@Api(value = "ErmSchfundField-api", description = "有关于学校指标的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermschfundfield")
public class ErmSchfundFieldController {
	@Autowired
	private ErmSchfundFieldService ermSchfundFieldService;

	

	/**
	 * 导出学校指标模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "schoolFieldTemplate", method = RequestMethod.GET)
	public R exportSchoolFieldTemplate(@RequestParam Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		params.put("schId",user.getSchoolId());
		params.put("flag", 1);
		List<Map<String,Object>> dictLists = Lists.newArrayList();
//		Map<String,Object> isPoor = Maps.newHashMap();
//		isPoor.put("type", "8");
//		isPoor.put("name", "贫困类型");
//		dictLists.add(isPoor);
		List<ColumnHeader> schoolFieldHeader = this.ermSchfundFieldService.querySchoolFieldHeader(params,dictLists);
		Map<String, Object> data = ExcelUtil.getDownData(schoolFieldHeader);
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			String fileName = "困难学生录入" + System.currentTimeMillis()+".xls";
			String filePath = "/public/img/work/Tomcat/localhost/ROOT/"+fileName;
			ExcelTemplate.createExcelTemplate(filePath, (String[]) data.get("titles"),
					(List<String[]>) data.get("downData"), (String[]) data.get("downRows"),"困难学生录入");
			return R.ok().put("fileName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
