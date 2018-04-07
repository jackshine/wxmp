

SET FOREIGN_KEY_CHECKS=0;

drop table if exists `tb_sys_user`;
CREATE TABLE `tb_sys_user` (
    id INT(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名，唯一',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    sex CHAR(1) COMMENT '性别：0-保密，1-男，2-女',
    passwd VARCHAR(255) COMMENT '密文(SHA512HEX)',
    introduce VARCHAR(600) COMMENT '个人简介',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    role_lvl char(2) not null comment ' 用户的角色级别',
    `status` CHAR(1) NOT NULL COMMENT '状态（1：正常，D：已注销）',
    open_kf char(1) not null default '0' comment '是否已开通客服（0-未开通，1-已开通）',
    nickname varchar(100) comment '客服昵称',
    head_img varchar(255) comment '客服头像，保存在服务器的路径',
    kf_passwd varchar(50) comment '客服密码，明文',
    CONSTRAINT `pk_sysuser_id` PRIMARY KEY (`id`),
    CONSTRAINT `uq_sysuser_username` UNIQUE KEY (`username`),
	CONSTRAINT `uq_sysuser_email` UNIQUE KEY (`email`)
) engine=InnoDB default charset=utf8 comment='用户信息';

drop table if exists `tb_wxmsg_log`;
CREATE TABLE `tb_wxmsg_log` (
    id INT(50) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    from_user VARCHAR(100) NOT NULL COMMENT ' 发消息者ID',
    `inout` CHAR(1) NOT NULL COMMENT '消息的进出方向:1-发给公众号，2-公众号发出 ',
    msg_type VARCHAR(50) NOT NULL COMMENT ' 消息的类型',
    event_type VARCHAR(50) COMMENT ' 事件消息的事件类型',
    is_mass char(1) default '0' comment ' 是否为群发消息（0-否，1-是）',
    is_tpl char(1) default '0' comment ' 是否为模板消息（0-否，1-是）',
    content TEXT NOT NULL COMMENT ' 消息内容，XML格式文本',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
     `status` CHAR(1) NOT NULL COMMENT '消息回复状态(0：待处理，1:不用回复，2：已回复，3:其他)；inout为2则默认为1',
     resp_id INT(50) comment ' 针对inout为2的消息回复日志ID' ,
    CONSTRAINT `pk_wxmsgLog_id` PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='微信消息日志表';

drop table if exists `tb_media_material`;
CREATE TABLE `tb_media_material` (
    id INT(50) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    media_type VARCHAR(10) NOT NULL COMMENT ' 素材类型（image、voice、video、thumb、news）',
    is_temp CHAR(1) NOT NULL COMMENT '是否为临时素材（0-否，1-是）',
    media_id VARCHAR(100) NOT NULL COMMENT ' 微信服务器返回的ID',
    media_url VARCHAR(255) COMMENT ' 微信服务器返回的URL',
    is_news_img char(1) default '0' comment ' 是否为图文消息专用而上传的图片(0-否，1-是)',
    content TEXT NOT NULL COMMENT ' 图文素材的内容，其他素材的服务器保存路径',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ' 更新时间',
     `status` CHAR(1) NOT NULL default '1' COMMENT '状态(1:可用，2：已删除)',
     update_user INT(11) comment ' 更新者' ,
    CONSTRAINT `pk_wxmsgLog_id` PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='多媒体素材管理日志表';

drop table if exists `tb_wx_qrcode`;
CREATE TABLE `tb_wx_qrcode` (
    id INT(30) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    is_perm CHAR(11) NOT NULL DEFAULT '0' COMMENT ' 是否为永久二维码（0-否，1-是）',
    scene_id INT(10) NOT NULL COMMENT '申请二维码使用的场景值',
    ticket VARCHAR(100) NOT NULL COMMENT ' 申请返回的ticket，凭借此ticket可以在有效时间内换取二维码',
    url VARCHAR(255) NOT NULL COMMENT ' 申请返回的二维码图片解析后的地址，根据该地址自行生成需要的二维码图片',
    expire_seconds INT(10)  default -1 COMMENT ' 该二维码有效时间，以秒为单位， 最大不超过2592000（即30天），为-1则为长期',
    local_img_url VARCHAR(255) COMMENT ' 二维码图片的本系统保存地址',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
    CONSTRAINT `pk_wxqrcode_id` PRIMARY KEY (`id`)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8 COMMENT='公众号二维码信息表';
