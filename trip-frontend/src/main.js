import { createApp } from "vue";
// import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";
import store from "./stores";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);

// app.use(createPinia());
app.use(router);

app.use(store).mount("#app");
