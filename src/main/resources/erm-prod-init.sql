/*
Navicat MySQL Data Transfer

Source Server         : 120.78.92.169
Source Server Version : 50638
Source Host           : 120.78.92.169:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2017-12-22 15:39:20
*/

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `tb_erm_admin`;

CREATE TABLE `tb_erm_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `telphone` varchar(20) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `disabled` tinyint(4) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '管理员类型',
  `email` varchar(50) DEFAULT NULL,
  `eb_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `用户名已存在` (`username`) USING BTREE,
  KEY `FK_Reference_21` (`school_id`) USING BTREE,
  KEY `FK_Reference_9` (`role_id`) USING BTREE,
  CONSTRAINT `tb_erm_admin_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `tb_erm_school` (`id`),
  CONSTRAINT `tb_erm_admin_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `tb_erm_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='管理员信息';

/*Table structure for table `tb_erm_admin_notice` */

DROP TABLE IF EXISTS `tb_erm_admin_notice`;

CREATE TABLE `tb_erm_admin_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `notice_id` int(11) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`admin_id`) USING BTREE,
  KEY `FK_Reference_11` (`notice_id`) USING BTREE,
  CONSTRAINT `tb_erm_admin_notice_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `tb_erm_admin` (`id`),
  CONSTRAINT `tb_erm_admin_notice_ibfk_2` FOREIGN KEY (`notice_id`) REFERENCES `tb_erm_funed_notice` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='用户消息订阅表';

/*Table structure for table `tb_erm_audit_log` */

DROP TABLE IF EXISTS `tb_erm_audit_log`;

CREATE TABLE `tb_erm_audit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(11) DEFAULT NULL COMMENT '审批状态',
  `level` varchar(10) DEFAULT NULL COMMENT '困难等级',
  `admin_id` int(11) DEFAULT NULL,
  `audit_date` date DEFAULT NULL COMMENT '审批时间',
  `audit_remark` varchar(200) DEFAULT NULL COMMENT '审批备注',
  `funded_id` int(11) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_12` (`funded_id`) USING BTREE,
  KEY `FK_Reference_7` (`admin_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8 COMMENT='学生资助审批日志表';

/*Table structure for table `tb_erm_dict` */

DROP TABLE IF EXISTS `tb_erm_dict`;

CREATE TABLE `tb_erm_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT NULL,
  `dict_code` varchar(20) DEFAULT NULL,
  `dict_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_erm_edubur` */

DROP TABLE IF EXISTS `tb_erm_edubur`;

CREATE TABLE `tb_erm_edubur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(30) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `prov` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL,
  `distincts` varchar(32) DEFAULT NULL COMMENT '区/县',
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='教育局信息';

insert  into `tb_erm_edubur`(`id`,`code`,`name`,`prov`,`city`,`distincts`,`creator`,`create_time`,`updator`,`update_time`) values (1,'4310000','慈利县教育局','湖南省','张家界市',NULL,NULL,NULL,NULL,NULL);
/*Table structure for table `tb_erm_family` */

DROP TABLE IF EXISTS `tb_erm_family`;

CREATE TABLE `tb_erm_family` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系人电话',
  `level` varchar(20) DEFAULT NULL COMMENT '家庭困难等级',
  `headman` varchar(30) DEFAULT NULL COMMENT '户主',
  `describ` varchar(200) DEFAULT NULL COMMENT '家庭描述',
  `father` varchar(20) DEFAULT NULL,
  `mother` varchar(20) DEFAULT NULL,
  `father_job` varchar(30) DEFAULT NULL,
  `mother_job` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `father_ablity` varchar(50) DEFAULT NULL,
  `mother_ablity` varchar(50) DEFAULT NULL,
  `other_ablity` varchar(50) DEFAULT NULL COMMENT '家庭其它人劳动能力',
  `house_info` varchar(50) DEFAULT NULL,
  `medicine_outcome` varchar(50) DEFAULT NULL,
  `disaster_info` varchar(50) DEFAULT NULL,
  `policy_care` varchar(50) DEFAULT NULL COMMENT '政策性优抚',
  `students_count` int(11) DEFAULT NULL COMMENT '就学人口',
  `family_change` varchar(50) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `father_age` int(11) DEFAULT NULL,
  `mother_age` int(11) DEFAULT NULL,
  `family_income` varchar(255) DEFAULT NULL COMMENT '家庭经济情况',
  `apply_reason` varchar(255) DEFAULT NULL COMMENT '申报理由',
  `evidence_urls` varchar(255) DEFAULT NULL COMMENT '证明材料,多个材料间逗号隔开',
  `is_archives` int(4) DEFAULT NULL COMMENT '是否建档',
  `archive_name` varchar(200) DEFAULT NULL COMMENT '建档立卡人姓名',
  `archive_idcard` varchar(200) DEFAULT NULL COMMENT '建档立卡人身份证',
  `archive_uplode` int(4) DEFAULT NULL COMMENT '上传人员证明 0:爷爷奶奶，1：爸爸妈妈，2：兄弟姐妹',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='家庭信息';

/*Table structure for table `tb_erm_field` */

DROP TABLE IF EXISTS `tb_erm_field`;

CREATE TABLE `tb_erm_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_code` varchar(30) NOT NULL,
  `field_name` varchar(30) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1文本 2 数字 3 选择器',
  `weight` double DEFAULT '50' COMMENT '字段权重',
  `flag` int(11) DEFAULT '0' COMMENT '类型：-1不显示 0：公共的，且显示的，2，学校自定义的',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_erm_field_val` */

DROP TABLE IF EXISTS `tb_erm_field_val`;

CREATE TABLE `tb_erm_field_val` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11) DEFAULT NULL COMMENT '字段ID关联tb_erm_field',
  `val_key` varchar(200) DEFAULT NULL COMMENT '选项code',
  `val_val` varchar(200) DEFAULT NULL COMMENT '选项值',
  `val_weight` double DEFAULT '0' COMMENT '权重',
  `flag` int(11) DEFAULT '0' COMMENT '-1为删除，0为公共显示的，其它为学校ID，表示学校创建的属性',
  PRIMARY KEY (`id`),
  KEY `FK_tb_erm_field_val` (`field_id`) USING BTREE,
  CONSTRAINT `tb_erm_field_val_ibfk_1` FOREIGN KEY (`field_id`) REFERENCES `tb_erm_field` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_erm_funded` */

DROP TABLE IF EXISTS `tb_erm_funded`;

CREATE TABLE `tb_erm_funded` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) DEFAULT NULL COMMENT '资助类型',
  `subtype` varchar(30) DEFAULT NULL COMMENT '资助子类型',
  `name` varchar(200) DEFAULT NULL,
  `eb_id` int(11) DEFAULT NULL,
  `describ` varchar(2000) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  `semester` varchar(200) DEFAULT NULL COMMENT '学期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_16` (`eb_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='资助信息';

/*Table structure for table `tb_erm_funded_info` */

DROP TABLE IF EXISTS `tb_erm_funded_info`;

