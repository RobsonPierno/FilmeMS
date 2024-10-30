package com.streaming.filme.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.streaming.filme.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

	List<Filme> findByActiveTrueAndDtReleaseBefore(Date dateEndSales, Sort sort);
	
//	@Query(value = "SELECT f FROM Filme f, Pessoa p WHERE f.idPessoa = p.id AND p.sex = :sex")
	@Query(value = "SELECT f.* FROM streaming.filme f INNER JOIN streaming.pessoa p ON (f.cadastradopor = p.id) WHERE p.sexo = :sex", 
			nativeQuery = true)
	List<Filme> findBySexOfWhoRegistered(@Param("sex") String sex);

}
