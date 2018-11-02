package com.coomia.erm.controller;

import java.util.List;
import java.util.Map;

import com.coomia.erm.entity.ErmFundedEntity;
import com.coomia.erm.service.ErmFundedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.service.ErmDictService;
import com.coomia.erm.util.BusinessUtil;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * 字典表
 *
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-08 23:37:37
 */
@Api(value = "ErmDict-api", description = "有关于字典表的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermdict")
public class ErmDictController {
    @Autowired
    private ErmDictService ermDictService;
    @Autowired
    private ErmFundedService ermFundedService;

    /**
     * 列表
     */
    @ApiOperation(value = "获取字典表列表", notes = "获取字典表列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ErmDictEntity> ermDictList = ermDictService.queryList(query);
        int total = ermDictService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(ermDictList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取学生信息字典表", notes = "获取学生信息字典表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/stuDict", method = RequestMethod.GET)
    public R stuDict(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("minType", 1);
        query.put("maxType", 20);
        Map<String, Object> ermDictList = ermDictService.querystuDict(query);

        return R.ok().put("dict", ermDictList);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取学校类型", notes = "获取学校类型")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/schTypeDict", method = RequestMethod.GET)
    public R schTypeDict(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("type", 21);
        Map<String, Object> ermDictList = ermDictService.querystuDict(query);

        return R.ok().put("dict", ermDictList);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取学期", notes = "获取学期")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/semesterDict", method = RequestMethod.GET)
    public R semesterDict(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("type", 22);
        Map<String, Object> ermDictList = ermDictService.querystuDict(query);

        return R.ok().put("dict", ermDictList);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取单个类别数据", notes = "获取单个类别数据")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/getFundDictData", method = RequestMethod.GET)
    public R getDictData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        Map<String, Object> ermDictList = ermDictService.querystuDict(query);

        return R.ok().put("dict", ermDictList);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "获取单个类别数据", notes = "获取单个类别数据")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/getFundDictDataByType", method = RequestMethod.GET)
    public R getFundDictDataByType(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ErmDictEntity> type = ermDictService.getDictByType(params.get("type").toString());
        ErmDictEntity term = ermDictService.getDictNameByName(params.get("term").toString(), "22");
        if (term != null) {
            for (ErmDictEntity entity : type) {
                ErmFundedEntity ermFundedEntity = ermFundedService.selectByNameAndSemester(term.getDictCode(),entity.getDictName());
                if (ermFundedEntity != null) {
		   	      entity.setStatus(ermFundedEntity.getStatus().toString());
                }else {
                    entity.setStatus("1");
                }
            }
        }


        return R.ok().put("dict", type);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据ID获取字典表", notes = "根据ID获取字典表")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public R info(@PathVariable("id") Integer id) {
        ErmDictEntity ermDict = ermDictService.queryObject(id);
        return R.ok().put("ermDict", ermDict);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增字典表", notes = "新增字典表")
    @ApiImplicitParam(name = "ErmDict", value = "字典表对象", required = true, dataType = "ErmDict")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(@RequestBody ErmDictEntity ermDict) {
        ermDictService.save(ermDict);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/testSave", method = RequestMethod.POST)
    public R testSave(Integer type, String value) {
        String[] vals = value.split(",");
        for (int i = 0; i < vals.length; i++) {
            ErmDictEntity dict = new ErmDictEntity();
            dict.setType(type);
            dict.setDictCode(BusinessUtil.getCodeUUID());
            dict.setDictName(vals[i]);
            ermDictService.save(dict);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改字典表", notes = "修改字典表")
    @ApiImplicitParam(name = "ErmDict", value = "字典表对象", required = true, dataType = "ErmDict")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody ErmDictEntity ermDict) {
        ermDictService.update(ermDict);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除字典表", notes = "删除字典表")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(@RequestBody Integer[] ids) {
        ermDictService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 生成学期字典
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/createSemester", method = RequestMethod.POST)
    public R createSemester(String years) {
        List<String> semesters = this.ermDictService.createSemester(years);
        return R.ok().put("semester", semesters);
    }


}
