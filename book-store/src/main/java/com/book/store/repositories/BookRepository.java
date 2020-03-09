package com.book.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.store.model.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
