package com.taskmanager.api.model.department;

public record DTODepartment(
		
		Long id,
		String name
		
		) {

	public DTODepartment(Department department) {
		this(department.getId(), department.getName());
	}
}
