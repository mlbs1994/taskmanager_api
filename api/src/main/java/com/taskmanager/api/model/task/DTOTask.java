package com.taskmanager.api.model.task;

import java.util.Date;

public record DTOTask(
		
		String title,
		
		String description,
		
		Date deadline,
		
		int duration,
		
		String person,
		
		boolean isDone
		
		) {
	
	public DTOTask(Task task) {
		this(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				task.getDuration(),
				task.getPerson().getName(),
				task.isDone());
	}

}
