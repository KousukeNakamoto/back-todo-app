package com.example.sample.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sample.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserDetails implements UserDetails {

	private final User user;

	// ユーザーが持つ権限を返す
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	// ユーザーアカウントが有効期限切れでないかを判定する
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// ユーザーアカウントがロックされていないかを判定する
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 認証情報が有効期限切れでないかを判定する
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
