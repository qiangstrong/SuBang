create table `worker_t`(
	`id` int auto_increment primary key,
    `name` char(12) not null,
	`cellnum` char(11) not null,
    `comment` varchar(150)
);

create table `city_t`(
	`id` int auto_increment primary key,
    `name` char(30) not null unique
);

create table `district_t`(
	`id` int auto_increment primary key,
    `name` char(30) not null,
    `cityid` int not null,
    foreign key (`cityid`) references `city_t`(`id`) on delete cascade
);

create table `region_t`(
	`id` int auto_increment primary key,
    `name` char(30) not null,
    `districtid` int not null,
    `workerid` int,
    foreign key (`districtid`) references `district_t`(`id`) on delete cascade,
    foreign key (`workerid`) references `worker_t`(`id`) on delete set null
);

create table `laundry_t`(
	`id` int auto_increment primary key,
    `name` char(30) not null unique,
    `cellnum` char(11) not null,
    `detail` char(150) not null,
    `comment` varchar(150)
);

SET foreign_key_checks=0;
create table `user_t`(
	`id` int auto_increment primary key,
    `openid` char(28) not null unique,
    `name` char(12),
    `nickname` varchar(300),
    `cellnum` char(11),
    `score` int zerofill,
    `photo` char(100),
    `sex` tinyint default 0,
    `country` char(30),
    `province` char(30),
    `city` char(30),
    `addrid` int,
    foreign key(`addrid`) references `addr_t`(`id`) on delete set null
);

create table `addr_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,
	`name` char(12) not null,
    `cellnum` char(11) not null,
	`detail` char(150) not null,
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
    `comment` varchar(150),
    `userid` int not null,
    `addrid` int not null,
    `workerid` int not null,
    `laundryid` int,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`addrid`) references `addr_t`(`id`) on delete restrict,
    foreign key(`workerid`) references `worker_t`(`id`) on delete restrict,
    foreign key(`laundryid`) references `laundry_t`(`id`) on delete restrict
);

create table `history_t`(
	`id` int auto_Increment primary key,
    `operation` tinyint not null,
    `time` datetime not null,
    `orderid` int not null,
    foreign key(`orderid`) references `order_t`(`id`) on delete cascade
);