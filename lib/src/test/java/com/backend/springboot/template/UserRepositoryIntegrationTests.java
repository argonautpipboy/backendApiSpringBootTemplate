package com.backend.springboot.template;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backend.springboot.template.constants.UserRole;
import com.backend.springboot.template.mongodb.model.User;
import com.backend.springboot.template.mongodb.repositories.UserRepository;

@ContextConfiguration(classes = {MongoDBConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryIntegrationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCreateUser() {
		User userSaved = userRepository.save(createUserObject());
		assertEquals("test@test.com", userSaved.getUserName());
	}

	@Test
	public void testDeleteUser(){
		userRepository.delete(createUserObject());
		User userDeleted = userRepository.findByUserNameIgnoreCase(createUserObject().getUserName());
		assertNull(userDeleted);
	}

	/**
	 * User test creation
	 * @return
	 */
	private User createUserObject(){
		return new User("test@test.com", "test", true, UserRole.USER.toString(),null);
	}
}
