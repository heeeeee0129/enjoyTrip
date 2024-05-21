<script setup>
import { ref, computed, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/index";
import { listTripPlan } from "@/api/tripplan.js";
import dayjs from "dayjs";
import Swal from "sweetalert2";

const trips = ref([]);

const store = useUserStore();
const isLoggedIn = computed(() => store.isLoggedIn);
const router = useRouter();

onMounted(() => {
  getTripPlanList();
});

const getTripPlanList = async () => {
  const success = (response) => {
    trips.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "Error!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listTripPlan(success, fail);
};

const goDetail = (id) => {
  router.push({ name: "PlanDetail", params: { id } });
};

function formatDate(dateString) {
  return dayjs(dateString).format("YYYY년 MM월 DD일");
}
</script>

<template>
  <div class="p-10 mt-6">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder">
        <h2 class="">여행 계획</h2>
      </div>
    </div>
    <div class="px-32">
      <div class="p-2 flex justify-between mb-2">
        <div v-if="!!isLoggedIn" class="">
          <router-link :to="{ name: 'MyPlanView' }" class="text-white">
            <button
              class="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg w-full">
              나의 여행 계획
            </button>
          </router-link>
        </div>
        <div v-if="!!isLoggedIn" class="text-right">
          <router-link :to="{ name: 'PlanWrite' }" class="text-white">
            <button
              class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg w-full">
              여행 계획 추가
            </button>
          </router-link>
        </div>
      </div>
      <hr class="border-gray-600 border-1 px-3 m-0" />

      <div class="mt-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div
          v-for="route in trips"
          :key="route.id"
          @click.prevent="goDetail(route.id)"
          type="button"
          class="p-6 bg-white rounded-lg shadow-lg transition transform hover:scale-105">
          <h3 class="text-2xl font-semibold mb-3 text-gray-800">
            {{ route.title }}
          </h3>
          <p class="text-gray-700 mb-4">{{ route.content }}</p>
          <p class="text-gray-500 mb-1">
            시작 날짜:
            <span class="font-medium">{{ formatDate(route.startDate) }}</span>
          </p>
          <p class="text-gray-500 mb-1">
            종료 날짜:
            <span class="font-medium">{{ formatDate(route.endDate) }}</span>
          </p>
          <p class="text-gray-400 text-sm font-light mt-4 border-t pt-3">
            작성일: {{ formatDate(route.createdAt) }} / 작성자:
            <span class="font-medium text-gray-600">{{ route.userName }}</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
button {
  transition: background-color 0.3s ease, transform 0.2s ease;
}

button:hover {
  transform: translateY(-2px);
}
</style>
