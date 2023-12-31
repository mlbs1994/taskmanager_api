package com.taskmanager.api.model.task;

import java.util.Date;

import com.taskmanager.api.model.department.DTODepartment;
import com.taskmanager.api.model.person.DTOPersonId;

public record DTOTask(
		
		String title,
		
		String description,
		
		Date deadline,
		
		DTODepartment department,
		
		int duration,
		
		DTOPersonId person,
		
		boolean isDone
		
		) {
	
	public DTOTask(Task task) {
		this(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				new DTODepartment(task.getDepartment().getId(), task.getDepartment().getName()),
				task.getDuration(),
				new DTOPersonId(task.getPerson().getId()),
				task.isDone());
	}

}
