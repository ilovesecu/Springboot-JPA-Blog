package com.pg.iloveblog.schedule;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.quartz.InterruptableJob;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	@Scheduled(cron = "0 * * * * *")
	public void checkFiles() {
		log.error("-------------------");
	}
}
