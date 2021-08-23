package com.kinsight.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AddressSaveParam {
	
	@NotNull
	private Long userid;
	@NotEmpty
	private String address1;
	@NotEmpty
	private String address2;
	@NotEmpty
	private String zipcode;
	@NotEmpty
	private String gubun;
}
