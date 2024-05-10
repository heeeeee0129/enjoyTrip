<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { writeArticle, getArticle, modifyArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(false);

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: "",
  hit: 0,
  registerTime: "",
});

if (props.type === "modify") {
  let { articleNo } = route.params;

  const success = (response) => {
    article.value = response.data;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  isUseId.value = true;
  getArticle(articleNo, success, fail);
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? registArticle() : updateArticle();
  }
}

function registArticle() {
  const success = () => {
    moveList();
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  writeArticle(article.value, success, fail);
}

function updateArticle() {
  const success = () => {
    moveList();
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  modifyArticle(article.value, success, fail);
}

function moveList() {
  router.replace({ name: "BoardList" });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userId" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.userId"
        :disabled="isUseId"
        placeholder="작성자ID..." />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.subject"
        placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea
        class="form-control"
        v-model="article.content"
        rows="10"></textarea>
    </div>
    <div class="col-auto text-center">
      <button
        type="submit"
        class="btn btn-outline-primary mb-3"
        v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>
        글수정
      </button>
      <button
        type="button"
        class="btn btn-outline-danger mb-3 ms-1"
        @click="moveList">
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
