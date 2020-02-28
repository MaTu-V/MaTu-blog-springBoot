/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : mt-blog

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2020-02-15 16:25:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mt-article
-- ----------------------------
DROP TABLE IF EXISTS `mt-article`;
CREATE TABLE `mt-article` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '文章标题',
  `summary` text CHARACTER SET utf8 NOT NULL COMMENT '文章摘要',
  `content` longtext CHARACTER SET utf8 NOT NULL COMMENT '文章内容',
  `image` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '文章图片',
  `keywords` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '文章关键字',
  `user_id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '文章作者',
  `category_id` int(11) NOT NULL COMMENT '文章分类',
  `establish_time` datetime DEFAULT NULL COMMENT '文章创建时间',
  PRIMARY KEY (`id`),
  KEY `mt-article_ibfk_1` (`category_id`),
  KEY `mt-article_ibfk_2` (`user_id`),
  CONSTRAINT `mt-article_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `mt-category` (`id`) ON DELETE NO ACTION,
  CONSTRAINT `mt-article_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `mt-user` (`id`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是文章详情列表';

-- ----------------------------
-- Table structure for mt-article-information
-- ----------------------------
DROP TABLE IF EXISTS `mt-article-information`;
CREATE TABLE `mt-article-information` (
  `article_id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '文章id',
  `like_counts` int(11) unsigned zerofill DEFAULT NULL COMMENT '受赞数',
  `click_counts` int(11) unsigned zerofill DEFAULT NULL COMMENT '浏览数',
  `comment_counts` int(11) unsigned zerofill DEFAULT NULL COMMENT '评论数',
  `collection_counts` int(11) DEFAULT NULL,
  PRIMARY KEY (`article_id`),
  CONSTRAINT `mt-article-information_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mt-article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是文章点赞等信息表';

-- ----------------------------
-- Table structure for mt-article-label
-- ----------------------------
DROP TABLE IF EXISTS `mt-article-label`;
CREATE TABLE `mt-article-label` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '文章id',
  `label_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`),
  KEY `mt-article-label_ibfk_1` (`article_id`),
  KEY `mt-article-label_ibfk_2` (`label_id`),
  CONSTRAINT `mt-article-label_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mt-article` (`id`) ON DELETE CASCADE,
  CONSTRAINT `mt-article-label_ibfk_2` FOREIGN KEY (`label_id`) REFERENCES `mt-label` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1 COMMENT='这是文章标签对应表';

-- ----------------------------
-- Table structure for mt-article-user
-- ----------------------------
DROP TABLE IF EXISTS `mt-article-user`;
CREATE TABLE `mt-article-user` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `article_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '文章id',
  `user_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `mt-article-user_ibfk_1` (`article_id`),
  KEY `mt-article-user_ibfk_2` (`user_id`),
  CONSTRAINT `mt-article-user_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mt-article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mt-article-user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `mt-user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for mt-category
-- ----------------------------
DROP TABLE IF EXISTS `mt-category`;
CREATE TABLE `mt-category` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `category_name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COMMENT='分类表';

-- ----------------------------
-- Table structure for mt-comment
-- ----------------------------
DROP TABLE IF EXISTS `mt-comment`;
CREATE TABLE `mt-comment` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `content` text CHARACTER SET utf8 NOT NULL COMMENT '评论内容',
  `comment_time` datetime DEFAULT NULL COMMENT '评论时间',
  `like_counts` int(11) DEFAULT NULL COMMENT '喜欢数量',
  `state` int(11) NOT NULL COMMENT '评论状态(0:评论默认通过.1:评论违规)',
  `user_id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '评论人id',
  `article_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '评论文章id',
  `parent_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '父评论id',
  PRIMARY KEY (`id`),
  KEY `mt-comment_ibfk_3` (`parent_id`),
  KEY `mt-comment_ibfk_1` (`article_id`),
  KEY `mt-comment_ibfk_4` (`user_id`),
  CONSTRAINT `mt-comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `mt-article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mt-comment_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `mt-comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mt-comment_ibfk_4` FOREIGN KEY (`user_id`) REFERENCES `mt-user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是评论表';

