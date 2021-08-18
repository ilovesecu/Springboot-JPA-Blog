package com.pg.iloveblog.service;

import javax.annotation.Resource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.UserRepository;

@Service
public class UserService {
	@Resource
	private UserRepository userRepository;
	@Resource
	private BCryptPasswordEncoder encoder;
	
	//회원가입 기능 자체가 하나의 트랜잭션으로 묶이게 된다. 그래서 하나라도 실패하면 rollback을 한다.
	@Transactional
	public int 회원가입(User user) {
		try {
			String rawPassword = user.getPassword(); //암호화되지 않은 비밀번호
			String encodePassword = encoder.encode(rawPassword);
			user.setPassword(encodePassword);
			userRepository.save(user);
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	//회원 수정
	@Transactional
	public int 회원수정(User reqUser) {
		//수정 시에는 JPA 영속성 컨텍스트 User 객체 영속화시키고, 영속화된 User객체를 수정하는 것이 제일 좋다.
		//영속화를 하기 위해서 DB로 부터 select를 한다. 
		//그리고 영속화된 User객체를 수정하고 서비스가 종료되면 트랜잭션이 종료되면서 더티체크에 위해서 DB에 변경점이 Flush된다.
		User user = userRepository.findById(reqUser.getNo()).orElseThrow(()->{
			return new IllegalArgumentException("해당 no의 회원을 찾을 수 없습니다. no:"+reqUser.getNo());
		});
		System.out.println("password:"+user.getPassword());
		System.out.println("oldPassword:"+encoder.encode(reqUser.getOldPassword()));
		/*
		if(reqUser.getPassword()!=null || !reqUser.getPassword().equals("")) {
			String rawPassword = reqUser.getPassword();
			String encPassword = encoder.encode(rawPassword);
			user.setPassword(encPassword);
		}
		user.setEmail(reqUser.getEmail());
		user.setName(reqUser.getName());
		*/
		return 1;
	}
	
	
}
