package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	public String restore(MultipartFile file) {
		//파일 정보 수집
		//저장 폴더
		String saveDir = "D:\\javaStudy\\upload";
		
		//원파일 이름
		String fileOriginalName= file.getOriginalFilename();
		System.out.println(fileOriginalName);
		
		//확장자
		String fileExName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		System.out.println(fileExName);
		
		//저장파일 이름
		String fileSaveName = System.currentTimeMillis() + UUID.randomUUID().toString() + fileExName;
		System.out.println(fileSaveName);
		
		//파일 위치(패스)
		String filePath = saveDir + "\\" + fileSaveName;
		System.out.println(filePath);
		
		//파일사이즈
		long fileSize = file.getSize();
		System.out.println(fileSize);
		
		//파일 카피
		try {
			byte[] fileData = file.getBytes();
			OutputStream out = new FileOutputStream(saveDir + "/" + fileSaveName); //저장위치
			BufferedOutputStream bout = new BufferedOutputStream(out);
			
			bout.write(fileData);
			
			if(bout != null) {
				bout.close();
			}
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return fileSaveName;
	}
}
