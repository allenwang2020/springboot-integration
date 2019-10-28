package com.esb.redis.key;

/**
* 緩衝key前缀
*/
public interface KeyPrefix {

   /**
    * 有效期
    * @return
    */
   public int expireSeconds();

   /**
    * 前缀
    * @return
    */
   public String getPrefix();
}