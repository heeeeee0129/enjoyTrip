<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import {
  checkFavorite,
  deleteFavorite,
  writeFavorite,
} from "@/api/favorite.js";
import { getArticle } from "@/api/hotplace.js";
import { useUserStore } from "@/stores/index";
import Swal from "sweetalert2";

const router = useRouter();
const store = useUserStore();
const props = defineProps({ favorite: Object, userId: String });
const hotplace = ref({});
const ImgPath = ref("");
const checked = ref(false);

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
    getCheck();
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

const getCheck = async () => {
  const success = (response) => {
    if (response.data != 0) {
      checked.value = true;
    } else {
      checked.value = false;
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

  await checkFavorite(
    {
      hotNo: hotplace.value.hotNo,
      userId: store.member.id,
    },
    success,
    fail
  );
};

const goDetail = (hotNo) => {
  router.push({
    name: "HotPlaceDetail",
    params: {
      hotNo,
    },
  });
};

const checkAdd = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "즐겨찾기 목록으로 가시겠습니까?",
      icon: "success",
      confirmButtonText: "OK",
      cancelButtonText: "NO", // 추가: 취소 버튼 텍스트 지정
      showCancelButton: true, // 추가: 취소 버튼 표시
    }).then((result) => {
      if (result.isConfirmed) {
        // 확인 버튼을 눌렀을 때
        router.push({ name: "FavoriteList" }); // 즐겨찾기 목록으로 이동
      } else {
        router.go(0);
      }
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

  await writeFavorite(
    {
      hotNo: hotplace.value.hotNo,
      userId: store.member.id,
    },
    success,
    fail
  );
};

const checkDelete = async () => {
  const success = () => {
    Swal.fire({
      title: "성공!",
      text: "좋아요가 해제되었습니다.",
      icon: "success",
      confirmButtonText: "OK",
    }).then(() => {
      router.go(0);
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
      userId: store.member.id,
    },
    success,
    fail
  );
};
</script>

<template>
  <div
    class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105">
    <div class="relative" @click="goDetail(hotplace.hotNo)" type="button">
      <img
        :src="ImgPath"
        :alt="hotplace.placeName"
        class="w-full h-64 object-cover" />
      <div class="absolute bottom-0 left-0 p-4">
        <h3 class="text-xl font-semibold text-white">
          {{ hotplace.placeName }}
        </h3>
      </div>
    </div>
    <div class="p-4 d-flex justify-content-between align-items-center">
      <p class="text-gray-700 mb-4">작성자: {{ hotplace.userName }}</p>
      <div>
        <button
          class="btn btn-primary rounded-pill px-4"
          v-if="checked"
          @click="checkDelete">
          ❤️
        </button>
        <button
          class="btn btn-outline-primary rounded-pill px-4"
          v-else
          @click="checkAdd">
          ❤️
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
