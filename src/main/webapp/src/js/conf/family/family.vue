<template>
  <div class="family">
    <Card :padding="10" :shadow="false" :dis-hover="false" :bordered="false">
      <h3>家庭经济情况调查表</h3>
      <Form ref="formStu" :model="formStu" :label-width="150" :rules="ruleStu" style="">
        <FormItem label="年级：" prop="grade">
          <Select v-model="formStu.grade" placeholder="请选择" style="width: 70%">
            <Option v-for="(item, index) in gradeData" :value="item" :key="index">{{item}}</Option>
          </Select>
        </FormItem>
        <FormItem label="班级：" prop="clazz">
          <Select v-model="formStu.clazz" placeholder="请选择" style="width: 70%">
            <Option v-for="(item, index) in clazzData" :value="item" :key="index">{{item}}</Option>
          </Select>
        </FormItem>
        <FormItem label="学生姓名：" prop="name">
          <Select v-model="formStu.name" placeholder="请选择" style="width: 70%">
            <Option v-for="(item, index) in nameData" :value="item" :key="index">{{item}}</Option>
          </Select>
        </FormItem>
        <FormItem label="身份证号：" prop="idcard">
          <Input v-model="formStu.idcard" placeholder="请输入" style="width: 70%"></Input>
        </FormItem>
        <!--<FormItem label="贫困类型：" prop="isPoor">-->
          <!--<Select v-model="formStu.isPoor" placeholder="请选择" style="width: 70%">-->
            <!--<Option v-for="(item, $index) in isPoorData" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
        <!--</FormItem>-->
        <FormItem v-for="(item, $index) in formDatas" :key="$index" :label="item.ermFieldEntity.fieldName + '：'">
          <Input v-if="item.ermFieldEntity.type == 1" v-model="item.ermFieldEntity.value" placeholder="请输入" style="width: 70%;"></Input>
          <Input v-if="item.ermFieldEntity.type == 2" v-model="item.ermFieldEntity.value" placeholder="请输入" style="width: 70%;"></Input>
          <Select v-if="item.ermFieldEntity.type == 3" filterable v-model="item.ermFieldEntity.value" placeholder="请选择" style="width: 70%;">
            <Option v-for="el in item.ermFieldEntity.fieldVal" :key="el.id" :value="el.valKey">{{el.valVal}}</Option>
          </Select>
          <Select v-if="item.ermFieldEntity.type == 4" multiple filterable v-model="item.ermFieldEntity.value" placeholder="请选择" style="width: 70%;">
            <Option v-for="el in item.ermFieldEntity.fieldVal" :key="el.id" :value="el.valKey">{{el.valVal}}</Option>
          </Select>
        </FormItem>
        <FormItem>
          <Button type="primary" @click="submit('formStu')">提交信息</Button>
        </FormItem>
      </Form>
    </Card>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import { URL } from 'js/lib/util/getUrlParam'
import { forEach, uniq } from 'lodash'

export default {
  name: 'family',
  data () {
    return {
      formStu: {
        name: '',
        idcard: '',
        grade: '',
        clazz: '',
        isPoor: ''
      },
      ruleStu: {
        name: [{ required: true, message: '请选择学生姓名', trigger: 'change' }],
        idcard: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { type: 'string', min: 18, message: '身份证号不能少于18位', trigger: 'blur' }
        ]
      },
      formDatas: [],
      schId: '',
      gradeData: [],
      clazzData: [],
      nameData: [],
      isPoorData: []
    }
  },
  components: {},
  methods: {
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let _data = {
            schoolId: this.schId,
            idcard: this.formStu.idcard,
            name: this.formStu.name,
            grade: this.formStu.grade,
            clazz: this.formStu.clazz,
            stuValues: []
          }

          forEach(this.formDatas, (item) => {
            if (item.ermFieldEntity.value) {
              _data.stuValues.push({
                fieldId: item.fieldId,
                fieldValVal: item.ermFieldEntity.type == 1 || item.ermFieldEntity.type == 2 ? item.ermFieldEntity.value : '',
                fieldValKey: item.ermFieldEntity.type == 3 ? item.ermFieldEntity.value : '',
                fieldValKeyList: item.ermFieldEntity.type == 4 ? item.ermFieldEntity.value : []
              })
            }
          })

          io.post(api.h5.save, _data, (res) => {
            this.$Message.success('信息提交成功！')
          }, (res) => {
            this.$Message.info(res.data.msg)
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取项目的动态表单
    getForm () {
      io.get(api.h5.querySchField, {
        schId: this.schId
      }, (res) => {
        let data = res.data.page
        this.formDatas = data
      })
    },
    // 全部年级
    getGrade () {
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
    getClazz () {
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
    getName () {
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
    getCard () {
      io.get(api.student.studentsByParam, {
        schoolId: this.schId,
        grade: this.formStu.grade,
        clazz: this.formStu.clazz,
        name: this.formStu.name
      }, (res) => {
        let data = res.data

        this.formStu.idcard = data.grades[0]['idcard']
      })
    },
    // 获取下拉数据
    getSubList () {
      io.get(api.dict.list, (res) => {
        let data = res.data.dict

        this.isPoorData = data['8']
      })
    }
  },
  watch: {
    'formStu.grade' (n) {
      if (n) {
        this.getClazz()
      }
    },
    'formStu.clazz' (n) {
      if (n) {
        this.getName()
      }
    },
    'formStu.name' (n) {
      if (n) {
        this.getCard()
      }
    }
  },
  mounted () {
    this.schId = URL.getVal('schId')
    this.getGrade()
    this.getForm()
    this.getSubList()
  }
}
</script>

<style lang="scss" scoped>
.family {
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
}
</style>
