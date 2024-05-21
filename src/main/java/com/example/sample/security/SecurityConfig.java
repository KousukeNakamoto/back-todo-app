package com.example.sample.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CORSの設定を有効化...(1)
		http.cors(cors -> cors.configurationSource(this.corsConfigurationSource()));
		//		http.cors().disable();
		// csrf設定を無効化。...(2)
		http.csrf(csrf -> csrf.disable());
		// (3)
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/login", "/register").permitAll()
				.anyRequest().authenticated());
		http.addFilterBefore(new AuthorizeFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	//	 (4)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// (5)
	@Bean
	public DaoAuthenticationProvider authenticationProvider(LoginUserDetailsService loginUserDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(loginUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	// CORSの設定
	private CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
		corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Reactアプリのオリジンを許可
		// クロスドメインのリクエストに対してX-AUTH-TOKENヘッダーでトークンを返すように設定しています。
		corsConfiguration.addExposedHeader("X-AUTH-TOKEN");
		corsConfiguration.addExposedHeader("user-id");
		//		corsConfiguration.addAllowedOrigin("http://front-origin.example.com");
		corsConfiguration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
		corsSource.registerCorsConfiguration("/**", corsConfiguration);
		return corsSource;
	}
}
