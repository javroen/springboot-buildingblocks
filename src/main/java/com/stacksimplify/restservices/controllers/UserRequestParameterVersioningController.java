package com.stacksimplify.restservices.controllers;

import java.util.Optional;

import javax.validation.constraints.Min;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.dtos.UserDtoV1;
import com.stacksimplify.restservices.dtos.UserDtoV2;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
@RequestMapping("/versioning/params/users")
public class UserRequestParameterVersioningController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value="/{id}", params="version=1")
	public UserDtoV1 getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {

		Optional<User> userOptional = userService.getUserById(id);

		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		User user = userOptional.get();
		UserDtoV1 userDTOV1 = modelMapper.map(user, UserDtoV1.class);
		return userDTOV1;
	}
	
	@GetMapping(value="/{id}", params="version=2")
	public UserDtoV2 getUserById2(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {

		Optional<User> userOptional = userService.getUserById(id);

		if (!userOptional.isPresent()) {
			throw new UserNotFoundException("User not found");
		}
		User user = userOptional.get();
		UserDtoV2 userDTOV2 = modelMapper.map(user, UserDtoV2.class);
		return userDTOV2;
	}	
}
