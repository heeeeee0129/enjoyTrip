<script setup>
import { useRouter } from "vue-router";
import { updateRead } from "@/api/message.js";

const router = useRouter();
const props = defineProps({ message: Object });

const goDetail = async () => {
  await updateRead(props.message);
  router.push({ name: "MessageDetail", params: { messageNo: props.message.messageNo } });
};
</script>

<template>
  <tr class="text-center" @click="goDetail" :class="{ read: message.readed }">
    <td :class="{ read: message.readed }">
      {{ message.fromName }}
    </td>
    <td :class="{ read: message.readed }">
      {{ message.toName }}
    </td>
    <td :class="{ read: message.readed }">{{ message.title }}</td>
    <td :class="{ read: message.readed }">{{ message.createdAt }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}

.read {
  background-color: #b8b6c0;
}
</style>
