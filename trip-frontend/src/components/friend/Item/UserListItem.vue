<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getSidoName, getGugunName } from "@/api/getDistricts";
import { useUserStore } from "@/stores";
import Swal from "sweetalert2";
import { addFriend } from "@/api/friend";

const router = useRouter();
const props = defineProps({ user: Object, index: Number });
const store = useUserStore();

const addr1 = ref("");
const addr2 = ref("");

onMounted(async () => {
  addr1.value = await getSidoName(props.user.sido);
  addr2.value = await getGugunName({ gugunCode: props.user.gugun, sidoCode: props.user.sido });
});

const goDetail = () => {
  router.push({ name: "FriendFavoriteList", params: { friendId: props.user.id } });
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

  await addFriend({ userId: store.member.id, friendId: props.user.id }, success, fail);
};
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ index + 1 }}</th>
    <td @click="goDetail">
      {{ user.id }}
    </td>
    <td @click="goDetail">
      {{ user.name }}
    </td>
    <td @click="goDetail">{{ user.emailId }}@{{ user.emailDomain }}</td>
    <td @click="goDetail">{{ addr1 }} {{ addr2 }}</td>
    <td>
      <button class="btn btn-outline-primary" @click="goAdd" v-if="user.id != store.member.id">
        추가
      </button>
    </td>
  </tr>
</template>

<style scoped></style>
