package com.taskmanager.api.model.person;

import java.util.ArrayList;
import java.util.List;

import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.task.Task;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name = "department")
	@Embedded
	private Department department;
	
	@OneToMany(mappedBy = "person")
	private List<Task> tasksList = new ArrayList<>();
	
	public Person(DTOPerson personData) {
		this.name = personData.name();
		this.department = new Department(personData.department());
	}
	
	public Person(DTOPerson personData, Department department) {
		this.name = personData.name();
		this.department = department;
	}

	public void update(DTOUpdatePerson personData, Department department) {
		this.name = personData.name();
		this.department = department;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
