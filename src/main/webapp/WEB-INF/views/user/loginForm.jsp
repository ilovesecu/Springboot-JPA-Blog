<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ILoveBlog Login</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link href="/css/login.css" rel="stylesheet"/>
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
</style>

</head>
<body class="text-center">
	<main class="form-signin">
		<form id="loginForm" method="POST" action="/auth/loginProc">
			<img class="mb-4" src="/img/utility/bootstrap-logo.svg" alt="" width="72" height="57">
			<h1 class="h3 mb-3 fw-normal">로그인</h1>
			<c:if test="${null ne requestScope.error}">
				<div class="alert alert-danger" role="alert">
				  ${requestScope.msg }
				</div>
			</c:if>
			<div class="form-floating">
				<input type="text" class="form-control" id="userId" name="userId" placeholder="userID"> <label for="userId">아이디</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="Password"> <label for="userPassword">비밀번호</label>
			</div>

			<div class="checkbox mb-3">
				<label> <input type="checkbox" value="remember-me"> Remember me
				</label>
			</div>
			<button class="w-100 btn btn-lg btn-primary" type="submit">로그인</button>
			<a href="/auth/joinForm" class="d-block mt-2">아직 회원이 아니신가요?</a>
			<p class="mt-5 mb-3 text-muted">&copy; ILoveBlog</p>
		</form>
	</main>
	<script type="text/javascript" src="/js/user.js"></script>
	<script>
		login.init();
	</script>
</body>
</html>