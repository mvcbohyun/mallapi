package com.kinsight.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private Long id;
	private String title;
	private String content;
	private boolean enabled;
	private String indate;
	private Long inid;
	private String upddate;
	private Long updid;
	private Long categoryid;
	private boolean secret;
	
	
	
	
}
