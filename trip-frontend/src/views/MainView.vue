<script setup>
import { onMounted } from "vue";
import AOS from "aos";
import "aos/dist/aos.css";

onMounted(() => {
  AOS.init();
});
</script>

<template>
  <div
    class="py-[20%] bg-cover bg-center"
    style="background-image: url('/bg_trip.jpg'); height: 300vh">
    <div class="flex flex-col items-center w-full mb-[20%]">
      <div
        class="font-extrabold text-7xl text-white"
        data-aos="fade-down"
        data-aos-easing="ease-in"
        data-aos-duration="1000">
        특별한 여행을 떠나세요
      </div>
    </div>
    <router-link :to="{ name: 'search' }" class="no-underline">
      <div
        class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 m-[10%] w-[50vw] ml-[10%] relative flex items-center"
        data-aos="fade-right"
        data-aos-duration="1000">
        <div class="flex-grow">
          <h3 class="text-white font-extrabold text-3xl mb-3">다양한 여행지</h3>
          <h4 class="text-gray-100">국내의 다양한 여행지를 검색해보세요</h4>
        </div>
        <h1
          class="text-black text-opacity-50 font-bold text-4xl text-right flex-none">
          >
        </h1>
      </div>
    </router-link>
    <router-link :to="{ name: 'plan' }" class="no-underline">
      <div
        class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 m-[10%] w-[50vw] ml-[20%]"
        data-aos="fade-right"
        data-aos-duration="1000">
        <h3 class="text-white font-extrabold text-3xl mb-3">
          편리한 일정 계획
        </h3>
        <h4 class="text-gray-100">
          지도 정보를 통해 편리하게 일정을 계획해보세요
        </h4>
      </div>
    </router-link>
    <router-link :to="{ name: 'BoardView' }" class="no-underline">
      <div
        class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 m-[10%] w-[50vw] ml-[30%]"
        data-aos="fade-right"
        data-aos-duration="1000">
        <h3 class="text-white font-extrabold text-3xl mb-3">나만의 관광지</h3>
        <h4 class="text-gray-100">나만의 특별한 관광지를 공유해보세요</h4>
      </div>
    </router-link>

    <div class="grid grid-cols-4 gap-4 p-4">
      <div
        data-aos="fade-up"
        data-aos-duration="1000"
        class="p-3 h-60 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"></div>
      <div
        data-aos="fade-up"
        data-aos-duration="1000"
        class="p-3 h-60 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"></div>
      <div
        data-aos="fade-up"
        data-aos-duration="1000"
        class="p-3 h-60 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"></div>
      <div
        data-aos="fade-up"
        data-aos-duration="1000"
        class="p-3 h-60 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"></div>
    </div>

    <!-- Footer Section -->
    <footer
      class="py-4 text-center rounded-b-lg text-lg font-extrabold text-white fixed bottom-0 w-full"
      data-aos="fade-up"
      data-aos-duration="1000"
      data-aos-delay="1500">
      <p>여행을 떠나는 여러분을 응원합니다. 즐거운 여정 되세요!</p>
    </footer>
  </div>
</template>

<style scoped></style>
