package com.taskmanager.api.model.task;

import java.util.Date;

import com.taskmanager.api.model.person.Person;

public record DTOTask(
		
		String title,
		
		String description,
		
		Date deadline,
		
		int duration,
		
		Person person,
		
		boolean isDone
		
		) {

}
