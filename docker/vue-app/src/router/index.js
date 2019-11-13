import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import GoodsList from '@/components/GoodsList'
import GoodsDetail from '@/components/GoodsDetail'
Vue.use(Router)

// export default new Router({
//   rmode: 'history',
//   routes: [
//     {
//       path: '/login',
//       name: 'Login',
//       component: Login
//     },
//     {
//       path: '/goodsList',
//       name: 'GoodsList',
//       meta: {
//         requireAuth: true
//       },
//       component: GoodsList
//     }
//   ]
// })
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/goodsList',
    name: 'GoodsList',
    meta: {
      requireAuth: true
    },
    component: GoodsList
  },
  {
    path: '/goodsDetail',
    name: 'GoodsDetail',
    meta: {
      requireAuth: true
    },
    component: GoodsDetail
  }
]
const router = new Router({
  routes
})

export default router
