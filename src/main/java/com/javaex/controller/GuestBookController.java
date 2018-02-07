package com.javaex.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.GuestBookService;
import com.javaex.vo.GuestBookVo;

@Controller
@RequestMapping("/gb")
public class GuestBookController {

	@Autowired
	GuestBookService guestBookService;
	
	@RequestMapping(value = "/list" , method = RequestMethod.GET)
	public String list(Model model) {
		List<GuestBookVo> l = guestBookService.list();
		model.addAttribute("l", l);
		return "guestbook/list";
	}
	
	//ajax 용 그냥 페이지 
	@RequestMapping(value = "/listajax" , method = RequestMethod.GET)
	public String listajax() {
		return "guestbook/listajax";
	}
	
	@RequestMapping(value = "/joinform" , method = RequestMethod.GET)
	public String joinform() {
		return "guestbook/joinform";
	}
	
	@RequestMapping(value = "/writing" , method = RequestMethod.POST)
	public String writing(@RequestParam Map<String,String> guestBook ) {
		int result = guestBookService.writing(guestBook);
		if(result == 1) {
			System.out.println("방명록 작성 성공");
		}else {
			System.out.println("방명록 작성 실패");
		}
		return "redirect:/gb/list";
	}
	
	@RequestMapping(value = "/deleteform" , method = RequestMethod.GET)
	public String deleteform() {
		return "guestbook/deleteform";
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/deletefail" , method = RequestMethod.GET)
	public String deletefail() {
		return "guestbook/deletefail";
	}
			
}
