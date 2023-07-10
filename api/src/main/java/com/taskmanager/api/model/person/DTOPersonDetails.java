package com.taskmanager.api.model.person;

import java.util.List;

import com.taskmanager.api.model.task.DTOTask;

public record DTOPersonDetails(
		
		String name,
		String department,
		List<DTOTask> tasks
		
		) {
	
}
