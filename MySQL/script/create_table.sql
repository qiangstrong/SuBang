use `subang`;

set foreign_key_checks=0;

#工作人员（取衣员）表
drop table if exists worker_t;
create table `worker_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,		#工作人员是否离职。1：未离职
    `core` tinyint not null default 0,		#是否是核心取衣员，与取衣员的分配有关。1：是核心取衣员
    `name` char(4) not null,				#真实姓名
	`password` char(50) not null,			#工作人员密码
	`cellnum` char(11) not null unique,		#手机号
    `detail` char(50) not null,				#取衣员的详细地址
    `comment` varchar(100)					#备注
);

#城市表，如沈阳市
drop table if exists city_t;
create table `city_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,
	`scope` char(100) not null,				#相对于这个城市的服务范围
    `scope_text` char(100)					#服务范围的描述
);

#区表，如和平区
drop table if exists district_t;
create table `district_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null,
    `cityid` int not null,
    unique (`name`,`cityid`),				#一个城市下不能有同名的区
    foreign key (`cityid`) references `city_t`(`id`) on delete cascade
);

#小区表，如新世界花园
drop table if exists region_t;
create table `region_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null,
    `districtid` int not null,
    `workerid` int,
    unique (`name`,`districtid`),
    foreign key (`districtid`) references `district_t`(`id`) on delete cascade,
    foreign key (`workerid`) references `worker_t`(`id`) on delete set null
);

#类别表，如衣服、鞋等
drop table if exists category_t;
create table `category_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,		#类别名称
    `icon` char(100) not null,				#类别图标的路径
    `comment` varchar(100)					#备注
);

#价格表，不同类别下分不同的价格；类别下的小类
drop table if exists price_t;
create table `price_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null,				#小类的名称
    `comment` varchar(100),
    `categoryid` int not null,
    unique (`name`,`categoryid`),
    foreign key (`categoryid`) references `category_t`(`id`) on delete cascade
);

#衣物类型表，不同的小类下分不同的衣物类型，如运动鞋、帆布鞋。用于用户的价目表
drop table if exists clothes_type_t;
create table `clothes_type_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,
    `money` double,
    `icon` char(100) not null,
    `categoryid` int not null,
    `priceid` int,
    foreign key (`categoryid`) references `category_t`(`id`) on delete cascade,
    foreign key (`priceid`) references `price_t`(`id`) on delete set null
);

#物品表，订单物品明细表，商户价格表引用这个表。
drop table if exists article_t;
create table `article_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique
);

#颜色表，订单物品明细引用这个表
drop table if exists color_t;
create table `color_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique
);

#服务类别表，不同城市有不同的服务类别
drop table if exists service_t;
create table `service_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,		#类别是否可用。1：可用
    `seq` tinyint,
    `cityid` int not null,
    `categoryid` int not null,
    unique (`cityid`,`categoryid`),
    foreign key (`cityid`) references `city_t`(`id`) on delete cascade,
    foreign key (`categoryid`) references `category_t`(`id`) on delete cascade
);

#卡券类型表
drop table if exists ticket_type_t;
create table `ticket_type_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,
    `icon` char(100) not null,
    `money` double not null,				#卡券的金额
    `score` int not null,					#兑换这张卡券所需的积分
    `deadline` datetime,					#过期时间，过期后的卡券作废
    `comment` varchar(1000),				#备注（卡券的使用规则等）
	`categoryid` int,						#卡券所属的类别
    foreign key (`categoryid`) references `category_t`(`id`) on delete cascade
);

#商家（洗衣店）表
drop table if exists laundry_t;
create table `laundry_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,
    `cellnum` char(11) not null,
    `detail` char(50) not null,				#详细地址
    `comment` varchar(100)					#备注（如洗衣店可以服务的类别）
);

#商家价格表
drop table if exists cost_t;
create table `cost_t`(
	`id` int auto_increment primary key,
    `money` double not null,
    `laundryid` int not null,
    `articleid` int not null,
    unique (`laundryid`,`articleid`),
    foreign key (`laundryid`) references `laundry_t`(`id`) on delete cascade,
    foreign key (`articleid`) references `article_t`(`id`) on delete cascade
);

#用户表
drop table if exists user_t;
create table `user_t`(
	`id` int auto_increment primary key,
    `login` tinyint not null default 0,		#今日用户是否登录过，用于计算积分
    `openid` char(28) unique,				#微信的openid
    `userno` char(10) unique,				#会员号
    `nickname` varchar(100),				#微信昵称，也可以作为本系统的用户名
    `password` char(50),					#用户密码;不需要用户密码，目前为null，以后为了app的用户认证，可使用动态密码（比如验证码）
    `cellnum` char(11) not null unique,		#用户绑定的电话号码
    `score` int not null default 0,			#积分
    `money` double not null default 0,		#用户余额
    `client` tinyint,						#用户的来源
    `addrid` int,							#用户的默认地址
    foreign key(`addrid`) references `addr_t`(`id`) on delete set null
);

#用户位置表，用于为用户提供位置服务
drop table if exists location_t;
create table `location_t`(
	`id` int auto_increment primary key,
	`latitude` char(15),
    `longitude` char(15),
	`time` datetime,				#获取位置的时间
    `cityid` int,
    `userid` int not null,
	foreign key (`cityid`) references `city_t`(`id`) on delete set null,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade
);

