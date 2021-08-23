package com.kinsight.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contents {

	private Long id;
	private String title;
	private String content;
	private int price;
	private Long categorylid;
	private Long categorymid;
	private boolean coninout;
	private Long inid;
	private String indate;
	private Long updid;
	private String upddate;
}
