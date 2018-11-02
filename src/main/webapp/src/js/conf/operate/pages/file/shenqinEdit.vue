<template>
  <div class="apply">
    <Card :padding="10" :shadow="false" :dis-hover="false" :bordered="false">
      <h3>湖南省 补助申请表</h3>
      <Form ref="formStu" :model="formStu" :label-width="150" :rules="ruleStu" style="">
        <FormItem label="年级：" prop="grade">
          <Select v-model="formStu.grade" placeholder="请选择" style="width: 70%">
            <Option v-for="(item, index) in gradeData" :value="item" :key="index">{{item}}</Option>
          </Select>
        </FormItem>
        <FormItem label="班级：" prop="clazz">
          <!--<Select v-model="formStu.clazz" placeholder="请选择" style="width: 70%">-->
            <!--<Option v-for="(item, index) in clazzData" :value="item" :key="index">{{item}}</Option>-->
          <!--</Select>-->
          <Input v-model="formStu.clazz" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="学生姓名：" prop="name">
          <!--<Select v-model="formStu.name" placeholder="请选择" style="width: 70%">-->
            <!--<Option v-for="(item, index) in nameData" :value="item" :key="index">{{item}}</Option>-->
          <!--</Select>-->
          <Input v-model="formStu.name" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="身份证号：" prop="idcard">
          <Input v-model="formStu.idcard" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="性别：">

          <label><input type="radio" v-model="formStu.gender" value="1" >男</label>
          <label><input type="radio" v-model="formStu.gender" value="2" >女</label>
          <!--<RadioGroup v-model="formStu.gender">-->
            <!--<Radio label="1">男</Radio>-->
            <!--<Radio label="2">女</Radio>-->
          <!--</RadioGroup>-->
        </FormItem>
        <FormItem label="出生年月：">
          <DatePicker v-model="formStu.birthDay" type="month" placeholder="请选择" style="width: 70%"></DatePicker>
        </FormItem>
        <FormItem label="民族：">
          <Input v-model="formStu.nation" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="学籍号：">
          <Input v-model="formStu.stuno" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="家庭住址：">
          <Input v-model="formStu.address" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="是否建档立卡户：">
          <label><input type="radio" v-model="formStu.isArchives" value="0" >否</label>
          <label><input type="radio" v-model="formStu.isArchives" value="1" >是</label>
          <!--<RadioGroup v-model="formStu.isArchives">-->
            <!--<Radio label="0">否</Radio>-->
            <!--<Radio label="1">是</Radio>-->
          <!--</RadioGroup>-->
        </FormItem>
        <FormItem label="湖南省扶贫补贴明白折（建档立卡）人姓名：" v-if="formStu.isArchives == 1">
          <Input v-model="formStu.archiveName" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="湖南省扶贫补贴明白折（建档立卡）人身份证号：" v-if="formStu.isArchives == 1">
          <Input v-model="formStu.archiveIdcard" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="湖南省扶贫补贴明白折（建档立卡）人账号：" v-if="formStu.isArchives == 1">
          <Input v-model="formStu.archiveAcount" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="湖南省扶贫补贴明白折（建档立卡）人关系：" v-if="formStu.isArchives == 1">
          <Input v-model="formStu.archiveRelation" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="学生资助卡姓名：" v-if="formStu.isArchives == 0">
          <Input v-model="formStu.supportName" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="学生资助卡银行账号：" v-if="formStu.isArchives == 0">
          <Input v-model="formStu.supportBankCard" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <!-- <FormItem label="上传人员证明：">
          <RadioGroup>
            <Radio label="0">爷爷奶奶</Radio>
            <Radio label="1">爸爸妈妈</Radio>
            <Radio label="2">兄弟姊妹</Radio>
          </RadioGroup>
        </FormItem> -->

        <FormItem label="家长/监护人姓名：">
          <Input v-model="formStu.father" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="关系：">
          <Input v-model="formStu.fatherAblity" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="年龄：">
          <Input v-model="formStu.fatherAge" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="工作单位及职业：">
          <Input v-model="formStu.fatherJob" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>

        <FormItem label="家长/监护人姓名：">
          <Input v-model="formStu.mother" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="关系：">
          <Input v-model="formStu.motherAblity" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="年龄：">
          <Input v-model="formStu.motherAge" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="工作单位及职业：">
          <Input v-model="formStu.motherJob" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>

        <FormItem label="家庭经济情况：">
          <Input v-model="formStu.familyIncome" type="textarea" :autosize="{minRows: 2,maxRows: 10}"
                 placeholder="家庭人口、收入及来源等" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="申报资助理由：">
          <Input v-model="formStu.applyReason" type="textarea" :autosize="{minRows: 2,maxRows: 10}" placeholder="请输入"
                 style="width: 70%"></Input>
        </FormItem>
        <FormItem label="帮扶人姓名：">
          <Input v-model="formStu.helper" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="帮扶人单位：">
          <Input v-model="formStu.helperWorkPlace" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="帮扶人职位：">
          <Input v-model="formStu.helperPosition" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label="帮扶人联系电话：">
          <Input v-model="formStu.helperTel" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <FormItem label=" ">
          <Checkbox v-model="isRead"></Checkbox>
          <a href="javascript:;" @click="rules()">已认证阅读并同意承诺条款</a>
        </FormItem>

        <FormItem label="上传证明材料照片">
          <input type="file" id="files" style="line-height: 20px;" accept="*" name="file" @change="getFile()">
          <p class="img-url"><img v-for="item in formStu.evidenceUrls" :src="'/erm/api/ermFile/img/' + item" alt=""></p>
        </FormItem>

        <p style="margin-bottom: 20px;">注：建档立卡户包括：建档立卡户、农村低保户、农村特困救助供养户或残疾人</p>

        <FormItem>
          <Button type="primary" @click="submit('formStu')">提交信息</Button>
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script>
  import {uniq} from 'lodash'
  import io from 'js/module/api/io'
  import {api} from 'js/module/api/url'
  import {URL} from 'js/lib/util/getUrlParam'

  export default {
    name: 'shenqinEdit',
    data() {
      return {
        formStu: {
          name: '',
          gender: '',
          birthDay: '',
          nation: '',
          stuno: '',
          grade: '',
          clazz: '',
          idcard: '',
          address: '',
          isArchives: '',
          archiveName: '',
          archiveIdcard: '',
          father: '',
          fatherAblity: '',
          fatherAge: '',
          fatherJob: '',
          mother: '',
          motherAblity: '',
          motherAge: '',
          motherJob: '',
          familyIncome: '',
          applyReason: '',
          evidenceUrls: [],
          //'湖南省扶贫补贴明白折（建档立卡）人关系'
          archiveRelation: '',
          //'湖南省扶贫补贴明白折（建档立卡）人账号'
          archiveAcount: '',
          //'学生资助卡姓名'
          supportName: '',

          //'学生资助卡银行账号'
          supportBankCard: '',
          //'帮扶人姓名'
          helper: '',
          // '帮扶人单位'
          helperWorkPlace: '',
          //'帮扶人职位'
          helperPosition: '',
          //'帮扶人联系电话'
          helperTel: '',
        },
        ruleStu: {
          name: [{required: true, message: '请选择姓名', trigger: 'change'}],
          idcard: [
            {required: true, message: '请输入身份证号', trigger: 'blur'},
            {type: 'string', min: 18, message: '身份证号长度不能小于18位', trigger: 'blur'}
          ]
        },
        isRead: false,
        schId: '',
        stuId:'',
        gradeData: [],
        clazzData: [],
        nameData: []
      }
    },
    components: {},
    methods: {
      // 提交信息
      submit(name) {
        this.$refs[name].validate(valid => {
          if (valid) {
            let date = new Date(this.formStu.birthDay)

            let _data = {
              name: this.formStu.name,
              gender: this.formStu.gender,
              birthDay: this.formStu.birthDay ? (date.getFullYear() + '-' + (date.getMonth() + 1)) : '',
              nation: this.formStu.nation,
              stuno: this.formStu.stuno,
              grade: this.formStu.grade,
              clazz: this.formStu.clazz,
              idcard: this.formStu.idcard,
              address: this.formStu.address,
              isArchives: this.formStu.isArchives,
              archiveName: this.formStu.archiveName,
              archiveIdcard: this.formStu.archiveIdcard,
              father: this.formStu.father,
              fatherAblity: this.formStu.fatherAblity,
              fatherAge: this.formStu.fatherAge,
              fatherJob: this.formStu.fatherJob,
              mother: this.formStu.mother,
              motherAblity: this.formStu.motherAblity,
              motherAge: this.formStu.motherAge,
              motherJob: this.formStu.motherJob,
              familyIncome: this.formStu.familyIncome,
              applyReason: this.formStu.applyReason,
              evidenceUrls: this.formStu.evidenceUrls.join(),
              //'湖南省扶贫补贴明白折（建档立卡）人关系'
              archiveRelation: this.formStu.archiveRelation,
              //'湖南省扶贫补贴明白折（建档立卡）人账号'
              archiveAcount: this.formStu.archiveAcount,
              //'学生资助卡姓名'
              supportName: this.formStu.supportName,

              //'学生资助卡银行账号'
              supportBankCard: this.formStu.supportBankCard,
              //'帮扶人姓名'
              helper: this.formStu.helper,
              // '帮扶人单位'
              helperWorkPlace: this.formStu.helperWorkPlace,
              //'帮扶人职位'
              helperPosition: this.formStu.helperPosition,
              //'帮扶人联系电话'
              helperTel: this.formStu.helperTel,
            }

            if (!this.isRead) {
              this.$Message.error('请先承诺条款！')
              return
            }

            if (this.formStu.isArchives == 1) {
              if (!this.formStu.archiveName) {
                this.$Message.error('请输入建档立卡人姓名！')
                return
              }
              if (!this.formStu.archiveIdcard) {
                this.$Message.error('请输入建档立卡人姓名身份证号！')
                return
              }
            }

            io.post(api.h5.saveApply, _data, (res) => {
              this.$Message.success('申请表提交成功！')
            }, (e) => {
              this.$Message.error('提交失败！')
            }, (e) => {
              this.$Message.error('提交失败！')
            })
          } else {
            this.$Message.error('表单验证失败！')
          }
        })
      },
      // 条款
      rules() {
        this.$Modal.info({
          title: '承诺条款',
          content: '<p>1：为了落实国家资助政策，不影响您的孩子及时得到国家资助，请您按照您家庭实际情况如实填写您的家庭经济信息，您所填写的信息仅用于学校和教育主管部门在学生资助工作中参考，会高度保密，请您放心！</p><p>2：同时，我们也提醒您，在填写本信息过程中务必实事求是，确保信息准确，如因信息不实造成有关后果将由您和您的家庭自行承担。</p><p>3：如您的家庭经济情况确实较好，不属于经济困难家庭，也可以不填写此信息。</p>'
        })
      },
      // 上传文件
      getFile() {
        let file = document.getElementById('files')

        this.fileData = new FormData()
        this.fileData.append('file', file.files[0])

        io.post(api.file.uploadFileWithoutLogin, this.fileData, (res) => {
          this.fileData = null
          this.formStu.evidenceUrls.push(res.data)
          this.$Message.success('该图片上传成功！')
        }, (e) => {
          this.$Message.error('文件上传出错！')
        }, (e) => {
          this.$Message.error('文件上传出错！')
        })
      },
      // 全部年级
      getGrade() {
        io.get(api.student.studentsByParam, {
          schoolId: this.schId
        }, (res) => {
          let data = res.data

          let _grade = []

          data.grades.forEach(item => {
            if (item.grade) {
              _grade.push(item.grade)
            }
          })

          this.gradeData = uniq(_grade)
        })
      },
      // 获取班级
      getClazz() {
        this.nameData = []
        this.formStu.name = ''
        this.clazzData = []
        this.formStu.clazz = ''
        this.formStu.idcard = ''

        io.get(api.student.studentsByParam, {
          schoolId: this.schId,
          grade: this.formStu.grade
        }, (res) => {
          let data = res.data

          let _clazz = []

          data.grades.forEach(item => {
            if (item.clazz) {
              _clazz.push(item.clazz)
            }
          })

          this.clazzData = uniq(_clazz)
        })
      },
      // 获取名字
      getName() {
        this.nameData = []
        this.formStu.name = ''
        this.formStu.idcard = ''

        io.get(api.student.studentsByParam, {
          schoolId: this.schId,
          grade: this.formStu.grade,
          clazz: this.formStu.clazz
        }, (res) => {
          let data = res.data

          let _name = []

          data.grades.forEach(item => {
            if (item.name) {
              _name.push(item.name)
            }
          })

          this.nameData = uniq(_name)
        })
      },
      // 获取身份证
      getCard() {
        io.get(api.student.queryStudentsByStuId, {
          stuId:this.stuId,
          schoolId: this.schId,
          grade: this.formStu.grade,
          clazz: this.formStu.clazz,
          name: this.formStu.name
        }, (res) => {
          let data = res.data
            this.formStu.name= data.grades[0]['name'];
            this.formStu.grade=data.grades[0]['grade'];
            this.formStu.clazz= data.grades[0]['clazz'];
          this.formStu.idcard = data.grades[0]['idcard'];
          this.formStu.stuno = data.grades[0]['stuno'];
          this.formStu.gender = data.grades[0]['gender'];
          this.formStu.address = data.grades[0]['address'];
          this.formStu.birthDay = data.grades[0]['birth'];
          this.formStu.nation = data.grades[0]['nation'];

          this.formStu.birthDay = data.grades[0]['birth'];
          //'湖南省扶贫补贴明白折（建档立卡）人关系'
          this.formStu.  archiveRelation= data.grades[0]['archiveRelation'];
            //'湖南省扶贫补贴明白折（建档立卡）人账号'
            this.formStu. archiveAcount= data.grades[0]['archiveAcount'];
            //'学生资助卡姓名'
            this.formStu. supportName= data.grades[0]['supportName'];

            //'学生资助卡银行账号'
            this.formStu.supportBankCard= data.grades[0]['supportBankCard'];
            //'帮扶人姓名'
            this.formStu. helper= data.grades[0]['helper'];
            // '帮扶人单位'
            this.formStu.  helperWorkPlace= data.grades[0]['helperWorkPlace'];
            //'帮扶人职位'
            this.formStu. helperPosition= data.grades[0]['helperPosition'];
            //'帮扶人联系电话'
            this.formStu.  helperTel= data.grades[0]['helperTel'];
          this.formStu. isArchives= data.grades[0]['isArchives'];
            this.formStu. archiveName= data.grades[0]['archiveName'];
            this.formStu. archiveIdcard= data.grades[0]['archiveIdcard'];




            this.formStu.  father= data.family.father,
            this.formStu.  fatherAblity= data.family.fatherAblity,
            this.formStu.  fatherAge= data.family.fatherAge,
            this.formStu.  fatherJob= data.family.fatherJob,
            this.formStu.   mother= data.family.mother,
            this.formStu.  motherAblity= data.family.motherAblity,
            this.formStu.  motherAge= data.family.motherAge,
            this.formStu.  motherJob= data.family.motherJob,
            this.formStu.    familyIncome= data.family.familyIncome,
            this.formStu.   applyReason= data.family.applyReason,
            this.formStu. evidenceUrls= data.family.evidenceUrls.join(),

          console.log(this.formStu)
        })
      }
    },
    watch: {
//      'formStu.grade'(n) {
//        if (n) {
//          this.getClazz()
//        }
//      },
      // 'formStu.clazz'(n) {
      //   if (n) {
      //     this.getName()
      //   }
      // },
      // 'formStu.name'(n) {
      //   if (n) {
      //     this.getCard()
      //   }
      // }
    },
    mounted() {
      this.stuId = this.$route.params.stuId
      this.schId = this.$route.params.schId
      this.getGrade()
      this.getCard()
    }
  }
</script>

<style lang="scss" scoped>
  .apply {
    max-width: 750px;
    margin: 0 auto;
    h3 {
      font-size: 24px;
      font-weight: normal;
      width: 260px;
      margin: 0 auto;
      color: #0098E1;
      text-align: center;
      margin-top: 30px;
      margin-bottom: 30px;
    }
    .img-url {
      img {
        width: 50px;
        height: 100px;
        border: 1px solid #d5d5d5;
        margin-right: 5px;
      }
    }
  }
</style>
