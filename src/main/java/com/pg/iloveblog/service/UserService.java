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
	
	
	
}
