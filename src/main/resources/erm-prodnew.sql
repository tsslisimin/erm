/*
Navicat MySQL Data Transfer

Source Server         : 120.79.184.156
Source Server Version : 50718
Source Host           : 120.79.184.156:3306
Source Database       : erm

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-09-05 13:42:32
*/

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
  KEY `FK_Reference_9` (`role_id`) USING BTREE,
  CONSTRAINT `tb_erm_admin_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `tb_erm_role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=821 DEFAULT CHARSET=utf8 COMMENT='管理员信息';

-- ----------------------------
-- Records of tb_erm_admin
-- ----------------------------
INSERT INTO `tb_erm_admin` VALUES ('1', '18163660089', 'admin', 'admin', '$2a$06$kXYpTwhkZhAIfxU1zdsdCeP9VzFUXZWOpmvpBVvX9VIF4B4UtRipG', '1', '2', '373416233@qq.com', '1', null, null, null, 'admin', '2018-09-04');

-- ----------------------------
-- Table structure for tb_erm_admin_notice
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户消息订阅表';

-- ----------------------------
-- Records of tb_erm_admin_notice
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_audit_log
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=787 DEFAULT CHARSET=utf8 COMMENT='学生资助审批日志表';

-- ----------------------------
-- Records of tb_erm_audit_log
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=394 DEFAULT CHARSET=utf8;

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
-- Table structure for tb_erm_edubur
-- ----------------------------
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

-- ----------------------------
-- Records of tb_erm_edubur
-- ----------------------------
INSERT INTO `tb_erm_edubur` VALUES ('1', '4310000', '慈利县教育局', '湖南省', '张家界市', null, null, null, null, null);

-- ----------------------------
-- Table structure for tb_erm_family
-- ----------------------------
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
  `archiveRelation` varchar(255) DEFAULT '' COMMENT '湖南省扶贫补贴明白折（建档立卡）人关系',
  `archiveAcount` varchar(200) DEFAULT '' COMMENT '湖南省扶贫补贴明白折（建档立卡）人账号',
  `supportName` varchar(255) DEFAULT '' COMMENT '学生资助卡姓名',
  `supportBankCard` varchar(255) DEFAULT '' COMMENT '学生资助卡银行账号',
  `helperName` varchar(255) DEFAULT '' COMMENT '帮扶人姓名',
  `helperWorkPlace` varchar(255) DEFAULT '' COMMENT '帮扶人单位',
  `helperPosition` varchar(255) DEFAULT '' COMMENT '帮扶人职位',
  `helperTel` varchar(255) DEFAULT '' COMMENT '帮扶人联系电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='家庭信息';

