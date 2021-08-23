package com.kinsight.bean;

import lombok.Data;

@Data
public class UserSMSAdressReturn {

	private String   username   ;
	private String  phonenumber ;
	private String homeaddress1 ;
	private String homeaddress2 ;
	private String  homezipcode ;
	private String  comaddress1 ;
	private String  comaddress2 ;
	private String comzipcode	;
	private String primaryaddress;
}