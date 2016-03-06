package com.subang.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.subang.domain.ClothesType;

@Repository
public class ClothesTypeDao extends BaseDao<ClothesType> {
	public ClothesType get(Integer id) {
		String sql = "select * from clothes_type_t where id=?";
		Object[] args = { id };
		ClothesType clothesType = null;
		try {
			clothesType = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<ClothesType>(ClothesType.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return clothesType;
	}

	public void save(ClothesType clothesType) {
		String sql = "insert into clothes_type_t values(null,?,?,?,?,?)";
		Object[] args = { clothesType.getName(), clothesType.getMoney(), clothesType.getIcon(),
				clothesType.getCategoryid(), clothesType.getPriceid() };
		jdbcTemplate.update(sql, args);
	}

	public void update(ClothesType clothesType) {
		String sql = "update clothes_type_t set name=?, money=?, icon=?, categoryid=?,priceid=? where id=?";
		Object[] args = { clothesType.getName(), clothesType.getMoney(), clothesType.getIcon(),
				clothesType.getCategoryid(), clothesType.getPriceid(), clothesType.getId() };
		jdbcTemplate.update(sql, args);
	}

	public void delete(Integer id) {
		String sql = "delete from clothes_type_t where id=?";
		Object[] args = { id };
		jdbcTemplate.update(sql, args);
	}

	public List<ClothesType> findAll() {
		String sql = "select * from clothes_type_t";
		List<ClothesType> clothesTypes = jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ClothesType>(ClothesType.class));
		return clothesTypes;
	}

	public List<ClothesType> findByPriceid(Integer priceid) {
		String sql = "select * from clothes_type_t where priceid=?";
		Object[] args = { priceid };
		List<ClothesType> clothesTypes = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<ClothesType>(ClothesType.class));
		return clothesTypes;
	}

	public List<ClothesType> findIncomplete(Integer categoryid) {
		String sql = "select * from clothes_type_t where categoryid=? and priceid is null";
		Object[] args = { categoryid };
		List<ClothesType> clothesTypes = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<ClothesType>(ClothesType.class));
		return clothesTypes;
	}

	public ClothesType getDetail(Integer clothesTypeid) {
		String sql = "select clothes_type_t.*, price_t.name `pricename` from clothes_type_t left join price_t "
				+ "on clothes_type_t.priceid=price_t.id  where clothes_type_t.id=?";
		Object[] args = { clothesTypeid };
		ClothesType clothesType = null;
		try {
			clothesType = jdbcTemplate.queryForObject(sql, args,
					new BeanPropertyRowMapper<ClothesType>(ClothesType.class));
		} catch (EmptyResultDataAccessException e) {
		}
		return clothesType;
	}

	public List<ClothesType> findDetailByCategoryid(Integer categoryid) {
		String sql = "select clothes_type_t.*, price_t.name `pricename` from clothes_type_t left join price_t "
				+ "on clothes_type_t.priceid=price_t.id  where clothes_type_t.categoryid=?";
		Object[] args = { categoryid };
		List<ClothesType> clothesTypes = jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper<ClothesType>(ClothesType.class));
		return clothesTypes;
	}
}
