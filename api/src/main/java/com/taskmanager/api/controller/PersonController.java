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

import com.taskmanager.api.model.person.DTOPersonList;
import com.taskmanager.api.model.person.DTOPerson;
import com.taskmanager.api.model.person.Person;
import com.taskmanager.api.model.person.PersonRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("person")
public class PersonController {
	
	@Autowired
	PersonRepository repository;

	@GetMapping
	public ResponseEntity<Page<DTOPersonList>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination ){
		Page<DTOPersonList> page = repository.findAll(pagination).map(DTOPersonList:: new);
		
		return ResponseEntity.ok(page);
	}
	
	
	
	@PostMapping
	@Transactional
	public ResponseEntity<DTOPerson> cadastrar(@RequestBody DTOPerson personData, UriComponentsBuilder uribuilder) {
		Person person = new Person(personData);
		repository.save(person);
		
		URI uri = uribuilder.path("person/{id}").buildAndExpand(person.getId()).toUri();
		
		return ResponseEntity.created(uri).body(personData);
	}
	
}
