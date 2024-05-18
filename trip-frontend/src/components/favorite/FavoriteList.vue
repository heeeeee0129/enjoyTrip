<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/index.js";
import { getFavorites } from "@/api/favorite.js";
import { useRouter } from "vue-router";
import FavoriteListItem from "@/components/favorite/Item/FavoriteListItem.vue";
import Swal from "sweetalert2";
const store = useUserStore();
const router = useRouter();
const favorites = ref([]);

onMounted(() => {
  setFavorite();
});

const setFavorite = async () => {
  const success = (response) => {
    favorites.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getFavorites(store.member.id, success, fail);
};

const showAlert = () => {
  Swal.fire({
    title: "좋아요를 눌러보세요!",
    text: "구경하러 가볼까요?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "OK",
    cancelButtonText: "취소",
  }).then((result) => {
    if (result.isConfirmed) {
      router.push({ name: "HotPlaceView" });
    }
  });
};
</script>

<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">관심 있는 장소 ({{ favorites.length }}개)</h2>
      </div>
      <div class="flex justify-between items-center my-12 mx-auto max-w-md">
        <router-link :to="{ name: 'SuggestList' }" class="text-white" v-if="favorites.length > 0">
          <button
            class="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          >
            여행지 추천
          </button>
        </router-link>

        <button
          class="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          v-else
          @click="showAlert"
        >
          여행지 추천
        </button>

        <router-link :to="{ name: 'HotPlaceView' }" class="text-white">
          <button
            class="bg-blue-500 hover:bg-blue-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          >
            추가하러 가기
          </button>
        </router-link>
      </div>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="favorite in favorites" :key="favorite.favoriteNo">
        <FavoriteListItem :favorite="favorite" :userId="store.member.id" />
      </div>
    </div>
  </div>
</template>

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
