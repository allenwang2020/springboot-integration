package com.esb.seckill;

import com.esb.order.OrderInfo;
import com.esb.user.User;
import com.esb.vo.GoodsVo;

public interface SeckillService {
	/**
	 * 保證這三個操作，減庫存 下訂單 寫入秒殺訂單是一個事物
	 * @param user
	 * @param goods
	 * @return
	 */
    public OrderInfo seckill(User user, GoodsVo goods);
    
    public long getSeckillResult(long userId, long goodsId);
}
