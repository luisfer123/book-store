package com.book.store.rest.controllers;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.book.store.exceptions.ResourceNotFoundException;
import com.book.store.model.entities.Book;
import com.book.store.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/api/v1/books")
public class BookResource {
		
	@Autowired
	private BookService bookService;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@RequestMapping(path = "")
	public ResponseEntity<Page<Book>> getBooks(
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "9") int pageSize,
			@RequestParam(defaultValue = "name") String sortBy) {
		
		System.out.println("Query params, page number: " + pageNumber);
		
		Page<Book> booksPage =
				bookService.findAllPaginated(pageNumber, pageSize, sortBy);
		
		if(pageNumber > booksPage.getTotalPages()) {
			throw new ResourceNotFoundException();
		}
		
		return ResponseEntity.ok(booksPage);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Book> getBook(
			@PathVariable("id") Long bookId) {
		
		Book book = bookService.findById(bookId);
		System.out.println("book id: " + bookId);
		
		return ResponseEntity.ok(book);
	}
	
	@GetMapping(path="/{id}/imageBook")
	public ResponseEntity<Map<String, String>> getBookImageById(
			@PathVariable("id") Long id) throws IOException {
		
		Book book = bookService.findById(id);
		Map<String, String> imageResponse = new HashMap<String, String>();
		byte[] image;
		
		if(book.getPicture() == null || book.getPicture().length <= 0) {
			
			Resource imageResource = resourceLoader.getResource("classpath:static/images/bookImage.png");			
			image = Files.readAllBytes(Paths.get(imageResource.getURI()));
			
		} else {
			image = book.getPicture();
			
		}
		
		String bookImage = Base64
				.getEncoder()
				.encodeToString(image);
		
		imageResponse.put("value", bookImage);
				
		return ResponseEntity
				.ok(imageResponse);
	}
	
	@PostMapping(path = "")
	public ResponseEntity<Book> createBook(
			@Valid @RequestBody Book book ) {
		
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
	
	@PutMapping(path = "/{id}/updateImage")
	public ResponseEntity<?> updateBookImage(
			@RequestParam(name = "imageFile", required = false) MultipartFile pictureFile,
			@PathVariable("id") Long bookId)
					throws IOException, ResourceNotFoundException {
		
		byte[] picture;
		
		if(pictureFile != null && !pictureFile.isEmpty()) {
			picture = pictureFile.getBytes();
			bookService.updateImageByBookId(picture, bookId);
		} else {
			throw new ResourceNotFoundException();
		}
					
		return ResponseEntity
				.ok()
				.build();
	}
	
	@PutMapping(path = "/{id}/updateInfo")
	public ResponseEntity<Book> updateBookInformation(
			@RequestBody @Valid Book updatedBook,
			@PathVariable("id") Long bookId) {
		
		Book book = bookService
				.updateBookInfo(bookId, updatedBook);
				
		return ResponseEntity
				.ok(book);
		
	}

}
