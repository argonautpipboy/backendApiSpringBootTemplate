package com.backend.springboot.template.service;

import java.util.List;

import com.backend.springboot.template.dto.UserDTO;

public interface UserService {
	UserDTO create(UserDTO user);
	UserDTO delete(String id);
    List<UserDTO> findAll();
    UserDTO findById(String id);
    UserDTO findByUserName(String userName);
    UserDTO update(UserDTO user, boolean updatePassword);
	UserDTO findByUserNameWithPassword(String userName);
}
