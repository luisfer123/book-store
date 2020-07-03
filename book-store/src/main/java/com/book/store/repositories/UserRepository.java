package com.book.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.book.store.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	boolean existsById(Long id);
	
	@Modifying
	@Query("update User u set u.password = :newPassword where u.id = :userId")
	void updatePasswordById(@Param("newPassword") String newPassword, @Param("userId") Long userId);

}
