<script setup>
import { watch, ref, onMounted } from "vue";
import { getAttractions } from "@/api/attraction";
import { useRouter } from "vue-router";
import AOS from "aos";
import "aos/dist/aos.css";
import searchBar from "@/components/trip/item/TripSearchBar.vue";

import { fetchSidos, fetchGuguns } from "@/api/getDistricts";
import { loadKakaoMapScript } from "@/utils/load-map";
const attractions = ref([]);
const selectedSidoCode = ref(0); // 시도
const selectSidos = ref([]);
const selectGuguns = ref([]);
const selectedGugunCode = ref(0);
const positions = ref([]);
const map = ref(null);
const router = useRouter();

// 카카오 맵 API 로드
onMounted(async () => {
  await loadKakaoMapScript();
  AOS.init();
  selectSidos.value = await fetchSidos();

  // Initialize the map
  map.value = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(37.500613, 127.036431),
    level: 5,
  });
});

watch(selectedSidoCode, async () => {
  selectedGugunCode.value = 0;
  selectGuguns.value = await fetchGuguns(selectedSidoCode.value);
});

// 카카오 맵 API 스크립트 로드

const handleSearch = async (queryString) => {
  try {
    const response = await getAttractions(queryString);
    console.log("Response Data: ", response);

    if (response) {
      attractions.value = response;
      console.log(attractions.value);
      updateMapMarkers(attractions.value);
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
  map.value.setCenter(nearestMarker);
  map.value.setLevel(7);
};
const getDistance = (latlng, avgLat, avgLng) => {
  const latDiff = latlng.getLat() - avgLat;
  const lngDiff = latlng.getLng() - avgLng;
  return Math.sqrt(latDiff * latDiff + lngDiff * lngDiff);
};
const displayMarker = () => {
  const imageSrc = "./marker1.png";
  const imageSize = new window.kakao.maps.Size(24, 35);
  positions.value.forEach((position) => {
    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
    const marker = new window.kakao.maps.Marker({
      map: map.value,
      position: position.latlng,
      title: position.title,
      image: markerImage,
      clickable: true,
    });
    marker.setMap(map.value);
    const content = `<div class="flex items-center justify-between relative bottom-8 border border-solid
     border-gray-300 rounded-lg shadow-md px-3 py-1 bg-gray-50 bg-opacity-60" onClick="console.log(position.title)">
    <span class="block text-center text-black font-bold text-base py-1">${position.title}</span>
    <span class=" py-auto ml-2">
      <svg class="w-5 h-5 text-blue-800 " fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path>
      </svg>
    </span>
</div>`;
    const goToTripDetail = (contentId) => {
      // Vue Router를 이용하여 TripDetail 페이지로 이동하는 메소드
      this.$router.push({
        name: "TripDetail",
        params: { contentId: contentId },
      });
    };
    const customoverlay = new window.kakao.maps.CustomOverlay({
      map: map.value,
      position: position.latlng,
      content: content,
      yAnchor: 1,
      xAnchor: 0.5,
      range: 300,
    });
  });
};
</script>

<template>
  <div class="p-10 mt-3">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder" id="search-header">
        <h2 class="">여행지 검색</h2>
      </div>
      <searchBar @search="handleSearch" />

      <div
        id="map"
        class="mt-3 rounded shadow-sm p-10"
        style="width: 100%; height: 550px"></div>
      <!-- //map 영역 위에까지 -->
      <div class="row" style="margin-top: 50px">
        <table class="table table-striped">
          <thead>
            <tr>
              <th>대표이미지</th>
              <th>관광지명</th>
              <th>주소</th>
              <th>위도</th>
              <th>경도</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="attraction in attractions"
              :key="attraction.contentId"
              @click="redirectToDetail(attraction.contentId)">
              <td>
                <img
                  :src="attraction.firstImage || 'default.png'"
                  width="100px" />
              </td>
              <td>{{ attraction.title }}</td>
              <td>{{ attraction.addr1 }} {{ attraction.addr2 }}</td>
              <td>{{ attraction.latitude }}</td>
              <td>{{ attraction.longitude }}</td>
            </tr>
          </tbody>
        </table>
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
</style>
