<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %> uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/style/global.jsp" %>

<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />
    
    <style>
    #temp {
		height: 200px;
	}
</style>
    <title>SSAFY</title>
  </head>
  <body>
<div class="container">
  <%@ include file="/common/header.jsp" %>
  <div id="temp"></div>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
          <h2 class="my-3 py-3 text-center">
            글쓰기
          </h2>
        </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-register" method="POST" action="">
        <input type="hidden" name="action" value="write">
        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input type="text" class="form-control" id="subject" name="subject" placeholder="제목..." />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea class="form-control" id="content" name="content" rows="7"></textarea>
        </div>
        <div class="col-auto text-center">
          <button type="button" id="btn-register" class="btn btn-outline-primary mb-3">글작성</button>
          <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
        </div>
      </form>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script>
  document.querySelector("#btn-register").addEventListener("click", function () {
    if (!document.querySelector("#subject").value) {
      alert("제목 입력!!");
      return;
    } else if (!document.querySelector("#content").value) {
      alert("내용 입력!!");
      return;
    } else {
      let form = document.querySelector("#form-register");
      form.setAttribute("action", "${root}/notice");
      form.submit();
    }
  });
</script>
</body>
</html>
