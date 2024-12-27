
-- create database
create database if not exists viro_center default charset=utf8mb4;
use viro_center;


-- 用户表
DROP TABLE IF EXISTS `user_info`;
create table if not exists `user_info`
(
    id bigint auto_increment primary key comment 'id',
    user_account varchar(256) not null comment '账号',
    `password` varchar(512) not null comment '密码',
    union_id varchar(256) null comment '微信开放平台id',
    mp_open_id varchar(256) null comment '公众号openId',
    secret_id varchar(255) not null comment '用于open api的关键参数之一',
    user_name varchar(256) null comment '用户昵称',
    user_avatar varchar(1024) null comment '用户头像',
    user_profile varchar(512) null comment '用户简介',
    is_delete tinyint default 0 not null comment '是否删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    ) default charset=utf8mb4 comment '用户信息表';
INSERT INTO `user_info` (`id`,`user_account`, `password`, `secret_id`) VALUES