#卡券表，记录用户拥有的卡券。优惠券无用（被使用，过期）之后，从系统中删除
drop table if exists ticket_t;
create table `ticket_t`(
	`id` int auto_increment primary key,
    `deadline` datetime,					#卡券的过期时间
    `userid` int not null,
    `ticket_typeid` int not null,			#卡券类型
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`ticket_typeid`) references `ticket_type_t`(`id`) on delete restrict
);

#地址表
drop table if exists addr_t;
create table `addr_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,		#用户是否删除了此地址
	`name` char(4) not null,				#用户真实姓名
    `cellnum` char(11) not null,			#手机号
	`detail` char(50) not null,				#详细地址
    `userid` int not null,
    `regionid` int not null,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`regionid`) references `region_t`(`id`) on delete restrict
);

#订单表
drop table if exists order_t;
create table `order_t`(
	`id` int auto_Increment primary key,	
    `orderno` char(14) not null unique,		#订单号
    `state` tinyint not null,				#订单状态
    `money` double,							#订单金额
    `freight` double,						#运费
    `date` date not null,					#用户指定的取件日期
    `time` tinyint not null,				#用户指定的取件时间
    `user_comment` varchar(100),			#用户指定的备注
    `worker_comment` varchar(100),			#工作人员对订单的备注，如衣物瑕疵等
    `remark` varchar(100),					#订单完成后，用户的评价
    `barcode` char(13) unique,				#标志衣物的条形码
    `categoryid` int not null,
    `userid` int not null,
    `addrid` int not null,
    `workerid` int not null,
    `laundryid` int,    
    foreign key (`categoryid`) references `category_t`(`id`) on delete cascade,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`addrid`) references `addr_t`(`id`) on delete cascade,
    foreign key(`workerid`) references `worker_t`(`id`) on delete restrict,
    foreign key(`laundryid`) references `laundry_t`(`id`) on delete restrict
);

#余额表，记录余额的每一笔收支
drop table if exists balance_t;
create table `balance_t`(
	`id` int auto_Increment primary key,	
    `orderno` char(14) not null unique,		#订单号
    `state` tinyint not null,				#订单状态
    `money` double not null,				#订单金额
    `time` datetime,						#订单完成支付的时间
    `userid` int not null,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade
);

#支付表，记录订单的支付信息和特定于api的参数
drop table if exists payment_t;
create table `payment_t`(
	`id` int auto_Increment primary key,
	`type` tinyint,								#支付类型，支付宝，微信支付，余额支付
    `money_ticket` double not null default 0,	#使用优惠券支付的金额
    `prepay_id` char(64),						#特定于微信支付的参数
    `time` tinyint,								#获取prepay_id的时间
	`orderno` char(14) not null unique
);

#物品信息表，订单中衣物明细
drop table if exists clothes_t;
create table `clothes_t`(
	`id` int auto_Increment primary key,
    `flaw` char(100),						#瑕疵
    `position` int,							#位置号
    `articleid` int not null,				#物品
    `colorid` int not null,					#颜色
	`orderid` int not null,
    foreign key(`articleid`) references `article_t`(`id`) on delete restrict,
    foreign key(`colorid`) references `color_t`(`id`) on delete restrict,
    foreign key(`orderid`) references `order_t`(`id`) on delete cascade
);

#快照表。每一件物品可以存储若干快照
drop table if exists snapshot_t;
create table `snapshot_t`(
	`id` int auto_Increment primary key,
    `icon` char(100) not null,				#图标
    `clothesid` int not null,
    foreign key(`clothesid`) references `clothes_t`(`id`) on delete cascade
);

#订单历史表，记录订单状态转换的过程
drop table if exists history_t;
create table `history_t`(
	`id` int auto_Increment primary key,
    `operation` tinyint not null,			#操作
    `time` datetime not null,				#操作发生的时间
    `orderid` int not null,
    foreign key(`orderid`) references `order_t`(`id`) on delete cascade
);

#管理人员表
drop table if exists admin_t;
create table `admin_t`(
	`id` int auto_Increment primary key,
    `username` char(10) not null unique,
    `password` char(50) not null
);

#信息表
drop table if exists info_t;
create table `info_t`(
	`id` int auto_Increment primary key,
    `phone` char(12) not null				#客服电话
);

#常见问题表
drop table if exists faq_t;
create table `faq_t`(
	`id` int auto_Increment primary key,
    `question` varchar(100) not null unique,#问题
    `answer` varchar(1000) not null			#答案
);

#用户反馈表
drop table if exists feedback_t;
create table `feedback_t`(
	`id` int auto_Increment primary key,
    `time` datetime not null,				#反馈的时间
    `comment` varchar(1000) not null	
);

#通知表，用于向管理员显示系统无法处理的异常
drop table if exists notice_t;
create table `notice_t`(
	`id` int auto_Increment primary key,
    `time` datetime not null,				#时间戳
    `code` int not null,					#异常代码
    `msg` char(100) 						#异常信息
);

#横幅表
drop table if exists banner_t;
create table `banner_t`(
	`id` int auto_increment primary key,
    `seq` tinyint,
    `link` char(100),
    `icon` char(100) not null,
    `comment` varchar(100)					
);

#折扣表
drop table if exists rebate_t;
create table `rebate_t`(
	`id` int auto_increment primary key,
    `money` double not null unique,			#充值的金额
    `benefit` double not null				#赠送的金额
);

set foreign_key_checks=1;