delimiter $$

#订单表级联添加支付记录
drop trigger if exists order_ins_tr$$
create trigger `order_ins_tr`
after insert on order_t
for each row 
begin
insert into payment_t(orderno) values(new.orderno);
end$$

#订单表级联删除支付记录
drop trigger if exists order_del_tr$$
create trigger `order_del_tr`
after delete on order_t
for each row 
begin
delete from payment_t where orderno=old.orderno;
end$$

#余额表级联添加支付记录
drop trigger if exists balance_ins_tr$$
create trigger `balance_ins_tr`
after insert on balance_t
for each row 
begin
insert into payment_t(orderno) values(new.orderno);
end$$

#余额表级联删除支付记录
drop trigger if exists balance_del_tr$$
create trigger `balance_del_tr`
after delete on balance_t
for each row 
begin
delete from payment_t where orderno=old.orderno;
end$$

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