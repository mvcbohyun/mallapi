package com.kinsight.bean;

import lombok.Data;

@Data
public class ContentsReturnBean {

	private Long id;
	private String title;
	private int price;
	private String fullpath;
	private int likecount;
	private int likestate;
	
}
