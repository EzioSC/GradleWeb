package com.sunchao.controller.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


/**
 * redis缓存配置文件
 * @author 91527
 *
 */
@Configuration
@EnableCaching
public class RedisConifg extends CachingConfigurerSupport  {
	
	
//	@Value("${spring.redis.host}") 已过时！
//	private String host;
//	
//	@Value("${spring.redis.port}")
//	private int port;
//	
//	@Value("${spring.redis.timeout}")
//	private int timeout;
//	
//	@Bean#SPRING boot 2.0已经移除这种方式
//	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		//设置缓存过期时间
//		
//	}
	@Bean  
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,  
            MessageListenerAdapter listenerAdapter) {  
  
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();  
        container.setConnectionFactory(connectionFactory);  
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));  
  
        return container;  
    }  
  
    @Bean  
    MessageListenerAdapter listenerAdapter(Receiver receiver) {  
        return new MessageListenerAdapter(receiver, "receiveMessage");  
    }  
  
    @Bean  
    Receiver receiver(CountDownLatch latch) {  
        return new Receiver(latch);  
    }  
  
    @Bean  
    CountDownLatch latch() {  
        return new CountDownLatch(1);  
    }  
  
    @Bean  
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {  
        return new StringRedisTemplate(connectionFactory);  
    }  
      
    public class Receiver {   
          
  
        private CountDownLatch latch;  
          
        @Autowired  
        public Receiver(CountDownLatch latch) {  
            this.latch = latch;  
        }  
          
        public void receiveMessage(String message) {  
            latch.countDown();  
        }  
    }  
	
}
