package com.book.store.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.model.entities.Authority;
import com.book.store.model.enums.ERole;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	Optional<Authority> findByAuthority(ERole authority);

}
