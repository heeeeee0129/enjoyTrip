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
  <header class="text-gray-600 py-4 shadow-sm">
    <div
      class="container flex flex-wrap text-base justify-between flex-row sm:flex-auto">
      <div class="flex flex-row">
        <a class="flex title-font font-medium items-center text-gray-900">
          <router-link :to="{ name: 'main' }" class="font-bold text-xl">
            <span class="ml-1 text-xl font-semibold no-underline"
              >EnjoyTrip</span
            >
          </router-link>
        </a>
        <nav class="flex flex-wrap text-base justify-around">
          <ul class="flex items-center space-x-4 lg:space-x-10 my-auto">
            <li>
              <router-link
                :to="{ name: 'search' }"
                class="hover:text-gray-300 no-underline"
                >여행지검색</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'plan' }"
                class="hover:text-gray-300 no-underline"
                >여행계획</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'HotPlaceView' }"
                class="hover:text-gray-300 no-underline"
                >여기좋아요</router-link
              >
            </li>

            <li v-if="isLoggedIn">
              <router-link
                :to="{ name: 'FavoriteView' }"
                class="hover:text-gray-300 no-underline"
                >관심있는장소</router-link
              >
            </li>

            <li v-if="isLoggedIn" @mouseenter="isDropdownOpen = true">
              <button
                class="hover:text-gray-300 no-underline"
                @click="toggleDropdown"
                @mouseenter="isDropdownOpen = true">
                커뮤니티
                <font-awesome-icon :icon="['fas', 'caret-down']" class="ml-1" />
              </button>
              <ul
                @mouseenter="isDropdownOpen = true"
                @mouseleave="isDropdownOpen = false"
                v-show="isDropdownOpen"
                class="absolute bg-white border rounded shadow-md mt-2 w-48 z-10"
                style="display: none">
                <li class="hover:bg-gray-100">
                  <router-link
                    :to="{ name: 'FriendView' }"
                    class="block p-2 text-sm m-1 text-gray-700 hover:text-gray-900"
                    >친구</router-link
                  >
                </li>
                <li class="hover:bg-gray-100">
                  <router-link
                    :to="{ name: 'MessageView' }"
                    class="block p-2 text-sm m-1 text-gray-700 hover:text-gray-900"
                    >메시지</router-link
                  >
                </li>
                <li class="hover:bg-gray-100">
                  <router-link
                    :to="{ name: 'ChatView' }"
                    class="block p-2 text-sm m-1 text-gray-700 hover:text-gray-900"
                    >채팅</router-link
                  >
                </li>
                <li class="hover:bg-gray-100">
                  <router-link
                    :to="{ name: 'BoardView' }"
                    class="block p-2 text-sm m-1 text-gray-700 hover:text-gray-900"
                    >자유게시판</router-link
                  >
                </li>
              </ul>
            </li>
            <li>
              <router-link
                :to="{ name: 'NewsView' }"
                class="hover:text-gray-300 no-underline"
                >뉴스</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'NoticeView' }"
                class="hover:text-gray-300 no-underline"
                >공지사항</router-link
              >
            </li>
          </ul>
        </nav>
      </div>

      <ul
        class="flex flex-row-reverse items-center space-x-4 lg:space-x-10 my-auto mt-1">
        <div v-if="isLoggedIn">
          <div class="flex items-center">
            <router-link
              :to="{ name: 'UserMypage' }"
              class="mx-6 inline-flex items-center bg-gray-200 border-0 py-2 shadow-sm px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900 mr-4">
              마이페이지
            </router-link>
            <button
              @click.prevent="doLogout"
              class="shadow-sm inline-flex items-center bg-gray-200 border-0 py-2 px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900 mx-6">
              로그아웃
            </button>
          </div>
        </div>
        <div v-else>
          <router-link
            :to="{ name: 'UserLogin' }"
            class="mx-6 inline-flex items-center bg-gray-200 border-0 py-2 shadow-sm px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900 mr-4">
            로그인
          </router-link>
          <router-link
            :to="{ name: 'UserJoin' }"
            class="mx-6 inline-flex items-center bg-gray-200 border-0 py-2 shadow-sm px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900 mr-4">
            회원가입
          </router-link>
        </div>
      </ul>
    </div>
  </header>
</template>

<style scoped>
a {
  color: inherit; /* 부모 요소의 색상 상속 */
  text-decoration: none; /* 밑줄 제거 */
  transition: color 0.3s; /* 호버 시 색상 변화를 부드럽게 만들기 위한 transition 속성 추가 */
}

a:hover {
  color: #4a5568; /* 호버 시 색상 변경 */
}

button:hover {
  background-color: #cbd5e0; /* 호버 시 배경색 변경 */
  color: #4a5568; /* 호버 시 텍스트 색상 변경 */
}
</style>
