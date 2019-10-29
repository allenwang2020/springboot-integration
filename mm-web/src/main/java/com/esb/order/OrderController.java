package com.esb.order;

import org.mm.result.CodeMsg;
import org.mm.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esb.goods.GoodsService;
import com.esb.redis.RedisService;
import com.esb.redis.key.GoodsKey;
import com.esb.user.User;
import com.esb.user.UserService;
import com.esb.vo.GoodsDetailVo;
import com.esb.vo.GoodsVo;
import com.esb.vo.OrderDetailVo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/order/*")
public class OrderController {
	 	@Autowired
	    UserService userService;

	    @Autowired
	    OrderService orderService;

	    @Autowired
	    GoodsService goodsService;
	    
	    @GetMapping("list")
	    public Result<OrderDetailVo> info(@RequestBody User user,
	    		@RequestParam(value="orderId", required=true) long orderId) {
	        if(user == null) {
	            return Result.error(CodeMsg.SESSION_ERROR);
	        }
	        OrderInfo order = orderService.getOrderById(orderId);
	        if(order == null) {
	            return Result.error(CodeMsg.ORDER_NOT_EXIST);
	        }
	        long goodsId = order.getGoodsId();
	        
	        GoodsVo goods = (GoodsVo)goodsService.getGoodsVoByGoodsId(GoodsKey.getGoodsDetail, Long.valueOf(goodsId));
	        OrderDetailVo vo = new OrderDetailVo();
	        vo.setOrder(order);
	        vo.setGoods(goods);
	        return Result.success(vo);
	    }

	   

}
