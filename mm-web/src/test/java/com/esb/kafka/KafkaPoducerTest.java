package com.esb.kafka;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaPoducerTest {

	@Autowired
    KafkaProducer kafkaProducer;
	
	@Test
	public void testKafkaProducer() {
		String msgStr= "測試發送訊息";
		kafkaProducer.send(msgStr);
		log.info("producer send message:"+msgStr);
    }   
	
}
