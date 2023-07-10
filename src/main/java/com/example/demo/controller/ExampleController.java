package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.Example;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.ExampleService;
import com.example.demo.service.JwtService;

@RestController
@RequestMapping("/example")
public class ExampleController {

	@Autowired
	private ExampleService service;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@GetMapping("/welcome")
	public String welcome(){
		return "Welcome To Example Project";
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Example> getAllExample(){
		return service.getExample();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public Example getExampleById(@PathVariable("id") int id){
		return service.getExampleById(id);
	}
	
	@PostMapping("/new-user")
	public String addUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}
	
	@PostMapping("/authenticate")
	public String jwtTokenGenerator(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(authRequest.getUsername());
		}
		else {
			throw new UsernameNotFoundException("invalid user request");
		}
	}

}
