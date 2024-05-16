<template>
  <div>
    <!-- 댓글 목록 표시 -->
    <div class="mt-3">
      <div class="bg-light rounded p-2 my-2 d-flex align-items-center">
        <div>
          <div class="fw-bold">
            {{ comment.userName }}({{ comment.userId }})
          </div>
          <div class="mt-2">
            <!-- 수정 가능한 입력 필드 -->
            <div v-if="isEditing">
              <textarea
                v-model="editedComment.content"
                class="form-control"></textarea>
            </div>
            <!-- 댓글 내용 -->
            <div v-else>
              <p class="mb-0 fs-5">{{ comment.content }}</p>
            </div>
          </div>
          <div class="mt-2 text-secondary smaller-text">
            {{ comment.registerTime }}
          </div>
        </div>
        <div class="ms-auto">
          <!-- 수정 버튼 -->
          <button
            v-if="!isEditing && userStore.member.id === comment.userId"
            class="btn btn-outline-success btn-sm me-2"
            @click="isEditing = true">
            수정
          </button>
          <!-- 저장 버튼 -->
          <button
            v-if="isEditing && userStore.member.id === comment.userId"
            class="btn btn-outline-primary btn-sm me-2"
            @click="saveEdit">
            저장
          </button>
          <!-- 삭제 버튼 -->
          <button
            class="btn btn-outline-danger btn-sm"
            @click="confirmDelete"
            v-if="userStore.member.id === comment.userId">
            삭제
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";
import { deleteComment, modifyComment } from "@/api/comment";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/index";
import Swal from "sweetalert2";

const props = defineProps({ comment: Object });
const userStore = useUserStore();
const router = useRouter();
let isEditing = ref(false);

let editedComment = ref({
  replyNo: props.comment.replyNo,
  articleNo: 0,
  userId: userStore.member.id,
  userName: "",
  content: props.comment.content,
  registerTime: "",
});

const saveEdit = async () => {
  const success = (response) => {
    if (response.data === 1) {
      Swal.fire({
        title: "성공!",
        text: "댓글이 수정되었습니다",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        router.go(0);
      });
    } else {
      Swal.fire({
        title: "실패!",
        text: "비속어가 포함되어있습니다. 다시 작서앻주세요.",
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

  await modifyComment(editedComment.value, success, fail);
};

const onDeleteComment = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "댓글이 삭제되었습니다",
      icon: "success",
      confirmButtonText: "OK",
    }).then(() => {
      router.go(0);
    });
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생했습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await deleteComment(props.comment.replyNo, success, fail);
};

const confirmDelete = () => {
  Swal.fire({
    title: "정말로 삭제 하시겠습니까?",
    text: "다시 되돌릴 수 없습니다. 신중하세요.",
    icon: "question",

    showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
    confirmButtonColor: "#3085d6", // confirm 버튼 색깔 지정
    cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
    confirmButtonText: "삭제", // confirm 버튼 텍스트 지정
    cancelButtonText: "취소", // cancel 버튼 텍스트 지정
    reverseButtons: true, // 버튼 순서 거꾸로
  }).then((result) => {
    // 만약 Promise리턴을 받으면,
    if (result.isConfirmed) {
      // 만약 모달창에서 confirm 버튼을 눌렀다면
      onDeleteComment();
    }
  });
};
</script>

<style scoped>
.smaller-text {
  font-size: 0.8rem;
}
</style>
