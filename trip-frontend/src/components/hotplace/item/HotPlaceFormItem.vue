<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores";
import { getArticle, writeArticle, modifyArticle } from "@/api/hotplace.js";
import { loadKakaoMapScript } from "@/utils/load-map";
import Swal from "sweetalert2";

const store = useUserStore(); // Vuex store 인스턴스 가져오기
const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

var map = null;

const hotplace = ref({
  hotNo: 0,
  userId: store.member.id,
  userName: "",
  placeName: "",
  category: "",
  content: "",
  hit: 0,
  registerTime: "",
  latitude: 0,
  longitude: 0,
  fileInfo: null,
});

const initmap = (lat, lng) => {
  // 지도 세팅
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

  // 지도 클릭 이벤트
  new window.kakao.maps.event.addListener(map, "click", function (mouseEvent) {
    // 클릭한 위도, 경도 정보를 가져옵니다
    let latlng = mouseEvent.latLng;
    marker.setPosition(latlng);

    hotplace.value.latitude = latlng.getLat();
    hotplace.value.longitude = latlng.getLng();
  });
};

onMounted(async () => {
  // console.log("애가 왜 호출됨 ? 2");
  // 지도 로딩
  await loadKakaoMapScript();

  if (props.type === "modify") {
    setHotPlace();
  } else {
    initmap(37.500613, 127.036431); // 초기값
  }
});

const setHotPlace = async () => {
  let { hotNo } = route.params;

  const success = (response) => {
    hotplace.value = response.data;
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

const handleFileChange = (event) => {
  const files = event.target.files;
  if (files.length > 0) {
    hotplace.value.fileInfo = files[0];
  }
};

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
const dateErrMsg = ref("");
const categoryErrMsg = ref("");
const fileErrMsg = ref("");
const latlngErrmsg = ref("");
watch(
  () => hotplace.value.placeName,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "장소 이름을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => hotplace.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "핫플레이스 소개를 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => hotplace.value.registerTime,
  (value) => {
    if (!value) {
      dateErrMsg.value = "날짜를 선택해 주세요!!!";
    } else {
      dateErrMsg.value = "";
    }
  },
  { immediate: true }
);
watch(
  () => hotplace.value.category,
  (value) => {
    if (!value) {
      categoryErrMsg.value = "카테고리를 선택해 주세요!!!";
    } else {
      categoryErrMsg.value = "";
    }
  },
  { immediate: true }
);
watch(
  () => hotplace.value.fileInfo,
  (value) => {
    if (value === 0) {
      fileErrMsg.value = "파일을 선택해 주세요!!!";
    } else {
      fileErrMsg.value = "";
    }
  },
  { immediate: true }
);
watch(
  () => hotplace.value.latitude,
  (value) => {
    if (!value) {
      latlngErrmsg.value = "마커를 찍어주세요";
    } else {
      latlngErrmsg.value = "";
    }
  },
  { immediate: true }
);

function onSubmit() {
  if (subjectErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: subjectErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (contentErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: contentErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (dateErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: dateErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (categoryErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: categoryErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (fileErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: fileErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (latlngErrmsg.value) {
    Swal.fire({
      title: "실패!",
      text: latlngErrmsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else {
    props.type === "regist" ? registArticle() : updateArticle();
  }
}

function moveList() {
  // router.replace({ name: "HotPlaceList" });
  router.go(-1);
}

function moveDetail() {
  router.replace({
    name: "HotPlaceDetail",
    params: {
      hotNo: hotplace.value.hotNo,
    },
  });
}

const registArticle = async () => {
  const success = (response) => {
    if (response.data === 1) {
      Swal.fire({
        title: "성공!",
        text: "글이 작성되었습니다",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        moveList();
      });
    } else if (response.data === 2) {
      Swal.fire({
        title: "실패!",
        text: "파일형식이 잘못되었습니다.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    } else {
      Swal.fire({
        title: "실패!",
        text: "비속어가 포함되어있습니다. 다시 확인해주세요.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    }
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생했습니다",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await writeArticle(hotplace.value, success, fail);
};

const updateArticle = async () => {
  const success = (response) => {
    if (response.data === 1) {
      // Swal.fire({
      //   title: "성공!",
      //   text: "글이 수정되었습니다",
      //   icon: "success",
      //   confirmButtonText: "OK",
      // }).then(() => {
      //   moveDetail();
      // });
      alert("글이 수정되었습니다.");
      moveDetail();
    } else if (response.data === 2) {
      Swal.fire({
        title: "실패!",
        text: "파일형식이 잘못되었습니다.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    } else {
      Swal.fire({
        title: "실패!",
        text: "비속어가 포함되어있습니다. 다시 확인해주세요.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    }
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생했습니다",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await modifyArticle(hotplace.value, success, fail);
};
</script>

<template>
  <div class="row flex">
    <!-- 왼쪽 반은 지도 -->
    <div class="col-md-6 mb-4 h-[500px] rounded-xl p-3 relative">
      <div class="marker-guide text-center text-gray-600">
        원하는 위치에 마커를 찍어주세요
      </div>
      <div
        id="map"
        class="mt-3 rounded-xl"
        style="width: 100%; height: 550px"></div>
    </div>

    <!-- 오른쪽 반은 글작성 또는 글수정 폼 -->
    <div class="col-md-6 mb-4">
      <form
        @submit.prevent="onSubmit"
        class="p-4 h-[600px] rounded-xl"
        enctype="multipart/form-data">
        <div class="mb-3">
          <label for="placeImage" class="form-label"
            >사진 등록(jpg, jpeg, png 파일만 가능):</label
          >
          <input
            type="file"
            class="form-control"
            @change="handleFileChange"
            accept="image/*" />
        </div>
        <div class="mb-3">
          <label for="placeName" class="form-label">장소 이름:</label>
          <input
            type="text"
            class="form-control"
            v-model="hotplace.placeName"
            placeholder="장소 이름을 입력하세요" />
        </div>
        <div class="mb-3">
          <label for="visitedDate" class="form-label">다녀온 날짜:</label>
          <input
            type="date"
            class="form-control"
            v-model="hotplace.registerTime" />
        </div>
        <div class="mb-3">
          <label for="graveType" class="form-label">장소 유형:</label>
          <select class="form-select" v-model="hotplace.category">
            <option value="" disabled selected hidden>유형 선택</option>
            <option value="관광지">관광지</option>
            <option value="문화시설">문화시설</option>
            <option value="행사/공연/축제">행사/공연/축제</option>
            <option value="여행코스">여행코스</option>
            <option value="레포츠">레포츠</option>
            <option value="숙박">숙박</option>
            <option value="쇼핑">쇼핑</option>
            <option value="음식점">음식점</option>
          </select>
        </div>
        <div class="mb-3">
          <label for="introduction" class="form-label">핫플레이스 소개:</label>
          <textarea
            class="form-control h-[200px]"
            v-model="hotplace.content"
            rows="5"
            placeholder="핫플레이스를 소개하세요"></textarea>
        </div>
      </form>
    </div>
    <div class="text-center mt-4">
      <button
        type="submit"
        class="btn btn-outline-primary rounded-pill px-4 mx-12">
        {{ type === "regist" ? "글작성" : "글수정" }}
      </button>
      <button
        type="button"
        class="btn btn-outline-secondary rounded-pill px-4 mx-12"
        @click="moveList">
        뒤로가기
      </button>
    </div>
  </div>
</template>
<style scoped></style>
