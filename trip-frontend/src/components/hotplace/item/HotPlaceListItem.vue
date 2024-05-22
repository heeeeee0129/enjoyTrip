<script setup>
import { ref, onMounted, computed } from "vue";
import {
  checkFavorite,
  deleteFavorite,
  writeFavorite,
} from "@/api/favorite.js";
import { useUserStore } from "@/stores/index";
import Swal from "sweetalert2";

const props = defineProps({ hotplace: Object, index: Number });
import { useRouter } from "vue-router";
import dayjs from "dayjs";

const router = useRouter();
const store = useUserStore();
const isLoggedIn = computed(() => store.isLoggedIn);
const checked = ref(false);

onMounted(() => {
  getCheck();
});

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
      hotNo: props.hotplace.hotNo,
      userId: store.member.id,
    },
    success,
    fail
  );
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
      hotNo: props.hotplace.hotNo,
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
      hotNo: props.hotplace.hotNo,
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
  <div
    class="bg-white rounded-lg shadow-md overflow-hidden transition transform hover:scale-105">
    <div class="relative" @click="goDetail(hotplace.hotNo)" type="button">
      <img
        :src="getImageUrl(ffolder, fname)"
        alt="이미지가 없음"
        class="w-full h-64 object-cover" />
      <div class="absolute bottom-0 left-0 p-4">
        <h3 class="text-xl font-semibold text-white">
          {{ hotplace.placeName }}
        </h3>
      </div>
    </div>
    <div class="p-4">
      <div class="flex justify-between items-center">
        <div>
          <p class="text-gray-700 mb-4">작성자: {{ hotplace.userName }}</p>
          <p class="text-gray-700 mb-4">
            다녀온 날짜: {{ formatDate(hotplace.registerTime) }}
          </p>
        </div>
        <div v-if="isLoggedIn">
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
  </div>
</template>

<style scoped></style>
