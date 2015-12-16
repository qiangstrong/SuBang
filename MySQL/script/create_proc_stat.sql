delimiter $$

drop procedure if exists stat $$
create procedure `stat` (`type0` int, `type1` int, `type2` int, `start_time` datetime, `end_time` datetime)
begin
	case type0
    when 1 then		#按区域统计
		case type1		
        when 0 then		#统计订单
			case type2
            when 0 then 	#城市
				call statOrderNumByCity(start_time, end_time);
            when 1 then		#区
				call statOrderNumByDistrict(start_time, end_time);
            when 2 then		#小区
				call statOrderNumByRegion(start_time, end_time);
            end case;
        when 1 then 	#统计用户
			case type2
            when 0 then 	#城市
				call statUserNumByCity();
            when 1 then		#区
				call statUserNumByDistrict();
            when 2 then		#小区
				call statUserNumByRegion();
            end case;
        end case;
    when 2 then		#按用户统计
		case type1		
        when 0 then		#统计订单
			call statOrderNumByUser(start_time, end_time);
        when 1 then 	#统计平均订单价格
			call statPriceAvgByUser(start_time, end_time);
        end case;
    end case;
end
$$

#统计完整的，不完整的区域
#统计处于各种状态的订单
drop procedure if exists statOrderNumByCity $$
create procedure `statOrderNumByCity` (`start_time` datetime, `end_time` datetime)
begin
select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select concat(city_t.name) 'name', city_t.id 'cityid'
	from city_t
) as first_t left join (
	select city_t.id 'cityid', orderdetail_v.id 'orderid'
    from city_t, district_t, region_t, addr_t, orderdetail_v
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.id=orderdetail_v.addrid 
     and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time
) as second_t on first_t.cityid=second_t.cityid
group by first_t.cityid;
end
$$

drop procedure if exists statOrderNumByDistrict $$
create procedure `statOrderNumByDistrict` (`start_time` datetime, `end_time` datetime)
begin
select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select concat(city_t.name, district_t.name) 'name', district_t.id 'districtid'
	from city_t, district_t
    where city_t.id=district_t.cityid
) as first_t left join (
	select district_t.id 'districtid', orderdetail_v.id 'orderid'
    from district_t, region_t, addr_t, orderdetail_v
    where district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.id=orderdetail_v.addrid 
     and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time
) as second_t on first_t.districtid=second_t.districtid
group by first_t.districtid;
end
$$

drop procedure if exists statOrderNumByRegion $$
create procedure `statOrderNumByRegion` (`start_time` datetime, `end_time` datetime)
begin
select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select concat(city_t.name, district_t.name, region_t.name) 'name', region_t.id 'regionid'
	from city_t, district_t, region_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid
) as first_t left join (
	select region_t.id 'regionid', orderdetail_v.id 'orderid'
    from region_t, addr_t, orderdetail_v
    where region_t.id=addr_t.regionid and addr_t.id=orderdetail_v.addrid 
     and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time
) as second_t on first_t.regionid=second_t.regionid
group by first_t.regionid;
end
$$

#用户没有区域之分,实际统计的是地址的数量
#统计valid=1的地址
drop procedure if exists statUserNumByCity $$
create procedure `statUserNumByCity` ()
begin
select first_t.name 'name', count(second_t.addrid) 'quantity' 
from (
	select concat(city_t.name) 'name', city_t.id 'cityid'
	from city_t
) as first_t left join (
	select city_t.id 'cityid', addr_t.id 'addrid'
    from city_t, district_t, region_t, addr_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.valid=1
) as second_t on first_t.cityid=second_t.cityid
group by first_t.cityid;
end
$$

drop procedure if exists statUserNumByDistrict $$
create procedure `statUserNumByDistrict` ()
begin
select first_t.name 'name', count(second_t.addrid) 'quantity' 
from (
	select concat(city_t.name, district_t.name) 'name', district_t.id 'districtid'
	from city_t, district_t
    where city_t.id=district_t.cityid
) as first_t left join (
	select district_t.id 'districtid', addr_t.id 'addrid'
    from district_t, region_t, addr_t
    where district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.valid=1
) as second_t on first_t.districtid=second_t.districtid
group by first_t.districtid;
end
$$

drop procedure if exists statUserNumByRegion $$
create procedure `statUserNumByRegion` ()
begin
select first_t.name 'name', count(second_t.addrid) 'quantity' 
from (
	select concat(city_t.name, district_t.name, region_t.name) 'name', region_t.id 'regionid'
	from city_t, district_t, region_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid
) as first_t left join (
	select region_t.id 'regionid', addr_t.id 'addrid'
    from region_t, addr_t
    where region_t.id=addr_t.regionid and addr_t.valid=1
) as second_t on first_t.regionid=second_t.regionid
group by first_t.regionid;
end
$$


drop procedure if exists statOrderNumByUser $$
create procedure `statOrderNumByUser` (`start_time` datetime, `end_time` datetime)
begin
select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select user_t.cellnum 'name', user_t.id 'userid'
    from user_t
) as first_t left join (
	select user_t.id 'userid', orderdetail_v.id 'orderid'
    from user_t, orderdetail_v 
	where user_t.id=orderdetail_v.userid
     and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time
) as second_t on first_t.userid=second_t.userid
group by first_t.userid;
end
$$

drop procedure if exists statPriceAvgByUser $$
create procedure `statPriceAvgByUser` (`start_time` datetime, `end_time` datetime)
begin
select first_t.name 'name', avg(second_t.money) 'quantity' 
from (
	select user_t.cellnum 'name', user_t.id 'userid'
    from user_t
) as first_t left join (
	select user_t.id 'userid', orderdetail_v.money+orderdetail_v.freight 'money'
    from user_t, orderdetail_v 
	where user_t.id=orderdetail_v.userid and orderdetail_v.money is not null and orderdetail_v.freight is not null
     and orderdetail_v.accept_time>=start_time and orderdetail_v.accept_time<=end_time
) as second_t on first_t.userid=second_t.userid
group by first_t.userid;
end
$$

delimiter ;
