<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List,com.ssafy.district.dto.Sido"%>
<%@ include file="/style/global.jsp"%>

<%
List<Sido> sidos = (List<Sido>) request.getAttribute("sidos");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Document</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
}

form {
	width: 80%;
	margin: 50px auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

form label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	color: #7952b3;
}

form input[type="text"], form input[type="password"], form select {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ced4da;
	border-radius: 5px;
}

form select {
	appearance: none;
	-webkit-appearance: none;
	-moz-appearance: none;
	background-image:
		url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='%23333'%3e%3cpath d='M6.906 9.781a.25.25 0 00-.312 0l-4 3A.25.25 0 002 13.25v2.25a.25.25 0 00.406.194l4-3a.25.25 0 000-.388zM2.938 10L7 12.344 11.063 10H2.937z'/%3e%3c/svg%3e");
	background-repeat: no-repeat;
	background-position: right 10px center;
	background-size: 16px;
}

form button {
	width: 100%;
	padding: 10px;
	margin-top: 10px;
	border: none;
	border-radius: 5px;
	background-color: #7952b3;
	color: #fff;
	font-size: 16px;
	cursor: pointer;
}

form button:hover {
	background-color: #5e3c8b;
}
</style>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div style="height: 100px"></div>
	
	<form action="${root}/user?action=join" id="joinForm" method="POST">
	<h2 class="mb-3">회원가입</h2>
		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="name" class="form-label">이름:</label> <input type="text"
				class="form-control" id="name" name="name" value="name1" />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="id" class="form-label">아이디:</label> <input type="text"
				class="form-control" id="id" name="id" value="userId1" />
		</div>

		<div id="result-view" class="mb-3"></div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="pass" class="form-label">비밀번호:</label> <input
				type="password" class="form-control" id="pass" name="pass"
				value="pass1" />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="pass2" class="form-label">비밀번호 확인:</label> <input
				type="password" class="form-control" id="pass2" name="pass2"
				value="pass1" />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="email" class="form-label">이메일:</label>
			<div class="row">
				<div class="col">
					<input type="text" class="form-control" id="email" name="email"
						value="이메일" />
				</div>
				@
				<div class="col">
					<select name="domain" id="domain">
						<option value="naver">naver.com</option>
						<option value="google">google.com</option>
					</select>
				</div>
			</div>
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label class="form-label">지역:</label> 
			<select name="sido" id="sido">
				<option value="" disabled selected>시도선택</option>
				<%
				for (Sido sido : sidos) {
				%>
				<option value="<%= sido.getName() %>" data-no="<%= sido.getCode() %>"><%= sido.getName() %></option>
				<%
				}
				%>
			</select> <select name="gugun" id="gugun">
				<option value="" disabled selected>구군선택</option>
			</select>
		</div>
		<div class="row">
			<div class="col">
				<button type="button" class="btn btn-primary w-100" id="join">
					회원가입</button>
			</div>
			<div class="col">
				<button type="reset" class="btn btn-secondary w-100" id="reset">
					초기화</button>
			</div>
			<div class="col">
				<button type="button" class="btn btn-danger w-100" id="cancel">
					취소</button>
			</div>
		</div>
	</form>
	<script>
		
		const idInput = document.getElementById("id");
		const checkIdResult = document.getElementById("result-view");
		let possibleId = false;
		idInput.addEventListener("keyup", async () => {
			const id = idInput.value;
			if(id.length < 4 || id.length > 16) {
				possibleId = false;
				checkIdResult.setAttribute("class", "mb-3 fw-bold text-dark");
				checkIdResult.innerText = "아이디는 4자 이상 16자 이하 입니다."
			} else {
				fetch("${root}/user?action=checkId&id=" + id)
				.then(resp => resp.text())
				.then(text => viewResult(id, text));
			}
		})
		
		const viewResult = (id, duplicated) => {
			if(duplicated == "true") {
				possibleId = false;
				checkIdResult.setAttribute("class", "mb-3 text-danger");
				checkIdResult.innerText = id + "는 사용할 수 없습니다."
				return;
			}
			possibleId = true;
			checkIdResult.setAttribute("class", "mb-3 text-success");
			checkIdResult.innerText = id + "는 사용할 수 있습니다."
		}
	
    	const cancelBtn = document.getElementById("cancel");
    	cancelBtn.addEventListener("click", () => {
    		location.href = "${root}"
    	})
    	
    	const joinBtn = document.getElementById("join");
    	const joinForm = document.getElementById("joinForm");
    	joinBtn.addEventListener("click", () => {
    		  const name = document.querySelector("#name").value;
    		  const userId = document.querySelector("#id").value;
    		  const pass = document.querySelector("#pass").value;
    		  const pass2 = document.querySelector("#pass2").value;
    		  const email = document.querySelector("#email").value;
    		  const domain = document.querySelector("#domain").value;
    		  const sido = document.querySelector("#sido").value;
    		  const gugun = document.querySelector("#gugun").value;

    		  if(!possibleId) {
    			  alert("아이디 중복 확인");
    			  return;
    		  }
    		  
    		  if (!name || !userId || !pass || !pass2 || !email || !sido || !gugun) {
    		     alert("회원 정보 입력");
    		     return;
    		  }
    		  
    		  if (pass != pass2) {
    		    alert("비밀번호 확인");
    		    return;
    		  }
    		  
    		  joinForm.submit();
    		  
    	})
    	
    	const sido = document.querySelector("#sido");
    	sido.addEventListener("change", () => {
    		const gugunSelect = document.querySelector("#gugun");
    		gugunSelect.innerHTML = "";
    		const sidoCode = sido.options[sido.selectedIndex].dataset.no;
    	 	const guguns = getGugun(sidoCode);
    	})
    	
    	const getGugun = (sidoCode) => {
    		fetch("${root}/district?action=gugun&code=" + sidoCode)
    		.then(res => res.json())
    		.then(guguns => makeGugunList(guguns))
    	}
    	
    	const makeGugunList = (guguns) => {
    		guguns.forEach(gugun => {
    			const gugunSelect = document.querySelector("#gugun");
    			const option = document.createElement("option");
    			option.value = gugun;
    			option.innerText = gugun;
    			gugunSelect.appendChild(option);
    		})
    	}
    </script>
</body>
</html>
