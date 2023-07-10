package com.taskmanager.api.model.person;

import com.taskmanager.api.model.department.DTODepartment;

public record DTOPersonList(
		
		String name,
		
		DTODepartment department
		
		){
	
	public DTOPersonList(Person person) {
		this(person.getName(), new DTODepartment(person.getDepartment().getName()));
	}

}
