
/* const BASE_URL = `https://apis.data.go.kr/B551011/KorService1/`;
const BASE_QUERY = `serviceKey=${serviceKey}&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;

let areaUrl =
  "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
  serviceKey +
  "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

fetch(areaUrl, { method: "GET" })
  .then((response) => response.json())
  .then((data) => makeOption(data));

let areaCode = 0;
let sigunguCode = 0;
let contentType = [];

function makeOption(data) {
  let areas = data.response.body.items.item;
  console.log(data.response.body.items);
  let sel = document.getElementById("search-area");
  areas.forEach((area) => {
    let opt = document.createElement("option");
    opt.setAttribute("value", area.code);
    opt.appendChild(document.createTextNode(area.name));
    sel.appendChild(opt);
  });
  sel.addEventListener("change", () => {
    areaCode = sel.value;
    if (areaCode) {
      makeSigungu();
      search();
    }
  });
}

const makeSigungu = () => {
  let url =
    "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
    serviceKey +
    `&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&areaCode=${areaCode}&_type=json`;
  fetch(url, { method: "GET" })
    .then((response) => response.json())
    .then((data) => {
      let sigungu = data.response.body.items.item;
      let sel = document.getElementById("search-area-2");
      for (let i = sel.options.length - 1; i >= 1; i--) {
        sel.remove(i);
      }

      sigungu.forEach((area) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", area.code);
        opt.appendChild(document.createTextNode(area.name));
        sel.appendChild(opt);
      });

      sel.addEventListener("change", () => {
        sigunguCode = sel.value;
        if (sigunguCode) {
          search();
        }
      });
    });
};

const search = () => {
  let queryString = ``;
  if (areaCode) {
    queryString += `&areaCode=${areaCode}`;
  }
  if (sigunguCode) {
    queryString += `&sigunguCode=${sigunguCode}`;
  }
  if (contentType && contentType.length > 0) {
    contentType.forEach((type) => {
      queryString += `&contentTypeId=${type}`;
    });
  }

  let service = ``;
  let keyword = document.getElementById("search-keyword").value;
  if (document.getElementById("search-keyword").value) {
    service = `searchKeyword1`;
    queryString += `&keyword=${keyword}`;
  } else {
    service = `areaBasedList1`;
  }

  let searchUrl = BASE_URL + service + "?" + BASE_QUERY + queryString;
  console.log(searchUrl);
  fetch(searchUrl)
    .then((response) => response.json())
    .then((data) => makeList(data));
};
*/

const selectAll = (checkbox) => {
  let checkboxes = document.querySelectorAll('input[name="tourism-type"]');
  checkboxes.forEach((cb) => {
    cb.checked = checkbox.checked;
  });
};

const handleCheck = () => {
  let selectedRadios = document.querySelectorAll(
    'input[name="tourism-type"]:checked'
  );
  let contentTypeIds = [];

  selectedRadios.forEach((radio) => {
    contentTypeIds.push(radio.value);
  });

 // if (contentTypeIds.length > 0) {
 //   console.log("선택된 값:", contentTypeIds);
 //   contentType = contentTypeIds;
 //   search();
 // } else {
 //   console.log("라디오 버튼이 선택되지 않았습니다.");
  //}
};


var positions; // marker 배열.

/*
function makeList(data) {
  if (document.querySelector("table").getAttribute("id") != "none"){
	  document.querySelector("table").setAttribute("style", "display: ;");
  }
  
  let trips = data.response.body.items.item;
  if (trips) {
    let tripList = ``;
    positions = [];
    trips.forEach((area) => {
      tripList += `
      <tr onclick="moveCenter(${area.mapy}, ${area.mapx});">
        <td><img src="${area.firstimage}" width="100px"></td>
        <td>${area.title}</td>
        <td>${area.addr1} ${area.addr2}</td>
        <td>${area.mapy}</td>
        <td>${area.mapx}</td>
      </tr>
    `;

      let markerInfo = {
        title: area.title,
        addr: area.addr1 + area.addr2,
        img: area.firstimage,
        latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
      };
      positions.push(markerInfo);
    });
    document.getElementById("trip-list").innerHTML = tripList;
    displayMarker();
  } else {
    document.getElementById("trip-list").innerHTML = `
    <tr class="text-center">
      <td colspan="5">검색 결과가 없습니다.</td>
    </tr>
  `;
  }
}
*/


// 카카오지도
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
    level: 5, // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

function displayMarker() {
  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "../assets/img/marker1.png";
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

/*
function displayMarkerInfo(position) {
    let infoDiv = document.getElementById('marker-info');
    if (!infoDiv) {
        // marker-info 요소가 없을 경우 생성
        infoDiv = document.createElement('div');
        infoDiv.id = 'marker-info';
        document.body.appendChild(infoDiv);
    }

    let markerInfoHTML = `
        <div>
            <img src="${position.img}" alt="${position.title}" style="width: 100px;">
            <input type="hidden" id="content_id" value="${position.contentId}"/>
            <p><strong>관광지명:</strong> ${position.title}</p>
            <p><strong>주소:</strong> ${position.addr}</p>
            <p><strong>위도:</strong> ${position.latlng.getLat()}</p>
            <p><strong>경도:</strong> ${position.latlng.getLng()}</p>
            <button class="btn btn-outline-success add-btn" id="plan_add_btn">추가</button>
        </div>
    `;

    infoDiv.innerHTML = markerInfoHTML;

    // 추가 버튼에 클릭 이벤트 리스너 추가
    let addButton = document.querySelector('.add-btn');
    addButton.addEventListener('click', function() {
        // position 데이터를 JSON 형식으로 변환
        let data = JSON.stringify(position);

        // AJAX를 사용하여 서버로 요청 보내기
        let xhr = new XMLHttpRequest();
        xhr.open('POST', './trip?action=add', true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    // 요청 성공 시의 처리
                    console.log('관광지가 성공적으로 추가되었습니다.');
                } else {
                    // 요청 실패 시의 처리
                    console.error('요청 실패');
                }
            }
        };
        xhr.send(data);
    });
}
*/

function moveCenter(lat, lng) {
  map.setCenter(new kakao.maps.LatLng(lat, lng));
}
