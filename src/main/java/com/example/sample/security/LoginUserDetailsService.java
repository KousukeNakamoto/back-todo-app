package com.example.sample.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sample.User;
import com.example.sample.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {

	private final UserRepository repository;

	//データベースに接続して検証
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> maybeUser = Optional.of(repository.findByUsername(username));
		System.out.println("UserServiceDetail" + maybeUser);
		return maybeUser.map(LoginUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("username not found."));
	}
}
