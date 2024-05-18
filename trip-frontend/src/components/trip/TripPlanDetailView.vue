<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">여행 경로</h2>
      </div>
      <button
        @click="goBack"
        class="mx-auto hover:shadow-lg bg-gray-500 hover:bg-gray-700 text-white py-2 px-4 rounded"
      >
        뒤로 가기
      </button>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="location in trip.tripLocations" :key="location.id">
        <TripPlanDetailItem :location="location" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import Swal from "sweetalert2";
import { getTripPlan } from "@/api/tripplan";
import TripPlanDetailItem from "@/components/trip/item/TripPlanDetailItem.vue";

const route = useRoute();
const tripId = Number(route.params.id);

const trip = ref({});
onMounted(() => {
  getTrip();
});

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
