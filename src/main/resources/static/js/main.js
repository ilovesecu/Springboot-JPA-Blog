/**
 * 로그인 및 회원가입 스크립트
 */

const main = {
	init: function() {
		const $body = document.querySelector("body");
		const $login = document.querySelector(".login");
		const $login_card = document.querySelector(".login--card");
		$login.addEventListener("click", ()=>{
			this.loginClickHandler($login_card);
		});
		$body.addEventListener("click", this.bodyClickHandler);
	},
	loginClickHandler: ($login_card)=>{
		$login_card.classList.toggle('show');
		event.stopPropagation();
	},
	bodyClickHandler:()=>{
		document.querySelectorAll(".show").forEach((v,i)=>{
			v.classList.remove("show");
		});
	}
}
main.init();