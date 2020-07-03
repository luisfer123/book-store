package com.book.store.model.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "orders_has_books")
public class OrderBook {
	
	@EmbeddedId
	private OrderBookId id;
	
	@ManyToOne
	@MapsId("bookId")
	@JoinColumn(name = "books_id")
	private Book book;
	
	@ManyToOne
	@MapsId("orderId")
	@JoinColumn(name = "orders_id")
	private Order order;
	
	// How many book items were selected from the book referenced 
	// by this relationship.
	@Column(name = "quantity")
	private int bookItemsNum;

	public OrderBook() {
		super();
	}

	public OrderBook(Book book, Order order) {
		super();
		this.id = new OrderBookId(order.getId(), book.getId());
		this.book = book;
		this.order = order;
	}

	public OrderBookId getId() {
		return id;
	}

	public void setId(OrderBookId id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public int getBookItemsNum() {
		return bookItemsNum;
	}

	public void setBookItemsNum(int bookItemsNum) {
		this.bookItemsNum = bookItemsNum;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(o == null || o.getClass() != getClass())
			return false;
		
		OrderBook other = (OrderBook) o;
		return Objects.equals(book, other.book) &&
				Objects.equals(order, other.order);
				
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(book, order);
	}

}
