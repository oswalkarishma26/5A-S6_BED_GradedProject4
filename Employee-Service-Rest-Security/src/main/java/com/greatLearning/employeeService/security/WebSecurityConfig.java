package com.greatLearning.employeeService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.greatLearning.employeeService.service.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService getMyUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(getMyUserDetailsService())
		    .passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/user", "/role").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/employees/*").hasAnyRole("USER","ADMIN")
				.antMatchers(HttpMethod.GET, "/employees/{employeeId}*").hasAnyRole("USER","ADMIN")
				.antMatchers(HttpMethod.POST, "/employees").hasRole("ADMIN")				
				.antMatchers(HttpMethod.PUT, "/employees").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/employees/*").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/employees/search/*").hasAnyRole("USER","ADMIN")
				.antMatchers(HttpMethod.GET, "/employees/sort/*").hasAnyRole("USER","ADMIN")
				.anyRequest().authenticated().and().httpBasic()	         
				.and().cors().and().csrf().disable();
	}

}
