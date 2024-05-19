<script setup>
import { ref, watch } from "vue";
import { useRouter } from "vue-router";
import { writeMessage } from "@/api/message.js";
import { useUserStore } from "@/stores";
import Swal from "sweetalert2";

const store = useUserStore(); // Vuex store 인스턴스 가져오기
const router = useRouter();

const message = ref({
  messageNo: 0,
  fromId: store.member.id,
  toId: "",
  title: "",
  content: "",
});

const toErrMsg = ref("");
const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => message.value.toId,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 16) {
      toErrMsg.value = "받는 사람을 확인해 주세요!!!";
    } else toErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => message.value.title,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => message.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 1000) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  if (toErrMsg.value) {
    Swal.fire({
      title: "실패!",
      text: toErrMsg.value,
      icon: "warning",
      confirmButtonText: "OK",
    });
  } else if (subjectErrMsg.value) {
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
    registArticle();
  }
}

const registArticle = async () => {
  const success = (response) => {
    if (response.data === 1) {
      Swal.fire({
        title: "성공!",
        text: "메시지가 전송되었습니다",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        moveList();
      });
    } else {
      Swal.fire({
        title: "실패!",
        text: "존재하지 않는 사용자입니다.",
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

  await writeMessage(message.value, success, fail);
};

function moveBack() {
  router.go(-1);
}

function moveList() {
  router.replace({ name: "MessageList" });
}
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-10">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <h2 class="py-3 text-center">메시지 작성</h2>
        </div>
        <form @submit.prevent="onSubmit" class="mb-4 p-4 rounded-lg shadow bg-light">
          <div class="mb-3">
            <label for="userId" class="form-label">작성자 ID:</label>
            <input
              type="text"
              class="form-control rounded-pill"
              v-model="message.userId"
              disabled="true"
              :placeholder="store.member.id"
              style="font-weight: bold"
            />
          </div>
          <div class="mb-3">
            <label for="subject" class="form-label">받는사람 :</label>
            <input
              type="text"
              class="form-control rounded-pill"
              v-model="message.toId"
              placeholder="받는사람..."
            />
          </div>
          <div class="mb-3">
            <label for="subject" class="form-label">제목 :</label>
            <input
              type="text"
              class="form-control rounded-pill"
              v-model="message.title"
              placeholder="제목..."
            />
          </div>
          <div class="mb-3">
            <label for="content" class="form-label">내용 :</label>
            <textarea class="form-control rounded" v-model="message.content" rows="5"></textarea>
          </div>
          <div class="text-center">
            <button
              type="button"
              class="btn btn-secondary rounded-pill px-4 me-2"
              @click="moveBack"
            >
              뒤로가기
            </button>
            <button type="submit" class="btn btn-primary rounded-pill px-4">전송</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
