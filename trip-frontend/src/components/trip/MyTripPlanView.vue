<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listUserTripPlan } from "@/api/tripplan.js";
import { useUserStore } from "@/stores/index";
import MyTripPlanItem from "@/components/trip/item/MyTripPlanItem.vue";
import Swal from "sweetalert2";

const trips = ref([]);
const store = useUserStore();
const router = useRouter();

onMounted(() => {
  setTripPlan();
});

const setTripPlan = async () => {
  const success = (response) => {
    trips.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listUserTripPlan(store.member.id, success, fail);
};

const moveBack = () => {
  router.go(-1);
};
</script>

<template>
  <div class="p-10 mt-6">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder">
        <h2 class="">나의 여행 계획</h2>
      </div>
    </div>
    <div class="px-32">
      <div class="flex justify-start mb-2 mx-auto">
        <button
          class="bg-gray-500 hover:bg-gray-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          @click="moveBack">
          뒤로가기
        </button>
      </div>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-32">
      <div v-for="trip in trips" :key="trip.tripNo">
        <MyTripPlanItem :trip="trip" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
