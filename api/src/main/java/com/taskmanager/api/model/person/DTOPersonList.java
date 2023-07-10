package com.taskmanager.api.model.person;

public record DTOPersonList(
		
		String name,
		
		String department
		
		){
	
	public DTOPersonList(Person person) {
		this(person.getName(), person.getDepartment().getName());
	}

}
