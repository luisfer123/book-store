package com.book.store.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.store.exceptions.PageNumberNotValidException;
import com.book.store.exceptions.ResourceNotFoundException;
import com.book.store.model.entities.Book;
import com.book.store.repositories.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepo;
	
	@Transactional(readOnly = true)
	public Page<Book> findAllPaginated(
			int pageNumber, int pageSize, String sortBy) {
		
		Pageable requestedPage = 
				PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		
		Page<Book> pageBooks = bookRepo.findAll(requestedPage);
		
		if(!pageBooks.hasContent()) {
			if(pageBooks.getTotalPages() < pageNumber || pageBooks.getTotalPages() < 0)
				throw new PageNumberNotValidException();
			if(bookRepo.findAll().isEmpty())
				throw new ResourceNotFoundException();
		}
		
		return pageBooks;
	}
	
	@Transactional(readOnly = true)
	public List<Book> findAllPaginatedAsList(
			int pageNumber, int pageSize, String sortBy) {
		
		return findAllPaginated(pageNumber, pageSize, sortBy).getContent();
	}
	
	@Transactional
	public Book createBook(Book book) {
		return bookRepo.save(book);
	}
	
	@Transactional
	public Book updateImageByBookId(byte[] image, long bookId)
		throws ResourceNotFoundException {
		
		if(image.length < 1)
			throw new RuntimeException("Image is not valid!");
		
		Book book = bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException());
		
		book.setPicture(image);
		
		return bookRepo.save(book);
		
	}
	
	@Transactional
	public Book updateBookInfo(Long bookId, Book updatedBook)
			throws ResourceNotFoundException {
		
		Book book = bookRepo.findById(bookId).orElseThrow(() ->
			new ResourceNotFoundException()
		);
		
		book.setName(updatedBook.getName());
		book.setAuthor(updatedBook.getAuthor());
		book.setPrice(updatedBook.getPrice());
		
		return bookRepo.save(book);
	}

	@Transactional(readOnly = true)
	public Book findById(Long id) throws ResourceNotFoundException {
		return bookRepo
				.findById(id)
				.orElseThrow(
						() -> new ResourceNotFoundException());
	}

}
