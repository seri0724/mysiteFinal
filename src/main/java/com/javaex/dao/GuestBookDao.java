package com.javaex.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestBookVo;

@Repository
public class GuestBookDao {
	
	@Autowired
	SqlSession sqlSession;
	
	public List<GuestBookVo> guestBookList() {
		return sqlSession.selectList("guestbook.list");
	}
	
	public int guestBookInsert(Map<String,String> guestBook) {
		return sqlSession.insert("guestbook.insert", guestBook);
	}

	public int guestBookDelete(Map<String,String> guestBook) {
		return sqlSession.delete("guestbook.delete", guestBook);
	}
	
}