-- ----------------------------
-- Table structure for mt-label
-- ----------------------------
DROP TABLE IF EXISTS `mt-label`;
CREATE TABLE `mt-label` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `label_name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '标签名称',
  `back` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '标签背景色',
  `category_id` int(11) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`),
  KEY `mt-label_ibfk_1` (`category_id`),
  CONSTRAINT `mt-label_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `mt-category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1 COMMENT='这是标签表';

-- ----------------------------
-- Table structure for mt-link
-- ----------------------------
DROP TABLE IF EXISTS `mt-link`;
CREATE TABLE `mt-link` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `author` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '友链博主',
  `web_name` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '友链网址名称',
  `url` varchar(500) CHARACTER SET utf8 NOT NULL COMMENT '友链地址',
  `describe` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '友链描述',
  `state` int(11) DEFAULT NULL COMMENT '友链状态(0申请中,1通过)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是友链表';

-- ----------------------------
-- Table structure for mt-permission
-- ----------------------------
DROP TABLE IF EXISTS `mt-permission`;
CREATE TABLE `mt-permission` (
  `id` int(11) NOT NULL COMMENT '主键',
  `permission_name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '权限名',
  `url` varchar(255) DEFAULT NULL COMMENT '访问路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for mt-role
-- ----------------------------
DROP TABLE IF EXISTS `mt-role`;
CREATE TABLE `mt-role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '角色名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for mt-role-permission
-- ----------------------------
DROP TABLE IF EXISTS `mt-role-permission`;
CREATE TABLE `mt-role-permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `mt-role-permission_ibfk_1` (`role_id`),
  KEY `mt-role-permission_ibfk_2` (`permission_id`),
  CONSTRAINT `mt-role-permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `mt-role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mt-role-permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `mt-permission` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for mt-user
-- ----------------------------
DROP TABLE IF EXISTS `mt-user`;
CREATE TABLE `mt-user` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `nick_name` varchar(200) CHARACTER SET utf8mb4 NOT NULL COMMENT '用户昵称',
  `password` varchar(100) CHARACTER SET utf8 NOT NULL COMMENT '用户密码',
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户头像',
  `describe` varchar(400) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '个人描述',
  `phone` varchar(200) NOT NULL COMMENT '联系方式',
  `qq` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT 'QQopenid',
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱地址',
  `role_id` int(11) NOT NULL DEFAULT '3' COMMENT '角色(1超级管理|2普通管理|3普通用户)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近登录时间',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `mt-user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `mt-role` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是用户信息表';

-- ----------------------------
-- Table structure for mt-user-follow
-- ----------------------------
DROP TABLE IF EXISTS `mt-user-follow`;
CREATE TABLE `mt-user-follow` (
  `id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `follow_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '跟随人id',
  `user_id` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `mt-user-follow_ibfk_1` (`follow_id`),
  KEY `mt-user-follow_ibfk_2` (`user_id`),
  CONSTRAINT `mt-user-follow_ibfk_1` FOREIGN KEY (`follow_id`) REFERENCES `mt-user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mt-user-follow_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `mt-user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是网站用户背景图表';

-- ----------------------------
-- Table structure for mt-user-info
-- ----------------------------
DROP TABLE IF EXISTS `mt-user-info`;
CREATE TABLE `mt-user-info` (
  `user_id` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '用户信息',
  `follow_counts` int(11) DEFAULT NULL COMMENT '关注数',
  `fans_counts` int(11) DEFAULT NULL COMMENT '粉丝数',
  `article_counts` int(11) DEFAULT NULL COMMENT '发表文章数量',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `mt-user-info_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `mt-user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for mt-website
-- ----------------------------
DROP TABLE IF EXISTS `mt-website`;
CREATE TABLE `mt-website` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` longtext CHARACTER SET utf8 NOT NULL COMMENT '网站描述',
  `establish_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='这是网站信息表';

-- ----------------------------
-- Procedure structure for mt-article-user-focus
-- ----------------------------
DROP PROCEDURE IF EXISTS `mt-article-user-focus`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `mt-article-user-focus`(IN `BlogStatus` int,IN `Id` varchar(255),IN `articleId` varchar(255),IN `userId` varchar(255))
BEGIN
	#Routine body goes here...
# 点赞收藏评论功能
  DECLARE cnt INT DEFAULT 0;
  SELECT count(1) INTO cnt FROM `mt-article-information` AS mai WHERE mai.article_id = articleId;
IF cnt < 1 THEN 
  INSERT INTO `mt-article-information` VALUES(articleId,0,0,0,0);
END IF;  
  case BlogStatus
    when '-1' then 
      UPDATE `mt-article-information` as mai SET mai.like_counts = (mai.like_counts-1) WHERE mai.article_id =articleId;
    when '1' then 
      UPDATE `mt-article-information` as mai SET mai.like_counts = (mai.like_counts+1) WHERE mai.article_id =articleId;  
    when '-2' then 
      UPDATE `mt-article-information` as mai SET mai.click_counts = (mai.click_counts-1) WHERE mai.article_id =articleId;
    when '2' then 
      UPDATE `mt-article-information` as mai SET mai.click_counts = (mai.click_counts+1) WHERE mai.article_id =articleId;
    when '-3' then 
      UPDATE `mt-article-information` as mai SET mai.comment_counts = (mai.comment_counts-1) WHERE mai.article_id =articleId;
    when '3' then 
      UPDATE `mt-article-information` as mai SET mai.comment_counts = (mai.comment_counts+1) WHERE mai.article_id =articleId;
    when '-4' then  
      IF (articleId IS NOT NULL && userId IS NOT NULL) THEN
        DELETE FROM `mt-article-user` WHERE article_id=articleId AND user_id=userId;
        UPDATE `mt-article-information` as mai SET mai.collection_counts = (mai.collection_counts-1) WHERE mai.article_id =articleId;
    END IF;
    when '4' then  
      IF (Id IS NOT NULL && userId IS NOT NULL) THEN
        INSERT INTO `mt-article-user` VALUES(Id,articleId,userId);
        UPDATE `mt-article-information` as mai SET mai.collection_counts = (mai.collection_counts+1) WHERE mai.article_id =articleId;
    END IF;
  END CASE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for mt-user-info-follow
-- ----------------------------
DROP PROCEDURE IF EXISTS `mt-user-info-follow`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `mt-user-info-follow`(IN `BlogStatus` int,IN `Id` varchar(255),IN `followId` varchar(255),IN `userId` varchar(255))
BEGIN
	#Routine body goes here...
# 用户关注 发表作品数据统计功能
  DECLARE follow_cnt INT DEFAULT 0;
  DECLARE user_cnt INT DEFAULT 0;
IF followId IS NOT NULL THEN
  SELECT count(1) INTO follow_cnt FROM `mt-user-info` AS mui WHERE mui.user_id = followId;
  IF follow_cnt < 1 THEN 
    INSERT INTO `mt-user-info` VALUES(followId,0,0,0);
  END IF;
END IF;
IF userId IS NOT NULL THEN
  SELECT count(1) INTO user_cnt FROM `mt-user-info` AS mui WHERE mui.user_id = userId;
  IF user_cnt < 1 THEN 
    INSERT INTO `mt-user-info` VALUES(userId,0,0,0);
  END IF;
END IF;
case BlogStatus
    when '-1' THEN 
      DELETE FROM `mt-user-follow` WHERE follow_id=followId AND user_id=userId; #  删除关注信息
      UPDATE `mt-user-info` as mui SET mui.fans_counts = (mui.fans_counts-1) WHERE mui.user_id = followId; # 被关注人粉丝 - 1
      UPDATE `mt-user-info` as mui SET mui.follow_counts = (mui.follow_counts-1) WHERE mui.user_id = userId; # 关注人关注数 - 1
    when '1' THEN 
      INSERT INTO `mt-user-follow` VALUES(Id,followId,userId); # 添加关注信息
      UPDATE `mt-user-info` as mui SET mui.fans_counts = (mui.fans_counts+1) WHERE mui.user_id = followId; # 被关注人粉丝 + 1
      UPDATE `mt-user-info` as mui SET mui.follow_counts = (mui.follow_counts+1) WHERE mui.user_id = userId; # 关注人关注数 + 1
   when '-2' THEN 
      UPDATE `mt-user-info` as mui SET mui.article_counts = (mui.article_counts-1) WHERE mui.user_id = userId;
    when '2' THEN 
      UPDATE `mt-user-info` as mui SET mui.article_counts = (mui.article_counts+1) WHERE mui.user_id = userId;
  END CASE;
END
;;
DELIMITER ;
