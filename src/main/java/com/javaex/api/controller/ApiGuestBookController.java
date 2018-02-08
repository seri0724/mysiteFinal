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
	@RequestMapping(value="/gb/api/fetchlistajax", method = RequestMethod.POST)
	public List<GuestBookVo> fetchlistajax(@RequestParam Map<String,String> addSizeAndNo) {
		List<GuestBookVo> l = guestBookService.listAjax(addSizeAndNo);
		System.out.println(addSizeAndNo.get("addSize") + " " + addSizeAndNo.get("no"));
		return l;
	}
	
	@RequestMapping(value = "/gb/api/gbwritingajax" , method = RequestMethod.POST)
	public Map<String, String> gbwritingajax(@RequestParam Map<String,Object> gvoAndNo) {
		System.out.println(gvoAndNo.get("gvo"));
		System.out.println(gvoAndNo.get("no"));
		
		
//		Map<String, String> gvo = guestBookService.writingAjax(gvo);
		return null;
	}
	
	@RequestMapping(value = "/gb/api/gbdeletingajax" , method = RequestMethod.POST)
	public String gbdeletingajax(@RequestParam Map<String,String> guestBook) {
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
