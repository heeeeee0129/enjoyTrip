<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/style/global.jsp"%>
<%
String msg = (String) request.getAttribute("msg");
String pwd = (String) request.getAttribute("pwd");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>LOGIN</title>

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

form input[type="text"], form input[type="password"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ced4da;
	border-radius: 5px;
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

form button#pass-find {
	background-color: #6c757d;
}

form button#pass-find:hover {
	background-color: #5a6268;
}
</style>
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div style="height: 100px"></div>
	<form action="${root}/user?action=login" id="joinForm" method="POST">
	<h2 class="mb-3">로그인</h2>
		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="id" class="form-label">아이디:</label> <input type="text"
				class="form-control" id="id" name="id" value="userId1" />
		</div>

		<div class="mb-3">
			<i class="bi bi-patch-question" style="color: rgb(121, 2, 119)"></i>
			<label for="pass" class="form-label">비밀번호:</label> <input
				type="password" class="form-control" id="pass" name="pass"
				value="pass1" />
		</div>

		<div class="row">
			<div class="col">
				<button type="submit" id="login-btn" class="btn btn-primary">
					로그인</button>
			</div>
			<div class="col">
				<button type="button" id="pass-find" class="btn btn-secondary">
					비밀번호 찾기</button>
			</div>
		</div>
	</form>


	<%
	if (msg != null) {
	%>
	<script>alert("<%=msg%>")
	</script>
	<%
	}
	%>

	<%
	if (pwd != null) {
	%>
	<script>alert("비밀번호: <%=pwd%>")
	location.href = "${root}/user/login.jsp";
	</script>
	<%
	}
	%>

	<script>
	const findBtn = document.getElementById("pass-find");
	findBtn.addEventListener("click", () => {
		location.href = "${root}/user/findpass.jsp"
	})
	</script>

</body>
</html>
