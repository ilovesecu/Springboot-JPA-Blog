let board = {
	init: function(){
		const btnSave=document.querySelector("#btn-save");
		const btnDelete = document.querySelector("#btn-delete");
		const deleteModal = document.querySelector("#deleteModal");
		const btnUpdate = document.querySelector("#btn-update"); 
		const replySaveForm = document.querySelector("#replySaveForm"); 
		if(btnSave!=null)
			btnSave.addEventListener("click",this.boardSave);
		if(btnDelete!=null)
			btnDelete.addEventListener("click",this.boardDeleteById);
		if(deleteModal!=null){
			deleteModal.addEventListener("show.bs.modal",this.shownBsModalHandler);
			deleteModal.addEventListener("hide.bs.modal",this.hideBsModalHandler);
		}
		if(btnUpdate!=null)
			btnUpdate.addEventListener("click",()=>this.boardUpdate(this));
		if(replySaveForm!=null)
			replySaveForm.addEventListener("submit",(event)=>this.replySave(this,event));
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
				alert('글쓰기 실패.');
				console.error(res.statusText);
			}
		}).catch(err => alert('글쓰기 실패.',err));
	},
	boardDeleteById:(event)=>{
		const boardNo = event.target.dataset.boardNo;
		//Request To Server
		fetch(`/api/board/${boardNo}`, {
			method: 'DELETE'
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
				res.json().then(json => {
					if (json.status === 200) {
						const deleteCompleteModal = new bootstrap.Modal(document.querySelector('#deleteCompleteModal'), {
							keyboard: false
						});
						deleteCompleteModal.show();
						document.querySelector("#goMainBtn").addEventListener("click",function(){
							location.href="/";
						});
					}
				});
			} else {
				console.error(res.statusText);
			}
		}).catch(err => console.error(err));
	},
	boardUpdate: function(that){
		that.fileSelector(); //지워질 파일을 선택
		const $title = document.querySelector("#title");
		const $content = document.querySelector("#content");
		const no = document.querySelector("#no").value; //어떤 글을 수정할지 서버에게 전송
		
		//HTTP BODY
		const body = {
			title: $title.value,
			content: $content.value,
			attachFiles : attachList, //첨부된 이미지 파일
			deleteFiles : deleteAttachList
		};
		
		//HTTP HEADER
		const headers = new Headers();
		headers.append('Content-type','application/json; charset=utf-8');
		console.log(body);
		//Request To Server
		fetch(`/api/board/${no}`, {
			method: 'PUT',
			body:JSON.stringify(body),
			headers: headers, // 이 부분은 따로 설정하고싶은 header가 있다면 넣으세요
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
				res.json().then(json => {
					if(json.status===200){
						alert('수정이 완료되었습니다.');
						location.href="/";
					}
				});
			} else {
				alert('수정 실패.');
				console.error(res.statusText);
			}
		}).catch(err => alert('수정 실패.',err));
		
	},
	replySave:(that,event)=>{ //댓글 작성
		event.preventDefault();
		const no = document.querySelector("#boardNo").value; //어떤 글의 댓글인지 판단을 위함
		const $replyContent = document.querySelector("#replyContent");
		
		//HTTP BODY
		const body = {
			content: $replyContent.value
		};
		
		//HTTP HEADER
		const headers = new Headers();
		headers.append('Content-type','application/json; charset=utf-8');
		
		//Request To Server
		fetch(`/api/board/${no}/reply`, {
			method: 'POST',
			body:JSON.stringify(body),
			headers: headers, 
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
				res.json().then(json => {
					if(json.status===200){
						alert('댓글 작성이 완료되었습니다.');
						that.refreshReply(no);
					}
				});
			} else {
				alert('댓글 작성 실패.');
				console.error(res.statusText);
			}
		}).catch(err => alert('댓글 작성 실패.',err));
	},
	refreshReply:(no)=>{ //댓글 새로고침
		//HTTP HEADER
		const headers = new Headers();
		headers.append('Content-type','application/json; charset=utf-8');
		
		//Request To Server
		fetch(`/api/board/${no}/reply`, {
			method: 'GET',
			headers: headers
		}).then((res) => {
			if (res.status === 200 || res.status === 201) {
				res.json().then(json => {
					if(json.status===200){
						console.log(json);
					}
				});
			} else {
				alert('댓글 작성 실패.');
				console.error(res.statusText);
			}
		}).catch(err => alert('댓글 작성 실패.',err));
	},
	shownBsModalHandler: ()=>{ //삭제 모달창 생성 시
		const screenBlock = document.querySelector("#screenBlock");
		screenBlock.style.display="block";
	},
	hideBsModalHandler:()=>{ //삭제 모달창 사라짐
		const screenBlock = document.querySelector("#screenBlock");
		screenBlock.style.display="none";
	},
	fileSelector:()=>{ //지워질 파일을 선택하는 함수
		const allImgs = []; //모든 이미지가 들어갈 리스트
		const $noteEditable = document.querySelector(".note-editable");
		const $imgs = $noteEditable.querySelectorAll("img");
		$imgs.forEach((v)=>{
			console.log(decodeURI(v.src.split('/summernoteImage/')[1]));
			allImgs.push(decodeURI(v.src.split('/summernoteImage/')[1]));
		});
		//에디터의 모든 이미지와 기존 이미지를 비교하여 기존 이미지들이 전체 이미지리스트에 없다면 삭제 리스트에 넣는다.
		for(let i=0; i<originalAttachList.length; i++){
			let flag = true;
			for(let j=0; j<allImgs.length; j++){
				if(originalAttachList[i] === allImgs[j]){
					flag = false;
					break;
				}
			}
			if(flag && !deleteAttachList.includes(originalAttachList[i])){
				//console.log(originalAttachList[i]);
				const dateIndex = originalAttachList[i].lastIndexOf('/');
				const uuidIndex = originalAttachList[i].indexOf('_');
				/*console.log('date:',originalAttachList[i].substring(0,dateIndex));
				console.log('uuid:',originalAttachList[i].substring(dateIndex+1,uuidIndex));
				console.log('name:',originalAttachList[i].substring(uuidIndex+1));*/
				const file = {
					'fileName': originalAttachList[i].substring(uuidIndex+1),
					'uploadPath': originalAttachList[i].substring(0,dateIndex),
					'uuid': originalAttachList[i].substring(dateIndex+1,uuidIndex)
				};
				deleteAttachList.push(file);
			}
		}
		
	}
}
board.init();