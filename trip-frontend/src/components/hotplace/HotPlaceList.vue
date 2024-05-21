<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <!-- 제목 -->
        <h2 class="text-3xl py-3">여기 좋아요</h2>
        <!-- 검색창 -->
        <div
          class="flex items-center max-w-md mx-auto bg-white rounded-lg mt-6">
          <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
          <div class="w-full">
            <input
              type="search"
              class="w-full px-4 py-1 text-gray-800 rounded-full focus:outline-none"
              v-model="param.word"
              placeholder="검색어..." />
          </div>
          <div>
            <button
              type="submit"
              class="flex items-center bg-blue-500 justify-center w-12 h-12 text-white rounded-r-lg"
              :class="
                param.word.length > 0
                  ? 'bg-blue-500'
                  : 'bg-gray-500 cursor-not-allowed'
              "
              :disabled="param.word.length == 0"
              @click="getHotPlaceList">
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
      </div>
      <div class="flex justify-end my-12 mx-auto">
        <button
          class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          @click="moveWrite"
          v-if="isLoggedIn">
          작성하러 가기
        </button>
      </div>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="(hotplace, index) in hotPlaces" :key="hotplace.hotNo">
        <HotPlaceListItem :index="index" :hotplace="hotplace" />
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
const param = ref({
  key: "",
  word: "",
});

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

  await listArticles(param.value, success, fail);
};

const moveWrite = () => {
  router.push({ name: "HotPlaceWrite" });
};

const changeKey = (val) => {
  param.value.key = val;
};
</script>

<style scoped>
#detail-header {
  margin-bottom: 2rem;
}

/* 이미지 위에 텍스트 오버레이 스타일 */
.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.bottom-0 {
  bottom: 0;
}

.left-0 {
  left: 0;
}

.p-4 {
  padding: 1rem;
}

.text-white {
  color: #ffffff;
}
button:hover {
  transform: translateY(-2px);
}
</style>
