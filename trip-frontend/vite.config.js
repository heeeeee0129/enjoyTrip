import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
// import vue from "@vitejs/plugin-vue";
import VueDevTools from "vite-plugin-vue-devtools";
import veauryVitePlugins from "veaury/vite/index.js";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    // vue(),
    VueDevTools(),
    veauryVitePlugins({
      type: "vue",
    }),
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
});
