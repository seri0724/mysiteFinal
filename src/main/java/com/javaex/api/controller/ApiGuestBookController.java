package com.javaex.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
public class ApiGuestBookController {
	
	@Autowired
	GuestBookService guestBookService;

	//ResponseBody 어노테이션을 선언하면 리턴할때 body 에 실어서 담기로 정의 됨과 동시에 json 포맷타입으로 전환되어 리턴된다.
	@ResponseBody
	@RequestMapping(value="/gb/api/fetchlistajax", method = RequestMethod.POST)
	public List<GuestBookVo> fetchlistajax(@RequestParam Map<String,String> addSizeAndNo) {
		System.out.println("게시물 리스트 진입");
		List<GuestBookVo> l = guestBookService.listAjax(addSizeAndNo);
		System.out.println("addSize : " + addSizeAndNo.get("addSize") + " minNo : " + addSizeAndNo.get("minNo"));
		return l;
	}
	
	@ResponseBody
	@RequestMapping(value = "/gb/api/gbwritingajax" , method = RequestMethod.POST)
	public List<GuestBookVo> gbwritingajax(@RequestBody Map<String,Object> gvoAndMaxNo) {
		System.out.println("게시물 삽입 진입");
		System.out.println("gvo : " + gvoAndMaxNo.get("gvo") + " maxNo : " + gvoAndMaxNo.get("maxNo"));
		List<GuestBookVo> afterInsertList = guestBookService.gbwritingajax(gvoAndMaxNo);
		return afterInsertList;
	}
	
	@ResponseBody
	@RequestMapping(value = "/gb/api/gbdeletingajax" , method = RequestMethod.POST)
	public int gbdeletingajax(@RequestParam Map<String,String> guestBook) {
		System.out.println("게시물 삭제 진입");
		int result = guestBookService.delete(guestBook);
		return result;
	}
	
	
}
