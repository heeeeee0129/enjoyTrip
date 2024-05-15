<template>
  <div class="container mt-5">
    <div class="row justify-content-between align-items-center">
      <div class="col-8">
        <h2 class="text-secondary text-center">
          <mark class="red">핫플레이스!!!</mark>
        </h2>
        <!-- 검색창 및 검색 조건 select 박스 -->
        <div class="flex items-center max-w-md mx-auto bg-white rounded-lg">
          <select
            class="form-select form-select-sm ms-5 me-1 w-50 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent bg-gradient-to-r from-pink-200 to-purple-200 text-gray-800"
            aria-label="검색 조건 선택"
          >
            <option
              selected
              class="text-gray-900 dark:text-white bg-white border-b border-gray-300"
            >
              장소
            </option>
            <option
              value="1"
              class="text-gray-900 dark:text-white bg-white border-b border-gray-300"
            >
              작성자
            </option>
          </select>
          <div class="w-full">
            <input
              type="search"
              class="w-full px-4 py-1 text-gray-800 rounded-full focus:outline-none"
              v-model="word"
              placeholder="검색어..."
            />
          </div>
          <button
            type="submit"
            class="flex items-center bg-blue-500 justify-center w-12 h-12 text-white rounded-r-lg"
            :class="word.length > 0 ? 'bg-blue-500' : 'bg-gray-500 cursor-not-allowed'"
            :disabled="word.length == 0"
          >
            <svg
              class="w-5 h-5"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
              ></path>
            </svg>
          </button>
        </div>
      </div>
      <!-- 등록 버튼 -->
      <div class="col-4">
        <button class="btn btn-outline-primary" @click="moveWrite" v-if="isLoggedIn">
          핫플레이스 등록
        </button>
      </div>
    </div>

    <!-- 카드들을 반복하여 보여줍니다 -->
    <div class="row justify-content-center">
      <HotPlaceListItem
        class="col-8"
        v-for="(hotplace, index) in hotPlaces"
        :key="hotplace.hotNo"
        :index="index"
        :hotplace="hotplace"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/index";
import { listArticles } from "@/api/hotplace.js";
import HotPlaceListItem from "@/components/hotplace/item/HotPlaceListItem.vue";

const router = useRouter();
const userStore = useUserStore();
const isLoggedIn = computed(() => userStore.isLoggedIn);

const word = ref("");

const hotPlaces = ref([]);

onMounted(() => {
  getHotPlaceList();
});

const getHotPlaceList = async () => {
  const success = (response) => {
    hotPlaces.value = response.data;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await listArticles(success, fail);
};

const moveWrite = () => {
  router.push({ name: "HotPlaceWrite" });
};
</script>

<style>
/* 여기에 추가적인 스타일링을 할 수 있습니다 */
</style>
