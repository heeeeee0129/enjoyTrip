import { createApp } from "vue";
// import { createPinia } from "pinia";
import AOS from 'aos';
import "aos/dist/aos.css";

import App from "./App.vue";
import router from "./router";
import store from "./stores";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

const app = createApp(App);
// app.component("font-awesome-icon", FontAwesomeIcon);

// app.use(router);

// app.use(store).mount("#app");
app.use(router)
   .use(store)
   .component("font-awesome-icon", FontAwesomeIcon)
   .mount("#app", () => {
      AOS.init();
   });