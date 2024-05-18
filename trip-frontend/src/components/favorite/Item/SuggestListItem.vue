<script setup>
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps({ attraction: Object });

const moveDetail = () => {
  router.push({
    name: "TripDetail",
    params: { contentId: props.attraction.contentId },
  });
};
</script>

<template>
  <div
    class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105"
    @click="moveDetail"
    type="button"
  >
    <div class="relative">
      <img :src="attraction.firstImage" alt="location.title" class="w-full h-64 object-cover" />
      <div class="absolute bottom-0 left-0 p-4">
        <h3 class="text-xl font-semibold text-white">
          {{ attraction.title }}
        </h3>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
