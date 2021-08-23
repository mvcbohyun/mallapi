package com.kinsight.bean;

import lombok.Data;

@Data
public class CommentsReturnBean {

	 private Long id;
	 private String content;
	 private Long replyid;
	 private Long seq;
	 private Long inid;
	 private String nickname;
	 private int depth;
}
