<script setup>
const props = defineProps({ article: Object, index: Number });
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listComment } from "@/api/comment";

const router = useRouter();
const comments = ref([]);

onMounted(() => {
  getComments();
});

const getComments = async () => {
  const success = (response) => {
    comments.value = response.data;
  };

  const fail = (error) => {
    alert("문제가 발생헀습니다.", error);
  };

  await listComment(props.article.articleNo, success, fail);
};

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
    <th scope="row">{{ index + 1 }}</th>
    <td>
      {{ article.userId }}
    </td>
    <td>{{ article.userName }}</td>
    <td>
      <a href="#" @click.prevent="goDetail(article.articleNo)">{{
        article.subject
      }}</a>
      <span v-if="comments.length > 0">({{ comments.length }})</span>
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
