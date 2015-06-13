use `subang`;

set foreign_key_checks=0;
drop table if exists `subang`.`addr_t`, `subang`.`admin_t`, `subang`.`city_t`, 
`subang`.`district_t`, `subang`.`history_t`, `subang`.`laundry_t`, 
`subang`.`order_t`, `subang`.`region_t`, `subang`.`user_t`, `subang`.`worker_t`;
set foreign_key_checks=1;

create table `worker_t`(
	`id` int auto_increment primary key,
    `core` tinyint not null default 0,
    `name` char(4) not null,
	`cellnum` char(11) not null,
    `comment` varchar(50)
);

create table `city_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique
);

create table `district_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null,
    `cityid` int not null,
    unique (`name`,`cityid`),
    foreign key (`cityid`) references `city_t`(`id`) on delete cascade
);

create table `region_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null,
    `districtid` int not null,
    `workerid` int,
    unique (`name`,`districtid`),
    foreign key (`districtid`) references `district_t`(`id`) on delete cascade,
    foreign key (`workerid`) references `worker_t`(`id`) on delete set null
);

create table `laundry_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,
    `cellnum` char(11) not null,
    `detail` char(50) not null,
    `comment` varchar(50)
);

SET foreign_key_checks=0;
create table `user_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,
    `openid` char(28) not null unique,
    `name` char(4),
    `nickname` varchar(100) not null,
    `password` char(50),
    `cellnum` char(11),
    `score` int zerofill,
    `photo` char(100),
    `sex` tinyint default 0,
    `country` char(10),
    `province` char(10),
    `city` char(10),
    `addrid` int,
    foreign key(`addrid`) references `addr_t`(`id`) on delete set null
);

create table `addr_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,
	`name` char(4) not null,
    `cellnum` char(11) not null,
	`detail` char(50) not null,
    `userid` int not null,
    `regionid` int not null,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`regionid`) references `region_t`(`id`) on delete restrict
);
SET foreign_key_checks=1;

create table `order_t`(
	`id` int auto_Increment primary key,
    `orderno` char(21) not null unique,
    `category` tinyint not null,
    `state` tinyint not null,
    `price` float,
    `date` date not null,
    `time` tinyint not null,
    `comment` varchar(50),
    `userid` int not null,
    `addrid` int not null,
    `workerid` int not null,
    `laundryid` int,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`addrid`) references `addr_t`(`id`) on delete cascade,
    foreign key(`workerid`) references `worker_t`(`id`) on delete restrict,
    foreign key(`laundryid`) references `laundry_t`(`id`) on delete restrict
);

create table `history_t`(
	`id` int auto_Increment primary key,
    `operatorid` int not null,
    `operation` tinyint not null,
    `time` datetime not null,
    `orderid` int not null,
    foreign key(`orderid`) references `order_t`(`id`) on delete cascade
);

create table `admin_t`(
	`id` int auto_Increment primary key,
    `username` char(10) not null unique,
    `password` char(50) not null
);

create table `info_t`(
	`id` int auto_Increment primary key,
	`price_path` char(100) not null,
    `price_text` varchar(1000),
    `scope_path` char(100) not null,
    `scope_text` varchar(1000),
    `about` varchar(1000) not null,
    `term` varchar(1000) not null,
    `phone` varchar(1000) not null
);