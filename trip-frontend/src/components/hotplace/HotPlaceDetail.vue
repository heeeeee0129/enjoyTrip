<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/hotplace.js";
import { useUserStore } from "@/stores/index";
import dayjs from "dayjs";
import Swal from "sweetalert2";

const userStore = useUserStore();
const { VITE_KAKAO_API_KEY } = import.meta.env; // 환경 변수에서 API 키 가져오기

const route = useRoute();
const router = useRouter();

const { hotNo } = route.params;

const hotplace = ref({});
const ImgPath = ref("");
const map = ref(null);

onMounted(async () => {
  // console.log("애가 왜 호출돼 1?");
  await loadKakaoMapScript();
  map.value = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(37.500613, 127.036431),
    level: 5,
  });
  detailArticle();
});
const loadKakaoMapScript = () => {
  return new Promise((resolve, reject) => {
    if (typeof window.kakao !== "undefined") {
      resolve();
      return;
    }

    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_API_KEY}&libraries=services,clusterer,drawing`;
    script.onload = () => {
      window.kakao.maps.load(() => {
        resolve();
      });
    };
    script.onerror = reject;
    document.head.appendChild(script);
  });
};
function getImageUrl(folder, name) {
  return new URL(`/src/assets/upload/${folder}/${name}`, import.meta.url);
}

function moveList() {
  router.push({ name: "HotPlaceList" });
}

function moveModify() {
  router.push({ name: "HotPlaceModify", params: { hotNo } });
}

const detailArticle = async () => {
  const success = (response) => {
    hotplace.value = response.data;
    ImgPath.value = getImageUrl(
      hotplace.value.fileInfo.saveFolder,
      hotplace.value.fileInfo.saveFile
    );
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
              <div class="col-md-6 mb-4 h-[90%] rounded shadow p-3">
                <div
                  id="map"
                  class="mt-3"
                  style="width: 100%; height: 550px"></div>
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
