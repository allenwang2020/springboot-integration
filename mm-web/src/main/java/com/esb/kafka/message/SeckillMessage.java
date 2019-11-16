package com.esb.kafka.message;

import java.io.Serializable;

import com.esb.user.User;

public class SeckillMessage  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1716078791473424842L;
	private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
