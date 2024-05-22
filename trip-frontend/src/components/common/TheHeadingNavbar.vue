<script setup>
import { useUserStore } from "@/stores/index.js";
import { ref, computed } from "vue"; // 계산된 속성 사용
import { useRouter } from "vue-router";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faCaretDown } from "@fortawesome/free-solid-svg-icons";

import "@/index.css";
const store = useUserStore();
const router = useRouter();
const isLoggedIn = computed(() => store.isLoggedIn);
import Swal from "sweetalert2";

const doLogout = () => {
  store.logout();
  Swal.fire({
    title: "성공!",
    text: "로그아웃 되었습니다.",
    icon: "success",
    confirmButtonText: "OK",
  }).then(() => {
    router.push({ name: "main" });
  });
};
const isDropdownOpen = ref(false);
const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value;
};
library.add(faCaretDown);
</script>
<template>
  <header class="header py-4 shadow-md fixed w-full top-0 z-50 h-24">
    <div class="container flex flex-wrap items-center justify-between">
      <div class="flex items-center">
        <router-link
          :to="{ name: 'main' }"
          class="text-3xl font-bold text-white">
          EnjoyTrip
        </router-link>
        <nav class="pt-2 text-white font-semibold text-base">
          <ul class="flex items-center space-x-6 pt-2 gap-x-3">
            <li>
              <router-link :to="{ name: 'search' }" class="hover:text-black"
                >여행지검색</router-link
              >
            </li>
            <li>
              <router-link :to="{ name: 'plan' }" class="hover:text-black"
                >여행계획</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'HotPlaceView' }"
                class="hover:text-black"
                >여기좋아요</router-link
              >
            </li>
            <li v-if="isLoggedIn">
              <router-link
                :to="{ name: 'FavoriteView' }"
                class="hover:text-black"
                >관심있는장소</router-link
              >
            </li>
            <li
              v-if="isLoggedIn"
              class="relative"
              @mouseenter="isDropdownOpen = true">
              <button
                @click="toggleDropdown"
                @mouseenter="isDropdownOpen = true"
                class="hover:text-black">
                커뮤니티
                <font-awesome-icon :icon="['fas', 'caret-down']" class="ml-1" />
              </button>
              <ul
                v-show="isDropdownOpen"
                @mouseenter="isDropdownOpen = true"
                @mouseleave="isDropdownOpen = false"
                class="dropdown-menu">
                <li class="hover:bg-black">
                  <router-link
                    :to="{ name: 'FriendView' }"
                    class="dropdown-link"
                    >친구</router-link
                  >
                </li>
                <li class="hover:bg-black">
                  <router-link
                    :to="{ name: 'MessageView' }"
                    class="dropdown-link"
                    >메시지</router-link
                  >
                </li>
                <li class="hover:bg-black">
                  <router-link :to="{ name: 'ChatView' }" class="dropdown-link"
                    >채팅</router-link
                  >
                </li>
                <li class="hover:bg-black">
                  <router-link :to="{ name: 'BoardView' }" class="dropdown-link"
                    >자유게시판</router-link
                  >
                </li>
              </ul>
            </li>
            <li>
              <router-link :to="{ name: 'NewsView' }" class="hover:text-black"
                >뉴스</router-link
              >
            </li>
            <li>
              <router-link :to="{ name: 'NoticeView' }" class="hover:text-black"
                >공지사항</router-link
              >
            </li>
          </ul>
        </nav>
      </div>

      <div class="flex items-center space-x-2">
        <div v-if="isLoggedIn" class="flex items-center space-x-4">
          <router-link :to="{ name: 'UserMypage' }" class="button"
            >마이페이지</router-link
          >
          <button @click.prevent="doLogout" class="button">로그아웃</button>
        </div>
        <div v-else class="flex items-center space-x-4">
          <router-link :to="{ name: 'UserLogin' }" class="button"
            >로그인</router-link
          >
          <router-link :to="{ name: 'UserJoin' }" class="button"
            >회원가입</router-link
          >
        </div>
      </div>
    </div>
  </header>
</template>
<style scoped>
.header {
  background-color: rgba(0, 0, 0, 0.3); /* 반투명 검은색 배경 */
  transition: background-color 0.3s;
}

.header:hover {
  /* background-color: rgba(255, 255, 255, 0.5); 호버 시 하얀색 반투명 배경 */
}

a {
  color: white;
  text-decoration: none;
  transition: color 0.3s, background-color 0.3s;
}

a:hover {
  color: black; /* 호버 시 텍스트 색상 변경 */
}

.button {
  color: white;
  background-color: rgba(0, 0, 0, 0.3); /* 버튼 배경 반투명 검은색 */
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  transition: background-color 0.3s, color 0.3s;
}

.button:hover {
  background-color: #cbd5e0; /* 호버 시 버튼 배경 색상 변경 */
  color: black; /* 호버 시 텍스트 색상 변경 */
}

.dropdown-menu {
  display: none;
  background-color: rgba(255, 255, 255, 0.9);
  border: 1px solid #ddd;
  border-radius: 0.25rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: absolute;
  top: 100%;
  left: 0;
  width: 200px;
  z-index: 1000;
}

.relative:hover .dropdown-menu {
  display: block; /* 부모 요소가 호버될 때 드롭다운 메뉴 표시 */
}

.dropdown-link {
  display: block;
  padding: 0.5rem 1rem;
  color: #333;
  text-decoration: none;
  transition: background-color 0.3s, color 0.3s;
}

.dropdown-link:hover {
  background-color: #f1f1f1;
  color: black; /* 호버 시 텍스트 색상 변경 */
}
</style>
