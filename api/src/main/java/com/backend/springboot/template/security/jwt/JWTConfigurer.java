package com.backend.springboot.template.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	public final static String	AUTHORIZATION_HEADER	= "Authorization";

	private JWTFilter			jwtFilter;

	public JWTConfigurer(JWTFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
