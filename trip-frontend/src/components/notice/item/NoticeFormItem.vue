<script setup>
import { ref, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/stores";
import { getArticle, writeArticle, modifyArticle } from "@/api/notice.js";

const store = useUserStore(); // Vuex store 인스턴스 가져오기
const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const notice = ref({
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
    notice.value = response.data;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await getArticle(articleNo, success, fail);
};

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => notice.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => notice.value.content,
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
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? registArticle() : updateArticle();
  }
}

const registArticle = async () => {
  const success = () => {
    alert("글이 작성되었습니다");
    moveList();
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await writeArticle(notice.value, success, fail);
};

const updateArticle = async () => {
  const success = () => {
    alert("글이 수정되었습니다");
    moveDetail();
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await modifyArticle(notice.value, success, fail);
};

function moveList() {
  router.replace({ name: "NoticeList" });
}

function moveDetail() {
  router.replace({
    name: "NoticeDetail",
    params: {
      articleNo: notice.value.articleNo,
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
        v-model="notice.userId"
        disabled="true"
        :placeholder="store.member.id"
        style="font-weight: bold"
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 :</label>
      <input
        type="text"
        class="form-control rounded-pill"
        v-model="notice.subject"
        placeholder="제목..."
      />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 :</label>
      <textarea class="form-control rounded" v-model="notice.content" rows="5"></textarea>
    </div>
    <div class="text-center">
      <button type="submit" class="btn btn-primary rounded-pill px-4 me-2" v-if="type === 'regist'">
        글작성
      </button>
      <button type="submit" class="btn btn-success rounded-pill px-4 me-2" v-else>글수정</button>
      <button type="button" class="btn btn-danger rounded-pill px-4" @click="moveList">
        목록으로 이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
