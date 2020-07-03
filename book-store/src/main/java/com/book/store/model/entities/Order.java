package com.book.store.model.entities;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.book.store.model.enums.EOrderStatus;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private EOrderStatus status;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;
	
	@OneToMany(mappedBy = "order")
	private Set<BookItem> bookItems;
	
	@OneToMany(
			mappedBy = "order",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<OrderBook> books;
	
	public void addBook(Book book) {
		OrderBook orderBook = new OrderBook(book, this);
		books.add(orderBook);
		book.getOrders().add(orderBook);
	}
	
	public void removeBook(Book book) {
		for(Iterator<OrderBook> iterator = books.iterator(); iterator.hasNext(); ) {
			OrderBook orderBook = iterator.next();
			
			if(orderBook.getBook().equals(book) &&
					orderBook.getOrder().equals(this)) {
				iterator.remove();
				orderBook.getBook().getOrders().remove(orderBook);
				orderBook.setBook(null);
				orderBook.setOrder(null);
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EOrderStatus getStatus() {
		return status;
	}

	public void setStatus(EOrderStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Set<BookItem> getBookItems() {
		return bookItems;
	}

	public void setBookItems(Set<BookItem> bookItems) {
		this.bookItems = bookItems;
	}

	public Set<OrderBook> getBooks() {
		return books;
	}

	public void setBooks(Set<OrderBook> books) {
		this.books = books;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(o == null || getClass() != o.getClass())
			return false;
		
		Order other = (Order) o;
		return id != null
				&& id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		return 35;
	}

}
