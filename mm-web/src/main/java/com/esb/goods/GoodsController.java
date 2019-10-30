package com.esb.goods;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esb.redis.RedisService;
import com.esb.redis.key.GoodsKey;
import com.esb.redis.key.UserKey;
import com.esb.result.Result;
import com.esb.user.User;
import com.esb.user.UserService;
import com.esb.vo.GoodsDetailVo;
import com.esb.vo.GoodsVo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/goods/*")
public class GoodsController {

	@Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;
    
    /**
     * 商品列表
     * QPS:433
     * 1000 * 10
     */
    @GetMapping("list")
    public List<GoodsVo> list() {
        
    	//第一次先檢查redis cache 裡有沒有，沒有就從資料庫取回並放入redis cache
        List<GoodsVo> goodsList = goodsService.listGoodsVo(GoodsKey.getGoodsList, "");
        
        return goodsList;
    }
    

    /**
     * 商品詳細清單
     */
    @GetMapping("detail")
    public Result<GoodsDetailVo> detail(
            @RequestParam(value="id", required=true) String id,
            @RequestParam(value="goodsId", required=true) String goodsId) {

    	//根據id取回使用者資料
    	//第一次先檢查redis cache 裡有沒有，沒有就從資料庫取回並放入redis cache
    	 User user = userService.getById(UserKey.getById,Long.valueOf(id));;
    	
    	//根據goodsId查詢商品詳細資料
    	//第一次先檢查redis cache 裡有沒有，沒有就從資料庫取回並放入redis cache
        GoodsVo goods = (GoodsVo)goodsService.getGoodsVoByGoodsId(GoodsKey.getGoodsDetail, Long.valueOf(goodsId));
        GoodsDetailVo vo = new GoodsDetailVo();
        
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;

        if (now < startTime) {//利殺還沒開始，倒數計時
            seckillStatus = 0;
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {//秒殺己經結束
            seckillStatus = 2;
            remainSeconds = -1;
        } else {//利殺進行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setSeckillStatus(seckillStatus);

        return Result.success(vo);
    }

}
