<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { loadKakaoMapScript } from "@/utils/load-map";

const router = useRouter();
const title = ref("");
const content = ref("");
const startDate = ref("");
const endDate = ref("");
const locations = ref([]);
let map = null;
const submitForm = () => {
  // 여행 계획 추가 로직을 작성하세요
  // 제목(title), 내용(content), 시작일(startDate), 종료일(endDate)을 이용하여 새로운 여행 계획을 추가합니다
  // 추가된 여행지를 locations에 추가합니다
};
onMounted(async () => {
  await loadKakaoMapScript(map);
  map = new window.kakao.maps.Map(document.getElementById("map"), {
    center: new window.kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  });
});

const goBack = () => {
  router.go(-1);
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
            type="submit"
            class="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">
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
            type="text"
            placeholder="장소 검색..."
            class="flex-grow py-2 px-4 border border-gray-300 rounded-l-md focus:outline-none focus:ring-blue-500 focus:border-blue-500" />
          <div
            class="bg-blue-300 py-2 px-4 rounded-r-md border border-gray-300">
            검색
          </div>
        </div>
        <!-- 지도 -->
        <div id="map" class="flex-grow rounded-lg"></div>
      </div>

      <!-- 오른쪽에는 여행 계획 추가 폼 -->
      <div class="w-1/2 pl-6 mt-20">
        <form @submit.prevent="submitForm">
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
              class="px-2 mt-1 focus:ring-blue-500 focus:border-blue-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md"></textarea>
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
      <h3 class="text-2xl font-semibold my-4">여행지 목록</h3>
      <div
        v-for="(location, index) in locations"
        :key="location.id"
        class="bg-white rounded-lg shadow-md overflow-hidden mb-4">
        <div class="relative">
          <img
            src="/attraction.jpg"
            :alt="location.title"
            class="w-full h-40 object-cover" />
          <div class="absolute bottom-0 left-0 p-4">
            <h3 class="text-xl font-semibold text-white">
              {{ location.title }}
            </h3>
          </div>
        </div>
        <div class="p-4">
          <p class="text-gray-700 mb-4">{{ location.content }}</p>
          <p class="text-gray-500 mb-1">
            시작 날짜:
            <span class="font-medium">{{ location.start_date }}</span>
          </p>
          <p class="text-gray-500 mb-1">
            종료 날짜: <span class="font-medium">{{ location.end_date }}</span>
          </p>
          <p class="text-gray-400 text-sm font-light mt-4">
            순서: {{ index + 1 }}
          </p>
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
