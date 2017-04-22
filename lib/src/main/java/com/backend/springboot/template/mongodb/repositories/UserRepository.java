package com.backend.springboot.template.mongodb.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.springboot.template.mongodb.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	@Override
	public void delete(User deleted);
	@Override
	public User save(User saved);
	public User findById(String id);
	public User findByUserNameIgnoreCase(String firstName);
    @Override
	public List<User> findAll();
}
