package com.coomia.erm.controller;


import com.coomia.erm.common.auth.JwtAuthenticationToken;
import com.coomia.erm.common.auth.model.UserContext;
import com.coomia.erm.entity.ErmAdminEntity;
import com.coomia.erm.entity.ErmDictEntity;
import com.coomia.erm.entity.ErmSchoolEntity;
import com.coomia.erm.entity.UserType;
import com.coomia.erm.service.*;
import com.coomia.erm.util.PageUtils;
import com.coomia.erm.util.Query;
import com.coomia.erm.util.R;
import com.coomia.erm.util.ReportMergeXls;
import com.sun.javafx.collections.MappingChange;
import com.sun.org.apache.bcel.internal.generic.NEW;
import jdk.nashorn.internal.ir.IfNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/erm/api/total")
public class ErmTotalController {

  @Autowired
  private ErmStudentService ermStudentService;
  @Autowired
  private ErmDictService dictService;
  @Autowired
  private UserService userService;
  @Autowired
  private ErmSchoolService ermSchoolService;
    /**
     *
     * 导出Excel报表
     * @param request
     * @return
     *
     */


    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, JwtAuthenticationToken token) {


        UserContext user = (UserContext) token.getPrincipal();
        ErmAdminEntity admin = userService.getByUser(user.getUsername());
        int roleId = admin.getRoleId();
        if (roleId == UserType.SCH.getCode() || roleId == UserType.OPER.getCode()) {
           params.put("schoolId",user.getSchoolId()) ;
        }
        SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");

        Query query = new Query(params);
//            // 构造导出数据
        List<Map<String, Object>> dataList = ermStudentService.queryStudentsByFundIdAndSchoolIdAndPage(query);

        Integer total=ermStudentService.queryStudentsByFundIdAndSchoolIdTotal(params);
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).put("No",i+1);
            Map<String,Object> model = dataList.get(i);
            if (model.get("gender")!=null){
            if (model.get("gender").toString().equals("1")){
                dataList.get(i).put("gender","男");
            }else {
                dataList.get(i).put("gender","女");
            }}
            else {
                dataList.get(i).put("gender","");
            }
            if (model.get("is_poor")!=null) {
                String is_poor = dictService.getDictNameByCode(model.get("is_poor").toString());
                dataList.get(i).put("is_poor",is_poor);
            }
            }

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtil);

    }
    @RequestMapping("/export")
    public void export(HttpServletRequest request,
                       HttpServletResponse response, Integer fundId,Integer schoolId,JwtAuthenticationToken token) {
        try {

            UserContext user = (UserContext) token.getPrincipal();
            ErmAdminEntity admin = userService.getByUser(user.getUsername());
            int roleId = admin.getRoleId();
            if(roleId == UserType.SCH.getCode()||roleId == UserType.OPER.getCode()){
                schoolId=user.getSchoolId();
            }
            SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
            Map<String, Object> params = new HashMap<String, Object>();

//            // 构造导出数据
            List<Map<String, Object>> dataList = ermStudentService.queryStudentsByFundIdAndSchoolId(fundId,schoolId);

            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).put("No",i+1);
                Map<String,Object> model = dataList.get(i);
                if (model.get("gender")!=null){
                if (model.get("gender").toString().equals("1")){
                    dataList.get(i).put("gender","男");
                }else {
                    dataList.get(i).put("gender","女");
                }}
                else {
                    dataList.get(i).put("gender","");
                }
                if (model.get("is_poor")!=null) {
                    String is_poor = dictService.getDictNameByCode(model.get("is_poor").toString());
                    switch (is_poor) {
                        case "建档立卡":
                            dataList.get(i).put("level1","1");
                            break;
                        case "农村低保":
                            dataList.get(i).put("level2","1");
                            break;
                        case "残疾":
                            dataList.get(i).put("level3","1");
                            break;
                        case "农村特困供养":
                            dataList.get(i).put("level4","1");
                            break;
                        case "其他":
                            dataList.get(i).put("level5","1");
                        case "不困难":
                            dataList.get(i).put("level6","1");
                            break;
                    }
                }
                if (model.get("zone")!=null) {

                    switch (model.get("zone").toString()) {
                        case "学前":
                            dataList.get(i).put("zone1","1");
                            break;
                        case "小学":
                            dataList.get(i).put("zone2","1");
                            break;
                        case "初中":
                            dataList.get(i).put("zone3","1");
                            break;
                        case "普高":
                            dataList.get(i).put("zone4","1");
                            break;
                        case "中职":
                            dataList.get(i).put("zone5","1");
                       break;
                    }
                }


            }
