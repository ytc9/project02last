import Vue from 'vue'
import App from './App.vue'
import VueRouter from "vue-router";
import router from "./router"
import ElementUI from "element-ui"
import "element-ui/lib/theme-chalk/index.css"
import "./assets/global.css"

import request from "@/utils/request";

import store from "@/store";

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(ElementUI ,{size:"mini"})

Vue.prototype.request=request()   //引入axios的实现类 里面有请求头拦截器等


new Vue({
  render: h => h(App),
  router:router,
  store:store
}).$mount('#app')
