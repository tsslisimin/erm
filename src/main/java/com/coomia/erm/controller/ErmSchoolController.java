package com.coomia.erm.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.ExcelConstants;
import com.coomia.erm.entity.ErmSchFieldEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.excel.ErmSchoolExcelEntity;
import com.coomia.erm.service.ErmSchFieldService;
import com.coomia.erm.service.ErmSchoolService;
import com.coomia.erm.util.ExcelUtil;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 学校信息
 * 
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:06
 */
@Api(value = "ErmSchool-api", description = "有关于学校信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermschool")
public class ErmSchoolController {
	@Autowired
	private ErmSchoolService ermSchoolService;
	
	@Autowired
	private ErmSchFieldService ermSchFieldService;

	/**
	 * 列表
	 */
	@ApiOperation(value = "获取学校信息列表", notes = "获取学校信息列表")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
  	  UserContext user = (UserContext) token.getPrincipal();
        params.put("ebId", user.getEbId());
		// 查询列表数据
		Query query = new Query(params);

		List<ErmSchoolEntity> ermSchoolList = ermSchoolService.queryList(query);
		int total = ermSchoolService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(ermSchoolList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	
	@ApiOperation(value = "查询学校指标", notes = "查询学校指标")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/querySchField", method = RequestMethod.GET)
	public R querySchField(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		Integer schoolId = user.getSchoolId();
		Map<String,Object> param = Maps.newHashMap();
		param.put("schId", schoolId);
		param.put("flag", 1);
		param.put("stuId", params.get("stuId"));
		List<ErmSchFieldEntity> schField = this.ermSchFieldService.queryList(param);
		return R.ok().put("page", schField);
	}
	
	/**
	 * 通过项目id查询学校信息
	 */
	@ApiOperation(value = "通过项目id查询学校信息", notes = "通过项目id查询学校信息")
	@ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
	@RequestMapping(value = "/listByFundedIdBak",method = RequestMethod.GET)
	public R listByFundedIdBak(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		
		List<ErmSchoolEntity> ermSchoolList = ermSchoolService.queryListByFundedId(query);
		
		PageUtils pageUtil = new PageUtils(ermSchoolList, 0, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	   
    /**
     * 通过项目id查询学校信息
     */
    @ApiOperation(value = "通过项目id查询学校信息", notes = "通过项目id查询学校信息")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/listByFundedId",method = RequestMethod.GET)
    public R listByFundedId(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);
        @SuppressWarnings("unchecked")
        List<ErmSchoolEntity> ermSchoolList = Collections.EMPTY_LIST;
        if(null != params.get("fundedId"))
          ermSchoolList= ermSchoolService.queryInitSchoolsByFundId(Integer.parseInt(params.get("fundedId").toString()));
        PageUtils pageUtil = new PageUtils(ermSchoolList, 0, query.getLimit(), query.getPage());
        
        return R.ok().put("page", pageUtil);
    }
    
    /**
     * 查询学校的所有班级
     */
    @ApiOperation(value = "查询学校的所有班级", notes = "查询学校的所有班级")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/listClazz",method = RequestMethod.GET)
    public R listClazz(@RequestParam Map<String, Object> params,JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		Integer schoolId = user.getSchoolId();
    	params.put("schId", schoolId);
    	List<Map<String,Object>> clazzs = this.ermSchoolService.querySchoolClazzs(params);
    	return R.ok().put("page", clazzs);
    }
    /**
     * 查询学校的所有年级班级List
     */
    @ApiOperation(value = "查询学校的所有年级班级List", notes = "查询学校的所有年级班级List")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/querySchGradeClazzList",method = RequestMethod.GET)
    public R querySchGradeClazzList(@RequestParam Map<String, Object> params,JwtAuthenticationToken token) {
    	UserContext user = (UserContext) token.getPrincipal();
    	Integer schoolId = user.getSchoolId();
    	params.put("schId", schoolId);
    	Map<String,Set<String>> gradeClazzList = this.ermSchoolService.querySchGradeClazzList(params);
    	return R.ok().put("page", gradeClazzList);
    }

	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取学校信息", notes = "根据ID获取学校信息")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
	public R info(@PathVariable("id") Integer id) {
		ErmSchoolEntity ermSchool = ermSchoolService.queryObject(id);
		return R.ok().put("ermSchool", ermSchool);
	}

	
	/**
	 * 信息
	 */
	@ApiOperation(value = "根据ID获取学校信息", notes = "根据ID获取学校信息")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
	@RequestMapping(value = "/schInfo", method = RequestMethod.GET)
	public R info(JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		ErmSchoolEntity ermSchool = ermSchoolService.queryObject(user.getSchoolId());
		return R.ok().put("ermSchool", ermSchool);
	}

	/**
	 * 保存
	 */
	@ApiOperation(value = "新增学校信息", notes = "新增学校信息")
	@ApiImplicitParam(name = "ErmSchool", value = "学校信息对象", required = true, dataType = "ErmSchool")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public R save(@RequestBody ErmSchoolEntity ermSchool, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		ermSchool.setEbId(user.getEbId());
		ermSchool.setCreator(user.getUsername());
		boolean flag =  ermSchoolService.save(ermSchool);
		if(flag)
          return R.ok();
        else
          return R.error("添加失败, 记录已存在！");
	}

	/**
	 * 修改
	 */
	@ApiOperation(value = "修改学校信息", notes = "修改学校信息")
	@ApiImplicitParam(name = "ErmSchool", value = "学校信息对象", required = true, dataType = "ErmSchool")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public R update(@RequestBody ErmSchoolEntity ermSchool, JwtAuthenticationToken token) {
		UserContext user = (UserContext) token.getPrincipal();
		if (user.getSchoolId()!=null){
		ermSchool.setId(user.getSchoolId());}
		ermSchool.setUpdator(user.getUsername());
		ermSchool.setEbId(user.getEbId());
		ermSchoolService.update(ermSchool);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@ApiOperation(value = "删除学校信息", notes = "删除学校信息")
	@ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public R delete(@RequestBody Integer[] ids) {
		ermSchoolService.deleteBatch(ids);

		return R.ok();
	}

	@ApiOperation(value = "导入学校信息", notes = "导入学校信息")
	@ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
	@RequestMapping(value = "importSchoolInfo", method = RequestMethod.POST)
	public R importSchoolInfo(@RequestParam(value = "file") MultipartFile file, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        Integer ebId = user.getEbId();
		if (file.isEmpty()) {
			return R.error("上传文件不存在");
		}
		String fileName = file.getOriginalFilename();
		String fileSuffixName = fileName.substring(fileName.lastIndexOf("."));
		if (!".xls".equals(fileSuffixName) && !".xlsx".equals(fileSuffixName)) {
			return R.error("文件格式不正确");
		}
		Map<String, Object> importInfo = null;
		try {
			importInfo = ermSchoolService.importSchoolInfo(file.getInputStream(),ebId);
		} catch (IOException e) {
			return R.error(e.getMessage());
		}
		return R.ok().put("resultData", importInfo);
	}

	@ApiOperation(value = "导出学校信息", notes = "导出学校信息")
	@RequestMapping(value = "exportSchoolInfo", method = RequestMethod.POST)
	public void exportSchoolInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        params.put("ebId", user.getEbId());
		Query query = new Query(params);
		List<ErmSchoolEntity> ermSchoolList = ermSchoolService.queryList(query);
		List<ErmSchoolExcelEntity> schoolExcelList = ExcelUtil.convertSchoolExcelList(ermSchoolList);
		String fileName = ExcelConstants.SCHOOL_TITLE + System.currentTimeMillis() + ".xls";
		ExcelUtil.downLoadExcelForVO(request, response, fileName, schoolExcelList, ExcelConstants.SCHOOL_TITLE_NAME,
				ExcelConstants.SCHOOL_TITLE, ExcelConstants.SCHOOL_SHEET_NAME);
	}

}
