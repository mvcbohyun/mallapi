package com.kinsight.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;

@Data
public class MySQLPageRequest {

	private int page;
	private int size ;

	@JsonIgnore
	private int limit;

	@JsonIgnore
	private int offset;

	public MySQLPageRequest(int page, int size, int limit, int offset) {
		this.page = page;
		this.size = size;
		this.limit = limit;
		this.offset = offset;
	}

}