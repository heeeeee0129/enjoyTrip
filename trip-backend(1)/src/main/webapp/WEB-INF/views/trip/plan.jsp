<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, com.ssafy.district.dto.Sido, com.ssafy.member.dto.Member" %>
<%@ include file="/style/global.jsp"%>
<%
List<Sido> sidos = (List<Sido>) request.getAttribute("sidos");
Member user = (Member) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>ENJOY TRIP</title>

<style>
.navbar-brand img {
	max-height: 40px; /* 로고 이미지의 최대 높이 */
}

.navbar-nav .nav-item {
	margin-right: 10px; /* 네비게이션 요소 간의 우측 여백 */
}

.navbar-nav .nav-link {
	transition: color 0.3s; /* 색상 변화에 대한 애니메이션 효과 */
}

.navbar-nav .nav-link:hover {
	color: #007bff; /* 호버 시 색상 변경 */
	font-weight: bolder;
}

.form-check-input {
	transition: all 0.3s ease; /* 모든 스타일 속성에 대해 0.3초의 트랜지션 효과 적용 */
}

.form-check-input[type="checkbox"]:checked {
	transform: scale(1.2); /* 선택된 체크박스 확대 */
}

#search-header {
	margin: 40px 0;
}

#temp {
	height: 60px;
}
/* 부모 요소에 flex 속성 적용 */
.map-container {
	display: flex;
	align-items: center;
}

/* map 요소의 너비 및 높이 설정 */
#map {
	min-width: 80%; /* 또는 다른 너비 값으로 설정 */
	height: 500px;
}

