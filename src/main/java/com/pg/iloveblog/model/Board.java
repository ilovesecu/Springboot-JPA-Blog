package com.pg.iloveblog.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pg.iloveblog.model.converter.BooleanToYNConverter;

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
	
	@JsonIgnoreProperties({"board"})
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)  //mappedBy = FK만들지 마세요!
	@OrderBy("no desc")
	private List<Reply> replys;
	
	@JsonIgnoreProperties({"board"})
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Convert(converter = BooleanToYNConverter.class)
	private List<AttachFile> attachFiles;
	
	@CreationTimestamp //insert될때 현재 시간이 자동으로 들어가도록 설정
	private Timestamp createDate;
	//private Date createDate;
	
	@UpdateTimestamp //update될때 현재 시간이 자동으로 들어가도록 설정
	private Timestamp updateDate;
	//private Date updateDate;
	
	@Transient //테이블에 적용하지 마세요!
	private String dateDisplayed;  //표시되는 날짜(yyyy-MM-dd or HH:mm:ss)
	@Transient //테이블에 적용하지 마세요!
	private List<AttachFile> deleteFiles;  //삭제될 파일
}
