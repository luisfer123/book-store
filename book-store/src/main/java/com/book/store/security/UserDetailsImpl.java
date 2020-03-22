package com.book.store.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.book.store.model.entities.User;

/**
 * Custom implementation of the interface UserDetails from Spring Security
 * 
 * A new instance of this class may be obtain by passing a User entity instance
 * to the build(User user) method or bay using the static method builder()
 * which returns a builder of this class, which ensures at least username,
 * password and authorities are set using the builder design pattern.
 * 
 * @author Luis Fernando Martinez Oritz
 *
 */
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private UserDetailsImpl(Long id, String username, String password,
			String email, Collection<? extends GrantedAuthority> authorities) {
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}
	
	/**
	 * Builds a UserDetailsImpl using an instance of User entity.
	 * 
	 * @param user
	 * @return 
	 */
	public static UserDetailsImpl build(User user) {
		
		List<GrantedAuthority> authorities = user.getAuthorities()
				.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getAuthority().toString()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(user.getId(), user.getUsername(),
				user.getPassword(), user.getEmail(), authorities);
	}
	
	public BuilderInterfaceUsername builder() {
		return new UserDetailsImplBuilder();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public static interface BuilderInterfaceUsername {
		BuilderInterfacePassword username(String username);
	}
	
	public static interface BuilderInterfacePassword {
		BuilderInterfaceAuthorities password(String password); 
	}
	
	public static interface BuilderInterfaceAuthorities {
		BuildUserDetails authorities(
				Collection<? extends GrantedAuthority> authorities);
	}
	
	public static interface BuildUserDetails {
		BuildUserDetails id(Long id);
		BuildUserDetails email(String email);
		UserDetailsImpl build();
	}
	
	public static class UserDetailsImplBuilder 
			implements BuildUserDetails, BuilderInterfaceAuthorities,
					BuilderInterfacePassword, BuilderInterfaceUsername {
		
		private Long id;
		private String username;
		private String password;
		private String email;
		private Collection<? extends GrantedAuthority> authorities;
		
		private UserDetailsImplBuilder() { }

		@Override
		public BuilderInterfacePassword username(String username) {
			this.username = username;
			return this;
		}

		@Override
		public BuilderInterfaceAuthorities password(String password) {
			this.password = password;
			return this;
		}

		@Override
		public BuildUserDetails authorities(
				Collection<? extends GrantedAuthority> authorities) {
			this.authorities = authorities;
			return this;
		}

		@Override
		public BuildUserDetails id(Long id) {
			this.id = id;
			return this;
		}

		@Override
		public BuildUserDetails email(String email) {
			this.email = email;
			return this;
		}

		@Override
		public UserDetailsImpl build() {
			return new UserDetailsImpl(
					id, username, password, email, authorities);
		}
		
	}
	

}
