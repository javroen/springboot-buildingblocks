package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exception.UserAlreadyExistsException;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Long id) throws UserNotFoundException{
		Optional<User> user =userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("User not found.");
		}
		return user;
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
		
	}
	public User updateUserById(Long id, User user) throws UserNotFoundException{
		Optional<User> optionaluser =userRepository.findById(id);
		
		if(!optionaluser.isPresent()) {
			throw new UserNotFoundException("User not found.");
		}
		
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) {
		Optional<User> optionaluser =userRepository.findById(id);
		
		if(!optionaluser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found.");
		}
		userRepository.deleteById(id);
	}
	
	public User createUser(User user) throws UserAlreadyExistsException{
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		if(existingUser != null) {
			throw new UserAlreadyExistsException("User already exists");
		}
		
		return userRepository.save(user);
	}
}
