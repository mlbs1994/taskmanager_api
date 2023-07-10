package com.taskmanager.api.controller;

import java.net.URI;
import java.util.stream.Collectors;

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

import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.department.DepartmentRepository;
import com.taskmanager.api.model.person.DTOPerson;
import com.taskmanager.api.model.person.DTOPersonDetails;
import com.taskmanager.api.model.person.DTOPersonList;
import com.taskmanager.api.model.person.DTOUpdatePerson;
import com.taskmanager.api.model.person.Person;
import com.taskmanager.api.model.person.PersonRepository;
import com.taskmanager.api.model.task.DTOTask;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("person")
public class PersonController {
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@GetMapping
	public ResponseEntity<Page<DTOPersonList>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination ){
		Page<DTOPersonList> page = personRepository.findAll(pagination).map(DTOPersonList:: new);
		
		return ResponseEntity.ok(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DTOPersonDetails> detail(@PathVariable Long id){
		Person person = personRepository.getReferenceById(id);
		
		DTOPersonDetails personDetailsData = new DTOPersonDetails(
				person.getName(),
				person.getDepartment().getName(),
				person.getTasksList().stream().map(DTOTask::new).collect(Collectors.toList()));
		
		return ResponseEntity.ok(personDetailsData);
		
	}
	
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<DTOPersonDetails> cadastrar(@RequestBody DTOPerson personData, UriComponentsBuilder uribuilder) {
		
		//Id must be required
		Department department = departmentRepository.getReferenceById(personData.department().id());
		
		Person person = new Person(personData, department);
		person = personRepository.save(person);
		
		DTOPersonDetails personDetailsData = new DTOPersonDetails(
				person.getName(),
				person.getDepartment().getName(),
				person.getTasksList().stream().map(DTOTask::new).collect(Collectors.toList()));
		
		URI uri = uribuilder.path("person/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(personDetailsData); 
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DTOPersonDetails> update(@RequestBody DTOUpdatePerson personData){
		Person person = personRepository.getReferenceById(personData.id());
		Department department = person.getDepartment();
		person.update(personData, department);
		
		DTOPersonDetails personDetailsData = new DTOPersonDetails(
				person.getName(),
				person.getDepartment().getName(),
				person.getTasksList().stream().map(DTOTask::new).collect(Collectors.toList()));
	
		return ResponseEntity.ok(personDetailsData);
		
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> delete(@PathVariable Long id){
		personRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
