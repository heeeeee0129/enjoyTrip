<script setup>
import { useRouter } from "vue-router";
import dayjs from "dayjs";
defineProps({ trip: Object });
const router = useRouter();

function formatDate(dateString) {
  return dayjs(dateString).format("YYYY년 MM월 DD일");
}

const goDetail = (id) => {
  router.push({ name: "PlanDetail", params: { id } });
};
</script>

<template>
  <div
    class="p-6 bg-white rounded-lg shadow-lg transition transform hover:scale-105"
    @click="goDetail(trip.id)"
    type="button"
  >
    <h3 class="text-2xl font-semibold mb-3 text-gray-800">
      {{ trip.title }}
    </h3>
    <p class="text-gray-700 mb-4">{{ trip.content }}</p>
    <p class="text-gray-500 mb-1">
      시작 날짜:
      <span class="font-medium">{{ formatDate(trip.startDate) }}</span>
    </p>
    <p class="text-gray-500 mb-1">
      종료 날짜:
      <span class="font-medium">{{ formatDate(trip.endDate) }}</span>
    </p>
    <p class="text-gray-400 text-sm font-light mt-4 border-t pt-3">
      작성일: {{ formatDate(trip.createdAt) }} / 작성자:
      <span class="font-medium text-gray-600">{{ trip.userName }}</span>
    </p>
  </div>
</template>

<style scoped></style>
