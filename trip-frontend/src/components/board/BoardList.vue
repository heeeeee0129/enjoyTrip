<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";

import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

import { useUserStore } from "@/stores/index";
import { useBoardStore } from "@/stores/board";
import { storeToRefs } from "pinia";
const userStore = useUserStore();
const boardStore = useBoardStore();
const isLoggedIn = computed(() => userStore.isLoggedIn);

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "article_no" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const { currentPage } = storeToRefs(boardStore);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  param.value.key = val;
};

const getArticleList = async () => {
  const success = (response) => {
    articles.value = response.data.articles;
    currentPage.value = response.data.currentPage;
    totalPage.value = response.data.totalPageCount;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await listArticle(param.value, success, fail);
};

const onPageChange = async (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  await getArticleList();
};

const moveWrite = () => {
  router.push({ name: "BoardWrite" });
};
</script>

<template>
  <div class="container">
    <!-- 카드 -->
    <div class="row justify-content-center">
      <div class="col-lg-8">
        <div class="card my-3 shadow-sm">
          <div class="card-body">
            <!-- 제목 -->
            <h2 class="card-title text-center py-3">글목록</h2>

            <!-- 검색창 -->
            <div class="flex items-center max-w-md mx-auto bg-white rounded-lg">
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
                글쓰기
              </button>
            </div>

            <!-- 글목록 테이블 -->
            <div class="table-responsive">
              <table class="table table-hover">
                <thead class="text-center">
                  <tr>
                    <th scope="col">글번호</th>
                    <th scope="col">ID</th>
                    <th scope="col">작성자</th>
                    <th scope="col">제목</th>
                    <th scope="col">조회수</th>
                    <th scope="col">작성일</th>
                  </tr>
                </thead>
                <tbody>
                  <BoardListItem
                    v-for="(article, index) in articles"
                    :key="article.articleNo"
                    :index="index + (currentPage - 1) * 20"
                    :article="article"></BoardListItem>
                </tbody>
              </table>
            </div>

            <!-- 페이지 네비게이션 -->
            <PageNavigation
              :current-page="currentPage"
              :total-page="totalPage"
              @pageChange="onPageChange"></PageNavigation>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
