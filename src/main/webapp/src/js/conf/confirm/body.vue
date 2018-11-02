<template>
  <div class="confirm">
    <Card :padding="10" :shadow="false" :dis-hover="false" :bordered="false">
      <h3>班主任认定</h3>
      <p class="p-1"><span>{{grade+clazz}}</span>班级评议小组认定</p>
      <p class="p-2">还有<span>{{studentAry.length}}</span>个学生待认定</p>
      <p class="p-3"><span>班主任签名：</span><span style="display: inline;"><Input v-model="teachName" placeholder="请输入" style="width: 50%;"></Input></span></p>
      <p class="p-4">
        <Button type="primary" @click="prev()">上一条</Button>&nbsp;&nbsp;
        <Button type="primary" @click="next()">下一条</Button>&nbsp;&nbsp;&nbsp;&nbsp;
        <Button type="warning" @click="goConfirm()">认 定</Button>
      </p>
      <div class="info-con">
        <h4 @click="toggle(0)"><i class="iconfont" v-if="showIdx === 0">&#xe64a;</i><i class="iconfont" v-else>&#xe643;</i>学生基本信息</h4>
        <div class="info-detail" v-if="showIdx === 0">
          <div class="clearfix">
            <p class="f-fl">姓名：<span>{{baseInfo.name}}</span></p>
            <p class="f-fl">学号：<span>{{baseInfo.stuno}}</span></p>
            <p class="f-fl">身份证号：<span>{{baseInfo.idcard}}</span></p>
            <p class="f-fl">年级：<span>{{baseInfo.grade}}</span></p>
            <p class="f-fl">班级：<span>{{baseInfo.clazz}}</span></p>
            <p class="f-fl">学期：<span>{{baseInfo.semester}}</span></p>
            <p class="f-fl">民族：<span>{{baseInfo.nation}}</span></p>
            <p class="f-fl">政治面貌：<span>{{baseInfo.politicalStatus}}</span></p>
            <p class="f-fl">毕业学校：<span>{{baseInfo.schoolName}}</span></p>
            <p class="f-fl">监护人姓名：<span>{{baseInfo.parentName}}</span></p>
            <p class="f-fl">监护人电话：<span>{{baseInfo.telphone}}</span></p>
            <p class="f-fl">家庭住址：<span>{{baseInfo.address}}</span></p>
          </div>
        </div>
      </div>
      <div class="info-con">
        <h4 @click="toggle(1)"><i class="iconfont" v-if="showIdx === 1">&#xe64a;</i><i class="iconfont" v-else>&#xe643;</i>学生困难信息</h4>
        <div class="info-detail" v-if="showIdx === 1">
          <div class="clearfix">
            <p class="f-fl" v-for="(item, $index) in fundInfo" :key="$index"><span>{{item.key}}：</span><span>{{item.value}}</span></p>
          </div>
        </div>
      </div>
      <div class="info-con">
        <h4 @click="toggle(2)"><i class="iconfont" v-if="showIdx === 2">&#xe64a;</i><i class="iconfont" v-else>&#xe643;</i>申请表信息</h4>
        <div class="info-detail" v-if="showIdx === 2">
          <div class="clearfix">
            <p class="f-fl">学生姓名：<span>{{baseInfo.name}}</span></p>
            <p class="f-fl">性别：<span>{{baseInfo.gender == 1 ? '男' : '女'}}</span></p>
            <p class="f-fl">出生年月日：<span>{{baseInfo.birth}}</span></p>
            <p class="f-fl">民族：<span>{{baseInfo.nature}}</span></p>
            <p class="f-fl">学籍号：<span>{{baseInfo.stuno}}</span></p>
            <p class="f-fl">年级：<span>{{baseInfo.grade}}</span></p>
            <p class="f-fl">班级：<span>{{baseInfo.clazz}}</span></p>
            <p class="f-fl">身份证号：<span>{{baseInfo.idcard}}</span></p>
            <p class="f-fl">家庭住址：<span>{{baseInfo.address}}</span></p>
            <p class="f-fl">建档立卡人：<span>{{applyInfo.archiveName}}</span></p>
            <p class="f-fl">建档立卡人身份证：<span>{{applyInfo.archiveIdcard}}</span></p>
            <p class="f-fl">监护人1姓名：<span>{{applyInfo.father}}</span></p>
            <p class="f-fl">与学生关系：<span>{{applyInfo.fatherAblity}}</span></p>
            <p class="f-fl">监护人1年龄：<span>{{applyInfo.fatherAge}}</span></p>
            <p class="f-fl">监护人1职业：<span>{{applyInfo.fatherJob}}</span></p>
            <p class="f-fl">监护人2姓名：<span>{{applyInfo.mother}}</span></p>
            <p class="f-fl">与学生关系：<span>{{applyInfo.motherAblity}}</span></p>
            <p class="f-fl">监护人2年龄：<span>{{applyInfo.motherAge}}</span></p>
            <p class="f-fl">监护人2职业：<span>{{applyInfo.motherJob}}</span></p>
            <p class="f-fl">家庭经济情况：<span>{{applyInfo.familyIncome}}</span></p>
            <p class="f-fl">申报资助理由：<span>{{applyInfo.applyReason}}</span></p>
          </div>
        </div>
      </div>
      <div class="info-con">
        <h4 @click="toggle(3)"><i class="iconfont" v-if="showIdx === 3">&#xe64a;</i><i class="iconfont" v-else>&#xe643;</i>证明材料</h4>
        <div class="info-detail" v-if="showIdx === 3">
          <p v-for="item in imgUrls" :key="item"><img :src="'/erm/api/ermFile/img/' + item" alt=""></p>
        </div>
      </div>
    </Card>
  </div>
