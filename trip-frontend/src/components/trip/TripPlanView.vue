<script setup>
import searchBar from "@/components/trip/item/TripSearchBar.vue";
</script>

<template>
  <div class="p-10 mt-6">
    <div class="row px-10">
      <div class="mt-3 text-center fw-bolder" id="search-header">
        <h2 class="">여행 계획</h2>
      </div>
      <searchBar @search="handleSearch" />

      <div
        id="map"
        class="mt-3"
        style="width: 100%; height: 500px"
        data-aos="fade-up"></div>
      <!-- //map 영역 위에까지 -->
    </div>
  </div>
</template>

<style scoped></style>
