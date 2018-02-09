package com.javaex.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileUploadService;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {
	
	@Autowired
	FileUploadService fuService;

	@RequestMapping("/form")
	public String form() {
		System.out.println("form 진입");
		
		return "fileupload/form";
	}
	
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file,Model model) {
		System.out.println("upload 진입");
		String fileSaveName = fuService.restore(file);
		
		String url = "upload/" + fileSaveName;
		
		model.addAttribute("url", url);
		
		return "fileupload/result";
	}
}
