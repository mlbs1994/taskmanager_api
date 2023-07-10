package com.taskmanager.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.taskmanager.api.model.department.DTODepartment;
import com.taskmanager.api.model.department.Department;
import com.taskmanager.api.model.department.DepartmentRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("department")
public class DepartmentController {
	
	@Autowired
	DepartmentRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DTODepartment> create(@RequestBody DTODepartment data, UriComponentsBuilder uribuilder){
		Department department = new Department(data);
		repository.save(department);
		
		URI uri = uribuilder.path("department/{id}").buildAndExpand(department.getId()).toUri();
		
		return ResponseEntity.created(uri).body(data);
		
	}
	
}
