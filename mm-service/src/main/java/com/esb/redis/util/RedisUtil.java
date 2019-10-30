package com.esb.redis.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

/**
 **/

@Component
public class RedisUtil {

	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private ValueOperations<String, Object> valueOperations;
	private HashOperations<String, Object, Object> hashOperations;
	private ListOperations<String, Object> listOperations;
	private SetOperations<String, Object> setOperations;
	private ZSetOperations<String, Object> zSetOperations;

	// =============================common============================
	/**
	 * 指定緩存失效時間
	 * 
	 * @param key  鍵
	 * @param time 時間(秒)
	 * @return
	 */
	public boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根據key 獲取過期時間
	 * 
	 * @param key 鍵 不能為null
	 * @return 時間(秒) 返回0代表為永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判斷key是否存在
	 * 
	 * @param key 鍵
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 刪除緩存
	 * 
	 * @param key 可以傳一個值 或多個
	 *//*
		 * @SuppressWarnings("unchecked") public void del(String ... key){
		 * if(key!=null&&key.length>0){ if(key.length==1){ redisTemplate.delete(key[0]);
		 * }else{ redisTemplate.delete(CollectionUtils.arrayToList(key)); } } }
		 */

	/**
	 * 刪除緩存
	 * 
	 * @param key 可以傳一個值 或多個
	 */
	public Boolean del(String key) {
		return redisTemplate.delete(key);
	}

	// ============================String=============================
	/**
	 * 普通緩存獲取
	 * 
	 * @param key 鍵
	 * @return 值
	 */
	public Object get(String key) {
		if(valueOperations==null) {
			valueOperations= redisTemplate.opsForValue();
		}
		return key == null ? null : valueOperations.get(key);
	}

	/**
	 * 普通缓存放入
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
		try {
			if(valueOperations==null) {
				valueOperations= redisTemplate.opsForValue();
			}
			valueOperations.set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 普通缓存放入並設置時間
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @param time  時間(秒) time要大於0 如果time小於等於0 将設置無限期
	 * @return true成功 false 失敗
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				if(valueOperations==null) {
					valueOperations= redisTemplate.opsForValue();
				}
				valueOperations.set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 增
	 * 
	 * @param key 鍵
	 * @param by  要增加幾個(大於0)
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("遞增因子必须大於0");
		}
		if(valueOperations==null) {
			valueOperations= redisTemplate.opsForValue();
		}
		return valueOperations.increment(key, delta);
	}

	/**
	 * 遞减
	 * 
	 * @param key 鍵
	 * @param by  要減少幾個(小於0)
	 * @return
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("遞减因子必须大於0");
		}
		if(valueOperations==null) {
			valueOperations= redisTemplate.opsForValue();
		}
		return valueOperations.increment(key, -delta);
	}

	// ================================Map=================================
	/**
	 * HashGet
	 * 
	 * @param key  鍵 不能為null
	 * @param item 項目 不能為null
	 * @return 值
	 */
	public Object hget(String key, String item) {
		return hashOperations.get(key, item);
	}

	/**
	 * 獲取hashKey對應的所有鍵值
	 * 
	 * @param key 鍵
	 * @return 對應的多個键值
	 */
	public Map<Object, Object> hmget(String key) {
		return hashOperations.entries(key);
	}

