package com.kinsight.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties
public class SMSProperties {

	private String aligo_id;
	private String aligo_key;
	private String aligo_calloutnumber;


}
