package com.subang.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.subang.bean.Pagination;
import com.subang.util.WebConst;

public class BaseDao<T> {

	protected static final Logger LOG = Logger.getLogger(BaseDao.class.getName());

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	protected Class<T> entityClass;

	public BaseDao() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
	}

	// pagination中需要传入type和pageno。函数会计算pagenum和recordnum，并会重新计算pageno
	protected List<T> findByPage(Pagination pagination, String sqlQuery, String sqlCount,
			Object[] args) {
		int recordnum = jdbcTemplate.queryForInt(sqlCount);
		pagination.setRecordnum(recordnum);
		pagination.round();
		int offset = pagination.getOffset();

		String sql = sqlQuery + " limit " + offset + " , " + WebConst.PAGE_SIZE;
		List<T> items = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T>(entityClass));
		return items;
	}

	protected <T1> List<T1> findByPage(Pagination pagination, String sqlQuery, String sqlCount,
			Object[] args, Class<T1> clazz) {
		int recordnum = jdbcTemplate.queryForInt(sqlCount);
		pagination.setRecordnum(recordnum);
		pagination.round();
		int offset = pagination.getOffset();

		String sql = sqlQuery + " limit " + offset + " , " + WebConst.PAGE_SIZE;
		List<T1> items = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<T1>(clazz));
		return items;
	}

}
