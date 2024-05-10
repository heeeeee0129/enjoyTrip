// 1. 회원가입 버튼 누르면 페이지 이동
// 2. 회원가입 페이지에서 입력받은 정보 유효성 검사하고
// 3. localStorage에 저장 후 원래 페이지로 돌아오기
// 4. main 페이지 onload할 때 로그인 확인.

// 지역 시도, 구군 받아오기
// const BASE_URL = `https://apis.data.go.kr/B551011/KorService1/`;
// const BASE_QUERY = `serviceKey=${serviceKey}&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;

const areaUrl =
  "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
  serviceKey +
  "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

fetch(areaUrl, { method: "GET" })
  .then((response) => response.json())
  .then((data) => makeOption(data));

let areaCode = 0;
let sigunguCode = 0;
let contentType = [];

let sido = "";
let gugun = "";

function makeOption(data) {
  let areas = data.response.body.items.item;
  console.log(data.response.body.items);
  let sel = document.getElementById("sido");
  areas.forEach((area) => {
    let opt = document.createElement("option");
    opt.setAttribute("value", area.code);
    opt.setAttribute("areaName", area.name);
    opt.appendChild(document.createTextNode(area.name));
    sel.appendChild(opt);
  });
  sel.addEventListener("change", (e) => {
    let selectedValue = e.target.value;
    let selectedOptionName = "";
    for (let i = 0; i < sel.options.length; i++) {
      if (sel.options[i].value === selectedValue) {
        selectedOptionName = sel.options[i].text;
        break;
      }
    }

    console.log(selectedOptionName);
    sido = selectedOptionName;
    areaCode = sel.value;

    if (areaCode) {
      makeSigungu();
      // search();
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
      let sel = document.getElementById("gugun");
      for (let i = sel.options.length - 1; i >= 1; i--) {
        sel.remove(i);
      }

      sigungu.forEach((area) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", area.code);
        opt.setAttribute("areaName", area.name);
        opt.appendChild(document.createTextNode(area.name));
        sel.appendChild(opt);
      });

      sel.addEventListener("change", (e) => {
        let selectedValue = e.target.value;
        let selectedOptionName = "";
        for (let i = 0; i < sel.options.length; i++) {
          if (sel.options[i].value === selectedValue) {
            selectedOptionName = sel.options[i].text;
            break;
          }
        }
        console.log(selectedOptionName);
        gugun = selectedOptionName;

        sigunguCode = sel.value;
        if (sigunguCode) {
          // search();
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
};

