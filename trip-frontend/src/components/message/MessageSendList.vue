<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/index.js";
import { useRouter } from "vue-router";
import { getSendMessages } from "@/api/message.js";
import MessageSendListItem from "@/components/message/Item/MessageSendListItem.vue";
import Swal from "sweetalert2";

const store = useUserStore();
const router = useRouter();

const messages = ref([]);

onMounted(() => {
  getMessage();
});

const getMessage = async () => {
  const success = (response) => {
    messages.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getSendMessages(store.member.id, success, fail);
};

const moveWrite = () => {
  router.push({ name: "MessageWrite" });
};

const moveBack = () => {
  router.go(-1);
};
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <!-- 제목 -->
          <h2 class="text-center py-3">보낸 메시지 목록</h2>

          <div class="button-container">
            <!-- 받은 메시지 목록 버튼 -->
            <span>
              <button type="button" class="btn btn-outline-success btn-sm" @click="moveBack">
                받은 메시지 목록
              </button>
            </span>

            <!-- 새 메시지 작성 버튼 -->
            <span>
              <button type="button" class="btn btn-outline-primary btn-sm" @click="moveWrite">
                새 메시지 작성
              </button>
            </span>
          </div>

          <!-- 글목록 테이블 -->
          <div class="table-responsive">
            <table class="table table-hover">
              <thead class="text-center">
                <tr>
                  <th scope="col">보낸 사람</th>
                  <th scope="col">받는 사람</th>
                  <th scope="col">제목</th>
                  <th scope="col">보낸 시간</th>
                </tr>
              </thead>
              <tbody>
                <MessageSendListItem
                  v-for="message in messages"
                  :key="message.messageNo"
                  :message="message"
                />
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.button-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem; /* 추가적인 여백을 위해 */
}

.button-container span {
  flex: 0 0 auto;
}
</style>
