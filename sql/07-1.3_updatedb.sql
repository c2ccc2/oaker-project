-- 增加日报字段
ALTER TABLE `mh_hour_detail` ADD COLUMN `daily` varchar(255) NULL COMMENT '日报内容' AFTER `everyday`;

-- 缺报详情表增加项目状态字段
ALTER TABLE `mh_user_hour_miss_detail`
ADD COLUMN `project_status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'a' COMMENT '项目状态：a 进行中，b运维，c 结束' AFTER `project_id`;

-- 请假表新增类型字段
ALTER TABLE `mh_user_leave`
ADD COLUMN `leave_type` int NOT NULL DEFAULT 1 COMMENT '类型：1请假  2倒休' AFTER `leave_date`;

-- 用户请假详情表
CREATE TABLE `mh_user_leave_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `leave_id` bigint(20) NOT NULL COMMENT '缺报记录表id',
  `project_id` bigint(20) NOT NULL COMMENT '项目id',
  `project_status` varchar(10) NOT NULL DEFAULT 'a' COMMENT '项目状态：a 进行中，b运维，c 结束',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `leave_date` date NOT NULL COMMENT '缺报日期',
  `leave_type` int(11) NOT NULL DEFAULT 1 COMMENT '类型：1请假  2倒休',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `project_id_index` (`project_id`) USING BTREE COMMENT '项目id索引',
  KEY `user_id_index` (`user_id`) USING BTREE COMMENT '用户id索引',
  KEY `leave_id_index` (`leave_id`) USING BTREE COMMENT '请假记录id索引'
) ENGINE=InnoDB AUTO_INCREMENT=2128 DEFAULT CHARSET=utf8mb3 COMMENT='用户请假详情表';

-- 增加日报菜单
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2069, '项目日报', 2002, 2, 'projectDai', 'workingHours/projectDai', 1, 0, 'C', '0', '0', 'system:project:daily', 'date', 'LSM-admin', '2021-12-14 14:38:36', 'admin', '2021-12-15 16:50:09', '');

-- 原型表增加效果图字段
ALTER TABLE `pr_prototype` ADD COLUMN `sketch_id` bigint NULL COMMENT '效果图id' AFTER `record_id`;

-- 效果图记录表
CREATE TABLE `pr_sketch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  `prototype_id` bigint(20) NOT NULL COMMENT '原型id',
  `path` varchar(255) DEFAULT NULL COMMENT '文件存放路径',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `prototype_id_index` (`prototype_id`) USING BTREE COMMENT '原型id索引'
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='效果图记录表';

-- 效果图文件表
CREATE TABLE `pr_sketch_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sketch_id` bigint(20) NOT NULL COMMENT '效果图记录id',
  `file_name` varchar(64) NOT NULL COMMENT '文档名称',
  `prototype_id` bigint(20) NOT NULL COMMENT '原型记录id',
  `file_url` varchar(255) NOT NULL COMMENT '文档地址',
  `path` varchar(255) NOT NULL COMMENT '文件存放路径',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `sketch_id_index` (`sketch_id`) USING BTREE COMMENT '效果图记录id索引',
  KEY `prototype_id_index` (`prototype_id`) USING BTREE COMMENT '原型id索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='效果图文件表';

-- 增加效果图按钮
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES (2070, '上传效果图', 2015, 4, '', NULL, 1, 0, 'F', '0', '0', 'pr:sketch:upload', '#', 'admin', '2021-12-21 15:11:10', '', NULL, '');
