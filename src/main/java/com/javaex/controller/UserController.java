package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/joinform" , method = RequestMethod.GET)
	public String joinform() {
		System.out.println("joinform 진입");
		return "user/joinform";
	}
	
	@RequestMapping(value = "/join" , method = RequestMethod.POST)
	public String join(@RequestParam Map<String,String> map) { //request 파라미터를 맵으로 받아서 그냥 바로 보내버렸다 꿀....핵꿀...
		System.out.println("join 진입");
		int result = 0;
		try {
			result = userService.join(map);
		} catch (DuplicateKeyException e) {
			System.err.println("이미 사용중인 email 입니다.");
		}
		if(result == 1) {
			return "redirect:/user/joinsuccess"; //mysiteFinal까지 적힌 상태이므로 이렇게 적는다
		}else {
			return "redirect:/user/joinfail";
		}
	}
	
	@RequestMapping(value = "/joinsuccess" , method = RequestMethod.GET)
	public String joinsuccess() {
		System.out.println("joinsuccess 진입");
		return "user/joinsuccess";
	}
	
	@RequestMapping(value = "/joinfail" , method = RequestMethod.GET)
	public String joinfail() {
		System.out.println("joinfail 진입");
		return "user/joinfail";
	}
	
	@RequestMapping(value = "/loginform" , method = RequestMethod.GET)
	public String loginform() {
		System.out.println("loginform 진입");
		return "user/loginform";
	}
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public String login(HttpSession session , @RequestParam Map<String,String> map) {
		System.out.println("login 진입");
		System.out.println("로그인 요청 : " + map.get("email") + "/" + map.get("password"));

		UserVo uvo = userService.login(map);
		
		if(uvo == null) {
			System.out.println("로그인 실패");
			return "redirect:/user/loginform?result=fail";
		}else {
			System.out.println("로그인 성공");
			session.setAttribute("authUser", uvo);
			
			return "redirect:/main";
		}
	}
	
	@RequestMapping(value = "/modifyform" , method = RequestMethod.GET)
	public String modifyform(HttpSession session, Model model) {
		System.out.println("modifyform 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("로그인 되지 않은 상태입니다.");
			return "user/loginform";
		}else {
			UserVo uvo = userService.modifyform(loginUser.getNo()); //로그인 되어있는 유저의 번호를 가지고 정보를 db에서 가져온다
			model.addAttribute("uvo", uvo);
			System.out.println("유저가 개인정보 수정을 시작");
			return "user/modifyform";
		}
	}
	
	@RequestMapping(value = "/modify" , method = RequestMethod.POST)
	public String modify(HttpSession session, @RequestParam Map<String,String> map) {
		System.out.println("modify 진입");
		UserVo loginUser = (UserVo) session.getAttribute("authUser");
		if(loginUser==null) {
			System.out.println("로그인 되지 않은 상태입니다.");
			return "redirect:/user/loginform";
		}else {
			map.put("no", String.valueOf(loginUser.getNo()));
			System.out.println("DB 유저 정보 수정 시작");
			int result = userService.modify(map);
			if(result == 1) {
				System.out.println("회원 정보 수정 성공");
				loginUser.setName(map.get("name"));
			}else {
				System.out.println("회원 정보 수정 실패");
			}
			return "redirect:/main";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println("logout 진입");
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/main";
	}
	
}
