package com.example.sample;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "todo")
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {

	public ToDo(String title, String description, long user_id, LocalDateTime due_date) {
		this.title = title;
		this.description = description;
		this.user_id = user_id;
		this.due_date = due_date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String description;
	private boolean completed = false;
	private LocalDateTime due_date;

	private Long user_id;

	// コンストラクタ、ゲッター、セッターなどを追加

	// コンストラクタ、ゲッター、セッターなどを追加
}
