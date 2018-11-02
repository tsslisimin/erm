package com.coomia.erm.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coomia.erm.dao.TbErmLogDao;
import com.coomia.erm.entity.*;
import com.coomia.erm.service.*;
import com.coomia.erm.util.*;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.constants.DictConstants;
import com.coomia.erm.constants.ExcelConstants;
import com.coomia.erm.entity.excel.ErmStudentExcelEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 学生信息
 *
 * @author leequn
 * @email lijichuen@gmail.com
 * @date 2017-11-03 23:15:08
 */
@Api(value = "ErmStudent-api", description = "有关于学生信息的操作", position = 5)
@RestController
@RequestMapping("/erm/api/ermstudent")
public class ErmStudentController {

    @Autowired
    private ErmStudentService ermStudentService;

    @Autowired
    private ErmDictService ermDictService;

    @Autowired
    private ErmFundedInfoService ermFundedInfoService;

    @Autowired
    private ErmSchFieldService ermSchFieldService;
    @Autowired
    private ErmLogService ermLogService;

    @Autowired
    private ErmFamilyService ermFamilyService;
    @Autowired
    private ErmSchoolService ermSchoolService;


    /**
     * 列表
     */
    @ApiOperation(value = "获取学生信息列表", notes = "获取学生信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        // 查询列表数据
        Query query = new Query(params);
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && user.getSchoolId() != 0)
            query.put("schId", user.getSchoolId());
        List<ErmStudentEntity> ermStudentList = ermStudentService.queryList(query);
        int total = ermStudentService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(ermStudentList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "导出学生信息列表", notes = "导出学生信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/exportStudentList", method = RequestMethod.GET)
    public Object exportStudentList(@RequestParam Map<String, Object> params, JwtAuthenticationToken token, HttpServletRequest req, HttpServletResponse resp) {
        // 查询列表数据
        Query query = new Query(params);
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && user.getSchoolId() != 0)
            query.put("schId", user.getSchoolId());
        Object ret = ermStudentService.exportStudentList(query, req, resp);
        return ret;

    }

    /**
     * 学生申请表信息列表
     */
    @ApiOperation(value = "学生申请表信息列表", notes = "学生申请表信息列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/stuApplyList", method = RequestMethod.GET)
    public R stuApplyList(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        // 查询列表数据
        Query query = new Query(params);
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && user.getSchoolId() != 0)
            query.put("schId", user.getSchoolId());
        List<ErmStudentEntity> ermStudentList = ermStudentService.queryList(query);
        int total = ermStudentService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(ermStudentList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据ID获取学生信息", notes = "根据ID获取学生信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public R info(@PathVariable("id") Integer id) {
        ErmStudentEntity ermStudent = ermStudentService.queryObject(id, null);
        // 查询困难信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("studentId", id + "");
        params.put("flag", 1);
        List<Map<String, Object>> ermFunedInfo = ermFundedInfoService.fundedStudentById(params);
        return R.ok().put("ermStudent", ermStudent).put("fundedInfo", ermFunedInfo);
    }

    @ApiOperation(value = "根据学生id获取学生信息", notes = "根据学生id获取学生信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/stuInfo", method = RequestMethod.GET)
    public R getStuInfoByStuId(Integer stuId, Integer schFundId, Integer fundId) {
        Map<String, Object> stuInfo = ermStudentService.queryStudentApplyInfoById(stuId, schFundId, fundId);
        return R.ok().put("stuInfo", stuInfo);
    }


    @ApiOperation(value = "根据学生id获取困难学生信息", notes = "根据学生id获取困难学生信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/diffStuInfo", method = RequestMethod.GET)
    public R diffStuInfo(Integer stuId, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        Integer schoolId = user.getSchoolId();
        Map<String, Object> param = Maps.newHashMap();
        param.put("schId", schoolId);
        param.put("flag", 1);
        param.put("stuId", String.valueOf(stuId));
        List<ErmSchFieldEntity> schField = this.ermSchFieldService.queryList(param);
        ErmStudentEntity ermStudent = this.ermStudentService.queryObject(stuId, "selectForUpdate");
        Map<String, Object> diffStuInfo = Maps.newHashMap();
        diffStuInfo.put("stuInfo", ermStudent);
        diffStuInfo.put("diffInfo", schField);
        return R.ok().put("diffStuInfo", diffStuInfo);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "根据ID获取学生信息", notes = "根据ID获取学生信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Integer")
    @RequestMapping(value = "/update/info/{id}", method = RequestMethod.GET)
    public R updateInfo(@PathVariable("id") Integer id) {
        String type = "update";
        ErmStudentEntity ermStudent = ermStudentService.queryObject(id, type);
        return R.ok().put("ermStudent", ermStudent);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "新增学生信息", notes = "新增学生信息")
    @ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(@RequestBody ErmStudentEntity ermStudent, Integer fundedId, JwtAuthenticationToken token) {
        // ErmSchoolFundedEntity dbEntity =
        // ermSchoolFundedService.queryObject(fundedId);
        // if (dbEntity.getStatus() == FundStatus.CLOSE.getCode())
        // return R.error("该流程环节不允许进行提交!");
        UserContext user = (UserContext) token.getPrincipal();

        Map<String, Object> stuParam = new HashMap<String, Object>();
        stuParam.put("schoolId", user.getSchoolId());
        stuParam.put("idcard", ermStudent.getIdcard());
        ErmStudentEntity entity = this.ermStudentService.queryObjectByMap(stuParam);

        Map<String, Object> checkParam = new HashMap<String, Object>();
        checkParam.put("name", ermStudent.getName());
        checkParam.put("idcard", ermStudent.getIdcard());
        checkParam.put("isGraduation", 0);
        ErmStudentEntity checkEntity = this.ermStudentService.queryObjectByMap(checkParam);
        if (entity == null && checkEntity != null) {
            ErmSchoolEntity ermSchoolEntity = ermSchoolService.queryObject(checkEntity.getSchoolId());
            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("学生新增异常");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("importStudentInfo");
            tbErmLog.setServiceId("importStudentInfo");
            tbErmLog.setMsg("学生新增异常,学生数据已存在" + ermSchoolEntity.getName());
            tbErmLog.setCreateTime(new Date());
            ermLogService.save(tbErmLog);
            return R.error("学生新增异常,学生数据已存在" + ermSchoolEntity.getName());
        } else if (entity != null) {
            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("学生新增异常");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("importStudentInfo");
            tbErmLog.setServiceId("importStudentInfo");
            tbErmLog.setMsg("学生新增异常,学生数据已存在");
            tbErmLog.setCreateTime(new Date());
            ermStudentService.save(ermStudent, user, fundedId);
            ermLogService.save(tbErmLog);
            // 如果存在则插入
            return R.ok("学生数据已存在!,学生信息已更新");

        } else {
            ermStudentService.save(ermStudent, user, fundedId);
            return R.ok("提交成功!");
        }


    }

    @ApiOperation(value = "新增学生信息", notes = "新增学生信息")
    @ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public R saveStudent(@RequestBody ErmStudentEntity ermStudent, Integer fundedId, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();

        if (!ermStudent.getIsPoor().equals("n000001571352444")) //n000001571352444   参：StuDictConstants里的值
        {
            ermStudent.setScore(100);
            ermStudent.setDiffLevel(3);
            ermStudent.setHeadTeacheCheck(1);
            ermStudent.setDiffLevelStr(DictConstants.DIFF_LEVEL.get(ermStudent.getDiffLevel()));
        }
        ermStudentService.save(ermStudent, user, fundedId);
        return R.ok("提交成功!");
    }

    @ApiOperation(value = "新增困难学生信息", notes = "新增困难学生信息")
    @ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
    @RequestMapping(value = "/saveDiffStudent", method = RequestMethod.POST)
    public R saveDiffStudent(@RequestBody ErmStudentEntity ermStudent, Integer fundedId, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ErmStudentEntity baseStudent = this.ermStudentService.queryStudentByIdCard(user.getSchoolId(),
                ermStudent.getName(), ermStudent.getIdcard());
        if (baseStudent != null) {
//          if(!ermStudent.getIsPoor().equals("n000001571352444")) //n000001571352444   参：StuDictConstants里的值
//          {
//            ermStudent.setScore(100);
//            ermStudent.setDiffLevel(3);
//            ermStudent.setHeadTeacheCheck(1);
//            ermStudent.setDiffLevelStr(DictConstants.DIFF_LEVEL.get(ermStudent.getDiffLevel()));
//          }
            ermStudentService.save(ermStudent, user, fundedId);

            return R.ok("提交成功!");
        } else {

            TbErmLog tbErmLog = new TbErmLog();
            tbErmLog.setTitle("新增困难学生信息异常");
            tbErmLog.setCreateId(user.getUserId());
            tbErmLog.setMethod("saveDiffStudent");
            tbErmLog.setServiceId("saveDiffStudent");
            tbErmLog.setMsg("新增困难学生信息异常,不存在该学生信息");
            tbErmLog.setCreateTime(new Date());
            ermLogService.save(tbErmLog);
            return R.error("不存在该学生信息");
        }
    }

    @ApiOperation(value = "删除困难学生信息", notes = "删除困难学生信息")
    @ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
    @RequestMapping(value = "/deleteDiffStudent", method = RequestMethod.POST)
    public R deleteDiffStudent(Integer stuId, JwtAuthenticationToken token) {
        this.ermStudentService.deleteDiffInfo(stuId);
        return R.ok("删除成功！");
    }

    @ApiOperation(value = "删除困难学生信息", notes = "删除困难学生信息")
    @ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
    @RequestMapping(value = "/deleteDiffStudents", method = RequestMethod.POST)
    public R deleteDiffStudents(Integer[] stuIds, JwtAuthenticationToken token) {
        for (Integer stuId : stuIds) {
            this.ermStudentService.deleteDiffInfo(stuId);
        }
        return R.ok("删除成功！");
    }

    /**
     * 修改
     */
    @ApiOperation(value = "修改学生信息", notes = "修改学生信息")
    @ApiImplicitParam(name = "ErmStudent", value = "学生信息对象", required = true, dataType = "ErmStudent")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody ErmStudentEntity ermStudent, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        ermStudent.setUpdator(user.getUsername());
        if (null == ermStudent.getSchoolId())
            ermStudent.setSchoolId(user.getSchoolId()); //将操作员的学校ID设置为学生的学校ID
        ermStudentService.update(ermStudent);
        return R.ok("修改成功！");
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除学生信息", notes = "删除学生信息")
    @ApiImplicitParam(name = "id", value = "id数组", required = true, dataType = "array")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(@RequestBody Integer[] ids) {
        ermStudentService.deleteBatch(ids);

        return R.ok("删除成功！");
    }

    @ApiOperation(value = "导入学生信息", notes = "导入学生信息")
    @ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
    @RequestMapping(value = "importStudentInfo", method = RequestMethod.POST)
    public R importStudentInfo(@RequestParam(value = "file") MultipartFile file, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        Integer schoolId = user.getSchoolId();
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
            importInfo = ermStudentService.importStudentInfo(file.getInputStream(), schoolId, user.getUserId());
        } catch (IOException e) {
            return R.error(e.getMessage());
        }
        return R.ok().put("resultData", importInfo);
    }

    @ApiOperation(value = "导入学生银行卡信息", notes = "导入学生银行卡信息")
    @ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
    @RequestMapping(value = "importStudentBankCardInfo/{schoolId}", method = RequestMethod.POST)
    public R importStudentBankCardInfo(@RequestParam(value = "file") MultipartFile file,
                                       @PathVariable("schoolId") Integer schoolId, JwtAuthenticationToken token) {
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
            importInfo = ermStudentService.importStudentBankCard(file.getInputStream(), schoolId);
        } catch (IOException e) {
            return R.error(e.getMessage());
        }
        return R.ok().put("resultData", importInfo);
    }

    @ApiOperation(value = "银行卡比对", notes = "银行卡比对")
    @ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
    @RequestMapping(value = "compareIdcard/{schoolId}", method = RequestMethod.POST)
    public R compareIdcard(@RequestParam(value = "file") MultipartFile file, @PathVariable("schoolId") Integer schoolId,
                           JwtAuthenticationToken token) {
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
            importInfo = ermStudentService.compareIdcard(file.getInputStream(), schoolId);
        } catch (IOException e) {
            return R.error(e.getMessage());
        }
        return R.ok().put("resultData", importInfo);
    }

    @ApiOperation(value = "导入学生pic信息", notes = "导入学生pic信息")
    @ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
    @RequestMapping(value = "importStudentPicInfo", method = RequestMethod.POST)
    public R importStudentPicInfo(@RequestParam(value = "file") MultipartFile file, JwtAuthenticationToken token) {
        ZipInputStream zs = null;
        UserContext user = (UserContext) token.getPrincipal();
        Integer schoolId = user.getSchoolId();
        if (file.isEmpty()) {
            return R.error("上传文件不存在");
        }
        String fileName = file.getOriginalFilename();
        String fileSuffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!".zip".equals(fileSuffixName)) {
            return R.error("文件格式不正确");
        }

        Map<String, Object> importInfo = new HashMap<String, Object>();
        int num = 0;
        List<String> failList = Lists.newArrayList();
        try {
            zs = new ZipInputStream(file.getInputStream(), Charset.forName("GBK"));
            // 解决中文问题，linux下中文路径，图片显示问题
            fileName = UUID.randomUUID() + fileSuffixName;
            File dest = new File(fileName);
            file.transferTo(dest);
            BufferedInputStream bs = new BufferedInputStream(zs);
            ZipEntry ze;
            byte bufer[] = new byte[1024];
            while ((ze = zs.getNextEntry()) != null) {
                String picName = ze.getName(); // pictureName
                // 文件名
                if (!picName.contains("."))
                    continue;
                String prefix = picName.substring(0, picName.lastIndexOf("."));
                // 后缀名
                String suffix = picName.substring(picName.lastIndexOf(".") + 1);
                String picNewName = UUID.randomUUID() + "." + suffix;
                // TODO 这里的目录要对应 linux中的 类似这个目录： /public/img/work/Tomcat/localhost/ROOT
                String uploadDir = "/public/img/work/Tomcat/localhost/ROOT"; // TODO
                FileOutputStream outputStream = new FileOutputStream(new File(uploadDir, picNewName));
                int len = 0;
                while (-1 != (len = bs.read(bufer))) {
                    outputStream.write(bufer, 0, len);
                }
                outputStream.close();
                String idCard = prefix.trim();
                if (idCard.contains("/"))
                    idCard = idCard.substring(idCard.lastIndexOf("/") + 1);
                ErmStudentEntity stu = ermStudentService.queryStudentByIdCard(schoolId, null, idCard);
                if (null != stu) {
                    stu.setPhotoUrl(picNewName);
                    ermStudentService.update(stu);
                    num++;
                } else {
                    TbErmLog tbErmLog = new TbErmLog();
                    tbErmLog.setTitle("身份证，导入学生图片异常");
                    tbErmLog.setCreateId(user.getUserId());
                    tbErmLog.setMethod("importStudentPicInfo");
                    tbErmLog.setServiceId("importStudentPicInfo");
                    tbErmLog.setMsg(idCard + " 身份证，导入学生图片异常");
                    tbErmLog.setCreateTime(new Date());
                    ermLogService.save(tbErmLog);

                    failList.add(idCard + " 身份证，导入学生图片异常");
                }

            }
            bs.close();
            zs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        importInfo.put("successNum", num);
        importInfo.put("failList", failList);
        return R.ok().put("resultData", importInfo);
    }

    @ApiOperation(value = "导入学生申请表拍照pic信息", notes = "导入学生申请表拍照pic信息")
    @ApiImplicitParam(name = "file", value = "导入的文件", required = true, dataType = "file")
    @RequestMapping(value = "importApplyPics/{schFundId}", method = RequestMethod.POST)
    public R importApplyPics(@RequestParam(value = "file") MultipartFile file,
                             @PathVariable("schFundId") Integer schFundId, JwtAuthenticationToken token) {
        ZipInputStream zs = null;
        UserContext user = (UserContext) token.getPrincipal();
        Integer schoolId = user.getSchoolId();
        if (file.isEmpty()) {
            return R.error("上传文件不存在");
        }
        String fileName = file.getOriginalFilename();
        String fileSuffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!".zip".equals(fileSuffixName)) {
            return R.error("文件格式不正确");
        }

        Map<String, Object> importInfo = new HashMap<String, Object>();
        List<String> failList = Lists.newArrayList();
        int num = 0;
        try {
            zs = new ZipInputStream(file.getInputStream());
            // 解决中文问题，linux下中文路径，图片显示问题
            fileName = UUID.randomUUID() + fileSuffixName;
            File dest = new File(fileName);
            file.transferTo(dest);
            BufferedInputStream bs = new BufferedInputStream(zs);
            ZipEntry ze;
            byte bufer[] = new byte[1024];
            while ((ze = zs.getNextEntry()) != null) {
                String picName = ze.getName(); // pictureName
                // 文件名
                if (!picName.contains("."))
                    continue;
                String prefix = picName.substring(0, picName.lastIndexOf("."));
                // 后缀名
                String suffix = picName.substring(picName.lastIndexOf(".") + 1);
                String picNewName = UUID.randomUUID() + "." + suffix;
                // TODO 这里的目录要对应 linux中的 类似这个目录： /public/img/work/Tomcat/localhost/ROOT
                String uploadDir = "/public/img/work/Tomcat/localhost/ROOT"; // TODO
                FileOutputStream outputStream = new FileOutputStream(new File(uploadDir, picNewName));
                int len = 0;
                while (-1 != (len = bs.read(bufer))) {
                    outputStream.write(bufer, 0, len);
                }
                outputStream.close();
                String idCard = prefix.trim();
                if (idCard.contains("/"))
                    idCard = idCard.substring(idCard.lastIndexOf("/") + 1);
                ErmFundedInfoEntity info = ermFundedInfoService.queryObject(schFundId, idCard, schoolId);
                if (null != info) {
                    info.setApplypicurl(picNewName);
                    ermFundedInfoService.update(info);
                    num++;
                } else {

                    TbErmLog tbErmLog = new TbErmLog();
                    tbErmLog.setTitle("身份证，导入学生图片异常");
                    tbErmLog.setCreateId(user.getUserId());
                    tbErmLog.setMethod("importStudentPicInfo");
                    tbErmLog.setServiceId("importStudentPicInfo");
                    tbErmLog.setMsg(idCard + " 身份证，导入学生图片异常");
                    tbErmLog.setCreateTime(new Date());
                    ermLogService.save(tbErmLog);
                    failList.add(idCard + " 身份证，导入学生图片异常");
                }

            }
            bs.close();
            zs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        importInfo.put("successNum", num);
        importInfo.put("failList", failList);
        return R.ok().put("resultData", importInfo);
    }

    @ApiOperation(value = "导出学生信息", notes = "导出学生信息")
    @RequestMapping(value = "exportStudentInfo", method = {RequestMethod.GET, RequestMethod.POST})
    public void exportStudentInfo(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody Map<String, Object> params, JwtAuthenticationToken token) throws Exception {
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && 0 != user.getSchoolId())
            params.put("schId", user.getSchoolId());
        String fileName = ExcelConstants.SCHOOL_TITLE + System.currentTimeMillis() + ".xls";
        List<ErmDictEntity> dictList = this.ermDictService.queryList(null);
        List<ErmStudentEntity> stuLists = this.ermStudentService.queryList(params);
        List<ErmStudentExcelEntity> excelList = ExcelUtil.convertStudentEntity(stuLists);
        ExcelUtil.downStudentModelXLS(request, response, fileName, excelList, ExcelConstants.STUDENT_TITLE_NAME,
                ExcelConstants.STUDENT_TITLE, ExcelConstants.STUDENT_SHEET_NAME, dictList);
    }

    /**
     * 导入资助学生名单
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/importAuditStudentInfo", method = RequestMethod.POST)
    public R importAuditStudentInfo(@RequestParam(value = "file") MultipartFile file, Integer fundedId,
                                    JwtAuthenticationToken token) {
        if (file.isEmpty()) {
            return R.error("上传文件不存在");
        }
        String fileName = file.getOriginalFilename();
        String fileSuffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!".xls".equals(fileSuffixName) && !".xlsx".equals(fileSuffixName)) {
            return R.error("文件格式不正确");
        }
        // ErmSchoolFundedEntity dbEntity =
        // ermSchoolFundedService.queryObject(fundedId);
        // if (dbEntity.getStatus() == FundStatus.CLOSE.getCode())
        // return R.error("该流程环节不允许进行提交!");
        UserContext user = (UserContext) token.getPrincipal();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("schId", user.getSchoolId());
        params.put("flag", 1);
        // if (fundedId == null) {
        // throw new RuntimeException("项目Id不存在");
        // }
        List<ColumnHeader> schoolFieldHeader = this.ermSchFieldService.querySchoolFieldHeader(params);
        Map<String, Object> importInfo = null;
        try {
            importInfo = ermStudentService.importAuditStudentInfo(file.getInputStream(), schoolFieldHeader, fundedId,
                    user.getSchoolId(), user.getUserId());
        } catch (IOException e) {
            return R.error(e.getMessage());
        }
        return R.ok().put("resultData", importInfo);
    }

    /**
     * 困难学生列表 定义:困难学生花名册（学校【操作员和校长端】） diffLevel schId headTeacheCheck
     */
    @ApiOperation(value = "困难学生列表", notes = "困难学生列表")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/listPoor", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('OPER', 'SCH')")
    public R listPoorStudents(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        // 查询列表数据
        Query query = new Query(params);
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && user.getSchoolId() != 0)
            query.put("schId", user.getSchoolId());
        query.put("headTeacheCheck", 1); // 班主任通过的才叫困难学生
        List<ErmStudentEntity> ermStudentList = ermStudentService.queryList(query);
        int total = ermStudentService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(ermStudentList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 申报对象名单 定义:申报对象名单（学校【校长端】） 通过了校长申请的学生. 结合 fund_info表及学生表查询.
     */
    @ApiOperation(value = "申报对象名单", notes = "申报对象名单")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/listFundable", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('OPER', 'SCH')")
    public R listFundableStudents(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        // 查询列表数据
        Query query = new Query(params);
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && user.getSchoolId() != 0)
            query.put("schId", user.getSchoolId());

        PageUtils pageUtil = null; // TODO

        return R.ok().put("page", pageUtil);
    }

    /**
     * 资助学生名单 定义: 教育局审批通过, 且已放款的学生. 结合 fund_info表及学生表查询, 只是状态不一样,跟上面的接口类似.
     * 资助学生名单（学校和教育局）
     */
    @ApiOperation(value = "资助学生名单", notes = "资助学生名单")
    @ApiImplicitParam(name = "params", value = "分页参数", required = true, dataType = "Map")
    @RequestMapping(value = "/listFunded", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('OPER', 'SCH', 'EB')")
    public R listFundedtudents(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {
        // 查询列表数据
        Query query = new Query(params);
        UserContext user = (UserContext) token.getPrincipal();
        if (null != user.getSchoolId() && user.getSchoolId() != 0)
            query.put("schId", user.getSchoolId());

        PageUtils pageUtil = null; // TODO

        return R.ok().put("page", pageUtil);
    }


    /**
     * 根据学校ID、年级、班级、姓名 查询学生信息
     */
    @ApiOperation(value = "根据学校ID、年级、班级、姓名 查询学生信息", notes = "根据学校ID、年级、班级、姓名 查询学生信息")
    @RequestMapping(value = "/listStudentsByParam", method = RequestMethod.GET)
    public R listStudentsByParam(@RequestParam Map<String, Object> params) {

        List<ErmStudentVo> studentList = ermStudentService.queryStudentsByParam(params);
        //Map<String, Map<String, Set<ErmStuVo>>> grades = convertData(studentList);
        return R.ok().put("grades", studentList);
    }

    /**
     * 根据学校ID、年级、班级、姓名 查询学生信息
     */
    @ApiOperation(value = "根据学校ID、年级、班级、姓名 查询学生信息", notes = "根据学校ID、年级、班级、姓名 查询学生信息")
    @RequestMapping(value = "/queryStudentsBySchoolId", method = RequestMethod.GET)
    public R queryStudentsBySchoolId(@RequestParam Map<String, Object> params) {

        List<ErmStudentEntity> ermStudentEntities = ermStudentService.queryStudentsBySchoolId(params);
        ermStudentEntities.forEach(item -> {
            if (item.getIsPoor().equals("q000001725581479")) {
                item.setIsArchives(1);
            } else {
                item.setIsArchives(0);
            }
        });
        ErmFamilyEntity ermFamilyEntity = null;
        if (ermStudentEntities.get(0).getFamilyId() != null) {
            ermFamilyEntity = ermFamilyService.queryObject(ermStudentEntities.get(0).getFamilyId());
        }

        //Map<String, Map<String, Set<ErmStuVo>>> grades = convertData(studentList);
        return R.ok().put("grades", ermStudentEntities).put("family", ermFamilyEntity);
    }

    /**
     * 根据学校ID、年级、班级、姓名 查询学生信息
     */
    @ApiOperation(value = "根据学校ID、年级、班级、姓名 查询学生信息", notes = "根据学校ID、年级、班级、姓名 查询学生信息")
    @RequestMapping(value = "/queryStudentsByStuId", method = RequestMethod.GET)
    public R queryStudentsByStuId(@RequestParam Map<String, Object> params) {

        List<ErmStudentEntity> ermStudentEntities = ermStudentService.queryStudentsBySchoolId(params);
        ErmFamilyEntity ermFamilyEntity = null;
        if (ermStudentEntities.get(0).getFamilyId() != null) {
            ermFamilyEntity = ermFamilyService.queryObject(ermStudentEntities.get(0).getFamilyId());
        }

        //Map<String, Map<String, Set<ErmStuVo>>> grades = convertData(studentList);
        return R.ok().put("grades", ermStudentEntities).put("family", ermFamilyEntity);
    }

    private Map<String, Map<String, Set<ErmStuVo>>> convertData(List<ErmStudentVo> studentList) {
        Map<String, Map<String, Set<ErmStuVo>>> grades =
                new HashMap<String, Map<String, Set<ErmStuVo>>>();
        for (ErmStudentVo s : studentList) {
            // clazzs
            Map<String, Set<ErmStuVo>> clazzs = grades.get(s.getGrade());
            if (null == clazzs)
                clazzs = new HashMap<String, Set<ErmStuVo>>();
            grades.put(s.getGrade(), clazzs);
            Set<ErmStuVo> stus = clazzs.get(s.getClazz());
            if (null == stus)
                stus = new HashSet<ErmStuVo>();
            clazzs.put(s.getClazz(), stus);
            stus.add(new ErmStuVo(s.getName(), s.getIdcard()));
        }
        return grades;
    }


    /**
     * 导出学校指标模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "checkTemp", method = RequestMethod.POST)
    public R exportSchoolFieldTemplate(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                       HttpServletResponse response, JwtAuthenticationToken token) {
        UserContext user = (UserContext) token.getPrincipal();
        params.put("schId", user.getSchoolId());
        params.put("flag", 1);
        List<Map<String, Object>> dictLists = Lists.newArrayList();
//		Map<String,Object> isPoor = Maps.newHashMap();
//		isPoor.put("type", "8");
//		isPoor.put("name", "贫困类型");
//		dictLists.add(isPoor);
        //List<ColumnHeader> schoolFieldHeader = this.ermSchfundFieldService.querySchoolFieldHeader(params,dictLists);
        // Map<String, Object> data = ExcelUtil.getDownData(schoolFieldHeader);
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String fileName = "学生信息比对" + System.currentTimeMillis() + ".xls";
            String filePath = "/public/img/work/Tomcat/localhost/ROOT/" + fileName;
            List<String> head = new ArrayList<>();
            head.add("姓名");
            head.add("身份证");

            List<String> headName = new ArrayList<>();
            head.add("name");
            head.add("idCard");
            ExcelUtil.downLoadExcelForMap(request, response, fileName, new ArrayList<Map<String, Object>>(), new String[]{"name", "idCard"}, new String[]{"姓名", "身份证"}, new boolean[1], "学生信息比对", "学生信息比对");

            return R.ok().put("fileName", fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping(value = "/downloadItems")
    public Object downloadItems(Map<String, Object> params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        return ermStudentService.downloadItems(params, req, resp);
    }

    /**
     * 导入比对名单
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/imporCheckStudentInfo", method = RequestMethod.POST)
    public R imporCheckStudentInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                   HttpServletResponse response, @RequestParam(value = "file") MultipartFile file,
                                   JwtAuthenticationToken token) {
        if (file.isEmpty()) {
            return R.error("上传文件不存在");
        }
        String fileName = file.getOriginalFilename();
        String fileSuffixName = fileName.substring(fileName.lastIndexOf("."));
        if (!".xls".equals(fileSuffixName) && !".xlsx".equals(fileSuffixName)) {
            return R.error("文件格式不正确");
        }
        List<ErmStudentEntity> importInfo = null;
        try {
            importInfo = ermStudentService.importAuditStudentInfo(file.getInputStream());
            List<ErmDictEntity> dictList = this.ermDictService.queryList(null);
            List<ErmStudentExcelEntity> excelList = ExcelUtil.convertStudentEntity(importInfo);
            ExcelUtil.downStudentModelXLS(request, response, fileName, excelList, ExcelConstants.STUDENT_TITLE_NAME,
                    ExcelConstants.STUDENT_TITLE, ExcelConstants.STUDENT_SHEET_NAME, dictList);
        } catch (IOException e) {
            return R.error(e.getMessage());
        }
        return R.ok().put("resultData", importInfo);
    }
}
