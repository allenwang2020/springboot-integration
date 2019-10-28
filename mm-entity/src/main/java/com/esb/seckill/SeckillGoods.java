package com.esb.seckill;

import java.io.Serializable;
import java.util.Date;

public class SeckillGoods implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6296305396670382421L;
	
	private Long id;
	 private Long goodsId;
	 private Integer stockCount;
	 private Date startDate;
	 private Date endDate;
	 private int version;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getStockCount() {
		return stockCount;
	}
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	 
	 

}
