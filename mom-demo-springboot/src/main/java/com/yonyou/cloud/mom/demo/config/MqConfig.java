package com.yonyou.cloud.mom.demo.config;

import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import com.yonyou.cloud.mom.client.impl.MqSenderDefaultImpl;
import com.yonyou.cloud.mom.core.util.SpringUtil;

@Configuration
public class MqConfig {

	@Bean
	public MqSenderDefaultImpl mqSenderDefaultImpl(RabbitOperations rabbitOperations){
		MqSenderDefaultImpl mqSenderDefaultImpl = new MqSenderDefaultImpl();
		mqSenderDefaultImpl.setRabbitOperations(rabbitOperations);
		return mqSenderDefaultImpl;
	}
	
	
	@Bean
	public SpringUtil springUtil(){
		return new SpringUtil();
	}
}
