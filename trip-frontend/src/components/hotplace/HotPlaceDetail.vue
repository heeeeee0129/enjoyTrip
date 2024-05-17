<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/hotplace.js";
import { useUserStore } from "@/stores/index";
import { loadKakaoMapScript } from "@/utils/load-map";
import dayjs from "dayjs";
import Swal from "sweetalert2";

const userStore = useUserStore();
const route = useRoute();
const router = useRouter();

const { hotNo } = route.params;

const hotplace = ref({}); //글정보
const ImgPath = ref(""); //이미지경로
var map = null; //지도

onMounted(async () => {
  // console.log("애가 왜 호출돼 1?");
  await loadKakaoMapScript();
  detailArticle();
});

function getImageUrl(folder, name) {
  console.log(new URL(`./upload/${folder}/${name}`, import.meta.url));
  return new URL(`./upload/${folder}/${name}`, import.meta.url);
}

function moveList() {
  router.push({ name: "HotPlaceList" });
}

function moveModify() {
  router.push({ name: "HotPlaceModify", params: { hotNo } });
}

const initmap = (lat, lng) => {
  // 지도 초기상태 설정
  map = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(lat, lng),
    level: 5,
  });

  //마커 생성
  const markerPosition = new window.kakao.maps.LatLng(lat, lng);
  const imageSrc = "/marker1.png";
  const imageSize = new window.kakao.maps.Size(24, 35);
  const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
  const marker = new window.kakao.maps.Marker({
    map: map,
    position: markerPosition,
    image: markerImage,
  });
  marker.setMap(map);

  // 지오코딩
  // 좌표를 주소로 변환하는 함수
  const reverseGeocoding = (res, status) => {
    // 통신 상태 확인

    let content = `<div class="flex items-center justify-between relative bottom-10 border border-solid border-gray-300 rounded-lg shadow-md px-3 py-1 bg-white bg-opacity-60"">
                    <div class="flex items-center">
                        <img src="${getImageUrl(
                          hotplace.value.fileInfo.saveFolder,
                          hotplace.value.fileInfo.saveFile
                        )}" alt="" class="w-10 h-10 mr-2">
                        <span class="block text-center text-black font-bold text-base py-1">지번 주소: `;

    if (status === window.kakao.maps.services.Status.OK) {
      // 지번 주소 가져오기
      content += `${res[0].address.address_name}`;
    } else if (status == window.kakao.maps.services.Status.ZERO_RESULT) {
      // 반환된 주소값이 없는 경우
      content += `데이터없음`;
    } else {
      // 에러
      alert("주소 변환 실패");
    }
    content += `</span>
                        <span class="py-auto ml-2">
                            <svg class="w-5 h-5 text-blue-800 " fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
                            </svg>
                        </span>
                    </div>
                </div>`;

    // 커스텀 오버레이를 생성합니다
    new window.kakao.maps.CustomOverlay({
      map: map,
      position: markerPosition,
      content: content,
      yAnchor: 1,
    });
  };
  const geocoder = new window.kakao.maps.services.Geocoder();
  geocoder.coord2Address(lng, lat, reverseGeocoding);
};

