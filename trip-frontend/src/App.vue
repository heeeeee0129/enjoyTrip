<script setup>
import { RouterView, useRoute } from "vue-router";
import TheHeadingNavbar from "@/components/common/TheHeadingNavbar.vue";
import { onMounted, ref, watch } from "vue";
import { useUserStore } from "./stores";
const { VITE_SEND_BIRD_APP_ID } = import.meta.env;

const route = useRoute();
const addSpacer = ref(false);
watch(
  () => route.name,
  (newRoute) => {
    addSpacer.value = newRoute !== "main";
  },
  { immediate: true }
);
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
    userId: useUserStore().member.id,
    nickname: useUserStore().member.name,
  });
});
</script>

<template>
  <div id="chatbot-container">
    <TheHeadingNavbar />
    <div v-if="addSpacer" class="h-24"></div>
    <router-view />
  </div>
</template>

<style scoped></style>
