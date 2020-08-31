package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.Order;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exception.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

@Validated
@RestController
@RequestMapping(value = "/hateoas/users")
public class UserHateoasController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

//	@GetMapping
//	public CollectionModel<User> getAllUsers() throws UserNotFoundException {
//
//		CollectionModel<User> allUsers = userService.getAllUsers();
//		for (User user : allUsers) {
//			Long userid = user.getId();
//			Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userid).withSelfRel();
//			user.add(selfLink);
//
//			List<Order> orders = ControllerLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userid);
//			Link ordersLink = ControllerLinkBuilder.linkTo(orders).withRel("all-orders");
//			user.add(ordersLink);
//		}
//
//		Link link = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();
//		
//		return allUsers;
//	}

	@GetMapping("/{id}")
	public RepresentationModel<User> getUserById(@PathVariable("id") @Min(3) Long id) {
		try {
			Optional<User> optionalUser = userService.getUserById(id);
			User user = optionalUser.get();
			Long userId = user.getId();
			Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
			user.add(selfLink);
			// RepresentationModel<User> finalUser = new RepresentationModel<>();
			return user;
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
