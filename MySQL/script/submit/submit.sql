
ALTER TABLE `subang`.`balance_t` 
ADD COLUMN `type` TINYINT NOT NULL DEFAULT 0 AFTER `id`;

#用户表添加userid列
ALTER TABLE `subang`.`user_t` 
ADD COLUMN `salary` DOUBLE NOT NULL DEFAULT 0 AFTER `money`,
ADD COLUMN `userid` INT NULL AFTER `addrid`,
ADD INDEX `user_t_ibfk_2_idx` (`userid` ASC);
ALTER TABLE `subang`.`user_t` 
ADD CONSTRAINT `user_t_ibfk_2`
  FOREIGN KEY (`userid`)
  REFERENCES `subang`.`user_t` (`id`)
  ON DELETE SET NULL
  ON UPDATE RESTRICT;

#扩充info表
ALTER TABLE `subang`.`info_t` 
ADD COLUMN `share_money` DOUBLE NOT NULL DEFAULT 1 AFTER `phone`,
ADD COLUMN `salary_limit` DOUBLE NOT NULL DEFAULT 50 AFTER `share_money`,
ADD COLUMN `prom0` INT NOT NULL DEFAULT 10 AFTER `salary_limit`,
ADD COLUMN `prom1` INT NOT NULL DEFAULT 3 AFTER `prom0`,
ADD COLUMN `prom2` INT NOT NULL DEFAULT 2 AFTER `prom1`;

#优惠码表
drop table if exists ticket_code_t;
create table `ticket_code_t`(
	`id` int auto_increment primary key,
    `valid` tinyint not null default 1,		#优惠券是否有效（没有使用过为有效）。1：有效
    `codeno` char(8) not null unique,		#优惠码
    `start` datetime not null,				#有效期，起始日期
    `end` datetime not null,				#有效期，截止日期
    `ticket_typeid` int not null,			#优惠码对应的优惠券类型
    foreign key (`ticket_typeid`) references `ticket_type_t`(`id`) on delete restrict
);

#商品表
drop table if exists goods_t;
create table `goods_t`(
	`id` int auto_increment primary key,
    `name` char(10) not null unique,
    `icon` char(100) not null,
    `poster` char(100) not null,			#详情页面展示的海报
    `money` double not null,				#兑换商品所需的金额
    `score` int not null,					#兑换商品所需的积分
    `count` int not null,					#商品的数量
    `comment` varchar(1000) not null		#备注（商品的使用规则等）	
);

#商品的兑换记录
drop table if exists record_t;
create table `record_t`(
	`id` int auto_Increment primary key,	
    `orderno` char(14) not null unique,		#订单号
    `state` tinyint not null,				#订单状态:paid, delivered
    `time` datetime,						#订单完成支付的时间
    `goodsid` int not null,
    `userid` int not null,
    `addrid` int not null,
    `workerid` int not null,
    foreign key(`goodsid`) references `goods_t`(`id`) on delete restrict,
    foreign key(`userid`) references `user_t`(`id`) on delete cascade,
    foreign key(`addrid`) references `addr_t`(`id`) on delete cascade,
    foreign key(`workerid`) references `worker_t`(`id`) on delete restrict
);

#删除类别表时受限删除订单表
ALTER TABLE `subang`.`order_t` 
DROP FOREIGN KEY `order_t_ibfk_1`;
ALTER TABLE `subang`.`order_t` 
ADD CONSTRAINT `order_t_ibfk_1`
  FOREIGN KEY (`categoryid`)
  REFERENCES `subang`.`category_t` (`id`)
  ON DELETE RESTRICT;

#增加poster列  
ALTER TABLE `subang`.`ticket_type_t` 
CHANGE COLUMN `comment` `comment` VARCHAR(1000) NOT NULL ,
ADD COLUMN `poster` CHAR(100) NOT NULL AFTER `icon`;			#需要随后添加数据

delimiter $$

#兑换记录表级联添加支付记录
drop trigger if exists record_ins_tr$$
create trigger `record_ins_tr`
after insert on record_t
for each row 
begin
insert into payment_t(orderno) values(new.orderno);
end$$

#兑换记录表级联删除支付记录
drop trigger if exists record_del_tr$$
create trigger `record_del_tr`
after delete on record_t
for each row 
begin
delete from payment_t where orderno=old.orderno;
end$$

delimiter ;

drop view if exists recorddetail_v;
create view recorddetail_v
as( select record_t.*, goods_t.name, goods_t.icon, goods_t.money, goods_t.score, payment_t.type `pay_type`
from record_t, goods_t, payment_t
where record_t.goodsid=goods_t.id and record_t.orderno=payment_t.orderno
);

#关于订单查找的存储过程，改名
DROP PROCEDURE `subang`.`count0`;
DROP PROCEDURE `subang`.`countAll`;
DROP PROCEDURE `subang`.`countByBarcode`;
DROP PROCEDURE `subang`.`countByCellnum`;
DROP PROCEDURE `subang`.`countByLaundryid`;
DROP PROCEDURE `subang`.`countByLaundryname`;
DROP PROCEDURE `subang`.`countByOrderno`;
DROP PROCEDURE `subang`.`countByState`;
DROP PROCEDURE `subang`.`countByUserid`;
DROP PROCEDURE `subang`.`countByUseridAndState`;
DROP PROCEDURE `subang`.`countByUsername`;
DROP PROCEDURE `subang`.`countByWorkerid`;
DROP PROCEDURE `subang`.`countByWorkeridAndState`;
DROP PROCEDURE `subang`.`find`;
DROP PROCEDURE `subang`.`findAll`;
DROP PROCEDURE `subang`.`findByBarcode`;
DROP PROCEDURE `subang`.`findByCellnum`;
DROP PROCEDURE `subang`.`findByLaundryid`;
DROP PROCEDURE `subang`.`findByLaundryname`;
DROP PROCEDURE `subang`.`findByOrderno`;
DROP PROCEDURE `subang`.`findByState`;
DROP PROCEDURE `subang`.`findByUserid`;
DROP PROCEDURE `subang`.`findByUseridAndState`;
DROP PROCEDURE `subang`.`findByUsername`;
DROP PROCEDURE `subang`.`findByWorkerid`;
DROP PROCEDURE `subang`.`findByWorkeridAndState`;

#执行下面的两个脚本
#create_proc_order
#create_proc_record