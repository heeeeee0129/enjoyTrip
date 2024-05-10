// index page 로딩 후 전국의 시도 설정.

const BASE_URL = `https://apis.data.go.kr/B551011/KorService1/`;
const BASE_QUERY = `serviceKey=${serviceKey}&numOfRows=50&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;


const user = localStorage.getItem("user");
const logoutBtn = document.querySelector("#logout");
const myPageBtn = document.querySelector("#mypage");
const loginBtn = document.querySelector("#login");
const joinBtn = document.querySelector("#join");

