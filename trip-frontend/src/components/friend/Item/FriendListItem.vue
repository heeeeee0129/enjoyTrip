<script setup>
import { ref, onMounted } from "vue";
import { getUser } from "@/api/user.js";
import { deleteFriend } from "@/api/friend.js";
import Swal from "sweetalert2";

const user = ref({});

onMounted(() => {
  getInfo();
});

const props = defineProps({ friend: Object, index: Number });

const getInfo = async () => {
  const success = (response) => {
    user.value = response.data;
  };

  const fail = () => {
    Swal({
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
    Swal({
      title: "성공!",
      text: "친구가 삭제되었습니다.",
      icon: "success",
      confirmButtonText: "OK",
    });
  };

  const fail = () => {
    Swal({
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
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ index + 1 }}</th>
    <td>
      {{ user.userId }}
    </td>
    <td>
      {{ user.userName }}
    </td>
    <td>{{ user.emailId }}@{{ user.emailDomain }}</td>
    <td>{{ user.sido }} {{ user.gugun }}</td>
    <td><button @click="goDelete">삭제</button></td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
