package com.pablo.superhero.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pablo.superhero.model.entity.Superhero;

/**
 * 
 * @author Pablo
 *
 */
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

	List<Superhero> findAllByNameContainingIgnoreCase(String query);

}
