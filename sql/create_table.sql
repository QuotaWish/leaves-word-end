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

alter table english_word
    add manual_score int default 0 null comment '人工评分';

alter table english_word
    add ai_score int null;

alter table english_word
    add reviewer bigint null;


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

alter table dictionary_word
    add id bigint null;

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

alter table english_dictionary
    add total_words int default 0 null comment '已关联的单词数量';

alter table english_dictionary
    add published_words int default 0 null comment '已关联的发布单词数量';

alter table english_dictionary
    add approved_words int default 0 null comment '已关联的通过单词数量';

-- 1. 试卷分类表
--    支持多级分类、软删除、排序、以及创建/更新审计信息
CREATE TABLE exam_category (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(255) NOT NULL UNIQUE COMMENT '分类名称',
                               parent_id INT DEFAULT NULL COMMENT '父级分类ID',
                               sort_order INT DEFAULT 0 COMMENT '排序值，控制前端显示顺序',
                               is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0: 正常, 1: 删除)',
                               created_by INT DEFAULT NULL COMMENT '创建者ID',
                               updated_by INT DEFAULT NULL COMMENT '更新者ID',
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               deleted_at TIMESTAMP NULL COMMENT '删除时间',
                               FOREIGN KEY (parent_id) REFERENCES exam_category(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷分类表';


-- 2. 试卷表
--    包含试卷基本信息、价格（以分为单位）、状态、软删除及审计字段
CREATE TABLE exam_paper (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL COMMENT '试卷名称',
                            thumbnail_url VARCHAR(255) COMMENT '缩略图 URL',
                            price INT NOT NULL DEFAULT 0 COMMENT '价格 (单位: 分)',
                            description VARCHAR(255) COMMENT '试卷描述 (限制 255 字符)',
                            total_questions INT DEFAULT 0 COMMENT '总题数',
                            status ENUM('draft', 'published', 'archived') DEFAULT 'draft' COMMENT '试卷状态',
                            is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标志 (0: 正常, 1: 删除)',
                            created_by INT DEFAULT NULL COMMENT '创建者ID',
                            updated_by INT DEFAULT NULL COMMENT '更新者ID',
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            deleted_at TIMESTAMP NULL COMMENT '删除时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';


-- 3. 分类与试卷关联表
--    用于处理试卷与分类之间的多对多关系，同时记录分类内排序和关联时间
CREATE TABLE exam_category_paper (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     category_id INT NOT NULL COMMENT '试卷分类ID',
                                     paper_id INT NOT NULL COMMENT '试卷ID',
                                     sort_order INT DEFAULT 0 COMMENT '分类内排序',
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关联创建时间',
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     FOREIGN KEY (category_id) REFERENCES exam_category(id) ON DELETE CASCADE,
                                     FOREIGN KEY (paper_id) REFERENCES exam_paper(id) ON DELETE CASCADE,
                                     UNIQUE KEY uq_category_paper (category_id, paper_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷与分类关联表';


-- 4. 标签表
--    用于给试卷添加标签，方便分类、搜索和推荐
CREATE TABLE exam_tag (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(50) NOT NULL UNIQUE COMMENT '标签名称',
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷标签表';


-- 5. 试卷与标签关联表
--    处理试卷与标签的多对多关系
CREATE TABLE exam_paper_tag (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                paper_id INT NOT NULL COMMENT '试卷ID',
                                tag_id INT NOT NULL COMMENT '标签ID',
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                FOREIGN KEY (paper_id) REFERENCES exam_paper(id) ON DELETE CASCADE,
                                FOREIGN KEY (tag_id) REFERENCES exam_tag(id) ON DELETE CASCADE,
                                UNIQUE KEY uq_paper_tag (paper_id, tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷与标签关联表';


-- 6. 试题表
--    存储各类试题信息，题型包括单选、多选、填空、判断等
CREATE TABLE exam_question (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               question_text TEXT NOT NULL COMMENT '题目内容',
                               question_type ENUM('single', 'multiple', 'fill', 'truefalse') DEFAULT 'single' COMMENT '题目类型',
                               options TEXT COMMENT '题目选项，建议以 JSON 格式存储',
                               answer TEXT COMMENT '答案',
                               explanation TEXT COMMENT '答案解析',
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试题表';


-- 7. 试卷与试题关联表
--    支持同一试题可在多个试卷中使用，并记录试题在试卷中的排序
CREATE TABLE exam_paper_question (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     paper_id INT NOT NULL COMMENT '试卷ID',
                                     question_id INT NOT NULL COMMENT '试题ID',
                                     sort_order INT DEFAULT 0 COMMENT '试题在试卷中的排序',
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '关联创建时间',
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     FOREIGN KEY (paper_id) REFERENCES exam_paper(id) ON DELETE CASCADE,
                                     FOREIGN KEY (question_id) REFERENCES exam_question(id) ON DELETE CASCADE,
                                     UNIQUE KEY uq_paper_question (paper_id, question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷与试题关联表';


-- 8. 试卷版本表
--    记录试卷的不同版本信息，便于版本管理与历史回溯
CREATE TABLE exam_paper_version (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    paper_id INT NOT NULL COMMENT '试卷ID',
                                    version_number INT NOT NULL COMMENT '版本号',
                                    content TEXT COMMENT '试卷版本内容，包含试题、答案、解析等信息',
                                    created_by INT DEFAULT NULL COMMENT '创建者ID',
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '版本创建时间',
                                    FOREIGN KEY (paper_id) REFERENCES exam_paper(id) ON DELETE CASCADE,
                                    UNIQUE KEY uq_paper_version (paper_id, version_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷版本记录表';


-- 9. 试卷统计表
--    记录试卷相关的统计数据，如浏览、下载、尝试次数及平均得分
CREATE TABLE exam_paper_stats (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  paper_id INT NOT NULL COMMENT '试卷ID',
                                  view_count INT DEFAULT 0 COMMENT '浏览次数',
                                  download_count INT DEFAULT 0 COMMENT '下载次数',
                                  attempt_count INT DEFAULT 0 COMMENT '考试尝试次数',
                                  average_score DECIMAL(5,2) DEFAULT 0.00 COMMENT '平均得分',
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '统计更新时间',
                                  FOREIGN KEY (paper_id) REFERENCES exam_paper(id) ON DELETE CASCADE,
                                  UNIQUE KEY uq_paper_stats (paper_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷统计表';


-- 10. 操作日志表
--     记录对各数据表的增删改操作，便于审计和问题追踪
CREATE TABLE exam_operation_log (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    table_name VARCHAR(255) NOT NULL COMMENT '操作的表名',
                                    record_id INT NOT NULL COMMENT '记录ID',
                                    action ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL COMMENT '操作类型',
                                    operator_id INT NOT NULL COMMENT '操作员ID',
                                    operation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                                    ip_address VARCHAR(45) COMMENT '操作员IP地址',
                                    description TEXT COMMENT '操作说明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';


-- 11. 用户配置表
--     使用JSON字段存储用户的所有配置项
CREATE TABLE user_config (
    user_id BIGINT NOT NULL PRIMARY KEY COMMENT '用户ID',
    config_json JSON NOT NULL COMMENT '用户配置JSON数据',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户配置表';

