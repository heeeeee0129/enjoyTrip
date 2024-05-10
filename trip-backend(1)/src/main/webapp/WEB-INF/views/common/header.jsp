<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.ssafy.member.dto.Member"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<%
Member loginUser = (Member) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enjoy Trip</title>
<style>
.navbar-brand img {
	max-height: 40px; /* 로고 이미지의 최대 높이 */
}

.navbar-nav .nav-item {
	margin-right: 30px; /* 네비게이션 요소 간의 우측 여백 */
}

.navbar-nav .nav-link {
	transition: color 0.3s; /* 색상 변화에 대한 애니메이션 효과 */
}

.navbar-nav .nav-link:hover {
	color: #007bff; /* 호버 시 색상 변경 */
	font-weight: bolder;
}
a {
    text-decoration: none; /* 링크의 밑줄 제거 */
    color: inherit; /* 링크의 색을 부모 요소로부터 상속받음 */
    cursor: pointer; /* 커서를 포인터로 변경하여 링크처럼 보이도록 함 */
}

#backImage {
	width: 130%;
	margin-right: -10px;
}
</style>

<script src="${root }/assets/js/key.js"></script>
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
		<div class="container">
			<a class="navbar-brand text-primary fw-bold text-center mx-auto"
				href="${root}/"> <img src="${root}/assets/img/logo.png"
				alt="" height="50" />
			</a>
			<button class="navbar-toggler" type="button" ${root}
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav mt-2 ms-4 mb-lg-0">
					<li class="nav-item"><a class="nav-link" id="search"
						aria-current="page" href="${root}/trip?action=mvsearch">여행지검색</a>
					</li>
					<% if(loginUser != null) { %>
					<li class="nav-item"><a class="nav-link" id="plan"
						aria-current="page" href="${root}/trip?action=mvplan&userId=<%= loginUser.getId() %>">여행계획</a>
						<% } else { %>
											<li class="nav-item"><a class="nav-link" id="plan"
						aria-current="page" href="${root}/trip?action=mvplan">여행계획</a>
						<% } %>
					</li>
					<li class="nav-item"><a class="nav-link" id="notice"
						aria-current="page" href="${root}/notice?action=list">공지사항</a>
					</li>
					<li class="nav-item"><a class="nav-link" id="board"
						aria-current="page" href="${root}/article?action=list">공유게시판</a>
					</li>
				</ul>

				<!-- 로그인 전 -->
				<% if(loginUser == null) { %>
				<ul class="navbar-nav ms-auto mb-2 me-2 mb-lg-0" id="guest-view">
					<li class="nav-item"><a class="nav-link" id="mvjoin"
						aria-current="page" href="${root}/user?action=mvjoin">회원가입</a></li>
					<li class="nav-item"><a class="nav-link" id="login"
						aria-current="page" href="${root}/user?action=mvlogin">로그인</a></li>
				</ul>
				<% } else { %>
				<!-- 로그인 후 -->
				<ul class="navbar-nav ms-auto mb-2 me-2 mb-lg-0" id="user-view">
					<li class="nav-item" style="margin: auto; margin-right: 10px; color: #000000cf;"><%= loginUser.getName() %>님 해윙</li>
					<li class="nav-item"><a class="nav-link" id="logout"
						aria-current="page" href="${root}/user?action=logout">로그아웃</a></li>
					<li class="nav-item"><a class="nav-link" id="mypage"
						aria-current="page" href="${root}/user?action=mypage">마이페이지</a></li>
				</ul>
				<% } %>
			</div>
		</div>
	</nav>
</body>
</html>