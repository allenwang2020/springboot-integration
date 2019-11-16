<template>
    <div class="panel panel-default" style="height:100%;background-color:rgba(222,222,222,0.8)">
        <div class="panel-heading">秒殺商品列表</div>
        <table class="table" id="goodslist">
            <tr>
                <td>商品名稱</td>
                <td>商品圖片</td>
                <td>商品原價</td>
                <td>秒殺價</td>
                <td>庫存數量</td>
                <td>詳細資料</td>
            </tr>
              <tbody>
                    <tr v-for="item in tableData" :key="item.id">
                        <td>{{ item.goodsName  }}</td>
                        <td>
                        <img v-bind:src="'static/'+item.goodsImg" width="100" height="100" ></td>
                        <td>{{ item.goodsPrice  }}</td>
                        <td>{{ item.seckillPrice  }}</td>
                         <td>{{ item.goodsStock  }}</td>
                        <td> <el-button  class="form-control"  v-on:click="getDetail(item.id)" >Detail</el-button></td>
                    </tr>
                </tbody>
            <!-- <tr th:each="goods,goodsStat : ${goodsList}">
                <td th:text="${goods.goodsName}"></td>
                <td><img th:src="@{${goods.goodsImg}}" width="100" height="100"/></td>
                <td th:text="${goods.goodsPrice}"></td>
                <td th:text="${goods.seckillPrice}"></td>
                <td th:text="${goods.stockCount}"></td>
                <td><a th:href="'/goods_detail.htm?goodsId='+${goods.id}">詳細資料</a></td>
            </tr> -->
        </table>
    </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'goodsList',
  data () {
    return {
      tableData: [],
      show: false
    }
  },
  mounted () {
    this.getData()
  },
  methods: {
    getData () {
      axios.get('/goods/list')//eslint-disable-line
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            this.tableData = res.data
          } else {
            this.$notify({
              title: '提示信息',
              message: res.data.msg,
              type: 'error'
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    getDetail (gid) {
      axios.get('/goods/detail', {
        params: {
          id: localStorage.mobile,
          goodsId: gid
        }
        })//eslint-disable-line
        .then(res => {
          console.log(res)
          if (res.status === 200) {
            this.$router.push(
              {
                name: 'GoodsDetail',
                params: { data: res.data.data }
              })
          } else {
            this.$notify({
              title: '提示信息',
              message: res.data.msg,
              type: 'error'
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    }
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
