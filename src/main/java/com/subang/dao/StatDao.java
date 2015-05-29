package com.subang.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.bean.StatArg;
import com.subang.bean.StatItem;

@Repository
public class StatDao extends BaseDao<StatItem> {

	public List<StatItem> findByStatArg(StatArg statArg) {
		String sql = "call stat(?,?,?)";
		Object[] args = { statArg.getType0(), statArg.getType1(), statArg.getType2() };
		List<StatItem> statItems = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<StatItem>(StatItem.class));
		return statItems;
	}
}
