package com.esb.goods;

import java.util.List;

import com.esb.vo.GoodsVo;

public interface GoodsService {
	/**
     * 查詢商品列表
     *
     * @return
     */
    public List<GoodsVo> listGoodsVo();
    
    /**
     * 根據id查詢指定商品
     *
     * @return
     */
    public GoodsVo getGoodsVoByGoodsId(long goodsId);
    
    /**
    * 減少庫存，每次減一
    * @return
    */
   public boolean reduceStock(GoodsVo goods);
    
   
   
    
}
