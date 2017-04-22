package com.backend.springboot.template.configuration;

import javax.inject.Inject;

import com.backend.springboot.template.constants.UserRole;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.springboot.template.security.Http401UnauthorizedEntryPoint;
import com.backend.springboot.template.security.jwt.JWTConfigurer;
import com.backend.springboot.template.security.jwt.JWTFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@ComponentScan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public final static String TOKEN_DOMAIN = "token";

    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Inject
    private UserDetailsService userDetailsService;

    @Inject
    private JWTFilter jwtFilter;

    @Inject
    private PasswordEncoder passwordEncoder;

	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        	.exceptionHandling()
        	.authenticationEntryPoint(authenticationEntryPoint)
        .and()
    		.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.authorizeRequests()
				.antMatchers("/metrics", "/metrics.json", "/metrics/**").hasRole(UserRole.ADMIN.name())
				.antMatchers("/token/authenticate", "/health", "/user/send", "/info").permitAll()
				.anyRequest().authenticated()
		.and()
            .apply(new JWTConfigurer(jwtFilter));
	}

}
