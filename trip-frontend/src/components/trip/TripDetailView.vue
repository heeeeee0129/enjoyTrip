<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getAttraction } from "@/api/attraction.js";
import Swal from "sweetalert2";

const router = useRouter();
const route = useRoute();
const { contentId } = route.params;

const attraction = ref({});

onMounted(async () => {
  detailAttracion();
});

const detailAttracion = async () => {
  const success = (response) => {
    attraction.value = response.data;
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getAttraction(contentId, success, fail);
};

const goBack = () => {
  router.go(-1);
};

const kakaoMapLink = computed(() => {
  const { latitude, longitude, title } = attraction.value;
  return `https://map.kakao.com/link/to/${encodeURIComponent(title)},${latitude},${longitude}`;
});

const naverSearchLink = computed(() => {
  const { title } = attraction.value;
  return `https://search.naver.com/search.naver?query=${encodeURIComponent(title)}`;
});
</script>

<template>
  <div class="container mt-4">
    <div class="card">
      <img :src="attraction.firstImage" class="card-img-top" alt="이미지 없음" />
      <div class="card-body">
        <h5 class="card-title">{{ attraction.title }}</h5>
        <h6 class="card-subtitle mb-2 text-muted">
          {{ attraction.addr1 }}, {{ attraction.addr2 }}
        </h6>
        <p class="card-text">{{ attraction.overview }}</p>
        <ul class="list-group list-group-flush">
          <li class="list-group-item"><strong>전화번호:</strong> {{ attraction.tel }}</li>
          <li class="list-group-item"><strong>우편번호:</strong> {{ attraction.zipcode }}</li>
          <li class="list-group-item"><strong>위도:</strong> {{ attraction.latitude }}</li>
          <li class="list-group-item"><strong>경도:</strong> {{ attraction.longitude }}</li>
        </ul>
      </div>
      <div class="card-footer d-flex justify-content-end">
        <button @click="goBack" class="btn btn-secondary mr-2">돌아가기</button>
        <a
          :href="kakaoMapLink"
          class="btn btn-warning mr-2"
          target="_blank"
          rel="noopener noreferrer"
          >카카오맵 길찾기</a
        >
        <a :href="naverSearchLink" class="btn btn-success" target="_blank" rel="noopener noreferrer"
          >네이버 검색</a
        >
      </div>
    </div>
  </div>
</template>

<style scoped></style>
