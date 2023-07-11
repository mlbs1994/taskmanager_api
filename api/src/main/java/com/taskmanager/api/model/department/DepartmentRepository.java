package com.taskmanager.api.model.department;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

	Department findByName(String name);
	
}
