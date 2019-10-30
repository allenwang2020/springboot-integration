package com.esb.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
public class KafkaTopicConfig {

    /**
     * 定義一個KafkaAdmin的bean，可以自動檢測集群中是否存在topic，不存在則建立
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        // 指定多個kafka集群多個地址，例如：192.168.2.11,9092,192.168.2.12:9092,192.168.2.13:9092
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,"${spring.kafka.bootstrap-servers}");
        return new KafkaAdmin(configs);
    }

    /**
     * 建立 Topic
     */
    @Bean
    public NewTopic topicinfo() {
    	//創建topic，需要指定創建的topic的名稱、分區數、副本數量(副本數數目設置要小於Broker數量
        return new NewTopic("topic-seckill", 0, (short) 0);
    }

}
