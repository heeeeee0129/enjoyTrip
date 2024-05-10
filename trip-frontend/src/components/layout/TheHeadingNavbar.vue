<script setup>
import { useStore } from "vuex"; // Vuex store 사용
import { computed } from "vue"; // 계산된 속성 사용

const store = useStore(); // Vuex store 인스턴스 가져오기
const isLoggedIn = computed(() => store.state.isLoggedIn); // 로그인 상태를 Vuex store에서 가져옴
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid">
      <router-link :to="{ name: 'main' }" class="navbar-brand">
        <img
          src="@/assets/ssafy_logo.png"
          class="rounded mx-auto d-block"
          alt="..." />
      </router-link>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarScroll"
        aria-controls="navbarScroll"
        aria-expanded="false"
        aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul
          class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll"
          style="--bs-scroll-height: 100px">
          <li class="nav-item">
            <router-link :to="{ name: 'search' }" class="nav-link"
              >여행지검색</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'plan' }" class="nav-link"
              >여행계획</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'board' }" class="nav-link"
              >자유게시판</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'notice' }" class="nav-link"
              >공지사항</router-link
            >
          </li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <template v-if="isLoggedIn">
              <router-link :to="{ name: 'mypage' }" class="nav-link"
                >회원정보 수정</router-link
              >
            </template>
            <template v-else>
              <router-link :to="{ name: 'login' }" class="nav-link"
                >로그인</router-link
              >
            </template>
          </li>
          <li class="nav-item">
            <template v-if="!isLoggedIn">
              <router-link :to="{ name: 'signup' }" class="nav-link"
                >회원가입</router-link
              >
            </template>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
