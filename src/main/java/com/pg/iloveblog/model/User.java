package com.pg.iloveblog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity //해당 모델클래스를 Table로 변환
public class User {
	@Id //Primary key
	//▼프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. (오라클DB를 사용하면 시퀀스가 된다는거고, MySQL을 사용하면 AUTO_INCREMENT를 사용)
	@GeneratedValue(strategy = GenerationType.IDENTITY) //yml파일 use-new-id-generator-mappings: false
	private int no;
	
	@Column(nullable = false, length = 40, unique = true)
	private String id;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	private String oauth; //kakao, google 등으로 로그인한 사용자 (일반 사용자는 null)
	
	@Enumerated(EnumType.STRING) //DB에는 RoleType 이란것이 없으니까 해당 타입이 String 이란 것을 알려줘야한다.
	private RoleType role; //나중에는 Enum을 사용해야한다. (Enum을 쓰면 도메인을 만들 수 있어서.. 도메인 : 범위(성별:남,여)
	
	@CreationTimestamp //insert될 때의 시간이 자동으로 입력된다.
	private Timestamp createDate; //가입날짜
	
	@Transient //컬럼아니에요!
	private String oldPassword;
}
