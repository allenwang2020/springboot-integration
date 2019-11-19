package com.esb.goods;

import java.util.List;

import com.esb.redis.key.KeyPrefix;
import com.esb.vo.GoodsVo;

public interface GoodsService {
	/**
     * 查詢商品列表
     *
     * @return
     */
    public List<GoodsVo> listGoodsVo(KeyPrefix prefix,String key);
    
    /**
     * 下單成功後查詢商品列表
     *
     * @return
     */
    public List<GoodsVo> seckillAfterListGoodsVo(KeyPrefix prefix,String key);
    
    /**
     * 根據id查詢指定商品
     *
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(KeyPrefix prefix,long goodsId);
    
    /**
     * 下單成功後根據id查詢指定商品
     *
     * @return
     */
    public GoodsVo seckillAfterGetGoodsVoByGoodsId(KeyPrefix prefix,long goodsId);
    
    /**
    * 減少庫存，每次減一
    * @return
    */
   public boolean reduceStock(GoodsVo goods);
    
   
   
    
}
