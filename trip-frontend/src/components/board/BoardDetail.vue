<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/board";
import { useStore } from "vuex"; // Vuex store 사용

const store = useStore(); // Vuex store 인스턴스 가져오기

const route = useRoute();
const router = useRouter();

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
    alert("글이 삭제되었습니다");
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
        <div class="card my-3 shadow">
          <div class="card-body">
            <div class="row my-2">
              <h2 class="text-secondary px-3 rounded-lg">
                {{ article.articleNo }}. {{ article.subject }}
              </h2>
            </div>
            <div class="row align-items-center">
              <div class="col-md-8">
                <div class="d-flex align-items-center">
                  <img
                    class="avatar me-2 bg-light p-2 rounded-circle"
                    src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                    style="width: 40px; height: 40px" />
                  <div class="ms-2">
                    <p class="mb-1">
                      <span class="fw-bold"
                        >{{ article.userName }}({{ article.userId }})</span
                      >
                    </p>
                  </div>
                </div>
              </div>
              <div class="col-md-4 text-end">
                <span class="badge bg-secondary rounded-pill p-2"
                  >댓글 : 17</span
                >
                <p class="mt-3">
                  <span class="text-secondary fw-light">
                    {{ article.registerTime }} 조회 : {{ article.hit }}
                  </span>
                </p>
              </div>
            </div>
            <div
              class="bg-white rounded p-3"
              style="min-width: 250px; min-height: 300px">
              <div class="text-secondary">
                {{ article.content }}
              </div>
            </div>
            <div class="d-flex justify-content-end mt-3">
              <button
                type="button"
                class="btn btn-outline-primary me-1 rounded-pill"
                @click="moveList">
                글목록
              </button>
              <button
                type="button"
                class="btn btn-outline-success me-1 rounded-pill"
                @click="moveModify"
                v-if="store.state.member.id === article.userId">
                글수정
              </button>
              <button
                type="button"
                class="btn btn-outline-danger rounded-pill"
                @click="onDeleteArticle"
                v-if="store.state.member.id === article.userId">
                글삭제
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
