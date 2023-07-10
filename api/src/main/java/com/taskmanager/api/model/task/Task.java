package com.taskmanager.api.model.task;

import java.util.Date;

import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.person.Person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="task")
@Entity(name = "Task")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;
	
	private String title;
	
	private String description;
	
	private Date deadline;
	
	@ManyToOne
	@JoinColumn(name = "deparment")
	private Department department;
	
	private int duration; 
	
	@Column(name="is_done")
	private boolean isDone;
	
	public Task(DTOTask task, Department department, Person person) {
		this.title = task.title();
		this.description = task.description();
		this.deadline = task.deadline();
		this.department = department;
		this.duration = task.duration();
		this.person = person;
		this.isDone = task.isDone();
		
	}

	public void update(DTOUpdateTask taskData, Department department, Person person) {
		this.title = taskData.title();
		this.description = taskData.description();
		this.deadline = taskData.deadline();
		this.department = department;
		this.duration = taskData.duration();
		this.person = person;
		this.isDone = taskData.isDone();
	}
}
