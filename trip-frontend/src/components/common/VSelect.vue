<script setup>
import { ref } from "vue";
defineProps({ selectOption: Array });
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  emit("onKeySelect", key.value);
};
</script>

<template>
  <select
    v-model="key"
    class="form-select form-select-sm ms-5 me-1 w-50 p-2 rounded-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent bg-gradient-to-r from-pink-200 to-purple-200 text-gray-800"
    @change="onSelect">
    <option
      v-for="option in selectOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
      class="text-gray-900 dark:text-white bg-white border-b border-gray-300">
      {{ option.text }}
    </option>
  </select>
</template>

<style scoped></style>
