package com.taskmanager.api.model.task;

import java.util.Date;

import com.taskmanager.api.model.department.DTODepartment;
import com.taskmanager.api.model.person.DTOPersonList;

public record DTOTaskDetails(
		String title,
		String description,
		Date deadline,
		DTODepartment department,
		int duration,
		DTOPersonList person, 
		boolean is_done
		) {
	
	public DTOTaskDetails(Task task) {
		this(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				new DTODepartment(task.getDepartment().getId(), task.getDepartment().getName()),
				task.getDuration(),
				new DTOPersonList(
						task.getPerson().getName(),
						task.getDepartment().getName(),
						task.getPerson().getTasksSpentTime()),
				task.isDone());
		
	}
}
