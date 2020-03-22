package com.book.store.rest.controllers;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@PostMapping(path = "")
	public ResponseEntity<Book> createBook(
			@Valid @RequestBody Book book,
			@RequestParam(name = "imageFile", required = false) MultipartFile pictureFile) throws IOException {
		
		if(pictureFile != null && !pictureFile.isEmpty())
			book.setPicture(pictureFile.getBytes());
		
		Book newBook = bookService.createBook(book);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newBook.getId())
				.toUri();
		
		return ResponseEntity
				.created(location)
				.build();
	}

}
