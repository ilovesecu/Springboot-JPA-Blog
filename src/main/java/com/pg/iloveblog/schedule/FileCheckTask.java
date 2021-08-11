package com.pg.iloveblog.schedule;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.quartz.InterruptableJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pg.iloveblog.model.AttachFile;
import com.pg.iloveblog.repository.AttachFileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component //DB에 저장된 파일목록과 실제 파일시스템의 파일목록을 비교하여 DB에 존재하지 않는 것을 삭제한다.
public class FileCheckTask {
	//1. DB에서 어제 사용된 파일의 목록을 얻어온다.
	//2. 해당 폴더의 파일 목록에서 DB에 없는 파일을 찾아낸다.
	//3. DB에 없는 파일들을 삭제한다.
	@Resource
	private AttachFileRepository attachFileRepository;
	
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1); // 1일전
		String str = sdf.format(cal.getTime());
		return str.replace("-", File.separator); 
	}
	
	@Scheduled(cron = "0 0 2 * * *") //새벽2시
	@Transactional
	public void checkFiles() {
		log.warn("File Check Task run..................");
		log.warn(new Date().toString());
		//file list in db
		List<AttachFile> files = attachFileRepository.findOldFiles();
		
		//ready for check file in directory with database file list
		List<Path> filePaths = files.stream().map(vo -> Paths.get("C:\\springboot\\upload",vo.getUploadPath(),
				vo.getUuid()+"_"+vo.getFileName())).collect(Collectors.toList());
		log.warn("=============================================================");
		filePaths.forEach(p -> log.warn(p.toString()));
		
		//files in yesterday directory
		File targetDir = Paths.get("C:\\springboot\\upload",getFolderYesterDay()).toFile();
		File[] removeFiles = targetDir.listFiles(file -> filePaths.contains(file.toPath()) == false);
		
		log.warn("---------------------------------------------------------------");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
	}
}
