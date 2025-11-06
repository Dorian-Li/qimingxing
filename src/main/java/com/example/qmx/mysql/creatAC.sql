-- 创建库
create database if not exists QMXdatabase;

-- 切换库
use QMXdatabase;

-- 碳块锁定数据
create table if not exists SD
(
    id         bigint auto_increment comment 'id' primary key,
    devName         varchar(50)                     not null  comment '设备名称',
    status          int                             not null comment '工作状态',
    time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'

);

-- 喷涂数据
create table if not exists PT
(
    id         bigint auto_increment comment 'id' primary key,
    devName         varchar(50)                     not null  comment '设备名称',
    status          int                             not null comment '工作状态',
    remain          double                             not null comment  '剩余涂料',
    pressure        double                             not null comment  '喷枪压力',
    time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'

);

-- 喷涂房数据
create table if not exists PTF
(
    id         bigint auto_increment comment 'id' primary key,
    devName         varchar(50)                     not null  comment '设备名称',
    status          int                             not null comment '工作状态',
    time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'

);

-- 上料数据
create table if not exists SL
(
    id         bigint auto_increment comment 'id' primary key,
    devName         varchar(50)                     not null  comment '设备名称',
    status           int                             not null comment '工作状态',
    level            double                             not null comment '料筒液位',
    time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'
);

-- 喷涂质量检测数据
create table if not exists ZLJC
(
    id         bigint auto_increment comment 'id' primary key,
    status           int                             not null comment '工作状态',
    targetNum        int                             not null comment '喷涂目标数量',
    completeNum      int                             not null comment '喷涂完成块数',
    passNum          int                             not null comment '喷涂合格块数',
    failNum          int                             not null comment '喷涂不合格块数',
    rate            double                             not null comment '喷涂合格率',
    time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'
);
