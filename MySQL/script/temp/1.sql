create procedure `statOrderNumByCity` ()
begin
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
end;