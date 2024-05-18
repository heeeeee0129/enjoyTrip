<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/index";
import { suggestAttraction } from "@/api/attraction.js";
import SuggestListItem from "@/components/favorite/Item/SuggestListItem.vue";
import Swal from "sweetalert2";

const attractions = ref([]);
const store = useUserStore();
const router = useRouter();

onMounted(() => {
  getAttractionList();
});

const getAttractionList = async () => {
  const success = (response) => {
    attractions.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "Error!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await suggestAttraction({ userId: store.member.id }, success, fail);
};

const moveBack = () => {
  router.go(-1);
};
</script>

<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">좋아요를 누른 곳과 비슷한 장소</h2>
      </div>
      <div class="flex justify-end my-12 mx-auto">
        <button
          class="bg-gray-500 hover:bg-blue-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          @click="moveBack"
        >
          뒤로가기
        </button>
      </div>

      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="attraction in attractions" :key="attraction.contentId">
        <SuggestListItem :attraction="attraction" />
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
