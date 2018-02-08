package com.javaex.service;

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
	
	public List<GuestBookVo> listAjax(Map<String,String> addSizeAndNo) {
		return gbDao.guestBookListAjax(addSizeAndNo);
	}
	
	public int writing(Map<String, String> guestBook) {
		return gbDao.guestBookInsert(guestBook);
	}

	public int delete(Map<String, String> guestBook) {
		return gbDao.guestBookDelete(guestBook);
	}

	public Map<String, String> writingAjax(Map<String, String> guestBook) {
		return gbDao.guestBookInsertAjax(guestBook);
	}

}
