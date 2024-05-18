<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores/index.js";
// import { useRouter } from "vue-router";
import { addFriend } from "@/api/friend";
import Swal from "sweetalert2";

const store = useUserStore();
// const router = useRouter();
const friendId = ref("");

const goAdd = async () => {
  const success = (response) => {
    if (response > 0) {
      Swal({
        title: "성공!",
        text: "친구가 삭제되었습니다.",
        icon: "success",
        confirmButtonText: "OK",
      });
    } else if (response == 0) {
      Swal({
        title: "실패!",
        text: "이미 추가된 친구입니다.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    } else {
      Swal({
        title: "실패!",
        text: "존재하지 않는 사용자입니다.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    }
  };

  const fail = () => {
    Swal({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await addFriend({ userId: store.member.id, friendId: friendId.value }, success, fail);
};
</script>

<template>
  <div class="container mt-5">
    <h1 class="mb-4">친구 추가</h1>
    <form @submit.prevent="goAdd" class="mb-4">
      <div class="input-group">
        <input type="text" class="form-control" v-model="friendId" placeholder="ID 입력" required />
        <button class="btn btn-primary" type="submit">친구 추가</button>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
