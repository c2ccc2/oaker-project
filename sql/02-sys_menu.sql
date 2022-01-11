/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.31-hdfr
 Source Server Type    : MySQL
 Source Server Version : 100604
 Source Host           : 192.168.1.31:3306
 Source Schema         : jx_project_hdfr

 Target Server Type    : MySQL
 Target Server Version : 100604
 File Encoding         : 65001

 Date: 12/11/2021 10:37:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2057 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 9, 'system', NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', '2021-08-22 08:37:02', 'admin', '2021-10-22 15:15:41', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 5, 'monitor', NULL, 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2021-08-22 08:37:02', 'admin', '2021-10-22 15:15:26', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 4, 'tool', NULL, 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2021-08-22 08:37:02', 'admin', '2021-10-22 15:15:17', '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, 'test', 0, 9999, 'http://www.hdfr.com.cn', NULL, 0, 0, 'M', '1', '1', '', 'guide', 'admin', '2021-08-22 08:37:02', 'admin', '2021-10-22 15:16:21', '若依官网地址');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 2000, 4, 'user', 'system/user/index', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2021-08-22 08:37:02', 'admin', '2021-09-01 05:49:19', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 2000, 2, 'role', 'system/role/index', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2021-08-22 08:37:02', 'admin', '2021-08-31 05:55:48', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2021-08-22 08:37:02', '', NULL, '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2021-08-22 08:37:02', '', NULL, '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '职位管理', 2000, 3, 'post', 'system/post/index', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2021-08-22 08:37:02', 'admin', '2021-09-01 05:49:07', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2021-08-22 08:37:02', '', NULL, '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2021-08-22 08:37:02', '', NULL, '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2021-08-22 08:37:02', '', NULL, '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2021-08-22 08:37:02', '', NULL, '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2021-08-22 08:37:02', '', NULL, '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2021-08-22 08:37:02', '', NULL, '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2021-08-22 08:37:02', '', NULL, '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2021-08-22 08:37:02', '', NULL, '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2021-08-22 08:37:02', '', NULL, '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '表单构建', 3, 1, 'build', 'tool/build/index', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2021-08-22 08:37:02', '', NULL, '表单构建菜单');
