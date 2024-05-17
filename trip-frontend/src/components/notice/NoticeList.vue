<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticles } from "@/api/notice.js";
import { useUserStore } from "@/stores/index";

import NoticeListItem from "@/components/notice/item/NoticeListItem.vue";

const userStore = useUserStore();

const router = useRouter();
const notices = ref([]);

onMounted(() => {
  getArticleList();
});

const getArticleList = async () => {
  const success = (response) => {
    notices.value = response.data;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await listArticles(success, fail);
};

const moveWrite = () => {
  router.push({ name: "NoticeWrite" });
};
</script>

<template>
  <!-- 카드 -->
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <!-- 제목 -->
          <h2 class="text-center py-3">공지사항</h2>

          <!-- 글쓰기 버튼 -->
          <div class="text-end mb-3">
            <button
              type="button"
              class="btn btn-outline-primary btn-sm"
              @click="moveWrite"
              v-if="userStore.member.id === 'admin'"
            >
              글쓰기
            </button>
          </div>

          <!-- 글목록 테이블 -->
          <div class="table-responsive">
            <table class="table table-hover">
              <thead class="text-center">
                <tr>
                  <th scope="col">글번호</th>
                  <th scope="col">작성자</th>
                  <th scope="col">제목</th>
                  <th scope="col">조회수</th>
                  <th scope="col">작성일</th>
                </tr>
              </thead>
              <tbody>
                <NoticeListItem
                  v-for="(notice, index) in notices"
                  :key="notice.articleNo"
                  :index="index"
                  :notice="notice"
                ></NoticeListItem>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
