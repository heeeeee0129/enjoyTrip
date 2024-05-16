<template>
  <div class="container">
    <!-- 카드 -->
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <div class="card my-3 shadow-sm">
          <div class="card-body">
            <!-- 제목 -->
            <h2 class="card-title text-center py-3">핫플레이스</h2>

            <!-- 검색창 -->
            <div class="flex items-center max-w-md mx-auto bg-white rounded-lg">
              <VSelect :selectOption="selectOption" />
              <div class="w-full">
                <input
                  type="search"
                  class="w-full px-4 py-1 text-gray-800 rounded-full focus:outline-none"
                  v-model="word"
                  placeholder="검색어..." />
              </div>
              <div>
                <button
                  type="submit"
                  class="flex items-center bg-blue-500 justify-center w-12 h-12 text-white rounded-r-lg"
                  :class="
                    word.length > 0
                      ? 'bg-blue-500'
                      : 'bg-gray-500 cursor-not-allowed'
                  "
                  :disabled="word.length == 0"
                  @click="getArticleList">
                  <svg
                    class="w-5 h-5"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg">
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                  </svg>
                </button>
              </div>
            </div>

            <!-- 글쓰기 버튼 -->
            <div class="text-end mb-3">
              <button
                type="button"
                class="btn btn-outline-primary btn-sm"
                @click="moveWrite"
                v-if="isLoggedIn">
                등록
              </button>
            </div>

            <!-- 카드들을 반복하여 보여줍니다 -->
            <div class="row justify-content-center">
              <HotPlaceListItem
                class="col-8"
                v-for="(hotplace, index) in hotPlaces"
                :key="hotplace.hotNo"
                :index="index"
                :hotplace="hotplace" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/index";
import { listArticles } from "@/api/hotplace.js";
import HotPlaceListItem from "@/components/hotplace/item/HotPlaceListItem.vue";
import VSelect from "@/components/common/VSelect.vue";
import Swal from "sweetalert2";

const router = useRouter();
const userStore = useUserStore();
const isLoggedIn = computed(() => userStore.isLoggedIn);

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "장소", value: "place_name" },
  { text: "작성자", value: "user_name" },
]);
const word = ref("");
const hotPlaces = ref([]);

onMounted(() => {
  getHotPlaceList();
});

const getHotPlaceList = async () => {
  const success = (response) => {
    hotPlaces.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
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
