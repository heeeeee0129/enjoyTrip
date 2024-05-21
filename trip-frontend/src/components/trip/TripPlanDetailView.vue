<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">여행 경로</h2>
      </div>
      <div class="button-container flex justify-between items-center">
        <button
          @click="goBack"
          class="hover:shadow-lg bg-gray-500 hover:bg-gray-700 text-white py-2 px-4 rounded"
        >
          뒤로 가기
        </button>
        <button
          @click="confirmDelete"
          class="hover:shadow-lg bg-red-500 hover:bg-red-700 text-white py-2 px-4 rounded"
        >
          삭제 하기
        </button>
      </div>
      <hr class="border-gray-400 mt-4" />
    </div>
    <div class="flex m-12">
      <div class="w-3/4 flex flex-col h-[550px] px-12">
        <div id="map" class="mt-3 rounded shadow-sm p-10 mx-auto w-full h-full"></div>
      </div>
      <div class="w-1/4 flex flex-col h-[550px] py-3 relative">
        <p class="m-0 text-base font-bold">여행지 목록</p>
        <hr class="border-gray-30 border-[0.5px]" />
        <div class="overflow-scroll custom-scrollbar">
          <div v-if="result.value !== null">
            <div
              v-for="(trip, index) in trip.tripLocations"
              :key="trip.id"
              @click="openDetailModal(trip)"
              class="bg-white bg-opacity-30 px-4 py-3 rounded-lg shadow-md my-3 border-gray-200 border-1"
            >
              <p class="text-sm font-bold m-0 p-0">{{ index + 1 }}. {{ trip.title }}</p>
              <hr class="my-2 border-gray-300" />
              <p class="text-xs text-gray-600 text-ellipsis whitespace-nowrap overflow-hidden">
                {{ trip.memo }}
              </p>
            </div>
          </div>
          <div class="h-[150px]"></div>
        </div>
        <div class="bg-white bg-opacity-30 p-4 rounded-lg shadow-md absolute bottom-0 w-full">
          <p class="text-sm font-bold">
            요금: {{ formatNumber(result.fare.taxi) }}원 (택시),
            {{ formatNumber(result.fare.toll) }}원 (통행료)
          </p>
          <p class="text-sm font-bold">거리: {{ formatNumber(result.distance / 1000) }}km</p>
          <p class="text-sm font-bold">소요 시간: {{ formatDuration(result.duration) }}</p>
        </div>
      </div>
    </div>
    <!-- 여행지 리스트 start -->
    <div class="my-24 mx-24">
      <h3>여행지 상세 리스트</h3>
      <hr class="border-gray-10 border-[0.5px] mb-10" />
      <div class="grid grid-cols-4 gap-4 p-4 px-24">
        <div v-for="location in trip.tripLocations" :key="location.id">
          <TripPlanDetailItem :location="location" />
        </div>
      </div>
    </div>
    <!-- 여행지 리스트 end -->
    <div v-if="isModalOpen" class="fixed z-10 inset-0 overflow-y-auto">
      <div
        class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0"
      >
        <div class="fixed inset-0 transition-opacity" aria-hidden="true">
          <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>
        <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true"
          >&#8203;</span
        >
        <div
          data-aos="fade-up"
          data-aos-duration="2000"
          class="inline-block align-center bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-2xl sm:w-full"
        >
          <div class="bg-white px-6 pt-5 pb-4 sm:p-6 sm:pb-4">
            <div class="sm:flex sm:items-start">
              <div class="text-center sm:mt-0 sm:text-left">
                <h3 class="text-2xl leading-6 font-bold text-gray-900 my-6">
                  {{ selectedTrip.title }}
                </h3>
                <div class="mt-2">
                  <p class="text-sm text-gray-500 px-5">
                    주소: {{ selectedTrip.addr1 }} {{ selectedTrip.addr2 }}
                  </p>
                  <p class="text-sm text-gray-500 px-5">전화번호: {{ selectedTrip.tel }}</p>
                  <p class="text-sm text-gray-500 px-5">우편번호: {{ selectedTrip.zipcode }}</p>
                  <p class="text-sm text-gray-500 px-5">설명: {{ selectedTrip.overview }}</p>
                  <div class="text-sm text-gray-500 px-5 mt-12">
                    나의 메모
                    <hr class="px-2 border-gray-600 border-[1px]" />
                    <div class="bg-gray-100 rounded-lg p-4">
                      {{ selectedTrip.memo }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button
              @click="closeModal"
              class="w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:w-auto sm:text-sm"
            >
              닫기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import Swal from "sweetalert2";
import { getTripPlan, deleteTripPlan } from "@/api/tripplan";
import TripPlanDetailItem from "@/components/trip/item/TripPlanDetailItem.vue";
import { loadKakaoMapScript, getCarDirection } from "@/utils/load-map";
import { formatDuration, formatNumber } from "@/utils/convert-time";
import { getAttraction } from "@/api/attraction";
import { fail } from "@/utils/error-handler";
const route = useRoute();
const tripId = Number(route.params.id);
let map = null;
const trip = ref({});
let result = ref({ fare: { taxi: 0, toll: 0 }, distance: 0, duration: 0 });
const isModalOpen = ref(false);
const selectedTrip = ref({});

const router = useRouter();

onMounted(async () => {
  await getTrip();
  await loadKakaoMapScript(map).then();
  map = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(37.500613, 127.036431),
    level: 11,
  });
  displayMarker();
  result.value = await getCarDirection(trip.value.tripLocations);

  result.value.polyline.setMap(map);
  map.setCenter(
    new window.kakao.maps.LatLng(trip.value.tripLocations[0].lat, trip.value.tripLocations[0].lng)
  );
});
const openDetailModal = async (trip) => {
  await getAttraction(
    trip.contentId,
    (response) => {
      selectedTrip.value = response.data;
    },
    fail
  ).then(() => {
    selectedTrip.value.memo = trip.memo;
    isModalOpen.value = true;
    map.panTo(
      new window.kakao.maps.LatLng(selectedTrip.value.latitude, selectedTrip.value.longitude)
    );
  });
  selectedTrip.value.memo = trip.memo;
  console.log(selectedTrip.value, isModalOpen.value);
};
const closeModal = () => {
  isModalOpen.value = false;
};

