// store.js
import { defineStore } from "pinia";
import { ref } from "vue";

export const useBoardStore = defineStore("board", () => {
  // State
  const currentPage = ref(1);

  return {
    currentPage,
  };
});
