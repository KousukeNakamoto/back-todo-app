package com.example.sample;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

	@Query(value = "SELECT * FROM todo " + "WHERE user_id = :user_id ", nativeQuery = true)
	List<ToDo> getToDoList(@Param("user_id") Long user_id);

}
