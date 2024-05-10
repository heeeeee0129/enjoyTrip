<script setup>
import { ref, onMounted, watch } from "vue"; // 반응형 변수 사용
import { modify, getSidos, getGuguns } from "@/api/user.js"; // ���원 가입 API
import { useRouter } from "vue-router";
import { useStore } from "vuex";
const store = useStore();
const userData = store.state.member;
console.log("userData : ", userData);

const router = useRouter();

// 반응형 변수 생성
const memberId = ref(userData.id); // 멤버 ID
const password = ref(userData.pwd); // 비밀번호
const name = ref(userData.name); // 이름
const emailId = ref(userData.emailId); // 이���일 ID
const emailDomain = ref(userData.emailDomain); // 이���일 도��인
const selectedSidoCode = ref(userData.sido);
const selectedGugunCode = ref(userData.gugun);
const selectSidos = ref([]);
const selectGuguns = ref([]);

const fetchSidos = async () => {
  try {
    getSidos(
      (response) => {
        selectSidos.value = response.data;
      },
      (error) => alert(error)
    );
  } catch (error) {
    console.log(error);
  }
};

const fetchGuguns = async () => {
  try {
    if (selectedSidoCode.value) {
      getGuguns(
        selectedSidoCode.value,
        (response) => {
          selectGuguns.value = response.data;
        },
        (error) => alert(error)
      );
    } else {
      selectGuguns.value = [];
    }
  } catch (error) {
    console.log(error);
  }
};
watch(selectedSidoCode, () => {
  selectedGugunCode.value = 0;
  fetchGuguns();
});
// 회원 가입 버튼 클릭 시 실행되는 함수
const doModify = () => {
  // 입력된 정보를 member 객체에 저장
  const member = {
    id: memberId.value, // 멤버 ID
    pwd: password.value, // 비밀번호
    name: name.value, // 이름
    emailId: emailId.value, // 이메일 아이디
    emailDomain: emailDomain.value, // 이메일 도메인
    sido: selectedSidoCode.value, // 시도
    gugun: selectedGugunCode.value, // 구군
  };

  modify(
    member,
    (response) => {
      if (response.data === 1) {
        alert("회원 정보 수정이 완료되었습니다.");
        store.dispatch("doLogin", member);
        router.push({
          name: "main",
        });
      }
    },
    (error) => {
      alert(error);
    }
  );
  console.log(member);

  // 여기에 회원 가입 로직 추가 가능
};
onMounted(() => {
  fetchSidos();
  fetchGuguns();
  selectedSidoCode.value = userData.sido;
  selectedGugunCode.value = userData.gugun;
});
</script>
<template>
  <div
    class="min-h-screen flex flex-col items-center justify-center bg-gray-300">
    <div
      class="flex flex-col bg-white shadow-md px-4 sm:px-6 md:px-8 lg:px-10 py-8 rounded-md w-full max-w-md">
      <div
        class="font-medium self-center text-xl sm:text-2xl uppercase text-gray-800">
        Modify Your Account
      </div>
      <div class="mt-10">
        <form action="#">
          <!-- 멤버 ID 입력 -->
          <div class="flex flex-col mb-6">
            <label
              for="memberId"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Member ID:</label
            >
            <input
              id="memberId"
              type="text"
              name="memberId"
              class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
              placeholder="Member ID"
              v-model="memberId" />
          </div>
          <!-- 비밀번호 입력 -->
          <div class="flex flex-col mb-6">
            <label
              for="password"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Password:</label
            >
            <input
              id="password"
              type="password"
              name="password"
              class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
              placeholder="Password"
              v-model="password" />
          </div>
          <!-- 이름 입력 -->
          <div class="flex flex-col mb-6">
            <label
              for="name"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Name:</label
            >
            <input
              id="name"
              type="text"
              name="name"
              class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
              placeholder="Name"
              v-model="name" />
          </div>
          <!-- 이메일 입력 -->
          <div class="flex flex-col mb-6">
            <label
              for="emailId"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Email:</label
            >
            <div class="flex">
              <input
                id="emailId"
                type="text"
                name="emailId"
                class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-l-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
                placeholder="Email ID"
                v-model="emailId" />
              <span class="px-2 text-sm sm:text-base text-gray-600">@</span>
              <input
                id="emailDomain"
                type="text"
                name="emailDomain"
                class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-r-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400"
                placeholder="Domain"
                v-model="emailDomain" />
            </div>
          </div>
          <!-- 시도 입력 -->
          <div class="flex flex-col mb-6">
            <label
              for="sido"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Sido:</label
            >
            <select
              id="sido"
              name="sido"
              v-model="selectedSidoCode"
              class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400">
              <option value="" disabled selected>Select Sido</option>
              <!-- sido 데이터를 반복하여 option 요소를 생성 -->
              <option
                v-for="sido in selectSidos"
                :key="sido.code"
                :value="sido.code">
                {{ sido.name }}
              </option>
            </select>
          </div>
          <!-- 구군 입력 -->
          <div class="flex flex-col mb-6">
            <label
              for="gugun"
              class="mb-1 text-xs sm:text-sm tracking-wide text-gray-600"
              >Gugun:</label
            >
            <select
              id="gugun"
              name="gugun"
              v-model="selectedGugunCode"
              class="text-sm sm:text-base placeholder-gray-500 pl-4 rounded-lg border border-gray-400 w-full py-2 focus:outline-none focus:border-blue-400">
              <option value="0" disabled selected>Select Gugun</option>
              <!-- sido 데이터를 반복하여 option 요소를 생성 -->
              <option
                v-for="gugun in selectGuguns"
                :key="gugun.code"
                :value="gugun.code">
                {{ gugun.name }}
              </option>
            </select>
          </div>
          <!-- 회원가입 버튼 -->
          <div class="flex w-full">
            <button
              @click.prevent="doModify"
              class="flex items-center justify-center focus:outline-none text-white text-sm sm:text-base bg-blue-600 hover:bg-blue-700 rounded py-2 w-full transition duration-150 ease-in">
              <span class="mr-2 uppercase">Modify</span>
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
    </div>
  </div>
</template>

<style scoped></style>
