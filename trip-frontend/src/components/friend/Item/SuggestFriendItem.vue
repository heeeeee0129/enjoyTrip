<script setup>
import { ref, onMounted } from "vue";
import { addFriend } from "@/api/friend";
import { getSidoName, getGugunName } from "@/api/getDistricts";
import { useRouter } from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();
const props = defineProps({ suggest: Object, userId: String });

const addr1 = ref("");
const addr2 = ref("");

onMounted(async () => {
  addr1.value = await getSidoName(props.suggest.sido);
  addr2.value = await getGugunName({
    gugunCode: props.suggest.gugun,
    sidoCode: props.suggest.sido,
  });
});

const goAdd = async () => {
  const success = (response) => {
    if (response.data > 0) {
      Swal.fire({
        title: "성공!",
        text: "친구가 추가되었습니다.",
        icon: "success",
        confirmButtonText: "OK",
      }).then(() => {
        router.go(0);
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

  await addFriend(
    { userId: props.userId, friendId: props.suggest.id },
    success,
    fail
  );
};

const goDetail = () => {
  router.push({
    name: "FriendFavoriteList",
    params: { friendId: props.suggest.id },
  });
};
</script>

<template>
  <tr class="text-center">
    <td @click="goDetail">
      {{ suggest.id }}
    </td>
    <td @click="goDetail">
      {{ suggest.name }}
    </td>
    <td @click="goDetail">{{ suggest.emailId }}@{{ suggest.emailDomain }}</td>
    <td @click="goDetail">{{ addr1 }} {{ addr2 }}</td>
    <td>
      <button class="btn btn-outline-primary" @click="goAdd">추가</button>
    </td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
