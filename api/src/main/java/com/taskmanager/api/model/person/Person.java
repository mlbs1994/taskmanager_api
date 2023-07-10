package com.taskmanager.api.model.person;

import java.util.List;

import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.task.Task;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="person")
@Entity(name="Person")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Embedded
	private Department department;
	
	@OneToMany(mappedBy = "person")
	private List<Task> tasksList;
	
	public Person(DTOPerson personData) {
		this.name = personData.name();
		this.department.updateDepartment(personData.department());
	}

}
