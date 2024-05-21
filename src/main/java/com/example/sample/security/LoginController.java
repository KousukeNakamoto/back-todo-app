package com.example.sample.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.sample.User;
import com.example.sample.UserForm;
import com.example.sample.UserService;

@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	private DaoAuthenticationProvider daoAuthenticationProvider;

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserForm request) {
		try {
			// DaoAuthenticationProviderを用いた認証を行う　データベースUserDetailServiceを経由して内部的に接続している
			daoAuthenticationProvider.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			// JWTトークンの生成
			//この部分を調べる
			Date expiresAt = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
			String token = JWT.create().withClaim("username", request.getUsername())
					.withExpiresAt(expiresAt)
					.sign(Algorithm.HMAC256("__secret__"));
			// トークンをクライアントに返す
			HttpHeaders httpHeaders = new HttpHeaders();
			User user = userService.getUserInfo(request.getUsername());
			httpHeaders.add("x-auth-token", token);
			httpHeaders.add("user-id", String.valueOf(user.getId()));
			return ResponseEntity.ok().headers(httpHeaders).body(token);
		} catch (AuthenticationException e) {
			System.out.println("/errora" + e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserForm userForm) {
		try {
			// ユーザーを登録
			userService.registerUser(userForm);
			return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user.");
		}
	}

	//	@PostMapping("/logout")
	//	public void logout(HttpServletRequest request, HttpServletResponse response) {
	//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	//		if (authentication != null) {
	//			new SecurityContextLogoutHandler().logout(request, response, authentication);
	//		}
	//
	//	}

	//トークンを再発行して有効期限を0秒に設定する
	//error
	//エンドポイントがlogoutだとなぜかloginがエンドポイントになる
	@PostMapping("/logouttest")
	public ResponseEntity<String> logout(@RequestHeader("X-AUTH-TOKEN") String tokenHeader) {

		try {
			// トークンからユーザー名を取得
			String token = tokenHeader.substring(7); // "Bearer " の部分を取り除く
			DecodedJWT decodedJWT = JWT.decode(token);
			String username = decodedJWT.getClaim("username").asString();

			// 新しいトークンを生成し、有効期限を0秒に設定する
			String newToken = JWT.create().withClaim("username", username)
					.withExpiresAt(new Date()) // 現在時刻を指定して即座に無効化
					.sign(Algorithm.HMAC256("__secret__"));

			// トークンをクライアントに返す
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("x-auth-token", newToken);
			return ResponseEntity.ok().headers(httpHeaders).body("Logged out successfully");
		} catch (Exception e) {
			// エラーが発生した場合はUNAUTHORIZEDを返す
			System.out.println("error");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
