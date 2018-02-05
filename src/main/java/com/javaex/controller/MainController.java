package com.javaex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	@RequestMapping("/main")
	public String main(HttpServletRequest request) {
		System.out.println("main 진입");
		
		System.out.println("페이지를 요청한 ip 주소 (Host) : " + request.getRemoteHost());
		System.out.println("페이지를 요청한 ip 주소 (Addr) : " + request.getRemoteAddr());
		System.out.println("LocalPort : " + request.getLocalPort());
		System.out.println("LocalName : " + request.getLocalName());
		System.out.println("Protocol : " + request.getProtocol());
		System.out.println("Scheme : " + request.getScheme());
		System.out.println("ContextPath : " + request.getContextPath());
		System.out.println("URI : " + request.getRequestURI());
		
		return "main/index";
	}
	
}
