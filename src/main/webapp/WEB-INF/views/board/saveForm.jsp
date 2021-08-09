<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<div class="container">
	<form>
		<div class="form-group">
			<label for="username">Title:</label> <input type="text"  class="form-control" placeholder="Enter Title" id="title">
		</div>
		<div class="form-group">
		  <label for="comment">Content:</label>
		  <textarea class="form-control summernote" rows="5"  id="content"></textarea>
		</div>
	</form>
	<button id="btn-save" class="btn btn-primary">글쓰기</button>
</div>
<script>
	$('.summernote').summernote({
	  placeholder: '',
	  tabsize: 2,
	  height: 300
	});
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>