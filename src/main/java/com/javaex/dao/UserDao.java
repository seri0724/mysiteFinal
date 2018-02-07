package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	SqlSession sqlSession;
	
	public int userInsert(Map<String,String> map) {
		return sqlSession.insert("user.insert",map);
	}
	
	public UserVo userSelect(Map<String,String> map) {
		return sqlSession.selectOne("user.select", map);
	}
	
	public UserVo userSelectByNo(int no) {
		return sqlSession.selectOne("user.selectByNo", no);
	}
	
	public UserVo userEmailCheck(String email) {
		return sqlSession.selectOne("user.emailCheck", email);
	}
	
	public int userUpdate(Map<String,String> map) {
		return sqlSession.update("user.update", map);
	}
	
	public int userDelete(Map<String,String> map) {
		return sqlSession.delete("user.delete", map);
	}
	
	public List<UserVo> userList() {
		return sqlSession.selectList("user.list");
	}
	
}
