package com.subang.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.domain.Admin;
import com.subang.exception.SuException;

@Service
public class RoleService extends BaseService  {
	
	public void modifyAdmin(Admin admin) throws SuException {
		try {
			adminDao.update(admin);
		} catch (DuplicateKeyException e) {
			throw new SuException("用户名不能相同。");
		}
	}
}
