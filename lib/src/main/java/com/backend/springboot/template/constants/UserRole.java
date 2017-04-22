package com.backend.springboot.template.constants;

/**
 * Roles de l'application
 */
public enum UserRole {
	ADMIN("ADMIN"), USER("USER");
	
	private final String role;
	
	private UserRole(String role){
		this.role = role;
	}
	
	@Override
	public String toString(){
		return role;
	}
}
