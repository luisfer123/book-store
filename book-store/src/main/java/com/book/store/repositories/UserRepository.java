package com.book.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);

}
