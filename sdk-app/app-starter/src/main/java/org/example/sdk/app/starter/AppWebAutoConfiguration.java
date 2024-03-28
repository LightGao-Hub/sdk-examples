package org.example.sdk.app.starter;

import org.example.sdk.app.starter.service.OrderService;
import org.example.sdk.app.starter.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 自动装配类
 */
@Configurable
@ComponentScan({ "org.example.sdk.app.starter" })
public class AppWebAutoConfiguration {
	@Bean
	public OrderService orderService() {
		return new OrderServiceImpl();
	}
}
