import {createApp} from 'vue'
import router from './router/index.js';
import App from 'vue/vue3tutorial/src/App.vue';

const app = createApp(App)
app.use(router)
app.mount('#app')
