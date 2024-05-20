<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/common/TheHeadingNavbar.vue";
import { onMounted } from "vue";
const { VITE_SEND_BIRD_APP_ID } = import.meta.env;

onMounted(() => {
  (function (w, d, s, ...args) {
    var div = d.createElement("div");
    div.id = "aichatbot";
    d.body.appendChild(div);
    w.chatbotConfig = args;
    var f = d.getElementsByTagName(s)[0],
      j = d.createElement(s);
    j.defer = true;
    j.type = "module";
    j.src = "https://aichatbot.sendbird.com/index.js";
    f.parentNode.insertBefore(j, f);
  })(window, document, "script", VITE_SEND_BIRD_APP_ID, "onboarding_bot", {
    apiHost: "https://api-cf-ap-2.sendbird.com",
  });
});
</script>

<template>
  <div id="chatbot-container">
    <TheHeadingNavbar />
    <router-view />
  </div>
</template>

<style scoped></style>