-- ----------------------------
-- Records of tb_erm_family
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8;

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
INSERT INTO `tb_erm_field` VALUES ('103', 'KAGxgtGD', '政策性优抚', '4', '10', '102');
INSERT INTO `tb_erm_field` VALUES ('104', 'hs8pTQvP', '学生本人健康状况', '3', '6', '102');
INSERT INTO `tb_erm_field` VALUES ('105', '8OoNCw8p', '学生本人消费情况', '3', '2', '102');
INSERT INTO `tb_erm_field` VALUES ('106', 'jjdL0kLD', '就学人口', '3', '6', '102');
INSERT INTO `tb_erm_field` VALUES ('107', '114UW03N', '家庭住地', '3', '2', '102');
INSERT INTO `tb_erm_field` VALUES ('108', '355kBEMf', '家庭变故', '3', '10', '102');
INSERT INTO `tb_erm_field` VALUES ('109', 'NGrIcpHl', '受灾情况', '3', '10', '102');
INSERT INTO `tb_erm_field` VALUES ('110', 'rjfpGtwR', '医疗支出', '3', '10', '102');
INSERT INTO `tb_erm_field` VALUES ('111', '0pi7Vf4C', '房屋情况', '3', '8', '102');
INSERT INTO `tb_erm_field` VALUES ('112', 'RIE9YHs9', '家庭其他成员劳动能力', '3', '4', '102');
INSERT INTO `tb_erm_field` VALUES ('113', 'Qset9GlV', '母亲劳动能力', '3', '10', '102');
INSERT INTO `tb_erm_field` VALUES ('114', 'nA8I6tKs', '父亲劳动能力', '3', '10', '102');
INSERT INTO `tb_erm_field` VALUES ('115', '8673W2cJ', '母亲职业', '3', '6', '102');
INSERT INTO `tb_erm_field` VALUES ('116', '43Lfmkzc', '父亲职业', '3', '6', '102');
INSERT INTO `tb_erm_field` VALUES ('117', '8iF9QRdo', '政策性优抚', '4', '10', '61');
INSERT INTO `tb_erm_field` VALUES ('118', 'uhOKnHdq', '学生本人健康状况', '3', '6', '61');
INSERT INTO `tb_erm_field` VALUES ('119', 'd997erey', '学生本人消费情况', '3', '2', '61');
INSERT INTO `tb_erm_field` VALUES ('120', 'erRXmRyT', '就学人口', '3', '6', '61');
INSERT INTO `tb_erm_field` VALUES ('121', 'Bl9UyO0i', '家庭住地', '3', '2', '61');
INSERT INTO `tb_erm_field` VALUES ('122', '7MVFO8ZO', '家庭变故', '3', '10', '61');
INSERT INTO `tb_erm_field` VALUES ('123', 'VUYenINg', '受灾情况', '3', '10', '61');
INSERT INTO `tb_erm_field` VALUES ('124', 'baBwID8w', '医疗支出', '3', '10', '61');
INSERT INTO `tb_erm_field` VALUES ('125', 'TXVwIlMO', '房屋情况', '3', '8', '61');
INSERT INTO `tb_erm_field` VALUES ('126', 'CDD8L5yu', '家庭其他成员劳动能力', '3', '4', '61');
INSERT INTO `tb_erm_field` VALUES ('127', 'MI71Vnov', '母亲劳动能力', '3', '10', '61');
INSERT INTO `tb_erm_field` VALUES ('128', 'cshUz9Xa', '父亲劳动能力', '3', '10', '61');
INSERT INTO `tb_erm_field` VALUES ('129', 'ByA5P1aa', '母亲职业', '3', '6', '61');
INSERT INTO `tb_erm_field` VALUES ('130', 'FDWkjIBI', '父亲职业', '3', '6', '61');
INSERT INTO `tb_erm_field` VALUES ('131', 'IizKjAdz', '政策性优抚', '4', '10', '103');
INSERT INTO `tb_erm_field` VALUES ('132', 'jJaRXGLg', '学生本人健康状况', '3', '6', '103');
INSERT INTO `tb_erm_field` VALUES ('133', 'b2aZq9lq', '学生本人消费情况', '3', '2', '103');
INSERT INTO `tb_erm_field` VALUES ('134', 'PqxVZiVI', '就学人口', '3', '6', '103');
INSERT INTO `tb_erm_field` VALUES ('135', 'ZD3m2g6Q', '家庭住地', '3', '2', '103');
INSERT INTO `tb_erm_field` VALUES ('136', 'Pbb56don', '家庭变故', '3', '10', '103');
INSERT INTO `tb_erm_field` VALUES ('137', 'U6wox5L5', '受灾情况', '3', '10', '103');
INSERT INTO `tb_erm_field` VALUES ('138', 'v7EkJP3T', '医疗支出', '3', '10', '103');
INSERT INTO `tb_erm_field` VALUES ('139', 'hlrXyo1U', '房屋情况', '3', '8', '103');
INSERT INTO `tb_erm_field` VALUES ('140', 'tRch7cHh', '家庭其他成员劳动能力', '3', '4', '103');
INSERT INTO `tb_erm_field` VALUES ('141', 'qYorugxk', '母亲劳动能力', '3', '10', '103');
INSERT INTO `tb_erm_field` VALUES ('142', 'Qy2ZI9wR', '父亲劳动能力', '3', '10', '103');
INSERT INTO `tb_erm_field` VALUES ('143', 'La3eQWda', '母亲职业', '3', '6', '103');
INSERT INTO `tb_erm_field` VALUES ('144', 'MyrO1YDB', '父亲职业', '3', '6', '103');
INSERT INTO `tb_erm_field` VALUES ('145', 'A6TTG1yW', '政策性优抚', '4', '10', '104');
INSERT INTO `tb_erm_field` VALUES ('146', 'GARRysdq', '学生本人健康状况', '3', '6', '104');
INSERT INTO `tb_erm_field` VALUES ('147', 'E6o7Ob6u', '学生本人消费情况', '3', '2', '104');
INSERT INTO `tb_erm_field` VALUES ('148', 'pIq85iZl', '就学人口', '3', '6', '104');
INSERT INTO `tb_erm_field` VALUES ('149', '4Q2B19MQ', '家庭住地', '3', '2', '104');
INSERT INTO `tb_erm_field` VALUES ('150', 'fupjnGZl', '家庭变故', '3', '10', '104');
INSERT INTO `tb_erm_field` VALUES ('151', 'onXDYWo9', '受灾情况', '3', '10', '104');
INSERT INTO `tb_erm_field` VALUES ('152', '9TAb0Aav', '医疗支出', '3', '10', '104');
INSERT INTO `tb_erm_field` VALUES ('153', '1wkoLzHK', '房屋情况', '3', '8', '104');
INSERT INTO `tb_erm_field` VALUES ('154', 'S2XHmPi0', '家庭其他成员劳动能力', '3', '4', '104');
INSERT INTO `tb_erm_field` VALUES ('155', 'aFt70VBz', '母亲劳动能力', '3', '10', '104');
INSERT INTO `tb_erm_field` VALUES ('156', '62iZoPmZ', '父亲劳动能力', '3', '10', '104');
INSERT INTO `tb_erm_field` VALUES ('157', '4kzeozJK', '母亲职业', '3', '6', '104');
INSERT INTO `tb_erm_field` VALUES ('158', 'NiP54vUc', '父亲职业', '3', '6', '104');
INSERT INTO `tb_erm_field` VALUES ('159', 'e8Sz1zL0', '政策性优抚', '4', '10', '145');
INSERT INTO `tb_erm_field` VALUES ('160', 'zAvDpUl2', '学生本人健康状况', '3', '6', '145');
INSERT INTO `tb_erm_field` VALUES ('161', 'tFUNbRPw', '学生本人消费情况', '3', '2', '145');
INSERT INTO `tb_erm_field` VALUES ('162', 't6ri0aqD', '就学人口', '3', '6', '145');
INSERT INTO `tb_erm_field` VALUES ('163', '86lv9jBv', '家庭住地', '3', '2', '145');
INSERT INTO `tb_erm_field` VALUES ('164', 'ZYJzChgg', '家庭变故', '3', '10', '145');
INSERT INTO `tb_erm_field` VALUES ('165', 'agBTljIV', '受灾情况', '3', '10', '145');
INSERT INTO `tb_erm_field` VALUES ('166', 'usR4Wian', '医疗支出', '3', '10', '145');
INSERT INTO `tb_erm_field` VALUES ('167', 'L72y0qtL', '房屋情况', '3', '8', '145');
INSERT INTO `tb_erm_field` VALUES ('168', 'oF8QSicS', '家庭其他成员劳动能力', '3', '4', '145');
INSERT INTO `tb_erm_field` VALUES ('169', 'NgpwPweX', '母亲劳动能力', '3', '10', '145');
INSERT INTO `tb_erm_field` VALUES ('170', 'pCgVewr3', '父亲劳动能力', '3', '10', '145');
INSERT INTO `tb_erm_field` VALUES ('171', 'AR38yJ1v', '母亲职业', '3', '6', '145');
INSERT INTO `tb_erm_field` VALUES ('172', 'cKOCKwVR', '父亲职业', '3', '6', '145');
INSERT INTO `tb_erm_field` VALUES ('173', 'eCdkKxhk', '政策性优抚', '4', '10', '106');
INSERT INTO `tb_erm_field` VALUES ('174', 'gl60bkeW', '学生本人健康状况', '3', '6', '106');
INSERT INTO `tb_erm_field` VALUES ('175', '3QrOozVd', '学生本人消费情况', '3', '2', '106');
INSERT INTO `tb_erm_field` VALUES ('176', 'jPg3t4Ky', '就学人口', '3', '6', '106');
INSERT INTO `tb_erm_field` VALUES ('177', 'EE9YCGrT', '家庭住地', '3', '2', '106');
INSERT INTO `tb_erm_field` VALUES ('178', 'XJDXmvH9', '家庭变故', '3', '10', '106');
INSERT INTO `tb_erm_field` VALUES ('179', 'Jgd37FUR', '受灾情况', '3', '10', '106');
INSERT INTO `tb_erm_field` VALUES ('180', '6D3bQmyx', '医疗支出', '3', '10', '106');
INSERT INTO `tb_erm_field` VALUES ('181', 'E9PA5y20', '房屋情况', '3', '8', '106');
INSERT INTO `tb_erm_field` VALUES ('182', '4MIbW6lZ', '家庭其他成员劳动能力', '3', '4', '106');
INSERT INTO `tb_erm_field` VALUES ('183', 'VIWtDuxi', '母亲劳动能力', '3', '10', '106');
INSERT INTO `tb_erm_field` VALUES ('184', 'Eq2YEdUQ', '父亲劳动能力', '3', '10', '106');
INSERT INTO `tb_erm_field` VALUES ('185', 'YgDAlO1j', '母亲职业', '3', '6', '106');
INSERT INTO `tb_erm_field` VALUES ('186', 'Z4Ln5KGm', '父亲职业', '3', '6', '106');
INSERT INTO `tb_erm_field` VALUES ('187', '9QTTAUWa', '政策性优抚', '4', '10', '150');
INSERT INTO `tb_erm_field` VALUES ('188', 'my7d6mHN', '学生本人健康状况', '3', '6', '150');
INSERT INTO `tb_erm_field` VALUES ('189', 'pZ6JNOcC', '学生本人消费情况', '3', '2', '150');
INSERT INTO `tb_erm_field` VALUES ('190', '1ha4d5Z3', '就学人口', '3', '6', '150');
INSERT INTO `tb_erm_field` VALUES ('191', 'Kn681Hiq', '家庭住地', '3', '2', '150');
INSERT INTO `tb_erm_field` VALUES ('192', '7N6KXJzu', '家庭变故', '3', '10', '150');
INSERT INTO `tb_erm_field` VALUES ('193', '8oKezy1Q', '受灾情况', '3', '10', '150');
INSERT INTO `tb_erm_field` VALUES ('194', 'p6gfjqex', '医疗支出', '3', '10', '150');
INSERT INTO `tb_erm_field` VALUES ('195', 'pEsWS8kP', '房屋情况', '3', '8', '150');
INSERT INTO `tb_erm_field` VALUES ('196', 'mTOfh5Ik', '家庭其他成员劳动能力', '3', '4', '150');
INSERT INTO `tb_erm_field` VALUES ('197', 'BVpiZY0p', '母亲劳动能力', '3', '10', '150');
INSERT INTO `tb_erm_field` VALUES ('198', '7VRO5arX', '父亲劳动能力', '3', '10', '150');
INSERT INTO `tb_erm_field` VALUES ('199', 'lGD8vY7d', '母亲职业', '3', '6', '150');
INSERT INTO `tb_erm_field` VALUES ('200', 'DWbSa0Hf', '父亲职业', '3', '6', '150');
INSERT INTO `tb_erm_field` VALUES ('201', '3kHv9FDq', '政策性优抚', '4', '10', '105');
INSERT INTO `tb_erm_field` VALUES ('202', 'LOa1CL6a', '学生本人健康状况', '3', '6', '105');
INSERT INTO `tb_erm_field` VALUES ('203', '6yzaYB13', '学生本人消费情况', '3', '2', '105');
INSERT INTO `tb_erm_field` VALUES ('204', 'cPHAOr7l', '就学人口', '3', '6', '105');
INSERT INTO `tb_erm_field` VALUES ('205', 'Y5gFIsh4', '家庭住地', '3', '2', '105');
INSERT INTO `tb_erm_field` VALUES ('206', 'aIipZCOf', '家庭变故', '3', '10', '105');
INSERT INTO `tb_erm_field` VALUES ('207', 'JT3NDrIb', '受灾情况', '3', '10', '105');
INSERT INTO `tb_erm_field` VALUES ('208', 'l4Qt9YYc', '医疗支出', '3', '10', '105');
INSERT INTO `tb_erm_field` VALUES ('209', 'E4Pbh2ED', '房屋情况', '3', '8', '105');
INSERT INTO `tb_erm_field` VALUES ('210', 'izdTEz4h', '家庭其他成员劳动能力', '3', '4', '105');
INSERT INTO `tb_erm_field` VALUES ('211', 'lXHYthAF', '母亲劳动能力', '3', '10', '105');
INSERT INTO `tb_erm_field` VALUES ('212', 'ZFSTSA21', '父亲劳动能力', '3', '10', '105');
INSERT INTO `tb_erm_field` VALUES ('213', 'IWCet30i', '母亲职业', '3', '6', '105');
INSERT INTO `tb_erm_field` VALUES ('214', 'xKZvcRQo', '父亲职业', '3', '6', '105');
INSERT INTO `tb_erm_field` VALUES ('215', 'M9GkHE6F', '政策性优抚', '4', '10', '107');
INSERT INTO `tb_erm_field` VALUES ('216', 'MfNQlPCP', '学生本人健康状况', '3', '6', '107');
INSERT INTO `tb_erm_field` VALUES ('217', 'VPc83Bc3', '学生本人消费情况', '3', '2', '107');
INSERT INTO `tb_erm_field` VALUES ('218', 'o0Qb70HU', '就学人口', '3', '6', '107');
INSERT INTO `tb_erm_field` VALUES ('219', '2m4WGTxb', '家庭住地', '3', '2', '107');
INSERT INTO `tb_erm_field` VALUES ('220', 'mhw5fftk', '家庭变故', '3', '10', '107');
INSERT INTO `tb_erm_field` VALUES ('221', 'GfiGTlIW', '受灾情况', '3', '10', '107');
INSERT INTO `tb_erm_field` VALUES ('222', '8TAtJPr8', '医疗支出', '3', '10', '107');
INSERT INTO `tb_erm_field` VALUES ('223', 'Oc45uNxZ', '房屋情况', '3', '8', '107');
INSERT INTO `tb_erm_field` VALUES ('224', 'RvK4XZaf', '家庭其他成员劳动能力', '3', '4', '107');
INSERT INTO `tb_erm_field` VALUES ('225', 'M7aW1Sn4', '母亲劳动能力', '3', '10', '107');
INSERT INTO `tb_erm_field` VALUES ('226', 'eAmN26RH', '父亲劳动能力', '3', '10', '107');
INSERT INTO `tb_erm_field` VALUES ('227', 'Ioh3hrFM', '母亲职业', '3', '6', '107');
INSERT INTO `tb_erm_field` VALUES ('228', 'DCOg82Qy', '父亲职业', '3', '6', '107');
INSERT INTO `tb_erm_field` VALUES ('229', 'VT1Xuipz', '政策性优抚', '4', '10', '135');
INSERT INTO `tb_erm_field` VALUES ('230', 'IcNZxg41', '学生本人健康状况', '3', '6', '135');
INSERT INTO `tb_erm_field` VALUES ('231', '8ruSkbsI', '学生本人消费情况', '3', '2', '135');
INSERT INTO `tb_erm_field` VALUES ('232', 'Gd9RtU0N', '就学人口', '3', '6', '135');
INSERT INTO `tb_erm_field` VALUES ('233', 'DFU1uKL8', '家庭住地', '3', '2', '135');
INSERT INTO `tb_erm_field` VALUES ('234', '906FiWAq', '家庭变故', '3', '10', '135');
INSERT INTO `tb_erm_field` VALUES ('235', 'N4PDGACQ', '受灾情况', '3', '10', '135');
INSERT INTO `tb_erm_field` VALUES ('236', 'ubvTG7mw', '医疗支出', '3', '10', '135');
INSERT INTO `tb_erm_field` VALUES ('237', 'A48GSJg2', '房屋情况', '3', '8', '135');
INSERT INTO `tb_erm_field` VALUES ('238', 'MQWrPWFo', '家庭其他成员劳动能力', '3', '4', '135');
INSERT INTO `tb_erm_field` VALUES ('239', 'hoNe13WB', '母亲劳动能力', '3', '10', '135');
INSERT INTO `tb_erm_field` VALUES ('240', 'dINwgY50', '父亲劳动能力', '3', '10', '135');
INSERT INTO `tb_erm_field` VALUES ('241', '7SYbl6XM', '母亲职业', '3', '6', '135');
INSERT INTO `tb_erm_field` VALUES ('242', 'UCioDAuD', '父亲职业', '3', '6', '135');
INSERT INTO `tb_erm_field` VALUES ('243', '5bOkB677', '政策性优抚', '4', '10', '109');
INSERT INTO `tb_erm_field` VALUES ('244', 'Hj4nWzMp', '学生本人健康状况', '3', '6', '109');
INSERT INTO `tb_erm_field` VALUES ('245', 'fuNSYX9L', '学生本人消费情况', '3', '2', '109');
INSERT INTO `tb_erm_field` VALUES ('246', 'AEEFJHDk', '就学人口', '3', '6', '109');
INSERT INTO `tb_erm_field` VALUES ('247', '4tLzD6QT', '家庭住地', '3', '2', '109');
INSERT INTO `tb_erm_field` VALUES ('248', 'iIkdLJe8', '家庭变故', '3', '10', '109');
INSERT INTO `tb_erm_field` VALUES ('249', 'boSDQJOS', '受灾情况', '3', '10', '109');
INSERT INTO `tb_erm_field` VALUES ('250', 'leHKnHQR', '医疗支出', '3', '10', '109');
INSERT INTO `tb_erm_field` VALUES ('251', 'IrQeB7To', '房屋情况', '3', '8', '109');
INSERT INTO `tb_erm_field` VALUES ('252', 'JYyk2EWY', '家庭其他成员劳动能力', '3', '4', '109');
INSERT INTO `tb_erm_field` VALUES ('253', 'Hf1TtnXe', '母亲劳动能力', '3', '10', '109');
INSERT INTO `tb_erm_field` VALUES ('254', '0f9DGfAg', '父亲劳动能力', '3', '10', '109');
INSERT INTO `tb_erm_field` VALUES ('255', 'VBxIR8hO', '母亲职业', '3', '6', '109');
INSERT INTO `tb_erm_field` VALUES ('256', 'DsfN0zrr', '父亲职业', '3', '6', '109');
INSERT INTO `tb_erm_field` VALUES ('257', 'oZXqitiW', '政策性优抚', '4', '10', '87');
INSERT INTO `tb_erm_field` VALUES ('258', 'Y04iAFMY', '学生本人健康状况', '3', '6', '87');
INSERT INTO `tb_erm_field` VALUES ('259', 'KpuDmEDT', '学生本人消费情况', '3', '2', '87');
INSERT INTO `tb_erm_field` VALUES ('260', '5k2eVYIY', '就学人口', '3', '6', '87');
INSERT INTO `tb_erm_field` VALUES ('261', '1EheQcCy', '家庭住地', '3', '2', '87');
INSERT INTO `tb_erm_field` VALUES ('262', 'jqO0MPgg', '家庭变故', '3', '10', '87');
INSERT INTO `tb_erm_field` VALUES ('263', 'ybOcabKJ', '受灾情况', '3', '10', '87');
INSERT INTO `tb_erm_field` VALUES ('264', '2Ozl9DQz', '医疗支出', '3', '10', '87');
INSERT INTO `tb_erm_field` VALUES ('265', '97zIyzqB', '房屋情况', '3', '8', '87');
INSERT INTO `tb_erm_field` VALUES ('266', '93RyxBHS', '家庭其他成员劳动能力', '3', '4', '87');
INSERT INTO `tb_erm_field` VALUES ('267', 'Njp71uvE', '母亲劳动能力', '3', '10', '87');
INSERT INTO `tb_erm_field` VALUES ('268', 'zGW5dnFE', '父亲劳动能力', '3', '10', '87');
INSERT INTO `tb_erm_field` VALUES ('269', 'IM12hRYF', '母亲职业', '3', '6', '87');
INSERT INTO `tb_erm_field` VALUES ('270', 'qBzbxGRB', '父亲职业', '3', '6', '87');

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
) ENGINE=InnoDB AUTO_INCREMENT=961 DEFAULT CHARSET=utf8;

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
INSERT INTO `tb_erm_field_val` VALUES ('325', '103', 'kS5TGSD2', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '102');
INSERT INTO `tb_erm_field_val` VALUES ('326', '103', 'oGHxL9NZ', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('327', '103', 'h09z6XWS', '少数民族', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('328', '103', 'HiH8Tosn', '农村独生子女或计划生育政策双女户', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('329', '104', 'fgpa9C8t', '健康', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('330', '104', 'u581X24n', '较差', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('331', '104', '7ssUhrC0', '很差', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('332', '105', 'hIXxa7Tk', '一般', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('333', '105', 'daaJE8ZT', '较少', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('334', '105', 'KkvRE7Dx', '很少', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('335', '106', 'YKXkkbBs', '1人', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('336', '106', 'cyh9Kbpf', '2人', '4', '102');
INSERT INTO `tb_erm_field_val` VALUES ('337', '106', 'JHzGOloc', '3人', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('338', '107', 'ngERI1vX', '近两年家庭实际住地在县城及以上', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('339', '107', 'RnOQK2LB', '近两年家庭实际住地在乡镇及以下', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('340', '108', 'Q2vrsBqu', '近两年内未出现家庭变故', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('341', '108', 'uY0V0up5', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '102');
INSERT INTO `tb_erm_field_val` VALUES ('342', '108', '7EqDIKne', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '102');
INSERT INTO `tb_erm_field_val` VALUES ('343', '109', 'toaP8kS0', '近两年内未遭受自然灾害', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('344', '109', 'ajpO5vZM', '近两年内遭受一般自然灾害，影响家庭收入', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('345', '109', 'xvVVVI6F', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '102');
INSERT INTO `tb_erm_field_val` VALUES ('346', '109', 'bGyvnD4W', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '102');
INSERT INTO `tb_erm_field_val` VALUES ('347', '110', 'KGbCt3gO', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('348', '110', 'KnC7TtbE', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('349', '110', 'Iu0DjVVH', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '102');
INSERT INTO `tb_erm_field_val` VALUES ('350', '110', 'aYd8FBvO', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '102');
INSERT INTO `tb_erm_field_val` VALUES ('351', '111', 'C26MWvcQ', '其他房屋或两套及以上', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('352', '111', 'aul3us1m', '农村简易砖瓦房或城市廉租房、公租房', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('353', '111', 'UQwNYPAI', '农村简易房或城市筒子楼', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('354', '111', 'oK1oB0Mn', '无房', '8', '102');
INSERT INTO `tb_erm_field_val` VALUES ('355', '112', 'llelJpMz', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('356', '112', 'Gg6ZAGzP', '其他家庭成员中部分有劳动能力或固定收入', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('357', '112', 'uSglU9hk', '其他成员均无劳动能力或固定收入', '4', '102');
INSERT INTO `tb_erm_field_val` VALUES ('358', '113', 'rDvz44PL', '身体健康且有一技之长 ', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('359', '113', 'MuyTrxOj', '身体健康但无一技之长', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('360', '113', 'nxiKlj20', '3-4级伤残，或有一定劳动能力', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('361', '113', 'PwnqRVB8', '1-2级伤残', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('362', '113', 'iaLC9GGl', '完全丧失劳动能力、失踪（联）或去世', '10', '102');
INSERT INTO `tb_erm_field_val` VALUES ('363', '114', 'yiye5Vbx', '身体健康且有一技之长 ', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('364', '114', 'G9FBI0dI', '身体健康但无一技之长', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('365', '114', 'ebHgVggy', '3-4级伤残，或有一定劳动能力', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('366', '114', '0UEHsqS2', '1-2级伤残', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('367', '114', 'XxKqqgqv', '完全丧失劳动能力、失踪（联）或去世', '10', '102');
INSERT INTO `tb_erm_field_val` VALUES ('368', '115', 'oLY00YBF', '公司股东或高管、私营业主、全职太太 ', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('369', '115', '6O176JZ7', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('370', '115', 'DVVelzYz', '进城务工人员或合同制工作人员', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('371', '115', 'uvtN7FBZ', '务农或临时务工 ', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('372', '115', 'pKTJKzaJ', '因身体或其他原因无法就业、失踪（联）或去世', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('373', '116', '6zqUmSdY', '公司股东或高管、私营业主', '0', '102');
INSERT INTO `tb_erm_field_val` VALUES ('374', '116', 'FyyIlT4R', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '102');
INSERT INTO `tb_erm_field_val` VALUES ('375', '116', 'LIGthevM', '进城务工人员或合同制工作人员 ', '2', '102');
INSERT INTO `tb_erm_field_val` VALUES ('376', '116', '4sEO9HTn', '务农或临时务工 ', '3', '102');
INSERT INTO `tb_erm_field_val` VALUES ('377', '116', 'MYOL1bWZ', '因身体或其他原因无法就业、失踪（联）或去世', '6', '102');
INSERT INTO `tb_erm_field_val` VALUES ('378', '117', 'ABqGI4Y4', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '61');
INSERT INTO `tb_erm_field_val` VALUES ('379', '117', '08aAVtoi', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('380', '117', 'OfGxpznU', '少数民族', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('381', '117', 'G4qUf92W', '农村独生子女或计划生育政策双女户', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('382', '118', 'aPfqR41q', '健康', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('383', '118', '4JgPkKVu', '较差', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('384', '118', 'qMXWnhrw', '很差', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('385', '119', '9ksWOOUp', '一般', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('386', '119', 'M3QqXOxP', '较少', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('387', '119', 'wDpOwRbp', '很少', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('388', '120', 'A1pTIqQL', '1人', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('389', '120', '1R1Wix7U', '2人', '4', '61');
INSERT INTO `tb_erm_field_val` VALUES ('390', '120', '5Z6TFmxd', '3人', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('391', '121', 'vS2YxGlj', '近两年家庭实际住地在县城及以上', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('392', '121', '48FTbGo3', '近两年家庭实际住地在乡镇及以下', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('393', '122', 'uNvdmmr8', '近两年内未出现家庭变故', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('394', '122', 'A1fvMY1j', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '61');
INSERT INTO `tb_erm_field_val` VALUES ('395', '122', 'XVMqz1J3', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '61');
INSERT INTO `tb_erm_field_val` VALUES ('396', '123', 'l7YLZM5s', '近两年内未遭受自然灾害', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('397', '123', 'hduDau8n', '近两年内遭受一般自然灾害，影响家庭收入', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('398', '123', 'n6mIhOuQ', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '61');
INSERT INTO `tb_erm_field_val` VALUES ('399', '123', 'TfdJ8Du3', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '61');
INSERT INTO `tb_erm_field_val` VALUES ('400', '124', 'P70z8eZp', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('401', '124', 'ElM5CMjh', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('402', '124', 'lXeOOM6S', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '61');
INSERT INTO `tb_erm_field_val` VALUES ('403', '124', 'JuOfIzTG', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '61');
INSERT INTO `tb_erm_field_val` VALUES ('404', '125', 'hBtXvSMC', '其他房屋或两套及以上', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('405', '125', 'FijKUxu3', '农村简易砖瓦房或城市廉租房、公租房', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('406', '125', 'OSbXIk6H', '农村简易房或城市筒子楼', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('407', '125', 'yqp8gwcf', '无房', '8', '61');
INSERT INTO `tb_erm_field_val` VALUES ('408', '126', 'w6IttlD1', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('409', '126', 'RDbNosS6', '其他家庭成员中部分有劳动能力或固定收入', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('410', '126', 'GRxnVXty', '其他成员均无劳动能力或固定收入', '4', '61');
INSERT INTO `tb_erm_field_val` VALUES ('411', '127', 'cl7HpTLe', '身体健康且有一技之长 ', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('412', '127', 'U7EfH48J', '身体健康但无一技之长', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('413', '127', 'O9b9DZcB', '3-4级伤残，或有一定劳动能力', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('414', '127', '7NdinkNs', '1-2级伤残', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('415', '127', 'Z1ZAJD6Y', '完全丧失劳动能力、失踪（联）或去世', '10', '61');
INSERT INTO `tb_erm_field_val` VALUES ('416', '128', '4mgbN9eq', '身体健康且有一技之长 ', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('417', '128', 'ZdDof2cs', '身体健康但无一技之长', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('418', '128', 'RxFWPlmu', '3-4级伤残，或有一定劳动能力', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('419', '128', 'TPozjcEl', '1-2级伤残', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('420', '128', 'I6wShrG5', '完全丧失劳动能力、失踪（联）或去世', '10', '61');
INSERT INTO `tb_erm_field_val` VALUES ('421', '129', 'DYure4Ul', '公司股东或高管、私营业主、全职太太 ', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('422', '129', '51vxuaSY', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('423', '129', 'rB7yY7pU', '进城务工人员或合同制工作人员', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('424', '129', 'BiSVCyys', '务农或临时务工 ', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('425', '129', 'p5GreZfv', '因身体或其他原因无法就业、失踪（联）或去世', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('426', '130', 'tCGmlSNn', '公司股东或高管、私营业主', '0', '61');
INSERT INTO `tb_erm_field_val` VALUES ('427', '130', '75ROkakk', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '61');
INSERT INTO `tb_erm_field_val` VALUES ('428', '130', 'lBuikKh0', '进城务工人员或合同制工作人员 ', '2', '61');
INSERT INTO `tb_erm_field_val` VALUES ('429', '130', 'Bn9h16xw', '务农或临时务工 ', '3', '61');
INSERT INTO `tb_erm_field_val` VALUES ('430', '130', 'oi6pubRD', '因身体或其他原因无法就业、失踪（联）或去世', '6', '61');
INSERT INTO `tb_erm_field_val` VALUES ('431', '131', 'h94TjbjI', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '103');
INSERT INTO `tb_erm_field_val` VALUES ('432', '131', 'MVmsyWFm', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('433', '131', 'uzHPSbbc', '少数民族', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('434', '131', 'mqAwLaXP', '农村独生子女或计划生育政策双女户', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('435', '132', 'hW85WRNa', '健康', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('436', '132', 'OmwcOd6N', '较差', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('437', '132', 'AYY1qS3R', '很差', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('438', '133', '5qmCRS15', '一般', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('439', '133', 'KeGAj01w', '较少', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('440', '133', 'ai6pV5hA', '很少', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('441', '134', '3EjSmfbL', '1人', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('442', '134', '7KBW6bxj', '2人', '4', '103');
INSERT INTO `tb_erm_field_val` VALUES ('443', '134', 'GdZkAbZb', '3人', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('444', '135', 'lshENbAa', '近两年家庭实际住地在县城及以上', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('445', '135', 'lhwpps50', '近两年家庭实际住地在乡镇及以下', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('446', '136', 'xUq17WWg', '近两年内未出现家庭变故', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('447', '136', 'pyiFLCi4', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '103');
INSERT INTO `tb_erm_field_val` VALUES ('448', '136', 'UofER2UL', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '103');
INSERT INTO `tb_erm_field_val` VALUES ('449', '137', 'dHuICvHn', '近两年内未遭受自然灾害', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('450', '137', 'T3UsFoVe', '近两年内遭受一般自然灾害，影响家庭收入', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('451', '137', 'Kq6Fpwwy', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '103');
INSERT INTO `tb_erm_field_val` VALUES ('452', '137', '4FonlT1x', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '103');
INSERT INTO `tb_erm_field_val` VALUES ('453', '138', 'HWpJ4yCM', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('454', '138', 'w4OeeJh7', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('455', '138', 'LaMM6hn2', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '103');
INSERT INTO `tb_erm_field_val` VALUES ('456', '138', 'Ini13siS', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '103');
INSERT INTO `tb_erm_field_val` VALUES ('457', '139', 'zUqZT8rR', '其他房屋或两套及以上', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('458', '139', '8h9GiuWs', '农村简易砖瓦房或城市廉租房、公租房', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('459', '139', 'lUweKN9J', '农村简易房或城市筒子楼', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('460', '139', 'Z9aPOupW', '无房', '8', '103');
INSERT INTO `tb_erm_field_val` VALUES ('461', '140', 'zANHheyj', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('462', '140', 'l5CaEpPs', '其他家庭成员中部分有劳动能力或固定收入', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('463', '140', 'oVfmuPV7', '其他成员均无劳动能力或固定收入', '4', '103');
INSERT INTO `tb_erm_field_val` VALUES ('464', '141', 'm22p0cKL', '身体健康且有一技之长 ', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('465', '141', 'f2bFN2Dn', '身体健康但无一技之长', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('466', '141', 'nSMmWBQz', '3-4级伤残，或有一定劳动能力', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('467', '141', 'aqckjAIc', '1-2级伤残', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('468', '141', 'yIizuPXJ', '完全丧失劳动能力、失踪（联）或去世', '10', '103');
INSERT INTO `tb_erm_field_val` VALUES ('469', '142', '8OI0YNRF', '身体健康且有一技之长 ', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('470', '142', 'Byaj1ZFa', '身体健康但无一技之长', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('471', '142', 'kWGOlRiY', '3-4级伤残，或有一定劳动能力', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('472', '142', 'wlkx3SIr', '1-2级伤残', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('473', '142', '4EJ1Xoek', '完全丧失劳动能力、失踪（联）或去世', '10', '103');
INSERT INTO `tb_erm_field_val` VALUES ('474', '143', '1qAqjv0d', '公司股东或高管、私营业主、全职太太 ', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('475', '143', 'bicXOtDv', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('476', '143', 'DLMFZG99', '进城务工人员或合同制工作人员', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('477', '143', 'YdcfWJmP', '务农或临时务工 ', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('478', '143', 'xW6n1ZUR', '因身体或其他原因无法就业、失踪（联）或去世', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('479', '144', 'd2KVwLHa', '公司股东或高管、私营业主', '0', '103');
INSERT INTO `tb_erm_field_val` VALUES ('480', '144', 'oHllvkrS', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '103');
INSERT INTO `tb_erm_field_val` VALUES ('481', '144', 'tZaZ9TD9', '进城务工人员或合同制工作人员 ', '2', '103');
INSERT INTO `tb_erm_field_val` VALUES ('482', '144', 'KS2DJZw8', '务农或临时务工 ', '3', '103');
INSERT INTO `tb_erm_field_val` VALUES ('483', '144', 'fFZOYTRz', '因身体或其他原因无法就业、失踪（联）或去世', '6', '103');
INSERT INTO `tb_erm_field_val` VALUES ('484', '145', 'HYYLZTBZ', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '104');
INSERT INTO `tb_erm_field_val` VALUES ('485', '145', 'qdqXEKLs', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('486', '145', '6WiicBjg', '少数民族', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('487', '145', 'oRSNDYME', '农村独生子女或计划生育政策双女户', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('488', '146', 'UHGHyUiu', '健康', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('489', '146', 'sFUgWQnp', '较差', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('490', '146', 'qAu5Td9A', '很差', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('491', '147', 'ifnHxK55', '一般', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('492', '147', 'q1ClyoY8', '较少', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('493', '147', 'm2HIlRsS', '很少', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('494', '148', '1CginY37', '1人', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('495', '148', 'lcUDZRoa', '2人', '4', '104');
INSERT INTO `tb_erm_field_val` VALUES ('496', '148', 'ANaw0sKi', '3人', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('497', '149', 'iX4kYzB4', '近两年家庭实际住地在县城及以上', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('498', '149', 'tgxUACpz', '近两年家庭实际住地在乡镇及以下', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('499', '150', 'xaAMnCLg', '近两年内未出现家庭变故', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('500', '150', 'Ty95Xom1', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '104');
INSERT INTO `tb_erm_field_val` VALUES ('501', '150', 'e52vqU1T', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '104');
INSERT INTO `tb_erm_field_val` VALUES ('502', '151', 'Elk59TIh', '近两年内未遭受自然灾害', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('503', '151', 'CC8X14OJ', '近两年内遭受一般自然灾害，影响家庭收入', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('504', '151', 'Bi5nbaAi', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '104');
INSERT INTO `tb_erm_field_val` VALUES ('505', '151', '6gYU6QW8', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '104');
INSERT INTO `tb_erm_field_val` VALUES ('506', '152', 'NjiSRcYF', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('507', '152', 'AsNAft5l', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('508', '152', 'PIJ87BS8', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '104');
INSERT INTO `tb_erm_field_val` VALUES ('509', '152', '53WEquIU', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '104');
INSERT INTO `tb_erm_field_val` VALUES ('510', '153', 'eoQII4lG', '其他房屋或两套及以上', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('511', '153', 'qjlWfwFi', '农村简易砖瓦房或城市廉租房、公租房', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('512', '153', 'OVa3lk6G', '农村简易房或城市筒子楼', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('513', '153', 'SuYFmaBA', '无房', '8', '104');
INSERT INTO `tb_erm_field_val` VALUES ('514', '154', '3kS4tafr', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('515', '154', 'vGv2dgIx', '其他家庭成员中部分有劳动能力或固定收入', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('516', '154', 'QjAywxRy', '其他成员均无劳动能力或固定收入', '4', '104');
INSERT INTO `tb_erm_field_val` VALUES ('517', '155', '4NmNQbWr', '身体健康且有一技之长 ', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('518', '155', 'RP7NGbWA', '身体健康但无一技之长', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('519', '155', 'emZK2JBm', '3-4级伤残，或有一定劳动能力', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('520', '155', 'VKSymEES', '1-2级伤残', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('521', '155', 'Oy29mJcZ', '完全丧失劳动能力、失踪（联）或去世', '10', '104');
INSERT INTO `tb_erm_field_val` VALUES ('522', '156', '3a2B4A9H', '身体健康且有一技之长 ', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('523', '156', 'pU8TaE06', '身体健康但无一技之长', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('524', '156', 'x9rULNDq', '3-4级伤残，或有一定劳动能力', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('525', '156', 'wIFafGUK', '1-2级伤残', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('526', '156', 'ty9A0nZC', '完全丧失劳动能力、失踪（联）或去世', '10', '104');
INSERT INTO `tb_erm_field_val` VALUES ('527', '157', 'EVI5wnl0', '公司股东或高管、私营业主、全职太太 ', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('528', '157', 'zgpjpTTm', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('529', '157', 'Z2dmko76', '进城务工人员或合同制工作人员', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('530', '157', 'bpdOVJC4', '务农或临时务工 ', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('531', '157', 'DNi4kG1d', '因身体或其他原因无法就业、失踪（联）或去世', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('532', '158', 'AnV0aFZC', '公司股东或高管、私营业主', '0', '104');
INSERT INTO `tb_erm_field_val` VALUES ('533', '158', 'AIYPyCC4', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '104');
INSERT INTO `tb_erm_field_val` VALUES ('534', '158', 'cWVY0MI3', '进城务工人员或合同制工作人员 ', '2', '104');
INSERT INTO `tb_erm_field_val` VALUES ('535', '158', '6kifRfan', '务农或临时务工 ', '3', '104');
INSERT INTO `tb_erm_field_val` VALUES ('536', '158', '5Q1IvM6N', '因身体或其他原因无法就业、失踪（联）或去世', '6', '104');
INSERT INTO `tb_erm_field_val` VALUES ('537', '159', 'vAnt0yzT', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '145');
INSERT INTO `tb_erm_field_val` VALUES ('538', '159', 'TFl8mBw7', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('539', '159', 'K7QmjLfV', '少数民族', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('540', '159', 'lQ2308mH', '农村独生子女或计划生育政策双女户', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('541', '160', 'NQgvvDE7', '健康', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('542', '160', 'twnopPzd', '较差', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('543', '160', 'SKv8RUwD', '很差', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('544', '161', 'y4UL8hFs', '一般', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('545', '161', 'PKBzVA1t', '较少', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('546', '161', 'MQ9w6VMi', '很少', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('547', '162', 'WSDoWDh7', '1人', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('548', '162', 'aDvb0ZRU', '2人', '4', '145');
INSERT INTO `tb_erm_field_val` VALUES ('549', '162', '18QEQ6lL', '3人', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('550', '163', 'B4q5emrS', '近两年家庭实际住地在县城及以上', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('551', '163', 'Fqyys4d5', '近两年家庭实际住地在乡镇及以下', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('552', '164', 'xDqoNH9Y', '近两年内未出现家庭变故', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('553', '164', 'S8aMPq3Y', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '145');
INSERT INTO `tb_erm_field_val` VALUES ('554', '164', '0a0hKMGw', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '145');
INSERT INTO `tb_erm_field_val` VALUES ('555', '165', 'ynyhXODa', '近两年内未遭受自然灾害', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('556', '165', '3Oe90TpL', '近两年内遭受一般自然灾害，影响家庭收入', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('557', '165', 'Wm57y83R', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '145');
INSERT INTO `tb_erm_field_val` VALUES ('558', '165', 'n1AWGTa4', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '145');
INSERT INTO `tb_erm_field_val` VALUES ('559', '166', 'L9r2VLLP', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('560', '166', 'hP7r70xr', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('561', '166', 'gnABk2wU', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '145');
INSERT INTO `tb_erm_field_val` VALUES ('562', '166', 'Qlka4a37', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '145');
INSERT INTO `tb_erm_field_val` VALUES ('563', '167', '3zngARdi', '其他房屋或两套及以上', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('564', '167', 'gfBiiZhr', '农村简易砖瓦房或城市廉租房、公租房', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('565', '167', 'OP1jeWto', '农村简易房或城市筒子楼', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('566', '167', 'SPqMwo5G', '无房', '8', '145');
INSERT INTO `tb_erm_field_val` VALUES ('567', '168', 'udMKB18D', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('568', '168', 'M5ekFVOe', '其他家庭成员中部分有劳动能力或固定收入', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('569', '168', 'qK7Zf6KT', '其他成员均无劳动能力或固定收入', '4', '145');
INSERT INTO `tb_erm_field_val` VALUES ('570', '169', 'QbnDHBWW', '身体健康且有一技之长 ', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('571', '169', 'NjK7WM3e', '身体健康但无一技之长', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('572', '169', 'YuAR8kme', '3-4级伤残，或有一定劳动能力', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('573', '169', 'Ukp7G09F', '1-2级伤残', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('574', '169', 'dyOqxR70', '完全丧失劳动能力、失踪（联）或去世', '10', '145');
INSERT INTO `tb_erm_field_val` VALUES ('575', '170', 'L0Aie1kx', '身体健康且有一技之长 ', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('576', '170', 'qwSaB9mi', '身体健康但无一技之长', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('577', '170', '00VSf8J7', '3-4级伤残，或有一定劳动能力', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('578', '170', 'auhdtNma', '1-2级伤残', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('579', '170', '08DxAkG0', '完全丧失劳动能力、失踪（联）或去世', '10', '145');
INSERT INTO `tb_erm_field_val` VALUES ('580', '171', '4s0EBmgO', '公司股东或高管、私营业主、全职太太 ', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('581', '171', 'm0Prbrtu', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('582', '171', 'rxNW5FuX', '进城务工人员或合同制工作人员', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('583', '171', '8zwjD5TZ', '务农或临时务工 ', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('584', '171', 'gbYOmbMe', '因身体或其他原因无法就业、失踪（联）或去世', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('585', '172', 'a1akDeOX', '公司股东或高管、私营业主', '0', '145');
INSERT INTO `tb_erm_field_val` VALUES ('586', '172', 'jyVW3dvr', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '145');
INSERT INTO `tb_erm_field_val` VALUES ('587', '172', 'IqkPk1ff', '进城务工人员或合同制工作人员 ', '2', '145');
INSERT INTO `tb_erm_field_val` VALUES ('588', '172', '6ojWyc8E', '务农或临时务工 ', '3', '145');
INSERT INTO `tb_erm_field_val` VALUES ('589', '172', 'IgDCnDhv', '因身体或其他原因无法就业、失踪（联）或去世', '6', '145');
INSERT INTO `tb_erm_field_val` VALUES ('590', '173', 'tFLdXXFP', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '106');
INSERT INTO `tb_erm_field_val` VALUES ('591', '173', 'lcglmNHW', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('592', '173', 'g1QtNCxQ', '少数民族', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('593', '173', 'rcji7iOA', '农村独生子女或计划生育政策双女户', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('594', '174', 'F2Wnmu60', '健康', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('595', '174', 'vNipzkTY', '较差', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('596', '174', '2UGQkjlU', '很差', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('597', '175', '9yDy15xC', '一般', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('598', '175', 'weMMgqDv', '较少', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('599', '175', 'IBY3SKrM', '很少', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('600', '176', '1VsDsGAM', '1人', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('601', '176', 'vxFSHl1g', '2人', '4', '106');
INSERT INTO `tb_erm_field_val` VALUES ('602', '176', 'EUaTPwih', '3人', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('603', '177', 'm5L8b5Jq', '近两年家庭实际住地在县城及以上', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('604', '177', 'pA2Iy9MX', '近两年家庭实际住地在乡镇及以下', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('605', '178', 'oRok7x03', '近两年内未出现家庭变故', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('606', '178', 'jiL0JAHk', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '106');
INSERT INTO `tb_erm_field_val` VALUES ('607', '178', '9KKlJVyo', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '106');
INSERT INTO `tb_erm_field_val` VALUES ('608', '179', '6kuW6A7E', '近两年内未遭受自然灾害', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('609', '179', 'ABiCaqqe', '近两年内遭受一般自然灾害，影响家庭收入', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('610', '179', 'MjoBevm9', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '106');
INSERT INTO `tb_erm_field_val` VALUES ('611', '179', 'jYEeaIzh', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '106');
INSERT INTO `tb_erm_field_val` VALUES ('612', '180', 'ec3uAjgb', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('613', '180', 'x0XVmi0q', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('614', '180', 'Y2tYl1PH', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '106');
INSERT INTO `tb_erm_field_val` VALUES ('615', '180', 'OjnTu2vW', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '106');
INSERT INTO `tb_erm_field_val` VALUES ('616', '181', 'XEL7eWZR', '其他房屋或两套及以上', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('617', '181', 'KP4nxgtA', '农村简易砖瓦房或城市廉租房、公租房', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('618', '181', 'zvxOSWbE', '农村简易房或城市筒子楼', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('619', '181', 'CSYTzjyN', '无房', '8', '106');
INSERT INTO `tb_erm_field_val` VALUES ('620', '182', 'cpbtmy6G', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('621', '182', 'ROpQA7zi', '其他家庭成员中部分有劳动能力或固定收入', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('622', '182', 'Vp1IYliv', '其他成员均无劳动能力或固定收入', '4', '106');
INSERT INTO `tb_erm_field_val` VALUES ('623', '183', 'OYqDmJaO', '身体健康且有一技之长 ', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('624', '183', '8rnjCYhi', '身体健康但无一技之长', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('625', '183', 'W35BFlGJ', '3-4级伤残，或有一定劳动能力', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('626', '183', 'ZQPTXYvP', '1-2级伤残', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('627', '183', '67EFmYAj', '完全丧失劳动能力、失踪（联）或去世', '10', '106');
INSERT INTO `tb_erm_field_val` VALUES ('628', '184', 'WUyTrN8u', '身体健康且有一技之长 ', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('629', '184', 'JLD8d7sx', '身体健康但无一技之长', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('630', '184', 'L0KTAEpA', '3-4级伤残，或有一定劳动能力', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('631', '184', 'HU9llWjk', '1-2级伤残', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('632', '184', '7HqC9Pkf', '完全丧失劳动能力、失踪（联）或去世', '10', '106');
INSERT INTO `tb_erm_field_val` VALUES ('633', '185', 'HdDysv0k', '公司股东或高管、私营业主、全职太太 ', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('634', '185', 'Y5eobl1E', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('635', '185', 'WrwgCRTZ', '进城务工人员或合同制工作人员', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('636', '185', 'zHtzkjw7', '务农或临时务工 ', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('637', '185', 'MQ10OzLx', '因身体或其他原因无法就业、失踪（联）或去世', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('638', '186', 'n0m44gSi', '公司股东或高管、私营业主', '0', '106');
INSERT INTO `tb_erm_field_val` VALUES ('639', '186', '5vsuFoXJ', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '106');
INSERT INTO `tb_erm_field_val` VALUES ('640', '186', 'X6fj5qd1', '进城务工人员或合同制工作人员 ', '2', '106');
INSERT INTO `tb_erm_field_val` VALUES ('641', '186', 'uV0rGDlJ', '务农或临时务工 ', '3', '106');
INSERT INTO `tb_erm_field_val` VALUES ('642', '186', 'AzoWptr0', '因身体或其他原因无法就业、失踪（联）或去世', '6', '106');
INSERT INTO `tb_erm_field_val` VALUES ('643', '187', 'BS04142t', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '150');
INSERT INTO `tb_erm_field_val` VALUES ('644', '187', 'Fp4sLscV', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('645', '187', '11qjeTyt', '少数民族', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('646', '187', 'aAB3sS0x', '农村独生子女或计划生育政策双女户', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('647', '188', 'Aq7SgMuh', '健康', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('648', '188', '5tRAwhb0', '较差', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('649', '188', 'm2hO78sP', '很差', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('650', '189', 'TgJwLnc5', '一般', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('651', '189', 'V3n1Z6XP', '较少', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('652', '189', 'BWGYkMm2', '很少', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('653', '190', 'hB0T9JYp', '1人', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('654', '190', 'KhRF5oWF', '2人', '4', '150');
INSERT INTO `tb_erm_field_val` VALUES ('655', '190', 'LMuDfxiH', '3人', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('656', '191', 'LJ8jRXdE', '近两年家庭实际住地在县城及以上', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('657', '191', 'hO0rOBam', '近两年家庭实际住地在乡镇及以下', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('658', '192', 'frzIulKx', '近两年内未出现家庭变故', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('659', '192', 'Wzi5S7op', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '150');
INSERT INTO `tb_erm_field_val` VALUES ('660', '192', 'JbAvNUFC', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '150');
INSERT INTO `tb_erm_field_val` VALUES ('661', '193', 'gLJ2BxVN', '近两年内未遭受自然灾害', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('662', '193', '9HLOIfaP', '近两年内遭受一般自然灾害，影响家庭收入', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('663', '193', 'g9RkDJ36', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '150');
INSERT INTO `tb_erm_field_val` VALUES ('664', '193', 'uNSuTiaf', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '150');
INSERT INTO `tb_erm_field_val` VALUES ('665', '194', 'RCaFRvAY', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('666', '194', 'E3F8pOC6', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('667', '194', 'RFFw9bmm', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '150');
INSERT INTO `tb_erm_field_val` VALUES ('668', '194', '39gqbkA6', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '150');
INSERT INTO `tb_erm_field_val` VALUES ('669', '195', 'qVCkphwA', '其他房屋或两套及以上', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('670', '195', 'UgL8Nl7K', '农村简易砖瓦房或城市廉租房、公租房', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('671', '195', 'DG8FiK8P', '农村简易房或城市筒子楼', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('672', '195', 'BJ1mOUTS', '无房', '8', '150');
INSERT INTO `tb_erm_field_val` VALUES ('673', '196', 'QI2aWmdJ', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('674', '196', 'RqEzYHAj', '其他家庭成员中部分有劳动能力或固定收入', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('675', '196', 'OqT9PY7c', '其他成员均无劳动能力或固定收入', '4', '150');
INSERT INTO `tb_erm_field_val` VALUES ('676', '197', 'MwQPj2bQ', '身体健康且有一技之长 ', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('677', '197', 'PK2wVU35', '身体健康但无一技之长', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('678', '197', 'TCaDrOg0', '3-4级伤残，或有一定劳动能力', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('679', '197', 'i4guPk40', '1-2级伤残', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('680', '197', 'UBQFu0Ny', '完全丧失劳动能力、失踪（联）或去世', '10', '150');
INSERT INTO `tb_erm_field_val` VALUES ('681', '198', 'eazJZMy4', '身体健康且有一技之长 ', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('682', '198', 'O4cZTYeo', '身体健康但无一技之长', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('683', '198', 'p8Yqh5zw', '3-4级伤残，或有一定劳动能力', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('684', '198', 'aEb6fEIp', '1-2级伤残', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('685', '198', 'cZpz2sCQ', '完全丧失劳动能力、失踪（联）或去世', '10', '150');
INSERT INTO `tb_erm_field_val` VALUES ('686', '199', 'jOmbhIvs', '公司股东或高管、私营业主、全职太太 ', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('687', '199', 'Jt83D8CJ', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('688', '199', 'EH1YncrG', '进城务工人员或合同制工作人员', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('689', '199', '0ZnQS5TG', '务农或临时务工 ', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('690', '199', '6MLBqKXg', '因身体或其他原因无法就业、失踪（联）或去世', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('691', '200', 'oQHLbuht', '公司股东或高管、私营业主', '0', '150');
INSERT INTO `tb_erm_field_val` VALUES ('692', '200', '6xfHdtJT', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '150');
INSERT INTO `tb_erm_field_val` VALUES ('693', '200', 'VPPxVcp5', '进城务工人员或合同制工作人员 ', '2', '150');
INSERT INTO `tb_erm_field_val` VALUES ('694', '200', 'cwRpQOeb', '务农或临时务工 ', '3', '150');
INSERT INTO `tb_erm_field_val` VALUES ('695', '200', 'DeGJd5HG', '因身体或其他原因无法就业、失踪（联）或去世', '6', '150');
INSERT INTO `tb_erm_field_val` VALUES ('696', '201', 'tCk6IcSo', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '105');
INSERT INTO `tb_erm_field_val` VALUES ('697', '201', 'bTm2R1lP', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('698', '201', 'AKZhGDpE', '少数民族', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('699', '201', 'AAtGJJoO', '农村独生子女或计划生育政策双女户', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('700', '202', '74jIFIJC', '健康', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('701', '202', 'm7EPoPv4', '较差', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('702', '202', 'B5UEyHr2', '很差', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('703', '203', 'oMDgCmFY', '一般', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('704', '203', 'dbp9ruvT', '较少', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('705', '203', 'gkGbohw0', '很少', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('706', '204', 'eEPRUenY', '1人', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('707', '204', 'bf3Zdlry', '2人', '4', '105');
INSERT INTO `tb_erm_field_val` VALUES ('708', '204', 'phpyFdAf', '3人', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('709', '205', 'rnL16kPG', '近两年家庭实际住地在县城及以上', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('710', '205', 'xvHXAcVV', '近两年家庭实际住地在乡镇及以下', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('711', '206', 'MMzji5IF', '近两年内未出现家庭变故', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('712', '206', 'jhHNGc5r', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '105');
INSERT INTO `tb_erm_field_val` VALUES ('713', '206', 'XNFAvwsI', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '105');
INSERT INTO `tb_erm_field_val` VALUES ('714', '207', '0voRZaxN', '近两年内未遭受自然灾害', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('715', '207', 'daqqCRle', '近两年内遭受一般自然灾害，影响家庭收入', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('716', '207', 'ahPFOa2m', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '105');
INSERT INTO `tb_erm_field_val` VALUES ('717', '207', 'HnVtg3zt', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '105');
INSERT INTO `tb_erm_field_val` VALUES ('718', '208', '0VztURTi', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('719', '208', '51fMcmDh', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('720', '208', 'iz9Z29QA', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '105');
INSERT INTO `tb_erm_field_val` VALUES ('721', '208', '3ahpml51', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '105');
INSERT INTO `tb_erm_field_val` VALUES ('722', '209', 'D5ITDFzr', '其他房屋或两套及以上', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('723', '209', 'G1lf4u1k', '农村简易砖瓦房或城市廉租房、公租房', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('724', '209', '5Sb8jSjq', '农村简易房或城市筒子楼', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('725', '209', 'R6lTUefP', '无房', '8', '105');
INSERT INTO `tb_erm_field_val` VALUES ('726', '210', 'GYceqUgb', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('727', '210', 'ybClY9IV', '其他家庭成员中部分有劳动能力或固定收入', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('728', '210', '5dZfqRFy', '其他成员均无劳动能力或固定收入', '4', '105');
INSERT INTO `tb_erm_field_val` VALUES ('729', '211', '7pFZuJzt', '身体健康且有一技之长 ', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('730', '211', 'JeyZ9s0p', '身体健康但无一技之长', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('731', '211', 'LqbOfSNx', '3-4级伤残，或有一定劳动能力', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('732', '211', 'ulQld3Nr', '1-2级伤残', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('733', '211', 'JFRkrknw', '完全丧失劳动能力、失踪（联）或去世', '10', '105');
INSERT INTO `tb_erm_field_val` VALUES ('734', '212', 'zluUyLyB', '身体健康且有一技之长 ', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('735', '212', '3zhaV3Qo', '身体健康但无一技之长', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('736', '212', '8OC3rdsv', '3-4级伤残，或有一定劳动能力', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('737', '212', 'JSDxwKUl', '1-2级伤残', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('738', '212', 'PSDpjxP5', '完全丧失劳动能力、失踪（联）或去世', '10', '105');
INSERT INTO `tb_erm_field_val` VALUES ('739', '213', 'qVwery6r', '公司股东或高管、私营业主、全职太太 ', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('740', '213', 'u1lPYeEK', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('741', '213', '8OlEtkVW', '进城务工人员或合同制工作人员', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('742', '213', 'hJk2Gpn1', '务农或临时务工 ', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('743', '213', 'Ne1lxQ6p', '因身体或其他原因无法就业、失踪（联）或去世', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('744', '214', 'YNqdL45y', '公司股东或高管、私营业主', '0', '105');
INSERT INTO `tb_erm_field_val` VALUES ('745', '214', 'P9WtGxYf', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '105');
INSERT INTO `tb_erm_field_val` VALUES ('746', '214', 'su0F4u96', '进城务工人员或合同制工作人员 ', '2', '105');
INSERT INTO `tb_erm_field_val` VALUES ('747', '214', 'GaOMolFL', '务农或临时务工 ', '3', '105');
INSERT INTO `tb_erm_field_val` VALUES ('748', '214', '9Ehiz3qF', '因身体或其他原因无法就业、失踪（联）或去世', '6', '105');
INSERT INTO `tb_erm_field_val` VALUES ('749', '215', 'KMU40Yd7', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '107');
INSERT INTO `tb_erm_field_val` VALUES ('750', '215', '9taW5CGY', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('751', '215', 'G8Lo1wEi', '少数民族', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('752', '215', 'epUuP65y', '农村独生子女或计划生育政策双女户', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('753', '216', 'R0vkONIQ', '健康', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('754', '216', 'EH2jkquX', '较差', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('755', '216', 'w63pqo24', '很差', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('756', '217', '4NXyFjCh', '一般', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('757', '217', 'aocTpSvs', '较少', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('758', '217', '9lSgmlmu', '很少', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('759', '218', 'Ks1JHpF7', '1人', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('760', '218', 'AA3bxp0h', '2人', '4', '107');
INSERT INTO `tb_erm_field_val` VALUES ('761', '218', '2PEYbyZm', '3人', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('762', '219', 'fI08ORzi', '近两年家庭实际住地在县城及以上', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('763', '219', 'JiUKNbYU', '近两年家庭实际住地在乡镇及以下', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('764', '220', 'mqNUAR8Y', '近两年内未出现家庭变故', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('765', '220', 'IzoQGX2D', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '107');
INSERT INTO `tb_erm_field_val` VALUES ('766', '220', 'kEwDnDFQ', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '107');
INSERT INTO `tb_erm_field_val` VALUES ('767', '221', 'L8VDEyqe', '近两年内未遭受自然灾害', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('768', '221', 'UlwdvyNP', '近两年内遭受一般自然灾害，影响家庭收入', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('769', '221', '2yTwP98x', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '107');
INSERT INTO `tb_erm_field_val` VALUES ('770', '221', 'TEzV8KgH', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '107');
INSERT INTO `tb_erm_field_val` VALUES ('771', '222', 'QXElyg0C', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('772', '222', 'NvWIpuSA', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('773', '222', 'VtemCNzh', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '107');
INSERT INTO `tb_erm_field_val` VALUES ('774', '222', 'VKPKV68Q', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '107');
INSERT INTO `tb_erm_field_val` VALUES ('775', '223', '4hDULFEg', '其他房屋或两套及以上', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('776', '223', 'artsBNGf', '农村简易砖瓦房或城市廉租房、公租房', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('777', '223', 'ClgUKPQc', '农村简易房或城市筒子楼', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('778', '223', 'ZnXMcRKD', '无房', '8', '107');
INSERT INTO `tb_erm_field_val` VALUES ('779', '224', 'gYNWILQl', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('780', '224', 'dW5IdZkj', '其他家庭成员中部分有劳动能力或固定收入', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('781', '224', 'zJ5uwGoY', '其他成员均无劳动能力或固定收入', '4', '107');
INSERT INTO `tb_erm_field_val` VALUES ('782', '225', 'Kb7pRFtM', '身体健康且有一技之长 ', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('783', '225', 'LmIXsiAV', '身体健康但无一技之长', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('784', '225', 'C8yaXcPy', '3-4级伤残，或有一定劳动能力', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('785', '225', '12MJX3EA', '1-2级伤残', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('786', '225', '73cUUPgY', '完全丧失劳动能力、失踪（联）或去世', '10', '107');
INSERT INTO `tb_erm_field_val` VALUES ('787', '226', 'xuIbMXJf', '身体健康且有一技之长 ', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('788', '226', 'Dru1YBq9', '身体健康但无一技之长', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('789', '226', 'tRv1piOi', '3-4级伤残，或有一定劳动能力', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('790', '226', '0utdRnKQ', '1-2级伤残', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('791', '226', 'vXdSN4pd', '完全丧失劳动能力、失踪（联）或去世', '10', '107');
INSERT INTO `tb_erm_field_val` VALUES ('792', '227', '6sQD04pL', '公司股东或高管、私营业主、全职太太 ', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('793', '227', 'Qiq2R7YL', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('794', '227', 'G9YVSi0g', '进城务工人员或合同制工作人员', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('795', '227', 'Up5ESSV1', '务农或临时务工 ', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('796', '227', 'WhefKuuu', '因身体或其他原因无法就业、失踪（联）或去世', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('797', '228', 'wxapM6vy', '公司股东或高管、私营业主', '0', '107');
INSERT INTO `tb_erm_field_val` VALUES ('798', '228', 'LU7oo1XW', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '107');
INSERT INTO `tb_erm_field_val` VALUES ('799', '228', 'LykQF5q9', '进城务工人员或合同制工作人员 ', '2', '107');
INSERT INTO `tb_erm_field_val` VALUES ('800', '228', 'z0DX78Xy', '务农或临时务工 ', '3', '107');
INSERT INTO `tb_erm_field_val` VALUES ('801', '228', 'f8BipkPu', '因身体或其他原因无法就业、失踪（联）或去世', '6', '107');
INSERT INTO `tb_erm_field_val` VALUES ('802', '229', 'SC2zfnr7', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '135');
INSERT INTO `tb_erm_field_val` VALUES ('803', '229', '1GlqWV8f', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('804', '229', 'hw0Ldwkk', '少数民族', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('805', '229', '7YUa2KCt', '农村独生子女或计划生育政策双女户', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('806', '230', '24JHyFxF', '健康', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('807', '230', 'SykIWzqt', '较差', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('808', '230', '8m568stp', '很差', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('809', '231', '122LKMXf', '一般', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('810', '231', 'ZvsoZu3Z', '较少', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('811', '231', 'WWEoU9Pw', '很少', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('812', '232', '7BeePaF8', '1人', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('813', '232', 'ySlpgbn8', '2人', '4', '135');
INSERT INTO `tb_erm_field_val` VALUES ('814', '232', 'U4CoSY6J', '3人', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('815', '233', 'qNg5hXWK', '近两年家庭实际住地在县城及以上', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('816', '233', '3gWRcMEA', '近两年家庭实际住地在乡镇及以下', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('817', '234', 'ABBGPMpx', '近两年内未出现家庭变故', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('818', '234', 'AkOyWuO1', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '135');
INSERT INTO `tb_erm_field_val` VALUES ('819', '234', 'RZrEK391', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '135');
INSERT INTO `tb_erm_field_val` VALUES ('820', '235', '3zJHvDoC', '近两年内未遭受自然灾害', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('821', '235', 'KC5AJHlO', '近两年内遭受一般自然灾害，影响家庭收入', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('822', '235', 'H4XRbuaM', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '135');
INSERT INTO `tb_erm_field_val` VALUES ('823', '235', 'mGkCVfYL', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '135');
INSERT INTO `tb_erm_field_val` VALUES ('824', '236', 'B00szti0', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('825', '236', '6u7I6jE4', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('826', '236', 'lveaiq1v', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '135');
INSERT INTO `tb_erm_field_val` VALUES ('827', '236', '9kRV49oR', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '135');
INSERT INTO `tb_erm_field_val` VALUES ('828', '237', 'hrLQfWo0', '其他房屋或两套及以上', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('829', '237', 'q6WMKCF1', '农村简易砖瓦房或城市廉租房、公租房', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('830', '237', 'wHXmvm5s', '农村简易房或城市筒子楼', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('831', '237', 'bKPbto7N', '无房', '8', '135');
INSERT INTO `tb_erm_field_val` VALUES ('832', '238', 'LSxYvti8', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('833', '238', 'Mx4hFkau', '其他家庭成员中部分有劳动能力或固定收入', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('834', '238', 'THHJSMnL', '其他成员均无劳动能力或固定收入', '4', '135');
INSERT INTO `tb_erm_field_val` VALUES ('835', '239', 'N8LVsIkA', '身体健康且有一技之长 ', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('836', '239', 'UBbClm8J', '身体健康但无一技之长', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('837', '239', 'lu3t3NaZ', '3-4级伤残，或有一定劳动能力', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('838', '239', 'J7AA0KKI', '1-2级伤残', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('839', '239', 'hNFGqAMQ', '完全丧失劳动能力、失踪（联）或去世', '10', '135');
INSERT INTO `tb_erm_field_val` VALUES ('840', '240', 'JAhqZjSU', '身体健康且有一技之长 ', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('841', '240', '19hSPRwQ', '身体健康但无一技之长', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('842', '240', 't4bQxtqq', '3-4级伤残，或有一定劳动能力', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('843', '240', 'u8pLW3aA', '1-2级伤残', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('844', '240', 'XDtp8Zcy', '完全丧失劳动能力、失踪（联）或去世', '10', '135');
INSERT INTO `tb_erm_field_val` VALUES ('845', '241', '6ALoX1d3', '公司股东或高管、私营业主、全职太太 ', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('846', '241', '1ynKrDYe', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('847', '241', 'XzCr1zA0', '进城务工人员或合同制工作人员', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('848', '241', '2NPpiB0T', '务农或临时务工 ', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('849', '241', 'bKJLIwSO', '因身体或其他原因无法就业、失踪（联）或去世', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('850', '242', 'BTbZZa4Y', '公司股东或高管、私营业主', '0', '135');
INSERT INTO `tb_erm_field_val` VALUES ('851', '242', 'TvQEbMLU', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '135');
INSERT INTO `tb_erm_field_val` VALUES ('852', '242', 'G9nymaVW', '进城务工人员或合同制工作人员 ', '2', '135');
INSERT INTO `tb_erm_field_val` VALUES ('853', '242', 'c2s486yP', '务农或临时务工 ', '3', '135');
INSERT INTO `tb_erm_field_val` VALUES ('854', '242', 'PwwXOMX8', '因身体或其他原因无法就业、失踪（联）或去世', '6', '135');
INSERT INTO `tb_erm_field_val` VALUES ('855', '243', 'wbRSg56R', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '109');
INSERT INTO `tb_erm_field_val` VALUES ('856', '243', 'MQ1G2tDc', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('857', '243', 'C37iGsYK', '少数民族', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('858', '243', 'b1WVOpaF', '农村独生子女或计划生育政策双女户', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('859', '244', 'ZRFtWDMI', '健康', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('860', '244', '1kcc7cXD', '较差', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('861', '244', '2Onku7UW', '很差', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('862', '245', 'atu8FaMr', '一般', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('863', '245', 'MHUFMeSh', '较少', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('864', '245', 'Al1PSicq', '很少', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('865', '246', 'IHQRQRDo', '1人', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('866', '246', 'v3r9IKy8', '2人', '4', '109');
INSERT INTO `tb_erm_field_val` VALUES ('867', '246', 'pZG7LpwP', '3人', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('868', '247', '8YdHM4ou', '近两年家庭实际住地在县城及以上', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('869', '247', 'dNoRI23q', '近两年家庭实际住地在乡镇及以下', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('870', '248', 'h1B3xeqt', '近两年内未出现家庭变故', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('871', '248', 'u0aESaRt', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '109');
INSERT INTO `tb_erm_field_val` VALUES ('872', '248', 'dk7k9ouy', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '109');
INSERT INTO `tb_erm_field_val` VALUES ('873', '249', 'a5qRhR7q', '近两年内未遭受自然灾害', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('874', '249', 'ySFKjlWE', '近两年内遭受一般自然灾害，影响家庭收入', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('875', '249', 'j6etpOA4', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '109');
INSERT INTO `tb_erm_field_val` VALUES ('876', '249', 'gC8IYWHN', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '109');
INSERT INTO `tb_erm_field_val` VALUES ('877', '250', 'zEzWlufp', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('878', '250', 'NnnNQLtJ', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('879', '250', 't3CZ8Cnz', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '109');
INSERT INTO `tb_erm_field_val` VALUES ('880', '250', 'YUIVE2EM', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '109');
INSERT INTO `tb_erm_field_val` VALUES ('881', '251', '31e9Esnn', '其他房屋或两套及以上', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('882', '251', 'jYFvaeEV', '农村简易砖瓦房或城市廉租房、公租房', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('883', '251', 'KEaW7Qhg', '农村简易房或城市筒子楼', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('884', '251', 'a3zM0XAP', '无房', '8', '109');
INSERT INTO `tb_erm_field_val` VALUES ('885', '252', 'LMG3yGod', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('886', '252', 'Y9Aas4D1', '其他家庭成员中部分有劳动能力或固定收入', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('887', '252', 'aqJUO4VN', '其他成员均无劳动能力或固定收入', '4', '109');
INSERT INTO `tb_erm_field_val` VALUES ('888', '253', 'fazM2HVX', '身体健康且有一技之长 ', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('889', '253', 'k2aRWUrN', '身体健康但无一技之长', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('890', '253', 'bER25mtA', '3-4级伤残，或有一定劳动能力', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('891', '253', 'nf3ZTZ7Z', '1-2级伤残', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('892', '253', 'VR4xXg2F', '完全丧失劳动能力、失踪（联）或去世', '10', '109');
INSERT INTO `tb_erm_field_val` VALUES ('893', '254', 'Tv2SdXPQ', '身体健康且有一技之长 ', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('894', '254', '2niUpun2', '身体健康但无一技之长', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('895', '254', 'lsRPnFOn', '3-4级伤残，或有一定劳动能力', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('896', '254', 'jmhLt6Tm', '1-2级伤残', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('897', '254', 'pHT5RYAG', '完全丧失劳动能力、失踪（联）或去世', '10', '109');
INSERT INTO `tb_erm_field_val` VALUES ('898', '255', 'pgtAQedq', '公司股东或高管、私营业主、全职太太 ', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('899', '255', 'AHQ9gwx7', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('900', '255', 'vZvC83Ft', '进城务工人员或合同制工作人员', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('901', '255', 'O9QBAB4D', '务农或临时务工 ', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('902', '255', 'UucI3yvj', '因身体或其他原因无法就业、失踪（联）或去世', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('903', '256', 'FP3cl97K', '公司股东或高管、私营业主', '0', '109');
INSERT INTO `tb_erm_field_val` VALUES ('904', '256', 'qHlgpfO1', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '109');
INSERT INTO `tb_erm_field_val` VALUES ('905', '256', 'RUECg1J1', '进城务工人员或合同制工作人员 ', '2', '109');
INSERT INTO `tb_erm_field_val` VALUES ('906', '256', '0Qnz8U0S', '务农或临时务工 ', '3', '109');
INSERT INTO `tb_erm_field_val` VALUES ('907', '256', '9Cf5j3d1', '因身体或其他原因无法就业、失踪（联）或去世', '6', '109');
INSERT INTO `tb_erm_field_val` VALUES ('908', '257', 'nTWwqYja', '烈士子女、城乡低保户家庭子女、被县级以上民政部门认定为低收入家庭子女、建档立卡贫困户子女', '5', '87');
INSERT INTO `tb_erm_field_val` VALUES ('909', '257', 'CP3x2X9H', '除烈士子女外的优抚家庭子女、重点工程移民子女，见义勇为人员子女', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('910', '257', 'OdLoaaoT', '少数民族', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('911', '257', 'X6wkCP3u', '农村独生子女或计划生育政策双女户', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('912', '258', 'fljwbYkn', '健康', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('913', '258', 'rKTyacjR', '较差', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('914', '258', 'u7MwUIYp', '很差', '6', '87');
INSERT INTO `tb_erm_field_val` VALUES ('915', '259', '0cs88kOv', '一般', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('916', '259', '6rCEeWB8', '较少', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('917', '259', '54srkpe2', '很少', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('918', '260', 'c4ac9cnp', '1人', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('919', '260', 'GJznYxJD', '2人', '4', '87');
INSERT INTO `tb_erm_field_val` VALUES ('920', '260', 'zH4k4te6', '3人', '6', '87');
INSERT INTO `tb_erm_field_val` VALUES ('921', '261', 'HeGihkFn', '近两年家庭实际住地在县城及以上', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('922', '261', 'cOchosHx', '近两年家庭实际住地在乡镇及以下', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('923', '262', 'KOddi8vE', '近两年内未出现家庭变故', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('924', '262', 'cUWtxITV', '近两年内家庭成员出现伤残、失踪或意外事故造成财产损失等情况', '5', '87');
INSERT INTO `tb_erm_field_val` VALUES ('925', '262', '8fzEpPkp', '近两年内家庭成员出现重大伤残、意外死亡或重大变故', '10', '87');
INSERT INTO `tb_erm_field_val` VALUES ('926', '263', 'uVfgtaeJ', '近两年内未遭受自然灾害', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('927', '263', 'Ns2pg1bb', '近两年内遭受一般自然灾害，影响家庭收入', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('928', '263', 'Auw6yBdA', '近两年内遭受较重自然灾害，影响家庭收入且造成财产损失', '5', '87');
INSERT INTO `tb_erm_field_val` VALUES ('929', '263', 'hY4I6Dm9', '近两年内遭受较重自然灾害，造成人身伤害和财产重大损失', '10', '87');
INSERT INTO `tb_erm_field_val` VALUES ('930', '264', '5KHe9uE2', '家庭成员医疗费用个人负担部分在2000元及以内', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('931', '264', 'fWhVbM4K', '家庭成员医疗费用个人负担部分在2000-1万（含1万）', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('932', '264', 'xzRn1zcd', '家庭成员医疗费用个人负担部分在1万-3万（含3万）', '5', '87');
INSERT INTO `tb_erm_field_val` VALUES ('933', '264', 'CyUpaWeH', '家庭成员医疗费用个人负担部分在3万以上或患重大疾病', '10', '87');
INSERT INTO `tb_erm_field_val` VALUES ('934', '265', '3rRsbKCH', '其他房屋或两套及以上', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('935', '265', 'gSBBeRlF', '农村简易砖瓦房或城市廉租房、公租房', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('936', '265', 'P9wbvubH', '农村简易房或城市筒子楼', '6', '87');
INSERT INTO `tb_erm_field_val` VALUES ('937', '265', 'u3qaWaoG', '无房', '8', '87');
INSERT INTO `tb_erm_field_val` VALUES ('938', '266', 'ZQmJqOVA', '无其他成员；或有其他成员且有劳动能力或固定收入', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('939', '266', 'olKbhUYm', '其他家庭成员中部分有劳动能力或固定收入', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('940', '266', '1nS4ke97', '其他成员均无劳动能力或固定收入', '4', '87');
INSERT INTO `tb_erm_field_val` VALUES ('941', '267', '3x0WNjJ0', '身体健康且有一技之长 ', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('942', '267', '8suoiuGW', '身体健康但无一技之长', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('943', '267', '7IV3e3ZW', '3-4级伤残，或有一定劳动能力', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('944', '267', 'C5CDSPyq', '1-2级伤残', '6', '87');
INSERT INTO `tb_erm_field_val` VALUES ('945', '267', 'VS9eHuB6', '完全丧失劳动能力、失踪（联）或去世', '10', '87');
INSERT INTO `tb_erm_field_val` VALUES ('946', '268', 'r1eFDOJj', '身体健康且有一技之长 ', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('947', '268', 'YFJHxsr7', '身体健康但无一技之长', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('948', '268', 'mUeexewt', '3-4级伤残，或有一定劳动能力', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('949', '268', '3gUG8fyH', '1-2级伤残', '6', '87');
INSERT INTO `tb_erm_field_val` VALUES ('950', '268', 'nsHPObMo', '完全丧失劳动能力、失踪（联）或去世', '10', '87');
INSERT INTO `tb_erm_field_val` VALUES ('951', '269', '5QtQsgov', '公司股东或高管、私营业主、全职太太 ', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('952', '269', 'WKVidwZM', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('953', '269', 'IiLXYYV2', '进城务工人员或合同制工作人员', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('954', '269', 'AospUWLm', '务农或临时务工 ', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('955', '269', 'Ujoqx5aY', '因身体或其他原因无法就业、失踪（联）或去世', '6', '87');
INSERT INTO `tb_erm_field_val` VALUES ('956', '270', 'LfzBcwFl', '公司股东或高管、私营业主', '0', '87');
INSERT INTO `tb_erm_field_val` VALUES ('957', '270', 'v2NwQxfq', '公务员、事业单位或国有企业工作人员、个体经营户', '1', '87');
INSERT INTO `tb_erm_field_val` VALUES ('958', '270', 'spTZbHsK', '进城务工人员或合同制工作人员 ', '2', '87');
INSERT INTO `tb_erm_field_val` VALUES ('959', '270', 'tjVX3K3l', '务农或临时务工 ', '3', '87');
INSERT INTO `tb_erm_field_val` VALUES ('960', '270', '6oLbVqvd', '因身体或其他原因无法就业、失踪（联）或去世', '6', '87');

-- ----------------------------
-- Table structure for tb_erm_funded
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='资助信息';

-- ----------------------------
-- Records of tb_erm_funded
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_funded_info
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8 COMMENT='学生资助信息表';

-- ----------------------------
-- Records of tb_erm_funded_info
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_funed_notice
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='通知信息';

-- ----------------------------
-- Records of tb_erm_funed_notice
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_log`;
CREATE TABLE `tb_erm_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(100) DEFAULT NULL COMMENT '服务ID',
  `create_id` int(11) DEFAULT '0' COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `method` varchar(100) DEFAULT NULL COMMENT '操作方式',
  `msg` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_date` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=3273 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
-- Records of tb_erm_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_report
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='报表信息表';

-- ----------------------------
-- Records of tb_erm_report
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_report_sub
-- ----------------------------
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

-- ----------------------------
-- Records of tb_erm_report_sub
-- ----------------------------

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
-- Table structure for tb_erm_sch_field
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=318 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_erm_sch_field
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=utf8 COMMENT='学校信息';

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

-- ----------------------------
-- Table structure for tb_erm_school_funded
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='项目资助';

-- ----------------------------
-- Records of tb_erm_school_funded
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_stu_value
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_erm_stu_value
-- ----------------------------

-- ----------------------------
-- Table structure for tb_erm_student
-- ----------------------------
DROP TABLE IF EXISTS `tb_erm_student`;
CREATE TABLE `tb_erm_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `family_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `gender` tinyint(4) DEFAULT NULL,
  `birth` varchar(20) DEFAULT NULL,
  `stuno` varchar(50) DEFAULT '' COMMENT '学号',
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
  `addressProvince` varchar(255) DEFAULT NULL COMMENT '省',
  `addressArea` varchar(255) DEFAULT NULL COMMENT '区',
  `addressCity` varchar(255) DEFAULT NULL COMMENT '市',
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
  `lodging` varchar(100) DEFAULT '' COMMENT '是否寄宿 否：不寄宿，是：寄宿',
  `helper` varchar(100) DEFAULT NULL COMMENT '帮付人',
  `is_archives` int(4) DEFAULT NULL COMMENT '是否建档',
  `archive_name` varchar(200) DEFAULT NULL COMMENT '建档立卡人姓名',
  `archive_idcard` varchar(200) DEFAULT NULL COMMENT '建档立卡人身份证',
  `archiveRelation` varchar(255) DEFAULT '' COMMENT '湖南省扶贫补贴明白折（建档立卡）人关系',
  `archiveAcount` varchar(200) DEFAULT '' COMMENT '湖南省扶贫补贴明白折（建档立卡）人账号',
  `supportName` varchar(255) DEFAULT '' COMMENT '学生资助卡姓名',
  `supportBankCard` varchar(255) DEFAULT '' COMMENT '学生资助卡银行账号',
  `helperName` varchar(255) DEFAULT '' COMMENT '帮扶人姓名',
  `helperWorkPlace` varchar(255) DEFAULT '' COMMENT '帮扶人单位',
  `helperPosition` varchar(255) DEFAULT '' COMMENT '帮扶人职位',
  `helperTel` varchar(255) DEFAULT '' COMMENT '帮扶人联系电话',
  `familyNum` int(6) DEFAULT NULL COMMENT '人口数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sch_idcard` (`school_id`,`idcard`) USING BTREE,
  KEY `FK_Reference_3` (`school_id`) USING BTREE,
  KEY `FK_Reference_4` (`family_id`) USING BTREE,
  CONSTRAINT `tb_erm_student_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `tb_erm_school` (`id`),
  CONSTRAINT `tb_erm_student_ibfk_2` FOREIGN KEY (`family_id`) REFERENCES `tb_erm_family` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2359 DEFAULT CHARSET=utf8 COMMENT='学生信息';

-- ----------------------------
-- Records of tb_erm_student
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
