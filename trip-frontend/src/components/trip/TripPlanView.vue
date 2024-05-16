<script setup></script>

<template>
  <div id="temp"></div>
  <div class="mt-3 text-center fw-bolder" id="search-header">
    <h2 class="mb-3">여행 계획</h2>
  </div>
  <searchBar @search="handleSearch" />
</template>

<style scoped></style>