//            String sheetName = "温湿度日记录";
//            String date = yearMonth;
            String[] head0 = new String[] { "序号", "学生姓名", "性别", "年龄", "身份证号","家长姓名","电话号码","家庭人口数（人）",
                    "乡镇", "学校名称", "学籍号", "就读阶段（在相应类型下画“1”）", "就读阶段（在相应类型下画“1”）","就读阶段（在相应类型下画“1”）","就读阶段（在相应类型下画“1”）","就读阶段（在相应类型下画“1”）","年级","班次","是否寄宿",
                    "省", "市", "县", "乡镇", "村（居委会）","组（号）",
                    "建档立卡", "农村低保", "残疾学生","农村特困供养", "其他困难", "不困难",
                    "姓 名（账户名）", "账号", "身份证号", "与学生关系",
                    "姓 名（账户名）", "银行账号",
                    "姓名","单位","职务","电话号码"
            };//在excel中的第3行每列的参数
            String[] head1 = new String[] { "学前", "小学", "初中", "普高" , "中职"};//在excel中的第4行每列（合并列）的参数
            String[] headnum0 = new String[] { "4,5,0,0", "4,5,1,1", "4,5,2,2","4,5,3,3","4,5,4,4","4,5,5,5","4,5,6,6","4,5,7,7",
                    "4,5,8,8", "4,5,9,9", "4,5,10,10","4,4,11,15","4,5,16,16","4,5,17,17","4,5,18,18"
                    ,"4,5,19,19","4,5,20,20","4,5,21,21","4,5,22,22","4,5,23,23","4,5,24,24",
                    "4,5,25,25","4,5,26,26","4,5,27,27","4,5,28,28","4,5,29,29",
                    "4,5,30,30","4,5,31,31","4,5,32,32","4,5,33,33","4,5,34,34",
                    "4,5,35,35","4,5,36,36",
                    "4,5,37,37","4,5,38,38","4,5,39,39","4,5,40,40","4,5,41,41","4,5,42,42"
            };//对应excel中的行和列，下表从0开始{"开始行,结束行,开始列,结束列"}
            String[] headnum1 = new String[] { "5,5,11,11", "5,5,12,12", "5,5,13,13",
                    "5,5,14,14", "5,5,15,15" };
            String[] colName = new String[] { "No", "name", "gender","age","idcard","parent_name","telphone","familyNum",
                    "study_place","school","stuno","zone1","zone2","zone3","zone4","zone5","grade","clazz","lodging",
                    "addressProvince","addressCity","addressArea","addressTown","addressTownship","addressGroup",
                    "level1","level2","level3","level4","level5","level6",
                    "archive_name","archiveAcount","archive_idcard","archiveRelation",
                    "supportName","supportBankCard",
                    "helper","helperWorkPlace","helperPosition","helperTel"
            };//需要显示在excel中的参数对应的值，因为是用map存的，放的都是对应的key

            String schoolname="";
            if (schoolId!=null){
            ErmSchoolEntity ermSchoolEntity = ermSchoolService.queryObject(schoolId);
            if (ermSchoolEntity!=null){
                schoolname=ermSchoolEntity.getName();
            }

            }

            ReportMergeXls.reportMergeXls(request, response, dataList, "慈利县2018年春季学期本乡镇（或本校）内就读学生信息台账", head0,
                    headnum0, head1, headnum1, colName, schoolname); //utils类需要用到的参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
