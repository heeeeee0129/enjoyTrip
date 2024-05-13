import { createApp } from "vue";
import { createPinia } from "pinia";
import AOS from "aos";
import "aos/dist/aos.css";

import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

const app = createApp(App);

const pinia = createPinia();
app.use(pinia);

app
  .use(router)
  .component("font-awesome-icon", FontAwesomeIcon)
  .mount("#app", () => {
    AOS.init();
  });
