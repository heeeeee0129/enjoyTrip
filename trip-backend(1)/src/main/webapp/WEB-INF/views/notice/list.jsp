<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/style/global.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.post-item {
	border: 1px solid #dee2e6;
	border-radius: 5px;
	margin-bottom: 20px;
	padding: 20px;
}

.post-title {
	font-size: 1.2rem;
	font-weight: bold;
}

.post-meta {
	font-size: 0.9rem;
	color: #6c757d;
}

.post-content {
	margin-top: 10px;
}

.btn {
	font-size: 0.9rem;
}

.board-container {
	margin-top: 200px;
}
</style>
</head>
<body class="medium">
	<%@ include file="/common/header.jsp"%>
	<div class="container board-container">
		<h1 style="text-align: left; margin: 30px 120px;">공지사항</h1>

		<div class="row">

			<div class="col-md-10 offset-md-1">
				<c:if test="${not empty user and user.id eq 'admin'}">
				<div class="col-md-2 text-start mb-3">
					<button type="button" id="btn-mv-register"
						class="btn btn-outline-primary btn-sm">공지사항 작성하기</button>
				</div>
				</c:if>
				
				<div class="table-responsive">

					<table class="table table-bordered table-striped">
						<thead class="table-light">
							<tr class="text-center">
								<th scope="col">글번호</th>
								<th scope="col">제목</th>
								<th scope="col">작성자</th>
								<th scope="col">조회수</th>
								<th scope="col">작성일</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="article" items="${articles}">
								<tr class="text-center">
									<th scope="row">${article.articleNo}</th>
									<td class="text-start"><a href="#"
										class="article-title link-dark" data-no="${article.articleNo}"
										style="text-decoration: none">
											${article.subject} </a></td>
									<td>${article.userId}</td>
									<td>${article.hit}</td>
									<td>${article.registerTime}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="row">
					<ul class="pagination justify-content-center">
						<li class="page-item"><a class="page-link" href="#">이전</a></li>
						<li class="page-item"><a class="page-link" href="#">1</a></li>
						<li class="page-item active"><a class="page-link" href="#">2</a>
						</li>
						<li class="page-item"><a class="page-link" href="#">3</a></li>
						<li class="page-item"><a class="page-link" href="#">4</a></li>
						<li class="page-item"><a class="page-link" href="#">5</a></li>
						<li class="page-item"><a class="page-link" href="#">다음</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>


	<!-- 중앙 content start -->
	<footer class="footer" style="margin-top: 10%; margin-bottom: 2%;">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12 text-center">
					<ul class="nav justify-content-center">
						<li class="nav-item"><a href="https://edu.ssafy.com/"
							class="nav-link text-secondary">edu.ssafy</a></li>
						<li class="nav-item"><a
							href="https://lab.ssafy.com/kmw9093/pjt02_buk04_11_02"
							class="nav-link text-secondary">github</a></li>
						<li class="nav-item"><a href=""
							class="nav-link text-secondary">공지환</a></li>
						<li class="nav-item"><a href=""
							class="nav-link text-secondary">정희수</a></li>
						<li class="nav-item"><span class="nav-link text-secondary">&copy;
								SSAFY Corp.</span></li>
					</ul>
				</div>
			</div>
		</div>
	</footer>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
		crossorigin="anonymous"></script>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=03e6555145d690facacd7eeb3ca5cca9&libraries=services,clusterer,drawing"></script>
	<script src="./assets/js/main.js">
    </script>
	<script>
    let titles = document.querySelectorAll(".article-title");
    titles.forEach(function (title) {
      title.addEventListener("click", function () {
        console.log(this.getAttribute("data-no"));
        location.href = "${root}/notice?action=view&articleno=" + this.getAttribute("data-no");
      });
    });
    document.querySelector("#btn-mv-register").addEventListener("click", function () {
        location.href = "${root}/notice?action=mvwrite";
      });
    </script>
</body>
</html>