const detailArticle = async () => {
  const success = (response) => {
    hotplace.value = response.data;
    ImgPath.value = getImageUrl(
      hotplace.value.fileInfo.saveFolder,
      hotplace.value.fileInfo.saveFile
    );

    initmap(hotplace.value.latitude, hotplace.value.longitude);
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getArticle(hotNo, success, fail);
};

const onDeleteArticle = async () => {
  const success = () => {
    // Swal.fire({
    //   title: "성공!",
    //   text: "글이 삭제되었습니다.",
    //   icon: "success",
    //   confirmButtonText: "OK",
    // }).then(() => {
    //   moveList();
    // });
    alert("글이 삭제되었습니다.");
    moveList();
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await deleteArticle(hotNo, success, fail);
};

const confirmDelete = () => {
  Swal.fire({
    title: "정말로 삭제 하시겠습니까?",
    text: "다시 되돌릴 수 없습니다. 신중하세요.",
    icon: "question",

    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
    confirmButtonColor: "#3085d6", // confirm 버튼 색깔 지정
    cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
    confirmButtonText: "삭제", // confirm 버튼 텍스트 지정
    cancelButtonText: "취소", // cancel 버튼 텍스트 지정
    reverseButtons: true, // 버튼 순서 거꾸로
  }).then((result) => {
    // 만약 Promise리턴을 받으면,
    if (result.isConfirmed) {
      // 만약 모달창에서 confirm 버튼을 눌렀다면
      onDeleteArticle();
    }
  });
};

const searchOnNaver = (title) => {
  const naverSearchLink = `https://search.naver.com/search.naver?query=${encodeURIComponent(
    title
  )}`;
  window.open(naverSearchLink, "_blank");
};

const kakaoMapLink = (title, latitude, longitude) => {
  const kakaoMapLink = `https://map.kakao.com/link/to/${encodeURIComponent(
    title
  )},${latitude},${longitude}`;
  window.open(kakaoMapLink, "_blank");
};

const formatDate = (dateString) => {
  return dayjs(dateString).format("YYYY-MM-DD");
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="card my-3 shadow-sm">
          <div class="card-body">
            <h2 class="card-title py-3 text-center">핫플레이스 상세보기</h2>
          </div>
          <img :src="ImgPath" alt="이미지가 없음" />
          <div class="container-fluid mt-5">
            <div class="row">
              <p class="text-end">조회수: {{ hotplace.hit }}</p>
              <!-- 왼쪽 반은 지도 -->
              <div
                class="col-md-6 mb-4 h-[90%] rounded shadow p-3 d-flex flex-column align-items-center">
                <div
                  id="map"
                  class="mt-3"
                  style="width: 100%; height: 550px"></div>
                <!-- Search Button -->
                <button
                  class="btn btn-success mt-3 w-75"
                  @click="searchOnNaver(hotplace.placeName)">
                  네이버 검색
                </button>
                <!-- Directions Button -->
                <button
                  class="btn btn-warning mt-3 w-75"
                  @click="
                    kakaoMapLink(
                      hotplace.placeName,
                      hotplace.latitude,
                      hotplace.longitude
                    )
                  ">
                  카카오맵 길찾기
                </button>
              </div>

              <!-- 오른쪽 반은 글작성 또는 글수정 폼 -->
              <div class="col-md-6 mb-4">
                <form class="p-4 rounded-lg shadow bg-light">
                  <div class="mb-3">
                    <label for="userName" class="form-label">작성자:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.userName"
                      readonly />
                  </div>
                  <div class="mb-3">
                    <label for="placeName" class="form-label">장소 이름:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.placeName"
                      readonly />
                  </div>
                  <div class="mb-3">
                    <label for="visitedDate" class="form-label"
                      >다녀온 날짜:</label
                    >
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="formatDate(hotplace.registerTime)"
                      readonly />
                  </div>
                  <div class="mb-3">
                    <label for="graveType" class="form-label">장소 유형:</label>
                    <input
                      type="text"
                      class="form-control"
                      :placeholder="hotplace.category"
                      readonly />
                  </div>
                  <div class="mb-3">
                    <label for="introduction" class="form-label"
                      >핫플레이스 소개:</label
                    >
                    <textarea
                      class="form-control"
                      :placeholder="hotplace.content"
                      rows="5"
                      readonly></textarea>
                  </div>
                  <div class="text-center">
                    <button
                      type="button"
                      class="btn btn-outline-primary rounded-pill px-4"
                      @click="moveModify"
                      v-if="userStore.member.id === hotplace.userId">
                      수정
                    </button>
                    <button
                      type="button"
                      class="btn btn-outline-danger rounded-pill px-4"
                      @click="confirmDelete"
                      v-if="userStore.member.id === hotplace.userId">
                      삭제
                    </button>
                    <button
                      type="button"
                      class="btn btn-outline-success rounded-pill px-4"
                      @click="moveList">
                      목록으로 이동...
                    </button>
                  </div>
                  <div class="text-center mt-3">
                    <button
                      type="button"
                      class="btn btn-outline-info rounded-pill px-4">
                      좋아요 ❤️ ( 5 )
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
