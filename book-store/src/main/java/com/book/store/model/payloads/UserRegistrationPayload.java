package com.book.store.model.payloads;

import java.util.Set;

import javax.persistence.Persistence;
import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.crypto.Data;

import org.hibernate.type.Type;

import com.book.store.model.entities.User;
import com.book.store.model.validation.PasswordMatch;

/**
 * Payload used to validate user information before creating a
 * new user. This payload class is used instead of the User entity
 * directly in the controller to facilitate the validation process, 
 * so we do not need to add the confirmPassword field to the User 
 * entity since it is used only at registration time.
 * 
 * Once validation is done, a User entity may be obtain by calling the
 * getUser() method.
 * 
 * @author Luis Fernando Martinez Oritz
 *
 */

@GroupSequence({
	Type.class, 
	Data.class, 
	Persistence.class, 
	UserRegistrationPayload.class})
@PasswordMatch(
		groups = Data.class, 
		first = "password", 
		second = "confirmPassword",
		message = "The New Password and Confirm New Password fields must match.")
public class UserRegistrationPayload {
	
private Long id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	@Size(min = 6, max = 15)
	private String password;
	
	@NotBlank
	@Size(min = 6, max = 15)
	private String confirmPassword;
	
	@NotBlank
	@Email
	private String email;
	
	private String type;
	
	private Set<String> authorities;
	
	public User getUserWhithoutAuthorities() {
		return new User(username, password, email, type);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
	
	

}
