package com.book.store.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
	
	@Id
	@Column(name = "id")
	private Long id;
		
	@OneToOne
	@MapsId
	@JoinColumn(name = "users_id")
	private User user;

}
