import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    title: 'Apple Store',
    userInfo: {
      expire: '',
      token: '',
      mobile: ''
    }
  },
  getters: {
    getToken (state) {
      return state.userInfo.token
    },
    isLogin () {
      var date = new Date()
      // Add a day
      date.setDate(date.getDate() + 2)
      let token_expire = Date.parse(date)//eslint-disable-line
      let now_time = Date.parse(new Date())//eslint-disable-line
      console.log('now_time : ', now_time, 'expire : ', token_expire)//eslint-disable-line
      if (localStorage.token !== '' && token_expire > now_time) {//eslint-disable-line
        return true
      } else {
        return false
      }
    }
  },
  mutations: {
    // 保存jwt认证后的token和expire
    setToken (state, payload) {
      state.userInfo.expire = payload.tokeExpire
      state.userInfo.token = payload.token
    }
  }

})
export default store
