<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ILoveBlog Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" crossorigin="anonymous">
<link href="/css/join.css" rel="stylesheet" />
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

.progress {
	height: 5px;
}

.block-help {
	font-weight: 300;
}

.hide {
	display: none;
}
</style>

</head>
<body class="text-center">
	<main class="form-signin">
		<img class="mb-4" src="/img/utility/bootstrap-logo.svg" alt="" width="72" height="57">
		<form class="row g-3" id="updateForm">
			<h1 class="h3 mb-3 fw-normal">회원수정</h1>

			<div class="form-floating">
				<input type="text" class="form-control" id="userId" name="userId" placeholder="UserID" value="${principal.user.id }" readonly> <label for="userId">아아디</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="Password"> <label for="userPassword">새로운 비밀번호</label>
			</div>
			<!-- password progresbar -->
			<div class="progress mt-1" id="reg-password-strength">
				<div id="password-strength" class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 0%"></div>
			</div>
			<!-- password remember & results -->
			<div class="help-block text-right">
				<span id="password-quality" class="hide block-help"> <small>Password <span id="password-quality-result"></span>
				</small>
				</span>
			</div>
			<!-- Password Rules -->
			<div id="password-rules" class="hide password-rule mt-2">
				<small>
					<ul class="list-unstyled">
						<li><span class="eight-character"><i class="fa fa-check-circle" aria-hidden="true"></i></span> &nbsp; 최소 8글자 이상</li>
						<li><span class="low-upper-case"><i class="fa fa-check-circle" aria-hidden="true"></i></span> &nbsp; 대문자 / 소문자 포함</li>
						<li><span class="one-number"><i class="fa fa-check-circle" aria-hidden="true"></i></span> &nbsp; 숫자/문자 포함</li>
						<li><span class="one-special-char"><i class="fa fa-check-circle" aria-hidden="true"></i></span> &nbsp; 특수문자 포함(!@#$%^&*)</li>
					</ul>
				</small>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="userPasswordConfirm" name="userPasswordConfirm" placeholder="비밀번호 확인"> <label for="userPassword">비밀번호 확인</label>
			</div>
			<!-- password-confirm error message -->
			<div class="help-block text-right">
				<small><span id="error-confirmpassword" class="hide pull-right block-help"> <i class="fa fa-info-circle text-danger" aria-hidden="true"></i> 비밀번호가 다릅니다.
				</span></small>
			</div>
			<div class="form-floating">
				<input type="text" class="form-control" id="username" name="username" placeholder="Username" value="${principal.user.name }"> <label for="email">이름</label>
			</div>
			<div class="form-floating">
				<input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${principal.user.email }"> <label for="email">이메일</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="oldPassword" name="oldPassword" placeholder="기존 비밀번호"> <label for="email">기존 비밀번호</label>
			</div>
			<button class="w-100 btn btn-lg btn-primary" type="submit">수정하기</button>
			<p class="mt-5 mb-3 text-muted">&copy; ILoveBlog</p>
		</form>
	</main>
	<script src="/js/user.js"></script>
	<script src="/js/passwordCheck.js"></script>
	<script>
	join.init();
	</script>
</body>
</html>