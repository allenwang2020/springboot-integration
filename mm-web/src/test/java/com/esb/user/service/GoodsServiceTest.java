package com.esb.user.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esb.goods.GoodsService;
import com.esb.redis.key.GoodsKey;
import com.esb.vo.GoodsVo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsServiceTest {
	
	@Autowired
    private GoodsService goodsService;
	 
	@Test
	public void testList() {
		String prefix = GoodsKey.getGoodsList.getPrefix();
    	log.info(prefix);
        final List<GoodsVo> list = goodsService.listGoodsVo(GoodsKey.getGoodsList, "");
        log.info("[listGoodsVo] - [{}]", list);
       
    }   
	
	@Test
	public void testDetail() {
		String prefix = GoodsKey.getGoodsDetail.getPrefix();
    	log.info(prefix);
    	
        final GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(GoodsKey.getGoodsDetail, 1L);
        log.info("[getGoodsVoByGoodsId] - [{}]", goodsVo);
       
    }  
}
