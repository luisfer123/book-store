package com.book.store.model.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.book.store.model.enums.ERole;

@Entity
@Table(name = "authorities")
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	
	@Column(name = "authority")
	@Enumerated(EnumType.STRING)
	private ERole authority;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
	private Set<User> users;
	
	public Authority() { }
	
	public Authority(ERole authority) {
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ERole getAuthority() {
		return authority;
	}

	public void setAuthority(ERole authority) {
		this.authority = authority;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public boolean equals(Object o) {
		
		if(o == this)
			return true;
		
		if(!(o instanceof Authority))
			return false;
		
		Authority other = (Authority) o;
		
		return id != null
				&& id.equals(other.getId());
	}
	
	public int hashCode() {
		return 99;
	}

	@Override
	public String toString() {
		return "Authority [id=" + id + ", authority=" + authority + "]";
	}
	
	

}
