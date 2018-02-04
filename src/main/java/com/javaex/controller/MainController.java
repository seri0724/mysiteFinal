package com.javaex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		System.out.println("main 진입");
		
		System.out.println("페이지를 요청한 ip 주소 : " + request.getLocalAddr());
		System.out.println("접근 포트 : " + request.getLocalPort());
		System.out.println("ip 주소 지역 위치 : " + request.getLocale());
		
		return "main/index";
	}
	
}