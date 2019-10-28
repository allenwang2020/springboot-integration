CREATE DATABASE IF NOT EXISTS seckill default charset utf8 COLLATE utf8_general_ci;

use seckill;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sk_goods
-- ----------------------------
DROP TABLE IF EXISTS `sk_goods`;
CREATE TABLE `sk_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(30) DEFAULT NULL COMMENT '商品名稱',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品標題',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品圖片',
  `goods_detail` longtext COMMENT '商品詳情',
  `goods_price` decimal(10,2) DEFAULT NULL,
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品庫存，-1表示没有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_goods
-- ----------------------------
INSERT INTO `sk_goods` VALUES ('1', 'iphoneX', 'Apple/蘋果iPhone X 4G手機蘋果X 10', '/img/iphonex.png', 'Apple/蘋果iPhone X 4G手機蘋果X 10', '7788.00', '100');
INSERT INTO `sk_goods` VALUES ('2', '華為 Mate 10', 'Huawei/華為 Mate 10 6G+128G 4G手機', '/img/meta10.png', 'Huawei/華為 Mate 10 6G+128G 4G手機', '4199.00', '50');

-- ----------------------------
-- Table structure for sk_goods_seckill
-- ----------------------------
DROP TABLE IF EXISTS `sk_goods_seckill`;
CREATE TABLE `sk_goods_seckill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒殺商品id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,2) DEFAULT '0.00' COMMENT '秒殺價',
  `stock_count` int(11) DEFAULT NULL COMMENT '庫存數量',
  `start_date` datetime DEFAULT NULL COMMENT '秒殺開始時間',
  `end_date` datetime DEFAULT NULL COMMENT '秒殺结束時間',
  `version` int(11) DEFAULT NULL COMMENT '開發版本控制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_goods_seckill
-- ----------------------------
INSERT INTO `sk_goods_seckill` VALUES ('1', '1', '0.01', '8', '2018-05-22 17:22:52', '2018-05-22 18:23:00', '0');
INSERT INTO `sk_goods_seckill` VALUES ('2', '2', '0.01', '8', '2018-04-29 22:56:10', '2018-05-01 22:56:15', '0');


-- ----------------------------
-- Table structure for sk_order
-- ----------------------------
DROP TABLE IF EXISTS `sk_order`;
CREATE TABLE `sk_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_uid_gid` (`user_id`,`goods_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_order
-- ----------------------------
INSERT INTO `sk_order` VALUES ('10', '18718185897', '1', '1');

-- ----------------------------
-- Table structure for sk_order_info
-- ----------------------------
DROP TABLE IF EXISTS `sk_order_info`;
CREATE TABLE `sk_order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `delivery_addr_id` bigint(20) DEFAULT NULL,
  `goods_name` varchar(30) DEFAULT NULL,
  `goods_count` int(11) DEFAULT NULL,
  `goods_price` decimal(10,2) DEFAULT NULL,
  `order_channel` tinyint(4) DEFAULT NULL COMMENT '訂單通路，1線上，2android，3ios',
  `status` tinyint(4) DEFAULT NULL COMMENT '訂單狀態，0新建未支付，1已支付，2已出貨，3已收貨，4已退款，5已完成',
  `create_date` datetime DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_order_info
-- ----------------------------
INSERT INTO `sk_order_info` VALUES ('10', '18718185897', '1', null, 'iphoneX', '1', '7788.00', '1', '0', '2018-05-29 17:02:00', null);

-- ----------------------------
-- Table structure for sk_user
-- ----------------------------
DROP TABLE IF EXISTS `sk_user`;
CREATE TABLE `sk_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '用戶id',
  `nickname` varchar(255) NOT NULL COMMENT '暱稱',
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt',
  `salt` varchar(10) DEFAULT NULL COMMENT '混淆盤',
  `head` varchar(128) DEFAULT NULL COMMENT '頭像，雲端儲存的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注冊時間',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登錄時間',
  `login_count` int(11) DEFAULT NULL COMMENT '登入次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sk_user
-- ----------------------------
INSERT INTO `sk_user` VALUES ('18181818181', 'jesper', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', null, '2018-05-21 21:10:21', '2018-05-21 21:10:25', '1');
INSERT INTO `sk_user` VALUES ('18217272828', 'jesper', 'b7797cce01b4b131b433b6acf4add449', '1a2b3c4d', null, '2018-05-21 21:10:21', '2018-05-21 21:10:25', '1');