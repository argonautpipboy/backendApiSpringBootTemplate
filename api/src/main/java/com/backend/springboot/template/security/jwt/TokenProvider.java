package com.backend.springboot.template.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

	private final Logger log	= LoggerFactory.getLogger(TokenProvider.class);

	private static final String	AUTHORITIES_KEY	= "auth";

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.validityInSeconds:3600}")
	private long tokenValidityInSeconds;

	@Value("${jwt.validityInSecondsForRememberMe:628992000}")
	private long tokenValidityInSecondsForRememberMe;

    public String createToken(Authentication authentication, Boolean rememberMe) {
		String authorities = authentication.getAuthorities().stream().map(authority -> authority.getAuthority())
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity = new Date(now);
		if (rememberMe) {
			validity = new Date(now + (this.tokenValidityInSecondsForRememberMe * 1000));
		} else {
			validity = new Date(now + (this.tokenValidityInSeconds * 1000));
		}

		return Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
				.signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity).compact();
	}

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

		String principal = claims.getSubject();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
				.map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.warn("Invalid JWT signature: " + e.getMessage());
			return false;
		}
		catch (ExpiredJwtException e) {
			log.info("Expired Jwt Token for user {} - {}", e.getClaims().getSubject(), e.getMessage());
			return false;
		}
	}
}
