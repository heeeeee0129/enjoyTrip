<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getMessage } from "@/api/message.js";
import MessageReWrite from "@/components/message/MessageReWrite.vue";
import Swal from "sweetalert2";

const route = useRoute();
const router = useRouter();

const { messageNo } = route.params;

const message = ref({});
const isWrite = ref(false);

onMounted(() => {
  detailMessage();
});

const detailMessage = async () => {
  const success = (response) => {
    message.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getMessage(messageNo, success, fail);
};

const changeIsWrite = () => {
  isWrite.value = !isWrite.value;
};

function moveBack() {
  router.go(-1);
}
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-10">
      <div class="card my-3 shadow">
        <div class="card-body">
          <div class="row my-2">
            <h2 class="text-secondary px-3 rounded-lg">
              {{ message.title }}
            </h2>
          </div>
          <div class="row align-items-center">
            <div class="col-md-8">
              <div class="d-flex align-items-center">
                From.
                <img
                  class="avatar me-2 bg-light p-2 rounded-circle"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                  style="width: 40px; height: 40px" />
                <div class="ms-2">
                  <p class="mb-1">
                    <span class="fw-bold"
                      >{{ message.fromName }}({{ message.fromId }})</span
                    >
                  </p>
                </div>
              </div>
              <div class="d-flex align-items-center">
                To.
                <img
                  class="avatar me-2 bg-light p-2 rounded-circle"
                  src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
                  style="width: 40px; height: 40px" />
                <div class="ms-2">
                  <p class="mb-1">
                    <span class="fw-bold"
                      >{{ message.toName }}({{ message.toId }})</span
                    >
                  </p>
                </div>
              </div>
            </div>
            <div class="col-md-4 text-end">
              <p class="mt-3">
                <span class="text-secondary fw-light">
                  {{ message.createdAt }}
                </span>
              </p>
            </div>
          </div>
          <div
            class="bg-white rounded p-3"
            style="min-width: 250px; min-height: 300px">
            <div class="text-secondary">
              {{ message.content }}
            </div>
          </div>
          <div class="d-flex justify-content-end mt-3">
            <button
              type="button"
              class="btn btn-outline-secondary me-1 rounded-pill"
              @click="moveBack">
              뒤로가기
            </button>
            <button
              type="button"
              class="btn btn-outline-primary me-1 rounded-pill"
              @click="changeIsWrite">
              답장
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <MessageReWrite
    v-if="isWrite"
    :toId="message.fromId"
    @changeIsWrite="changeIsWrite" />
</template>

<style scoped></style>
