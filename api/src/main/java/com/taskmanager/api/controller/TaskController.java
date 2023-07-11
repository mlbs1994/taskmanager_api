package com.taskmanager.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.taskmanager.api.model.department.DTODepartment;
import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.department.DepartmentRepository;
import com.taskmanager.api.model.person.DTOPersonId;
import com.taskmanager.api.model.person.DTOPersonList;
import com.taskmanager.api.model.person.Person;
import com.taskmanager.api.model.person.PersonRepository;
import com.taskmanager.api.model.task.DTOTask;
import com.taskmanager.api.model.task.DTOTaskDetails;
import com.taskmanager.api.model.task.DTOUpdateTask;
import com.taskmanager.api.model.task.DTOUpdateTaskAllocation;
import com.taskmanager.api.model.task.Task;
import com.taskmanager.api.model.task.TaskRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@GetMapping
	public ResponseEntity<Page<DTOTaskDetails>> list(@PageableDefault(size = 10, sort = {"title"}) Pageable pagination ){
		Page<DTOTaskDetails> page = taskRepository.findAll(pagination).map(DTOTaskDetails:: new);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DTOTaskDetails> detail(@PathVariable Long id){
		Task task = taskRepository.getReferenceById(id);
		
		DTOTaskDetails taskDetailsData = new DTOTaskDetails(task);
		
		return ResponseEntity.ok(taskDetailsData);
		
	}
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<DTOTaskDetails> create(@RequestBody DTOTask taskData, UriComponentsBuilder uribuilder) {
		
		Department department = departmentRepository.getReferenceById(taskData.department().id());
		DTOPersonId personData = taskData.person();
		
		Person person = personData != null? 
				personRepository.getReferenceById(personData.id()) :
					null;
		
		Task task = new Task(taskData, department, person);
		task = taskRepository.save(task);
		
		DTOTaskDetails taskDetailsData = new DTOTaskDetails(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				new DTODepartment(department.getId(), department.getName()),
				task.getDuration(),
				person != null? new DTOPersonList(person) : null,
				task.isDone());
		
		URI uri = uribuilder.path("task/{id}").buildAndExpand(task.getId()).toUri();
		
		return ResponseEntity.created(uri).body(taskDetailsData); 
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DTOTaskDetails> update(@RequestBody DTOUpdateTask taskData){
		Task task = taskRepository.getReferenceById(taskData.id());
		Department department = task.getDepartment();
		Person person = task.getPerson();
		task.update(taskData, department, person);
		
		DTOTaskDetails taskDetailsData = new DTOTaskDetails(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				new DTODepartment(department.getId(), department.getName()),
				task.getDuration(),
				person != null ? new DTOPersonList(person) : null,
				task.isDone());
	
		return ResponseEntity.ok(taskDetailsData);
		
	}
	
	@PutMapping("/allocate/{id}")
	@Transactional
	public ResponseEntity<DTOTaskDetails> allocate(@RequestBody DTOUpdateTaskAllocation data, @PathVariable Long id){
		Task task = taskRepository.getReferenceById(data.id());
		Person person = personRepository.getReferenceById(id);
		if(task.getDepartment().getId() == person.getDepartment().getId()) {
			task.allocate(person);
		} else {
			// throw Validation exception
		}
		
		DTOTaskDetails taskDetailsData = new DTOTaskDetails(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				new DTODepartment(task.getDepartment().getId(), task.getDepartment().getName()),
				task.getDuration(),
				new DTOPersonList(person),
				task.isDone());
	
		return ResponseEntity.ok(taskDetailsData);
	}
	
	@PutMapping("/finish/{id}")
	@Transactional
	public ResponseEntity<DTOTaskDetails> finish(@PathVariable Long id){
		Task task = taskRepository.getReferenceById(id);
		task.done();
		
		DTOTaskDetails taskDetailsData = new DTOTaskDetails(
				task.getTitle(),
				task.getDescription(),
				task.getDeadline(),
				new DTODepartment(task.getDepartment().getId(), task.getDepartment().getName()),
				task.getDuration(),
				new DTOPersonList(task.getPerson()),
				task.isDone());
	
		return ResponseEntity.ok(taskDetailsData);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id){
		taskRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}

}
