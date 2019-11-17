package com.esb.seckill;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esb.goods.GoodsService;
import com.esb.kafka.KafkaProducer;
import com.esb.kafka.message.SeckillMessage;
import com.esb.order.OrderService;
import com.esb.redis.RedisService;
import com.esb.redis.key.GoodsKey;
import com.esb.result.CodeMsg;
import com.esb.result.Result;
import com.esb.user.User;
import com.esb.vo.GoodsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/seckill/*")
public class SeckillController implements InitializingBean{
	    @Autowired
	    GoodsService goodsService;

	    @Autowired
	    OrderService orderService;

	    @Autowired
	    SeckillService seckillService;

	    @Autowired
	    RedisService redisService;
	    
	    @Autowired
	    KafkaProducer kafkaProducer;
	    
	    @Autowired
		private ObjectMapper objectMapper;

	    //基於令版桶算法的限流實現
	    RateLimiter rateLimiter = RateLimiter.create(10);

	    //做標記，判斷該商品是否被處理過了
	    private HashMap<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();
	    
	    

	    
	    /**
	     * 非同步下單
	     *
	     * @param model
	     * @param user
	     * @param goodsId
	     * @return
	     */
	    @PostMapping("do_seckill")
	    public Result<Integer> list(@RequestBody User user, @RequestParam("goodsId") long goodsId) {

	        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
	            return  Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
	        }

	        if (user == null) {
	            return Result.error(CodeMsg.SESSION_ERROR);
	        }
	        //內存標記，減少redis訪問
	        boolean over = localOverMap.get(goodsId);
	        if (over) {
	            return Result.error(CodeMsg.SECKILL_OVER);
	        }
	        //預減庫存
	        long stock = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId,1);//10
	        if (stock < 0) {
	            afterPropertiesSet();
	            long stock2 = redisService.decr(GoodsKey.getGoodsStock, "" + goodsId,1);//10
	            if(stock2 < 0){
	                localOverMap.put(goodsId, true);
	                return Result.error(CodeMsg.SECKILL_OVER);
	            }
	        }
	        //判斷重複秒殺
	        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
	        if (order != null) {
	            return Result.error(CodeMsg.REPEATE_SECKILL);
	        }
	        
	        SeckillMessage seckillMessage = new SeckillMessage();
	        seckillMessage.setUser(user);
	        seckillMessage.setGoodsId(goodsId);
	        String sendMessage = null;
			try {
				sendMessage = objectMapper.writeValueAsString(seckillMessage);
			} catch (JsonProcessingException e) {
				log.error(e.getMessage(),e);
			}
			log.info(sendMessage);
	        kafkaProducer.send(sendMessage);
			
	        return Result.success(0);//排隊中
	    }
	    
	    
	    /**
	     * 系統初始化，將商品訊息載入到redis和記憶體
	     */
	    @Override
	    public void afterPropertiesSet() {
	        List<GoodsVo> goodsVoList = goodsService.listGoodsVo(GoodsKey.getGoodsList, "");
	        if (goodsVoList == null) {
	            return;
	        }
	        for (GoodsVo goods : goodsVoList) {
	            redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), goods.getStockCount());
	            //初始化商品都是沒有處理過的
	            localOverMap.put(goods.getId(), false);
	        }
	    }

	    /**
	     * orderId：成功
	     * -1：秒殺失敗
	     * 0： 排隊中
	     */
	    @GetMapping("result")
	    public Result<Long> seckillResult(@RequestBody User user,
	                                      @RequestParam("goodsId") long goodsId) {
	        if (user == null) {
	            return Result.error(CodeMsg.SESSION_ERROR);
	        }
	        long orderId = seckillService.getSeckillResult(user.getId(), goodsId);
	        return Result.success(orderId);
	    }
}
