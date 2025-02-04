# 数据库初始化

-- 创建库
create database if not exists leaves_word;

-- 切换库
use leaves_word;

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
CREATE TABLE if not exists english_word (
    id BIGINT PRIMARY KEY COMMENT 'id',
    word_head VARCHAR(255),
    thumbnail VARCHAR(255),
    info JSON,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_delete TINYINT(1) DEFAULT 0
) COMMENT='单词表';

ALTER TABLE english_word
    ADD COLUMN status VARCHAR(255) NULL COMMENT '当前状态';


-- 单词变更记录表
CREATE TABLE if not exists english_word_change_log (
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
CREATE TABLE if not exists english_dictionary (
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

-- 单词和词典关系表
CREATE TABLE if not exists dictionary_word (
                                dictionary_id BIGINT NOT NULL,
                                word_id BIGINT NOT NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (dictionary_id, word_id),
                                FOREIGN KEY (dictionary_id) REFERENCES english_dictionary(id) ON DELETE CASCADE,
                                FOREIGN KEY (word_id) REFERENCES english_word(id) ON DELETE CASCADE
) COMMENT='单词和词典关系表';

CREATE TABLE if not exists audio_file (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            path VARCHAR(255),
                            content MEDIUMTEXT,
                            creator_id BIGINT,
                            create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                            update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            is_delete INT DEFAULT 0,
                            FOREIGN KEY (creator_id) REFERENCES user(id)
) COMMENT='音频文件表';

ALTER TABLE audio_file
    ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'UNKNOWN';

ALTER TABLE audio_file
    ADD COLUMN name VARCHAR(255) NOT NULL DEFAULT '默认音频文件';


CREATE TABLE if not exists word_status_change (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
                                    word_id BIGINT NOT NULL COMMENT '单词ID',
                                    status VARCHAR(255) NOT NULL COMMENT '变更状态',
                                    info JSON COMMENT '变更信息',
                                    comment VARCHAR(255) COMMENT '评论',
                                    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    is_delete INT DEFAULT 0 COMMENT '是否删除'
) COMMENT='单词状态变更记录表';


CREATE TABLE if not exists media_creator (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               word_id BIGINT NOT NULL,
                               media_type VARCHAR(50) NOT NULL,
                               media_url VARCHAR(255),
                               creator_id BIGINT DEFAULT NULL,
                               info TEXT,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (word_id) REFERENCES english_word(id)
);

CREATE TABLE category (
                          id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
                          parent_id INT UNSIGNED DEFAULT 0 COMMENT '父分类ID',
                          name VARCHAR(100) NOT NULL COMMENT '分类名称',
                          sort_order INT DEFAULT 0 COMMENT '同级排序顺序',
                          description VARCHAR(500) COMMENT '分类描述',
                          is_root TINYINT(1) DEFAULT 0 COMMENT '是否为根分类',
                          created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                          updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          INDEX idx_parent_id (parent_id),
                          INDEX idx_sort_order (sort_order)
) ENGINE=InnoDB COMMENT='分类表';

CREATE TABLE dictionary_category (
    id INT UNSIGNED AUTO_INCREMENT NOT NULL, -- 新增自增主键列
    dictionary_id bigint UNSIGNED NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    sort_order INT DEFAULT 0 COMMENT '分类内排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id), -- 设置新的自增列为主键
    INDEX idx_category_id (category_id)
) ENGINE=InnoDB COMMENT='书籍分类关系表';
