package com.backend.springboot.template.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.backend.springboot.template.security.AuthenticationRequest;
import com.backend.springboot.template.security.jwt.JWTToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backend.springboot.template.security.jwt.JWTConfigurer;
import com.backend.springboot.template.security.jwt.TokenProvider;

@RestController
@RequestMapping("/token")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Inject
	private TokenProvider			tokenProvider;

	@Inject
	private AuthenticationManager	authenticationManager;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authorize(@Valid @RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword());

		try {
			Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			boolean rememberMe = authenticationRequest.isRememberMe() != null ? authenticationRequest
					.isRememberMe() : false;
			String jwt = tokenProvider.createToken(authentication, rememberMe);
			response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);
			return ResponseEntity.ok(new JWTToken(jwt));
		} catch (AuthenticationException exception) {
			log.info("AuthenticationException : " + exception.toString());
			return new ResponseEntity<>("[\"" + exception.getLocalizedMessage() + "\"]", HttpStatus.UNAUTHORIZED);
		}

	}

}
