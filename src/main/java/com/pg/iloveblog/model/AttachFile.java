package com.pg.iloveblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachFile {
	private String fileName; //원본파일 이름
	private String uploadPath; //업로드 경로
	private String uuid; //uuid
	private boolean image; //이미지인지 여부
}
