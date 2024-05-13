<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/common/TheHeadingNavbar.vue";
</script>

<template>
  <div>
    <TheHeadingNavbar />
    <router-view />
  </div>
</template>

<style scoped></style>
