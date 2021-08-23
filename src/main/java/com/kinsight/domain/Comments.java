package com.kinsight.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

	private Long id;
	private Long boardid;
	private String content;
	private String indate;
	private Long inid;
	private String upddate;
	private Long updid;
	private Long replyid;
	private int seq;
	private int depth;
	
	
}
