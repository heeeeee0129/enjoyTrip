<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { getFavorites } from "@/api/favorite.js";
import { listUserTripPlan } from "@/api/tripplan.js";
import { getUser } from "@/api/user.js";
import FriendFavoriteListItem from "@/components/friend/Item/FriendFavoriteListItem.vue";
import FriendPlanListItem from "@/components/friend/Item/FriendPlanListItem.vue";
import Swal from "sweetalert2";

const favorites = ref([]);
const user = ref({});
const trips = ref([]);
const router = useRouter();
const route = useRoute();
const userId = route.params.friendId;

onMounted(() => {
  setFavorite();
  setTripPlan();
  getUserName();
});

const setTripPlan = async () => {
  const success = (response) => {
    trips.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await listUserTripPlan(userId, success, fail);
};

const setFavorite = async () => {
  const success = (response) => {
    favorites.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getFavorites(userId, success, fail);
};

const getUserName = async () => {
  const success = (response) => {
    user.value = response.data;
  };

  const fail = (error) => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다." + error,
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getUser(userId, success, fail);
};

const moveBack = () => {
  router.go(-1);
};
</script>

<template>
  <div class="p-10 mt-6 min-h-screen">
    <div class="mx-24 my-12">
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">{{ user.name }}({{ user.id }})님이 좋아요한 장소</h2>
      </div>
      <div class="flex justify-start my-12 mx-auto">
        <button
          class="bg-gray-500 hover:bg-gray-600 text-white py-2 px-4 rounded transition transform hover:-translate-y-1 hover:shadow-lg"
          @click="moveBack"
        >
          뒤로가기
        </button>
      </div>

      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="favorite in favorites" :key="favorite.favoriteNo">
        <FriendFavoriteListItem :favorite="favorite" />
      </div>
    </div>
    <div class="mx-24 my-12">
      <hr class="border-gray-400" />
      <div id="detail-header" class="text-center">
        <h2 class="text-3xl mt-4">{{ user.name }}({{ user.id }})님의 여행 계획</h2>
      </div>
      <hr class="border-gray-400" />
    </div>
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-12 px-24">
      <div v-for="trip in trips" :key="trip.tripNo">
        <FriendPlanListItem :trip="trip" />
      </div>
    </div>
  </div>
</template>

<style scoped>
#detail-header {
  margin-bottom: 2rem;
}

/* 이미지 위에 텍스트 오버레이 스타일 */
.relative {
  position: relative;
}

.absolute {
  position: absolute;
}

.bottom-0 {
  bottom: 0;
}

.left-0 {
  left: 0;
}

.p-4 {
  padding: 1rem;
}

.text-white {
  color: #ffffff;
}
button:hover {
  transform: translateY(-2px);
}
</style>
