<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/style/global.jsp" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>ENJOY TRIP</title>
    
       
      <style>
        /* 추가적인 스타일링 */
      body {
        background-image: url("./assets/img/enjoyTrip.png");
        background-size: contain;
        background-position: top;
        background-repeat: no-repeat;
      }

      #search-box {
        margin: 50% 20% 20%;
      }

      .search-link {
        color: gray;
        transition: color 0.6s;
        text-decoration: none;
      }
      .search-link:hover {
        color: #238eff;
        font-weight: bolder;
      }
        
    </style>
  </head>
  <body>
  <%@ include file="/common/header.jsp" %>
    <div class="text-center" id="search-box">
      <h1 class="text-center" style="margin-bottom: 50px">여행지 검색</h1>

      <form class="d-flex my-3" onsubmit="return false;" role="search">
        <select id="search-area" class="form-select me-2">
          <option value="0" selected>검색 할 지역 선택</option>
        </select>

        <select id="search-area-2" class="form-select me-2">
          <option value="0" selected>시군구 선택</option>
        </select>

        <input
          id="search-keyword"
          class="form-control me-2"
          type="search"
          placeholder="검색어"
          aria-label="검색어" />
          
        <button
          id="btn-search"
          class="btn btn-outline-success"
          style="min-width: 70px; min-height: 45px"
          type="button">
          검색
        </button>
      </form>

      <div class="text-center" onchange="handleCheck()">
        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="select-all"
            onchange="selectAll(this)" />
          <label class="form-check-label" for="select-all">모두</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-12"
            name="tourism-type"
            value="12" />
          <label class="form-check-label" for="tourism-type-12">관광지</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-14"
            name="tourism-type"
            value="14" />
          <label class="form-check-label" for="tourism-type-14">문화시설</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-15"
            name="tourism-type"
            value="15" />
          <label class="form-check-label" for="tourism-type-15"
            >축제공연행사</label
          >
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-25"
            name="tourism-type"
            value="25" />
          <label class="form-check-label" for="tourism-type-25">여행코스</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-28"
            name="tourism-type"
            value="28" />
          <label class="form-check-label" for="tourism-type-28">레포츠</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-32"
            name="tourism-type"
            value="32" />
          <label class="form-check-label" for="tourism-type-32">숙박</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-38"
            name="tourism-type"
            value="38" />
          <label class="form-check-label" for="tourism-type-38">쇼핑</label>
        </div>

        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="checkbox"
            id="tourism-type-39"
            name="tourism-type"
            value="39" />
          <label class="form-check-label" for="tourism-type-39">음식점</label>
        </div>
      </div>
      <p style="margin-top: 50px">
        <a href="${root}/trip?action=mvsearch" class="search-link">여행지 검색하러 가기 ></a>
      </p>
    </div>

    <!-- 중앙 content start -->
    <footer class="footer" style="margin-top: 10%; margin-bottom: 2%">
      <div class="container-fluid">
        <div class="row">
          <div class="col-lg-12 text-center">
            <ul class="nav justify-content-center">
              <li class="nav-item">
                <a href="https://edu.ssafy.com/" class="nav-link text-secondary"
                  >edu.ssafy</a
                >
              </li>
              <li class="nav-item">
                <a
                  href="https://lab.ssafy.com/kmw9093/pjt02_buk04_11_02"
                  class="nav-link text-secondary"
                  >github</a
                >
              </li>
              <li class="nav-item">
                <a href="" class="nav-link text-secondary">공지환</a>
              </li>
              <li class="nav-item">
                <a href="" class="nav-link text-secondary">정희수</a>
              </li>
              <li class="nav-item">
                <span class="nav-link text-secondary">&copy; SSAFY Corp.</span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </footer>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
      crossorigin="anonymous"></script>
    
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=03e6555145d690facacd7eeb3ca5cca9&libraries=services,clusterer,drawing"></script>

  </body>
</html>
