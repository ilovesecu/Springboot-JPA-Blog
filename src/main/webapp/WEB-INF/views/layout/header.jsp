<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>I Love Blog</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link href="/css/headers.css" rel="stylesheet" />
<link href="/css/index.css" rel="stylesheet" />
<link href="/css/font-awesome/all.min.css" rel="stylesheet"/>
</head>
<body>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="principal"/>
	</sec:authorize>
	
	<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
	  <symbol id="bootstrap" viewBox="0 0 118 94">
	    <title>Bootstrap</title>
	    <path fill-rule="evenodd" clip-rule="evenodd"
			d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z"></path>
	  </symbol>
	  <symbol id="people-circle" viewBox="0 0 16 16">
	    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
	    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
	  </symbol>
	</svg>
	<header>
		<div class="px-3 py-2 bg-dark text-white">
			<div class="container">
				<div class="d-flex justify-content-between">
					<a href="/" class="d-flex align-items-center my-2 my-lg-0 text-white text-decoration-none"> <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
								<use xlink:href="#bootstrap" /></svg>
					</a>
					<form class="d-flex align-items-center col-6 my-lg-0">
						<input type="search" class="form-control" placeholder="Search..." aria-label="Search">
					</form>
					<ul class="nav col-12 col-lg-auto my-2 justify-content-center my-md-0 text-small">
						<c:choose>
						<c:when test="${not empty principal }">
							<li class="menu-item d-flex flex-column">
								<a href="/board/saveForm" class="nav-link text-white">
									<i class="fas fa-feather-alt"></i>
								</a>
								<span>Write</span>
							</li>
						</c:when>
						</c:choose>
						<li class="login">
							<a href="#" class="nav-link text-white"> <svg class="bi d-block mx-auto mb-1" width="24" height="24">
								<use xlink:href="#people-circle" /></svg> Guest
							</a>
							<div class="card login--card" style="width: 19rem;">
								<div class="card-body">
									<c:choose>
										<c:when test="${empty principal }">
											<h5 class="card-title">아직 로그인하지 않으셨군요?</h5>
											<p class="card-text">현재 게스트 모드입니다. 특정 기능에 제한이 있을 수 있습니다.</p>
											<a href="/auth/loginForm" class="btn btn-success">로그인</a>
											<a href="/auth/joinForm" class="btn btn-primary">회원가입</a>
										</c:when>
										<c:otherwise>
											<h5 class="card-title">${principal.user.name }님 환영합니다!</h5>
											<p class="card-text">안녕하세요?</p>
											<a href="/logout" class="btn btn-success">로그아웃</a>
											<a href="/user/updateForm" class="btn btn-primary">회원정보</a>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</header>

	