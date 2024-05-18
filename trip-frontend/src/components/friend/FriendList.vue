<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/stores/index.js";
import { useRouter } from "vue-router";
import { listFriends } from "@/api/friend.js";
import FriendListItem from "@/components/friend/Item/FriendListItem.vue";
import Swal from "sweetalert2";

const store = useUserStore();
const router = useRouter();

const friends = ref([]);

onMounted(() => {
  getFriends();
});

const getFriends = async () => {
  const success = (response) => {
    friends.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listFriends(store.member.id, success, fail);
};

const moveAdd = () => {
  router.push({ name: "FriendAdd" });
};
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8">
      <div class="card my-3 shadow-sm">
        <div class="card-body">
          <!-- 제목 -->
          <h2 class="text-center py-3">친구목록</h2>

          <!-- 친구추가 버튼 -->
          <div class="text-end mb-3">
            <button type="button" class="btn btn-outline-primary btn-sm" @click="moveAdd">
              친구추가
            </button>
          </div>

          <!-- 글목록 테이블 -->
          <div class="table-responsive">
            <table class="table table-hover">
              <thead class="text-center">
                <tr>
                  <th scope="col">친구번호</th>
                  <th scope="col">ID</th>
                  <th scope="col">이름</th>
                  <th scope="col">Email</th>
                  <th scope="col">주소</th>
                  <th scope="col"></th>
                </tr>
              </thead>
              <tbody>
                <FriendListItem
                  v-for="(friend, index) in friends"
                  :key="friend.friendNo"
                  :index="index"
                  :friend="friend"
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
