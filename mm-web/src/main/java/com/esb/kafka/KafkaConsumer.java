package com.esb.kafka;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.esb.goods.GoodsService;
import com.esb.kafka.message.SeckillMessage;
import com.esb.order.OrderService;
import com.esb.redis.RedisService;
import com.esb.redis.key.GoodsKey;
import com.esb.seckill.SeckillOrder;
import com.esb.seckill.SeckillService;
import com.esb.user.User;
import com.esb.vo.GoodsVo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KafkaConsumer {
	
	@Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;
    
    @KafkaListener(topics= {"${kafka.topic.name}"})
	public void listeener(ConsumerRecord<?, ?> record) {
		
		Optional<?> msg = Optional.ofNullable(record.value());
		//判断你消息是否存在
		if(msg.isPresent()) {
			if(SeckillMessage.class.isInstance(msg)) {
				SeckillMessage seckillMessage = (SeckillMessage)msg.get();
				User user = seckillMessage.getUser();
		        long goodsId = seckillMessage.getGoodsId();

		        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(GoodsKey.getGoodsDetail, goodsId);
		        int stock = goodsVo.getStockCount();
		        if(stock <= 0){
		            return;
		        }
		        //判斷重覆秒殺
		        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
		        if(order != null) {
		            return;
		        }
		        //減庫存 下訂單 寫入秒殺訂單
		        seckillService.seckill(user, goodsVo);
		        log.info(seckillMessage);
			}
		}
	}
	
}
