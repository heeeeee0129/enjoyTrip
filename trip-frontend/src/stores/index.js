// store.js

import { createStore } from "vuex";

export default createStore({
  state: {
    isLoggedIn: false, // 초기 상태는 로그인되지 않은 상태
    member: {
      id: "", // 멤버 ID
      pwd: "", // 비밀번호
      name: "", // 이름
      emailId: "", // 이메일 아이디
      emailDomain: "", // 이메일 도메인
      sido: "", // 시도
      gugun: "", // 구군
    },
  },
  mutations: {
    login(state, userData) {
      state.isLoggedIn = true; // 로그인 상태로 변경
      state.member = userData;
      console.log(state.member);
    },
    logout(state) {
      state.isLoggedIn = false; // 로그아웃 상태로 변경
    },
  },
  actions: {
    // 로그인 액션
    doLogin({ commit }, userData) {
      // 실제 로그인 로직 수행 후
      commit("login", userData); // 상태를 변경하는 뮤테이션 호출
    },
    // 로그아웃 액션
    doLogout({ commit }) {
      // 실제 로그아웃 로직 수행 후
      commit("logout"); // 상태를 변경하는 뮤테이션 호출
    },
  },
});
