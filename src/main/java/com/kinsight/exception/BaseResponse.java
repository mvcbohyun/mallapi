package com.kinsight.exception;

import lombok.Data;

@Data
public class BaseResponse<T> {

	private BaseResponseCode code;
	private String message;
	private T data;
	
	public BaseResponse(T data) {
		this.code = BaseResponseCode.SUCCESS;
		this.data = data;
	}

	public BaseResponse(BaseResponseCode code, String message) {
		this.code = code;
		this.message = message;
	}
	
}