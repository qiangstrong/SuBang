CREATE TABLE `addr_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(8) NOT NULL,
  `cellnumber` char(11) NOT NULL,
  `detail` varchar(200) NOT NULL,
  `userid` char(28) DEFAULT NULL,
  `regionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  KEY `regionid` (`regionid`),
  CONSTRAINT `addr_t_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user_t` (`openid`),
  CONSTRAINT `addr_t_ibfk_2` FOREIGN KEY (`regionid`) REFERENCES `region_t` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `merchant_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(40) NOT NULL,
  `cellnumber` char(11) NOT NULL,
  `detail` varchar(200) NOT NULL,
  `regionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `regionid` (`regionid`),
  CONSTRAINT `merchant_t_ibfk_1` FOREIGN KEY (`regionid`) REFERENCES `region_t` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order_t` (
  `id` char(10) NOT NULL,
  `detail` varchar(200) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `price` int(11) DEFAULT NULL,
  `placetime` datetime NOT NULL,
  `fetchtime` datetime DEFAULT NULL,
  `finishtime` datetime DEFAULT NULL,
  `canceltime` datetime DEFAULT NULL,
  `userid` char(28) DEFAULT NULL,
  `addrid` int(11) DEFAULT NULL,
  `merchantid` int(11) DEFAULT NULL,
  `workerid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  KEY `addrid` (`addrid`),
  KEY `merchantid` (`merchantid`),
  KEY `workerid` (`workerid`),
  CONSTRAINT `order_t_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user_t` (`openid`),
  CONSTRAINT `order_t_ibfk_2` FOREIGN KEY (`addrid`) REFERENCES `addr_t` (`id`),
  CONSTRAINT `order_t_ibfk_3` FOREIGN KEY (`merchantid`) REFERENCES `merchant_t` (`id`),
  CONSTRAINT `order_t_ibfk_4` FOREIGN KEY (`workerid`) REFERENCES `worker_t` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `region_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city` char(10) NOT NULL,
  `district` char(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_t` (
  `openid` char(28) NOT NULL,
  `username` char(60) DEFAULT NULL,
  `cellnumber` char(11) DEFAULT NULL,
  `score` int(10) DEFAULT NULL,
  `picture` longblob,
  PRIMARY KEY (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `worker_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(40) NOT NULL,
  `cellnumber` char(11) NOT NULL,
  `regionid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `regionid` (`regionid`),
  CONSTRAINT `worker_t_ibfk_1` FOREIGN KEY (`regionid`) REFERENCES `region_t` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
