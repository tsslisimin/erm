<template>
  <div class="wrapper">
    <Location>学生管理 > 学生信息修改</Location>
    <div class="con">
      <Card :padding="10">
        <Form ref="formStu" :model="formStu" :rules="ruleStu" :label-width="0" style="padding-top: 15px;margin-bottom: 10px;">
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">姓名：</p></Col>
              <Col span="5">
                <FormItem prop="name">
                  <Input v-model="formStu.name" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="3"><p class="form-label">身份证号：</p></Col>
              <Col span="5">
                <FormItem prop="idcard">
                  <Input v-model="formStu.idcard" placeholder="请输入"></Input>
                </FormItem>
              </Col>
              <Col span="3"><p class="form-label">照片：</p></Col>
              <Col span="5">
                <FormItem>
                  <input type="file" id="files" style="line-height: 20px;" accept="*" name="file" @change="getFile()">
                </FormItem>
              </Col>
            </Row>
          </FormItem>
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <!--<Col span="3"><p class="form-label">姓名拼音：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
              <!--<Input v-model="formStu.pinyin" placeholder="请输入"></Input>-->
              <!--</FormItem>-->
              <!--</Col>-->
              <Col span="3"><p class="form-label">学号：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.stuno" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">监护人姓名：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.parentName" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">监护人联系电话：</p></Col>
              <Col span="5">
              <FormItem prop="telphone">
                <Input v-model="formStu.telphone" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">家庭住址：</p></Col>
              <Col span="1">
              <FormItem prop="addressProvince">
                <Input v-model="formStu.addressProvince" placeholder="省"></Input>
              </FormItem>
              </Col>
              <Col span="1">
              <FormItem prop="addressCity">
                <Input v-model="formStu.addressCity" placeholder="市"></Input>
              </FormItem>
              </Col>
              <Col span="1">
              <FormItem prop="addressArea">
                <Input v-model="formStu.addressArea" placeholder="区"></Input>
              </FormItem>
              </Col>
              <Col span="1">
              <FormItem prop="addressTown">
                <Input v-model="formStu.addressTown" placeholder="乡镇"></Input>
              </FormItem>
              </Col>
              <Col span="1">
              <FormItem prop="addressTownship">
                <Input v-model="formStu.addressTownship" placeholder="村/居委会"></Input>
              </FormItem>
              </Col>
              <Col span="1">
              <FormItem prop="addressGroup">
                <Input v-model="formStu.addressGroup" placeholder="组"></Input>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">就读年级：</p></Col>
              <Col span="5">
              <FormItem prop="grade">
                <Select v-model="formStu.grade" v-if="schoolType == 1" placeholder="请选择">
                  <Option value="大班">大班</Option>
                  <Option value="中班">中班</Option>
                  <Option value="小班">小班</Option>
                </Select>
                <Select v-model="formStu.grade" v-if="schoolType == 2 || schoolType == 3" placeholder="请选择">
                  <Option value="一年级">一年级</Option>
                  <Option value="二年级">二年级</Option>
                  <Option value="三年级">三年级</Option>
                  <Option value="四年级">四年级</Option>
                  <Option value="五年级">五年级</Option>
                  <Option value="六年级">六年级</Option>
                  <Option value="七年级">七年级</Option>
                  <Option value="八年级">八年级</Option>
                  <Option value="九年级">九年级</Option>
                </Select>
                <Select v-model="formStu.grade" v-if="schoolType == 4" placeholder="请选择">
                  <Option value="高一">高一</Option>
                  <Option value="高二">高二</Option>
                  <Option value="高三">高三</Option>
                </Select>
                <Select v-model="formStu.grade" v-if="schoolType == 5" placeholder="请选择">
                  <Option value="一年级">一年级</Option>
                  <Option value="二年级">二年级</Option>
                  <Option value="三年级">三年级</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">班级名称：</p></Col>
              <Col span="4">
              <FormItem prop="clazz">
                <Input v-model="formStu.clazz" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>
          <!--<FormItem style="margin-bottom: 20px;">-->
          <!--<Row :gutter="10">-->
          <!--<Col span="3"><p class="form-label">招生方式：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.recruitType" placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.recruitType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--<Col span="3"><p class="form-label">学制：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.schSystem" filterable placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.schSystem" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!---->
          <!--</Row>-->
          <!--</FormItem>-->
          <!--<FormItem style="margin-bottom: 20px;">-->
          <!--<Row :gutter="10">-->
          <!--&lt;!&ndash;<Col span="3"><p class="form-label">学期：</p></Col>&ndash;&gt;-->
          <!--&lt;!&ndash;<Col span="5">&ndash;&gt;-->
          <!--&lt;!&ndash;<FormItem>&ndash;&gt;-->
          <!--&lt;!&ndash;<Input v-model="formStu.semester" placeholder="请输入"></Input>&ndash;&gt;-->
          <!--&lt;!&ndash;</FormItem>&ndash;&gt;-->
          <!--&lt;!&ndash;</Col>&ndash;&gt;-->
          <!--<Col span="3"><p class="form-label">学生类别：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.stuType" filterable placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.stuType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--<Col span="3"><p class="form-label">学习形式：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.studyType" filterable placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.studyType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--</Row>-->
          <!--</FormItem>-->
          <!--<FormItem style="margin-bottom: 20px;">-->
            <!--<Row :gutter="10">-->
              <!--<Col span="3"><p class="form-label">入学年份：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
              <!--<DatePicker v-model="formStu.startYear" type="year" placeholder="请选择" style="width: 100%;"></DatePicker>-->
              <!--</FormItem>-->
              <!--</Col>-->

              <!--<Col span="3"><p class="form-label">毕业学校：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
              <!--<Input v-model="formStu.schoolName" placeholder="请输入"></Input>-->
              <!--</FormItem>-->
              <!--</Col>-->
            <!--</Row>-->
          <!--</FormItem>-->
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">户口性质：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.accountType" placeholder="请选择">
                  <Option v-for="(item, $index) in formData.accountType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>

              <Col span="3"><p class="form-label">民族：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.nature" filterable placeholder="请选择">
                  <Option v-for="(item, $index) in formData.nature" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>


              <Col span="3"><p class="form-label">贫困类型：</p></Col>
              <Col span="5">
              <FormItem  >
                <Select v-model="formStu.isPoor" placeholder="请选择" >
                  <Option v-for="(item, $index) in formData.isPoorData" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>

            </Row>
          </FormItem>
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">是否寄宿：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.lodging" placeholder="请选择">
                  <Option v-for="(item, $index) in lodgings" :value="item.code" :key="$index">{{item.des}}</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">政治面貌：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.politicalStatus" placeholder="请选择">
                  <Option v-for="(item, $index) in formData.politicalStatus" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">银行卡卡号：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.actualBankcard" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <!--<Col span="3"><p class="form-label">学生来源：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
              <!--<Select v-model="formStu.studentFrom" placeholder="请选择">-->
              <!--<Option v-for="(item, $index) in formData.studentFrom" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
              <!--</Select>-->
              <!--</FormItem>-->
              <!--</Col>-->
              <!--<Col span="3"><p class="form-label">专业简称：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
              <!--<Input v-model="formStu.major" placeholder="请输入"></Input>-->
              <!--</FormItem>-->
              <!--</Col>-->
            </Row>
          </FormItem>
          <!--<FormItem style="margin-bottom: 20px;">-->
          <!--<Row :gutter="10">-->
          <!--<Col span="3"><p class="form-label">入学方式：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.learnType" placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.learnType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--<Col span="3"><p class="form-label">就读方式：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.attendingType" placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.attendingType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--<Col span="3"><p class="form-label">港澳台侨外：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.nation" placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.nation" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--</Row>-->
          <!--</FormItem>-->
          <!--<FormItem style="margin-bottom: 20px;">-->
            <!--<Row :gutter="10">-->
              <!--&lt;!&ndash;<Col span="3"><p class="form-label">婚姻状况：</p></Col>&ndash;&gt;-->
              <!--&lt;!&ndash;<Col span="5">&ndash;&gt;-->
              <!--&lt;!&ndash;<FormItem>&ndash;&gt;-->
              <!--&lt;!&ndash;<Select v-model="formStu.marriageStatus" placeholder="请选择">&ndash;&gt;-->
              <!--&lt;!&ndash;<Option v-for="(item, $index) in formData.marriageStatus" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>&ndash;&gt;-->
              <!--&lt;!&ndash;</Select>&ndash;&gt;-->
              <!--&lt;!&ndash;</FormItem>&ndash;&gt;-->
              <!--&lt;!&ndash;</Col>&ndash;&gt;-->
              <!--<Col span="3"><p class="form-label">籍贯地行政区划码：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
                <!--<Select v-model="formStu.divisionCode" placeholder="请选择">-->
                  <!--<Option v-for="(item, $index) in formData.divisionCode" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
                <!--</Select>-->
              <!--</FormItem>-->
              <!--</Col>-->
              <!--<Col span="3"><p class="form-label">出生地行政区划码：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
                <!--<Select v-model="formStu.birthDivisionCode" placeholder="请选择">-->
                  <!--<Option v-for="(item, $index) in formData.divisionCode" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
                <!--</Select>-->
              <!--</FormItem>-->
              <!--</Col>-->

              <!--<Col span="3"><p class="form-label">户口地行政区划码：</p></Col>-->
              <!--<Col span="5">-->
              <!--<FormItem>-->
                <!--<Select v-model="formStu.registeredDivisionCode" placeholder="请选择">-->
                  <!--<Option v-for="(item, $index) in formData.divisionCode" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
                <!--</Select>-->
              <!--</FormItem>-->
              <!--</Col>-->
            <!--</Row>-->
          <!--</FormItem>-->
          <!-- <FormItem style="margin-bottom: 20px;">
             <Row :gutter="10">
               <Col span="3"><p class="form-label">户口地行政区划码：</p></Col>
               <Col span="5">
                 <FormItem>
                   <Select v-model="formStu.registeredDivisionCode" placeholder="请选择">
                     <Option v-for="(item, $index) in formData.divisionCode" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                   </Select>
                 </FormItem>
               </Col>
               &lt;!&ndash;<Col span="3"><p class="form-label">户口地县以下地址：</p></Col>&ndash;&gt;
               &lt;!&ndash;<Col span="5">&ndash;&gt;
                 &lt;!&ndash;<FormItem>&ndash;&gt;
                   &lt;!&ndash;<Input v-model="formStu.birthPlace" placeholder="请输入"></Input>&ndash;&gt;
                 &lt;!&ndash;</FormItem>&ndash;&gt;
               &lt;!&ndash;</Col>&ndash;&gt;
               &lt;!&ndash;<Col span="3"><p class="form-label">所属派出所：</p></Col>&ndash;&gt;
               &lt;!&ndash;<Col span="5">&ndash;&gt;
                 &lt;!&ndash;<FormItem>&ndash;&gt;
                   &lt;!&ndash;<Input v-model="formStu.policeStation" placeholder="请输入"></Input>&ndash;&gt;
                 &lt;!&ndash;</FormItem>&ndash;&gt;
               &lt;!&ndash;</Col>&ndash;&gt;
             </Row>
           </FormItem>-->
          <!--<FormItem style="margin-bottom: 20px;">-->
          <!--<Row :gutter="10">-->
          <!--&lt;!&ndash;<Col span="3"><p class="form-label">乘火车区间：</p></Col>&ndash;&gt;-->
          <!--&lt;!&ndash;<Col span="5">&ndash;&gt;-->
          <!--&lt;!&ndash;<FormItem>&ndash;&gt;-->
          <!--&lt;!&ndash;<Input v-model="formStu.trainRegion" placeholder="请输入"></Input>&ndash;&gt;-->
          <!--&lt;!&ndash;</FormItem>&ndash;&gt;-->
          <!--&lt;!&ndash;</Col>&ndash;&gt;-->
          <!--<Col span="3"><p class="form-label">招生对象：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Select v-model="formStu.studentObj" placeholder="请选择">-->
          <!--<Option v-for="(item, $index) in formData.studentObj" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>-->
          <!--</Select>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--<Col span="3"><p class="form-label">教学点：</p></Col>-->
          <!--<Col span="5">-->
          <!--<FormItem>-->
          <!--<Input v-model="formStu.studyPlace" placeholder="请输入"></Input>-->
          <!--</FormItem>-->
          <!--</Col>-->
          <!--</Row>-->
          <!--</FormItem>-->
          <FormItem style="margin-bottom: 20px;">

            <Row :gutter="10">
              <Col span="3"><p class="form-label">健康状况：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.healthStatus" placeholder="请选择">
                  <Option v-for="(item, $index) in formData.healthStatus" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">是否随迁子女：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.isMove" placeholder="请选择">
                  <Option v-for="(item, $index) in formData.isTrue" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">跨省招生：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.transProvincial" placeholder="请选择">
                  <Option v-for="(item, $index) in formData.isTrue" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>
            </Row>
          </FormItem>
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">学生居住地类型：</p></Col>
              <Col span="5">
              <FormItem>
                <Select v-model="formStu.addressType" placeholder="请选择">
                  <Option v-for="(item, $index) in formData.addressType" :value="item.dictCode" :key="$index">{{item.dictName}}</Option>
                </Select>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">学生资助卡姓名：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.supportName" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">学生资助卡银行账号：</p></Col>
              <Col span="5">
              <FormItem >
                <Input v-model="formStu.supportBankCard" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>

          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">帮扶人姓名：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.helper" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">帮扶人单位：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.helperWorkPlace" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="3"><p class="form-label">帮扶人职位：</p></Col>
              <Col span="5">
              <FormItem >
                <Input v-model="formStu.helperPosition" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>
          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="3"><p class="form-label">帮扶人联系电话：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.helperTel" placeholder="请输入"></Input>
              </FormItem>
              </Col>

              <Col span="3"><p class="form-label">家庭人口数：</p></Col>
              <Col span="5">
              <FormItem>
                <Input v-model="formStu.familyNum" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>



          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="6"><p class="form-label">湖南省扶贫补贴明白折人姓名：</p></Col>
              <Col span="6">
              <FormItem>
                <Input v-model="formStu.archiveName" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="6"><p class="form-label">湖南省扶贫补贴明白折人身份证号：</p></Col>
              <Col span="6">
              <FormItem>
                <Input v-model="formStu.archiveIdcard" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>


          <FormItem style="margin-bottom: 20px;">
            <Row :gutter="10">
              <Col span="6"><p class="form-label">湖南省扶贫补贴明白折人账号：</p></Col>
              <Col span="6">
              <FormItem>
                <Input v-model="formStu.archiveAcount" placeholder="请输入"></Input>
              </FormItem>
              </Col>
              <Col span="6"><p class="form-label">湖南省扶贫补贴明白折人关系：</p></Col>
              <Col span="6">
              <FormItem>
                <Input v-model="formStu.archiveRelation" placeholder="请输入"></Input>
              </FormItem>
              </Col>
            </Row>
          </FormItem>
          <FormItem style="margin-top: 30px;">
            <Row :gutter="10">
              <Col span="24" style="text-align: center;"><Button type="primary" @click="submit('formStu')">保存信息</Button></Col>
            </Row>
          </FormItem>
        </Form>
      </Card>
    </div>
  </div>
