package com.kinsight.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties
public class FileProperties {

	private String uploadFilePath;
	private String imageFilePath;
	private String fullfilePath;
	private String vidiofullfilePath;
}
