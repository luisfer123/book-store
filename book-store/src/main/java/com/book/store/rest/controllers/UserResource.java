package com.book.store.rest.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.exceptions.UserNotFoundException;
import com.book.store.model.entities.User;
import com.book.store.model.payloads.ConfirmPasswordCommand;
import com.book.store.services.interfaces.IUserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/users")
public class UserResource {

	@Autowired
	private IUserService userService;
	
	@GetMapping(path = "")
	public ResponseEntity<List<User>> getUsers(
			@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "9") Integer pageSize,
			@RequestParam(defaultValue = "username") String sortBy) {
		
		List<User> usersPage =
				userService.findAllPaginated(pageNumber, pageSize, sortBy);
		
		return new ResponseEntity<List<User>>(
				usersPage, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public EntityModel<User> getUser(@PathVariable("id") Long id) 
			throws UserNotFoundException {
		
		User user = userService.findById(id);
		
		Link linkAllUsers = linkTo(methodOn(this.getClass())
				.getUsers(0, 9, "id"))
				.withRel("All-Users");
		
		Link selfLink = linkTo(methodOn(this.getClass())
				.getUser(user.getId()))
				.withSelfRel();
		
		
		return new EntityModel<User>(user, linkAllUsers, selfLink);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateUserGeneralInfo(
			@PathVariable("id") Long id,
			@RequestBody User user) {
		
		userService.updateUserData(user);
		
		return ResponseEntity
				.noContent()
				.build();
	}
	
	@PutMapping(path = "/{id}/updatePassword")
	public ResponseEntity<?> updatePassword(
			@RequestBody @Valid ConfirmPasswordCommand confirmPassword) {
		
		System.out.println("password updated!");
		
		return ResponseEntity
				.noContent()
				.build();
		
	}
}
