ALTER TABLE `subang`.`balance_t` 
ADD COLUMN `type` TINYINT NOT NULL DEFAULT 0 AFTER `id`;

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