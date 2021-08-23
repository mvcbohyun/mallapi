package com.kinsight.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kinsight.jwt.JwtAccessDeniedHandler;
import com.kinsight.jwt.JwtAuthenticationEntryPoint;
import com.kinsight.jwt.JwtSecurityConfig;
import com.kinsight.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

 

    public WebSecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

	@Override 
	protected void configure(HttpSecurity http) throws Exception { 
		http
        // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        .csrf().disable()

        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler)

        // 세션을 사용하지 않기 때문에 STATELESS로 설정
        
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				/*
				 * .and() .authorizeRequests() .antMatchers("/user/login").permitAll()
				 * .anyRequest().authenticated()
				 */

        .and()
        .apply(new JwtSecurityConfig(tokenProvider));
	}
	
	


	@Bean public PasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder(); 
	}
}
	 
	