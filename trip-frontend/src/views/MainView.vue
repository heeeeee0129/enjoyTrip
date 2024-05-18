<script setup>
import { ref, onMounted } from "vue";
import { getTopAttractions } from "@/api/attraction";
import { getTopHotplace } from "@/api/hotplace";
import AOS from "aos";
import Swal from "sweetalert2";
import "aos/dist/aos.css";
import { useRouter } from "vue-router";

const router = useRouter();
const attractions = ref([]);
const hotplaces = ref([]);

onMounted(() => {
  AOS.init();
  setAttraction();
  setHotplace();
});

const setAttraction = async () => {
  const success = (response) => {
    attractions.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getTopAttractions(success, fail);
};

const setHotplace = async () => {
  const success = (response) => {
    hotplaces.value = response.data;
    // console.log(hotplaces);
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getTopHotplace(success, fail);
};

const goDetailAttraction = (contentId) => {
  router.push({
    name: "TripDetail",
    params: {
      contentId: contentId,
    },
  });
};

const goDetailHotPlace = (hotNo) => {
  router.push({
    name: "HotPlaceDetail",
    params: {
      hotNo: hotNo,
    },
  });
};

function getImageUrl(folder, name) {
  return new URL(`/src/assets/upload/${folder}/${name}`, import.meta.url);
}
</script>

<template>
  <div
    class="py-[20%] bg-cover bg-center"
    style="background-image: url('/bg_trip.jpg'); height: 300vh"
  >
    <div class="flex flex-col items-center w-full mb-[20%]">
      <div
        class="font-extrabold text-7xl text-white"
        data-aos="fade-down"
        data-aos-easing="ease-in"
        data-aos-duration="1000"
      >
        특별한 여행을 떠나세요
      </div>
    </div>
    <router-link :to="{ name: 'search' }" class="no-underline">
      <div
        class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 m-[10%] w-[50vw] ml-[10%] relative flex items-center"
        data-aos="fade-right"
        data-aos-duration="1000"
      >
        <div class="flex-grow">
          <h3 class="text-white font-extrabold text-3xl mb-3">다양한 여행지</h3>
          <h4 class="text-gray-100">국내의 다양한 여행지를 검색해보세요</h4>
        </div>
        <h1 class="text-black text-opacity-50 font-bold text-4xl text-right flex-none">></h1>
      </div>
    </router-link>
    <router-link :to="{ name: 'plan' }" class="no-underline">
      <div
        class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 m-[10%] w-[50vw] ml-[20%] relative flex items-center"
        data-aos="fade-right"
        data-aos-duration="1000"
      >
        <div class="flex-grow">
          <h3 class="text-white font-extrabold text-3xl mb-3">편리한 일정 계획</h3>
          <h4 class="text-gray-100">지도 정보를 통해 편리하게 일정을 계획해보세요</h4>
        </div>
        <h1 class="text-black text-opacity-50 font-bold text-4xl text-right flex-none">></h1>
      </div>
    </router-link>
    <router-link :to="{ name: 'HotPlaceView' }" class="no-underline">
      <div
        class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 m-[10%] w-[50vw] ml-[30%] relative flex items-center"
        data-aos="fade-right"
        data-aos-duration="1000"
      >
        <div class="flex-grow">
          <h3 class="text-white font-extrabold text-3xl mb-3">나만의 핫플레이스</h3>
          <h4 class="text-gray-100">나만의 특별한 핫플레이스를 공유해보세요</h4>
        </div>
        <h1 class="text-black text-opacity-50 font-bold text-4xl text-right flex-none">></h1>
      </div>
    </router-link>

    <div data-aos="fade-right" data-aos-duration="1000" class="mb-5">
      <router-link :to="{ name: 'TripView' }" class="no-underline">
        <h1
          class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 relative flex items-center ml-[5%] w-[90vw] text-white font-extrabold text-3xl mb-3"
        >
          인기 여행지
        </h1></router-link
      >
      <div class="grid grid-cols-4 gap-4 p-4">
        <div
          v-for="attraction in attractions"
          :key="attraction.contentId"
          data-aos="fade-up"
          data-aos-duration="1000"
          class="p-3 h-60 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"
          @click="goDetailAttraction(attraction.contentId)"
          type="button"
        >
          <img
            :src="attraction.firstImage"
            :alt="attraction.title"
            class="w-full h-full object-cover"
          />
          <div class="absolute bottom-0 left-0 p-4">
            <h3 class="text-xl font-semibold text-white">
              {{ attraction.title }}
            </h3>
          </div>
        </div>
      </div>
    </div>

    <div data-aos="fade-right" data-aos-duration="1000">
      <router-link :to="{ name: 'TripView' }" class="no-underline">
        <h1
          class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 relative flex items-center ml-[5%] w-[90vw] text-white font-extrabold text-3xl mb-3"
        >
          나만의 장소
        </h1></router-link
      >
      <div class="grid grid-cols-4 gap-4 p-4">
        <div
          v-for="hotplace in hotplaces"
          :key="hotplace.hotNo"
          data-aos="fade-up"
          data-aos-duration="1000"
          class="p-3 h-60 bg-gray-100 bg-opacity-40 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-300 m-2"
          @click="goDetailHotPlace(hotplace.hotNo)"
          type="button"
        >
          <img
            :src="getImageUrl(hotplace.fileInfo.saveFolder, hotplace.fileInfo.saveFile)"
            :alt="hotplace.placeName"
            class="w-full h-full object-cover"
          />
          <div class="absolute bottom-0 left-0 p-4">
            <h3 class="text-xl font-semibold text-white">
              {{ hotplace.placeName }}
            </h3>
          </div>
        </div>
      </div>
    </div>

    <!-- Footer Section -->
    <footer
      class="py-4 text-center rounded-b-lg text-lg font-extrabold text-white fixed bottom-0 w-full"
      data-aos="fade-up"
      data-aos-duration="1000"
      data-aos-delay="1500"
    >
      <p>여행을 떠나는 여러분을 응원합니다. 즐거운 여정 되세요!</p>
    </footer>
  </div>
</template>

<style scoped></style>
