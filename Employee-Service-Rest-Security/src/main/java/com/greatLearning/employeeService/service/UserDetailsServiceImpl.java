package com.greatLearning.employeeService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.greatLearning.employeeService.dao.UserRepository;
import com.greatLearning.employeeService.entity.User;
import com.greatLearning.employeeService.security.MyUserDetails;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);

		if (user == null) {
			System.out.println("Could not find user....."+username);
			throw new UsernameNotFoundException("Could not find user");
		}
		
		System.out.println("User from the repository ");
        System.out.println(user);

		return new MyUserDetails(user);
	}

}
