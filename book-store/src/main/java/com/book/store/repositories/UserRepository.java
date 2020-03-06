package com.book.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
