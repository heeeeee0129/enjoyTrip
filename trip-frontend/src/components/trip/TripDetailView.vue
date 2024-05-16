<script setup>
import { useRoute } from "vue-router";
const route = useRoute();
const { contentId } = route.params;
</script>

<template>
  <div>tripDetail</div>
  {{ contentId }}
</template>

<style scoped></style>
