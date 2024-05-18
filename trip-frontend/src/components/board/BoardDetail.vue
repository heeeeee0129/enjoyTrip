<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getArticle, deleteArticle } from "@/api/board";
import { listComment, writeComment, getCountComment } from "@/api/comment";
import { useUserStore } from "@/stores/index";
import BoardCommentItem from "@/components/board/item/BoardCommentItem.vue";
import Swal from "sweetalert2";

const userStore = useUserStore();
const isLoggedIn = computed(() => userStore.isLoggedIn);

const route = useRoute();
const router = useRouter();

const { articleNo } = route.params;

const article = ref({});

const newComment = ref({
  articleNo,
  replyNo: 0,
  userId: userStore.member.id,
  userName: "",
  content: "",
  registerTime: "",
  parentReplyNo: 0,
});

const comments = ref([]);
const count = ref(0);

onMounted(() => {
  detailArticle();
  getComments();
  getCount();
});

const detailArticle = async () => {
  const success = (response) => {
    article.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getArticle(articleNo, success, fail);
};

const getComments = async () => {
  const success = (response) => {
    comments.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listComment(articleNo, success, fail);
};

const getCount = async () => {
  const success = (response) => {
    count.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getCountComment(articleNo, success, fail);
};

function moveList() {
  router.push({ name: "BoardList" });
}

function moveModify() {
  router.push({ name: "BoardModify", params: { articleNo } });
}

const onDeleteArticle = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "글이 삭제되었습니다.",
      icon: "success",
      confirmButtonText: "OK",
    }).then(() => {
      moveList();
    });
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await deleteArticle(articleNo, success, fail);
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
      onDeleteArticle();
    }
  });
};

const registComment = async () => {
  const success = (response) => {
    if (response.data === 1) {
      Swal.fire({
        title: "성공!",
        text: "댓글이 작성되었습니다",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        newComment.value.content = "";
        getComments();
        getCount();
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
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await writeComment(newComment.value, success, fail);
};
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-10">
      <div class="card my-3 shadow">
        <div class="card-body">
          <div class="row my-2">
            <h2 class="text-secondary px-3 rounded-lg">
              {{ article.subject }}
            </h2>
          </div>
          <div class="row align-items-center">
            <div class="col-md-8">
              <div class="d-flex align-items-center">
                <img
                  class="avatar me-2 bg-light p-2 rounded-circle"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                  style="width: 40px; height: 40px"
                />
                <div class="ms-2">
                  <p class="mb-1">
                    <span class="fw-bold">{{ article.userName }}({{ article.userId }})</span>
                  </p>
                </div>
              </div>
            </div>
            <div class="col-md-4 text-end">
              <span class="badge bg-first rounded-pill p-2 border border-secondary">
                <span class="text-dark">댓글 : {{ count }}</span>
              </span>
              <span class="badge bg-first rounded-pill p-2 border border-secondary ms-2">
                <span class="text-dark">조회수: {{ article.hit }}</span>
              </span>
              <p class="mt-3">
                <span class="text-secondary fw-light">
                  {{ article.registerTime }}
                </span>
              </p>
            </div>
          </div>
          <div class="bg-white rounded p-3" style="min-width: 250px; min-height: 300px">
            <div class="text-secondary">
              {{ article.content }}
            </div>
          </div>
          <div class="d-flex justify-content-end mt-3">
            <button
              type="button"
              class="btn btn-outline-secondary me-1 rounded-pill"
              @click="moveList"
            >
              글목록
            </button>
            <button
              type="button"
              class="btn btn-outline-success me-1 rounded-pill"
              @click="moveModify"
              v-if="userStore.member.id === article.userId"
            >
              글수정
            </button>
            <button
              type="button"
              class="btn btn-outline-danger rounded-pill"
              @click="confirmDelete"
              v-if="userStore.member.id === article.userId"
            >
              글삭제
            </button>
          </div>
          <BoardCommentItem
            v-for="comment in comments"
            :key="comment.replyNo"
            :comment="comment"
            @get-count="getCount"
          />
          <!-- 댓글 작성 폼 -->
          <div class="mt-3" v-if="isLoggedIn">
            <textarea
              v-model="newComment.content"
              class="form-control rounded"
              rows="3"
              placeholder="댓글을 입력하세요..."
            ></textarea>
            <div class="d-flex justify-content-end mt-2">
              <button
                type="button"
                class="btn btn-outline-primary me-1 rounded-pill"
                @click="registComment"
              >
                작성
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
