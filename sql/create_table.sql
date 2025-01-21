# 数据库初始化

-- 创建库
create database if not exists my_db;

-- 切换库
use my_db;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';

-- 单词表
CREATE TABLE english_word (
    id BIGINT PRIMARY KEY COMMENT 'id',
    word_head VARCHAR(255),
    thumbnail VARCHAR(255),
    info JSON,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_delete TINYINT(1) DEFAULT 0
) COMMENT='单词表';

-- 单词变更记录表
CREATE TABLE english_word_change_log (
    id BIGINT PRIMARY KEY COMMENT 'id',
    english_word_id BIGINT NOT NULL COMMENT '单词ID',
    field_name VARCHAR(255) NOT NULL COMMENT '变更字段名',
    old_value TEXT COMMENT '变更前的值',
    new_value TEXT COMMENT '变更后的值',
    change_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '变更时间',
    changed_by BIGINT COMMENT '变更操作人ID',
    FOREIGN KEY (english_word_id) REFERENCES english_word (id) ON DELETE CASCADE
) COMMENT='单词变更记录表';

-- 英语词书
CREATE TABLE english_dictionary (
    id BIGINT PRIMARY KEY COMMENT 'id',
    name VARCHAR(255),
    description TEXT,
    image_url VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(13),
    publication_date DATE,
    publisher VARCHAR(255),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_delete TINYINT(1) DEFAULT 0
) COMMENT='英语词书';

CREATE TABLE audio_file (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            path VARCHAR(255) NOT NULL,
                            content TEXT,
                            creator_id BIGINT,
                            create_time DATETIME,
                            update_time DATETIME,
                            is_delete INT DEFAULT 0,
                            FOREIGN KEY (creator_id) REFERENCES user(id)
) COMMENT='音频文件表';

ALTER TABLE audio_file
    ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'UNKNOWN';

ALTER TABLE audio_file
    ADD COLUMN name VARCHAR(255) NOT NULL DEFAULT '默认音频文件';