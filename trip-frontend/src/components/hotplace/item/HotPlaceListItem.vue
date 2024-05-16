<script setup>
import { ref } from "vue";

const props = defineProps({ hotplace: Object, index: Number });
import { useRouter } from "vue-router";
import dayjs from "dayjs";

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

const formatDate = (dateString) => {
  return dayjs(dateString).format("YYYY-MM-DD");
};
</script>

<template>
  <div class="card mb-3" style="position: relative">
    <img
      :src="getImageUrl(ffolder, fname)"
      alt="이미지가 없음"
      style="max-width: 100%; height: auto; display: block" />
    <div
      class="card-body text-center"
      style="
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 100%;
      ">
      <h5 class="card-title" style="color: white; font-size: 2.5rem">
        {{ hotplace.placeName }}
      </h5>
      <p class="card-text" style="color: white">
        작성자: {{ hotplace.userName }}
      </p>
      <p class="card-text" style="color: white">
        다녀온 날짜: {{ formatDate(hotplace.registerTime) }}
      </p>
      <p
        class="card-button"
        style="
          position: absolute;
          bottom: 10px;
          right: 10px;
          font-size: 1.5rem;
        ">
        <a
          href="#"
          class="btn btn-primary"
          @click="goDetail(hotplace.hotNo)"
          style="color: white"
          >자세히 보기</a
        >
      </p>
    </div>
  </div>
</template>

<style scoped></style>
