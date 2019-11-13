<template>
<div class="panel panel-default">
<div class="panel-body">
    <div v-if="detail.isLogin==false">您還沒有登錄，請登錄後再操作</div>
    <div v-else-if="detail.isLogin">
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
              <span id="countDown"></span>秒</div>
        </td>
        <td>
            <div v-if="detail.seckillStatus === 1">
            <form id="seckillForm" method="post" action="/seckill/do_seckill">
                <button class="btn btn-primary btn-block" type="submit" id="buyButton">立即秒殺</button>
                <input type="hidden" name="goodsId" th:value="${goods.id}"/>
            </form>
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
        <td colspan="3" >{{detail.goodsStock}}</td>
    </tr>
    </table>
    </div>
     </div>
</div>
</template>
<script>
export default {
  data () {
    return {
      detail: {
        goodsDetail: this.$route.params.data.goods.goodsDetail,
        goodsImg: this.$route.params.data.goods.goodsImg,
        startDate: this.$route.params.data.goods.startDate,
        seckillStatus: this.$route.params.data.seckillStatus,
        goodsPrice: this.$route.params.data.goods.goodsPrice,
        seckillPrice: this.$route.params.data.goods.seckillPrice,
        goodsStock: this.$route.params.data.goods.goodsStock,
        isLogin: this.$store.getters.isLogin,
        remainSeconds: this.$route.params.data.remainSeconds
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          alert('submit!')
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    countDown(){

    }
  }
}
</script>
