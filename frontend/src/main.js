/* Copyright 2026 Shanghai Rujing Zhihua Information Technology Co., Ltd. · https://www.zhuatech.cn/ */
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Vant from 'vant'
import 'vant/lib/index.css'
import './assets/main.css'
import App from './App.vue'
import router from './router'
createApp(App).use(createPinia()).use(router).use(Vant).mount('#app')
