package com.pg.iloveblog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int no;
	
	@Column(nullable = false, length = 200)
	private String content; //댓글 내용
	
	@ManyToOne
	@JoinColumn(name="userId", nullable = false)
	User user; //어떤 사용자가 작성하였는가?
	
	@ManyToOne
	@JoinColumn(name="boardId")
	Board board; //어떤 글의 댓글인가?
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@UpdateTimestamp
	private Timestamp updateDate;
	
	@Transient //테이블에 적용하지 마세요!
	private String dateDisplayed;  //표시되는 날짜(yyyy-MM-dd or HH:mm:ss)
	
}
