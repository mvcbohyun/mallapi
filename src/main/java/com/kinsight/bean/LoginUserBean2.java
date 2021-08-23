package com.kinsight.bean;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserBean2 {
	private Long id ; 
	private String email;
	private String nickname;
	private boolean mailyn;
	private String token;
	private String role;
	}
