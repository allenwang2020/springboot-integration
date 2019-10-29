package com.esb.order;

import com.esb.seckill.SeckillOrder;
import com.esb.user.User;
import com.esb.vo.GoodsVo;

public interface OrderService {

	public SeckillOrder getOrderByUserIdGoodsId(long userId, long goodsId);
	
	public OrderInfo getOrderById(long orderId);
	
	public OrderInfo createOrder(User user, GoodsVo goods);
}
