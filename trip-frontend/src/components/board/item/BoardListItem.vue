<script setup>
const props = defineProps({ article: Object, index: Number });
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listComment, getCountComment } from "@/api/comment";
import Swal from "sweetalert2";

const router = useRouter();
const comments = ref([]);
const count = ref(0);

onMounted(() => {
  getComments();
  getCount();
});

const getComments = async () => {
  const success = (response) => {
    comments.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listComment(props.article.articleNo, success, fail);
};

const getCount = async () => {
  const success = (response) => {
    count.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getCountComment(props.article.articleNo, success, fail);
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
      <span v-if="count > 0">({{ count }})</span>
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
