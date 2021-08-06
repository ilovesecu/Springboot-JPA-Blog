/**
 * 로그인 및 회원가입 스크립트
 */

const join = {
	init: function() {
		const $form = document.querySelector("form");
		$form.addEventListener("submit", this.save);
	},
	save: (event) => {
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
				res.json().then(json => console.log(json));
			} else {
				console.error(res.statusText);
			}
		}).catch(err => console.error(err));


	}

}
join.init();