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
	const form = document.querySelector("form");
	const attachList = []; //첨부파일 이미지 리스트
	$('.summernote').summernote({
		height: 600,
		fontNames : [ '맑은고딕', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', ],
		fontNamesIgnoreCheck : [ '맑은고딕' ],
		focus: true,
		placeholder: '내용을 입력해주세요',
		callbacks: {
			onImageUpload: function(files, editor, welEditable) {
	            for (let i = files.length - 1; i >= 0; i--) {
	            	sendFile(files[i], this);
	            }
	        },
	        onPaste: function (e) {
				var clipboardData = e.originalEvent.clipboardData;
				if (clipboardData && clipboardData.items && clipboardData.items.length) {
					var item = clipboardData.items[0];
					if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
						e.preventDefault();
					}
				}
			}
		}
	});
	
	const sendFile = (file, el)=>{
		const formData = new FormData();
		formData.append('file',file);
		
		fetch('/api/file', {
		  method: 'POST', // POST 메소드 지정
		  body: formData, // formData를 데이터로 설정
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
		    	res.json().then(json => {
		    		console.log(json);
		    		const data = json.data.response;
		    		const uploadPath = data.uploadPath;
		    		const uuid = data.uuid;
		    		const fileName = data.fileName;
		    		//console.log(uploadPath+"/"+uuid+"_"+fileName);
		    		const filePath = "/summernoteImage/"+uploadPath+"/"+uuid+"_"+fileName;
		    		$(el).summernote('insertImage', filePath);
		    		attachList.push(data);
		    	});
		    	
		  	} else {
		    	console.error(res.statusText);
		  	}
		}).catch(err => console.error(err));
	}
</script>
<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>