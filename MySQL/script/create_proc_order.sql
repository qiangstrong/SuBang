delimiter $$

drop procedure if exists find $$
create procedure `find` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
	case type
    when 1 then		
		call findAll(start_time, end_time);
	when 2 then		
		call findByState(arg, start_time, end_time);
	when 3 then		
		call findByOrderno(arg, start_time, end_time);
	when 7 then		
		call findByUserid(arg, start_time, end_time);
	when 8 then		
		call findByWorkerid(arg, start_time, end_time);
	when 9 then		
		call findByLaundryid(arg, start_time, end_time);
	when 10 then		
		call findByBarcode(arg, start_time, end_time);
	when 11 then		
		call findByUseridAndState(upperid, arg, start_time, end_time);
	when 12 then		
		call findByWorkeridAndState(upperid, arg, start_time, end_time);
    end case;
end
$$

drop procedure if exists findAll $$
create procedure `findAll` (`start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByState $$
create procedure `findByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where state= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByOrderno $$
create procedure `findByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where orderno like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByUserid $$
create procedure `findByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where userid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByWorkerid $$
create procedure `findByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where workerid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByLaundryid $$
create procedure `findByLaundryid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where laundryid= arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByBarcode $$
create procedure `findByBarcode` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where barcode like concat('%',arg,'%') and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByUseridAndState $$
create procedure `findByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where userid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

drop procedure if exists findByWorkeridAndState $$
create procedure `findByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select * from orderdetail_v where workerid=upperid and state=arg and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time;
end
$$

delimiter ;