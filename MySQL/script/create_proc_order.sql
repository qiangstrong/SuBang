delimiter $$

drop procedure if exists find $$
create procedure `find` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
	case type
    when 1 then		
		call findAll(start_time, end_time, offset0, page_size);
	when 2 then		
		call findByState(arg, start_time, end_time, offset0, page_size);
	when 3 then		
		call findByOrderno(arg, start_time, end_time, offset0, page_size);
	when 4 then		
		call findByUsername(arg, start_time, end_time, offset0, page_size);
	when 5 then		
		call findByCellnum(arg, start_time, end_time, offset0, page_size);
	when 6 then		
		call findByLaundryname(arg, start_time, end_time, offset0, page_size);
	when 7 then		
		call findByUserid(arg, start_time, end_time, offset0, page_size);
	when 8 then		
		call findByWorkerid(arg, start_time, end_time, offset0, page_size);
	when 9 then		
		call findByLaundryid(arg, start_time, end_time, offset0, page_size);
	when 10 then		
		call findByBarcode(arg, start_time, end_time, offset0, page_size);
	when 11 then		
		call findByUseridAndState(upperid, arg, start_time, end_time, offset0, page_size);
	when 12 then		
		call findByWorkeridAndState(upperid, arg, start_time, end_time, offset0, page_size);
    end case;
end
$$

drop procedure if exists findAll $$
create procedure `findAll` (`start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByState $$
create procedure `findByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where state= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByOrderno $$
create procedure `findByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where orderno like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByUsername $$
create procedure `findByUsername` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select orderdetail_v.* from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.nickname like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByCellnum $$
create procedure `findByCellnum` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select orderdetail_v.* from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.cellnum like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByLaundryname $$
create procedure `findByLaundryname` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select orderdetail_v.* from orderdetail_v, laundry_t where orderdetail_v.laundryid=laundry_t.id and laundry_t.name like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByUserid $$
create procedure `findByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where userid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByWorkerid $$
create procedure `findByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where workerid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByLaundryid $$
create procedure `findByLaundryid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where laundryid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByBarcode $$
create procedure `findByBarcode` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where barcode like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByUseridAndState $$
create procedure `findByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where userid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findByWorkeridAndState $$
create procedure `findByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from orderdetail_v where workerid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time limit offset0, page_size;
end
$$


#和上面的函数一一对应，查询返回的记录数
drop procedure if exists count0 $$
create procedure `count0` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
	case type
    when 1 then		
		call countAll(start_time, end_time);
	when 2 then		
		call countByState(arg, start_time, end_time);
	when 3 then		
		call countByOrderno(arg, start_time, end_time);
	when 4 then		
		call countByUsername(arg, start_time, end_time);
	when 5 then		
		call countByCellnum(arg, start_time, end_time);
	when 6 then		
		call countByLaundryname(arg, start_time, end_time);
	when 7 then		
		call countByUserid(arg, start_time, end_time);
	when 8 then		
		call countByWorkerid(arg, start_time, end_time);
	when 9 then		
		call countByLaundryid(arg, start_time, end_time);
	when 10 then		
		call countByBarcode(arg, start_time, end_time);
	when 11 then		
		call countByUseridAndState(upperid, arg, start_time, end_time);
	when 12 then		
		call countByWorkeridAndState(upperid, arg, start_time, end_time);
    end case;
end
$$

drop procedure if exists countAll $$
create procedure `countAll` (`start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByState $$
create procedure `countByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where state= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByOrderno $$
create procedure `countByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where orderno like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByUsername $$
create procedure `countByUsername` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.nickname like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByCellnum $$
create procedure `countByCellnum` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v, user_t where orderdetail_v.userid=user_t.id and user_t.cellnum like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByLaundryname $$
create procedure `countByLaundryname` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v, laundry_t where orderdetail_v.laundryid=laundry_t.id and laundry_t.name like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByUserid $$
create procedure `countByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where userid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByWorkerid $$
create procedure `countByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where workerid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByLaundryid $$
create procedure `countByLaundryid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where laundryid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByBarcode $$
create procedure `countByBarcode` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where barcode like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByUseridAndState $$
create procedure `countByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where userid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists countByWorkeridAndState $$
create procedure `countByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from orderdetail_v where workerid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

delimiter ;