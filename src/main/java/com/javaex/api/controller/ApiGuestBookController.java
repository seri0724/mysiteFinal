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

	@ResponseBody
	@RequestMapping(value="/gb/api/addlistajax", method = RequestMethod.POST)
	public List<GuestBookVo> addlistajax(@RequestBody Map<String,String> MinMax) {
		List<GuestBookVo> l = guestBookService.listAjax(MinMax);
		System.out.println(MinMax.get("min") + " " + MinMax.get("max"));
		return l;
	}
	
	@RequestMapping(value = "/gb/api/writingajax" , method = RequestMethod.POST)
	public void writingajax(@RequestParam Map<String,String> guestBook) {
		int result = guestBookService.writing(guestBook);
		if(result == 1) {
			System.out.println("방명록 작성 성공");
		}else {
			System.out.println("방명록 작성 실패");
		}
	}
	
	@RequestMapping(value = "/gb/api/deleteajax" , method = RequestMethod.POST)
	public String deleteform(@RequestParam Map<String,String> guestBook) {
		int result = guestBookService.delete(guestBook);
		if(result == 1) {
			System.out.println("비밀번호 일치 : 방명록 삭제 성공");
			return "redirect:/gb/list";
		}else {
			System.out.println("비밀번호 불일치 : 방명록 삭제 실패");
			return "redirect:/gb/deletefail";
		}
		
	}
}
