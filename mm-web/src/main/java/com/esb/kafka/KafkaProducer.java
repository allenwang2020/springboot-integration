package com.esb.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${spring.kafka.topic.name}")
	private String topicNm;
	
	/**
	 * 功能描述: 發送訊息
	 * @param message
	 */
	public void send(String message) {
		this.kafkaTemplate.send(topicNm, message);
		log.debug(message);
	}

}
