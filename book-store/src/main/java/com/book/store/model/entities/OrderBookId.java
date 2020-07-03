package com.book.store.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import io.jsonwebtoken.lang.Objects;

@Embeddable
public class OrderBookId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "orders_id")
	private Long orderId;
	
	@Column(name = "books_id")
	private Long bookId;

	public OrderBookId() {
		super();
	}

	public OrderBookId(Long orderId, Long bookId) {
		super();
		this.orderId = orderId;
		this.bookId = bookId;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) 
			return true;
		
		if(o == null || getClass() != o.getClass())
			return false;
		
		OrderBookId that = (OrderBookId) o;
		return Objects.nullSafeEquals(bookId, that.bookId) &&
				Objects.nullSafeEquals(orderId, that.orderId);
	}
	
	@Override
	public int hashCode() {
		return Objects.nullSafeHashCode(new Long[] {orderId, bookId});
	}

}
