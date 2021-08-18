/**
 * 로그인 및 회원가입 스크립트
 */

const join = {
	init: function() {
		const $joinForm = document.querySelector("#joinForm");
		$joinForm.addEventListener("submit", (event)=>{this.joinProc(this,event)});
	},
	joinProc: (that,event) => {
		event.preventDefault();
		const $userId = document.querySelector("#userId");
		const $userPassword = document.querySelector("#userPassword");
		const $userPasswordConfirm = document.querySelector("#userPasswordConfirm");
		const $username = document.querySelector("#username");
		const $email = document.querySelector("#email");

		const userId = $userId.value;
		const userPassword = $userPassword.value;
		const userPasswordConfirm = $userPasswordConfirm.value;
		const username = $username.value;
		const email = $email.value;
		
		const exceptionCheckResult = that.exceptionCheck(); //예외처리 함수
		if(!exceptionCheckResult) return ; 
		
		//HTTP BODY
		const body = {
			id: userId,
			password: userPassword, 
			name:username,
			email:email
		};
		
		//HTTP HEADER
		const headers = new Headers();
		headers.append('Content-type','application/json; charset=utf-8');
		
		//Request To Server
		fetch('/auth/joinProc', {
			method: 'POST',
			body:JSON.stringify(body),
			headers: headers, // 이 부분은 따로 설정하고싶은 header가 있다면 넣으세요
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
				res.json().then(json => {
					if(json.status===200){
						if(json.response === "fail"){
							console.log(json);
							alert('회원가입 실패');	
							return ;
						}
						alert('회원가입에 성공하였습니다.');
						location.href="/";
					}
				});
			} else {
				console.error(res.statusText);
			}
		}).catch(err => console.error(err));
	},
	exceptionCheck:()=>{ //회원가입할 때 아이디, 비밀번호,이름,이메일이 안써져있지는 않은지 체크
		const $userId = document.querySelector("#userId");
		const $userPassword = document.querySelector("#userPassword");
		const $userPasswordConfirm = document.querySelector("#userPasswordConfirm");
		const $username = document.querySelector("#username");
		const $email = document.querySelector("#email");
		
		if($userId.value == "" || $userId.value==null){
			alert('아이디를 입력해주세요.');
			$userId.focus();
			return false;
		}
		if($userPassword.value == "" || $userPassword.value == null){
			alert('비밀번호를 입력해주세요.');
			$userPassword.focus();
			return false;
		}
		if($username.value == "" || $username.value == null){
			alert('이름을 입력해주세요.');
			$username.focus();
			return false;
		}
		if($email.value == "" || $email.value == null){
			alert('이메일을 입력해주세요.');
			$email.focus();
			return false;
		}
		/*
		//비밀번호 체크 (근데 이미 회원가입 버튼을 disabled 시키니까 사용되지는 않을것.)
		if ($userPassword.value !== $userPasswordConfirm.value) {
			const $error_confirmpassword = document.querySelector("#error-confirmpassword"); //패스워드 다르다는 표시
			$userPasswordConfirm.classList.add(".is-invalid"); //빨간색 테두리 표시
			$error_confirmpassword.classList.remove("hide"); //패스워드 다르다는 표시
			$userPasswordConfirm.focus();
		}*/
	}
	
}

const update = {
	init : function(){
		const $updateForm = document.querySelector("#updateForm");
		$updateForm.addEventListener("submit",(event)=>this.updateProc(this,event));
	},
	updateProc:(that,event)=>{
		event.preventDefault();
		const exceptionCheckResult = that.exceptionCheck();
	},
	exceptionCheck:()=>{
		
	}
}

const login = {
	init: function(){
		const $loginForm = document.querySelector("#loginForm");
		const $userId = document.querySelector("#userId");
		const $userPassword = document.querySelector("#userPassword");
		$loginForm.addEventListener("submit",this.loginProc);
		$userId.addEventListener("blur",this.blurHandler);
		$userPassword.addEventListener("blur",this.blurHandler);
	},
	loginProc: (event)=>{
		event.preventDefault();
		const $userId = document.querySelector("#userId");
		const $userPassword = document.querySelector("#userPassword");
		const userId = $userId.value;
		const userPassword = $userPassword.value;
		if(userId === null || userId === ""){
			alert('아이디를 입력해주세요.');
			$userId.classList.add('is-invalid');
			$userId.focus();
			return ;
		}
		if(userPassword === null || userPassword === ""){
			alert('비밀번호를 입력해주세요.');
			$userPassword.classList.add('is-invalid');
			$userPassword.focus();
			return ;
		}
		event.target.submit(); //시큐리티때문에 폼 로그인 사용할 예정.
	},
	blurHandler: (event)=>{ //제대로 입력하고 포커스 아웃이 되었을 때는 빨간색 테두리 제거
		const target = event.target;
		if(target.value !== null && target.value !== ""){
			target.classList.remove('is-invalid');
		}
		
	}
}