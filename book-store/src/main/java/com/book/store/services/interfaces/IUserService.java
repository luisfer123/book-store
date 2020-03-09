package com.book.store.services.interfaces;

import java.util.List;

import com.book.store.model.entities.User;

public interface IUserService {

	/**
	 * Find a subset of Users defined by the required page.
	 * The total number of Users is divided into pages of the specified size.
	 * 
	 * @param pageNumber Number of page requested. Goes from 0 to #users/pageSize. Defaults: 0.
	 * @param pageSize Number of users to be included in each page. Defaults: 9
	 * @param sortBy parameter used to sort the users before build the required page. Defaults: username
	 * @return List of Users
	 * @exception When the required page is empty.
	 */
	List<User> findAllPaginated(int pageNumber, int pageSize, String sortBy);
	
	User saveNewUser(User user);
	
	void deleteById(Long id);

}