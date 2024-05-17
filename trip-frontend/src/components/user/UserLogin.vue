<script setup>
import "@/index.css";
import { login } from "@/api/user";
import { ref } from "vue";
import { useUserStore } from "@/stores"; // Import the Pinia store
import { useRouter, useRoute } from "vue-router";
import Swal from "sweetalert2";

const router = useRouter();
const route = useRoute();
const queryId = route.query.id;
const queryPwd = route.query.pwd;

const store = useUserStore(); // Use the Pinia store

// const isLoggedIn = computed(() => store.isLoggedIn);

const memberId = ref(queryId ? String(queryId) : "");
const password = ref(queryPwd ? String(queryPwd) : "");

const FindPassword = () => {
  router.push({ name: "FindPassword" });
};

const doSignUp = () => {
  router.push({ name: "UserJoin" });
};

const doLogin = () => {
  const member = {
    id: memberId.value,
    pwd: password.value,
    name: "",
    emailId: "",
    emailDomain: "",
    sido: "",
    gugun: "",
  };

  const success = (response) => {
    // console.log(response.data);
    store.login(response.data);
    // console.log(isLoggedIn.value);
    router.push({ name: "main" });
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "로그인에 실패하였습니다. \n",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  login(member, success, fail);
};
</script>

<template>
  <link
    rel="stylesheet"
    href="https://kit-pro.fontawesome.com/releases/v5.15.1/css/pro.min.css" />

  <div
    class="min-h-screen flex flex-col items-center justify-center bg-gray-300">
    <div
      class="flex flex-col bg-white shadow-md px-4 sm:px-6 md:px-8 lg:px-10 py-8 rounded-md w-full max-w-md">
      <div
        class="font-medium self-center text-xl sm:text-2xl uppercase text-gray-800">
        Login To Your Account
      </div>

      <div class="mt-10">
        <form action="#">
          <div class="flex flex-col mb-6">
            <label
              for="email"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >member ID:</label
            >
            <div class="relative">
              <div
                class="inline-flex items-center justify-center absolute left-0 top-0 h-full w-10 text-gray-400">
                <svg
                  class="h-6 w-6"
                  fill="none"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  viewBox="0 0 24 24"
                  stroke="currentColor">
                  <path
                    d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207" />
                </svg>
              </div>

              <input
                id="email"
                type="email"
                name="email"
                class="text-sm sm:text-base placeholder-gray-500 pl-10 pr-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
                placeholder="member id"
                v-model="memberId" />
            </div>
          </div>
          <div class="flex flex-col mb-6">
            <label
              for="password"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Password:</label
            >
            <div class="relative">
              <div
                class="inline-flex items-center justify-center absolute left-0 top-0 h-full w-10 text-gray-400">
                <span>
                  <svg
                    class="h-6 w-6"
                    fill="none"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor">
                    <path
                      d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                  </svg>
                </span>
              </div>

              <input
                id="password"
                type="password"
                name="password"
                class="text-sm sm:text-base placeholder-gray-500 pl-10 pr-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
                placeholder="Password"
                v-model="password" />
            </div>
          </div>

          <div
            class="flex items-center mb-6 -mt-4"
            @click.prevent="FindPassword">
            <div class="flex ml-auto">
              <a
                href="#"
                class="inline-flex text-xs sm:text-sm text-blue-500 hover:text-blue-700"
                >Forgot Your Password?</a
              >
            </div>
          </div>

          <div class="flex w-full">
            <button
              @click.prevent="doLogin"
              class="flex items-center justify-center focus:outline-none text-white text-sm sm:text-base bg-blue-600 hover:bg-blue-700 rounded py-2 w-full transition duration-150 ease-in">
              <!-- <router-link href="#" :to="{ name: 'LoginView' }" -->
              <span class="mr-2 uppercase">Login</span>
              <span>
                <svg
                  class="h-6 w-6"
                  fill="none"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  viewBox="0 0 24 24"
                  stroke="currentColor">
                  <path
                    d="M13 9l3 3m0 0l-3 3m3-3H8m13 0a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </span>
            </button>
          </div>
        </form>
      </div>
      <div
        class="flex justify-center items-center mt-6"
        @click.prevent="doSignUp">
        <a
          href="#"
          target="_blank"
          class="inline-flex items-center font-bold text-blue-500 hover:text-blue-700 text-xs text-center">
          <span>
            <svg
              class="h-6 w-6"
              fill="none"
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              viewBox="0 0 24 24"
              stroke="currentColor">
              <path
                d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
            </svg>
          </span>
          <span class="ml-2">You don't have an account?</span>
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
