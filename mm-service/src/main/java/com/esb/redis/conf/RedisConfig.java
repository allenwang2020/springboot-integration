package com.esb.redis.conf;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

@Configuration
@EnableCaching // Enables Spring's annotation-driven cache management capability
public class RedisConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String hostName;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.cache.redis.time-to-live.seconds}")
	private int entryTTL;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
		return new LettuceConnectionFactory(config);
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			// 当没有指定缓存的 key时来根据类名、方法名和方法参数来生成key
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append('.').append(method.getName());
				if (params.length > 0) {
					sb.append('[');
					for (Object obj : params) {
						sb.append(obj.toString());
					}
					sb.append(']');
				}
				System.out.println("keyGenerator=" + sb.toString());
				return sb.toString();
			}
		};
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.create(connectionFactory);
	}

	/*
	 * private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap()
	 * { Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new
	 * HashMap<String, RedisCacheConfiguration>();
	 * //redisCacheConfigurationMap.put("user",
	 * this.getRedisCacheConfigurationWithTtl(30)); // 单独设置某些cache的超时时间 return
	 * redisCacheConfigurationMap; }
	 * 
	 * private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer
	 * seconds) { // 设置CacheManager的值序列化方式为JdkSerializationRedisSerializer, //
	 * 但其实RedisCacheConfiguration默认就是使用StringRedisSerializer序列化key， //
	 * JdkSerializationRedisSerializer序列化value,所以以下注释代码为默认实现 // ClassLoader loader =
	 * this.getClass().getClassLoader(); // JdkSerializationRedisSerializer
	 * jdkSerializer = new // JdkSerializationRedisSerializer(loader); //
	 * RedisSerializationContext.SerializationPair<Object> pair = //
	 * RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer); //
	 * RedisCacheConfiguration defaultCacheConfig = //
	 * RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair); //
	 * RedisCacheConfiguration defaultCacheConfig = //
	 * RedisCacheConfiguration.defaultCacheConfig();
	 * 
	 * Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new
	 * Jackson2JsonRedisSerializer<>( Object.class); ObjectMapper om = new
	 * ObjectMapper(); om.setVisibility(PropertyAccessor.ALL,
	 * JsonAutoDetect.Visibility.ANY);
	 * om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	 * jackson2JsonRedisSerializer.setObjectMapper(om);
	 * 
	 * RedisCacheConfiguration redisCacheConfiguration =
	 * RedisCacheConfiguration.defaultCacheConfig();
	 * 
	 * redisCacheConfiguration = redisCacheConfiguration .serializeValuesWith(
	 * RedisSerializationContext.SerializationPair.fromSerializer(
	 * jackson2JsonRedisSerializer)) .entryTtl(Duration.ofSeconds(seconds));
	 * 
	 * 
	 * return redisCacheConfiguration; }
	 */

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		
		template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setDefaultSerializer(new StringRedisSerializer());
		
		template.afterPropertiesSet();
		return template;
	}
	
	/**
     * 通过自定义配置构建Redis的Json序列化器
     * @return Jackson2JsonRedisSerializer对象
     */
   
	@Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }
	
}
