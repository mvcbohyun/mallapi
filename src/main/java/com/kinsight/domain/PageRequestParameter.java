package com.kinsight.domain;

import lombok.Data;


@Data
public class PageRequestParameter<T> {

	private MySQLPageRequest pageRequest;
	private T parameter;

	public PageRequestParameter(MySQLPageRequest pageRequest, T parameter) {
		this.pageRequest = pageRequest;
		this.parameter = parameter;
	}

}