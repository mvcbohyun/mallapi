package com.kinsight.domain;



import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Long id;
	private String nickname;
	private String email;
	private String password;
	private boolean enabled;
	private String joindate;
	private String mailkey;
	private boolean mailyn;
	private String newpwkey;
	private boolean newpwyn;
	private LocalDateTime mailpwtime;
	private String role;
	
	
	
}
