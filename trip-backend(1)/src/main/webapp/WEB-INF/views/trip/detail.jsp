<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/style/global.jsp" %>


<!DOCTYPE html>
<html>
<head>
<link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
      crossorigin="anonymous"
    />
<meta charset="UTF-8">
<title>SSAFY</title>
<style>
	
        .container {
        	
            h2 {
            	color: #007bff; /* 제목 색상 지정 */
	        }
	        p {
	            margin-bottom: 10px; /* 문단 사이 여백 지정 */
	        }
	        img {
	            max-width: 100%; /* 이미지 최대 너비 지정 */
	            height: auto; /* 이미지 높이 자동으로 조정 */
	            border-radius: 5px; /* 이미지 테두리 둥글게 처리 */
	            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
	        }
	        .back-btn {
	            margin-top: 20px; /* 뒤로 가기 버튼 위쪽 여백 지정 */
	        }
        }
        
    #temp {
		height: 100px;
	}
</style>
</head>

<body>
<%@ include file="/common/header.jsp" %>
	<div class="container">
        
        <div id="temp"></div>
        <div class="back-btn mb-4" >
            <a href="javascript:void(0);" onclick="history.back();" class="btn btn-primary">뒤로 가기</a>
        </div>
        <div>
            <h2 class="fw-bolder">${attraction.title }</h2>
            <p><strong>개요:</strong> ${attraction.overview }</p>
            <p><strong>주소:</strong> ${attraction.addr1 } ${attraction.addr2 }</p>
            <c:if test="${not empty attraction.tel}">
			    	<p><strong>전화번호:</strong> ${attraction.tel }</p>
			</c:if>
            
            <p>
            	<c:if test="${not empty attraction.firstImage}">
			    	<img src="${attraction.firstImage}" alt="${attraction.title}">
				</c:if>
			</p>
        </div>
    </div>
</body>
</html>