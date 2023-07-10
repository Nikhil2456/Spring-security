package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.config.UserInfoUserDetails;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = userRepository.findByName(username);
		return userInfo.map(UserInfoUserDetails::new)
			.orElseThrow(()-> new UsernameNotFoundException("User not found "+username));
	}

}
