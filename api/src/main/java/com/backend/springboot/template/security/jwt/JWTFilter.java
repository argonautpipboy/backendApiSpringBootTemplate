package com.backend.springboot.template.security.jwt;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.backend.springboot.template.dto.UserDTO;
import com.backend.springboot.template.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.backend.springboot.template.service.UserService;

/**
 * Filters incoming requests and installs a Spring Security principal if a header
 * corresponding to a valid user is found.
 */
@Component
public class JWTFilter extends GenericFilterBean {
	private static final Logger	log	= LoggerFactory.getLogger(JWTFilter.class);
	@Inject
	private TokenProvider tokenProvider;

	@Inject
	private UserService	userService;

	public JWTFilter() {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
//		try {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String jwt = resolveToken(httpServletRequest);
		if (StringUtils.hasText(jwt)) {
			if (this.tokenProvider.validateToken(jwt)) {
				Authentication authentication = this.tokenProvider.getAuthentication(jwt);
				boolean accountNonLocked = false;
				try {
					UserDTO user = userService.findByUserName(authentication.getName());
					accountNonLocked = user.isEnabled();
				} catch (UserNotFoundException e) {
					log.info("Account {} not found", authentication.getName());
				}
				if (accountNonLocked) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
//		}
//		catch (ExpiredJwtException eje) {
//			log.info("Security exception for user {} - {}", eje.getClaims().getSubject(), eje.getMessage());
//			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//		}
	}

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			// 7 for the number of "Bearer " characters
			String jwt = bearerToken.substring(7, bearerToken.length());
			return jwt;
		}
		return null;
	}
}
