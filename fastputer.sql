create database fastputer_db;
use fastputer_db;


SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `tb_fastputer_admin_user`;
CREATE TABLE `tb_fastputer_admin_user`  (
                                              `admin_user_id` int(11) NOT NULL AUTO_INCREMENT,
                                              `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
                                              `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Admin nickname',
                                              `locked` tinyint(4) NULL DEFAULT 0 COMMENT '0 is good,  1 is locked cannot login',
                                              PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `tb_fastputer_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'dandan', 0);

-- ----------------------------------------------------

DROP TABLE IF EXISTS `tb_fastputer_carousel`;
CREATE TABLE `tb_fastputer_carousel`  (
                                            `carousel_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Primarykey id',
                                            `carousel_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
                                            `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '\'##\'' ,
                                            `carousel_rank` int(11) NOT NULL DEFAULT 0 COMMENT 'Rank(Larger display first)',
                                            `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1 is deleted',
                                            `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                            `create_user` int(11) NOT NULL DEFAULT 0 ,
                                            `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                            `update_user` int(11) NOT NULL DEFAULT 0 ,
                                            PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------------------------------


DROP TABLE IF EXISTS `tb_fastputer_goods_category`;
CREATE TABLE `tb_fastputer_goods_category`  (
                                                  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Category id',
                                                  `category_level` tinyint(4) NOT NULL DEFAULT 0 COMMENT 'Category level(1-level 1, 2-level 2, 3-level3)',
                                                  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Parent category id',
                                                  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
                                                  `category_rank` int(11) NOT NULL DEFAULT 0 COMMENT 'Rank, larger display first',
                                                  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1 is deleted',
                                                  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                                  `create_user` int(11) NOT NULL DEFAULT 0 ,
                                                  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                  `update_user` int(11) NULL DEFAULT 0 ,
                                                  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `tb_fastputer_goods_category` (category_id, category_level, parent_id, category_name, category_rank, is_deleted, create_time, create_user, update_time, update_user)
VALUES
(1, 1, 0, 'Components', 100, 0, '2022-03-11 18:45:40', 0, '2022-03-11 18:45:40', 0),
    (10, 2, 1, 'CPU', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
         (18, 3, 10, 'Intel CPUs', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
         (19, 3, 10, 'AMD CPUs', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),

    (11, 2, 1, 'Motherboards', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
         (20, 3, 11, 'Intel Motherboards', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
         (21, 3, 11, 'AMD Motherboards', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),

    (12, 2, 1, 'Graphic', 98, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (28, 3, 12, 'NVIDIA Video Cards', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (29, 3, 12, 'AMD Video Cards', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
    (13, 2, 1, 'Cases', 97, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (30, 3, 13, 'Full ATX Towers', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (31, 3, 13, 'Mid ATX Towers', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
    (14, 2, 1, 'Power', 96, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (32, 3, 14, '800W & Above', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (33, 3, 14, '600W to 799W', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
    (15, 2, 1, 'Memory', 95, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (34, 3, 15, 'G.Skill Memory', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (35, 3, 15, 'Corsair Memory', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (36, 3, 15, 'Crucial Memory', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
    (16, 2, 1, 'Storage', 94, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),

    (17, 2, 1, 'PC Cooling', 93, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),

(2, 1, 0, 'Computer System', 99, 0, '2022-03-11 18:46:07', 0, '2022-03-11 18:46:07', 0),
    (22, 2, 2, 'Desktop', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (24, 3, 22, 'ASUS', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (25, 3, 22, 'DELL', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
    (23, 2, 2, 'Laptop', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (26, 3, 23, 'ASUS', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (27, 3, 23, 'DELL', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),

(3, 1, 0, 'Gaming', 98, 0, '2022-03-12 00:08:46', 0, '2022-03-12 00:08:46', 0),
    (37, 2, 3, 'Xbox', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (39, 3, 37, 'Xbox Series X', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (40, 3, 37, 'Xbox Series S', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
    (38, 2, 3, 'PlayStation', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (41, 3, 38, 'PS5', 100, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),
        (42, 3, 38, 'PS4', 99, 0, '2022-03-11 18:45:40', 0, '2021-03-11 18:45:40', 0),

(4, 1, 0, 'Networking', 97, 0, '2022-03-12 00:03:00', 0, '2022-03-12 00:03:00', 0),
(5, 1, 0, 'Office Solutions', 96, 0, '2022-03-12 00:03:27', 0, '2022-03-12 00:03:27', 0),
(6, 1, 0, 'Software Services', 94, 0, '2022-03-12 00:03:51', 0, '2022-03-17 18:22:34', 0),
(7, 1, 0, 'Home Tools', 93, 0, '2022-03-12 00:10:07', 0, '2022-03-12 00:10:07', 0),
(8, 1, 0, 'AutoMotive', 92, 0, '2022-03-12 00:10:35', 0, '2022-03-12 00:16:30', 0),
(9, 1, 0, 'Toys & Drones', 91, 0, '2022-03-12 00:11:17', 0, '2022-03-12 00:11:17', 0);










-- ----------------------------------------------------

DROP TABLE IF EXISTS `tb_fastputer_goods_info`;
CREATE TABLE `tb_fastputer_goods_info`  (
                                              `goods_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Product Primary key',
                                              `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
                                              `goods_intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'Product introduction',
                                              `goods_category_id` bigint(20) NOT NULL DEFAULT 0 COMMENT 'Related category id',
                                              `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT 'Product image',
                                              `goods_carousel` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT 'Product trend image',
                                              `goods_detail_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Product deatail',
                                              `original_price` int(11) NOT NULL DEFAULT 1 ,
                                              `selling_price` int(11) NOT NULL DEFAULT 1 ,
                                              `stock_num` int(11) NOT NULL DEFAULT 0 ,
                                              `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'Product label',
                                              `goods_sell_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0 means listed, 1 means removed',
                                              `create_user` int(11) NOT NULL DEFAULT 0 ,
                                              `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              `update_user` int(11) NOT NULL DEFAULT 0 ,
                                              `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10896 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `tb_fastputer_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_carousel`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`)
VALUES
    (1000,'my cat','cat',18,'/goods-img/a.jpg','/goods-img/a.jpg','<p>This is my cat...</p>',10000000,10000000,1,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1001,'Intel Core i9-12900K','CPU',18,'/goods-img/1.jpg','/goods-img/1.jpg','<p>This is Intel!!!!!!!!!!!...</p>',649,609,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1002,'AMD Ryzen 5 5600X','CPU',19,'/goods-img/2.jpg','/goods-img/2.jpg','<p>This is AMD YES...</p>',299,229,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1003,'AMD Ryzen 7 5800X','cat',19,'/goods-img/3.jpg','/goods-img/3.jpg','<p>This is AMD YES...</p>',449,359,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1004,'ASUS ROG STRIX B550-A','Motherboard',21,'/goods-img/4.jpg','/goods-img/4.jpg','<p>This is AMD YES...</p>',179,168,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1005,'ASUS TUF Gaming Z690-Plus','Motherboard',20,'/goods-img/5.jpg','/goods-img/5.jpg','<p>This is Intel...</p>',299,289,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),

    (1006,'ASUS ROG Strix GA15DK Gaming Desktop PC','Desktop',24,'/goods-img/6.jpg','/goods-img/6.jpg','<p>This is...</p>',1999,1899,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1007,'Dell Alienware Aurora R12','Desktop',25,'/goods-img/7.jpg','/goods-img/7.jpg','<p>This is ...</p>',2229,2149,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1008,'ASUS ZenBook Duo 14 UX482','Laptop',26,'/goods-img/8.jpg','/goods-img/8.jpg','<p>This is ...</p>',1299,1199,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1009,'New Dell XPS 13.4" ','Laptop',27,'/goods-img/9.jpg','/goods-img/9.jpg','<p>This is ...</p>',1799,1699,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),

    (1010,'Microsoft Xbox Series X','Xbox',39,'/goods-img/10.jpg','/goods-img/10.jpg','<p>This is...</p>',499,499,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1011,'Microsoft Xbox Series S','Xbox',40,'/goods-img/11.jpg','/goods-img/11.jpg','<p>This is ...</p>',299.98,290.77,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1012,'PS5','PlayStation',41,'/goods-img/13.jpg','/goods-img/13.jpg','<p>This is ...</p>',858,784,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (1013,'PS4" ','PlayStation',42,'/goods-img/12.jpg','/goods-img/12.jpg','<p>This is ...</p>',529,529,1000,'',0,0,'2021-09-18 13:18:47',0,'2020-10-13 10:41:59');

-- ----------------------------------------------------


DROP TABLE IF EXISTS `tb_fastputer_index_config`;
CREATE TABLE `tb_fastputer_index_config`  (
                                                `config_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Primary keyid',
                                                `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'Display characters (cannot empty when configuring search, others can be empty)',
                                                `config_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1- Search box hot search 2- Search drop down box hot search 3-(Home)Hot products 4-(Home)New products 5-(Home)Recommended for you',
                                                `goods_id` bigint(20) NOT NULL DEFAULT 0,
                                                `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '##',
                                                `config_rank` int(11) NOT NULL DEFAULT 0 COMMENT 'Rank(Larger display first)',
                                                `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1 is deleted',
                                                `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                                `create_user` int(11) NOT NULL DEFAULT 0 ,
                                                `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ,
                                                `update_user` int(11) NULL DEFAULT 0,
                                                PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `tb_fastputer_index_config`(config_id, config_name, config_type, goods_id, redirect_url, config_rank, is_deleted, create_time, create_user, update_time, update_user)
VALUES
    (1, 'Hot product my cat', 3, 1000, '##', 10, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (2, 'Hot product Intel 12900K', 3, 1001, '##', 9, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (3, 'Hot product AMD 5600X', 3, 1002, '##', 8, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (4, 'Hot product AMD 5800X', 3, 1003, '##', 7, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),

    (5, 'New product my cat', 4, 1000, '##', 10, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (6, 'New product Intel 12900K', 4, 1001, '##', 8, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (7, 'New product AMD 5600X', 4, 1002, '##', 7, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (8, 'New product AMD 5800X', 4, 1003, '##', 6, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),

    (9, 'Recommend my cat', 5, 1000, '##', 10, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (10, 'Recommend Intel 12900K', 5, 1001, '##', 9, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (11, 'Recommend AMD 5600X', 5, 1002, '##', 8, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (12, 'Recommend AMD 5800X', 5, 1003, '##', 7, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (13, 'Recommend Xbox S', 5, 1011, '##', 6, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (14, 'Recommend PS4', 5, 1013, '##', 5, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (15, 'Recommend ASUS ROG Desktop', 5, 1006, '##', 4, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (16, 'Recommend Dell XPS 13', 5, 1009, '##', 3, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (17, 'Recommend Xbox X', 5, 1010, '##', 2, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0),
    (18, 'Recommend PS5', 5, 1012, '##', 1, 0, '2021-09-18 17:04:56', 0, '2021-09-18 17:04:56', 0);
-- ----------------------------



DROP TABLE IF EXISTS `tb_fastputer_user`;
CREATE TABLE `tb_fastputer_user`  (
                                        `user_id` bigint(20) NOT NULL AUTO_INCREMENT ,
                                        `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
                                        `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
                                        `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5 Encrypted password',
                                        `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
                                        `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
                                        `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1 is deleted',
                                        `locked_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '1 is locked',
                                        `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO tb_fastputer_user (nick_name, login_name, password_md5, introduce_sign, address, is_deleted, locked_flag, create_time)
VALUE
    ('cs3354','cs3354','e10adc3949ba59abbe56e057f20f883e','I like CS3354','CS 3354 Drive', 0,0,'2022-04-18 17:04:56')
