package com.book.store.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.model.entities.Book;
import com.book.store.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/v1/books")
public class BookResource {
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping(path = "")
	public ResponseEntity<List<Book>> getBooks(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "9") int pageSize,
			@RequestParam(defaultValue = "name") String sortBy) {
		
		List<Book> books =
				bookService.findAllPaginatedAsList(pageNumber, pageSize, sortBy);
		
		return ResponseEntity.ok(books);
	}

}
