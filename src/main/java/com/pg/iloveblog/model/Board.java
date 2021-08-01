package com.pg.iloveblog.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	
	@Column(nullable = false, length = 50)
	private String title; //제목
	
	@Lob //대용량 데이터
	private String content; //섬머노트를 사용할 예정이기에 매우 길수도있음.
	
	@ColumnDefault("0") //문자였으면 작은 따옴표 필요
	private int count; //조회수
	
	@JoinColumn(name="userId", nullable = false) //FK로 userId를 생성해주세요
	@ManyToOne //Many:board, One:User
	private User user; //누가 이 글을 적었는지
	
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)  //mappedBy = FK만들지 마세요!
	private List<Reply> reply;
	
	@CreationTimestamp //insert될때 현재 시간이 자동으로 들어가도록 설정
	private Timestamp createDate;
	
	@LastModifiedDate
	private LocalDateTime updateDate;
}
