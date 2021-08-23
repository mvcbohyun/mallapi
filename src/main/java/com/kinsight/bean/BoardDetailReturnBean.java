package com.kinsight.bean;

import java.util.List;

import lombok.Data;

@Data
public class BoardDetailReturnBean {
	private Long id;
	private String title;
	private String content;
	private String indate;
	private String nickname; 
	private List<FileReturnBean> filelist;
}
