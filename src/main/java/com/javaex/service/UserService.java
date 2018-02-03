package com.javaex.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	public int join(Map<String,String> map) {
		return userDao.userInsert(map);
	}
	
	public UserVo login(Map<String,String> map) {
		return userDao.userSelect(map);
	}
	
	public UserVo modifyform(int no) {
		return userDao.userSelectByNo(no);
	}
	
	public int modify(Map<String,String> map) {
		return userDao.userUpdate(map);
	}
	
	public int delete(Map<String,String> map) {
		return userDao.userDelete(map);
	}
}
