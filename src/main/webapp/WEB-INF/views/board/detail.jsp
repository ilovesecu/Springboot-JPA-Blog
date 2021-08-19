<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<style>
	#screenBlock{
		display:none;
		background-color: #e9e9e9;
		position: absolute;
		opacity: 0.7;
		width: 100%;
		height: 1900px;
	}
</style>
<!-- Page content-->
<div id="screenBlock"></div>
<div class="container mt-5">
	<div class="row">
		<div class="col-lg-12">
			<!-- Post content-->
			<article>
				<!-- Post header-->
				<header class="mb-4">
					<!-- Post title-->
					<h1 class="fw-bolder mb-1">${board.title }</h1>
					<!-- Post meta content-->
					<div class="text-muted fst-italic mb-2">Posted on ${board.createDate } by ${board.user.id }</div>
					<!-- Post categories-->
					<a class="badge bg-secondary text-decoration-none link-light" href="#!">Web Design</a> <a class="badge bg-secondary text-decoration-none link-light" href="#!">Freebies</a>
				</header>
				<!-- Preview image figure-->
				<!-- <figure class="mb-4"><img class="img-fluid rounded" src="https://dummyimage.com/900x400/ced4da/6c757d.jpg" alt="..." /></figure> -->
				<!-- Post content-->
				${board.content }
			</article>
			<div class="d-flex justify-content-end">
				<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
				<c:if test="${principal.user.id == board.user.id }">
					<a href="/board/${board.no}/updateForm" class="btn btn-warning">수정</a>
					<button data-bs-toggle="modal" data-bs-target="#deleteModal" class="btn btn-danger">삭제</button>
				</c:if>
			</div>
			<!-- Comments section-->
			<section class="mb-5 mt-2">
				<div class="card bg-light">
					<div class="card-body">
						<!-- Comment form-->
						<form class="mb-4">
							<input type="hidden" id="boardNo" value="${board.no}" />
							<div class="input-group">
							  <textarea class="form-control" rows="3" id="replyContent" 
							  	placeholder="댓글을 남겨보세요!"></textarea>
							  <button id="btnReplySave" class="btn btn-primary">댓글등록</button>
							</div>
						</form>
						<!-- Single comment-->
						<c:forEach var="reply" items="${board.replys }">
							<div class="d-flex">
								<div class="flex-shrink-0">
									<img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." />
								</div>
								<div class="ms-3">
									<span class="fw-bold">${reply.user.id } </span>
									<span class="badge" style="color:black;">${reply.createDate }</span> 
									<div>
										${reply.content }
									</div>
									<div>
										<button class="btn">답글달기</button>
										<button class="btn">삭제</button>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</section>
		</div>
	</div>
</div>

<div class="modal fade" tabindex="-1" id="deleteModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">삭제 확인</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>게시글을 정말 삭제하시겠습니까?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
        <button type="button" id="btn-delete" class="btn btn-danger" data-board-no="${board.no }">삭제</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" tabindex="-1" id="deleteCompleteModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deleteCompleteModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">삭제 완료</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <p>게시글 삭제를 완료하였습니다. 메인화면으로 이동합니다.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="goMainBtn" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

<<script type="text/javascript" src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>