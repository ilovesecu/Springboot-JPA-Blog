let board = {
	init: function(){
		const btnSave=document.querySelector("#btn-save");
		btnSave.addEventListener("click",this.boardSave);
	},
	boardSave: ()=>{
		const $title = document.querySelector("#title");
		const $content = document.querySelector("#content");
		//HTTP BODY
		const body = {
			title: $title.value,
			content: $content.value
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
	}
}
board.init();