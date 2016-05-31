delimiter $$

drop procedure if exists findOrder $$
create procedure `findOrder` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
	case type
    when 1 then		
		call findOrderAll(start_time, end_time, offset0, page_size);
	when 2 then		
		call findOrderByState(arg, start_time, end_time, offset0, page_size);
	when 3 then		
		call findOrderByOrderno(arg, start_time, end_time, offset0, page_size);
	when 4 then		
		call findOrderByUsername(arg, start_time, end_time, offset0, page_size);
	when 5 then		
		call findOrderByCellnum(arg, start_time, end_time, offset0, page_size);
	when 6 then		
		call findOrderByLaundryname(arg, start_time, end_time, offset0, page_size);
	when 7 then		
		call findOrderByUserid(arg, start_time, end_time, offset0, page_size);
	when 8 then		
		call findOrderByWorkerid(arg, start_time, end_time, offset0, page_size);
	when 9 then		
		call findOrderByLaundryid(arg, start_time, end_time, offset0, page_size);
	when 10 then		
		call findOrderByBarcode(arg, start_time, end_time, offset0, page_size);
	when 11 then		
		call findOrderByUseridAndState(upperid, arg, start_time, end_time, offset0, page_size);
	when 12 then		
		call findOrderByWorkeridAndState(upperid, arg, start_time, end_time, offset0, page_size);
    end case;
end
$$

drop procedure if exists findOrderAll $$
create procedure `findOrderAll` (`start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByState $$
create procedure `findOrderByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where state= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByOrderno $$
create procedure `findOrderByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where orderno like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByUsername $$
create procedure `findOrderByUsername` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select orderdetail_v.* from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.nickname like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByCellnum $$
create procedure `findOrderByCellnum` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select orderdetail_v.* from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.cellnum like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByLaundryname $$
create procedure `findOrderByLaundryname` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select orderdetail_v.* from orderdetail_v, laundry_t where orderdetail_v.laundryid=laundry_t.id and laundry_t.name like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByUserid $$
create procedure `findOrderByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where userid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByWorkerid $$
create procedure `findOrderByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where workerid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByLaundryid $$
create procedure `findOrderByLaundryid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where laundryid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByBarcode $$
create procedure `findOrderByBarcode` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where barcode like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByUseridAndState $$
create procedure `findOrderByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where userid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findOrderByWorkeridAndState $$
create procedure `findOrderByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where workerid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$


#和上面的函数一一对应，查询返回的记录数
drop procedure if exists countOrder $$
create procedure `countOrder` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
	case type
    when 1 then		
		call countOrderAll(start_time, end_time);
	when 2 then		
		call countOrderByState(arg, start_time, end_time);
	when 3 then		
		call countOrderByOrderno(arg, start_time, end_time);
	when 4 then		
		call countOrderByUsername(arg, start_time, end_time);
	when 5 then		
		call countOrderByCellnum(arg, start_time, end_time);
	when 6 then		
		call countOrderByLaundryname(arg, start_time, end_time);
	when 7 then		
		call countOrderByUserid(arg, start_time, end_time);
	when 8 then		
		call countOrderByWorkerid(arg, start_time, end_time);
	when 9 then		
		call countOrderByLaundryid(arg, start_time, end_time);
	when 10 then		
		call countOrderByBarcode(arg, start_time, end_time);
	when 11 then		
		call countOrderByUseridAndState(upperid, arg, start_time, end_time);
	when 12 then		
		call countOrderByWorkeridAndState(upperid, arg, start_time, end_time);
    end case;
end
$$

drop procedure if exists countOrderAll $$
create procedure `countOrderAll` (`start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByState $$
create procedure `countOrderByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where state= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByOrderno $$
create procedure `countOrderByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where orderno like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByUsername $$
create procedure `countOrderByUsername` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.nickname like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByCellnum $$
create procedure `countOrderByCellnum` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.cellnum like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByLaundryname $$
create procedure `countOrderByLaundryname` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v, laundry_t where orderdetail_v.laundryid=laundry_t.id and laundry_t.name like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByUserid $$
create procedure `countOrderByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where userid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByWorkerid $$
create procedure `countOrderByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where workerid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByLaundryid $$
create procedure `countOrderByLaundryid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where laundryid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByBarcode $$
create procedure `countOrderByBarcode` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where barcode like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByUseridAndState $$
create procedure `countOrderByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where userid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countOrderByWorkeridAndState $$
create procedure `countOrderByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where workerid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

delimiter ;