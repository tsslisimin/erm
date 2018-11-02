ALTER TABLE `erm`.`tb_erm_student`
ADD COLUMN `school` VARCHAR(100) NULL COMMENT '自行填写的学校' AFTER `familyNum`;