INSERT INTO `sys_menu` VALUES (115, '代码生成', 3, 2, 'gen', 'tool/gen/index', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2021-08-22 08:37:02', '', NULL, '代码生成菜单');
INSERT INTO `sys_menu` VALUES (116, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2021-08-22 08:37:02', '', NULL, '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2021-08-22 08:37:02', '', NULL, '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2021-08-22 08:37:02', '', NULL, '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1017, '部门查询', 103, 1, '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1018, '部门新增', 103, 2, '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1019, '部门修改', 103, 3, '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1020, '部门删除', 103, 4, '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1021, '岗位查询', 104, 1, '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1022, '岗位新增', 104, 2, '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1023, '岗位修改', 104, 3, '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1024, '岗位删除', 104, 4, '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1025, '岗位导出', 104, 5, '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1026, '字典查询', 105, 1, '#', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1027, '字典新增', 105, 2, '#', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1028, '字典修改', 105, 3, '#', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1029, '字典删除', 105, 4, '#', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1030, '字典导出', 105, 5, '#', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1031, '参数查询', 106, 1, '#', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1032, '参数新增', 106, 2, '#', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1033, '参数修改', 106, 3, '#', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1034, '参数删除', 106, 4, '#', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1035, '参数导出', 106, 5, '#', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1036, '公告查询', 107, 1, '#', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1037, '公告新增', 107, 2, '#', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1038, '公告修改', 107, 3, '#', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1039, '公告删除', 107, 4, '#', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1040, '操作查询', 500, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1041, '操作删除', 500, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 7, '#', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 115, 1, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 115, 2, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 115, 3, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 115, 2, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 115, 4, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 115, 5, '#', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2021-08-22 08:37:02', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2000, '管理', 0, 2, 'manage', NULL, 1, 0, 'M', '0', '0', '', 'logininfor', 'admin', '2021-08-30 06:38:25', 'admin', '2021-10-22 15:15:01', '');
INSERT INTO `sys_menu` VALUES (2001, '项目管理', 2000, 1, 'project', 'system/project', 1, 0, 'C', '0', '0', 'system:project:list', 'example', 'admin', '2021-08-30 06:40:28', 'admin', '2021-10-27 16:54:38', '');
INSERT INTO `sys_menu` VALUES (2002, '工时', 0, 1, 'workingHours', NULL, 1, 0, 'M', '0', '0', '', 'time', 'admin', '2021-09-01 02:12:05', 'admin', '2021-10-22 15:14:53', '');
INSERT INTO `sys_menu` VALUES (2003, '我的工时', 2002, 1, 'myWorkingHours', 'workingHours/myWorkingHours/index', 1, 0, 'C', '0', '0', 'mh:hour:list', 'time-range', 'admin', '2021-09-01 02:18:07', 'admin', '2021-10-27 17:27:36', '');
INSERT INTO `sys_menu` VALUES (2004, '我的统计', 2002, 2, 'myStatistics', 'workingHours/myStatistics/index', 1, 0, 'C', '0', '0', 'mh:hour:stat', 'server', 'admin', '2021-09-01 02:22:00', 'admin', '2021-10-27 16:44:31', '');
INSERT INTO `sys_menu` VALUES (2005, '项目统计', 2002, 3, 'projectManagement', 'workingHours/projectManagement/index', 1, 0, 'C', '0', '0', 'system:project:stat:query', 'cascader', 'admin', '2021-09-01 02:24:49', 'admin', '2021-10-27 16:46:07', '');
INSERT INTO `sys_menu` VALUES (2010, '项目设置', 2000, 5, 'projectSettings', 'system/projectSettings/index', 1, 0, 'C', '1', '0', '', 'system', 'admin', '2021-09-10 09:16:33', 'admin', '2021-09-27 11:08:00', '');
INSERT INTO `sys_menu` VALUES (2014, '原型', 0, 3, 'prototype', NULL, 1, 0, 'M', '0', '0', '', 'edit', 'admin', '2021-10-22 15:16:57', 'admin', '2021-10-22 15:22:27', '');
INSERT INTO `sys_menu` VALUES (2015, '项目原型', 2014, 1, 'projectPrototype', 'prototype/projectPrototype', 1, 0, 'C', '0', '0', 'pr:proto:list', 'edit', 'admin', '2021-10-22 15:17:50', 'admin', '2021-10-27 17:08:27', '');
INSERT INTO `sys_menu` VALUES (2020, '我参加的项目', 2003, 1, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:all', '#', 'admin', '2021-10-27 16:43:07', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2021, '提交工时', 2003, 1, '', NULL, 1, 0, 'F', '0', '0', 'mh:hour:add', '#', 'admin', '2021-10-27 16:43:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2022, '查看详情', 2003, 2, '', NULL, 1, 0, 'F', '0', '0', 'mh:hour:detail', '#', 'admin', '2021-10-27 16:44:08', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2024, '详细模式', 2004, 2, '', NULL, 1, 0, 'F', '0', '0', 'mh:hour:stat:detail', '#', 'admin', '2021-10-27 16:45:36', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2025, '上报记录', 2005, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:project:stat:fill:detail', '#', 'admin', '2021-10-27 16:46:35', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2026, '工时明细', 2005, 2, '', NULL, 1, 0, 'F', '0', '0', 'system:project:stat:query', '#', 'admin', '2021-10-27 16:47:03', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2027, '项目详情', 2005, 3, '', NULL, 1, 0, 'F', '0', '0', 'system:project:query', '#', 'admin', '2021-10-27 16:48:33', 'admin', '2021-10-27 16:49:02', '');
INSERT INTO `sys_menu` VALUES (2028, '项目概要', 2027, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:project:query', '#', 'admin', '2021-10-27 16:49:34', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2029, '人员管理', 2027, 2, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:list', '#', 'admin', '2021-10-27 16:50:17', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2030, '工时设置', 2027, 3, '', NULL, 1, 0, 'F', '0', '0', 'system:project:hour:edit', '#', 'admin', '2021-10-27 16:50:47', 'admin', '2021-10-28 11:59:49', '');
INSERT INTO `sys_menu` VALUES (2031, '项目管理', 2027, 4, '', NULL, 1, 0, 'F', '0', '0', 'system:project:status:edit', '#', 'admin', '2021-10-27 16:51:18', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2032, '编辑', 2028, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:project:edit', '#', 'admin', '2021-10-27 16:52:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2033, '添加项目成员', 2029, 1, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:add', '#', 'admin', '2021-10-27 16:52:51', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2034, '移除项目人员', 2029, 2, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:remove', '#', 'admin', '2021-10-27 16:53:24', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2036, '创建项目', 2001, 0, '', NULL, 1, 0, 'F', '0', '0', 'system:project:add', '#', 'admin', '2021-10-27 16:55:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2037, '项目设置', 2001, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:project:query', '#', 'admin', '2021-10-27 17:02:40', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2038, '归档', 2001, 2, '', NULL, 1, 0, 'F', '0', '0', 'system:project:status:edit', '#', 'admin', '2021-10-27 17:03:13', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2039, '删除', 2001, 3, '', NULL, 1, 0, 'F', '0', '0', 'system:project:remove', '#', 'admin', '2021-10-27 17:03:38', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2040, '项目概要', 2037, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:project:query', '#', 'admin', '2021-10-27 17:04:18', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2041, '人员管理', 2037, 2, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:list', '#', 'admin', '2021-10-27 17:04:51', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2042, '工时设置', 2037, 3, '', NULL, 1, 0, 'F', '0', '0', 'system:project:hour:edit', '#', 'admin', '2021-10-27 17:05:15', 'admin', '2021-10-28 12:00:21', '');
INSERT INTO `sys_menu` VALUES (2043, '项目管理', 2037, 4, '', NULL, 1, 0, 'F', '0', '0', 'system:project:status:edit', '#', 'admin', '2021-10-27 17:05:45', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2044, '编辑', 2040, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:project:edit', '#', 'admin', '2021-10-27 17:06:13', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2045, '添加项目成员', 2041, 1, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:add', '#', 'admin', '2021-10-27 17:06:52', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2046, '移除项目成员', 2041, 2, '', NULL, 1, 0, 'F', '0', '0', 'mh:project:user:remove', '#', 'admin', '2021-10-27 17:07:20', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2048, '上传原型', 2015, 1, '', NULL, 1, 0, 'F', '0', '0', 'pr:proto:upload', '#', 'admin', '2021-10-27 17:09:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2049, '文档管理', 2015, 2, '', NULL, 1, 0, 'F', '0', '0', 'pr:doc:query', '#', 'admin', '2021-10-27 17:09:30', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2050, '原型管理', 2015, 3, '', NULL, 1, 0, 'F', '0', '0', 'pr:proto:record:list', '#', 'admin', '2021-10-27 17:09:59', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2051, '原型编辑', 2015, 4, '', NULL, 1, 0, 'F', '0', '0', 'pr:proto:update', '#', 'admin', '2021-10-27 17:10:28', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2052, '原型删除', 2015, 5, '', NULL, 1, 0, 'F', '0', '0', 'pr:proto:delete', '#', 'admin', '2021-10-27 17:11:00', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2053, '文档删除', 2049, 1, '', NULL, 1, 0, 'F', '0', '0', 'pr:doc:delete', '#', 'admin', '2021-10-27 17:11:46', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2054, '删除', 2050, 1, '', NULL, 1, 0, 'F', '0', '0', 'pr:proto:record:delete', '#', 'admin', '2021-10-27 17:12:10', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2055, '创建原型', 2015, 1, '', NULL, 1, 0, 'F', '0', '0', 'pr:proto:add', '#', 'admin', '2021-10-28 10:50:39', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2056, '关于', 1, 10, 'about', 'system/about/index', 1, 0, 'C', '0', '0', 'system:about:list', 'peoples', 'admin', '2021-11-01 14:23:05', 'admin', '2021-11-01 14:23:20', '');

SET FOREIGN_KEY_CHECKS = 1;
