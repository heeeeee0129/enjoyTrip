<script setup>
import { watch, ref, onMounted } from "vue";
import { getAttractions } from "@/api/attraction";
import AOS from "aos";
import "aos/dist/aos.css";
import searchBar from "@/components/trip/item/TripSearchBar.vue";

import { fetchSidos, fetchGuguns } from "@/api/getDistricts";
const { VITE_KAKAO_API_KEY } = import.meta.env; // 환경 변수에서 API 키 가져오기

const attractions = ref([]);
const selectedSidoCode = ref(0); // 시도
const selectSidos = ref([]);
const selectGuguns = ref([]);
const selectedGugunCode = ref(0);
const positions = ref([]);
const map = ref(null);

// 카카오 맵 API 로드
onMounted(async () => {
  await loadKakaoMapScript();
  AOS.init();
  // Fetch sidos and set them
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
const handleSearch = async (queryString) => {
  try {
    const response = await getAttractions(queryString);
    console.log("Response Data: ", response);

    if (response && response.response && response.response.body) {
      attractions.value = response;
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
    img: trip.firstimage || "default.png",
    latlng: new window.kakao.maps.LatLng(trip.mapy, trip.mapx),
  }));

  displayMarker();
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

    window.kakao.maps.event.addListener(marker, "click", function () {
      displayMarkerInfo(position);
    });

    marker.setMap(map.value);
  });

  map.value.setCenter(positions.value[0].latlng);
};

const displayMarkerInfo = (position) => {
  // Implement the info display logic here
  console.log("Display marker info:", position);
};
</script>

<template>
  <div class="p-10 mt-6">
    <div class="row px-10">
      <div id="temp"></div>
      <div class="mt-3 text-center fw-bolder" id="search-header">
        <h2 class="mb-3">여행지 검색</h2>
      </div>
      <searchBar @search="handleSearch" />

      <div
        id="map"
        class="mt-3"
        style="width: 100%; height: 500px"
        data-aos="fade-up"></div>
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
                  :src="attraction.firstimage || 'default.png'"
                  width="100px" />
              </td>
              <td>{{ attraction.title }}</td>
              <td>{{ attraction.addr1 }} {{ attraction.addr2 }}</td>
              <td>{{ attraction.mapy }}</td>
              <td>{{ attraction.mapx }}</td>
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

#temp {
  height: 60px;
}
</style>
