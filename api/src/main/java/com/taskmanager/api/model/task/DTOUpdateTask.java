package com.taskmanager.api.model.task;

import java.util.Date;

import com.taskmanager.api.model.department.DTODepartment;
import com.taskmanager.api.model.person.DTOPersonId;

public record DTOUpdateTask(
		Long id,
		String title,
		String description,
		Date deadline,
		DTODepartment department,
		int duration,
		DTOPersonId person,
		boolean isDone
		
		) {

}
