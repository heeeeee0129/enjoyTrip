<script setup>
import { ref, computed } from "vue";
import { tripRoutes } from "@/assets/mock/trip";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/index";

const router = useRouter();
const trips = ref(tripRoutes);
const store = useUserStore();
const isLoggedIn = computed(() => store.isLoggedIn);

const goDetail = (id) => {
  console.log(id);
  router.push({ name: "PlanDetail", params: { id } });
};
console.log(isLoggedIn.value);
</script>

<template>
  <div class="p-10 mt-6">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder" id="search-header">
        <h2 class="">여행 계획</h2>
      </div>
    </div>
    <div class="px-32">
      <div v-if="!!isLoggedIn" class="flex justify-end my-12 mx-auto">
        <!-- 여행 계획 추가 버튼 -->
        <router-link :to="{ name: 'PlanWrite' }" class="text-white">
          <button
            class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg">
            여행 계획 추가
          </button>
        </router-link>
      </div>

      <div class="mt-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div
          v-for="route in trips"
          :key="route.id"
          @click.prevent="goDetail(route.id)"
          class="p-6 bg-white rounded-lg shadow-lg transition transform hover:scale-105">
          <h3 class="text-2xl font-semibold mb-3 text-gray-800">
            {{ route.title }}
          </h3>
          <p class="text-gray-700 mb-4">{{ route.content }}</p>
          <p class="text-gray-500 mb-1">
            시작 날짜: <span class="font-medium">{{ route.start_date }}</span>
          </p>
          <p class="text-gray-500 mb-1">
            종료 날짜: <span class="font-medium">{{ route.end_date }}</span>
          </p>
          <p class="text-gray-400 text-sm font-light mt-4 border-t pt-3">
            작성일: {{ route.created_at }} / 작성자:
            <span class="font-medium text-gray-600">{{ route.user_id }}</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#search-header {
  margin-bottom: 2rem;
}

button {
  transition: background-color 0.3s ease, transform 0.2s ease;
}

button:hover {
  transform: translateY(-2px);
}
</style>
