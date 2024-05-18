<script setup>
import { ref, onMounted } from "vue";
import { getUser } from "@/api/user.js";
import { useRouter } from "vue-router";
import { deleteFriend } from "@/api/friend.js";
import { getSidoName, getGugunName } from "@/api/getDistricts";
import Swal from "sweetalert2";

const user = ref({});
const router = useRouter();
const props = defineProps({ friend: Object, index: Number });

const addr1 = ref("");
const addr2 = ref("");

onMounted(() => {
  getInfo();
});

const getInfo = async () => {
  const success = async (response) => {
    user.value = response.data;
    addr1.value = await getSidoName(user.value.sido);
    addr2.value = await getGugunName({ gugunCode: user.value.gugun, sidoCode: user.value.sido });
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getUser(props.friend.friendId, success, fail);
};

const goDelete = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "친구가 삭제되었습니다.",
      icon: "success",
      confirmButtonText: "OK",
    }).then(() => {
      router.go(0);
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

  await deleteFriend(
    { userId: props.friend.userId, friendId: props.friend.friendId },
    success,
    fail
  );
};

const goDetail = () => {
  router.push({ name: "FriendFavoriteList", params: { friendId: props.friend.friendId } });
};
</script>

<template>
  <tr class="text-center">
    <th scope="row" @click="goDetail">{{ index + 1 }}</th>
    <td @click="goDetail">
      {{ user.id }}
    </td>
    <td @click="goDetail">
      {{ user.name }}
    </td>
    <td @click="goDetail">{{ user.emailId }}@{{ user.emailDomain }}</td>
    <td @click="goDetail">{{ addr1 }} {{ addr2 }}</td>
    <td><button class="btn btn-outline-danger" @click="goDelete">삭제</button></td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
