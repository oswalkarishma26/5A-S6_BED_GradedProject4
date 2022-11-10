package com.greatLearning.employeeService.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.greatLearning.employeeService.dao.EmployeeRepository;
import com.greatLearning.employeeService.dao.RoleRepository;
import com.greatLearning.employeeService.dao.UserRepository;
import com.greatLearning.employeeService.entity.Employee;
import com.greatLearning.employeeService.entity.Role;
import com.greatLearning.employeeService.entity.User;

@Component
public class BootStrapAppData {
	
	@Autowired
	private  EmployeeRepository employeeRepository;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@EventListener(ApplicationReadyEvent.class)
	public void insertEmployees(ApplicationReadyEvent event) {
		Employee emp1 = new Employee();
		emp1.setFirstName("Ramesh");
		emp1.setLastName("Kumar");
		emp1.setEmail("ramesh@gmail.com");
		this.employeeRepository.save(emp1);
		
		Employee emp2 = new Employee();
		emp2.setFirstName("Suresh");
		emp2.setLastName("Kumar");
		emp2.setEmail("suresh@gmail.com");
		this.employeeRepository.save(emp2);
		
		
		User user1 = new User();
		user1.setUsername("admin");
		user1.setPassword(this.passwordEncoder.encode("admin"));
		
		User user2 = new User();
		user2.setUsername("normal");
		user2.setPassword(this.passwordEncoder.encode("normal"));

		
		Role userRole = new Role();
		userRole.setName("USER");
		
		Role adminRole = new Role();
		adminRole.setName("ADMIN");
		
		List<Role> rolesList1 = new ArrayList<>();
		//rolesList1.add(userRole);
		rolesList1.add(adminRole);
		
		List<Role> rolesList2 = new ArrayList<>();
		rolesList2.add(userRole);
		
		user1.addRole(adminRole);
		user2.addRole(userRole);
		
		
		roleRepository.save(userRole);
		roleRepository.save(adminRole);
		userRepository.save(user1);
		userRepository.save(user2);
	}

}
