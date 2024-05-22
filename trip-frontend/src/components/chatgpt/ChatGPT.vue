<script setup>
import { ref, nextTick } from "vue";
import { getChatResponse } from "@/api/chatgpt.js"; // api.js에서 정의한 함수를 import 합니다.
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
  if (chatGPT.value.userMsg.trim() === "") return; // 빈칸

  // 사용자 메시지를 로그에 추가
  chatLog.value.push({ sender: "user", message: chatGPT.value.userMsg });

  // API 호출
  getChat();
  chatGPT.value.userMsg = ""; // 입력 필드를 초기화
};

const getChat = async () => {
  const success = async (response) => {
    chatGPT.value = response.data;
    chatLog.value.push({ sender: "bot", message: chatGPT.value.gptMsg });
    await nextTick(); // DOM 업데이트 후에 실행
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

<template>
  <div class="text-center" style="margin-top: 20px"><h2>Chat-GPT</h2></div>
  <div class="chat-container">
    <div ref="chatLogRef" class="chat-log">
      <div
        v-for="(log, index) in chatLog"
        :key="index"
        :class="[log.sender, 'bubble']">
        <span>{{ log.sender }}: {{ log.message }}</span>
      </div>
    </div>
    <div class="chat-input">
      <input
        v-model="chatGPT.userMsg"
        @keyup.enter="sendMessage"
        placeholder="Type a message..." />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 70vh;
  max-width: 600px;
  margin: 20px auto 0 auto;
  border: 1px solid #ccc;
  border-radius: 8px;
  overflow: hidden;
}

.chat-log {
  flex: 1;
  padding: 10px;
  overflow-y: auto;
  background-color: #f9f9f9;
}

.bubble {
  max-width: 70%; /* 말풍선 최대 너비 설정 */
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 10px;
}

.user {
  align-self: flex-end; /* 사용자 말풍선을 오른쪽에 정렬 */
  background-color: #007bff; /* 사용자 말풍선 색상 */
  color: white;
}

.bot {
  align-self: flex-start; /* 봇 말풍선을 왼쪽에 정렬 */
  background-color: #f9f9f9; /* 봇 말풍선 색상 */
  color: black;
}

.chat-input {
  display: flex;
  border-top: 1px solid #ccc;
}

.chat-input input {
  flex: 1;
  padding: 10px;
  border: none;
  border-right: 1px solid #ccc;
}

.chat-input button {
  padding: 10px 20px;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.chat-input button:hover {
  background-color: #0056b3;
}
</style>
