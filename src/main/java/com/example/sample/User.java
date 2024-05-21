package com.example.sample;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	public User(String name, String pass) {
		this.username = name;
		this.password = pass;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;
	private String password;

	@OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL)
	private List<ToDo> todos;

	// コンストラクタ、ゲッター、セッターなどを追加

	// コンストラクタ、ゲッター、セッターなどを追加
}
