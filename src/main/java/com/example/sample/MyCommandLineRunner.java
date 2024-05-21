package com.example.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		//		User user = new User("test", "password");
		//		userRepository.save(user);
		//		System.out.println(userRepository.findAll());
		System.out.println("test");
		System.out.println("aaaaaaaaaaaaaaaaaaa");
	}

}