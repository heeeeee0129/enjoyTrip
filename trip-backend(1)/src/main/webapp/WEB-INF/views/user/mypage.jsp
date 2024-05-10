<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="com.ssafy.member.dto.Member, com.ssafy.district.dto.Sido"
	import="java.util.List"%>
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
<link rel="shortcut icon" href="/main/assets/img/favicon.png" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" />
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
	
	<form id="form" method="POST">
	<h2 class="mb-3">마이페이지</h2>
		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="name" class="form-label">이름:</label> <input type="text"
				class="form-control" id="name" name="name" value="${user.name}"
				disabled />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="id" class="form-label">아이디:</label> <input type="text"
				class="form-control" id="id" name="id" value="${user.id}" disabled />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="pass" class="form-label">비밀번호:</label> <input
				type="password" class="form-control" id="pass" name="pass"
				value="${user.pwd}" disabled />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="email" class="form-label">이메일:</label> <input type="text"
				class="form-control" id="email" name="email" value="${user.emailId}"
				disabled /> @ <select name="domain" id="domain" disabled>
				<option value="naver.com">naver.com</option>
				<option value="google.com">google.com</option>
				<option value="ssafy.com">ssafy.com</option>
			</select>
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label class="form-label">지역:</label> <select name="sido" id="sido"
				disabled>
				<option value="" disabled selected>시도선택</option>
				<%
				for (Sido sido : sidos) {
				%>
				<option value="<%=sido.getName() %>" data-no="<%= sido.getCode() %>"><%=sido.getName()%></option>
				<%
				}
				%>
			</select> <select name="gugun" id="gugun" disabled>
				<option value="" disabled>구군선택</option>
			</select>
		</div>

		<input type="hidden" id="modifiedId" name="modifiedId" value="">
		<input type="hidden" id="modifiedName" name="modifiedName" value="">
		<input type="hidden" id="modifiedPass" name="modifiedPass" value="">
		<input type="hidden" id="modifiedEmail" name="modifiedEmail" value="">
		<input type="hidden" id="modifiedDomain" name="modifiedDomain"
			value=""> <input type="hidden" id="modifiedSido"
			name="modifiedSido" value=""> <input type="hidden"
			id="modifiedGugun" name="modifiedGugun" value="">

		<button type="button" id="home">홈</button>
		<button type="button" id="modify">수정</button>
		<button type="button" id="remove">회원탈퇴</button>

		<button type="button" id="confirm" style="display: none">확인</button>
		<button type="button" id="cancel" style="display: none">취소</button>
	</form>
	<script>
		window.onload = () => {
			const sidoCode = sido.options[sido.selectedIndex].dataset.no;
	    	const guguns = getGugun(sidoCode);	
		}
	
    	const selectedDomain = "${user.emailDomain}";
    	const domainOptions = document.getElementById("domain").options;
    	Array.from(domainOptions).forEach(option => {
    	    if (option.value === selectedDomain) {
    	        option.selected = true;
    	    }
    	});
    	
    	const selectedSido = "${user.sido}";
    	const sidoOptions = document.getElementById("sido").options;
    	Array.from(sidoOptions).forEach(option => {
    	    if (option.value === selectedSido) {
    	        option.selected = true;
    	    }
    	});
    	
    	const modifyBtn = document.querySelector("#modify");
    	const removeBtn = document.querySelector("#remove");
    	const form = document.querySelector("#form");
		
    	modifyBtn.addEventListener("click", () => {
    		  const name = document.querySelector("#name");
    		  const userId = document.querySelector("#id");
    		  const pass = document.querySelector("#pass");
    		  const email = document.querySelector("#email");
    		  const domain = document.querySelector("#domain");
    		  const sido = document.querySelector("#sido");
    		  const gugun = document.querySelector("#gugun");

    		  const removeBtn = document.querySelector("#remove");
    		  removeBtn.style.display = "none";
    		  modifyBtn.style.display = "none";

    		  const confirmBtn = document.querySelector("#confirm");
    		  const cancelBtn = document.querySelector("#cancel");
    		  confirmBtn.style.display = "";
    		  cancelBtn.style.display = "";

    		  confirmBtn.addEventListener("click", () => {
    		    // 유효성 검사 추가하기

    		    name.disabled = true;
    		    pass.disabled = true;
    		    email.disabled = true;
    		    domain.disabled = true;
  		      sido.setAttribute("disabled", "disabled");
		      gugun.setAttribute("disabled", "disabled");


    		    removeBtn.style.display = "";
    		    modifyBtn.style.display = "";

    		    confirmBtn.style.display = "none";
    		    cancelBtn.style.display = "none";
    		    
    		    document.getElementById("modifiedId").value = userId.value;
    		    document.getElementById("modifiedName").value = name.value;
    		    document.getElementById("modifiedPass").value = pass.value;
    		    document.getElementById("modifiedEmail").value = email.value;
    		    document.getElementById("modifiedDomain").value = domain.value;
    		    document.getElementById("modifiedSido").value = sido.value;
    		    document.getElementById("modifiedGugun").value = gugun.value;
    		    
    		    form.setAttribute("action", "${root}/user?action=modify");
    		    form.setAttribute("method", "post");
    		    form.submit();
    		  });
    		  
    		  cancelBtn.addEventListener("click", () => {
    		    name.disabled = true;
    		    pass.disabled = true;
    		    email.disabled = true;
    		    domain.disabled = true;
    		    sido.disabled = true;
    		    gugun.disabled = true;
    		      sido.setAttribute("disabled", "disabled");
    		      gugun.setAttribute("disabled", "disabled");

    		    removeBtn.style.display = "";
    		    modifyBtn.style.display = "";

    		    confirmBtn.style.display = "none";
    		    cancelBtn.style.display = "none";
    		  });

    		  name.disabled = false;
    		  pass.disabled = false;
    		  email.disabled = false;
    		  domain.disabled = false;
    		  sido.disabled = false;
    		  gugun.disabled = false;
    		});
    	
    	const homeBtn = document.querySelector("#home");
    	homeBtn.addEventListener("click", () => {
    		location.href = "${root}/index.jsp"
    	})
    	
    	const expireBtn = document.querySelector("#remove");
    	expireBtn.addEventListener("click", () => {
    		fetch("${root}/user?action=expire&id=${user.id}")
    		.then(response => {
    			if(response.ok) {
    				location.href = "${root}/";
    			}
    		})
    	})

    	const sido = document.querySelector("#sido");
    	sido.addEventListener("change", () => {
    		const sidoCode = sido.options[sido.selectedIndex].dataset.no;
    		const gugunSelect = document.querySelector("#gugun");
    		gugunSelect.innerHTML = "";
    	 	const guguns = getGugun(sidoCode);
    	})
    	
    	const getGugun = async (sidoCode) => {
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
        	const selectedGugun = "${user.gugun}";
        	const gugunOptions = document.getElementById("gugun").options;
        	Array.from(gugunOptions).forEach(option => {
        	    if (option.value === selectedGugun) {
        	        option.selected = true;
        	    }
        	});
    	}
    </script>
</body>
</html>
