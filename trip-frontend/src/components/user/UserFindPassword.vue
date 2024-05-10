<script setup>
import { ref } from "vue";
import { findPass } from "@/api/user";
import { useRouter } from "vue-router";
const router = useRouter();
const memberId = ref("");
const findPassword = () => {
  findPass(
    memberId.value,
    (response) => {
      alert(`회원님의 비밀번호는 ${response.data}입니다.`);
      router.push({ name: "UserLogin" });
    },
    (err) => {
      alert(err);
    }
  );
};

const doLogin = () => {
  router.push({ name: "UserLogin" });
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
        Find Your Password
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
                class="inline-flex items-center justify-center absolute left-0 top-0 h-full w-10 text-gray-400"></div>

              <input
                id="email"
                type="email"
                name="email"
                class="text-sm sm:text-base placeholder-gray-500 pl-6 pr-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
                placeholder="member id"
                v-model="memberId" />
            </div>
          </div>
          <div class="flex w-full">
            <button
              @click.prevent="findPassword"
              class="flex items-center justify-center focus:outline-none text-white text-sm sm:text-base bg-blue-600 hover:bg-blue-700 rounded py-2 w-full transition duration-150 ease-in">
              <span class="mr-2 uppercase">FIND!</span>
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
        @click.prevent="doLogin">
        <a
          href="#"
          target="_blank"
          class="inline-flex items-center font-bold text-blue-500 hover:text-blue-700 text-xs text-center">
          <span class="ml-2">Would you like to go to login?</span>
        </a>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
