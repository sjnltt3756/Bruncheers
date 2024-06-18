package com.bruncheers.user.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bruncheers.user.dto.CustomUserDetails;
import com.bruncheers.user.entity.User;
import com.bruncheers.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository repository;
	
	public CustomUserDetailsService(UserRepository repository) {
		this.repository=repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> userData = repository.findByUserEmail(username);
		
		if (userData.isPresent()) {
		    return new CustomUserDetails(userData.get());
		}
		
		return null;
	}
	

}
