<script setup></script>

<template>
  <div
    class="flex flex-col items-center justify-center min-h-screen bg-cover bg-center"
    style="background-image: url('/bg_trip.jpg')">
    <!-- Header Section -->
    <header
      class="bg-gradient-to-r from-blue-500 to-purple-500 py-6 text-center rounded-t-lg">
      <h1 class="text-4xl font-semibold text-white">여행을 떠나세요</h1>
      <p class="text-lg text-white mt-2">
        새로운 모험을 찾아떠나는 여정으로 떠나세요.
      </p>
    </header>

    <!-- Main Content Section -->
    <main class="py-8">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- Feature 1 -->
        <div class="bg-white rounded-lg shadow-lg p-6">
          <h2 class="text-xl font-semibold mb-2">다양한 여행지</h2>
          <p>세계 각지의 아름다운 여행지를 탐험해보세요.</p>
        </div>
        <!-- Feature 2 -->
        <div class="bg-white rounded-lg shadow-lg p-6">
          <h2 class="text-xl font-semibold mb-2">편리한 예약</h2>
          <p>간편한 예약 시스템으로 여행 일정을 계획하세요.</p>
        </div>
        <!-- Feature 3 -->
        <div class="bg-white rounded-lg shadow-lg p-6">
          <h2 class="text-xl font-semibold mb-2">여행 가이드</h2>
          <p>전문적인 여행 가이드와 함께하는 특별한 여행 경험을 즐기세요.</p>
        </div>
      </div>
    </main>

    <!-- Footer Section -->
    <footer class="bg-gray-200 py-4 text-center rounded-b-lg">
      <p>여행을 떠나는 여러분을 응원합니다. 즐거운 여정 되세요!</p>
    </footer>
  </div>
</template>

<style scoped></style>
