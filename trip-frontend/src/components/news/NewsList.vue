<script setup>
import { ref, onMounted } from "vue";
import { listArticle } from "@/api/news.js";
import NewsListItem from "@/components/news/Item/NewsListItem.vue";
import Swal from "sweetalert2";

const newses = ref([]);

onMounted(() => {
  getNews();
});

const getNews = async () => {
  const success = (response) => {
    newses.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listArticle(success, fail);
};
</script>

<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">최신 뉴스</h2>
      </div>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="(news, index) in newses" :key="index">
        <NewsListItem :news="news" />
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
