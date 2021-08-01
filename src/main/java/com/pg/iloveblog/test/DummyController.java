package com.pg.iloveblog.test;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pg.iloveblog.model.RoleType;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.UserRepository;

@RestController
public class DummyController {
	@Resource
	UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(@RequestBody User user) {
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입 완료";
	}
	
	@GetMapping("/dummy/user/{no}")
	public User detail(@PathVariable int no) {
		User user = userRepository.findById(no).orElseThrow(()->{
			return new IllegalArgumentException("해당 번호의 유저를 찾을 수 없습니다.");
		});
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> details(){
		return userRepository.findAll();
	}
	
	@GetMapping("/dummy/user") // http://localhost:9490/dummy/user?page=0
	public Page<User>pageList(@PageableDefault(size=2, sort="no", direction = Sort.Direction.DESC) Pageable page){
		Page<User> users = userRepository.findAll(page);
		return users;
	}
	
	@PutMapping("/dummy/user/{no}")
	@Transactional
	public User updateUser(@PathVariable int no, @RequestBody User reqUser) {
		//영속화 시키기
		User user = userRepository.findById(no).orElseThrow(()->{ 
			return new IllegalArgumentException("해당 번호의 사용자를 찾을 수 없습니당");
		});
		
		//영속화된 객체에 수정하기
		user.setPassword(reqUser.getPassword());
		user.setEmail(reqUser.getEmail());
		user.setName(reqUser.getName());
		return user;
	}
}
