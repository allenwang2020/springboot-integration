package com.esb.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esb.redis.key.KeyPrefix;
import com.esb.redis.util.RedisUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service("redisService")
public class RedisServcieImpl implements RedisService {

	@Autowired
    RedisUtil redisUtil;
	
	
	@Override
	public Object get(KeyPrefix prefix, String key) {
		//對key增加前綴，可以用於分類也避免key重複
        String realkey = prefix.getPrefix() + key;
        return redisUtil.get(realkey);
	}

	@Override
	public  Boolean set(KeyPrefix prefix, String key, Object value) {
		if (value == null ) {
            return false;
        }
        String realKey = prefix.getPrefix() + key;
        long seconds = prefix.expireSeconds();//獲取過期時間
        if (seconds <= 0) {
        	redisUtil.set(realKey, value);
        } else {
        	redisUtil.set(realKey,value,seconds);
        }

        return true;
	}

	@Override
	public boolean delete(KeyPrefix prefix, String key) {
		//產生真正的key
        String realkey = prefix.getPrefix() + key;
        return redisUtil.del(realkey);
	}

	@Override
	public  boolean exists(KeyPrefix prefix, String key) {
		//產生真正的key
		String realkey = prefix.getPrefix() + key;
		return redisUtil.hasKey(realkey);
	}

	@Override
	public Long incr(KeyPrefix prefix, String key,long delta) {
		//產生真正的key
        String realkey = prefix.getPrefix() + key;
        return redisUtil.incr(realkey, delta);
	}

	@Override
	public Long decr(KeyPrefix prefix, String key,long delta) {
		//產生真正的key
		String realkey = prefix.getPrefix() + key;
        return redisUtil.decr(realkey, delta);
	}
	
}
