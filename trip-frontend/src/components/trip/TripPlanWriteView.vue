<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { loadKakaoMapScript } from "@/utils/load-map";
import { writeTripPlan } from "@/api/tripplan";
import { getAttractions, getTripAttraction } from "@/api/attraction";
import AOS from "aos";
import "aos/dist/aos.css";
import { useUserStore } from "@/stores";

const store = useUserStore();
const userData = store.member;

const router = useRouter();
const title = ref("");
const content = ref("");
const startDate = ref("");
const endDate = ref("");
const locations = ref([]);
const keyword = ref("");
let map = null;
let markerCluster = null; // 마커 클러스터
let overlayCluster = null;
const positions = ref([]);
const isModalOpen = ref(false);
const selectedAttraction = ref({});
const attractions = ref([]);

const submitForm = async () => {
  const tripPlan = {
    id: 0,
    title: title.value,
    content: content.value,
    startDate: startDate.value,
    endDate: endDate.value,
    userId: userData.id, // 실제 로그인된 사용자 ID를 사용하세요.
    userName: userData.name, // 실제 로그인된 사용자 이름을 사용하세요.
    createdAt: new Date().toISOString(), // 현재 시간을 생성 일시로 설정합니다.
    tripLocations: locations.value.map((location, index) => ({
      id: location.id,
      routeId: 0, // 경로 ID는 0으로 임시 설정합니다.
      memo: location.content,
      idx: index,
      lat: location.latitude,
      lng: location.longitude,
      contentId: location.id,
      title: location.title,
    })),
  };

  try {
    await writeTripPlan(
      tripPlan,
      (response) => {
        console.log("Trip plan created successfully:", response);
        // 성공 시 페이지를 다른 곳으로 이동하거나 알림을 표시할 수 있습니다.
        alert("여행 계획이 성공적으로 등록되었습니다.");
        router.push({ name: "plan" }); // 예: 여행 계획 목록 페이지로 이동
      },
      (error) => {
        console.error("Failed to create trip plan:", error);
        alert("여행 계획 등록에 실패했습니다.");
      }
    );
  } catch (error) {
    console.error("Error during trip plan submission:", error);
    alert("여행 계획 등록 중 오류가 발생했습니다.");
  }
};

