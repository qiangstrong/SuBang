package com.subang.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.subang.domain.User;
import com.subang.exception.BackException;

@Service
public class FrontUserService extends CommUserService {
	
	/**
	 * 与用户相关的操作
	 */
	public User getUserByOpenid(String openid){
		return userDao.findByOpenid(openid);
	}
	
	public void addUser(User user){
		try {
			userDao.save(user);
		} catch (DuplicateKeyException e) {
			//用户取消关注后，再关注时引发此异常，此处不需要处理
		}
	}
	
	
}
