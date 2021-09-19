import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import global from './globalFun.js'


//类似ajax 导入
import axios from './axios.js'
import request from "./axios.js";
// 全局可以使用axioscnpm install qs --save
Vue.prototype.$axios = request

Vue.use(Element)

//require('./mock.js')
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
