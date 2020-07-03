package com.book.store.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "book_items")
public class BookItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column(name = "status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "orders_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "books_id")
	private Book book;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(o == null || o.getClass() != getClass())
			return false;
		
		BookItem other = (BookItem) o;
		return id != null
				&& id.equals(other.getId());
	}
	
	@Override
	public int hashCode() {
		return 34; 
	}

}
