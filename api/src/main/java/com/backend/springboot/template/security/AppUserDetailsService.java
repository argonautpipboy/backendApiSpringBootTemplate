package com.backend.springboot.template.security;

import javax.inject.Inject;

import com.backend.springboot.template.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.backend.springboot.template.service.UserService;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class AppUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(AppUserDetailsService.class);

	@Inject
	private UserService	userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("loadUserByUsername {}", username);
		UserDTO user = userService.findByUserNameWithPassword(username);
		UserDetails userDetails = null;
		if (user != null) {
			userDetails = new SimpleUserDetails(user.getUserName(), user.getUserPassword(),
					user.isEnabled(), user.getRole());
		}
		return userDetails;
	}


}
