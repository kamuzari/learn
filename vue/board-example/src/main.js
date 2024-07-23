import { createApp } from "vue";
import App from "./App.vue";

import axios from "axios";

import router from "./router/index.js";

import { createPinia } from "pinia";
import piniaPersistedState from "pinia-plugin-persistedstate";
import vuetify from "./plugins/vuetify";

const app = createApp(App);
const pinia = createPinia();
pinia.use(piniaPersistedState);
app.config.globalProperties.axios = axios;
app.use(router).use(pinia).use(vuetify);

app.mount("#app");
