<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">여행 경로</h2>
      </div>
      <button
        @click="goBack"
        class="mx-auto hover:shadow-lg bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded">
        뒤로 가기
      </button>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="location in locations" :key="location.id">
        <div
          class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105">
          <div class="relative">
            <img
              :src="location.img"
              alt="location.title"
              class="w-full h-64 object-cover" />
            <div class="absolute bottom-0 left-0 p-4">
              <h3 class="text-xl font-semibold text-white">
                {{ location.title }}
              </h3>
            </div>
          </div>
          <div class="p-4">
            <p class="text-gray-700 mb-4">{{ location.content }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { getLocationsByTripId } from "@/assets/mock/trip";

const route = useRoute();
const tripId = Number(route.params.id);
const locations = getLocationsByTripId(tripId);
console.log(locations);
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
