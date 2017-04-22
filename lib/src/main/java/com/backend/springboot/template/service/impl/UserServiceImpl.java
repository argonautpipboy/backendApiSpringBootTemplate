package com.backend.springboot.template.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.backend.springboot.template.constants.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.springboot.template.dto.UserDTO;
import com.backend.springboot.template.exception.UserNotFoundException;
import com.backend.springboot.template.mongodb.model.User;
import com.backend.springboot.template.mongodb.repositories.UserRepository;
import com.backend.springboot.template.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository repository;

    @Inject
    private PasswordEncoder passwordEncoder;

	@Autowired
	UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

	@Override
    public UserDTO create(UserDTO user) {
		if (user == null || user.getUserName() == null || user.getUserPassword() == null) {
			throw new IllegalArgumentException("Incomplete UserDTO, it is either empty, userName or password is missing");
		}
        User persistedUser = new User(user.getUserName(), user.getUserPassword(), user.isEnabled(), UserRole.USER.name(),
        		new Date());
        User existingPersistedUser = repository.findByUserNameIgnoreCase(user.getUserName());
        if(existingPersistedUser == null) {
        	persistedUser.setUserPassword(passwordEncoder.encode(persistedUser.getUserPassword()));
        	persistedUser = repository.save(persistedUser);
        } else {
			throw new IllegalArgumentException("User " + user.getUserName() + " already exists");
        }
        return convertToDTO(persistedUser);
    }

    @Override
    public UserDTO delete(String id) {
        User deleted = findUserById(id);
        repository.delete(deleted);
        return convertToDTO(deleted);
    }

    @Cacheable("user")
    @Override
	public UserDTO findByUserName(String userName) {
		User found = repository.findByUserNameIgnoreCase(userName);
		if (found == null) {
			throw new UserNotFoundException(userName);
		}
		return convertToDTO(found);
	}

	@Override
	public UserDTO findByUserNameWithPassword(String userName) {
		User found = repository.findByUserNameIgnoreCase(userName);
		if (found == null) {
			throw new UserNotFoundException(userName);
		}
		return convertToDTO(found, false);
	}

    @Override
    public List<UserDTO> findAll() {
        List<User> userEntries = repository.findAll();
        return convertToDTOs(userEntries);
    }

    private List<UserDTO> convertToDTOs(List<User> models) {
        return models.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(String id) {
        User found = findUserById(id);
        return convertToDTO(found);
    }

    @Override
    public UserDTO update(UserDTO user, boolean updatePassword) {
    	User updated = findUserById(user.getId());
    	updated.setUserName(user.getUserName());
    	if (updatePassword) {
    		if(user.getUserPassword() == null) {
    			throw new IllegalArgumentException("Password shouldn't be null");
    		}
        	updated.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
    	}
    	updated.setEnabled(user.isEnabled());
    	if(user.getRole().equals(UserRole.USER.name()) || user.getRole().equals(UserRole.ADMIN.name())){
    		updated.setRole(user.getRole());
    	}
        updated = repository.save(updated);
        return convertToDTO(updated);
    }

	/**
	 * Get user by id, can be used in case we update the userName
	 * @param id, the technical id to give
	 * @return the found User
	 */
	private User findUserById(String id) {
        return repository.findById(id);
    }

    /**
     * Convert a user into a UserDTO
     * @param model, the user model to convert
     * @return the converted UserDTO
     */
    private UserDTO convertToDTO(User model) {
    	return convertToDTO(model, true);
    }

    /**
     * Convert a user into a UserDTO
     * @param model, the user model to convert
	 * @param erasePassword, used to know if we need to hide password or not
     * @return the converted UserDTO
     */
    private UserDTO convertToDTO(User model, boolean erasePassword) {
    	if(model != null){
    		UserDTO dto = new UserDTO();
	        dto.setId(model.getId());
	        dto.setUserName(model.getUserName());
	        dto.setEnabled(model.isEnabled());
	        dto.setRole(model.getRole());
	        if (!erasePassword) {
		        dto.setUserPassword(model.getUserPassword());
	        }
	        dto.setDateCreation(model.getDateCreation());
	        return dto;
    	} else{
    		return null;
    	}
    }

}