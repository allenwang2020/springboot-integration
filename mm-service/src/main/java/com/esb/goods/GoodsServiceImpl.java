package com.esb.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.esb.redis.key.KeyPrefix;
import com.esb.seckill.SeckillGoods;
import com.esb.vo.GoodsVo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("goodsSrvice")
public class GoodsServiceImpl implements GoodsService{
	//樂觀鎖衝突最大重試次數
    private static final int DEFAULT_MAX_RETRIES = 5;

    @Autowired
    GoodsMapper goodsMapper;
    
	@Override
	@Cacheable(value = "goodsVo", key = "#prefix.prefix + '' + #key.toString()")
	public List<GoodsVo> listGoodsVo(KeyPrefix prefix ,String key) {
		return goodsMapper.listGoodsVo();
	}

	@Override
	@Cacheable(value = "goodsVo", key = "#prefix.prefix + '' + #goodsId.toString()")
	public GoodsVo getGoodsVoByGoodsId(KeyPrefix prefix,long goodsId) {
		 return goodsMapper.getGoodsVoByGoodsId(goodsId);
	}

	@Override
	public boolean reduceStock(GoodsVo goods) {
		int numAttempts = 0;
        int ret = 0;
        SeckillGoods sg = new SeckillGoods();
        sg.setGoodsId(goods.getId());
        sg.setVersion(goods.getVersion());
        do {
            numAttempts++;
            try {
                sg.setVersion(goodsMapper.getVersionByGoodsId(goods.getId()));
                ret = goodsMapper.reduceStockByVersion(sg);
            } catch (Exception e) {
            	log.error(e.getMessage(), e);
            }
            if (ret != 0)
                break;
        } while (numAttempts < DEFAULT_MAX_RETRIES);

        return ret > 0;
	}

}
