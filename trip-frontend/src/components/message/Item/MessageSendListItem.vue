<script setup>
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps({ message: Object });

const goDetail = async () => {
  router.push({ name: "MessageDetail", params: { messageNo: props.message.messageNo } });
};
</script>

<template>
  <tr class="text-center" @click="goDetail">
    <td>
      {{ message.fromName }}
    </td>
    <td>
      {{ message.toName }}
    </td>
    <td>{{ message.title }}</td>
    <td>{{ message.createdAt }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
