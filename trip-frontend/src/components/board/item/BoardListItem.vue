<script setup>
defineProps({ article: Object });
import { useRouter } from "vue-router";

const router = useRouter();

const goDetail = (articleNo) => {
  router.push({
    name: "BoardDetail",
    params: {
      articleNo,
    },
  });
};
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.articleNo }}</th>
    <td class="text-start">
      {{ article.userId }}
    </td>
    <td>{{ article.userName }}</td>
    <td>
      <a href="#" @click.prevent="goDetail(article.articleNo)">{{
        article.subject
      }}</a>
    </td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registerTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
