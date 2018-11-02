const perfix = ''
const perApi = '/erm/api/'
const fileserver = 'http://iooicloud.com:8080'
const pageUrl = {
  home: perfix + '/view/home/index.html'
}

export {pageUrl}

const api = {
  sign: {
    'login': perApi + 'auth/login',
    'user': perApi + 'me',
    'loginOut': perApi + 'auth/logout'
  },
  school: {
    'save': perApi + 'ermschool/save',
    'list': perApi + 'ermschool/list',
    'delete': perApi + 'ermschool/delete',
    'edit': perApi + 'ermschool/update',
    'import': perApi + 'ermschool/importSchoolInfo',
    'export': perApi + 'ermschool/exportSchoolInfo',
    'listByFundedId': perApi + 'ermschool/listByFundedId',
    'querySchField': perApi + 'ermschool/querySchField',
    'listClazz': perApi + 'ermschool/listClazz',
    'querySchGradeClazzList': perApi + 'ermschool/querySchGradeClazzList',
    'info': perApi + 'ermschool/schInfo'
  },
  admin: {
    'list': perApi + 'ermadmin/list',
    'saveAdmins': perApi + 'ermadmin/saveAdmins',
    'delete': perApi + 'ermadmin/delete',
    'edit': perApi + 'ermadmin/update',
    'create': perApi + 'ermadmin/exportCreateSchoolAdmin',
    'export': perApi + 'ermadmin/exportAdminInfo',
    'code': perApi + 'ermadmin/updatePassWord'
  },
  funded: {
    'saveShcFund': perApi + 'ermfunded/saveShcFund',
    'save': perApi + 'ermfunded/save',
    'list': perApi + 'ermfunded/list',
    'edit': perApi + 'ermfunded/update',
    'delete': perApi + 'ermfunded/delete',
    'schoolFundedlist': perApi + 'ermfunded/schoolFundedlist',
    'info': perApi + 'ermfunded/info'
  },
  schoolFunded: {
    'save': perApi + 'ermschoolfunded/save',
    'list': perApi + 'ermschoolfunded/list',
    'edit': perApi + 'ermschoolfunded/update',
    'delete': perApi + 'ermschoolfunded/delete',
    'template': perApi + 'ermschoolfunded/exportSchoolFundedTemplate',
    'import': perApi + 'ermschoolfunded/importSchoolFundedInfo',
    'fundList': perApi + 'ermschoolfunded/fundList',
    'fundStatus': perApi + 'ermschoolfunded/fundStatus',
    'batchsave': perApi + 'ermschoolfunded/batchUpdateFunded',
    'listSimple': perApi + 'ermschoolfunded/fundListSimple',
    'saveNew': perApi + 'ermschoolfunded/saveSchFundNew',
    'updatePublicImg': perApi + 'ermschoolfunded/updatePublicImg'
  },
  notice: {
    'save': perApi + 'ermfunednotice/save',
    'list': perApi + 'ermfunednotice/list',
    'edit': perApi + 'ermfunednotice/update',
    'delete': perApi + 'ermfunednotice/delete',
    'myNotice': perApi + 'ermfunednotice/myNotice',
    'saveExt': perApi + 'ermfunednotice/saveExt'
  },
  student: {
    'save': perApi + 'ermstudent/save',
    'list': perApi + 'ermstudent/list',
    'edit': perApi + 'ermstudent/update',
    'delete': perApi + 'ermstudent/delete',
    'import': perApi + 'ermstudent/importStudentInfo',
    'export': perApi + 'ermstudent/exportStudentInfo',
    'checkTemp': perApi + 'ermstudent/checkTemp',
    'importAudit': perApi + 'ermstudent/importAuditStudentInfo',
    'saveStudent': perApi + 'ermstudent/saveStudent',
    'info': perApi + 'ermstudent/info',
    'editInfo': perApi + 'ermstudent/update/info',
    'importPic': perApi + 'ermstudent/importStudentPicInfo',
    'importApplyPics': perApi + 'ermstudent/importApplyPics',
    'stuApplyList': perApi + 'ermstudent/stuApplyList',
    'bankCard': perApi + 'ermstudent/importStudentBankCardInfo',
    'compareIdcard': perApi + 'ermstudent/compareIdcard',
    'saveDiffStudent': perApi + 'ermstudent/saveDiffStudent',
    'deleteDiffStudent': perApi + 'ermstudent/deleteDiffStudent',
    'diffStuInfo': perApi + 'ermstudent/diffStuInfo',
    'stuInfo': perApi + 'ermstudent/stuInfo',
    'studentsByParam': perApi + 'ermstudent/listStudentsByParam',
    'queryStudentsBySchoolId': perApi + 'ermstudent/queryStudentsBySchoolId',
    'queryStudentsByStuId': perApi + 'ermstudent/queryStudentsByStuId',
    'importcheck': perApi + 'ermstudent/imporCheckStudentInfo',
    'downloadItems': perApi + 'ermstudent/downloadItems',
    'exportStudentList': perApi + 'ermstudent/exportStudentList',
    'excelImport': fileserver + '/excel',
  },
  ledger: {
    'export': perApi + 'total/export',
    'list': perApi + 'total/list'
  },
  fundedinfo: {
    'save': perApi + 'ermfundedinfo/save',
    'list': perApi + 'ermfundedinfo/list',
    'edit': perApi + 'ermfundedinfo/update',
    'delete': perApi + 'ermfundedinfo/delete',
    'batchSave': perApi + 'ermfundedinfo/batchSave',
    'studentFund': perApi + 'ermfundedinfo/studentFundinfo'
  },
  family: {
    'save': perApi + 'ermfamily/save',
    'list': perApi + 'ermfamily/list',
    'edit': perApi + 'ermfamily/update',
    'delete': perApi + 'ermfamily/delete'
  },
  fundProcess: {
    'start': perApi + 'process/fund/start',
    'list': perApi + 'process/fund/students/list',
    'listMy': perApi + 'process/fund/students/list/my',
    'verifyOne': perApi + 'process/fund/operSingleSubmit',
    'verifyMore': perApi + 'process/fund/operBatchSubmit',
    'schverifyOne': perApi + 'process/fund/schSingleSubmit',
    'schverifyMore': perApi + 'process/fund/schBatchSubmit',
    'ebverifyOne': perApi + 'process/fund/ebSingleAudit',
    'ebverifyMore': perApi + 'process/fund/ebBatchSubmit',
    'status': perApi + 'process/fund/status',
    'auditByFund': perApi + 'process/fund/auditByFund',
    'fundList': perApi + 'process/fund/list',
    'money': perApi + 'process/fund/updateFundMoney',
    'stuMoney': perApi + 'process/fund/updateStudentMoney',
    'fundStart': perApi + 'process/fund/schFundStart',
    'countryStart': perApi + 'process/fund/startCountryFund',
    'audit': perApi + 'process/fund/audit',
    'adjust': perApi + 'process/fund/adjustDiffLevel',
    'audited': perApi + 'process/fund/students/audited',
    'remainCount': perApi + 'process/fund/remainCount',
    'queryApply': perApi + 'process/fund/students/queryApply',
    'deleteApply': perApi + 'process/fund/students/deleteApply',
    'exportStudentApply': perApi + 'process/fund/students/exportStudentApply'
  },
  schfundfield: {
    'batchSave': perApi + 'ermschfundfield/batchSave',
    'batchUpdate': perApi + 'ermschfundfield/batchUpdate',
    'list': perApi + 'ermschfundfield/list',
    'template': perApi + 'ermschfundfield/schoolFieldTemplate'
  },
  field: {
    'save': perApi + 'ermfield/save',
    'list': perApi + 'ermfield/list',
    'edit': perApi + 'ermfield/update',
    'delete': perApi + 'ermfield/delete'
  },
  dict: {
    'list': perApi + 'ermdict/stuDict',
    'schTypeDict': perApi + 'ermdict/schTypeDict',
    'semester': perApi + 'ermdict/semesterDict',
    'fundDict': perApi + 'ermdict/getFundDictData',
    'getFundDictDataByType': perApi + 'ermdict/getFundDictDataByType'
  },
  file: {
    'upload': perApi + 'ermFile/uploadFile',
    'download': perApi + 'ermFile/exportFile',
    'uploadFileWithoutLogin': perApi + 'ermFile/uploadFileWithoutLogin'
  },
  msg: {
    'list': perApi + 'ermfundedinfo/fundedInfoList'
  },
  total: {
    'export': perApi + 'total/export',
  },
  report: {
    'total': perApi + 'ermreport/totalReport',
    'student': perApi + 'ermreport/fundedStudentReport',
    'school': perApi + 'ermreport/fundedSchoolReport',
    'type': perApi + 'ermreport/fundedTypeReport',
    'export': perApi + 'ermreport/exportTotalReport',
    'overview': perApi + 'ermreport/reportOverview',
    'view': perApi + 'ermreport/generalView',
    'queryReport': perApi + 'ermreport/queryReportOverViewList',
    'years': perApi + 'ermreport/getReportYears',
    'column': perApi + 'ermreport/getExportColumn',
    'studentInfo': perApi + 'ermreport/fundedStudentInfo',
    'studentIndex': perApi + 'ermreport/fundedStudentById',
    'exportKunnan': perApi + 'ermreport/exportFundedStudentInfo',
    'stuImg': perApi + 'ermreport/fundedStudentImgById',
    'exportPublicStuInfo': perApi + 'ermreport/exportPublicStuInfo'
  },
  h5: {
    'save': perApi + 'ermH5StudentInfo/save',
    'list': perApi + 'ermH5StudentInfo/list',
    'saveApply': perApi + 'ermH5StudentInfo/saveApply',
    'confirmList': perApi + 'ermH5StudentInfo/queryByClazz',
    'getStudent': perApi + 'ermH5StudentInfo/getStuInfoByStuId',
    'confirm': perApi + 'ermH5StudentInfo/headTeachConfirm',
    'querySchField': perApi + 'ermH5StudentInfo/querySchField'
  },
  schoolKpi: {
    'save': perApi + 'schoolKpi/save',
    'list': perApi + 'schoolKpi/list',
    'delete': perApi + 'schoolKpi/delete',
    'active': perApi + 'schoolKpi/active',
    'edit': perApi + 'schoolKpi/update'
  },
  logs: {
    'list': perApi + 'logs/list',
    'delete': perApi + 'logs/delete'

  }
}
const baseUrl = "http://home.xiaoxuezha.com"

export {api, baseUrl}
