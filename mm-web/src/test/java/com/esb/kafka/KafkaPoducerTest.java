package com.esb.kafka;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.json.JSONObject;
import com.esb.kafka.message.SeckillMessage;
import com.esb.redis.key.UserKey;
import com.esb.user.User;
import com.esb.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaPoducerTest {

	@Autowired
    private UserService userService;
	
	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testKafkaProducer() throws JsonProcessingException {
		    //String msgStr= "測試發送訊息";
		    String prefix = UserKey.getById.getPrefix();
    	    log.info(prefix);
            final User user = userService.getById(UserKey.getById,18181818181L);
		     SeckillMessage seckillMessage = new SeckillMessage();
	        seckillMessage.setUser(user);
	        seckillMessage.setGoodsId(new Long(1));
	      
	        String msgStr = objectMapper.writeValueAsString(seckillMessage);
	        kafkaProducer.send(msgStr);
		
		    kafkaProducer.send(msgStr);
		    log.info("producer send message:"+msgStr);
    }   
	
}
