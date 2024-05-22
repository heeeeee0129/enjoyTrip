<template>
  <div class="max-w-md mx-auto p-4">
    <!-- Chat Container -->
    <div class="bg-white rounded-lg shadow-md p-4">
      <!-- Chat Header -->
      <div class="flex items-center mb-4">
        <div class="ml-3">
          <p class="text-xl font-medium text-blue-500">맛집 추천</p>
          <p class="text-gray-500">Chat-GPT</p>
        </div>
      </div>

      <!-- Chat Messages -->
      <div ref="chatLogRef" class="space-y-4 max-h-128 overflow-y-auto">
        <!-- Chat Log -->
        <div
          v-for="(log, index) in chatLog"
          :key="index"
          :class="
            log.sender === 'user'
              ? 'flex items-end justify-end'
              : 'flex items-start'
          ">
          <template v-if="log.sender === 'bot'">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 100 100"
              width="100"
              height="100"
              fill="#009688"
              class="w-8 h-8 rounded-full">
              <!-- Robot Face -->
              <circle cx="50" cy="50" r="20" fill="#009688" />
              <circle cx="50" cy="40" r="2" fill="#fff" />
              <rect x="47" y="45" width="6" height="10" fill="#fff" />
              <circle cx="50" cy="65" r="3" fill="#009688" />
              <!-- Robot Eyes -->
              <circle cx="45" cy="45" r="3" fill="#fff" />
              <circle cx="55" cy="45" r="3" fill="#fff" />
              <circle cx="45" cy="45" r="1" fill="#000" />
              <circle cx="55" cy="45" r="1" fill="#000" />
              <!-- Robot Antennas -->
              <line
                x1="50"
                y1="30"
                x2="40"
                y2="20"
                stroke="#009688"
                stroke-width="2" />
              <line
                x1="50"
                y1="30"
                x2="60"
                y2="20"
                stroke="#009688"
                stroke-width="2" />
            </svg>
            <div class="ml-3 bg-gray-100 p-3 rounded-lg">
              <p class="text-sm text-gray-800">{{ log.message }}</p>
            </div>
          </template>
          <template v-else>
            <div class="bg-blue-500 p-3 rounded-lg text-right">
              <p class="text-sm text-white">{{ log.message }}</p>
            </div>
            <img
              src="https://pbs.twimg.com/profile_images/1707101905111990272/Z66vixO-_normal.jpg"
              alt="User Avatar"
              class="w-8 h-8 rounded-full ml-3" />
          </template>
        </div>
      </div>

      <!-- Chat Input -->
      <div class="mt-4 flex items-center">
        <input
          v-model="chatGPT.userMsg"
          type="text"
          placeholder="Type your message..."
          class="flex-1 py-2 px-3 rounded-full bg-gray-100 focus:outline-none"
          @keyup.enter="sendMessage" />
        <button
          @click="sendMessage"
          class="bg-blue-500 text-white px-4 py-2 rounded-full ml-3 hover:bg-blue-600">
          Send
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from "vue";
import { getChatResponse } from "@/api/chatgpt.js";
import Swal from "sweetalert2";

const chatGPT = ref({
  userMsg: "",
  gptMsg: "",
});

const chatLog = ref([
  {
    sender: "bot",
    message: "어디로 놀러가시나요? 맛집을 추천해드릴게요!",
  },
]);

const chatLogRef = ref(null);

const sendMessage = () => {
  if (chatGPT.value.userMsg.trim() === "") return;

  chatLog.value.push({ sender: "user", message: chatGPT.value.userMsg });

  getChat();
  chatGPT.value.userMsg = "";
};

const getChat = async () => {
  const success = async (response) => {
    chatGPT.value = response.data;
    chatLog.value.push({ sender: "bot", message: chatGPT.value.gptMsg });
    await nextTick();
    scrollToBottom();
  };

  const fail = () => {
    Swal.fire({
      title: "실패!",
      text: "문제가 발생헀습니다.",
      icon: "error",
      confirmButtonText: "OK",
    });
  };

  await getChatResponse(chatGPT.value, success, fail);
};

const scrollToBottom = () => {
  const chatLogElement = chatLogRef.value;
  chatLogElement.scrollTop = chatLogElement.scrollHeight;
};
</script>

<style scoped>
.max-w-md {
  max-width: 28rem;
}
.mx-auto {
  margin-left: auto;
  margin-right: auto;
}
.p-4 {
  padding: 1rem;
}
.bg-white {
  background-color: white;
}
.rounded-lg {
  border-radius: 0.5rem;
}
.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.flex {
  display: flex;
}
.items-center {
  align-items: center;
}
.mb-4 {
  margin-bottom: 1rem;
}
.ml-3 {
  margin-left: 0.75rem;
}
.text-xl {
  font-size: 1.25rem;
}
.font-medium {
  font-weight: 500;
}
.text-gray-500 {
  color: #6b7280;
}
.space-y-4 > :not([hidden]) ~ :not([hidden]) {
  --tw-space-y-reverse: 0;
  margin-top: calc(1rem * calc(1 - var(--tw-space-y-reverse)));
  margin-bottom: calc(1rem * var(--tw-space-y-reverse));
}
.items-start {
  align-items: flex-start;
}
.w-8 {
  width: 2rem;
}
.h-8 {
  height: 2rem;
}
.rounded-full {
  border-radius: 9999px;
}
.bg-gray-100 {
  background-color: #f3f4f6;
}
.p-3 {
  padding: 0.75rem;
}
.text-sm {
  font-size: 0.875rem;
}
.text-gray-800 {
  color: #1f2937;
}
.items-end {
  align-items: flex-end;
}
.justify-end {
  justify-content: flex-end;
}
.bg-blue-500 {
  background-color: #3b82f6;
}
.text-white {
  color: white;
}
.mt-4 {
  margin-top: 1rem;
}
.flex-1 {
  flex: 1;
}
.py-2 {
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
}
.px-3 {
  padding-left: 0.75rem;
  padding-right: 0.75rem;
}
.rounded-full {
  border-radius: 9999px;
}
.bg-gray-100 {
  background-color: #f3f4f6;
}
.focus\:outline-none:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
}
.hover\:bg-blue-600:hover {
  background-color: #2563eb;
}
.max-h-128 {
  max-height: 32rem;
}
</style>
