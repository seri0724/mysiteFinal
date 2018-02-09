package com.javaex.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestBookDao;
import com.javaex.vo.GuestBookVo;

@Service
public class GuestBookService {
	
	@Autowired
	GuestBookDao gbDao;

	public List<GuestBookVo> list() {
		return gbDao.guestBookList();
	}
	
	public int writing(Map<String, String> guestBook) {
		return gbDao.guestBookInsert(guestBook);
	}

	public int delete(Map<String, String> guestBook) {
		return gbDao.guestBookDelete(guestBook);
	}
	
	
	//ajax
	public List<GuestBookVo> listAjax(Map<String,String> addSizeAndNo) {
		return gbDao.guestBookListAjax(addSizeAndNo);
	}
	
	public List<GuestBookVo> gbwritingajax(Map<String,Object> gvoAndMaxNo) {
		Map<String,Object> afterInsertMap = gbDao.guestBookInsertAjax(gvoAndMaxNo);
		
		if(afterInsertMap != null) {
			Map<String,Object> gvo = (Map<String,Object>) afterInsertMap.get("gvo"); //**gvo 객체를 받아와서 Map 으로 강제 형변환 하였다.
			Map<String,Integer> maxNoAndInsertNo = new HashMap<String,Integer>();
			maxNoAndInsertNo.put("maxNo",(Integer) gvoAndMaxNo.get("maxNo"));
			maxNoAndInsertNo.put("insertNo", (Integer) gvo.get("no"));
			
			List<GuestBookVo> afterInsertList= gbDao.guestBookListAjaxAfterInsert(maxNoAndInsertNo);
			System.out.println(afterInsertList);
			return afterInsertList;
		}else {
			return new ArrayList<GuestBookVo>();
		}
	}

}
