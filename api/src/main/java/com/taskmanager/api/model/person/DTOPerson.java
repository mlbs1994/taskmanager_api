	package com.taskmanager.api.model.person;
	
	import java.util.List;
	
	import com.taskmanager.api.model.department.DTODepartment;
	import com.taskmanager.api.model.task.Task;
	
	
	public record DTOPerson(
			String name,
			
			DTODepartment department,
			
			List<Task> tasks) {
	
	}
