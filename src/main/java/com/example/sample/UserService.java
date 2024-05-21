package com.example.sample;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void registerUser(UserForm data) {
		System.out.println(data.getUsername());
		System.out.println(data.getPassword());
		User user = new User(data.getUsername(), passwordEncoder.encode(data.getPassword()));
		userRepository.save(user);
	}

	public User getUserInfo(String name) {

		return userRepository.findByUsername(name);
	}
}
