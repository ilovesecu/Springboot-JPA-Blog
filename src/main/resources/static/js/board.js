let board = {
	init: function(){
		const btnSave=document.querySelector("#btn-save");
		const btnDelete = document.querySelector("#btn-delete");
		const deleteModal = document.querySelector("#deleteModal"); 
		if(btnSave!=null)
			btnSave.addEventListener("click",this.boardSave);
		if(btnDelete!=null)
			btnDelete.addEventListener("click",this.boardDeleteById);
		if(deleteModal!=null){
			deleteModal.addEventListener("show.bs.modal",this.shownBsModalHandler);
			deleteModal.addEventListener("hide.bs.modal",this.hideBsModalHandler);
		}
		
	},
	boardSave: ()=>{
		const $title = document.querySelector("#title");
		const $content = document.querySelector("#content");
		console.log('attachList',attachList);
		//HTTP BODY
		const body = {
			title: $title.value,
			content: $content.value,
			attachFiles : attachList //첨부된 이미지 파일
		};
		
		//HTTP HEADER
		const headers = new Headers();
		headers.append('Content-type','application/json; charset=utf-8');
		
		//Request To Server
		fetch('/api/board', {
			method: 'POST',
			body:JSON.stringify(body),
			headers: headers, // 이 부분은 따로 설정하고싶은 header가 있다면 넣으세요
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
				res.json().then(json => {
					if(json.status===200){
						alert('글쓰기가 완료되었습니다.');
						location.href="/";
					}
				});
			} else {
				console.error(res.statusText);
			}
		}).catch(err => console.error(err));
	},
	boardDeleteById:()=>{
		console.log("삭제 나옴");
	},
	shownBsModalHandler: ()=>{ //모달창 생성 시
		const screenBlock = document.querySelector("#screenBlock");
		screenBlock.style.display="block";
	},
	hideBsModalHandler:()=>{ //모달창 사라짐
		const screenBlock = document.querySelector("#screenBlock");
		screenBlock.style.display="none";
	}
}
board.init();