<script setup>
import { ref, onMounted } from "vue";
import { getTopAttractions } from "@/api/attraction";
import { getTopHotplace } from "@/api/hotplace";
import AOS from "aos";
import "aos/dist/aos.css";
import { useRouter } from "vue-router";
import { fail } from "@/utils/error-handler";
import InfoSection from "@/components/common/InfoSection.vue";
import {
  aiChat,
  attraction,
  chat,
  friend,
  like,
  news,
  plan,
  board,
  route,
  search,
  upload,
  weather,
} from "@/assets/icons/index.js";
const router = useRouter();
const attractions = ref([]);
const hotplaces = ref([]);

onMounted(() => {
  AOS.init();
  setAttraction();
  setHotplace();
});
const trip = ref([
  {
    duration: 500,
    hover: false,
    title: "여행지 검색",
    content: "국내의 다양한 여행지를 검색해보세요",
    img: search,
  },
  {
    duration: 1000,
    hover: false,
    title: "여행 계획",
    content: "나만의 여행 계획을 세워보세요",
    img: plan,
  },
  {
    duration: 1500,
    hover: false,
    title: "여행 경로",
    content: "길찾기를 통한 전체 여행 경로를 확인해보세요",
    img: route,
  },
]);

const place = ref([
  {
    duration: 500,
    hover: false,
    title: "나만의 여행지 공유",
    content: "나의 특별한 여행지를 공유해보세요",
    img: upload,
  },
  {
    duration: 1000,
    hover: false,
    title: "핫플레이스",
    content: "다양한 특별한 여행지를 확인해보세요",
    img: attraction,
  },
  {
    duration: 1500,
    hover: false,
    title: "좋아요한 여행지",
    content: "좋아요한 여행지를 한 눈에 확인해보세요",
    img: like,
  },
]);
const community = ref([
  {
    duration: 500,
    hover: false,
    title: "친구",
    content: "여행을 함께 할 친구를 추가해보세요",
    img: friend,
  },
  {
    duration: 1000,
    hover: false,
    title: "채팅",
    content: "친구와 실시간 채팅을 통해 소통해보세요",
    img: chat,
  },
  {
    duration: 1500,
    hover: false,
    title: "자유게시판",
    content: "게시판을 통해 여행 관련 글을 남겨보세요",
    img: board,
  },
]);
const info = ref([
  {
    duration: 500,
    hover: false,
    title: "AI 챗봇",
    content: "AI를 탑재한 챗봇에게 궁금한 것을 물어보세요",
    img: aiChat,
  },
  {
    duration: 1000,
    hover: false,
    title: "최신 뉴스",
    content: "여행 관련 최신 뉴스를 확인해보세요",
    img: news,
  },
  {
    duration: 1500,
    hover: false,
    title: "날씨",
    content: "여행지의 실시간 날씨 정보를 확인해보세요",
    img: weather,
  },
]);

const setAttraction = async () => {
  const success = (response) => {
    attractions.value = response.data;
  };
  await getTopAttractions(success, fail);
};

const setHotplace = async () => {
  const success = (response) => {
    hotplaces.value = response.data;
  };
  await getTopHotplace(success, fail);
};
function getImageUrl(folder, name) {
  return new URL(`/src/assets/upload/${folder}/${name}`, import.meta.url);
}

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
</script>

<template>
  <div
    class="py-[20%] bg-cover bg-center"
    style="background-image: url('/bg_trip.jpg'); height: 370vh">
    <div class="flex flex-col items-center w-full mb-[20%]">
      <div
        class="font-extrabold text-7xl text-white"
        data-aos="fade-down"
        data-aos-easing="ease-in"
        data-aos-duration="1000">
        특별한 여행을 떠나세요
      </div>
    </div>
    <div class="bg-white bg-opacity-10 py-20">
      <InfoSection :info="trip" sectionTitle="편리하게 여행을 계획하세요" />
      <InfoSection
        :info="place"
        sectionTitle="나의 특별한 여행지를 공유해보세요" />
      <InfoSection
        :info="community"
        sectionTitle="여행을 함께 할 메이트를 찾아보세요" />
      <InfoSection :info="info" sectionTitle="여행 관련 정보를 찾아보세요" />
    </div>

    <div data-aos="fade-right" data-aos-duration="1000" class="mb-5">
      <router-link :to="{ name: 'TripView' }" class="no-underline">
        <h1
          class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 relative flex items-center ml-[5%] w-[90vw] text-white font-extrabold text-3xl mb-3">
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
          type="button">
          <img
            :src="attraction.firstImage"
            :alt="attraction.title"
            class="w-full h-full object-cover" />
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
          class="bg-gray-400 bg-opacity-50 shadow-lg rounded-lg p-5 relative flex items-center ml-[5%] w-[90vw] text-white font-extrabold text-3xl mb-3">
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
          type="button">
          <img
            :src="
              getImageUrl(
                hotplace.fileInfo.saveFolder,
                hotplace.fileInfo.saveFile
              )
            "
            :alt="hotplace.placeName"
            class="w-full h-full object-cover" />
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
      data-aos-delay="1500">
      <p>여행을 떠나는 여러분을 응원합니다. 즐거운 여정 되세요!</p>
    </footer>
  </div>
</template>

<style scoped></style>
