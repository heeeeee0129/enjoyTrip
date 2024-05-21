<script setup>
import { watch, ref, onMounted } from "vue";
import { getAttractions, getAttraction } from "@/api/attraction";
import AOS from "aos";
import "aos/dist/aos.css";
import searchBar from "@/components/trip/item/TripSearchBar.vue";
import { useRouter } from "vue-router";
import { fetchSidos, fetchGuguns } from "@/api/getDistricts";
import { loadKakaoMapScript } from "@/utils/load-map";
import { fail } from "@/utils/error-handler";
import image from "@/assets/images.png";
import { getContentTypeName } from "@/utils/convert-content-type";
const attractions = ref([]);
const selectedSidoCode = ref(0); // 시도
const selectSidos = ref([]);
const selectGuguns = ref([]);
const selectedGugunCode = ref(0);
const positions = ref([]);
const router = useRouter();
const isModalOpen = ref(false); //
const selectedAttraction = ref({});

var map = null; // 지도는 ref사용하면 안됨
var markerCluster = null; // 마커 클러스터
var overlayCluster = null; // 오버레이 클러스터

// 카카오 맵 API 로드
onMounted(async () => {
  await loadKakaoMapScript();
  AOS.init();
  selectSidos.value = await fetchSidos();

  // Initialize the map
  map = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(37.500613, 127.036431),
    level: 7,
  });
});

const openDetailModal = async (contentId) => {
  await getAttraction(
    contentId,
    (response) => {
      selectedAttraction.value = response.data;
    },
    fail
  ).then(() => {
    map.panTo(
      new window.kakao.maps.LatLng(
        selectedAttraction.value.latitude,
        selectedAttraction.value.longitude
      )
    );
    isModalOpen.value = true;
  });
};
const closeModal = () => {
  isModalOpen.value = false;
};
watch(selectedSidoCode, async () => {
  selectedGugunCode.value = 0;
  selectGuguns.value = await fetchGuguns(selectedSidoCode.value);
});

// 카카오 맵 API 스크립트 로드

const handleSearch = async (queryString) => {
  try {
    const response = await getAttractions(queryString);
    // console.log("Response Data: ", response);

    if (response) {
      attractions.value = response;
      // console.log(attractions.value);
      updateMapMarkers(attractions.value);
      console.log(attractions.value);
    } else {
      console.error("Unexpected response structure:", response);
    }
  } catch (error) {
    console.error("Error during search:", error);
  }
};

const updateMapMarkers = (trips) => {
  positions.value = trips.map((trip) => ({
    title: trip.title,
    addr: `${trip.addr1} ${trip.addr2}`,
    img: trip.firstImage || "./default.png",
    latlng: new window.kakao.maps.LatLng(trip.latitude, trip.longitude),
    contentId: trip.contentId,
    contentTypeId: trip.contentTypeId,
  }));

  displayMarker();
  let minDistance = Number.MAX_VALUE;
  let nearestMarker = null;

  // 평균 Lat, Lng 계산
  let totalLat = 0;
  let totalLng = 0;
  positions.value.forEach((position) => {
    totalLat += position.latlng.getLat();
    totalLng += position.latlng.getLng();
  });
  const avgLat = totalLat / positions.value.length;
  const avgLng = totalLng / positions.value.length;

  // 가장 가까운 마커 찾기
  positions.value.forEach((position) => {
    const distance = getDistance(position.latlng, avgLat, avgLng);
    if (distance < minDistance) {
      minDistance = distance;
      nearestMarker = position.latlng;
    }
  });

  // 가장 가까운 마커를 중심으로 지도 설정
  map.setCenter(nearestMarker);
  map.setLevel(7);
};
const getDistance = (latlng, avgLat, avgLng) => {
  const latDiff = latlng.getLat() - avgLat;
  const lngDiff = latlng.getLng() - avgLng;
  return Math.sqrt(latDiff * latDiff + lngDiff * lngDiff);
};

const goToTripDetail = (contentId) => {
  // Vue Router를 이용하여 TripDetail 페이지로 이동하는 메소드
  router.push({
    name: "TripDetail",
    params: { contentId: contentId },
  });
};

// 클러스터링
const addCluster = (markers, idx) => {
  if (idx == 1) {
    if (!markerCluster) {
      markerCluster = new window.kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 3,
      });
    }
    markerCluster.addMarkers(markers);
  } else {
    if (!overlayCluster) {
      overlayCluster = new window.kakao.maps.MarkerClusterer({
        map: map,
        averageCenter: true,
        minLevel: 3,
      });
    }
    overlayCluster.addMarkers(markers);
  }
};

