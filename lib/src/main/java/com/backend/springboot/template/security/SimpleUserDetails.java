package com.backend.springboot.template.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Specific implementation of spring security UserDetails
 */
public class SimpleUserDetails implements UserDetails, CredentialsContainer {

	private static final long serialVersionUID = 8917068078767704445L;
	private String username;
	private String password;
	private boolean enabled = true;
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

	/**
	 * Constructor
	 * @param username
	 * @param pw
	 * @param extraRoles
	 */
	public SimpleUserDetails(String username, String pw, boolean enabled, String... extraRoles) {
		this.username = username;
		this.password = pw;
		this.enabled = enabled;

		// setup roles
		Set<String> roles = new HashSet<String>();
		roles.addAll(Arrays.<String>asList(null == extraRoles ? new String[0] : extraRoles));

		// export them as part of authorities
		for (String r : roles) {
			authorities.add(new SimpleGrantedAuthority(role(r)));
		}

	}

	/**
	 * Return the Json object User
	 */
	@Override
	public String toString() {
		return "{enabled:" + isEnabled() + ", username:'" + getUsername() + "', password:[PROTECTED]'}";
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.enabled;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	private String role(String i) {
		return "ROLE_" + i;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public void eraseCredentials() {
		this.password = null;
	}
}
