package com.kinsight.bean;

import java.util.List;

import lombok.Data;

@Data
public class ContentsDetailReturnBean {
	private Long id;
	private String title;
	private String content;
	private int price;
	private Long categorylid;
	private Long categorymid;
	private boolean inout;
	private String fullpath; 
}
