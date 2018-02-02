package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.UserDAO;
import com.javaex.service.UserService;
import com.javaex.vo.UserVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/joinform")
	public String joinform() {
		System.out.println("joinform 진입");
		return "user/joinform";
	}
	
	@RequestMapping("/join")
	public String join() {
		System.out.println("join 진입");
		
//	}else if(actionName.equals("join")) {
//		System.out.println("join 진입");
//		String name = request.getParameter("name");
//		String email = request.getParameter("email");
//		String password = request.getParameter("password");
//		String gender = request.getParameter("gender");
//		UserVO vo = new UserVO();
//		vo.setName(name);
//		vo.setEmail(email);
//		vo.setPassword(password);
//		vo.setGender(gender);
//		UserDAO dao = new UserDAO();
//		dao.insert(vo);
//		WebUtil.redirect(request, response, "/mysite/user?a=joinsuccess"); //DB 에 insert 를 하는 페이지이기 때문에 forward 를 사용 하면 안된다. 중복요청의 가능성이 생기기 때문에....
//		
		return "user/join";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess() {
		System.out.println("joinsuccess 진입");
		return "user/joinsuccess";
	}
	
	@RequestMapping("/loginform")
	public String loginform() {
		System.out.println("loginform 진입");
		return "user/loginform";
	}
	
	@RequestMapping("/login")
	public String login(HttpSession session , @RequestParam("email") String email, @RequestParam("password") String password) {
		System.out.println("login 진입");
		System.out.println("로그인 요청 : " + email + "/" + password);
		
		UserVO vo = userService.login(email,password);
		
		return "redirect:/mysite4/user/loginform&result=fail";
		
		if(vo == null) {
			System.out.println("로그인 실패");
			WebUtil.redirect(request, response, "/mysite/user?a=loginform&result=fail");
		}else {
			System.out.println("로그인 성공");
			HttpSession session = request.getSession();
			session.setAttribute("authUser", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
		}
		
		
		
		return "user/loginform";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println("logout 진입");
		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:main";
	}
	
	@RequestMapping("/modifyform")
	public String modifyform(HttpServletRequest request, HttpSession session) {
		System.out.println("modifyform 진입");
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser==null) {
			System.out.println("로그인 되지 않은 상태입니다.");
			return "user/loginform";
		}else {
			System.out.println("유저가 개인정보 수정을 시작");
			request.setAttribute("userVo", authUser);  //가지고 있는 세션값을 이용하여 클라이언트 요청 페이지에 채워서 보내준다.
			return "modifyform";
		}
	}
	
//		}else if(actionName.equals("modify")) {
//			System.out.println("modify 진입");
//			
//			HttpSession session = request.getSession(true);
//			UserVO authUser = (UserVO) session.getAttribute("authUser");
//			
//			if(authUser==null) {
//				System.out.println("로그인 되지 않은 상태입니다.");
//				WebUtil.redirect(request, response, "/mysite/user?a=loginform");
//			}else {
//				int no = authUser.getNo();
//				String name = request.getParameter("name");
//				String password = request.getParameter("password");
//				String gender = request.getParameter("gender");
//				UserVO vo = new UserVO(no,name,null,password,gender);
//				UserDAO dao = new UserDAO();
//				dao.update(vo);
//				
//				authUser.setName(name);
//				authUser.setPassword(password);
//				authUser.setGender(gender);
//				
//				WebUtil.redirect(request, response, "/mysite/main");
//			}
//			
//		}else {
//			System.out.println("잘못된 접근입니다.");
//		}
//	}
	
}
