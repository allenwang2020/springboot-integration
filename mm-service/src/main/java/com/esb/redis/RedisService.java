package com.esb.redis;

import com.esb.redis.key.KeyPrefix;

public interface RedisService {

	/**
     * 取得實例
     */
	public Object get(KeyPrefix prefix, String key);
	
	/**
     * 儲存實例
     */
	public  Boolean set(KeyPrefix prefix, String key,Object value) ;
	
	/**
     * 刪除
     */
	public boolean delete(KeyPrefix prefix, String key) ;
	
	/**
     * 判斷key是否存在
     */
	public boolean exists(KeyPrefix prefix, String key);
	
	/**
     * 增加值
     * Redis Incr 命令將 key 中儲存的數增值增一。    如果 key 不存在，那 key 的值會先被初始化為 0 ，然後再執行 INCR 操作
     */
    public  Long incr(KeyPrefix prefix, String key,long delta);
    
    /**
     * 減少值
     */
    public Long decr(KeyPrefix prefix, String key,long delta);
}
