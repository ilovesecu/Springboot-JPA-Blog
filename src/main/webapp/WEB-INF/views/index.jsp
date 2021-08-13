<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>

<!-- Main Content-->
<div class="container px-4 px-lg-5">
	<div class="row gx-4 gx-lg-5 justify-content-center">
		<div class="col-md-10 col-lg-8 col-xl-7">
			<!-- Post preview-->
			<c:forEach var="board" items="${boards.content}">
				<div class="post-preview">
					<a href="/board/${board.no}">
						<h2 class="post-title">${board.title }</h2>
						<h3 class="post-subtitle">Problems look mighty small from 150 miles up</h3>
					</a>
					<p class="post-meta">
						Posted by <a href="#!">${board.user.id }</a> on ${board.dateDisplayed}
					</p>
				</div>
				<!-- Divider-->
				<hr class="my-4" />
			</c:forEach>
			<!-- Pager-->
			<nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center">
			  	<c:choose>
			  		<c:when test="${isPrev==false}">
					    <li class="page-item disabled">
					      <a class="page-link" href="?page=${startPage-2}" aria-disabled="true">Previous</a>
					    </li>
				    </c:when>
				    <c:otherwise>
				    	<li class="page-item">
					      <a class="page-link" href="?page=${startPage-2}" aria-disabled="true">Previous</a>
					    </li>
				    </c:otherwise>
			    </c:choose>
			    <c:forEach var="page" begin="${startPage}" end="${endPage }">
			    	<c:choose>
			    		<c:when test="${boards.pageable.pageNumber+1 == page}">
				    		<li class="page-item active"><a class="page-link" href="?page=${page-1}">${page }</a></li>
			    		</c:when>
			    		<c:otherwise>
			    			<li class="page-item"><a class="page-link" href="?page=${page-1}">${page }</a></li>
			    		</c:otherwise>
				    </c:choose>
			    </c:forEach>
			    <c:choose>
			   	 	<c:when test="${isNext==false}">
					    <li class="page-item disabled">
					      <a class="page-link" href="?page=${endPage}">Next</a>
					    </li>
				    </c:when>
				    <c:otherwise>
				    	<li class="page-item">
					      <a class="page-link" href="?page=${endPage}">Next</a>
					    </li>
				    </c:otherwise>
			    </c:choose>
			  </ul>
			</nav>	
			
			<!-- <div class="d-flex justify-content-end mb-4">
				<a class="btn btn-primary text-uppercase" href="#!">Older Posts →</a>
			</div> -->
			<!-- Post preview-->
			<!-- 
			<div class="post-preview">
				<a href="post.html">
					<h2 class="post-title">Man must explore, and this is exploration at its greatest</h2>
					<h3 class="post-subtitle">Problems look mighty small from 150 miles up</h3>
				</a>
				<p class="post-meta">
					Posted by <a href="#!">Start Bootstrap</a> on September 24, 2021
				</p>
			</div>
			<hr class="my-4" />
			
			<div class="post-preview">
				<a href="post.html"><h2 class="post-title">I believe every human has a finite number of heartbeats. I don't intend to waste any of mine.</h2></a>
				<p class="post-meta">
					Posted by <a href="#!">Start Bootstrap</a> on September 18, 2021
				</p>
			</div>
			<hr class="my-4" />

			<div class="post-preview">
				<a href="post.html">
					<h2 class="post-title">Science has not yet mastered prophecy</h2>
					<h3 class="post-subtitle">We predict too much for the next year and yet far too little for the next ten.</h3>
				</a>
				<p class="post-meta">
					Posted by <a href="#!">Start Bootstrap</a> on August 24, 2021
				</p>
			</div>
			<hr class="my-4" />
			
			<div class="post-preview">
				<a href="post.html">
					<h2 class="post-title">Failure is not an option</h2>
					<h3 class="post-subtitle">Many say exploration is part of our destiny, but it’s actually our duty to future generations.</h3>
				</a>
				<p class="post-meta">
					Posted by <a href="#!">Start Bootstrap</a> on July 8, 2021
				</p>
			</div>
			<hr class="my-4" />
			<div class="d-flex justify-content-end mb-4">
				<a class="btn btn-primary text-uppercase" href="#!">Older Posts →</a>
			</div>
			-->
		</div>
	</div>
</div>
<%@ include file="layout/footer.jsp"%>