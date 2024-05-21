package com.example.sample;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDoForm {
	//	private String username;
	//	private String password;
	private long todo_id;
	private long user_id;
	private String title;
	private String description;
	private LocalDateTime due_date;
}
