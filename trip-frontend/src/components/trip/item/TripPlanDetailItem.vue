<script setup>
import { ref, onMounted } from "vue";
import { getAttraction } from "@/api/attraction";
import Swal from "sweetalert2";

const props = defineProps({ location: Object });
const attraction = ref({});

onMounted(() => {
  getAttract(props.location.contentId);
});

const getAttract = async (contentId) => {
  const success = (response) => {
    attraction.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getAttraction(contentId, success, fail);
};
</script>

<template>
  <div class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105">
    <div class="relative">
      <img :src="attraction.firstImage" alt="location.title" class="w-full h-64 object-cover" />
      <div class="absolute bottom-0 left-0 p-4">
        <h3 class="text-xl font-semibold text-white">
          {{ attraction.title }}
        </h3>
      </div>
    </div>
    <div class="p-4">
      <p class="text-gray-700 mb-4">{{ location.memo }}</p>
    </div>
  </div>
</template>

<style scoped></style>
