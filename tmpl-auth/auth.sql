-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `login_name` varchar(32) NOT NULL DEFAULT '' COMMENT '登录名',
  `pwd` varchar(64) NOT NULL DEFAULT '' COMMENT '密码（sp_password加密）',
  `real_name` varchar(32) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `nick_name` varchar(32) NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(64) NOT NULL DEFAULT '' COMMENT '头像',
  `email` varchar(64) NOT NULL DEFAULT '' COMMENT '登录邮箱',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '登录手机号',
  `sex` smallint(1) NOT NULL DEFAULT '0' COMMENT '性别（0=保密，1=男，2=女）',
  `stat` int(11) NOT NULL DEFAULT '1' COMMENT '账户状态(1=正常，-1=锁定 )',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除（0=否，1=是）',
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `last_login_ip` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '上次登录IP',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_loginname` (`login_name`),
  UNIQUE KEY `uniq_phone` (`phone`),
  UNIQUE KEY `uniq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin`(id, login_name, pwd, nick_name) VALUES ('1', 'super', 'c3284d0f94606de1fd2af172aba15bf3', '管理员');

-- ----------------------------
-- Table structure for t_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_role`;
CREATE TABLE `t_admin_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `adminid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '管理员id',
  `roleid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色 id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员角色对应表';

-- ----------------------------
-- Records of t_admin_role
-- ----------------------------
INSERT INTO `t_admin_role` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(16) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(16) NOT NULL DEFAULT '' COMMENT '角色名称',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父角色ID',
  `description` varchar(255) NOT NULL DEFAULT '' COMMENT '角色描述',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态（0=未删除，1=已删除）',
  `is_pub` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态（0=禁用，1=启用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role`(id, code, role_name, pid) VALUES (1, 'ROLE_SUPER','超级管理员', 0);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `roleid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `permissionid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色授权表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 300);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 310);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 311);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 312);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 313);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 314);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 315);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 320);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 321);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 322);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 323);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 324);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 325);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 330);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 331);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 332);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 333);
INSERT INTO `t_role_permission`(roleid,permissionid) VALUES (1, 334);

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission`  (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单权限编码(前端按钮权限标识)',
  `title` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单权限名称',
  `icon` varchar(64) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `perms` varchar(32) NOT NULL DEFAULT '' COMMENT '授权(如：sys:user:add)',
  `path` varchar(64) NOT NULL DEFAULT '' COMMENT '访问地址URL（前端文件路由）',
  `permission_name` varchar(64) NOT NULL DEFAULT '' COMMENT '与前端vue路由name保持一致',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `priority` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '优先级',
  `kind` int(11) NULL DEFAULT 1 COMMENT '菜单权限类型(1=目录,2=菜单,3=按钮)',
  `is_del` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除状态（0=未删除，1=已删除）',
  `is_pub` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用状态（0=禁用，1=启用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (300, '', '权限管理', 'el-icon-menu', '', '/auth', 'auth', 0, 300, 1);

INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (310, '', '管理员管理', 'el-icon-user-solid', '', '/admin', 'admin', 300, 310, 2);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (311, 'btn-admin-save', '新增管理员权限', '', 'sys:admin:save', '/manage/admin', '', 310, 311, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (312, 'btn-admin-modify', '更新管理员信息权限', '', 'sys:admin:modify', '/manage/admin', '', 310, 312, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (313, 'btn-admin-del', '删除管理员权限', '', 'sys:admin:del', '/manage/admin', '', 310, 313, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (314, 'btn-admin-modify-role', '赋予管理员角色权限', '', 'sys:admin:modify:role', '/manage/admin/role', '', 310, 314, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (315, 'btn-admin-page', '分页查询管理员列表权限', '', 'sys:admin:page', '/manage/admin', '', 310, 315, 3);

INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (320, '', '角色管理', 'el-icon-user', '', '/role', 'role', 300, 320, 2);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (321, 'btn-role-save', '新增角色权限', '', 'sys:role:save', '/manage/role', '', 320, 321, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (322, 'btn-role-modify', '更新角色权限', '', 'sys:role:modify', '/manage/role', '',320, 322, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (323, 'btn-role-del', '删除角色权限', '', 'sys:role:del', '/manage/role/*', '', 320, 323, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (324, 'btn-role-modify-permission', '赋予角色菜单权限', '', 'sys:role:modify:permission', '/manage/role/*', '', 320, 324, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (325, 'btn-role-page', '分页查询角色列表权限', '', 'sys:role:page', '/manage/role', '', 320, 325, 3);

INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (330, '', '菜单权限管理', 'el-icon-menu', '', '/menu', 'menu', 300, 330, 2);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (331, 'btn-permission-save', '新增菜单权限', '', 'sys:permission:save', '/manage/permission', '', 330, 331, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (332, 'btn-permission-modify', '更新菜单权限', '', 'sys:permission:modify', '/manage/permission', '', 330, 332, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (333, 'btn-permission-del', '删除菜单权限', '', 'sys:permission:del', '/manage/permission', '', 330, 333, 3);
INSERT INTO `t_permission`(id, code, title, icon, perms, path, permission_name, pid, priority, kind) VALUES (334, 'btn-permission-page', '分页查询菜单权限列表权限', '', 'sys:permission:page', '/manage/permissions', '', 330, 334, 3);


