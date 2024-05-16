<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { writeArticle, getArticle, modifyArticle } from "@/api/board";
import { useUserStore } from "@/stores";
import Swal from "sweetalert2";

const store = useUserStore(); // Vuex store 인스턴스 가져오기
const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const article = ref({
  articleNo: 0,
  userId: store.member.id,
  userName: "",
  subject: "",
  content: "",
  hit: 0,
  registerTime: "",
});

onMounted(() => {
  if (props.type === "modify") {
    setArticle();
  }
});

const setArticle = async () => {
  let { articleNo } = route.params;

  const success = (response) => {
    article.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "Cool",
    });
  };

  await getArticle(articleNo, success, fail);
};

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
  if (subjectErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: subjectErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (contentErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: contentErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else {
    props.type === "regist" ? registArticle() : updateArticle();
  }
}

const registArticle = async () => {
  const success = (response) => {
    if (response.data === 1) {
      Swal.fire({
        title: "성공!",
        text: "글이 작성되었습니다",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        moveList();
      });
    } else {
      Swal.fire({
        title: "실패!",
        text: "비속어가 포함되어있습니다. 다시 작성해주세요.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    }
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생했습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await writeArticle(article.value, success, fail);
};

const updateArticle = async () => {
  const success = (response) => {
    if (response.data === 1) {
      Swal.fire({
        title: "성공!",
        text: "글이 수정되었습니다",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        moveDetail();
      });
    } else {
      Swal.fire({
        title: "실패!",
        text: "비속어가 포함되어있습니다. 다시 작성해주세요.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    }
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생했습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await modifyArticle(article.value, success, fail);
};

function moveList() {
  router.replace({ name: "BoardList" });
}

function moveDetail() {
  router.replace({
    name: "BoardDetail",
    params: {
      articleNo: article.value.articleNo,
    },
  });
}
</script>

<template>
  <form @submit.prevent="onSubmit" class="mb-4 p-4 rounded-lg shadow bg-light">
    <div class="mb-3">
      <label for="userId" class="form-label">작성자 ID:</label>
      <input
        type="text"
        class="form-control rounded-pill"
        v-model="article.userId"
        disabled="true"
        :placeholder="store.member.id"
        style="font-weight: bold" />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 :</label>
      <input
        type="text"
        class="form-control rounded-pill"
        v-model="article.subject"
        placeholder="제목..." />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 :</label>
      <textarea
        class="form-control rounded"
        v-model="article.content"
        rows="5"></textarea>
    </div>
    <div class="text-center">
      <button
        type="submit"
        class="btn btn-primary rounded-pill px-4 me-2"
        v-if="type === 'regist'">
        글작성
      </button>
      <button
        type="submit"
        class="btn btn-success rounded-pill px-4 me-2"
        v-else>
        글수정
      </button>
      <button
        type="button"
        class="btn btn-danger rounded-pill px-4"
        @click="moveList">
        목록으로 이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
