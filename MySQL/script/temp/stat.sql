select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select concat(city_t.name, district_t.name, region_t.name) 'name', region_t.id 'regionid'
	from city_t, district_t, region_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid
) as first_t left join (
	select region_t.id 'regionid', order_t.id 'orderid'
    from region_t, addr_t, order_t
    where region_t.id=addr_t.regionid and addr_t.id=order_t.addrid 
    and order_t.state=2
) as second_t on first_t.regionid=second_t.regionid
group by first_t.regionid;

select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select concat(city_t.name, district_t.name) 'name', district_t.id 'districtid'
	from city_t, district_t
    where city_t.id=district_t.cityid
) as first_t left join (
	select district_t.id 'districtid', order_t.id 'orderid'
    from district_t, region_t, addr_t, order_t
    where district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.id=order_t.addrid 
    and order_t.state=2
) as second_t on first_t.districtid=second_t.districtid
group by first_t.districtid;

select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select concat(city_t.name) 'name', city_t.id 'cityid'
	from city_t
) as first_t left join (
	select city_t.id 'cityid', order_t.id 'orderid'
    from city_t, district_t, region_t, addr_t, order_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.id=order_t.addrid 
    and order_t.state=2
) as second_t on first_t.cityid=second_t.cityid
group by first_t.cityid;

select first_t.name 'name', count(second_t.userid) 'quantity' 
from (
	select concat(city_t.name, district_t.name, region_t.name) 'name', region_t.id 'regionid'
	from city_t, district_t, region_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid
) as first_t left join (
	select region_t.id 'regionid', user_t.id 'userid'
    from region_t, addr_t, order_t, user_t
    where region_t.id=addr_t.regionid and addr_t.id=order_t.addrid and order_t.userid=user_t.id
) as second_t on first_t.regionid=second_t.regionid
group by first_t.regionid;

select first_t.name 'name', count(second_t.userid) 'quantity' 
from (
	select concat(city_t.name, district_t.name) 'name', district_t.id 'districtid'
	from city_t, district_t
    where city_t.id=district_t.cityid
) as first_t left join (
	select district_t.id 'districtid', user_t.id 'userid'
    from district_t, region_t, addr_t, order_t, user_t
    where district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.id=order_t.addrid and order_t.userid=user_t.id 
) as second_t on first_t.districtid=second_t.districtid
group by first_t.districtid;

select first_t.name 'name', count(second_t.userid) 'quantity' 
from (
	select concat(city_t.name) 'name', city_t.id 'cityid'
	from city_t
) as first_t left join (
	select city_t.id 'cityid', user_t.id 'userid'
    from city_t, district_t, region_t, addr_t, order_t, user_t
    where city_t.id=district_t.cityid and district_t.id=region_t.districtid and region_t.id=addr_t.regionid and addr_t.id=order_t.addrid and order_t.userid=user_t.id
) as second_t on first_t.cityid=second_t.cityid
group by first_t.cityid;

select first_t.name 'name', count(second_t.orderid) 'quantity' 
from (
	select user_t.nickname 'name', user_t.id 'userid'
    from user_t
) as first_t left join (
	select user_t.id 'userid', order_t.id 'orderid'
    from user_t, order_t 
	where user_t.id=order_t.userid and order_t.state=2
) as second_t on first_t.userid=second_t.userid
group by first_t.userid;

select first_t.name 'name', avg(second_t.price) 'quantity' 
from (
	select user_t.nickname 'name', user_t.id 'userid'
    from user_t
) as first_t left join (
	select user_t.id 'userid', order_t.price 'price'
    from user_t, order_t 
	where user_t.id=order_t.userid and order_t.state=2 and order_t.price is not null 
) as second_t on first_t.userid=second_t.userid
group by first_t.userid;
