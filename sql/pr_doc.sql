/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.21-dev-jx
 Source Server Type    : MySQL
 Source Server Version : 100604
 Source Host           : 192.168.1.21:3306
 Source Schema         : jx_project_dev

 Target Server Type    : MySQL
 Target Server Version : 100604
 File Encoding         : 65001

 Date: 22/10/2021 18:29:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pr_doc
-- ----------------------------
DROP TABLE IF EXISTS `pr_doc`;
CREATE TABLE `pr_doc`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `doc_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档名称',
  `prototype_id` bigint NOT NULL COMMENT '原型记录id',
  `doc_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文档地址',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件存放路径',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `prototype_id_index`(`prototype_id`) USING BTREE COMMENT '原型id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '原型文档' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pr_prototype
-- ----------------------------
DROP TABLE IF EXISTS `pr_prototype`;
CREATE TABLE `pr_prototype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(64) NOT NULL COMMENT '名称',
  `project_id` bigint(20) DEFAULT NULL COMMENT '项目id',
  `record_id` bigint(20) DEFAULT NULL COMMENT '原型地址记录id',
  `pub_id` varchar(64) NOT NULL COMMENT '原型公开id',
  `remark` text DEFAULT NULL COMMENT '备注说明',
  `deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除：0未删除， 1已删除',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT current_timestamp() COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pub_id_index` (`pub_id`) USING HASH COMMENT '原型公开id',
  KEY `project_id_index` (`project_id`) USING BTREE COMMENT '项目id索引'
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COMMENT='原型表';

-- ----------------------------
-- Table structure for pr_prototype_record
-- ----------------------------
DROP TABLE IF EXISTS `pr_prototype_record`;
CREATE TABLE `pr_prototype_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `file_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `prototype_id` bigint NOT NULL COMMENT '原型id',
  `prototype_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '原型地址',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件存放路径',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `prototype_id_index`(`prototype_id`) USING BTREE COMMENT '原型id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '原型记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
