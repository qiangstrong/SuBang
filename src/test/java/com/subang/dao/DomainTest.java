package com.subang.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.subang.domain.Color;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DomainTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void test() {
		Color color = new Color();
		color.setName("蓝色");
		save(color);
		System.out.println(color.getId());
		pause();
	}

	public void save(final Color color) {
		final String sql = "insert into color_t values(null,?)";
		KeyHolder holder = new GeneratedKeyHolder();
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setObject(1, color.getName());
				return ps;
			}
		};
		jdbcTemplate.update(psc, holder);
		color.setId(holder.getKey().intValue());
	}

	public void pause() {

	}
}
