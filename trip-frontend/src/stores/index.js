// store.js

import { defineStore } from "pinia";
import { ref } from "vue";

export const useUserStore = defineStore(
  "user",
  () => {
    const isLoggedIn = ref(false);
    const member = ref({
      id: "", // 멤버 ID
      pwd: "", // 비밀번호
      name: "", // 이름
      emailId: "", // 이메일 아이디
      emailDomain: "", // 이메일 도메인
      sido: "", // 시도
      gugun: "", // 구군
    });
    const login = (userData) => {
      isLoggedIn.value = true; // 로그인 상태로 변경
      member.value = userData;
      // console.log(member.value);
    };
    const logout = () => {
      isLoggedIn.value = false; // 로그아웃 상태로 변경
    };
    return { isLoggedIn, member, login, logout };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  }
);
