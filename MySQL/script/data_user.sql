insert into user_t values(null,0,'o6_bmjrPTlm6_2sgVt7hMZOPfL2M',null,'123','15502457990',0,0,null);
insert into addr_t values(null,1,'小明','15502457990','辽宁省沈阳市和平区文化路3号巷11号',1,1);
update user_t set addrid=1 where id=1;
insert into order_t values(null,'15062400010002',2,5.4,10.0,curdate(),9,'这是第一个订单',null,null,null,1,1,1,1,null);
insert into payment_t values(null, 1,3.0,null,null,'15062400010002');
insert into history_t values(null,2,now(),1);