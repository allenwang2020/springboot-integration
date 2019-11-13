// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Vuex from 'vuex'
import axios from 'axios'
import Element from 'element-ui'
import store from './vuex/store.js'
import {delCookie, getCookie} from '@/util/util'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'
import 'font-awesome/css/font-awesome.css'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(Vuex)
Vue.use(Element)

Vue.config.productionTip = false
axios.defaults.baseURL = 'http://192.168.99.117:8082'
axios.interceptors.request.use(function (config) {
  if (localStorage.token) {
    config.headers.Authorization = `Bearer ${localStorage.token}`
  }
  return config
}, function (err) {
  return Promise.reject(err)
})
// router.beforeEach((to, from, next) => {
//   if (to.meta.requireAuth) {
//     console.log(store.getters.isLogin)
//     if (store.getters.isLogin) {
//       next()
//     } else {
//       next({
//         path: '/login',
//         query: {redirect: to.fullPath}
//       })
//     }
//   } else {
//     next()
//   }
// })

// 备注：请注意路由中的 meta:{requireAuth: true }，这个配置，主要为下面的验证做服务。
// if(to.meta.requireAuth)，这段代码意思就是说，如果requireAuth: true ,那就判断用户是否存在。
// 如果存在，就继续执行下面的操作，如果不存在，就删除客户端的Cookie,同时页面跳转到登录页，代码如下。
// 这个是请求页面路由的时候会验证token存不存在，不存在的话会到登录页
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.getters.isLogin) {
      next()
    } else {
      if (getCookie('session')) {
        delCookie('session')
      }
      if (getCookie('u_uuid')) {
        delCookie('u_uuid')
      }
      next({
        path: '/login'
      })
    }
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
