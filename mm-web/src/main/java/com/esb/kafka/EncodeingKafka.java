package com.esb.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.esb.util.BeanUtils;

public class EncodeingKafka implements Serializer<Object>{
	@Override
	public void configure(Map configs, boolean isKey) {
		
	}
	@Override
	public byte[] serialize(String topic, Object data) {
		return BeanUtils.bean2Byte(data);
	}
	/*
	 * producer呼叫close()方法是呼叫
	 */
	@Override
	public void close() {
		System.out.println("EncodeingKafka is close");
	}
}
