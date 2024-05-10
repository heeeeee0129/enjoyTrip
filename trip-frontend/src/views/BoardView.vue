<script setup></script>

<template>
  <div class="container text-center mt-5">
    <div class="card shadow-sm">
      <div class="card-body">
        <h1 class="card-title mb-0">질문 게시판</h1>
        <p class="card-text text-muted">여기에서 궁금한 것을 물어보세요!</p>
      </div>
    </div>
  </div>
  <router-view />
</template>

<style>
.card {
  background-color: #f8f9fa;
  border: none;
}

.card-title {
  color: #007bff;
}

.card-text {
  font-size: 1.1rem;
}
</style>