	/**
	 * HashSet
	 * 
	 * @param key 鍵
	 * @param map 對應多個鍵值
	 * @return true 成功 false 失败
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			hashOperations.putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * HashSet 並設置時間
	 * 
	 * @param key  鍵
	 * @param map  對應多個键值
	 * @param time 時間(秒)
	 * @return true成功 false失敗
	 */
	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			hashOperations.putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一張hash表中放入數據,如果不存在將創建
	 * 
	 * @param key   鍵
	 * @param item  項目
	 * @param value 值
	 * @return true 成功 false失敗
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			hashOperations.put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 向一張hash表中放入數據,如果不存在將創建
	 * 
	 * @param key   鍵
	 * @param item  項
	 * @param value 值
	 * @param time  時間(秒) 注意:如果已存在的hash表有時間,這裡將會替换原有的时間
	 * @return true 成功 false失敗
	 */
	public boolean hset(String key, String item, Object value, long time) {
		try {
			hashOperations.put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 刪除hash表中的值
	 * 
	 * @param key  键 不能為null
	 * @param item 項目 可以使用多個 不能為null
	 */
	public void hdel(String key, Object... item) {
		hashOperations.delete(key, item);
	}

	/**
	 * 判斷hash表中是否有该項的值
	 * 
	 * @param key  鍵 不能為null
	 * @param item 項目 不能為null
	 * @return true 存在 false不存在
	 */
	public boolean hHasKey(String key, String item) {
		return hashOperations.hasKey(key, item);
	}

	/**
	 * hash遞增 如果不存在,就會創建一個 並把新增後的值返回
	 * 
	 * @param key  鍵
	 * @param item 項
	 * @param by   要增加幾個(大於0)
	 * @return
	 */
	public double hincr(String key, String item, double by) {
		return hashOperations.increment(key, item, by);
	}

	/**
	 * hash遞减
	 * 
	 * @param key  鍵
	 * @param item 項
	 * @param by   要減少幾個(小於0)
	 * @return
	 */
	public double hdecr(String key, String item, double by) {
		return hashOperations.increment(key, item, -by);
	}

	// ============================set=============================
	/**
	 * 根据key獲取Set中的所有值
	 * 
	 * @param key 鍵
	 * @return
	 */
	public Set<Object> sGet(String key) {
		try {
			return setOperations.members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根據value從一个set中查詢,是否存在
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return setOperations.isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 將數據放入set缓存
	 * 
	 * @param key    鍵
	 * @param values 值 可以是多個
	 * @return 成功個數
	 */
	public long sSet(String key, Object... values) {
		try {
			return setOperations.add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 將set數據放入缓存
	 * 
	 * @param key    鍵
	 * @param time   時間(秒)
	 * @param values 值 可以是多個
	 * @return 成功個數
	 */
	public long sSetAndTime(String key, long time, Object... values) {
		try {
			Long count = setOperations.add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 獲取set缓存的長度
	 * 
	 * @param key 鍵
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			return setOperations.size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值為value的
	 * 
	 * @param key    鍵
	 * @param values 值 可以是多個
	 * @return 移除的個數
	 */
	public long setRemove(String key, Object... values) {
		try {
			Long count = setOperations.remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// ===============================list=================================

	/**
	 * 獲取list缓存的内容
	 * 
	 * @param key   鍵
	 * @param start 開始
	 * @param end   结束 0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return listOperations.range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 獲取list缓存的所有内容
	 * 
	 * @param key
	 * @return
	 */
	public List<Object> lGetAll(String key) {
		return lGet(key, 0, -1);
	}

	/**
	 * 獲取list缓存的長度
	 * 
	 * @param key 
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return listOperations.size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 通過索引 獲取list中的值
	 * 
	 * @param key   鍵
	 * @param index 索引 index>=0时， 0 表頭，1 第二個元素，依次類推；index<0时，-1，表尾，-2倒數第二个元素，依次類推
	 * @return
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return listOperations.index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 將list放入缓存
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @param time  時間(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value) {
		try {
			listOperations.rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 將list放入缓存
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @param time  時間(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			listOperations.rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 將list放入缓存
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @param time  時間(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value) {
		try {
			listOperations.rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 將list放入缓存
	 * 
	 * @param key   鍵
	 * @param value 值
	 * @param time  時間(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			listOperations.rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某條數據
	 * 
	 * @param key   鍵
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			listOperations.set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N個值為value
	 * 
	 * @param key   鍵
	 * @param count 移除多少個
	 * @param value 值
	 * @return 移除的個數
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = listOperations.remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}