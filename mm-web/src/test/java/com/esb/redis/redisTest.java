package com.esb.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esb.goods.GoodsService;
import com.esb.redis.key.GoodsKey;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class redisTest {
	
	@Autowired
    private GoodsService goodsService;

    @Test
    public void get() {
    	
    	log.info("in catche:"+goodsService.listGoodsVo(GoodsKey.getGoodsList, ""));
    	log.info("no in catche start...");
    	log.info("update after:"+goodsService.seckillAfterListGoodsVo(GoodsKey.getGoodsList, ""));
    	log.info("no in catche end...");
       
    }
}
