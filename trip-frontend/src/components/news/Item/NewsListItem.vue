<script setup>
defineProps({ news: Object });
</script>

<template>
  <div class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105">
    <div class="relative" @click="goDetail(hotplace.hotNo)" type="button">
      <a :href="news.url" target="_blank">
        <img :src="news.image" alt="이미지 없음" class="w-full h-64 object-cover" />
      </a>
    </div>
    <div class="p-4 d-flex justify-content-between align-items-center">
      <p class="text-gray-700 mb-4" style="font-size: 1.2rem; font-weight: bold">
        {{ news.subject }}
      </p>
    </div>
  </div>
</template>

<style scoped></style>
