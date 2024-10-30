package com.streaming.filme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.streaming.filme.model.Gender;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {

	public Optional<Gender> findByName(String name);

}
