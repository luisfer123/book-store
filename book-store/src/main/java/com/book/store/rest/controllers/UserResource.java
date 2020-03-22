package com.book.store.rest.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.book.store.model.entities.User;
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
	
	@PostMapping(path = "")
	public ResponseEntity<String> createUser(@RequestBody User newUser) {
		
		newUser = userService.saveNewUser(newUser);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
			.path("{id}")
				.buildAndExpand(newUser.getId())
				.toUri();
		
		return ResponseEntity
				.created(location)
				.build();
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
