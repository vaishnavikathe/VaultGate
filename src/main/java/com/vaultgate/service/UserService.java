package com.vaultgate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaultgate.entity.User;
import com.vaultgate.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;

	public User register(User user) {
		return userRepository.save(user);
	}

}
