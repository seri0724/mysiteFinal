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
	
	
	//ajax
	public List<GuestBookVo> guestBookListAjax(Map<String,String> addSizeAndNo) {
		return sqlSession.selectList("guestbook.listAjax", addSizeAndNo);
	}
	
	public Map<String, Object> guestBookInsertAjax(Map<String,Object> gvoAndMaxNo) {
		int result = sqlSession.insert("guestbook.insertAjax", gvoAndMaxNo);
		System.out.println("게시물 추가 처리 결과 : " + result);
		if(result == 1) {
			return gvoAndMaxNo;
		}else {
			return null;
		}
		
	}
	
	public List<GuestBookVo> guestBookListAjaxAfterInsert(Map<String,Integer> maxNoAndInsertNo) {
		return sqlSession.selectList("guestbook.listAjaxAfterInsert", maxNoAndInsertNo);
	}

}
