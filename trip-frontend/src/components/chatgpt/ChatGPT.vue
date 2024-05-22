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
        :class="['bubble-container', log.sender]">
        <div :class="[log.sender, 'bubble']">
          <span>{{ log.message }}</span>
        </div>
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
  border: 1px solid #ddd;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.chat-log {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f3f3f3;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.bubble-container {
  display: flex;
  width: 100%;
}

.user {
  justify-content: flex-end;
}

.bot {
  justify-content: flex-start;
}

.bubble {
  display: inline-block; /* 크기를 내용에 맞춤 */
  max-width: 75%;
  padding: 10px 15px;
  border-radius: 20px;
  font-size: 16px;
  line-height: 1.5;
  position: relative;
  animation: fadeIn 0.5s ease-in-out;
  word-wrap: break-word; /* 긴 단어가 있을 때 줄바꿈 */
}

.user .bubble {
  background-color: #007bff;
  color: white;
  border-bottom-right-radius: 5px;
  border-top-left-radius: 20px;
  text-align: right;
  margin-left: auto; /* 사용자 말풍선을 오른쪽에 정렬 */
}

.bot .bubble {
  background-color: #ebebeb;
  color: black;
  border-bottom-left-radius: 5px;
  border-top-right-radius: 20px;
}

.chat-input {
  display: flex;
  border-top: 1px solid #ddd;
}

.chat-input input {
  flex: 1;
  padding: 15px;
  border: none;
  border-right: 1px solid #ddd;
  font-size: 16px;
}

.chat-input button {
  padding: 15px 20px;
  border: none;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.chat-input button:hover {
  background-color: #0056b3;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
    transform: translateY(10px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