/* marker-info 요소의 스타일 설정 */
#marker-info {
	padding: 20px; /* 내부 여백 설정 */
	:
	empty
	{
	display
	:
	none;
}
}
</style>
</head>
<body>
	<%@ include file="/common/header.jsp"%>
	<div class="container">
		<div style="height: 100px"></div>
		<div class="row">

			<div class="">
				<div id="temp"></div>
				<div class=" mt-3 text-center fw-bolder" id="search-header">
					<h2 class="mb-3">여행 계획</h2>
				</div>

				<form class="d-flex my-3" role="search">
					<select id="sido" class="form-select me-2">
						<option value="0" selected>시/도 선택</option>
						<%
						for (Sido sido : sidos) {
						%>
						<option value="<%=sido.getName()%>"
							data-no="<%=sido.getCode()%>"><%=sido.getName()%></option>
						<%
						}
						%>
					</select> <select id="gugun" class="form-select me-2">
						<option value="0" selected>군/구 선택</option>
					</select> <input id="search-keyword" class="form-control me-2" type="search"
						placeholder="검색어" aria-label="검색어" style="display: none;" />


					<button id="btn-search" class="btn btn-outline-success"
						type="button">검색</button>
				</form>

				<!-- kakao map start -->
				<div class="map-container">
					<div id="map" class="mt-3"></div>
					<div id="marker-info"></div>
				</div>
				<!-- kakao map end -->
				<div class="row">
					<table class="table table-striped" style="display: none" id="none">
						<thead>
							<tr>
								<th>대표이미지</th>
								<th>관광지명</th>
								<th>주소</th>
								<th>위도</th>
								<th>경도</th>
							</tr>
						</thead>
						<tbody id="trip-list"></tbody>
					</table>
				</div>
				<!-- 관광지 검색 end -->
			</div>

			<div>
				
					<div class="container">
					    <div class="row mt-5">
					        <div class="col">
					            <h2 class="text-center">TIMELINE</h2>
					            <div class="row row-cols-1 row-cols-md-2 g-4 mt-3" id="trip-box">
					            	<c:forEach var="attraction" items="${attractions}" >
					               
					                    <div class="col">
					                        <div class="card">
					                            <img src="${ attraction.firstImage}" class="card-img-top" alt="${ attraction.title}">
					                            <div class="card-body">
					                                <h5 class="card-title">${ attraction.title}</h5>
					                                <p class="card-text">${ attraction.addr1}%> ${ attraction.addr2}%></p>
					                                
					                                <a href="${root}/attraction?action=detail&contentId=${ attraction.contentId}%>" class="btn btn-primary">상세보기</a>
					                            </div>
					                        </div>
					                    </div>
					                </c:forEach>
					            </div>
					        </div>
					  
					</div>
					
					
				</div>
			</div>
		</div>
		<!-- 중앙 content end -->

		<!-- 하단 footer start -->
		<footer class="footer" style="margin-top: 10%; margin-bottom: 2%;">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12 text-center">
						<ul class="nav justify-content-center">
							<li class="nav-item"><a href="#"
								class="nav-link text-secondary">카페소개</a></li>
							<li class="nav-item"><a href="#"
								class="nav-link text-secondary">개인정보처리방침</a></li>
							<li class="nav-item"><a href="#"
								class="nav-link text-secondary">이용약관</a></li>
							<li class="nav-item"><a href="#"
								class="nav-link text-secondary">오시는길</a></li>
							<li class="nav-item"><span class="nav-link text-secondary">&copy;
									SSAFY Corp.</span></li>
						</ul>
					</div>
				</div>
			</div>
		</footer>
		<!-- 하단 footer end -->
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
			crossorigin="anonymous"></script>

		<script type="text/javascript"
			src="//dapi.kakao.com/v2/maps/sdk.js?appkey=03e6555145d690facacd7eeb3ca5cca9&libraries=services,clusterer,drawing"></script>
		<script src="${root }/assets/js/map.js">
    	</script>
		<script>
		const sido = document.querySelector("#sido");
		const gugunSelect = document.querySelector("#gugun");
		const search = document.querySelector("#btn-search");
		const contentTypeIds = document.getElementsByName("tourism-type");
		console.log(contentTypeIds);

		search.addEventListener('click', () => {
			
			sidoCode = (sido.options[sido.selectedIndex].dataset.no) || 0;
			gugunCode = (gugun.options[gugun.selectedIndex].dataset.no) || 0;
			const searchKeyword = document.getElementById('search-keyword').value;
			
			url = "${root}/attraction?action=list&sido=" + sidoCode+"&gugun=" + gugunCode+"&keyword=" + encodeURIComponent(searchKeyword);
			
			contentTypeIds.forEach(id => {
				if(id.checked) {
					url += "&contentTypeId="+id.value;
				}
			})
			
			console.log(url);
				fetch(url)
				.then(resp => resp.json())
				.then(trips => {
					if (trips) {
				    let tripList = ``;
				    positions = [];
				    trips.forEach((area) => {
				    	if(area.firstImage == ""){
				    		area.firstImage = "${root}/assets/img/default.png";
				    	}
				    	
				    
				      tripList += `
				      <tr id= ` +area.contentId+` onclick="redirectToDetail(` +area.contentId+` )">
				        <td><img src=`+area.firstImage+` width="100px"></td>
				        <td>`+area.title + `</td>
				        <td>`+area.addr1 + " " + area.addr2+`</td>
				        <td>`+area.latitude+`</td>
				        <td>`+area.longitude+`</td>
				      </tr>
				    `;		
				     
				    let markerInfo = {
				    	        title: area.title,
				    	        addr: area.addr1 +" "+ area.addr2,
				    	        img: area.firstImage,
				    	        latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
				    	        contentId: area.contentId,
				    	      };
				    positions.push(markerInfo);
				    });
				    // console.log(tripList)
				    document.getElementById("trip-list").innerHTML = tripList;
				    displayMarker();
				   
				  } else {
				    document.getElementById("trip-list").innerHTML = `
				    <tr class="text-center">
				      <td colspan="5">검색 결과가 없습니다.</td>
				    </tr>
				  `;
				  }});
			
			
			
		})
		function redirectToDetail(contentId) {
			console.log(contentId)
		  window.location.href = `${root}/attraction?action=detail&contentId=`+contentId;
		}
		
		
		function displayMarker() {
		  // 마커 이미지의 이미지 주소입니다
		  var imageSrc = "${root}/assets/img/marker1.png";
		  var imageSize = new kakao.maps.Size(24, 35);
		  positions.forEach((position) => {
		    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
		    var marker = new kakao.maps.Marker({
		      map: map,
		      position: position.latlng,
		      title: position.title,
		      image: markerImage,
		      clickable: true,
		    });
		    var content =
		    	  '<div class="popup-content flex">' +
		    	  '<img src="' +
		    	  position.img +
		    	  '" width=40% alt="' +
		    	  position.title +
		    	  '" class="popup-img">' +
		    	  '<div class="popup-info">' +
		    	  "<h4>" +
		    	  position.title +
		    	  "</h4>" +
		    	  "<p>" +
		    	  position.addr +
		    	  "</p>" +
		    	  "</div>" +
		    	  "</div>";
		
		    kakao.maps.event.addListener(marker, "click", function () {
				displayMarkerInfo(position);
		    });
		
		    marker.setMap(map);
		  });
		  map.setCenter(positions[0].latlng);
		}
		
		function displayMarkerInfo(position) {
		    let infoDiv = document.getElementById('marker-info');
		    console.log(position)
		    if (!infoDiv) {
		        // marker-info 요소가 없을 경우 생성
		        infoDiv = document.createElement('div');
		        infoDiv.id = 'marker-info';
		        document.body.appendChild(infoDiv);
		    }

		    let markerInfoHTML = `
		        <div>
		            <img src="` + position.img+`" alt="`+position.title+`" style="width: 100px;"><br/>
		            <input type="hidden" id="content_id" name="content_id" value="` + position.contentId+`"
		            <p><strong>관광지명:</strong> `+position.title+`</p>
		            <p><strong>주소:</strong> `+ position.addr+`</p>
		            <p><strong>위도:</strong> `+ position.latlng.getLat()+`</p>
		            <p><strong>경도:</strong> `+ position.latlng.getLng()+`</p>
		            <button class="btn btn-outline-success add-btn" id="plan_add_btn">추가</button>
		        </div>
		    `;

		    infoDiv.innerHTML = markerInfoHTML;

		    // 추가 버튼에 클릭 이벤트 리스너 추가
		    let addButton = document.querySelector('#plan_add_btn');
		    addButton.addEventListener('click', async function() {
		    	const contentId = document.querySelector("#content_id").value;
		        <% if(user == null) { %>
		            alert("로그인후 사용 가능합니다.");
		            location.href = "${root}/user?action=mvlogin";
		        <% } else { %>
		        	const url = "${root}/trip?action=addPlan&contentId=" + contentId + "&userId=<%= user.getId() %>";
		        	console.log(url);
		            fetch(url)
						.then(res => res.json())
		                .then(data => {
		                	let addedtrip = `<div class="col">
                            <div class="card">
                                <img src="`+ data.firstImage+`" class="card-img-top" alt="`+ data.title+`">
                                <div class="card-body">
                                    <h5 class="card-title">`+data.title+`</h5>
                                    <p class="card-text">`+ data.addr1 + " " + data.addr2+`</p>
                                    <a href="${root}/attraction?action=detail&contentId=`+data.contentId+`" class="btn btn-primary">상세보기</a>
                                </div>
                            </div>
                            </div>`;

                            document.getElementById("trip-box").insertAdjacentHTML("afterbegin", addedtrip);
		                });
		                
		        <% } %>
		    });
		}
	
		sido.addEventListener('change', () => {
			gugunSelect.innerHTML = "";
			const sidoCode = sido.options[sido.selectedIndex].dataset.no;
	    	const guguns = getGugun(sidoCode);	
		})    	
    	
    	const getGugun = async (sidoCode) => {
    		fetch("${root}/district?action=gugun&code=" + sidoCode)
    		.then(res => res.json())
    		.then(guguns => makeGugunList(guguns))
    	}
    	
    	const makeGugunList = (guguns) => {
    		const defaultOption = document.createElement("option");
    		defaultOption.value = "0";
    		defaultOption.innerText = "시군구 선택";
    		gugunSelect.appendChild(defaultOption);
    		guguns.forEach((gugun, idx) => {
    			const option = document.createElement("option");
    			option.value = gugun;
    			option.innerText = gugun;
    			option.dataset.no = idx+1;
    			gugunSelect.appendChild(option);
    		})
    	}

		</script>
</body>
</html>
