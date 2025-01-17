<template>
  <div
    class="container max-w-4xl my-24 bg-white rounded-lg overflow-hidden shadow-lg py-3">
    <div class="flex flex-row">
      <!-- 왼쪽에 이미지 -->
      <img
        :src="attraction.firstImage ? attraction.firstImage : image"
        class="h-[500px] rounded-xl shadow-md w-[500px]"
        alt="이미지 없음" />
      <div class="flex flex-col p-10">
        <div>
          <!-- 제목과 주소 -->
          <h5 class="text-xl font-semibold">{{ attraction.title }}</h5>
          <p class="text-sm text-gray-600 mb-4">
            {{ attraction.addr1 }}, {{ attraction.addr2 }}
          </p>
          <!-- 설명 -->
          <p class="text-base md:text-base">{{ attraction.overview }}</p>
        </div>
        <ul
          class="mt-8 bg-slate-100 py-10 rounded-xl shadow-gray-400 shadow-sm">
          <!-- 전화번호 -->
          <li v-if="attraction.tel" class="flex items-center mb-2">
            <strong class="mr-2">전화번호:</strong>
            <span>{{ attraction.tel }}</span>
          </li>
          <!-- 우편번호 -->
          <li class="flex items-center mb-2">
            <strong class="mr-2">우편번호:</strong>
            <span>{{ attraction.zipcode }}</span>
          </li>
          <!-- 위도 -->
          <li class="flex items-center mb-2">
            <strong class="mr-2">위도:</strong>
            <span>{{ attraction.latitude }}</span>
          </li>
          <!-- 경도 -->
          <li class="flex items-center">
            <strong class="mr-2">경도:</strong>
            <span>{{ attraction.longitude }}</span>
          </li>
        </ul>
      </div>
    </div>
    <div class="pt-4 border-gray-200 flex justify-end">
      <!-- 버튼 -->
      <button
        @click="goBack"
        class="py-2 px-4 bg-gray-200 text-gray-800 rounded-lg mr-2">
        돌아가기
      </button>
      <button
        @click="changeModal"
        class="py-2 px-4 bg-blue-200 text-blue-800 rounded-lg mr-2">
        날씨보기
      </button>
      <a
        :href="kakaoMapLink"
        target="_blank"
        rel="noopener noreferrer"
        class="py-2 px-4 bg-yellow-500 text-white rounded-lg mr-2">
        카카오맵 길찾기
      </a>
      <a
        :href="naverSearchLink"
        target="_blank"
        rel="noopener noreferrer"
        class="py-2 px-4 bg-green-500 text-white rounded-lg">
        네이버 검색
      </a>
    </div>

    <!-- 모달 -->
    <Modal
      v-if="isModalVisible"
      @close="isModalVisible = false"
      class="fixed z-10 inset-0 overflow-y-auto">
      <WeatherDetail :weather="weather" @change-modal="changeModal" />
    </Modal>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getAttraction } from "@/api/attraction.js";
import { fail } from "@/utils/error-handler";
import WeatherDetail from "@/components/weather/WeatherDetail.vue";
import image from "@/assets/images.png";

const router = useRouter();
const route = useRoute();
const { contentId } = route.params;

const attraction = ref({});
const weather = ref({});
const isModalVisible = ref(false);

onMounted(async () => {
  detailAttraction();
});

const detailAttraction = async () => {
  const success = (response) => {
    attraction.value = response.data;
    weather.value.lat = attraction.value.latitude;
    weather.value.lon = attraction.value.longitude;
  };

  await getAttraction(contentId, success, fail);
};

const goBack = () => {
  router.go(-1);
};

const kakaoMapLink = computed(() => {
  const { latitude, longitude, title } = attraction.value;
  return `https://map.kakao.com/link/to/${encodeURIComponent(
    title
  )},${latitude},${longitude}`;
});

const naverSearchLink = computed(() => {
  const { title } = attraction.value;
  return `https://search.naver.com/search.naver?query=${encodeURIComponent(
    title
  )}`;
});

const changeModal = () => {
  isModalVisible.value = !isModalVisible.value;
};
</script>

<style scoped></style>
