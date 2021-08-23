package com.kinsight.param;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserSaveParam {
	@NotEmpty
	@Size(max = 50)
	private String nickname;
	@NotEmpty
	@Email
	@Size(max = 100)
	private String email;
	@NotEmpty
	@Size(max = 100)
	private String password;
	private LocalDateTime emailtimechk ; 
		

}
