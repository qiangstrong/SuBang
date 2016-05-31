delimiter $$

drop procedure if exists findRecord $$
create procedure `findRecord` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
	case type
    when 1 then		
		call findRecordAll(start_time, end_time, offset0, page_size);
	when 2 then		
		call findRecordByState(arg, start_time, end_time, offset0, page_size);
	when 3 then		
		call findRecordByOrderno(arg, start_time, end_time, offset0, page_size);
	when 4 then		
		call findRecordByUsername(arg, start_time, end_time, offset0, page_size);
	when 5 then		
		call findRecordByCellnum(arg, start_time, end_time, offset0, page_size);
	when 7 then		
		call findRecordByUserid(arg, start_time, end_time, offset0, page_size);
	when 8 then		
		call findRecordByWorkerid(arg, start_time, end_time, offset0, page_size);
	when 11 then		
		call findRecordByUseridAndState(upperid, arg, start_time, end_time, offset0, page_size);
	when 12 then		
		call findRecordByWorkeridAndState(upperid, arg, start_time, end_time, offset0, page_size);
    end case;
end
$$

drop procedure if exists findRecordAll $$
create procedure `findRecordAll` (`start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByState $$
create procedure `findRecordByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where state= arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByOrderno $$
create procedure `findRecordByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where orderno like concat('%',arg,'%') and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByUsername $$
create procedure `findRecordByUsername` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select recorddetail_v.* from recorddetail_v, user_t where recorddetail_v.userid=user_t.id and user_t.nickname like concat('%',arg,'%') and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByCellnum $$
create procedure `findRecordByCellnum` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select recorddetail_v.* from recorddetail_v, user_t where recorddetail_v.userid=user_t.id and user_t.cellnum like concat('%',arg,'%') and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByUserid $$
create procedure `findRecordByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where userid= arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByWorkerid $$
create procedure `findRecordByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where workerid= arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByUseridAndState $$
create procedure `findRecordByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where userid=upperid and state=arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$

drop procedure if exists findRecordByWorkeridAndState $$
create procedure `findRecordByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime, `offset0` int, `page_size` int)
begin
select * from recorddetail_v where workerid=upperid and state=arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time limit offset0, page_size;
end
$$


#和上面的函数一一对应，查询返回的记录数
drop procedure if exists countRecord $$
create procedure `countRecord` (`type` int, `upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
	case type
    when 1 then		
		call countRecordAll(start_time, end_time);
	when 2 then		
		call countRecordByState(arg, start_time, end_time);
	when 3 then		
		call countRecordByOrderno(arg, start_time, end_time);
	when 4 then		
		call countRecordByUsername(arg, start_time, end_time);
	when 5 then		
		call countRecordByCellnum(arg, start_time, end_time);
	when 7 then		
		call countRecordByUserid(arg, start_time, end_time);
	when 8 then		
		call countRecordByWorkerid(arg, start_time, end_time);
	when 11 then		
		call countRecordByUseridAndState(upperid, arg, start_time, end_time);
	when 12 then		
		call countRecordByWorkeridAndState(upperid, arg, start_time, end_time);
    end case;
end
$$

drop procedure if exists countRecordAll $$
create procedure `countRecordAll` (`start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByState $$
create procedure `countRecordByState` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where state= arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByOrderno $$
create procedure `countRecordByOrderno` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where orderno like concat('%',arg,'%') and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByUsername $$
create procedure `countRecordByUsername` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v, user_t where recorddetail_v.userid=user_t.id and user_t.nickname like concat('%',arg,'%') and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByCellnum $$
create procedure `countRecordByCellnum` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v, user_t where recorddetail_v.userid=user_t.id and user_t.cellnum like concat('%',arg,'%') and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByUserid $$
create procedure `countRecordByUserid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where userid= arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByWorkerid $$
create procedure `countRecordByWorkerid` (`arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where workerid= arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByUseridAndState $$
create procedure `countRecordByUseridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where userid=upperid and state=arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

drop procedure if exists countRecordByWorkeridAndState $$
create procedure `countRecordByWorkeridAndState` (`upperid` int, `arg` varchar(100), `start_time` datetime, `end_time` datetime)
begin
select count(*) from recorddetail_v where workerid=upperid and state=arg and recorddetail_v.time>=start_time and recorddetail_v.time<=end_time;
end
$$

delimiter ;