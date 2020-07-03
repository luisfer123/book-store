package com.book.store.model.entities;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@NotBlank
	@Column(name = "name")
	private String name;
	
	@NotBlank
	@Column(name = "author")
	private String author;
	
	@NotNull
	@Column(name = "price")
	private BigDecimal price;
	
	@Lob
	@Column(name = "picture")
	private byte[] picture;
	
	@OneToMany(mappedBy = "book")
	private Set<BookItem> bookItems;
	
	@OneToMany(
			mappedBy = "book",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<OrderBook> orders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public Set<BookItem> getBookItems() {
		return bookItems;
	}

	public void setBookItems(Set<BookItem> bookItems) {
		this.bookItems = bookItems;
	}

	public Set<OrderBook> getOrders() {
		return orders;
	}

	public void setOrders(Set<OrderBook> orders) {
		this.orders = orders;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		
		if(getClass() != o.getClass())
			return false;
		
		Book other = (Book) o;
		return id != null &&
				id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		return 99;
	}

}
