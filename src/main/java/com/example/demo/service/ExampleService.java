package com.example.demo.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Example;
import com.example.demo.entity.UserInfo;
import com.example.demo.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ExampleService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	List<Example> exampleList;
	
	@PostConstruct
	public void loadExampleRandomly() {
		exampleList=IntStream.rangeClosed(1, 100)
				.mapToObj(i -> Example.builder()
						.exampleId(i)
						.exampleName("Example "+i)
						.qty(new Random().nextInt(10))
						.price(new Random().nextFloat(5000)).build()
						).collect(Collectors.toList());
	}
	
	public List<Example> getExample(){
		return exampleList;
	}
	
	public Example getExampleById(int id) {
		return exampleList.stream()
				.filter(example -> example.getExampleId()==id)
				.findAny()
				.orElseThrow(()-> new RuntimeException("Product "+id+" not found !"));
	}
	
	public String addUser(UserInfo user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return "User added successfully";
	}
}
