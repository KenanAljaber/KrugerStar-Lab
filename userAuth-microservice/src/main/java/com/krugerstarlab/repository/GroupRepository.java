package com.krugerstarlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krugerstarlab.entity.Group;


public interface GroupRepository extends JpaRepository<Group,Long> {
	
	Optional<Group> findByName (String name);

}
