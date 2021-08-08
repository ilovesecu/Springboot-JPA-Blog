/**
 * 로그인 및 회원가입 스크립트
 */

const join = {
	init: function() {
		const $joinForm = document.querySelector("#joinForm");
		$joinForm.addEventListener("submit", this.joinProc);
	},
	joinProc: (event) => {
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

		//비밀번호 체크 (근데 이미 회원가입 버튼을 disabled 시키니까 사용되지는 않을것.)
		if (userPassword !== userPasswordConfirm) {
			const $error_confirmpassword = document.querySelector("#error-confirmpassword"); //패스워드 다르다는 표시
			$userPasswordConfirm.classList.add(".is-invalid"); //빨간색 테두리 표시
			$error_confirmpassword.classList.remove("hide"); //패스워드 다르다는 표시
			$userPasswordConfirm.focus();
		}
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
						alert('회원가입에 성공하였습니다.');
						location.href="/";
					}
				});
			} else {
				console.error(res.statusText);
			}
		}).catch(err => console.error(err));
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