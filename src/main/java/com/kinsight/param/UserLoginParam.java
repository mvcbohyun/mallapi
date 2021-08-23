package com.kinsight.param;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserLoginParam {
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	}
