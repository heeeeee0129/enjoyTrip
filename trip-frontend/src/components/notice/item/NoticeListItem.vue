<script setup>
defineProps({ notice: Object, index: Number });
import { useRouter } from "vue-router";

const router = useRouter();

const goDetail = (articleNo) => {
  router.push({
    name: "NoticeDetail",
    params: {
      articleNo,
    },
  });
};
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ index + 1 }}</th>
    <td>
      {{ notice.userName }}
    </td>
    <td>
      <a href="#" @click.prevent="goDetail(notice.articleNo)">{{
        notice.subject
      }}</a>
    </td>
    <td>{{ notice.hit }}</td>
    <td>{{ notice.registerTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
