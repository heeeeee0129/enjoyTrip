<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/board";

const route = useRoute();
const router = useRouter();

// const articleno = ref(route.params.articleno);
const { articleNo } = route.params;

const article = ref({});

onMounted(() => {
  detailArticle();
});

const detailArticle = () => {
  const success = (response) => {
    article.value = response.data;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  getArticle(articleNo, success, fail);
};

function moveList() {
  router.push({ name: "BoardList" });
}

function moveModify() {
  router.push({ name: "BoardModify", params: { articleNo } });
}

function onDeleteArticle() {
  const success = () => {
    console.log("삭제 성공");
    moveList();
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  deleteArticle(articleNo, success, fail);
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">
            {{ article.articleNo }}. {{ article.subject }}
          </h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
              <p>
                <span class="fw-bold">안효인</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }}1 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button
              type="button"
              class="btn btn-outline-primary mb-3"
              @click="moveList">
              글목록
            </button>
            <button
              type="button"
              class="btn btn-outline-success mb-3 ms-1"
              @click="moveModify">
              글수정
            </button>
            <button
              type="button"
              class="btn btn-outline-danger mb-3 ms-1"
              @click="onDeleteArticle">
              글삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
