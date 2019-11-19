<template>
<div class="panel panel-default">
<div class="panel-body">
  <div v-if="detail !== undefined">
        <div class="panel-heading">秒殺商品詳情</div>
    <table class="table" id="goodslist">
    <tr>
        <td>商品名稱</td>
        <td colspan="3"> {{detail.goodsDetail}}</td>
    </tr>
    <tr>
        <td>商品圖片</td>
        <td colspan="3"><img v-bind:src="'static/'+detail.goodsImg"  width="200" height="200"/></td>
    </tr>
    <tr>
        <td>秒殺開始時間</td>
        <td >{{detail.startDate}}</td>
    </tr>
    <tr>
        <td id="seckillTip">
             <div v-if="detail.seckillStatus === 0">秒殺倒數計時：</div>
             <div v-else-if="detail.seckillStatus === 1">秒殺進行中</div>
             <div v-else>秒殺己經結束</div>
        </td>
        <td>
             <div v-if="detail.seckillStatus === 0">
              <span id="countDown" >{{detail.remainSeconds}}</span>秒</div>
        </td>
        <td>
            <div v-if="detail.seckillStatus === 1">
               <button class="btn btn-primary btn-block" @click="doBuy">立即秒殺</button>
            </div>
        </td>
    </tr>
    <tr>
        <td>商品原價</td>
        <td colspan="3" >{{detail.goodsPrice}}</td>
    </tr>
    <tr>
        <td>秒殺價</td>
        <td colspan="3" >{{detail.seckillPrice}}</td>
    </tr>
    <tr>
        <td>庫存數量</td>
        <td colspan="3" >{{detail.stockCount}}</td>
    </tr>
    </table>
     </div>
    </div>
</div>
</template>
<script>
import axios from 'axios'
export default {
  data () {
    if (this.$route.params.data !== undefined) {
      return {
        detail: {
          goodsId: this.$route.params.data.goods.id,
          goodsDetail: this.$route.params.data.goods.goodsDetail,
          goodsImg: this.$route.params.data.goods.goodsImg,
          startDate: this.$route.params.data.goods.startDate,
          seckillStatus: this.$route.params.data.seckillStatus,
          goodsPrice: this.$route.params.data.goods.goodsPrice,
          seckillPrice: this.$route.params.data.goods.seckillPrice,
          stockCount: this.$route.params.data.goods.stockCount,
          isLogin: this.$store.getters.isLogin,
          remainSeconds: this.$route.params.data.remainSeconds,
          user: this.$route.params.data.user
        }
      }
    }
  },
  mounted () {
    this.countDown()
  },
  methods: {
    doBuy () {
      axios.post('/seckill/do_seckill', this.detail.user, {
        params: {
          goodsId: this.detail.goodsId
        }
        })//eslint-disable-line
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            if (res.data.code === 0) {//eslint-disable-line
              this.$notify({
                title: '提示信息',
                message: '下單成功',
                type: 'success'
              })
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
              message: '錯誤',
              type: 'error'
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    countDown () {
      if (this.detail === undefined) {
        this.$router.push({path: '/goodsList'})
      } else {
        if (!this.timer) {
          this.timer = setInterval(() => {
            if (this.detail.remainSeconds > 0) {
              this.detail.remainSeconds--
              if (this.detail.remainSeconds === 0) {
                this.detail.seckillStatus = 1
                clearInterval(this.timer)
              }
            }
          }, 1000)
        }
      }
    }
  }
}
</script>
