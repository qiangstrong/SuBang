#用户密码可以为空
ALTER TABLE `subang`.`user_t` 
CHANGE COLUMN `password` `password` CHAR(50) NULL ;

#增加用户来源字段
ALTER TABLE `subang`.`user_t` 
ADD COLUMN `client` TINYINT NULL AFTER `money`;

#price表改为类别下的小类
ALTER TABLE `subang`.`price_t` 
CHANGE COLUMN `money` `name` CHAR(10) NOT NULL ,
DROP INDEX `money` ,
ADD UNIQUE INDEX `name` (`name` ASC, `categoryid` ASC);

#衣物类型添加价格字段
ALTER TABLE `subang`.`clothes_type_t` 
ADD COLUMN `money` DOUBLE NULL AFTER `name`;

#物品表，订单物品明细表，商户价格表引用这个表。
create table `article_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique
);

#颜色表，订单物品明细引用这个表
create table `color_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique
);

#商家价格表
create table `cost_t`(
	`id` int auto_increment primary key,
    `money` double not null,
    `laundryid` int not null,
    `articleid` int not null,
    unique (`laundryid`,`articleid`),
    foreign key (`laundryid`) references `laundry_t`(`id`) on delete cascade,
    foreign key (`articleid`) references `article_t`(`id`) on delete cascade
);

#一定注意数据的迁移，要写专门的程序来迁移数据
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