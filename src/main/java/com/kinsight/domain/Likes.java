package com.kinsight.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Likes {

	private Long id;
	private Long typeid;
	private Long userid;
	private String tabletype;
	private String indate;
}
