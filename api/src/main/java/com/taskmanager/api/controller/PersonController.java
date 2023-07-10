package com.taskmanager.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.taskmanager.api.model.department.DTODepartment;
import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.department.DepartmentRepository;
import com.taskmanager.api.model.person.DTOPerson;
import com.taskmanager.api.model.person.DTOPersonList;
import com.taskmanager.api.model.person.Person;
import com.taskmanager.api.model.person.PersonRepository;

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
	
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<DTOPerson> cadastrar(@RequestBody DTOPerson personData, UriComponentsBuilder uribuilder) {
		
		Department department = personData.department().id() != 0? 
				departmentRepository.getReferenceById(personData.department().id()):
				departmentRepository.save(new Department(personData.department().name()));
		
		Person person = new Person(personData, department);
		person = personRepository.save(person);
		
		if(personData.department().name() == null || personData.department().name().isBlank()) {
			personData = new DTOPerson(person.getName(),
						 new DTODepartment(person.getDepartment().getId(),
								 		   person.getDepartment().getName()),
						 				   person.getTasksList());
		}
		
		URI uri = uribuilder.path("person/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(personData);
	}
	
}
