package com.example.sample;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToDoService {
	private final ToDoRepository todoRepository;

	public void createToDo(ToDo todo) {
		todoRepository.save(todo);
	}

	public List<ToDo> getToDos(long user_id) {
		return todoRepository.getToDoList(user_id);
	}

	public void delete(long todo_id) {
		todoRepository.deleteById(todo_id);
	}

	public void update(ToDo todo) {
		System.out.println(todo.getId());
		todoRepository.save(todo);
	}
}
