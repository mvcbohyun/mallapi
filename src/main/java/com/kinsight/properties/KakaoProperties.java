package com.kinsight.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties
public class KakaoProperties {

			private String Kakao_RESTAPI;
			private String Kakao_JavaScript;
			private String Kakao_Admin;
}
