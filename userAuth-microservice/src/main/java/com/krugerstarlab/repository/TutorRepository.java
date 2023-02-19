package com.krugerstarlab.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krugerstarlab.entity.Tutor;


public interface TutorRepository extends JpaRepository<Tutor, Long> {
	Optional<Tutor> findByEmail(String email);

}
