SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_permissions`
-- ----------------------------
DROP TABLE IF EXISTS `tb_permissions`;
CREATE TABLE `tb_permissions` (
                                  `permission_id` int NOT NULL AUTO_INCREMENT,
                                  `permission_code` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                  `permission_name` varchar(60) DEFAULT NULL,
                                  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_permissions
-- ----------------------------
INSERT INTO `tb_permissions` VALUES ('1', 'sys:c:save', '入库');
INSERT INTO `tb_permissions` VALUES ('2', 'sys:c:delete', '出库');
INSERT INTO `tb_permissions` VALUES ('3', 'sys:c:update', '修改');
INSERT INTO `tb_permissions` VALUES ('4', 'sys:c:find', '查询');
INSERT INTO `tb_permissions` VALUES ('5', 'sys:x:save', '新增订单');
INSERT INTO `tb_permissions` VALUES ('6', 'sys:x:delete', '删除订单');
INSERT INTO `tb_permissions` VALUES ('7', 'sys:x:update', '修改订单');
INSERT INTO `tb_permissions` VALUES ('8', 'sys:x:find', '查询订单');
INSERT INTO `tb_permissions` VALUES ('9', 'sys:k:save', '新增客户');
INSERT INTO `tb_permissions` VALUES ('10', 'sys:k:delete', '删除客户');
INSERT INTO `tb_permissions` VALUES ('11', 'sys:k:update', '修改客户');
INSERT INTO `tb_permissions` VALUES ('12', 'sys:k:find', '查询客户');

-- ----------------------------
-- Table structure for `tb_roles`
-- ----------------------------
DROP TABLE IF EXISTS `tb_roles`;
CREATE TABLE `tb_roles` (
                            `role_id` int NOT NULL AUTO_INCREMENT,
                            `role_name` varchar(60) NOT NULL,
                            PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_roles
-- ----------------------------
INSERT INTO `tb_roles` VALUES ('1', 'admin');
INSERT INTO `tb_roles` VALUES ('2', 'cmanager');
INSERT INTO `tb_roles` VALUES ('3', 'xmanager');
INSERT INTO `tb_roles` VALUES ('4', 'kmanager');
INSERT INTO `tb_roles` VALUES ('5', 'zmanager');

-- ----------------------------
-- Table structure for `tb_roles_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_roles_permission`;
CREATE TABLE `tb_roles_permission` (
                                       `rid` int NOT NULL,
                                       `pid` int NOT NULL,
                                       KEY `FK_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_roles_permission
-- ----------------------------
INSERT INTO `tb_roles_permission` VALUES ('2', '1');
INSERT INTO `tb_roles_permission` VALUES ('2', '2');
INSERT INTO `tb_roles_permission` VALUES ('2', '3');
INSERT INTO `tb_roles_permission` VALUES ('2', '4');
INSERT INTO `tb_roles_permission` VALUES ('3', '5');
INSERT INTO `tb_roles_permission` VALUES ('3', '6');
INSERT INTO `tb_roles_permission` VALUES ('3', '7');
INSERT INTO `tb_roles_permission` VALUES ('3', '8');
INSERT INTO `tb_roles_permission` VALUES ('3', '9');
INSERT INTO `tb_roles_permission` VALUES ('3', '10');
INSERT INTO `tb_roles_permission` VALUES ('3', '11');
INSERT INTO `tb_roles_permission` VALUES ('3', '12');
INSERT INTO `tb_roles_permission` VALUES ('3', '4');
INSERT INTO `tb_roles_permission` VALUES ('4', '11');
INSERT INTO `tb_roles_permission` VALUES ('4', '12');
INSERT INTO `tb_roles_permission` VALUES ('5', '4');
INSERT INTO `tb_roles_permission` VALUES ('5', '8');
INSERT INTO `tb_roles_permission` VALUES ('5', '12');

-- ----------------------------
-- Table structure for `tb_users`
-- ----------------------------
DROP TABLE IF EXISTS `tb_users`;
CREATE TABLE `tb_users` (
                            `user_id` int NOT NULL AUTO_INCREMENT,
                            `username` varchar(20) NOT NULL,
                            `password` varchar(20) NOT NULL,
                            `password_salt` varchar(60) DEFAULT NULL,
                            PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_users
-- ----------------------------
INSERT INTO `tb_users` VALUES ('1', 'zhangsan', '123456', null);
INSERT INTO `tb_users` VALUES ('2', 'lisi', '123456', null);
INSERT INTO `tb_users` VALUES ('3', 'wangwu', '123456', null);
INSERT INTO `tb_users` VALUES ('4', 'zhaoliu', '123456', null);
INSERT INTO `tb_users` VALUES ('5', 'chenqi', '123456', null);

-- ----------------------------
-- Table structure for `tb_users_roles`
-- ----------------------------
DROP TABLE IF EXISTS `tb_users_roles`;
CREATE TABLE `tb_users_roles` (
                                  `uid` int NOT NULL,
                                  `rid` int NOT NULL,
                                  KEY `FK_role` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tb_users_roles
-- ----------------------------
INSERT INTO `tb_users_roles` VALUES ('1', '1');
INSERT INTO `tb_users_roles` VALUES ('2', '2');
INSERT INTO `tb_users_roles` VALUES ('3', '3');
INSERT INTO `tb_users_roles` VALUES ('4', '4');
INSERT INTO `tb_users_roles` VALUES ('5', '5');
INSERT INTO `tb_users_roles` VALUES ('1', '2');
INSERT INTO `tb_users_roles` VALUES ('1', '3');
INSERT INTO `tb_users_roles` VALUES ('1', '4');
INSERT INTO `tb_users_roles` VALUES ('1', '5');