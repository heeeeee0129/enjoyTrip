<script setup>
import { ref } from "vue";

const props = defineProps({ hotplace: Object, index: Number });
import { useRouter } from "vue-router";

const router = useRouter();

const goDetail = (hotNo) => {
  router.push({
    name: "HotPlaceDetail",
    params: {
      hotNo,
    },
  });
};

const ffolder = ref(props.hotplace.fileInfo.saveFolder);
const fname = ref(props.hotplace.fileInfo.saveFile);

function getImageUrl(folder, name) {
  return new URL(`/src/assets/upload/${folder}/${name}`, import.meta.url);
}
</script>

<template>
  <div class="card mb-3" style="position: relative">
    <img
      :src="getImageUrl(ffolder, fname)"
      :alt="getImageUrl(ffolder, fname)"
      style="max-width: 100%; height: auto; display: block"
    />
    <div class="card-body" style="position: absolute; top: 0; left: 0; width: 100%">
      <h5 class="card-title">{{ hotplace.placeName }}</h5>
      <p class="card-text">작성자: {{ hotplace.userName }}</p>
      <p class="card-text">다녀온 날짜: {{ hotplace.registerTime }}</p>
      <a
        href="#"
        class="btn btn-outline-primary"
        @click="goDetail(hotplace.hotNo)"
        style="position: absolute; bottom: 10px; right: 10px"
        >자세히 보기</a
      >
    </div>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