CREATE TABLE `tb_erm_funded_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) DEFAULT NULL,
  `funded_id` int(11) DEFAULT NULL,
  `global_status` int(11) DEFAULT NULL COMMENT '资助当前状态',
  `money` double DEFAULT NULL COMMENT '资助金额',
  `funded_time` date DEFAULT NULL COMMENT '发放时间',
  `score` double DEFAULT NULL COMMENT '评分',
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `suggestion` varchar(500) DEFAULT NULL COMMENT '校长意见',
  `applypicurl` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_13` (`funded_id`) USING BTREE,
  KEY `FK_Reference_14` (`stu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='学生资助信息表';

/*Table structure for table `tb_erm_funed_notice` */

DROP TABLE IF EXISTS `tb_erm_funed_notice`;

CREATE TABLE `tb_erm_funed_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL COMMENT '通知URL',
  `describ` varchar(3000) DEFAULT NULL,
  `schools` varchar(300) DEFAULT NULL COMMENT '学校id串',
  `type` char(1) DEFAULT NULL COMMENT '通知类型（自建或审核流程通知）',
  `creator` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='通知信息';

/*Table structure for table `tb_erm_report` */

DROP TABLE IF EXISTS `tb_erm_report`;

CREATE TABLE `tb_erm_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_name` varchar(50) NOT NULL,
  `report_desc` varchar(2000) DEFAULT NULL,
  `sch_id` int(11) DEFAULT NULL,
  `report_type` int(11) DEFAULT NULL,
  `report_year` int(11) DEFAULT NULL COMMENT '报表年份',
  `creator_id` int(11) DEFAULT NULL,
  `fund_id` int(11) DEFAULT NULL,
  `create_date` date DEFAULT NULL,
  `creator` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='报表信息表';

/*Table structure for table `tb_erm_report_sub` */

DROP TABLE IF EXISTS `tb_erm_report_sub`;

CREATE TABLE `tb_erm_report_sub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `report_id` int(11) DEFAULT NULL,
  `sub_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tb_erm_report_sub_user` (`sub_user`) USING BTREE,
  KEY `FK_tb_erm_report_sub_report` (`report_id`) USING BTREE,
  CONSTRAINT `tb_erm_report_sub_ibfk_1` FOREIGN KEY (`report_id`) REFERENCES `tb_erm_report` (`id`),
  CONSTRAINT `tb_erm_report_sub_ibfk_2` FOREIGN KEY (`sub_user`) REFERENCES `tb_erm_admin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表订阅';

/*Table structure for table `tb_erm_role` */

DROP TABLE IF EXISTS `tb_erm_role`;

CREATE TABLE `tb_erm_role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息';

/*Table structure for table `tb_erm_sch_field` */

DROP TABLE IF EXISTS `tb_erm_sch_field`;

CREATE TABLE `tb_erm_sch_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sch_id` int(11) NOT NULL,
  `field_id` int(11) NOT NULL,
  `weight` double DEFAULT '10' COMMENT '该字段权重',
  `flag` tinyint(4) DEFAULT '1' COMMENT '是否显示，默认为1：显示；为0时，不显示，做逻辑删除时用。',
  PRIMARY KEY (`id`),
  KEY `FK_tb_erm_stu_field` (`field_id`),
  KEY `FK_tb_erm_schfund_field` (`sch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_erm_school` */

DROP TABLE IF EXISTS `tb_erm_school`;

CREATE TABLE `tb_erm_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eb_id` int(11) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `seal` varchar(200) DEFAULT NULL COMMENT '学校印章地址',
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `school_seal` varchar(255) DEFAULT NULL COMMENT '学校公章',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`code`) USING BTREE,
  KEY `FK_Reference_15` (`eb_id`) USING BTREE,
  CONSTRAINT `tb_erm_school_ibfk_1` FOREIGN KEY (`eb_id`) REFERENCES `tb_erm_edubur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8 COMMENT='学校信息';

/*Table structure for table `tb_erm_school_funded` */

DROP TABLE IF EXISTS `tb_erm_school_funded`;

CREATE TABLE `tb_erm_school_funded` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `funded_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `schzone` varchar(50) DEFAULT NULL COMMENT '学区(小学/初中）code,值从dict表取',
  `level` varchar(30) DEFAULT NULL COMMENT '资助级别，一级/二级/三级',
  `count` int(11) DEFAULT '0' COMMENT '资助名额个数',
  `form_url` varchar(500) DEFAULT NULL,
  `total_money` double DEFAULT '0',
  `year` int(11) DEFAULT NULL COMMENT '项目|资助年份',
  `semester` varchar(50) DEFAULT NULL COMMENT '学期',
  `status` int(11) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL COMMENT '备注',
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `public_url` varchar(500) DEFAULT NULL,
  `public_date` date DEFAULT NULL COMMENT '公示日期',
  `public_status` int(4) DEFAULT '0' COMMENT '公示状态,0：不可提交审批，1:可提交 ',
  `public_img` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`funded_id`) USING BTREE,
  KEY `FK_Reference_2` (`school_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='项目资助';

/*Table structure for table `tb_erm_stu_value` */

DROP TABLE IF EXISTS `tb_erm_stu_value`;

CREATE TABLE `tb_erm_stu_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  `field_val_val` varchar(300) DEFAULT NULL,
  `field_val_key` varchar(300) DEFAULT NULL,
  `flag` int(11) DEFAULT '1' COMMENT '标识位，表示这个学生的指标是否显示，默认是显示',
  PRIMARY KEY (`id`),
  KEY `FK_tb_erm_stu_value` (`stu_id`) USING BTREE,
  KEY `FK_tb_erm_field_value` (`field_id`) USING BTREE,
  CONSTRAINT `tb_erm_stu_value_ibfk_1` FOREIGN KEY (`field_id`) REFERENCES `tb_erm_field` (`id`),
  CONSTRAINT `tb_erm_stu_value_ibfk_3` FOREIGN KEY (`stu_id`) REFERENCES `tb_erm_student` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

/*Table structure for table `tb_erm_student` */

DROP TABLE IF EXISTS `tb_erm_student`;

CREATE TABLE `tb_erm_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `family_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `birth` varchar(20) DEFAULT NULL,
  `stuno` varchar(50) DEFAULT NULL COMMENT '学号',
  `card_type` varchar(20) DEFAULT NULL COMMENT '身份证件类型',
  `idcard` varchar(30) DEFAULT NULL COMMENT '身份证件号',
  `major` varchar(50) DEFAULT NULL,
  `sch_system` varchar(20) DEFAULT NULL COMMENT '学制',
  `pinyin` varchar(30) DEFAULT NULL,
  `engname` varchar(20) DEFAULT NULL,
  `start_year` varchar(30) DEFAULT NULL COMMENT '入学年份',
  `age` int(11) DEFAULT NULL,
  `zone` varchar(30) DEFAULT NULL COMMENT '校区ID',
  `grade` varchar(20) DEFAULT NULL COMMENT '年级',
  `study_type` varchar(50) DEFAULT NULL COMMENT '学习形式',
  `stu_type` varchar(20) DEFAULT NULL,
  `clazz` varchar(20) DEFAULT NULL COMMENT '班级',
  `telphone` varchar(30) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `division_code` varchar(50) DEFAULT NULL,
  `residence_type` varchar(20) DEFAULT NULL,
  `nature` varchar(20) DEFAULT NULL,
  `birth_place` varchar(30) DEFAULT NULL,
  `city_code` varchar(30) DEFAULT NULL,
  `marriage_status` varchar(200) DEFAULT NULL,
  `health_status` varchar(200) DEFAULT NULL,
  `political_status` varchar(200) DEFAULT NULL,
  `nation` varchar(200) DEFAULT NULL,
  `is_foreign` varchar(200) DEFAULT NULL,
  `attending_type` varchar(200) DEFAULT NULL COMMENT '就读方式',
  `learn_type` varchar(200) DEFAULT NULL COMMENT '统一招生考试/普通入学',
  `low_insurance` varchar(200) DEFAULT NULL COMMENT '是否低保',
  `handicapped` varchar(200) DEFAULT NULL COMMENT '残疾人',
  `consume_info` varchar(50) DEFAULT NULL COMMENT '学生消费情况',
  `default_bankcard` varchar(200) DEFAULT NULL COMMENT '默认卡号',
  `actual_bankcard` varchar(200) DEFAULT NULL COMMENT '实际卡号',
  `creator` varchar(200) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `addressType` varchar(200) DEFAULT NULL COMMENT '居住地类型',
  `account_type` varchar(200) DEFAULT NULL COMMENT '户口类型',
  `school_name` varchar(100) DEFAULT NULL COMMENT '毕业学校',
  `is_poor` varchar(30) DEFAULT NULL,
  `student_from` varchar(200) DEFAULT NULL COMMENT '学生来源',
  `police_station` varchar(200) DEFAULT NULL COMMENT '所属派出所',
  `train_region` varchar(200) DEFAULT NULL COMMENT '乘火车区间',
  `student_obj` varchar(200) DEFAULT NULL COMMENT '招生对象',
  `study_place` varchar(200) DEFAULT NULL COMMENT '教学点',
  `is_move` varchar(200) DEFAULT NULL COMMENT '是否随迁子女',
  `trans_provincial` varchar(200) DEFAULT NULL COMMENT '跨省招生',
  `cooperation_type` varchar(200) DEFAULT NULL COMMENT '联招合作类型',
  `sub_teach` varchar(200) DEFAULT NULL COMMENT '分段培养方式',
  `parent_name` varchar(200) DEFAULT NULL COMMENT '监护人姓名',
  `money` double DEFAULT NULL COMMENT '金额',
  `photo_url` varchar(500) DEFAULT NULL COMMENT '照片名称',
  `application_photo_url` varchar(500) DEFAULT NULL COMMENT '申请表照片地址',
  `semester` varchar(200) DEFAULT NULL COMMENT '学期',
  `recruitType` varchar(200) DEFAULT NULL,
  `birthDivisionCode` varchar(200) DEFAULT NULL,
  `registeredDivisionCode` varchar(200) DEFAULT NULL,
  `addressTown` varchar(200) DEFAULT NULL COMMENT '镇',
  `addressTownship` varchar(200) DEFAULT NULL COMMENT '组',
  `addressGroup` varchar(200) DEFAULT NULL COMMENT '号',
  `score` double DEFAULT NULL COMMENT '得分',
  `diff_level` int(4) DEFAULT '0' COMMENT '困难等级 0:不困难，1：困难>40，2：一般困难>60，3：特别困难>80',
  `head_teache_check` int(4) DEFAULT '0' COMMENT '班主任确认，0：未确认，1：通过，2：不通过',
  `head_teache_name` varchar(255) DEFAULT NULL COMMENT '班主任名字',
  `flag` tinyint(4) DEFAULT '0' COMMENT '有新指标增加时，该字段的值会变化，默认为0，有变化时置为1',
  `note` varchar(500) DEFAULT NULL,
  `is_graduation` int(4) DEFAULT '0' COMMENT '是否已经毕业 0：在读，1：已毕业',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sch_idcard` (`school_id`,`idcard`) USING BTREE,
  KEY `FK_Reference_3` (`school_id`) USING BTREE,
  KEY `FK_Reference_4` (`family_id`) USING BTREE,
  CONSTRAINT `tb_erm_student_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `tb_erm_school` (`id`),
  CONSTRAINT `tb_erm_student_ibfk_2` FOREIGN KEY (`family_id`) REFERENCES `tb_erm_family` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2121 DEFAULT CHARSET=utf8 COMMENT='学生信息';

 

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_erm_admin
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_admin`;
CREATE TABLE `tb_erm_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `telphone` varchar(20) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `disabled` tinyint(4) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL COMMENT '管理员类型',
  `email` varchar(50) DEFAULT NULL,
  `eb_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `用户名已存在` (`username`) USING BTREE,
  KEY `FK_Reference_21` (`school_id`) USING BTREE,
  KEY `FK_Reference_9` (`role_id`) USING BTREE,
  CONSTRAINT `tb_erm_admin_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `tb_erm_school` (`id`),
  CONSTRAINT `tb_erm_admin_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `tb_erm_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='管理员信息';

-- ----------------------------
-- Records of tb_erm_admin
-- ----------------------------
INSERT INTO `tb_erm_admin` VALUES ('1', '18163660089', 'spancer', 'spancer', '$2a$06$NGvukSuW1F90krjZSSDFl.DQVJXJMPaqvib3Zl40Myjil90Mz949S', '1', '2', '373416233@qq.com', '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for tb_erm_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_dict`;
CREATE TABLE `tb_erm_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT NULL,
  `dict_code` varchar(20) DEFAULT NULL,
  `dict_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=391 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_erm_dict
-- ----------------------------
INSERT INTO `tb_erm_dict` VALUES ('3', '1', 'o000001525221723', '男');
INSERT INTO `tb_erm_dict` VALUES ('4', '1', 'v000000656460997', '女');
INSERT INTO `tb_erm_dict` VALUES ('64', '2', 'j000000974467772', '汉族');
INSERT INTO `tb_erm_dict` VALUES ('65', '2', 'f000001570696622', '朝鲜族');
INSERT INTO `tb_erm_dict` VALUES ('66', '2', 'd000001296713483', '满族');
INSERT INTO `tb_erm_dict` VALUES ('67', '2', 'p000000286447673', '侗族');
INSERT INTO `tb_erm_dict` VALUES ('68', '2', 's000000780040818', '瑶族');
INSERT INTO `tb_erm_dict` VALUES ('69', '2', 'r000000232125208', '白族');
INSERT INTO `tb_erm_dict` VALUES ('70', '2', 'k000001396724136', '土家族');
INSERT INTO `tb_erm_dict` VALUES ('71', '2', 'p000001768102048', '哈尼族');
INSERT INTO `tb_erm_dict` VALUES ('72', '2', 'f000001596063887', '哈萨克族');
INSERT INTO `tb_erm_dict` VALUES ('73', '2', 'f000001596472899', '傣族');
INSERT INTO `tb_erm_dict` VALUES ('74', '2', 'v000000928808967', '黎族');
INSERT INTO `tb_erm_dict` VALUES ('75', '2', 'e000000625208752', '蒙古族');
INSERT INTO `tb_erm_dict` VALUES ('76', '2', 'p000001937583472', '傈僳族');
INSERT INTO `tb_erm_dict` VALUES ('77', '2', 'x000001860072939', '佤族');
INSERT INTO `tb_erm_dict` VALUES ('78', '2', 'z000000873746984', '畲族');
INSERT INTO `tb_erm_dict` VALUES ('79', '2', 'r000000617370473', '高山族');
INSERT INTO `tb_erm_dict` VALUES ('80', '2', 'o000000746703336', '拉祜族');
INSERT INTO `tb_erm_dict` VALUES ('81', '2', 'd000000324715958', '水族');
INSERT INTO `tb_erm_dict` VALUES ('82', '2', 'a000001429640012', '东乡族');
INSERT INTO `tb_erm_dict` VALUES ('83', '2', 'u000002105759127', '纳西族');
INSERT INTO `tb_erm_dict` VALUES ('84', '2', 'a000001274440826', '景颇族');
INSERT INTO `tb_erm_dict` VALUES ('85', '2', 'm000000738730771', '柯尔克孜族');
INSERT INTO `tb_erm_dict` VALUES ('86', '2', 'k000001532205740', '回族');
INSERT INTO `tb_erm_dict` VALUES ('87', '2', 't000001639765176', '土族');
INSERT INTO `tb_erm_dict` VALUES ('88', '2', 'x000000982066211', '达斡尔族');
INSERT INTO `tb_erm_dict` VALUES ('89', '2', 'g000002122194704', '仫佬族');
INSERT INTO `tb_erm_dict` VALUES ('90', '2', 'f000001703692936', '羌族');
INSERT INTO `tb_erm_dict` VALUES ('91', '2', 'd000000506186181', '布朗族');
INSERT INTO `tb_erm_dict` VALUES ('92', '2', 'p000000728567630', '撒拉族');
INSERT INTO `tb_erm_dict` VALUES ('93', '2', 'q000001687955424', '毛南族');
INSERT INTO `tb_erm_dict` VALUES ('94', '2', 'c000000486723983', '仡佬族');
INSERT INTO `tb_erm_dict` VALUES ('95', '2', 'u000001523845331', '锡伯族');
INSERT INTO `tb_erm_dict` VALUES ('96', '2', 'u000001894631424', '阿昌族');
INSERT INTO `tb_erm_dict` VALUES ('97', '2', 'x000000302486999', '藏族');
INSERT INTO `tb_erm_dict` VALUES ('98', '2', 'j000000273522903', '普米族');
INSERT INTO `tb_erm_dict` VALUES ('99', '2', 'w000001058915374', '塔吉克族');
INSERT INTO `tb_erm_dict` VALUES ('100', '2', 's000000052763195', '怒族');
INSERT INTO `tb_erm_dict` VALUES ('101', '2', 'm000000476092200', '乌孜别克族');
INSERT INTO `tb_erm_dict` VALUES ('102', '2', 't000001955411738', '俄罗斯族');
INSERT INTO `tb_erm_dict` VALUES ('103', '2', 'd000001601921653', '鄂温克族');
INSERT INTO `tb_erm_dict` VALUES ('104', '2', 'k000000108417685', '德昂族');
INSERT INTO `tb_erm_dict` VALUES ('105', '2', 'v000002044186263', '保安族');
INSERT INTO `tb_erm_dict` VALUES ('106', '2', 'k000000856439885', '裕固族');
INSERT INTO `tb_erm_dict` VALUES ('107', '2', 't000001795394893', '京族');
INSERT INTO `tb_erm_dict` VALUES ('108', '2', 'a000000933208528', '维吾尔族');
INSERT INTO `tb_erm_dict` VALUES ('109', '2', 'x000001577944035', '塔塔尔族');
INSERT INTO `tb_erm_dict` VALUES ('110', '2', 'm000000270759317', '独龙族');
INSERT INTO `tb_erm_dict` VALUES ('111', '2', 'd000001147007569', '鄂伦春族');
INSERT INTO `tb_erm_dict` VALUES ('112', '2', 'b000000617587621', '赫哲族');
INSERT INTO `tb_erm_dict` VALUES ('113', '2', 'c000000273152749', '门巴族');
INSERT INTO `tb_erm_dict` VALUES ('114', '2', 'h000000234379683', '珞巴族');
INSERT INTO `tb_erm_dict` VALUES ('115', '2', 'r000001802821065', '基诺族');
INSERT INTO `tb_erm_dict` VALUES ('116', '2', 'n000000146702007', '苗族');
INSERT INTO `tb_erm_dict` VALUES ('117', '2', 'i000000548655218', '彝族');
INSERT INTO `tb_erm_dict` VALUES ('118', '2', 'v000000093820770', '壮族');
INSERT INTO `tb_erm_dict` VALUES ('119', '2', 'z000001845988722', '布依族');
INSERT INTO `tb_erm_dict` VALUES ('120', '2', 'y000001711662834', '穿青人族');
INSERT INTO `tb_erm_dict` VALUES ('121', '2', 'k000001995319725', '其他');
INSERT INTO `tb_erm_dict` VALUES ('122', '2', 'g000002088356154', '外国血统中国籍人士');
INSERT INTO `tb_erm_dict` VALUES ('123', '3', 'g000001157322281', '健康或良好');
INSERT INTO `tb_erm_dict` VALUES ('124', '3', 'z000002147153871', '一般或较弱');
INSERT INTO `tb_erm_dict` VALUES ('125', '3', 'w000001226853008', '有慢性病');
INSERT INTO `tb_erm_dict` VALUES ('126', '3', 'g000002041820531', '残疾');
INSERT INTO `tb_erm_dict` VALUES ('127', '4', 'a000002072870250', '中国共产主义青年团团员');
INSERT INTO `tb_erm_dict` VALUES ('128', '4', 'd000001042070375', '群众');
INSERT INTO `tb_erm_dict` VALUES ('129', '4', 'r000000398017741', '中国共产党党员');
INSERT INTO `tb_erm_dict` VALUES ('130', '4', 'i000000423751126', '中国共产党预备党员');
INSERT INTO `tb_erm_dict` VALUES ('131', '4', 'b000000344896199', '中国国民党革命委员会会员');
INSERT INTO `tb_erm_dict` VALUES ('132', '4', 'a000000150282595', '中国民主同盟盟员');
INSERT INTO `tb_erm_dict` VALUES ('133', '4', 'h000001270862020', '中国民主建国会会员');
INSERT INTO `tb_erm_dict` VALUES ('134', '4', 'd000001125882315', '中国民主促进会会员');
INSERT INTO `tb_erm_dict` VALUES ('135', '4', 'i000001290027575', '中国农工民主党党员');
INSERT INTO `tb_erm_dict` VALUES ('136', '4', 'k000000057905653', '中国致公党党员');
INSERT INTO `tb_erm_dict` VALUES ('137', '4', 'l000001034147817', '九三学社社员');
INSERT INTO `tb_erm_dict` VALUES ('138', '4', 'd000000346802462', '台湾民主自治同盟盟员');
INSERT INTO `tb_erm_dict` VALUES ('139', '4', 'd000000464753225', '无党派民主人士');
INSERT INTO `tb_erm_dict` VALUES ('140', '5', 't000000350523219', '农业户口');
INSERT INTO `tb_erm_dict` VALUES ('141', '5', 'j000002070228011', '非农业户口');
INSERT INTO `tb_erm_dict` VALUES ('142', '6', 'x000000100176999', '农村');
INSERT INTO `tb_erm_dict` VALUES ('143', '6', 'b000000419397343', '县城');
INSERT INTO `tb_erm_dict` VALUES ('144', '6', 'k000002032183714', '乡镇非农');
INSERT INTO `tb_erm_dict` VALUES ('145', '6', 'h000000650954528', '城市');
INSERT INTO `tb_erm_dict` VALUES ('146', '7', 'c000000011901957', '应届');
INSERT INTO `tb_erm_dict` VALUES ('147', '7', 'm000001219775221', '非应届');
INSERT INTO `tb_erm_dict` VALUES ('150', '21', '1', '幼儿园');
INSERT INTO `tb_erm_dict` VALUES ('151', '21', '2', '小学');
INSERT INTO `tb_erm_dict` VALUES ('152', '21', '3', '初中');
INSERT INTO `tb_erm_dict` VALUES ('153', '21', '4', '普高');
INSERT INTO `tb_erm_dict` VALUES ('154', '21', '5', '中职');
INSERT INTO `tb_erm_dict` VALUES ('155', '22', 'c000001592854020', '2017年春季');
INSERT INTO `tb_erm_dict` VALUES ('156', '22', 'k000000963998252', '2017年秋季');
INSERT INTO `tb_erm_dict` VALUES ('157', '22', 'z000000989635532', '2018年春季');
INSERT INTO `tb_erm_dict` VALUES ('158', '22', 'q000001298097866', '2018年秋季');
INSERT INTO `tb_erm_dict` VALUES ('159', '22', 'h000001407394646', '2019年春季');
INSERT INTO `tb_erm_dict` VALUES ('160', '22', 'u000000573439890', '2019年秋季');
INSERT INTO `tb_erm_dict` VALUES ('161', '9', 'c000001325272139', '湖南省(430000000000)');
INSERT INTO `tb_erm_dict` VALUES ('162', '9', 'z000001675074298', '湖南省长沙市(430100000000)');
INSERT INTO `tb_erm_dict` VALUES ('163', '9', 'k000002057155931', '长沙市芙蓉区(430102000000)');
INSERT INTO `tb_erm_dict` VALUES ('164', '9', 't000000798584581', '长沙市天心区(430103000000)');
INSERT INTO `tb_erm_dict` VALUES ('165', '9', 'r000000635239726', '长沙市岳麓区(430104000000)');
INSERT INTO `tb_erm_dict` VALUES ('166', '9', 'r000000841434962', '长沙市开福区(430105000000)');
INSERT INTO `tb_erm_dict` VALUES ('167', '9', 'j000000992187257', '长沙市雨花区(430111000000)');
INSERT INTO `tb_erm_dict` VALUES ('168', '9', 's000000667277498', '长沙市望城区(430112000000)');
INSERT INTO `tb_erm_dict` VALUES ('169', '9', 't000001491989835', '长沙市长沙县(430121000000)');
INSERT INTO `tb_erm_dict` VALUES ('170', '9', 'q000001501428318', '长沙市宁乡县(430124000000)');
INSERT INTO `tb_erm_dict` VALUES ('171', '9', 'p000001470609071', '长沙市浏阳市(430181000000)');
INSERT INTO `tb_erm_dict` VALUES ('172', '9', 'u000000151042154', '长沙市长沙市高新区(4301A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('173', '9', 'c000001574769654', '长沙市长沙市市本级(4301A2000000)');
INSERT INTO `tb_erm_dict` VALUES ('174', '9', 'h000001567087678', '湖南省株洲市(430200000000)');
INSERT INTO `tb_erm_dict` VALUES ('175', '9', 'u000001044561257', '株洲市荷塘区(430202000000)');
INSERT INTO `tb_erm_dict` VALUES ('176', '9', 'q000001721433973', '株洲市芦淞区(430203000000)');
INSERT INTO `tb_erm_dict` VALUES ('177', '9', 'd000000655714705', '株洲市石峰区(430204000000)');
INSERT INTO `tb_erm_dict` VALUES ('178', '9', 'r000001917804996', '株洲市天元区(430211000000)');
INSERT INTO `tb_erm_dict` VALUES ('179', '9', 'q000001834094788', '株洲市株洲县(430221000000)');
INSERT INTO `tb_erm_dict` VALUES ('180', '9', 'h000001497323432', '株洲市攸县(430223000000)');
INSERT INTO `tb_erm_dict` VALUES ('181', '9', 'h000000858315661', '株洲市茶陵县(430224000000)');
INSERT INTO `tb_erm_dict` VALUES ('182', '9', 's000001570852564', '株洲市炎陵县(430225000000)');
INSERT INTO `tb_erm_dict` VALUES ('183', '9', 'z000001145027118', '株洲市醴陵市(430281000000)');
INSERT INTO `tb_erm_dict` VALUES ('184', '9', 'k000001548831056', '株洲市云龙示范区(4302A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('185', '9', 'o000001458825487', '株洲市株洲市市本级(4302A2000000)');
INSERT INTO `tb_erm_dict` VALUES ('186', '9', 'u000001102821050', '湖南省湘潭市(430300000000)');
INSERT INTO `tb_erm_dict` VALUES ('187', '9', 'y000001111318977', '湘潭市雨湖区(430302000000)');
INSERT INTO `tb_erm_dict` VALUES ('188', '9', 'h000001121199314', '湘潭市岳塘区(430304000000)');
INSERT INTO `tb_erm_dict` VALUES ('189', '9', 'w000001749212200', '湘潭市湘潭县(430321000000)');
INSERT INTO `tb_erm_dict` VALUES ('190', '9', 'n000001130628595', '湘潭市湘乡市(430381000000)');
INSERT INTO `tb_erm_dict` VALUES ('191', '9', 'k000000311157847', '湘潭市韶山市(430382000000)');
INSERT INTO `tb_erm_dict` VALUES ('192', '9', 's000001011802614', '湘潭市九华经开区(4303A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('193', '9', 'p000001026941388', '湘潭市昭山示范区(4303A2000000)');
INSERT INTO `tb_erm_dict` VALUES ('194', '9', 'p000000717991346', '湘潭市湘潭市市本级(4303A3000000)');
INSERT INTO `tb_erm_dict` VALUES ('195', '9', 'h000000757712782', '湖南省衡阳市(430400000000)');
INSERT INTO `tb_erm_dict` VALUES ('196', '9', 'z000001159963625', '衡阳市珠晖区(430405000000)');
INSERT INTO `tb_erm_dict` VALUES ('197', '9', 'k000000194469630', '衡阳市雁峰区(430406000000)');
INSERT INTO `tb_erm_dict` VALUES ('198', '9', 'e000000341412359', '衡阳市石鼓区(430407000000)');
INSERT INTO `tb_erm_dict` VALUES ('199', '9', 'd000001818994034', '衡阳市蒸湘区(430408000000)');
INSERT INTO `tb_erm_dict` VALUES ('200', '9', 'c000001190351865', '衡阳市南岳区(430412000000)');
INSERT INTO `tb_erm_dict` VALUES ('201', '9', 'q000001058129182', '衡阳市衡阳县(430421000000)');
INSERT INTO `tb_erm_dict` VALUES ('202', '9', 't000000361040636', '衡阳市衡南县(430422000000)');
INSERT INTO `tb_erm_dict` VALUES ('203', '9', 'e000000935019493', '衡阳市衡山县(430423000000)');
INSERT INTO `tb_erm_dict` VALUES ('204', '9', 'z000001287084626', '衡阳市衡东县(430424000000)');
INSERT INTO `tb_erm_dict` VALUES ('205', '9', 'w000001264054816', '衡阳市祁东县(430426000000)');
INSERT INTO `tb_erm_dict` VALUES ('206', '9', 'l000001091037732', '衡阳市耒阳市(430481000000)');
INSERT INTO `tb_erm_dict` VALUES ('207', '9', 'h000001941955158', '衡阳市常宁市(430482000000)');
INSERT INTO `tb_erm_dict` VALUES ('208', '9', 'j000000289271886', '衡阳市衡阳市市本级(4304A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('209', '9', 'a000001189624184', '湖南省邵阳市(430500000000)');
INSERT INTO `tb_erm_dict` VALUES ('210', '9', 'p000000704362064', '邵阳市双清区(430502000000)');
INSERT INTO `tb_erm_dict` VALUES ('211', '9', 'h000000723422349', '邵阳市大祥区(430503000000)');
INSERT INTO `tb_erm_dict` VALUES ('212', '9', 'n000001314461993', '邵阳市北塔区(430511000000)');
INSERT INTO `tb_erm_dict` VALUES ('213', '9', 'k000001665199124', '邵阳市邵东县(430521000000)');
INSERT INTO `tb_erm_dict` VALUES ('214', '9', 'r000001001601696', '邵阳市新邵县(430522000000)');
INSERT INTO `tb_erm_dict` VALUES ('215', '9', 'f000000277538410', '邵阳市邵阳县(430523000000)');
INSERT INTO `tb_erm_dict` VALUES ('216', '9', 'o000001952104793', '邵阳市隆回县(430524000000)');
INSERT INTO `tb_erm_dict` VALUES ('217', '9', 'l000001238127958', '邵阳市洞口县(430525000000)');
INSERT INTO `tb_erm_dict` VALUES ('218', '9', 'k000001476059109', '邵阳市绥宁县(430527000000)');
INSERT INTO `tb_erm_dict` VALUES ('219', '9', 'j000000582424538', '邵阳市新宁县(430528000000)');
INSERT INTO `tb_erm_dict` VALUES ('220', '9', 'r000000618114105', '邵阳市城步苗族自治县(430529000000)');
INSERT INTO `tb_erm_dict` VALUES ('221', '9', 'o000000352346530', '邵阳市武冈市(430581000000)');
INSERT INTO `tb_erm_dict` VALUES ('222', '9', 'g000000894987678', '邵阳市邵阳市市本级(4305A1000000)(430822000000)');
INSERT INTO `tb_erm_dict` VALUES ('223', '9', 'm000001129537795', '张家界市张家界市市本级(4308A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('224', '9', 'x000000472280946', '湖南省益阳市(430900000000)');
INSERT INTO `tb_erm_dict` VALUES ('225', '9', 'x000001867436359', '益阳市资阳区(430902000000)');
INSERT INTO `tb_erm_dict` VALUES ('226', '9', 'q000001636412986', '益阳市赫山区(430903000000)');
INSERT INTO `tb_erm_dict` VALUES ('227', '9', 'd000000309770211', '益阳市南县(430921000000)');
INSERT INTO `tb_erm_dict` VALUES ('228', '9', 't000000357703401', '益阳市桃江县(430922000000)');
INSERT INTO `tb_erm_dict` VALUES ('229', '9', 'p000001661930506', '益阳市安化县(430923000000)');
INSERT INTO `tb_erm_dict` VALUES ('230', '9', 't000001426500621', '益阳市沅江市(430981000000)');
INSERT INTO `tb_erm_dict` VALUES ('231', '9', 'k000000099463371', '益阳市大通湖区(4309A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('232', '9', 'j000001079712218', '湖南省郴州市(431000000000)');
INSERT INTO `tb_erm_dict` VALUES ('233', '9', 'z000001455130041', '郴州市北湖区(431002000000)');
INSERT INTO `tb_erm_dict` VALUES ('234', '9', 'b000001816730063', '郴州市苏仙区(431003000000)');
INSERT INTO `tb_erm_dict` VALUES ('235', '9', 'z000001005503832', '郴州市桂阳县(431021000000)');
INSERT INTO `tb_erm_dict` VALUES ('236', '9', 'r000000845467654', '郴州市宜章县(431022000000)');
INSERT INTO `tb_erm_dict` VALUES ('237', '9', 'p000000816366429', '郴州市永兴县(431023000000)');
INSERT INTO `tb_erm_dict` VALUES ('238', '9', 'z000001151931845', '郴州市嘉禾县(431024000000)');
INSERT INTO `tb_erm_dict` VALUES ('239', '9', 'o000000670560710', '郴州市临武县(431025000000)');
INSERT INTO `tb_erm_dict` VALUES ('240', '9', 'f000001108168704', '郴州市汝城县(431026000000)');
INSERT INTO `tb_erm_dict` VALUES ('241', '9', 'p000000457724631', '郴州市桂东县(431027000000)');
INSERT INTO `tb_erm_dict` VALUES ('242', '9', 'd000000503640027', '郴州市安仁县(431028000000)');
INSERT INTO `tb_erm_dict` VALUES ('243', '9', 'm000001345910956', '郴州市资兴市(431081000000)');
INSERT INTO `tb_erm_dict` VALUES ('244', '9', 'h000001184546632', '郴州市郴州市市本级(4310A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('245', '9', 'b000001539912647', '湖南省永州市(431100000000)');
INSERT INTO `tb_erm_dict` VALUES ('246', '9', 'c000001484091931', '永州市零陵区(431102000000)');
INSERT INTO `tb_erm_dict` VALUES ('247', '9', 'm000000155276471', '永州市冷水滩区(431103000000)');
INSERT INTO `tb_erm_dict` VALUES ('248', '9', 'z000000969406012', '永州市祁阳县(431121000000)');
INSERT INTO `tb_erm_dict` VALUES ('249', '9', 'm000000464010192', '永州市东安县(431122000000)');
INSERT INTO `tb_erm_dict` VALUES ('250', '9', 'f000000354709796', '永州市双牌县(431123000000)');
INSERT INTO `tb_erm_dict` VALUES ('251', '9', 'r000001393926276', '永州市道县(431124000000)');
INSERT INTO `tb_erm_dict` VALUES ('252', '9', 'i000001242490141', '永州市江永县(431125000000)');
INSERT INTO `tb_erm_dict` VALUES ('253', '9', 'r000000647723967', '永州市宁远县(431126000000)');
INSERT INTO `tb_erm_dict` VALUES ('254', '9', 'u000001906931326', '永州市蓝山县(431127000000)');
INSERT INTO `tb_erm_dict` VALUES ('255', '9', 'k000000589431873', '永州市新田县(431128000000)');
INSERT INTO `tb_erm_dict` VALUES ('256', '9', 's000001033837266', '永州市江华瑶族自治县(431129000000)');
INSERT INTO `tb_erm_dict` VALUES ('257', '9', 's000001241507627', '永州市回龙圩管理区(4311A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('258', '9', 'y000001309928081', '永州市金洞管理区(4311A2000000)');
INSERT INTO `tb_erm_dict` VALUES ('259', '9', 'e000002086450787', '永州市永州市市本级(4311A3000000)');
INSERT INTO `tb_erm_dict` VALUES ('260', '9', 'w000000777421743', '湖南省怀化市(431200000000)');
INSERT INTO `tb_erm_dict` VALUES ('261', '9', 'p000000927505712', '怀化市鹤城区(431202000000)');
INSERT INTO `tb_erm_dict` VALUES ('262', '9', 'y000000254177225', '怀化市中方县(431221000000)');
INSERT INTO `tb_erm_dict` VALUES ('263', '9', 'b000000751276594', '怀化市沅陵县(431222000000)');
INSERT INTO `tb_erm_dict` VALUES ('264', '9', 'f000001488082051', '怀化市辰溪县(431223000000)');
INSERT INTO `tb_erm_dict` VALUES ('265', '9', 'h000001517518956', '怀化市溆浦县(431224000000)');
INSERT INTO `tb_erm_dict` VALUES ('266', '9', 'y000002016319704', '怀化市会同县(431225000000)');
INSERT INTO `tb_erm_dict` VALUES ('267', '9', 'g000001820446067', '怀化市麻阳苗族自治县(431226000000)');
INSERT INTO `tb_erm_dict` VALUES ('268', '9', 'o000001657575261', '怀化市新晃侗族自治县(431227000000)');
INSERT INTO `tb_erm_dict` VALUES ('269', '9', 'k000000274117674', '怀化市芷江侗族自治县(431228000000)');
INSERT INTO `tb_erm_dict` VALUES ('270', '9', 'x000002042630509', '怀化市靖州苗族侗族自治县(431229000000)');
INSERT INTO `tb_erm_dict` VALUES ('271', '9', 'j000001251019359', '怀化市通道侗族自治县(431230000000)');
INSERT INTO `tb_erm_dict` VALUES ('272', '9', 'a000000338743107', '怀化市洪江市(431281000000)');
INSERT INTO `tb_erm_dict` VALUES ('273', '9', 't000001328327423', '怀化市洪江区(4312A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('274', '9', 'q000001326625248', '怀化市怀化市市本级(4312A2000000)');
INSERT INTO `tb_erm_dict` VALUES ('275', '9', 'f000001124671694', '湖南省娄底市(431300000000)');
INSERT INTO `tb_erm_dict` VALUES ('276', '9', 'r000000958226771', '娄底市娄星区(431302000000)');
INSERT INTO `tb_erm_dict` VALUES ('277', '9', 'q000001984788807', '娄底市双峰县(431321000000)');
INSERT INTO `tb_erm_dict` VALUES ('278', '9', 'a000001486148981', '娄底市新化县(431322000000)');
INSERT INTO `tb_erm_dict` VALUES ('279', '9', 'c000000265692278', '娄底市冷水江市(431381000000)');
INSERT INTO `tb_erm_dict` VALUES ('280', '9', 'r000000257227149', '娄底市涟源市(431382000000)');
INSERT INTO `tb_erm_dict` VALUES ('281', '9', 'v000001467570813', '娄底市娄底市经济开发区(4313A1000000)');
INSERT INTO `tb_erm_dict` VALUES ('282', '9', 'k000000101201662', '娄底市娄底市市本级(4313A2000000)');
INSERT INTO `tb_erm_dict` VALUES ('283', '9', 'k000001548189006', '湖南省湘西土家族苗族自治州(433100000000)');
INSERT INTO `tb_erm_dict` VALUES ('284', '9', 'z000001108321007', '湘西土家族苗族自治州吉首市(433101000000)');
INSERT INTO `tb_erm_dict` VALUES ('285', '9', 'r000001194231355', '湘西土家族苗族自治州泸溪县(433122000000)');
INSERT INTO `tb_erm_dict` VALUES ('286', '9', 'b000000467535834', '湘西土家族苗族自治州凤凰县(433123000000)');
INSERT INTO `tb_erm_dict` VALUES ('287', '9', 's000000448512064', '湘西土家族苗族自治州花垣县(433124000000)');
INSERT INTO `tb_erm_dict` VALUES ('288', '9', 'n000000586703584', '湘西土家族苗族自治州保靖县(433125000000)');
INSERT INTO `tb_erm_dict` VALUES ('289', '9', 'g000000648472883', '湘西土家族苗族自治州古丈县(433126000000)');
INSERT INTO `tb_erm_dict` VALUES ('290', '9', 'v000001619293988', '湘西土家族苗族自治州永顺县(433127000000)');
INSERT INTO `tb_erm_dict` VALUES ('291', '9', 'c000000633695767', '湘西土家族苗族自治州龙山县(433130000000)');
INSERT INTO `tb_erm_dict` VALUES ('292', '10', 't000001432177748', '非港澳台侨');
INSERT INTO `tb_erm_dict` VALUES ('293', '10', 'a000001971597135', '香港同胞');
INSERT INTO `tb_erm_dict` VALUES ('294', '10', 'g000001433694348', '香港同胞亲属');
INSERT INTO `tb_erm_dict` VALUES ('295', '10', 'k000000896387175', '澳门同胞');
INSERT INTO `tb_erm_dict` VALUES ('296', '10', 'f000001141122971', '澳门同胞亲属');
INSERT INTO `tb_erm_dict` VALUES ('297', '10', 'p000000846782861', '台湾同胞');
INSERT INTO `tb_erm_dict` VALUES ('298', '10', 'x000001015447165', '台湾同胞亲属');
INSERT INTO `tb_erm_dict` VALUES ('299', '10', 'c000000637011858', '华侨');
INSERT INTO `tb_erm_dict` VALUES ('300', '10', 'v000001610298628', '侨眷');
INSERT INTO `tb_erm_dict` VALUES ('301', '10', 'r000001336827918', '归侨');
INSERT INTO `tb_erm_dict` VALUES ('302', '10', 'j000000852187636', '归侨子女');
INSERT INTO `tb_erm_dict` VALUES ('303', '10', 'z000000265600155', '归国留学人员');
INSERT INTO `tb_erm_dict` VALUES ('304', '10', 't000000522797552', '非华裔中国人');
INSERT INTO `tb_erm_dict` VALUES ('305', '10', 'e000001472622373', '外籍华裔人');
INSERT INTO `tb_erm_dict` VALUES ('306', '10', 'u000000741150095', '外国人');
INSERT INTO `tb_erm_dict` VALUES ('307', '10', 'f000000032484484', '其他');
INSERT INTO `tb_erm_dict` VALUES ('308', '11', 'g000000797590461', '统一招生考试/普通入学');
INSERT INTO `tb_erm_dict` VALUES ('309', '11', 'v000001038040455', '保送');
INSERT INTO `tb_erm_dict` VALUES ('310', '11', 'h000001037601687', '民族班');
INSERT INTO `tb_erm_dict` VALUES ('311', '11', 'b000000458965577', '定向培养');
INSERT INTO `tb_erm_dict` VALUES ('312', '11', 'm000001768676428', '体育特招');
INSERT INTO `tb_erm_dict` VALUES ('313', '11', 'k000000602666048', '文艺特招');
INSERT INTO `tb_erm_dict` VALUES ('314', '11', 'f000001998860113', '学生干部保送');
INSERT INTO `tb_erm_dict` VALUES ('315', '11', 'p000001118868708', '考试推荐');
INSERT INTO `tb_erm_dict` VALUES ('316', '11', 'o000000966177379', '外校转入');
INSERT INTO `tb_erm_dict` VALUES ('317', '11', 'd000001513881664', '恢复入学资格');
INSERT INTO `tb_erm_dict` VALUES ('318', '11', 'z000001912900265', '其他');
INSERT INTO `tb_erm_dict` VALUES ('319', '12', 'u000000862697341', '走读');
INSERT INTO `tb_erm_dict` VALUES ('320', '12', 'o000000817699095', '住校');
INSERT INTO `tb_erm_dict` VALUES ('321', '13', 'u000000057435032', '统一招生');
INSERT INTO `tb_erm_dict` VALUES ('322', '13', 'z000001274283521', '自主招生');
INSERT INTO `tb_erm_dict` VALUES ('328', '14', 'x000000732450798', '三年制');
INSERT INTO `tb_erm_dict` VALUES ('329', '14', 'r000000679783764', '四年制');
INSERT INTO `tb_erm_dict` VALUES ('330', '14', 'p000000578037892', '五年制');
INSERT INTO `tb_erm_dict` VALUES ('331', '14', 'o000001432638150', '一年制');
INSERT INTO `tb_erm_dict` VALUES ('332', '14', 'b000000560063433', '一年半制');
INSERT INTO `tb_erm_dict` VALUES ('333', '14', 'o000001866072410', '两年制');
INSERT INTO `tb_erm_dict` VALUES ('334', '14', 'v000000420558932', '两年半制');
INSERT INTO `tb_erm_dict` VALUES ('335', '14', 'd000001250174757', '三年半制');
INSERT INTO `tb_erm_dict` VALUES ('336', '14', 'e000000676582140', '四年半制');
INSERT INTO `tb_erm_dict` VALUES ('337', '14', 'q000000291409570', '五年半制');
INSERT INTO `tb_erm_dict` VALUES ('338', '14', 'c000000885367106', '六年制');
INSERT INTO `tb_erm_dict` VALUES ('339', '14', 'd000000650930416', '六年半制');
INSERT INTO `tb_erm_dict` VALUES ('340', '14', 'c000000476198574', '七年制');
INSERT INTO `tb_erm_dict` VALUES ('341', '14', 'x000000639261936', '八年制');
INSERT INTO `tb_erm_dict` VALUES ('342', '15', 'm000002079322274', '调整后中职学生');
INSERT INTO `tb_erm_dict` VALUES ('343', '15', 'a000001157121233', '职业高中学生');
INSERT INTO `tb_erm_dict` VALUES ('344', '15', 'a000000007767197', '普通中专学生');
INSERT INTO `tb_erm_dict` VALUES ('345', '15', 't000000418358987', '成人中专学生');
INSERT INTO `tb_erm_dict` VALUES ('346', '15', 'v000001278852589', '技工学校学生');
INSERT INTO `tb_erm_dict` VALUES ('347', '16', 'u000000626278827', '未婚');
INSERT INTO `tb_erm_dict` VALUES ('348', '16', 'q000001600892524', '已婚');
INSERT INTO `tb_erm_dict` VALUES ('349', '17', 'a000001893504419', '应届初中毕业生');
INSERT INTO `tb_erm_dict` VALUES ('350', '17', 'i000000584022551', '应届高中毕业生');
INSERT INTO `tb_erm_dict` VALUES ('351', '17', 'd000001950646550', '往届初中毕业生');
INSERT INTO `tb_erm_dict` VALUES ('352', '17', 'd000000838929707', '进城务工人员');
INSERT INTO `tb_erm_dict` VALUES ('353', '17', 'f000000135292649', '农民');
INSERT INTO `tb_erm_dict` VALUES ('354', '17', 's000000254476111', '退役军人');
INSERT INTO `tb_erm_dict` VALUES ('355', '17', 'p000001883411642', '城镇下岗职工');
INSERT INTO `tb_erm_dict` VALUES ('356', '17', 'g000001151817381', '其他');
INSERT INTO `tb_erm_dict` VALUES ('357', '17', 'j000000981015972', '高职高专学生');
INSERT INTO `tb_erm_dict` VALUES ('358', '17', 'c000001639500214', '本科及研究生学生');
INSERT INTO `tb_erm_dict` VALUES ('359', '18', 'j000000883867185', '非联合办学');
INSERT INTO `tb_erm_dict` VALUES ('360', '18', 's000001788755627', '城乡联合办学');
INSERT INTO `tb_erm_dict` VALUES ('361', '18', 'l000002087786806', '东中西部联合办学');
INSERT INTO `tb_erm_dict` VALUES ('362', '19', 'u000000356728128', '否');
INSERT INTO `tb_erm_dict` VALUES ('363', '19', 'i000001216442795', '是');
INSERT INTO `tb_erm_dict` VALUES ('366', '20', 'v000001016785466', '全日制');
INSERT INTO `tb_erm_dict` VALUES ('367', '20', 'x000000371530595', '非全日制');
INSERT INTO `tb_erm_dict` VALUES ('374', '111', 'm000000406810974', '学前教育资助-家庭经济困难幼儿园补助');
INSERT INTO `tb_erm_dict` VALUES ('375', '112', 'u000000941578650', '义务教育资助-农村义务教育阶段家庭经济困难寄宿生生活补助');
INSERT INTO `tb_erm_dict` VALUES ('376', '113', 'n000001234465169', '普高教育资助-国家助学金');
INSERT INTO `tb_erm_dict` VALUES ('377', '113', 'l000001071251404', '普高教育资助-免除学杂费');
INSERT INTO `tb_erm_dict` VALUES ('378', '114', 'h000000680246265', '中职教育资助-免除学杂费');
INSERT INTO `tb_erm_dict` VALUES ('379', '114', 's000001582244986', '中职教育资助-国家助学金');
INSERT INTO `tb_erm_dict` VALUES ('380', '115', 'w000000819230670', '高等教育资助-大学生生源地信用助学贷款');
INSERT INTO `tb_erm_dict` VALUES ('381', '24', 's000001545652070', '一档');
INSERT INTO `tb_erm_dict` VALUES ('382', '24', 'i000001433586083', '二档');
INSERT INTO `tb_erm_dict` VALUES ('383', '24', 'f000002017290577', '三档');
INSERT INTO `tb_erm_dict` VALUES ('384', '25', 'm000001419064641', '小学');
INSERT INTO `tb_erm_dict` VALUES ('385', '25', 't000000177770692', '初中');
INSERT INTO `tb_erm_dict` VALUES ('386', '25', 'j000000177770333', '九年一贯制');
INSERT INTO `tb_erm_dict` VALUES ('387', '8', 'e000001184094259', '农村低保');
INSERT INTO `tb_erm_dict` VALUES ('388', '8', 'e000002341341524', '城市低保');
INSERT INTO `tb_erm_dict` VALUES ('389', '8', 'o000001806482767', '残疾');
INSERT INTO `tb_erm_dict` VALUES ('390', '8', 'l000001151600300', '农村特困供养');
INSERT INTO `tb_erm_dict` VALUES ('391', '8', 'l000002432677843', '城市特困供养');
INSERT INTO `tb_erm_dict` VALUES ('392', '8', 'q000001725581479', '建档立卡');
INSERT INTO `tb_erm_dict` VALUES ('393', '8', 'n000001571352444', '其他');

-- ----------------------------
-- Table structure for tb_erm_field
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_field`;
CREATE TABLE `tb_erm_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_code` varchar(30) NOT NULL,
  `field_name` varchar(30) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1文本 2 数字 3 选择器',
  `weight` double DEFAULT '50' COMMENT '字段权重',
  `flag` int(11) DEFAULT '0' COMMENT '类型：-1不显示 0：公共的，且显示的，2，学校自定义的',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_erm_field
-- ----------------------------
INSERT INTO `tb_erm_field` VALUES ('7', '000000119458697', '父亲职业', '3', '6', '0');
INSERT INTO `tb_erm_field` VALUES ('8', '000001001042941', '母亲职业', '3', '6', '0');
INSERT INTO `tb_erm_field` VALUES ('9', '000000388468130', '父亲劳动能力', '3', '10', '0');
INSERT INTO `tb_erm_field` VALUES ('10', '000001270712601', '母亲劳动能力', '3', '10', '0');
INSERT INTO `tb_erm_field` VALUES ('11', '000000965812685', '家庭其他成员劳动能力', '3', '4', '0');
INSERT INTO `tb_erm_field` VALUES ('12', '000001534197634', '房屋情况', '3', '8', '0');
INSERT INTO `tb_erm_field` VALUES ('13', '000001997871548', '医疗支出', '3', '10', '0');
INSERT INTO `tb_erm_field` VALUES ('14', '000000727805062', '受灾情况', '3', '10', '0');
INSERT INTO `tb_erm_field` VALUES ('15', '000000540948804', '家庭变故', '3', '10', '0');
INSERT INTO `tb_erm_field` VALUES ('16', '000000782510813', '家庭住地', '3', '2', '0');
INSERT INTO `tb_erm_field` VALUES ('17', '000000677487012', '就学人口', '3', '6', '0');
INSERT INTO `tb_erm_field` VALUES ('18', '000002104672688', '学生本人消费情况', '3', '2', '0');
INSERT INTO `tb_erm_field` VALUES ('19', '000000347383007', '学生本人健康状况', '3', '6', '0');
INSERT INTO `tb_erm_field` VALUES ('20', '000001207122335', '政策性优抚', '4', '10', '0');

-- ----------------------------
-- Table structure for tb_erm_field_val
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_field_val`;
CREATE TABLE `tb_erm_field_val` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11) DEFAULT NULL COMMENT '字段ID关联tb_erm_field',
  `val_key` varchar(200) DEFAULT NULL COMMENT '选项code',
  `val_val` varchar(200) DEFAULT NULL COMMENT '选项值',
  `val_weight` double DEFAULT '0' COMMENT '权重',
  `flag` int(11) DEFAULT '0' COMMENT '-1为删除，0为公共显示的，其它为学校ID，表示学校创建的属性',
  PRIMARY KEY (`id`),
  KEY `FK_tb_erm_field_val` (`field_id`) USING BTREE,
  CONSTRAINT `tb_erm_field_val_ibfk_1` FOREIGN KEY (`field_id`) REFERENCES `tb_erm_field` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=325 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_erm_field_val
-- ----------------------------
INSERT INTO `tb_erm_field_val` VALUES ('4', '7', '000000404550625', '公司股东或高管、私营业主', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('5', '7', '000000590427638', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('6', '7', '000001288637759', '进城务工人员或合同制工作人员 ', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('7', '7', '000000429375695', '务农或临时务工 ', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('8', '7', '000001049471409', '因身体或其他原因无法就业、失踪（联）或去世', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('9', '8', '000000016691864', '公司股东或高管、私营业主、全职太太 ', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('10', '8', '000000613322163', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('11', '8', '000000281630549', '进城务工人员或合同制工作人员', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('12', '8', '000001736771189', '务农或临时务工 ', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('13', '8', '000001703101562', '因身体或其他原因无法就业、失踪（联）或去世', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('14', '9', '000001899733275', '身体健康且有一技之长 ', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('15', '9', '000001816190628', '身体健康但无一技之长', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('16', '9', '000000600708645', '3-4级伤残，或有一定劳动能力', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('17', '9', '000000224577250', '1-2级伤残', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('18', '9', '000000924096447', '完全丧失劳动能力、失踪（联）或去世', '10', '0');
INSERT INTO `tb_erm_field_val` VALUES ('19', '10', '000000218207598', '身体健康且有一技之长 ', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('20', '10', '000001088496691', '身体健康但无一技之长', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('21', '10', '000001591255193', '3-4级伤残，或有一定劳动能力', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('22', '10', '000000778999452', '1-2级伤残', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('23', '10', '000000866577969', '完全丧失劳动能力、失踪（联）或去世', '10', '0');
INSERT INTO `tb_erm_field_val` VALUES ('24', '11', '000001517676658', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('25', '11', '000001764186081', '其他家庭成员中部分有劳动能力或固定收入', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('26', '11', '000001477292453', '其他成员均无劳动能力或固定收入', '4', '0');
INSERT INTO `tb_erm_field_val` VALUES ('27', '12', '000001573493929', '其他房屋或两套及以上', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('28', '12', '000000624750648', '农村简易砖瓦房或城市廉租房、公租房', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('29', '12', '000000551260769', '农村简易房或城市筒子楼', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('30', '12', '000000078017318', '无房', '8', '0');
INSERT INTO `tb_erm_field_val` VALUES ('31', '13', '000001261435220', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('32', '13', '000001811499114', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('33', '13', '000001198934148', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '0');
INSERT INTO `tb_erm_field_val` VALUES ('34', '13', '000000431845010', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '0');
INSERT INTO `tb_erm_field_val` VALUES ('35', '14', '000002119301070', '近两年内未遭受自然灾害', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('36', '14', '000000983324801', '近两年内遭受一般自然灾害，影响家庭收入', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('37', '14', '000000180192003', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '0');
INSERT INTO `tb_erm_field_val` VALUES ('38', '14', '000000459766727', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '0');
INSERT INTO `tb_erm_field_val` VALUES ('39', '15', '000001338760868', '近两年内未出现家庭变故', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('40', '15', '000001677513791', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '0');
INSERT INTO `tb_erm_field_val` VALUES ('41', '15', '000000633419493', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '0');
INSERT INTO `tb_erm_field_val` VALUES ('42', '16', '000001460121713', '近两年家庭实际住地在县城及以上', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('43', '16', '000001995618718', '近两年家庭实际住地在乡镇及以下', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('44', '17', '000001702139562', '1人', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('45', '17', '000001835641730', '2人', '4', '0');
INSERT INTO `tb_erm_field_val` VALUES ('46', '17', '000000677410959', '3人', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('47', '18', '000001451475491', '一般', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('48', '18', '000002110156267', '较少', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('49', '18', '000002004783022', '很少', '2', '0');
INSERT INTO `tb_erm_field_val` VALUES ('50', '19', '000001004668044', '健康', '0', '0');
INSERT INTO `tb_erm_field_val` VALUES ('51', '19', '000001794666375', '较差', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('52', '19', '000001824420864', '很差', '6', '0');
INSERT INTO `tb_erm_field_val` VALUES ('53', '20', '000000593695320', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '0');
INSERT INTO `tb_erm_field_val` VALUES ('54', '20', '000000604817101', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '0');
INSERT INTO `tb_erm_field_val` VALUES ('55', '20', '000001841206406', '少数民族', '1', '0');
INSERT INTO `tb_erm_field_val` VALUES ('56', '20', '000001937731123', '农村独生子女或计划生育政策双女户', '1', '0');


-- ----------------------------
-- Table structure for tb_erm_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_role`;
CREATE TABLE `tb_erm_role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色信息';

-- ----------------------------
-- Records of tb_erm_role
-- ----------------------------
INSERT INTO `tb_erm_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `tb_erm_role` VALUES ('7', 'ROLE_AI');
INSERT INTO `tb_erm_role` VALUES ('2', 'ROLE_EB');
INSERT INTO `tb_erm_role` VALUES ('4', 'ROLE_OPER');
INSERT INTO `tb_erm_role` VALUES ('6', 'ROLE_PS');
INSERT INTO `tb_erm_role` VALUES ('3', 'ROLE_SCH');
INSERT INTO `tb_erm_role` VALUES ('5', 'ROLE_STU');

-- ----------------------------
-- Table structure for tb_erm_school
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_school`;
CREATE TABLE `tb_erm_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `eb_id` int(11) DEFAULT NULL,
  `type` char(1) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `seal` varchar(200) DEFAULT NULL COMMENT '学校印章地址',
  `creator` varchar(20) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `updator` varchar(20) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `school_seal` varchar(255) DEFAULT NULL COMMENT '学校公章',
  PRIMARY KEY (`id`),
  UNIQUE KEY `AK_Key_2` (`code`) USING BTREE,
  KEY `FK_Reference_15` (`eb_id`) USING BTREE,
  CONSTRAINT `tb_erm_school_ibfk_1` FOREIGN KEY (`eb_id`) REFERENCES `tb_erm_edubur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8 COMMENT='学校信息';

-- ----------------------------
-- Records of tb_erm_school
-- ----------------------------
INSERT INTO `tb_erm_school` VALUES ('61', '1', '3', '430821101', '慈利县岩泊渡镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('62', '1', '3', '430821102', '慈利县南山坪乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('63', '1', '3', '430821103', '慈利县甘堰土家族乡宜冲桥中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('64', '1', '3', '430821104', '慈利县甘堰土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('65', '1', '3', '430821105', '慈利县阳和土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('66', '1', '3', '430821106', '慈利县许家坊土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('67', '1', '3', '430821107', '慈利县金岩土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('68', '1', '3', '430821108', '慈利县溪口镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('69', '1', '3', '430821109', '慈利县洞溪乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('70', '1', '3', '430821110', '慈利县高桥镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('71', '1', '3', '430821111', '慈利县龙潭河金坪中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('72', '1', '3', '430821112', '慈利县龙潭河镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('73', '1', '3', '430821113', '慈利县二坊坪乡二坊坪中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('74', '1', '3', '430821114', '慈利县二坊坪乡景龙桥中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('75', '1', '3', '430821115', '慈利县零溪镇朝阳中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('76', '1', '3', '430821116', '慈利县零溪镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('77', '1', '3', '430821117', '慈利县苗市镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('78', '1', '3', '430821118', '慈利县广福桥镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('79', '1', '3', '430821119', '慈利县杨柳铺乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('80', '1', '3', '430821120', '慈利县东岳观镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('81', '1', '3', '430821121', '慈利县通津铺镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('82', '1', '3', '430821122', '慈利县三合镇庄塌中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('83', '1', '3', '430821123', '慈利县三合镇国太桥中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('84', '1', '3', '430821124', '慈利县杉木桥镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('85', '1', '3', '430821125', '慈利县象市镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('86', '1', '3', '430821126', '慈利县三合镇三合口中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('87', '1', '3', '430821127', '慈利县江垭镇中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('88', '1', '3', '430821128', '慈利县赵家岗土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('89', '1', '3', '430821129', '慈利县三官寺土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('90', '1', '3', '430821130', '慈利县高峰土家族乡中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('91', '1', '3', '430821131', '慈利县零阳镇城北中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('92', '1', '2', '430821132', '慈利县零阳镇第一完全小学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('93', '1', '2', '430821133', '慈利县金慈实验小学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('94', '1', '2', '430821134', '慈利县零阳镇双岗中心完小', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('95', '1', '2', '430821135', '慈利县零阳镇柳林完小', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('96', '1', '2', '430821136', '慈利县零阳镇北岗中心完小', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('97', '1', '3', '430821137', '慈利县零阳镇一鸣中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('98', '1', '3', '430821138', '慈利县零阳镇城西中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('99', '1', '3', '430821141', '慈利县特殊教育学校', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('100', '1', '3', '430821139', '慈利县零阳镇中心校', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('101', '1', '3', '430821140', '慈利县铄武武术学校', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('102', '1', '4', '430821201', '慈利县第一中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('103', '1', '4', '430821202', '慈利县第二中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('104', '1', '4', '430821203', '慈利县第三中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('105', '1', '4', '430821204', '慈利县第四中学', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('106', '1', '5', '430821301', '慈利县职业中专', null, 'eb', '2017-12-16', null, null, null);
INSERT INTO `tb_erm_school` VALUES ('107', '1', '5', '430821302', '慈利县教师进修学校', null, 'eb', '2017-12-16', null, null, null);
INSERT INTO `tb_erm_school` VALUES ('108', '1', '5', '430821303', '慈利县信息工程学校', null, 'eb', '2017-12-16', null, null, null);
INSERT INTO `tb_erm_school` VALUES ('109', '1', '1', '43082110100', '慈利县岩泊渡镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('110', '1', '1', '43082110200', '慈利县南山坪乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('111', '1', '1', '43082110300', '慈利县甘堰土家族乡宜冲桥中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('112', '1', '1', '43082110400', '慈利县甘堰土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('113', '1', '1', '43082110500', '慈利县阳和土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('114', '1', '1', '43082110600', '慈利县许家坊土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('115', '1', '1', '43082110700', '慈利县金岩土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('116', '1', '1', '43082110800', '慈利县溪口镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('117', '1', '1', '43082110900', '慈利县洞溪乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('118', '1', '1', '43082111000', '慈利县高桥镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('119', '1', '1', '43082111100', '慈利县龙潭河金坪中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('120', '1', '1', '43082111200', '慈利县龙潭河镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('121', '1', '1', '43082111300', '慈利县二坊坪乡二坊坪中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('122', '1', '1', '43082111400', '慈利县二坊坪乡景龙桥中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('123', '1', '1', '43082111500', '慈利县零溪镇朝阳中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('124', '1', '1', '43082111600', '慈利县零溪镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('125', '1', '1', '43082111700', '慈利县苗市镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('126', '1', '1', '43082111800', '慈利县广福桥镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('127', '1', '1', '43082111900', '慈利县杨柳铺乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('128', '1', '1', '43082112000', '慈利县东岳观镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('129', '1', '1', '43082112100', '慈利县通津铺镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('130', '1', '1', '43082112200', '慈利县三合镇庄塌中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('131', '1', '1', '43082112300', '慈利县三合镇国太桥中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('132', '1', '1', '43082112400', '慈利县杉木桥镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('133', '1', '1', '43082112500', '慈利县象市镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('134', '1', '1', '43082112600', '慈利县三合镇三合口中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('135', '1', '1', '43082112700', '慈利县江垭镇中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('136', '1', '1', '43082112800', '慈利县赵家岗土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('137', '1', '1', '43082112900', '慈利县三官寺土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('138', '1', '1', '43082113000', '慈利县高峰土家族乡中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('139', '1', '1', '43082113100', '慈利县零阳镇城北中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('140', '1', '1', '43082113200', '慈利县零阳镇第一完全小学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('141', '1', '1', '43082113300', '慈利县金慈实验小学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('142', '1', '1', '43082113400', '慈利县零阳镇双岗中心完小(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('143', '1', '1', '43082113500', '慈利县零阳镇柳林完小(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('144', '1', '1', '43082113600', '慈利县零阳镇北岗中心完小(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('145', '1', '1', '43082113700', '慈利县零阳镇一鸣中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('146', '1', '1', '43082113800', '慈利县零阳镇城西中学(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('147', '1', '1', '43082113900', '慈利县零阳镇中心校(附属)幼儿园', null, null, null, null, null, null);
INSERT INTO `tb_erm_school` VALUES ('148', '1', '1', '43082114000', '慈利县铄武武术学校(附属)幼儿园', null, null, null, null, null, null);
