package com.kinsight.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({
	FileProperties.class,SMSProperties.class,KakaoProperties.class
})
@PropertySource({
	"classpath:properties/configs.properties"
})
public class PropertiesConfiguration {

}