onMounted(async () => {
  await loadKakaoMapScript(map);
  AOS.init();
  map = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  });
});
const handleSearch = async () => {
  const queryString = keyword.value
    ? `&sido=0&gugun=0&keyword=${keyword.value}`
    : "&sido=0&gugun=0";

  try {
    const response = await getAttractions(queryString);
    console.log("Response Data: ", response);

    if (response) {
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
  console.log("trips", trips);
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
  map.setLevel(12);
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
    const imageSrc = `/marker${position.contentTypeId}.png`; // 컨텐츠 타입별로 마커 이미지 다르게 해줘야함.
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
    content.addEventListener("click", () => goToTripDetail(position.contentId));

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
  addCluster(markers, 1);
  addCluster(overlays, 2);
};
const goToTripDetail = async (contentId) => {
  // contentId에 해당하는 여행지 정보를 가져오는 메소드
  const attraction = await getTripAttraction(contentId);

  if (attraction) {
    selectedAttraction.value = attraction;
    isModalOpen.value = true;
  }
};
const closeModal = () => {
  isModalOpen.value = false;
};
const addLocation = () => {
  console.log(selectedAttraction.value);
  const location = {
    id: selectedAttraction.value.contentId,
    title: selectedAttraction.value.title,
    content: selectedAttraction.value.memo,
    img: selectedAttraction.value.firstImage,
    idx: locations.value.length,
    latitude: selectedAttraction.value.latitude,
    longitude: selectedAttraction.value.longitude,
  };
  locations.value.push(location);
  closeModal();
};
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
const getDistance = (latlng, avgLat, avgLng) => {
  const latDiff = latlng.getLat() - avgLat;
  const lngDiff = latlng.getLng() - avgLng;
  return Math.sqrt(latDiff * latDiff + lngDiff * lngDiff);
};

const goBack = () => {
  router.go(-1);
};

const dragStart = (event, index) => {
  event.dataTransfer.effectAllowed = "move";
  event.dataTransfer.setData("index", index);
  event.target.classList.add("dragging");
};

const dragEnd = (event) => {
  event.target.classList.remove("dragging");
};

const dragOver = (event) => {
  event.preventDefault();
};

const drop = (event, index) => {
  event.preventDefault();
  const draggedIndex = event.dataTransfer.getData("index");
  const draggedItem = locations.value.splice(draggedIndex, 1)[0];
  locations.value.splice(index, 0, draggedItem);
  updateLocationIndexes();
};
const updateLocationIndexes = () => {
  locations.value.forEach((location, index) => {
    location.idx = index;
  });
};
</script>

<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">여행 계획 추가</h2>
      </div>
      <div class="flex">
        <div class="flex-grow-0">
          <button
            @click="goBack"
            class="hover:shadow-lg bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">
            뒤로 가기
          </button>
        </div>

        <div class="flex-grow-0 ml-auto">
          <button
            class="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded"
            @click="submitForm">
            여행 계획 등록하기
          </button>
        </div>
      </div>
      <hr class="border-gray-400" />
    </div>
    <div class="flex mx-24">
      <!-- 왼쪽에는 map 지도를 띄울 공간 -->
      <div class="w-1/2 flex flex-col">
        <!-- 검색 바 --><span class="text-base pl-2">여행지 검색</span>
        <div class="py-2 my-2 bg-white rounded-t-lg flex items-center">
          <input
            @keydown.enter="handleSearch"
            id="keyword"
            name="keyword"
            v-model="keyword"
            type="text"
            placeholder="장소 검색..."
            class="flex-grow py-2 px-4 border border-gray-300 rounded-l-md focus:outline-none focus:ring-blue-500 focus:border-blue-500" />
          <div
            @click="handleSearch"
            class="bg-blue-300 py-2 px-4 rounded-r-md border border-gray-300">
            검색
          </div>
        </div>
        <!-- 지도 -->
        <div id="map" class="flex-grow rounded-lg"></div>
      </div>

      <!-- 오른쪽에는 여행 계획 추가 폼 -->
      <div class="w-1/2 pl-6 mt-20">
        <form>
          <div class="mb-4">
            <label for="title" class="block text-sm font-medium text-gray-700"
              >여행 제목</label
            >
            <input
              type="text"
              id="title"
              name="title"
              v-model="title"
              class="px-2 my-2 focus:ring-blue-500 py-2 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" />
          </div>
          <div class="mb-4">
            <label for="content" class="block text-sm font-medium text-gray-700"
              >여행 설명</label
            >
            <textarea
              id="content"
              name="content"
              v-model="content"
              rows="3"
              class="px-2 py-2 mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"></textarea>
          </div>
          <div class="mb-4">
            <label
              for="start_date"
              class="block text-sm font-medium text-gray-700"
              >출발일</label
            >
            <input
              type="date"
              id="start_date"
              name="start_date"
              v-model="startDate"
              class="px-2 my-2 focus:ring-blue-500 py-2 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" />
          </div>
          <div class="mb-4">
            <label
              for="end_date"
              class="block text-sm font-medium text-gray-700"
              >도착일</label
            >
            <input
              type="date"
              id="end_date"
              name="end_date"
              v-model="endDate"
              class="px-2 my-2 focus:ring-blue-500 py-2 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" />
          </div>
        </form>
      </div>
    </div>
    <!-- 여행지 목록 -->
    <div class="mx-24">
      <h3 class="text-2xl font-semibold mt-24">TIMELINE</h3>
      <hr class="border-gray-400" />
      <div class="mt-10 grid grid-cols-4 gap-4 p-4 px-24">
        <div
          v-for="(location, index) in locations"
          draggable="true"
          @dragstart="(event) => dragStart(event, index)"
          @dragend="dragEnd"
          @dragover="dragOver"
          @drop="(event) => drop(event, index)"
          :key="location.contentId"
          class="p-3 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"
          data-aos="fade-up"
          data-aos-duration="1000">
          <div class="rounded-lg overflow-hidden">
            <div class="relative">
              <img
                :src="location.img ? location.img : 'public/attraction.jpg'"
                alt="location.title"
                class="w-full h-64 object-cover" />
              <div class="absolute bottom-0 left-0 p-4">
                <h3 class="text-xl font-semibold text-white">
                  {{ location.title }}
                </h3>
              </div>
            </div>
            <p class="text-sm text-gray-500">
              순서: {{ location.idx }} / lat: {{ location.latitude }}/lng:
              {{ location.longitude }}
            </p>
            <div class="p-4">
              <p class="text-gray-700 mb-4">{{ location.content }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 타임라인 끝 -->
    <!-- 모달 -->
    <div v-if="isModalOpen" class="fixed z-10 inset-0 overflow-y-auto">
      <div
        class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        <div class="fixed inset-0 transition-opacity" aria-hidden="true">
          <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <span
          class="hidden sm:inline-block sm:align-middle sm:h-screen"
          aria-hidden="true"
          >&#8203;</span
        >
        <div
          data-aos="fade-up"
          data-aos-duration="1000"
          class="inline-block align-center bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-2xl sm:w-full">
          <div class="bg-white px-6 pt-5 pb-4 sm:p-6 sm:pb-4">
            <div class="sm:flex sm:items-start">
              <div class="text-center sm:mt-0 sm:text-left">
                <h3 class="text-2xl leading-6 font-bold text-gray-900 my-6">
                  {{ selectedAttraction.title }}
                </h3>
                <div class="mt-2">
                  <p class="text-sm text-gray-500">
                    {{ selectedAttraction.overview }}
                  </p>
                </div>
                <img
                  :src="selectedAttraction.firstImage"
                  alt="Image"
                  class="w-full h-64 object-cover mt-4" />
                <div class="mt-4">
                  <input
                    v-model="selectedAttraction.memo"
                    type="text"
                    placeholder="나만의 메모를 입력하세요"
                    class="w-full h-[100px] px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-indigo-500" />
                </div>
              </div>
            </div>
          </div>
          <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button
              @click="addLocation"
              class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-blue-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 sm:ml-3 sm:w-auto sm:text-sm">
              여행지 등록하기
            </button>
            <button
              @click="closeModal"
              class="w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:w-auto sm:text-sm">
              닫기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#detail-header {
  margin-bottom: 2rem;
}

button:hover {
  transform: translateY(-2px);
}
</style>
