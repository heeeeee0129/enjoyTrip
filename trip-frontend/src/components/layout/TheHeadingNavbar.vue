<script setup>
import { useStore } from "vuex"; // Vuex store 사용
import { computed, ref } from "vue"; // 계산된 속성 사용
import "@/index.css";

const store = useStore(); // Vuex store 인스턴스 가져오기
const isLoggedIn = computed(() => store.state.isLoggedIn); // 로그인 상태를 Vuex store에서 가져옴

const doLogout = () => {
  store.dispatch("doLogout");
  alert("로그아웃 되었습니다.");
}; // 로그아웃 세션을 Vuex store에서 가져�

const isOpen = ref(false); // 모바일에서 메뉴 열고 닫기를 제어하는 변수
</script>

<template>
  <nav class="bg-blue-500">
    <div class="container mx-auto">
      <div class="flex justify-between items-center py-1">
        <router-link
          :to="{ name: 'main' }"
          class="text-white font-bold text-xl">
          <img
            src="@/assets/ssafy_logo.png"
            class="w-12 h-12"
            alt="SSAFY Logo" />
        </router-link>
        <!-- 모바일용 햄버거 메뉴 -->
        <button
          class="text-white focus:outline-none lg:hidden"
          @click="isOpen = !isOpen">
          <svg
            class="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg">
            <path
              :class="[isOpen ? 'block' : 'hidden']"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
        <!-- 모바일에서 열린 메뉴 -->
        <div
          :class="[
            isOpen ? 'block' : 'hidden',
            'lg:flex lg:items-center lg:w-auto',
          ]">
          <!-- 왼쪽에 배치된 메뉴 항목들 -->
          <ul class="flex items-center space-x-4 lg:space-x-8 my-auto">
            <li>
              <router-link
                :to="{ name: 'search' }"
                class="text-white hover:text-gray-300 no-underline"
                >여행지검색</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'plan' }"
                class="text-white hover:text-gray-300 no-underline"
                >여행계획</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'board' }"
                class="text-white hover:text-gray-300 no-underline"
                >자유게시판</router-link
              >
            </li>
            <li>
              <router-link
                :to="{ name: 'notice' }"
                class="text-white hover:text-gray-300 no-underline"
                >공지사항</router-link
              >
            </li>
          </ul>

          <!-- 로그인 상태에 따른 회원정보 수정과 회원가입 -->
          <ul class="flex items-center space-x-2 lg:space-x-8 my-auto">
            <div class="px-3 text-white">|</div>
            <div v-if="isLoggedIn">
              <div class="flex items-center space-x-2">
                <router-link
                  :to="{ name: 'UserMypage' }"
                  class="text-white hover:text-gray-300 pr-8 no-underline">
                  [{{ store.state.member.name }}]님</router-link
                >
                <span
                  class="text-white hover:text-gray-300 cursor-pointer"
                  @click.prevent="doLogout"
                  >로그아웃</span
                >
              </div>
            </div>
            <div v-else>
              <router-link
                :to="{ name: 'UserLogin' }"
                class="text-white hover:text-gray-300 pr-8 no-underline"
                >로그인</router-link
              >
              <router-link
                :to="{ name: 'UserJoin' }"
                class="text-white hover:text-gray-300 no-underline"
                >회원가입</router-link
              >
            </div>
          </ul>
        </div>
      </div>
    </div>
  </nav>
</template>

<style scoped>
/* 추가적인 스타일링은 필요한 경우 여기에 작성합니다. */
</style>
