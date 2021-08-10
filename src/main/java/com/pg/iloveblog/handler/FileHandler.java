package com.pg.iloveblog.handler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.pg.iloveblog.model.AttachFile;

@Component //파일 업로드 핸들러
public class FileHandler {
	public AttachFile parseFileInfo(MultipartFile multipartFile) {
		//서머노트로 업로드한 이미지를 웹루트에 업로드해버리면 하면 빌드하고 재배포시 이미지가 다 사라지니 외부 경로에 잡아준다.
		String uploadFolder = "C:\\springboot\\upload";
		String uploadFolderPath = getFolder();
		//make folder
		File uploadPath = new File(uploadFolder, uploadFolderPath); //uploadFolder + yyyy\\MM\\dd
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		String uploadFileName = multipartFile.getOriginalFilename();
		//IE has file path
		uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
		
		UUID uuid = UUID.randomUUID(); //중복 방지를 위한 uuid
		uploadFileName = uuid.toString()+"_"+uploadFileName;
		try {
			File saveFile = new File(uploadPath, uploadFileName);
			multipartFile.transferTo(saveFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new AttachFile(multipartFile.getOriginalFilename(), uploadFolderPath,uuid.toString(), true);
	}
	
	//날짜 형식의 폴더 경로를 반환하는 메소드.
	private String getFolder() {
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sfd.format(date); //2021-08-10 형식
		return str.replace("-", File.separator); // -는 파일 구분자로 변경하자.
	}
}
