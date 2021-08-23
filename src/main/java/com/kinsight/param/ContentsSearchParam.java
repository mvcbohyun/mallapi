package com.kinsight.param;

import lombok.Data;

@Data
public class ContentsSearchParam {
	private String  keyword;
	private Long    userid;
	private String  searchType;
	private Integer lowprice;
	private Integer highprice;
	private Long    categorylid;
	private Long    categorymid;
	
}
