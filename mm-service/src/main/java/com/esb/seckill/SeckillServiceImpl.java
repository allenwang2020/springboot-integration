package com.esb.seckill;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esb.goods.GoodsService;
import com.esb.order.OrderInfo;
import com.esb.order.OrderService;
import com.esb.redis.RedisService;
import com.esb.redis.key.SeckillKey;
import com.esb.user.User;
import com.esb.user.UserServiceImpl;
import com.esb.vo.GoodsVo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("seckillService")
public class SeckillServiceImpl implements SeckillService{

	@Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;
	
	@Override
	@Transactional
	public OrderInfo seckill(User user, GoodsVo goods) {
		//減庫存
        boolean success = goodsService.reduceStock(goods);
        if (success){
            //下訂單 寫入秒殺訂單
            return orderService.createOrder(user, goods);
        }else {
            setGoodsOver(goods.getId());
            return null;
        }
	}

	@Override
	public long getSeckillResult(long userId, long goodsId) {
		SeckillOrder order = orderService.getOrderByUserIdGoodsId(userId, goodsId);
        if (order != null){
            return order.getOrderId();
        }else{
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
	}
	
	private void setGoodsOver(Long goodsId) {
        redisService.set(SeckillKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(SeckillKey.isGoodsOver, ""+goodsId);
    }

}