const displayMarker = () => {
  const markers = [];
  const imageSize = new window.kakao.maps.Size(24, 35);
  trip.value.tripLocations.forEach((position) => {
    const imageSrc = `/marker1.png`;
    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize);
    const marker = new window.kakao.maps.Marker({
      position: new window.kakao.maps.LatLng(position.lat, position.lng),
      title: position.title,
      image: markerImage,
    });
    markers.push(marker);
    marker.setMap(map); // Set the marker on the map

    const content = document.createElement("div");
    content.className = "customoverlay h-full w-[300px] whitespace-normal p-2";
    content.style.backgroundColor = "rgba(255, 255, 255, 0.8)";

    content.innerHTML = `
  <p class="block text-center text-black text-xs py-1">${position.memo}</p>
`;
    content.addEventListener("mouseleave", () => {
      closeOverlay();
    });

    const customOverlay = new window.kakao.maps.CustomOverlay({
      position: new window.kakao.maps.LatLng(position.lat, position.lng),
      content: content,
      yAnchor: 1,
      xAnchor: 0.5,
      zIndex: 1,
    });

    new window.kakao.maps.event.addListener(marker, "click", () => {
      customOverlay.setMap(map);
    });
    const closeOverlay = () => {
      customOverlay.setMap(null);
    };
  });
};

const getTrip = async () => {
  const success = (response) => {
    trip.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "Error!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getTripPlan(tripId, success, fail);
};

const confirmDelete = () => {
  Swal.fire({
    title: "정말로 삭제 하시겠습니까?",
    text: "다시 되돌릴 수 없습니다. 신중하세요.",
    icon: "question",
    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
    confirmButtonColor: "#d33", // confirm 버튼 색깔 지정
    cancelButtonColor: "#3085d6", // cancel 버튼 색깔 지정
    confirmButtonText: "삭제", // confirm 버튼 텍스트 지정
    cancelButtonText: "취소", // cancel 버튼 텍스트 지정
    reverseButtons: true, // 버튼 순서 거꾸로
  }).then((result) => {
    // 만약 Promise리턴을 받으면,
    if (result.isConfirmed) {
      // 만약 모달창에서 confirm 버튼을 눌렀다면
      goDelete();
    }
  });
};

const goDelete = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "삭제 되었습니다.",
      icon: "success",
      confirmButtonText: "OK",
    }).then(() => {
      router.replace({ name: "plan" });
    });
  };

  const fail = (error) => {
    Swal.fire({
      title: "Error!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await deleteTripPlan(tripId, success, fail);
};

const goBack = () => {
  window.history.back();
};
</script>

<style scoped>
#detail-header {
  margin-bottom: 2rem;
}

/* 이미지 위에 텍스트 오버레이 스타일 */
.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.bottom-0 {
  bottom: 0;
}

.left-0 {
  left: 0;
}

.p-4 {
  padding: 1rem;
}

.text-white {
  color: #ffffff;
}
button:hover {
  transform: translateY(-2px);
}
</style>
