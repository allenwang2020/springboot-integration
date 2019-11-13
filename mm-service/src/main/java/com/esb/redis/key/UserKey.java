package com.esb.redis.key;

/**
 */
public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 172800000;//預設兩天(毫秒)

    /**
     * 防止被外面實體化
     */
    private UserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要緩存的字段
     */
    public static UserKey token = new UserKey(TOKEN_EXPIRE,"token");
    public static UserKey getById = new UserKey(0, "id");

}