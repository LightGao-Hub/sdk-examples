package org.example.sdk.app.starter.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

/**
 * 参数配置类
 */
@Getter
@Configurable
public class AppWebConfig {

	@Value("${tenant.order.name}")
	private String orderName;

}