</template>

<script>
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import { URL } from 'js/lib/util/getUrlParam'

export default {
  name: 'body',
  data () {
    return {
      teachName: '',
      grade: '',
      clazz: '',
      showIdx: -1,
      curIdx: 0,
      curId: '',
      studentAry: [],
      baseInfo: {},
      applyInfo: {},
      fundInfo: [],
      imgUrls: []
    }
  },
  methods: {
    // 展开和缩起
    toggle (n) {
      if (this.showIdx == n) {
        this.showIdx = -1
      } else {
        this.showIdx = n
      }
    },
    // 获取信息列表
    getList () {
      io.get(api.h5.confirmList, {
        grade: this.grade,
        clazz: this.clazz
      }, (res) => {
        let data = res.data

        this.studentAry = data.ermStudents
        this.curId = this.studentAry[this.curIdx]['id']
        this.getStudent(this.curId)
      })
    },
    // 获取指定的学生信息
    getStudent (id) {
      io.get(api.h5.getStudent, {
        stuId: id
      }, (res) => {
        let data = res.data

        this.baseInfo = data.ermStudentApplyInfo.baseInfo
        this.applyInfo = data.ermStudentApplyInfo.applyInfo
        this.fundInfo = data.ermStudentApplyInfo.fundInfo
        this.imgUrls = data.ermStudentApplyInfo.imgUrls.split(',')
      })
    },
    // 上一条
    prev () {
      if (this.curIdx >= 1 && this.studentAry.length) {
        this.curIdx--
        this.curId = this.studentAry[this.curIdx]['id']
        this.getStudent(this.curId)
      }
    },
    // 下一条
    next () {
      if (this.curIdx < this.studentAry.length - 1 && this.studentAry.length) {
        this.curIdx++
        this.curId = this.studentAry[this.curIdx]['id']
        this.getStudent(this.curId)
      }
    },
    // 认定
    goConfirm () {
      if (!this.teachName) {
        return
      }

      io.post(api.h5.confirm, {
        stuId: this.curId,
        headTeachName: this.teachName,
        isOk: 1
      }, (res) => {
        let data = res.data

        if (data.headTeachConfirmResult.result) {
          this.curIdx = 0
          this.getList()
          this.$Message.success(data.headTeachConfirmResult.msg)
        } else {
          this.$Message.error(data.headTeachConfirmResult.msg)
        }
      })
    }
  },
  mounted () {
    this.grade = URL.getVal('grade')
    this.clazz = URL.getVal('clazz')

    this.getList()
  }
}
</script>

<style lang="scss" scoped>
.confirm {
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
  .p-1 {
    margin-left: 50px;
    span {
      margin-right: 5px;
      font-weight: bold;
    }
  }
  .p-2 {
    margin-left: 50px;
    margin-top: 10px;
    span {
      margin-left: 3px;
      margin-right: 3px;
      font-weight: bold;
      color: #f68f70;
    }
  }
  .p-3 {
    margin-left: 50px;
    margin-top: 10px;
  }
  .p-4 {
    margin-left: 50px;
    margin-top: 10px;
    margin-bottom: 20px;
  }
  .info-con {
    border-bottom: 1px solid #eee;
    h4 {
      color: #0098E1;
      font-size: 15px;
      height: 30px;
      line-height: 30px;
      cursor: pointer;
      i {
        margin-right: 3px;
        font-size: 13px;
      }
    }
    .info-detail {
      .clearfix {
        .f-fl {
          width: 50%;
          height: 30px;
          line-height: 30px;
          overflow: hidden;
          text-overflow:ellipsis;
          white-space: nowrap;
        }
      }
    }
  }
}
</style>
