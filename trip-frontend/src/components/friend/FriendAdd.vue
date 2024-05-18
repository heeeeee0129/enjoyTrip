<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/index.js";
import { useRouter } from "vue-router";
import { addFriend } from "@/api/friend";
import { getUserList } from "@/api/user";
import UserListItem from "@/components/friend/Item/UserListItem.vue";
import Swal from "sweetalert2";

const store = useUserStore();
const router = useRouter();
const friendId = ref("");
const users = ref([]);

onMounted(() => {
  getUsers();
});

const getUsers = async () => {
  const success = (response) => {
    users.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getUserList(success, fail);
};

const goAdd = async () => {
  const success = (response) => {
    if (response.data > 0) {
      Swal.fire({
        title: "성공!",
        text: "친구가 추가되었습니다.",
        icon: "success",
        confirmButtonText: "OK",
      });
    } else if (response.data == 0) {
      Swal.fire({
        title: "실패!",
        text: "이미 추가된 친구입니다.",
        icon: "warning",
        confirmButtonText: "OK",
      });
    } else if (response.data == -2) {
      Swal.fire({
        title: "실패!",
        text: "자기 자신은 추가할 수 없습니다.",
        icon: "warning",
        confirmButtonText: "OK",
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
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await addFriend({ userId: store.member.id, friendId: friendId.value }, success, fail);
};

const goBack = () => {
  router.go(-1);
};
</script>

<template>
  <div class="container mt-5 text-center">
    <div>
      <h1 class="mb-4">친구 추가</h1>
      <button @click="goBack" class="btn btn-secondary float-start mb-6">뒤로가기</button>
    </div>
    <form @submit.prevent="goAdd" class="mb-4">
      <div class="input-group">
        <input type="text" class="form-control" v-model="friendId" placeholder="ID 입력" required />
        <button class="btn btn-primary" type="submit">친구 추가</button>
      </div>
    </form>
  </div>

  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <!-- 제목 -->
          <h2 class="text-center py-3">전체 사용자 목록</h2>

          <!-- 글목록 테이블 -->
          <div class="table-responsive">
            <table class="table table-hover">
              <thead class="text-center">
                <tr>
                  <th scope="col">사용자번호</th>
                  <th scope="col">ID</th>
                  <th scope="col">이름</th>
                  <th scope="col">Email</th>
                  <th scope="col">주소</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                <UserListItem
                  v-for="(user, index) in users"
                  :key="user.userId"
                  :index="index"
                  :user="user"
                />
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
