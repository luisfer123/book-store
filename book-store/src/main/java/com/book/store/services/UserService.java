package com.book.store.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.book.store.exceptions.PageNumberNotValidException;
import com.book.store.exceptions.ResourceNotFoundException;
import com.book.store.exceptions.UserNotFoundException;
import com.book.store.model.entities.User;
import com.book.store.repositories.UserRepository;
import com.book.store.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = true)
	public List<User> findAllPaginated(
			int pageNumber, int pageSize, String sortBy) {
		
		Pageable pageRequest = 
				PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<User> usersPage = userRepo.findAll(pageRequest);
				
		if(!usersPage.hasContent()) {
			if(usersPage.getTotalPages() < pageNumber || usersPage.getTotalPages() < 0)
				throw new PageNumberNotValidException();
			else
				throw new ResourceNotFoundException();
		}
		
		return usersPage.getContent();
	}
	
	@Override
	@Transactional(readOnly = true)
	public User findById(Long id) throws UserNotFoundException {
		Optional<User> oUser = userRepo.findById(id);
		
		return oUser.orElseThrow(
				() -> new UserNotFoundException(
						"User with id: " + id + " was not found!")
			);
	}
	
	@Override
	@Transactional
	public User saveNewUser(User newUser) {
		return userRepo.save(newUser);
	}
	
	@Override
	@Transactional
	public User updateUserData(User user) throws UserNotFoundException {
		
		User userUpdated = userRepo.findById(user.getId())
				.orElseThrow(() -> 
					new UserNotFoundException("User with id " + user.getId() + " was not found1"));
		
		userUpdated.setUsername(user.getUsername());
		userUpdated.setEmail(user.getEmail());
		
		return userRepo.save(userUpdated);
	}
	
	@Override
	@Transactional
	public void updatePassword(Long userId, String newPassword) 
			throws UserNotFoundException {
		
		if(!userRepo.existsById(userId))
			throw new UserNotFoundException("An User with the id: " + userId + " was not found!");
		
		newPassword = encoder.encode(newPassword);
		userRepo.updatePasswordById(newPassword, userId);
	}
	
	@Override
	@Transactional
	public void deleteById(Long id) {
		Optional<User> oUser = userRepo.findById(id);
		
		if(!oUser.isPresent())
			throw new RuntimeException();
		
		userRepo.delete(oUser.get());
	}

}
