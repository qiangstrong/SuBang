create view  area_v 
as ( select city_t.name `cityname`, district_t.name `districtname`, region_t.name `regionname`, region_t.id `regionid`, region_t.workerid `workerid` 
from city_t, district_t, region_t 
where city_t.id=district_t.cityid and district_t.id=region_t.districtid 
);

create view addrdetail_v 
as ( select addr_t.*, area_v.cityname, area_v.districtname, area_v.regionname 
from addr_t, area_v
where addr_t.regionid=area_v.regionid
);

create view orderdetail_v 
as ( select order_t.*, user_t.nickname, addrdetail_v.name `addrname`, addrdetail_v.cellnum `addrcellnum`, addrdetail_v.cityname, addrdetail_v.districtname, addrdetail_v.regionname, addrdetail_v.detail `addrdetail`,  
worker_t.name `workername`, worker_t.cellnum `workercellnum` 
from order_t, user_t, addrdetail_v, worker_t 
where order_t.userid=user_t.id and order_t.addrid=addrdetail_v.id and order_t.workerid=worker_t.id 
);


