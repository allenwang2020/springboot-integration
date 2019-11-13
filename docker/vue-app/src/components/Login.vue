<template>
  <div id="bg" class="bg">
    <h2 style="text-align:center; margin-bottom: 20px">用戶登入</h2>
     <div class="login" @keyup.13="doLogin">
    <div class="form-horizontal login">
    <div class="form-group input-group input-group-lg">
            <label class="form-label col-md-4">請輸入手機號碼</label>
                <input id="mobile" name="mobile" class="form-control" type="text" placeholder="手機號碼" required="true"
                       minlength="11" maxlength="11" v-model="loginVo.mobile"/>
    </div>

    <div class="form-group input-group input-group-lg">
        <label class="form-label col-md-4">請輸入密碼</label>
    <input id="password" name="password" class="form-control" type="password" placeholder="密碼"
                       required="true" minlength="6" maxlength="16" v-model="loginVo.password"/>
    </div>

     <div class="form-group">
          <el-button class="form-control" @click="doLogin">登 入</el-button>
        </div>
        </div>
    </div>
  </div>
</template>

<script>
import {setCookie} from '@/util/util'
import axios from 'axios'
import md5 from 'js-md5'
export default {
  name: 'login',
  data () {
    return {
      logining: false,
      loginVo: {
        mobile: '',
        password: ''
      },
      show: false
    }
  },
  methods: {
    doLogin () {
      if (mobile.value == '') {//eslint-disable-line
        alert('手機號碼不能為空')
        return false
      }
      if (password.value == '') {//eslint-disable-line
        alert('密碼不能為空')
        return false
      }
      var salt = '1a2b3c4d'
      var toMd5Password =  '' + salt.charAt(0) + salt.charAt(2) + password.value + salt.charAt(5) + salt.charAt(4)//eslint-disable-line
      this.loginVo.password = md5(toMd5Password)//eslint-disable-line
      axios.post('/login/do_login', this.loginVo)//eslint-disable-line
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            if (res.data.code === 0) {//eslint-disable-line
              // let expireDays = 1000 * 60 * 60
              setCookie('session', res.data.data.token, res.data.data.tokeExpire) // 设置Session
              setCookie('u_uuid', res.data.data.token, res.data.data.tokeExpire) // 设置用户编号
              this.$store.commit('setToken', res.data.data)
              localStorage.mobile = this.loginVo.mobile
              localStorage.token_expire = res.data.data.tokeExpire
              localStorage.token = res.data.data.token
              if (this.$route.query.redirect) {
                this.$router.push(this.$route.query.redirect)
              } else {
                this.$notify({
                  title: '提示信息',
                  message: '登入成功',
                  type: 'success'
                })
                this.$router.push({path: '/goodsList'})
              }
            } else {
              this.$notify({
                title: '提示信息',
                message: res.data.msg,
                type: 'error'
              })
            }
          } else {
            this.$notify({
              title: '提示信息',
              message: '手機號碼或密碼錯誤',
              type: 'error'
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    }
  },
  mounted () {
    var wi = window.screen.width
    var hi = window.screen.height
    document.getElementById('bg').style.width = wi + 'px'
    document.getElementById('bg').style.height = hi + 'px'
  }
}
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  /*.bg {*/
    /*!*background-color: aqua;*!*/
    /*background-image: url("../assets/bj.jpg");*/
    /*background-size:100% 100%*/
  /*}*/
  .login {
    position:absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    -o-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    width: 400px;
  }
  .login-btn {
    background-color :whitesmoke;
  }
  .logo {
    font-family: "DejaVu Sans Mono";
    color: lightblue;
    font-size: 50px;
  }
</style>
