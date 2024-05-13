<script setup>
import { useUserStore } from "@/stores/index.js";
import { computed } from "vue"; // 계산된 속성 사용
import "@/index.css";
const store = useUserStore();
const isLoggedIn = computed(() => store.isLoggedIn);

const doLogout = () => {
  store.logout();
  alert("로그아웃 되었습니다.");
};
</script>

<template>
  <header class="text-gray-600 body-fon py-4 shadow-sm">
    <div
      class="container mx-auto flex flex-wrap flex-col md:flex-row items-center">
      <a class="flex title-font font-medium items-center text-gray-900">
        <router-link :to="{ name: 'main' }" class="font-bold text-xl">
          <!-- <img
            src="@/assets/ssafy_logo.png"
            class="w-12 h-12"
            alt="SSAFY Logo" /> -->
          <span class="ml-3 text-xl font-semibold no-underline">EnjoyTrip</span>
        </router-link>
      </a>
      <nav
        class="md:mr-auto md:ml-4 md:py-1 md:pl-4 md:border-l md:border-gray-400 flex flex-wrap items-center text-base justify-center">
        <ul class="flex items-center space-x-4 lg:space-x-8 my-auto">
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
              :to="{ name: 'BoardView' }"
              class="hover:text-gray-300 no-underline"
              >자유게시판</router-link
            >
          </li>
          <li>
            <router-link
              :to="{ name: 'notice' }"
              class="hover:text-gray-300 no-underline"
              >공지사항</router-link
            >
          </li>
        </ul>
      </nav>

      <ul class="flex items-center space-x-2 lg:space-x-8 my-auto gap-y-4">
        <div v-if="isLoggedIn">
          <div class="flex items-center">
            <router-link
              :to="{ name: 'UserMypage' }"
              class="hover:text-gray-300 pr-6 no-underline flex items-center text-sm">
              <span class="text-gray-900 mx-2"
                >[ {{ store.member.name }}]님</span
              ><span class="text-gray-900">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 512 512"
                  width="25">
                  <path
                    fill="#363636"
                    d="M406.5 399.6C387.4 352.9 341.5 320 288 320H224c-53.5 0-99.4 32.9-118.5 79.6C69.9 362.2 48 311.7 48 256C48 141.1 141.1 48 256 48s208 93.1 208 208c0 55.7-21.9 106.2-57.5 143.6zm-40.1 32.7C334.4 452.4 296.6 464 256 464s-78.4-11.6-110.5-31.7c7.3-36.7 39.7-64.3 78.5-64.3h64c38.8 0 71.2 27.6 78.5 64.3zM256 512A256 256 0 1 0 256 0a256 256 0 1 0 0 512zm0-272a40 40 0 1 1 0-80 40 40 0 1 1 0 80zm-88-40a88 88 0 1 0 176 0 88 88 0 1 0 -176 0z" />
                </svg>
              </span>
            </router-link>
            <button
              @click.prevent="doLogout"
              class="shadow-sm inline-flex items-center bg-gray-200 border-0 py-2 px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900">
              로그아웃
            </button>
          </div>
        </div>
        <div v-else>
          <router-link
            :to="{ name: 'UserLogin' }"
            class="inline-flex items-center bg-gray-200 border-0 py-2 shadow-sm px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900 mr-4">
            로그인
          </router-link>
          <router-link
            :to="{ name: 'UserJoin' }"
            class="inline-flex items-center bg-gray-200 border-0 py-2 shadow-sm px-3 focus:outline-none hover:bg-gray-300 rounded text-sm text-gray-900 mr-4">
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
