<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getArticle } from "@/api/hotplace.js";
import { deleteFavorite } from "@/api/favorite.js";
import Swal from "sweetalert2";

const router = useRouter();
const props = defineProps({ favorite: Object, userId: String });
const hotplace = ref({});
const ImgPath = ref("");

onMounted(() => {
  getAttract(props.favorite.hotNo);
});

const getAttract = async (hotNo) => {
  const success = (response) => {
    hotplace.value = response.data;
    ImgPath.value = getImageUrl(
      hotplace.value.fileInfo.saveFolder,
      hotplace.value.fileInfo.saveFile
    );
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getArticle(hotNo, success, fail);
};

function getImageUrl(folder, name) {
  return new URL(`/src/assets/upload/${folder}/${name}`, import.meta.url);
}

const goDetail = (hotNo) => {
  router.push({
    name: "HotPlaceDetail",
    params: {
      hotNo,
    },
  });
};

const checkDelete = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "좋아요가 해제되었습니다.",
      icon: "success",
      confirmButtonText: "OK",
    });
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await deleteFavorite(
    {
      hotNo: hotplace.value.hotNo,
      userId: props.userId,
    },
    success,
    fail
  );
};
</script>

<template>
  <div
    class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105"
    @click="goDetail(hotplace.hotNo)"
  >
    <div class="relative">
      <img :src="ImgPath" :alt="hotplace.placeName" class="w-full h-64 object-cover" />
      <div class="absolute bottom-0 left-0 p-4">
        <h3 class="text-xl font-semibold text-white">
          {{ hotplace.placeName }}
        </h3>
      </div>
    </div>
    <div class="p-4 d-flex justify-content-between align-items-center">
      <p class="text-gray-700 mb-4">작성자: {{ hotplace.userName }}</p>
      <button type="button" class="btn btn-info rounded-pill px-4" @click="checkDelete">❤️</button>
    </div>
  </div>
</template>

<style scoped></style>