</template>

<script>
import Location from 'js/module/components/location/location'
import io from 'js/module/api/io'
import { api } from 'js/module/api/url'
import $ from 'jquery'
import { domain } from 'js/module/api/domain'

export default {
  name: 'stuManageMingdan',
  data () {
    return {
      formStu: {
        stuId: '',
        name: '',
        idcard: '',
        photoUrl: '',
        pinyin: '',
        parentName: '',
        telphone: '',
        addressTown: '',
        addressTownship: '',
        addressGroup: '',
        addressProvince: '',
        addressCity: '',
        addressArea: '',
        grade: '',
        semester: '',
        schSystem: '',
        stuno: '',
        major: '',
        clazz: '',
        stuType: '',
        studyType: '',
        startYear: '',
        nature: '',
        schoolName: '',
        accountType: '',
        addressType: '',
        politicalStatus: '',
        healthStatus: '',
        studentFrom: '',
        learnType: '',
        attendingType: '',
        nation: '',
        marriageStatus: '',
        divisionCode: '',
        birthDivisionCode: '',
        registeredDivisionCode: '',
        birthPlace: '',
        policeStation: '',
        trainRegion: '',
        studentObj: '',
        studyPlace: '',
        isMove: '',
        transProvincial: '',
        recruitType: '',
        lodging:'',
        actualBankcard:'',
        isPoor:'',
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
        archiveName: '',
        archiveIdcard: '',
        familyNum:''
      },
      lodgings:[
        {code:'否',des:'否'},{code:'否',des:'是'}
      ],
      ruleStu: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        idcard: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { type: 'string', min: 18, message: '身份证号长度不能小于18位', trigger: 'blur' }
        ],
        telphone: [
          { required: true, message: '请输入监护人联系电话', trigger: 'blur' }
        ],
        grade: [{ required: true, message: '请选择就读年级', trigger: 'change' }],
        clazz: [{ required: true, message: '请输入班级名称', trigger: 'blur' }],
        addressTown: [{ required: true, message: '乡镇', trigger: 'blur' }],
        addressTownship: [{ required: true, message: '村/居委会', trigger: 'blur' }],
        addressGroup: [{ required: true, message: '组', trigger: 'blur' }],
        addressProvince: [{ required: true, message: '省', trigger: 'blur' }],
        addressCity: [{ required: true, message: '市', trigger: 'blur' }],
        addressArea: [{ required: true, message: '县', trigger: 'blur' }],
        accountType:[{ required: true, message: '户口性质', trigger: 'blur' }],
        nature:[{ required: true, message: '民族', trigger: 'blur' }],
        lodging: [{ required: true, message: '是否寄宿', trigger: 'blur' }],
        isPoor: [{ required: true, message: ' ', trigger: 'change' }],
      },
      formData: {
        schSystem: [],
        stuType: [],
        studyType: [],
        nature: [],
        accountType: [],
        addressType: [],
        politicalStatus: [],
        healthStatus: [],
        studentFrom: [],
        learnType: [],
        attendingType: [],
        nation: [],
        marriageStatus: [],
        divisionCode: [],
        studentObj: [],
        isTrue: [],
        recruitType: [],
        isPoorData:[]
      },
      fileData: null,
      formDatas: null,
      modalFile: false,
      schoolType: '',
      schoolId: ''
    }
  },
  components: { Location },
  methods: {
    // 提交信息
    submit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let date = new Date(this.formStu.startYear)

          io.post(api.student.edit, {
            schoolId: this.schoolId,
            id: this.formStu.stuId,
            name: this.formStu.name,
            idcard: this.formStu.idcard,
            photoUrl: this.formStu.photoUrl,
            pinyin: this.formStu.pinyin,
            parentName: this.formStu.parentName,
            telphone: this.formStu.telphone,
            addressProvince: this.formStu.addressProvince,
            addressCity: this.formStu.addressCity,
            addressArea: this.formStu.addressArea,
            addressTown: this.formStu.addressTown,
            addressTownship: this.formStu.addressTownship,
            addressGroup: this.formStu.addressGroup,
            grade: this.formStu.grade,
            semester: this.formStu.semester,
            schSystem: this.formStu.schSystem,
            stuno: this.formStu.stuno,
            major: this.formStu.major,
            clazz: this.formStu.clazz,
            stuType: this.formStu.stuType,
            studyType: this.formStu.studyType,
            startYear: date ? date.getFullYear() : '',
            nature: this.formStu.nature,
            schoolName: this.formStu.schoolName,
            accountType: this.formStu.accountType,
            addressType: this.formStu.addressType,
            politicalStatus: this.formStu.politicalStatus,
            healthStatus: this.formStu.healthStatus,
            studentFrom: this.formStu.studentFrom,
            learnType: this.formStu.learnType,
            attendingType: this.formStu.attendingType,
            nation: this.formStu.nation,
            marriageStatus: this.formStu.marriageStatus,
            divisionCode: this.formStu.divisionCode,
            birthDivisionCode: this.formStu.birthDivisionCode,
            registeredDivisionCode: this.formStu.registeredDivisionCode,
            birthPlace: this.formStu.birthPlace,
            policeStation: this.formStu.policeStation,
            trainRegion: this.formStu.trainRegion,
            studentObj: this.formStu.studentObj,
            studyPlace: this.formStu.studyPlace,
            isMove: this.formStu.isMove,
            transProvincial: this.formStu.transProvincial,
            recruitType: this.formStu.recruitType,
            lodging:this.formStu.lodging,
            actualBankcard:this.formStu.actualBankcard,
            isPoor:this.formStu.isPoor,
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
            helperTel:this.formStu.helperTel,
            archiveName: this.formStu.archiveName,
            archiveIdcard: this.formStu.archiveIdcard,
            familyNum: this.formStu.familyNum
          }, (res) => {
            this.$Message.success('学生信息保存成功！')
          })
        } else {
          this.$Message.error('表单验证失败！')
        }
      })
    },
    // 获取下拉数据
    getSubList () {
      io.get(api.dict.list, (res) => {
        let data = res.data.dict

        this.formData.schSystem = data['14']
        this.formData.stuType = data['15']
        this.formData.studyType = data['20']
        this.formData.nature = data['2']
        this.formData.accountType = data['5']
        this.formData.addressType = data['6']
        this.formData.politicalStatus = data['4']
        this.formData.healthStatus = data['3']
        this.formData.studentFrom = data['7']
        this.formData.learnType = data['11']
        this.formData.attendingType = data['12']
        this.formData.nation = data['10']
        this.formData.marriageStatus = data['16']
        this.formData.divisionCode = data['9']
        this.formData.studentObj = data['17']
        this.formData.isTrue = data['19']
        this.formData.recruitType = data['13']
        this.formData.isPoorData = data['8']

      })
    },
    // 上传文件
    getFile () {
      let file = document.getElementById('files')

      this.fileData = new FormData()
      this.fileData.append('file', file.files[0])

      io.post(api.file.upload, this.fileData, (res) => {
        this.formStu.photoUrl = res.data
      }, (e) => {
        this.$Message.error('文件上传出错！')
      }, (e) => {
        this.$Message.error('文件上传出错！')
      })
    },
    // 获取数据
    getInfo () {
      io.get(api.student.editInfo + '/' + this.formStu.stuId, (res) => {
        let data = res.data
        for (let k in data.ermStudent) {
          if (this.formStu.hasOwnProperty(k)) {
            this.formStu[k] = data.ermStudent[k]
          }
        }
        this.schoolId = data.ermStudent.schoolId
      })
    },
    // 获取学校信息
    getSchool () {
      io.get(api.school.info, (res) => {
        let data = res.data.ermSchool

        this.schoolType = data.type
      })
    }
  },
  mounted () {
    this.formStu.stuId = this.$route.params.stuId || ''
    this.getSchool()
    this.getInfo()
    this.getSubList()
  }
}
</script>

<style lang="scss" scoped>
@import "../../../../module/components/main";

.wrapper {
  margin: 0 10px 20px 10px;
  .con {
    .photo {
      margin-top: 20px;
    }
    .page {
      margin-top: 10px;
    }
    .do-some {
      margin-bottom: 10px;
    }
    .form-label {
      color: $form-label-color;
      text-align: right;
      font-size: 12px;
    }
  }
}

.data-in {
  h3 {
    font-weight: normal;
    font-size: 16px;
    text-align: center;
    color: $color-warning;
  }
  p {
    margin-top: 20px;
    font-size: 14px;
    color: $font-primary-color;
  }
}
</style>
