/**
 * 회원가입 시 비밀번호 체크
 */
const passwordCheck = {
	init: function() {
		const $submitBtn = document.querySelector("form button[type=submit]");
		document.addEventListener("DOMContentLoaded", function() {
			const $userPW = document.querySelector("#userPassword");
			const $userPWConfirm = document.querySelector("#userPasswordConfirm");
			//const $passwordToggleBtn = document.querySelector("#button-append1");
			//const $passwordConfirmToggleBtn = document.querySelector("#button-append2");

			$userPW.addEventListener("keyup", passwordEventHandler);
			$userPW.addEventListener("focusout", passwordFocusOutHandler);
			$userPWConfirm.addEventListener("focusout", passwordFocusOutHandler);
			//$passwordToggleBtn.addEventListener("click",togglePassword);
			//$passwordConfirmToggleBtn.addEventListener("click",togglePassword);
		});
		const passwordEventHandler = (e) => {
			const target = e.target;
			//패스워드 강도 체크
			if (checkPwStrength(target.value) === false) {
				$submitBtn.setAttribute("disabled", "disabled");
			}
			//패스워드에 아무것도 없다면 강도체크 하는 부분 모두 없애기 (이것이  강도체크 앞부분에 있으면 비밀번호에 아무 것도 없을 때 강도 텍스트가 숨겨지지 않는다.)
			if (target.value) {
				document.querySelector("#password-rules").classList.remove("hide");
				document.querySelector("#password-strength").classList.remove("hide"); //프로그래스바
			} else {
				document.querySelector("#password-quality-result").classList.add("hide"); //강도 텍스트
				document.querySelector("#password-quality").classList.add('hide');
				document.querySelector("#password-rules").classList.add("hide");
				document.querySelector("#password-strength").classList.add("hide"); //프로그래스바
			}
		}
		const passwordFocusOutHandler = (e) => {
			const $userPWConfirm = document.querySelector("#userPasswordConfirm");
			//const target = e.currentTarget;
			const target = $userPWConfirm;
			const $userPW = document.querySelector("#userPassword");
			const $error_confirmpassword = document.querySelector("#error-confirmpassword");
			if (target.value === $userPW.value) { //비밀번호 === 비밀번호 확인
				$error_confirmpassword.classList.add("hide"); //비밀번호가 다르다는 메시지 숨기기
				$submitBtn.removeAttribute("disabled"); //회원가입 버튼 활성화
				target.classList.remove("is-invalid"); //빨간색 테두리 제거
			} else {
				target.classList.add("is-invalid"); //빨간색 테두리 표시
				$error_confirmpassword.classList.remove("hide");
				$submitBtn.setAttribute("disabled", "disabled");
			}
		}
		//비밀번호 강도 체크
		const checkPwStrength = (password) => {
			let strength = 0;
			const $userPW = document.querySelector("#userPassword");
			//비밀번호에 소문자/대문자가 모두 포함된 경우 비밀번호 강도 Up
			if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
				strength++;
				document.querySelector(".low-upper-case").classList.add('text-success');
				document.querySelector(".low-upper-case i").classList.remove('fa-check');
				document.querySelector(".low-upper-case i").classList.add('fa-check');
				document.querySelector("#password-quality").classList.add('hide');
			} else {
				document.querySelector(".low-upper-case").classList.remove('text-success');
				document.querySelector(".low-upper-case i").classList.add('fa-check');
				document.querySelector(".low-upper-case i").classList.remove('fa-check');
				document.querySelector("#password-quality").classList.add('hide');
			}
			//숫자와 문자가 있으면 강도up
			if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) {
				strength++;
				document.querySelector(".one-number").classList.add('text-success');
				document.querySelector(".one-number i").classList.remove('fa-check');
				document.querySelector(".one-number i").classList.add('fa-check');
				document.querySelector("#password-quality").classList.add('hide');
			} else {
				document.querySelector(".one-number").classList.remove('text-success');
				document.querySelector(".one-number i").classList.add('fa-check');
				document.querySelector(".one-number i").classList.remove('fa-check');
				document.querySelector("#password-quality").classList.add('hide');
			}
			//특수문자가 있으면 강도up
			if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) {
				strength++;
				document.querySelector(".one-special-char").classList.add('text-success');
				document.querySelector(".one-special-char i").classList.remove('fa-check');
				document.querySelector(".one-special-char i").classList.add('fa-check');
				document.querySelector("#password-quality").classList.add('hide');
			} else {
				document.querySelector(".one-special-char").classList.remove('text-success');
				document.querySelector(".one-special-char i").classList.add('fa-check');
				document.querySelector(".one-special-char i").classList.remove('fa-check');
				document.querySelector("#password-quality").classList.add('hide');
			}
			//8글자 이상이면 강도up
			if (password.length > 7) {
				strength++;
				document.querySelector(".eight-character").classList.add('text-success');
				document.querySelector(".eight-character i").classList.remove('fa-check');
				document.querySelector(".eight-character i").classList.add('fa-check');
				document.querySelector("#password-quality").classList.remove('hide');
			} else {
				document.querySelector(".eight-character").classList.remove('text-success');
				document.querySelector(".eight-character i").classList.add('fa-check');
				document.querySelector(".eight-character i").classList.remove('fa-check');
				document.querySelector("#password-quality").classList.remove('hide');
			}
			//비밀번호 강도에 따른 result 표시
			const $passwordQualityResult = document.querySelector("#password-quality-result"); //강도 텍스트
			const $passwordStrength = document.querySelector("#password-strength"); //프로그래스 바
			if (strength < 2) {
				$userPW.classList.add("is-invalid"); //빨간 테두리 표시
				$passwordStrength.classList.add('progress-bar-danger');
				$passwordStrength.style.width = "10%";
				$passwordQualityResult.className = ""; //클래스 초기화
				$passwordQualityResult.classList.add("text-danger");
				$passwordQualityResult.innerText = "매우 약함";
			} else if (strength == 2) {
				$userPW.classList.add("is-invalid"); //빨간 테두리 표시
				$passwordStrength.classList.remove('progress-bar-danger');
				$passwordStrength.classList.add('progress-bar-warning');
				$passwordStrength.style.width = "60%";

				$passwordQualityResult.classList.add('good');
				$passwordQualityResult.classList.add('text-warning');
				$passwordQualityResult.innerText = "약함";
			} else if (strength == 4) {
				$userPW.classList.remove("is-invalid"); //빨간 테두리 제거
				$passwordStrength.classList.remove('progress-bar-warning');
				$passwordStrength.classList.add('progress-bar-success');
				$passwordStrength.style.width = "100%";

				$passwordQualityResult.className = ""; //클래스 초기화
				$passwordQualityResult.classList.add('strong');
				$passwordQualityResult.classList.add('text-success');
				$passwordQualityResult.innerText = "강함";
				return true;
			}
			return false;
		}
		//비밀번호 눈동자 누르면 text ↔ password 토글
		const togglePassword = (e) => {
			let toggle = null;
			if (e.currentTarget.id == "button-append1") {
				toggle = document.querySelector("#userPassword");
			} else {
				toggle = document.querySelector("#userPasswordConfirm");
			}
			toggle.type = (toggle.type === 'password' ? 'text' : 'password');
		}
	}
}
passwordCheck.init();
