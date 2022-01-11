DELETE FROM sys_menu WHERE menu_id = 2056;
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'首页',		0,		0,		'index',		NULL,		1,		0,		'M',		'0',		'0',		'',		'dashboard',		'admin',		'2021-11-04 14:43:55',		'admin',		'2021-11-04 15:06:45',		'' 	);
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'首页',		2057,		0,		'/',		'index',		1,		0,		'C',		'0',		'0',		'',		'dashboard',		'admin',		'2021-11-04 14:44:54',		'admin',		'2021-11-04 15:07:02',		'' 	);
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'总体统计',		2002,		4,		'overallStatistics',		'workingHours/overallStatistics',		1,		0,		'C',		'0',		'0',		'system:project:overall:detail',		'server',		'admin',		'2021-11-10 11:44:27',		'admin',		'2021-11-11 13:43:59',		'' 	);
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'应用设置',		2000,		6,		'appsSett',		'manage/appsSett',		1,		0,		'C',		'0',		'0',		'system:app:config:edit',		'online',		'admin',		'2021-11-03 11:54:49',		'admin',		'2021-11-03 14:11:47',		'' 	);
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'系统设置',		2000,		7,		'setEditor',		'manage/setEditor',		1,		0,		'C',		'0',		'0',		'system:info:config:edit',		'system',		'admin',		'2021-11-03 11:55:46',		'admin',		'2021-11-03 14:24:33',		'' 	);
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'成本设置',		2000,		8,		'costOf',		'manage/costOf',		1,		0,		'C',		'0',		'0',		'mh:cost:set',		'form',		'admin',		'2021-11-04 13:43:30',		'admin',		'2021-11-11 11:24:28',	'' 	);
INSERT INTO `sys_menu` (	`menu_name`,	`parent_id`,	`order_num`,	`path`,	`component`,	`is_frame`,	`is_cache`,	`menu_type`,	`visible`,	`status`,	`perms`,	`icon`,	`create_by`,	`create_time`,	`update_by`,	`update_time`,	`remark` )VALUES	(		'成本统计',		2005,		3,		'',		NULL,		1,		0,		'F',		'0',		'0',		'mh:cost:query',		'#',		'admin',		'2021-11-11 11:49:21',		'',		NULL,	'' 	);
-- 增加项目启动暂停状态
ALTER TABLE `sys_project` ADD COLUMN `enable` tinyint NOT NULL DEFAULT 1 COMMENT '1 启用  0 暂停' AFTER `project_manager`;
-- 增加项目的开始时间和结束时间字段
ALTER TABLE `sys_project` ADD COLUMN `start_date` date NOT NULL COMMENT '开始日期' AFTER `deleted`,ADD COLUMN `end_date` date DEFAULT NULL COMMENT '结束日期' AFTER `start_date`;
UPDATE sys_project p SET p.start_date='2021-09-01', p.end_date='2021-12-30';
-- 新增岗位成本表
CREATE TABLE `mh_post_cost`  (
  `post_id` bigint NOT NULL COMMENT '岗位id',
  `cost` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '岗位成本',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT current_timestamp(0) COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  UNIQUE INDEX `post_id_index`(`post_id`) USING HASH COMMENT '岗位id索引'
) COMMENT = '岗位成本表';
-- 新增人员成本表
CREATE TABLE `mh_user_cost`  (
  `user_id` bigint NOT NULL COMMENT '人员id',
  `cost` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '人员成本',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT current_timestamp(0) COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) COMMENT = '人员成本表';

-- 新增成本记录表
CREATE TABLE `mh_cost` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`project_id` BIGINT ( 20 ) NOT NULL COMMENT '项目id',
	`user_id` BIGINT ( 20 ) NOT NULL COMMENT '用户id',
	`use_hour` DOUBLE ( 10, 2 ) NOT NULL DEFAULT 0.00 COMMENT '所用工时',
	`post_cost` DECIMAL ( 10, 2 ) NOT NULL DEFAULT 0.00 COMMENT '岗位成本',
	`user_cost` DECIMAL ( 10, 2 ) NOT NULL DEFAULT 0.00 COMMENT '人员成本',
	`cost_date` date DEFAULT NULL COMMENT '日期',
	`project_status` VARCHAR ( 10 ) CHARACTER
	SET utf8mb3 NOT NULL DEFAULT 'a' COMMENT '项目状态：a 进行中，b运维，c 结束',
	`create_time` datetime DEFAULT CURRENT_TIMESTAMP () COMMENT '创建时间',
	PRIMARY KEY ( `id` ),
	KEY `project_id_index` ( `project_id` ) USING BTREE COMMENT '项目id索引',
KEY `user_id_index` ( `user_id` ) USING BTREE COMMENT '用户id索引'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '成本记录表';

-- 请假记录表
CREATE TABLE `mh_user_leave` (
	`id` BIGINT ( 20 ) NOT NULL AUTO_INCREMENT COMMENT 'id',
	`user_id` BIGINT ( 20 ) NOT NULL COMMENT '用户id',
	`leave_date` date NOT NULL COMMENT '请假日期',
	`create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP () COMMENT '创建时间',
	PRIMARY KEY ( `id` ),
	KEY `leave_date_index` ( `leave_date` ) USING BTREE COMMENT '请假日期索引',
	KEY `user_id_index` ( `user_id` ) USING BTREE COMMENT '用户id索引'
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COMMENT = '用户请假记录表';

-- 项目人员表增加 每日上报临时上报字段
ALTER TABLE `sys_project_user` ADD COLUMN `everyday` tinyint NOT NULL DEFAULT 1 COMMENT '是否需要每日上报工时 0 不需要  1 需要' AFTER `remove_time`;

-- 工时填报详情表增加项目状态字段
ALTER TABLE `mh_hour_detail` ADD COLUMN `project_status` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'a' COMMENT '项目状态：a 进行中，b运维，c 结束' AFTER `fill_date`;

-- 工时填报详情表增加每日上报临时上报字段
ALTER TABLE `mh_hour_detail` ADD COLUMN `everyday` tinyint NOT NULL DEFAULT 1 COMMENT '是否为每日上报工时记录 0 否  1 是' AFTER `project_status`;

-- 缺报记录详情表增加索引
ALTER TABLE  `mh_user_hour_miss_detail` ADD INDEX `project_id_index`(`project_id`) USING BTREE COMMENT '项目id索引',ADD INDEX `user_id_index`(`user_id`) USING BTREE COMMENT '用户id索引';

-- 工时填报详情表添加索引
ALTER TABLE `mh_hour_detail` ADD INDEX `fill_data_index`(`fill_date`) USING BTREE COMMENT '填报日期索引';