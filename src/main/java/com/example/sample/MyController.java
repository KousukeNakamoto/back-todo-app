package com.example.sample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@Autowired
	UserService userService;

	@Autowired
	ToDoService todoService;

	@GetMapping("/auth")
	public User authCheck() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// 認証されたユーザー名を取得 ダブルクォーテーションがついているので注意
		String username = authentication.getName();

		return userService.getUserInfo(username.replaceAll("\"", ""));

	}

	@GetMapping("/todos/{id}")
	public List<ToDo> getToDo(@PathVariable("id") long id) {

		return todoService.getToDos(id);
	}

	@PostMapping("/create_todo")
	public void createToDo(@RequestBody ToDoForm post) {
		ToDo todo = new ToDo(post.getTitle(), post.getDescription(), post.getUser_id(), post.getDue_date());
		System.err.println(post.getDue_date());
		todoService.createToDo(todo);
	}

	@PatchMapping("/update_todo")
	public void updateToDo(@RequestBody ToDoForm post) {
		ToDo todo = new ToDo(post.getTitle(), post.getDescription(), post.getUser_id(), post.getDue_date());
		todo.setId(post.getTodo_id());
		todo.setDue_date(post.getDue_date());
		todo.setCompleted(post.isCompleted());
		System.out.println(post.isCompleted());
		System.out.println(todo.getId());
		todoService.update(todo);
	}

	@PostMapping("/delete_todo")
	public void deleteToDo(@RequestBody ToDoForm post) {
		todoService.delete(post.getTodo_id());
	}

}
