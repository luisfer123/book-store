package com.book.store.rest.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.model.entities.Authority;
import com.book.store.model.entities.User;
import com.book.store.model.enums.ERole;
import com.book.store.repositories.AuthorityRepository;
import com.book.store.repositories.UserRepository;
import com.book.store.security.UserDetailsImpl;
import com.book.store.security.jwt.JwtUtils;
import com.book.store.security.jwt.payload.JwtResponse;
import com.book.store.security.jwt.payload.LoginRequest;
import com.book.store.security.jwt.payload.MessageResponse;
import com.book.store.security.jwt.payload.SignupRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/auth")
public class JwtAuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AuthorityRepository authorityRepo;
	
	@PostMapping(value = "/login")
	public ResponseEntity<Object> authenticateUser(
			@Valid @RequestBody LoginRequest loginRequest) throws AuthenticationException {
		
		Authentication auth = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwtToken = jwtUtils.generateToken(auth);
		
		UserDetailsImpl principal = 
				(UserDetailsImpl) auth.getPrincipal();
		List<String> roles = 
							principal
								.getAuthorities()
								.stream()
								.map(authority -> authority.getAuthority())
								.collect(Collectors.toList());
		
		return ResponseEntity.ok(
					new JwtResponse(
							jwtToken,
							principal.getId(),
							principal.getUsername(),
							principal.getEmail(),
							jwtUtils.getExpirationAt(jwtToken),
							roles)
					);
		
	}
	
	@PostMapping(value = "/signup")
	public ResponseEntity<Object> registerUser(
			@Valid @RequestBody SignupRequest signupRequest) {
		
		if(userRepo.existsByEmail(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username already in use!"));
		}
		
		if(userRepo.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email already associated with an account!"));
		}
		
		User newUser = new User(
				signupRequest.getUsername(),
				signupRequest.getEmail(),
				signupRequest.getPassword()
			);
		
		Set<String> strAuthorities = signupRequest.getRoles();
		Set<Authority> authorities = new HashSet<>();
		
		if(strAuthorities == null) {
			Authority userAuth = authorityRepo.findByAuthority(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role not found!"));
			authorities.add(userAuth);
		} else {
			
			strAuthorities.forEach(
					strAuthority -> {
						switch(strAuthority) {
						case "admin":
							Authority adminAuthority = authorityRepo
									.findByAuthority(ERole.ROLE_ADMIN)
									.orElseThrow(() -> new RuntimeException("Error: Authority not found!"));
							authorities.add(adminAuthority);
							
							break;
							
						default:
							Authority userAuthority = authorityRepo
									.findByAuthority(ERole.ROLE_USER)
									.orElseThrow(() -> new RuntimeException("Error: Authority not found!"));
							
							authorities.add(userAuthority);
							
							break;
						}
					});
		}
		
		newUser.setAuthorities(authorities);
		userRepo.save(newUser);
		
		return ResponseEntity.ok(null);
	}

}