const displayMarker = () => {
  const markers = [];
  const overlays = [];
  const imageSize = new window.kakao.maps.Size(24, 35);
  // 기존 클러스터에 추가된 마커들 삭제
  if (markerCluster) {
    markerCluster.clear();
  }
  if (overlayCluster) {
    overlayCluster.clear();
  }

  positions.value.forEach((position) => {
    //마커설정
    const imageSrc = `./marker${position.contentTypeId}.png`; // 컨텐츠 타입별로 마커 이미지 다르게 해줘야함.
    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
    const marker = new window.kakao.maps.Marker({
      position: position.latlng,
      title: position.title,
      image: markerImage,
      clickable: true,
    });
    // 마커에 클릭이벤트를 등록합니다
    // 클릭 이벤트가 됐다 안됐다 한다...
    new window.kakao.maps.event.addListener(marker, "click", function () {
      map.panTo(position.latlng);
    });
    markers.push(marker);

    // 커스텀 오버레이의 content를 생성합니다
    const content = document.createElement("div");
    content.className =
      "flex items-center justify-between relative bottom-8 border border-solid border-gray-300 rounded-lg shadow-md px-3 py-1 bg-gray-50 bg-opacity-60";
    content.innerHTML = `
      <span class="block text-center text-black font-bold text-base py-1">${position.title}</span>
      <span class="py-auto ml-2">
        <svg class="w-5 h-5 text-blue-800" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
        </svg>
      </span>
    `;
    // 오버레이에 클릭 이벤트를 추가합니다
    content.addEventListener("click", () =>
      openDetailModal(position.contentId)
    );
    // 오버레이설정
    const customoverlay = new window.kakao.maps.CustomOverlay({
      position: position.latlng,
      content: content,
      yAnchor: 1,
      xAnchor: 0.5,
      range: 300,
    });
    overlays.push(customoverlay);
  });
  // 클러스터에 추가
  addCluster(markers, 1);
  addCluster(overlays, 2);
};
</script>

<template>
  <div class="p-10 mt-3 px-36">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder" id="search-header">
        <h2 class="mb-1">여행지 검색</h2>
      </div>
      <searchBar @search="handleSearch" class="mb-6" />

      <div
        id="map"
        class="mt-3 rounded shadow-sm p-10"
        style="width: 100%; height: 550px"></div>
      <!-- //map 영역 위에까지 -->
      <div class="row mt-5">
        <div class="col">
          <table class="table table-hover align-middle text-center border-b-0">
            <thead class="bg-sky-400">
              <tr>
                <th class="bg-sky-100" scope="col">대표 이미지</th>
                <th class="bg-sky-100" scope="col">관광지명</th>
                <th class="bg-sky-100" scope="col">주소</th>
                <th class="bg-sky-100" scope="col">관광지 유형</th>
                <th class="bg-sky-100" scope="col">전화번호</th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="attraction in attractions"
                :key="attraction.contentId"
                @click="goToTripDetail(attraction.contentId)"
                style="cursor: pointer">
                <td>
                  <img
                    :src="attraction.firstImage || image"
                    class="object-fill w-24 h-18 rounded-md" />
                </td>
                <td class="align-middle">{{ attraction.title }}</td>
                <td class="align-middle">
                  {{ attraction.addr1 }} {{ attraction.addr2 }}
                </td>
                <td class="align-middle">
                  {{ getContentTypeName(attraction.contentTypeId) }}
                </td>
                <td class="align-middle">{{ attraction.tel || "-" }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
  <div v-if="isModalOpen" class="fixed z-10 inset-0 overflow-y-auto">
    <div
      class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
      <div
        class="fixed inset-0 transition-opacity"
        aria-hidden="true"
        @click="closeModal">
        <div class="absolute inset-0 bg-gray-500 opacity-30"></div>
      </div>
      <span
        class="hidden sm:inline-block sm:align-middle sm:h-screen"
        aria-hidden="true"
        >&#8203;</span
      >
      <div
        data-aos="fade-up"
        data-aos-duration="500"
        class="inline-block align-center bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-2xl sm:w-full">
        <div class="bg-white px-6 pt-5 pb-4 sm:p-6 sm:pb-4">
          <div class="sm:flex sm:items-start">
            <div class="text-center sm:mt-0 sm:text-left">
              <h3 class="text-2xl leading-6 font-bold text-gray-900 my-6">
                {{ selectedAttraction.title }}
              </h3>
              <div class="mt-2">
                <p class="text-sm text-gray-500 px-5">
                  주소: {{ selectedAttraction.addr1 }}
                  {{ selectedAttraction.addr2 }}
                </p>
                <p
                  v-if="selectedAttraction.tel"
                  class="text-sm text-gray-500 px-5">
                  전화번호: {{ selectedAttraction.tel }}
                </p>
                <p class="text-sm text-gray-500 px-5">
                  우편번호: {{ selectedAttraction.zipcode }}
                </p>
                <p class="text-sm text-gray-500 px-5">
                  설명: {{ selectedAttraction.overview }}
                </p>
                <img
                  :src="
                    selectedAttraction.firstImage
                      ? selectedAttraction.firstImage
                      : image
                  "
                  alt="Image"
                  class="w-full h-64 object-cover mt-4" />
              </div>
            </div>
          </div>
        </div>
        <div class="px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
          <button
            @click="closeModal"
            class="w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:w-auto sm:text-sm">
            닫기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
#customoverlay {
  position: relative;
  bottom: 85px;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  float: left;
}
#customoverlay:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
#customoverlay a {
  display: block;
  text-decoration: none;
  color: #000;
  text-align: center;
  border-radius: 6px;
  font-size: 14px;
  font-weight: bold;
  overflow: hidden;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
#customoverlay .title {
  display: block;
  text-align: center;
  background: #fff;
  margin-right: 35px;
  padding: 10px 15px;
  font-size: 14px;
  font-weight: bold;
}
#customoverlay:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: -12px;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.table-hover tbody tr:hover {
  background-color: #f1f1f1;
}
/* 
.table-dark th {
  background-color: #c7e3ff;
  color: white;
} */
</style>
