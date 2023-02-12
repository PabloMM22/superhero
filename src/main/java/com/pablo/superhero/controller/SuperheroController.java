package com.pablo.superhero.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pablo.superhero.annotation.TrackExecutionTime;
import com.pablo.superhero.model.dto.SuperheroDTO;
import com.pablo.superhero.service.SuperheroService;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Pablo
 *
 */
@RestController
@RequestMapping("/superhero")
@AllArgsConstructor
public class SuperheroController {

	private final SuperheroService superheroService;

	@TrackExecutionTime
	@GetMapping("/")
	public ResponseEntity<List<SuperheroDTO>> getAll() {
		return ResponseEntity.ok(superheroService.findAll());
	}

	@TrackExecutionTime
	@GetMapping("/{id}")
	public ResponseEntity<SuperheroDTO> getById(@PathVariable Long id) {
		SuperheroDTO superHeroe = superheroService.findById(id);
		return ResponseEntity.ok(superHeroe);
	}

	@TrackExecutionTime
	@PutMapping
	public ResponseEntity<String> update(@RequestBody SuperheroDTO superhero) {
		superheroService.update(superhero);
		return ResponseEntity.ok("Superhero with id: " + superhero.getId() + " updated sucessfully!");
	}

	@TrackExecutionTime
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		superheroService.delete(id);
		return ResponseEntity.ok("Superhero with id: " + id + " deleted sucessfully!");
	}

	@TrackExecutionTime
	@GetMapping("/searchByName/{query}")
	public ResponseEntity<?> search(@PathVariable String query) {
		List<SuperheroDTO> superHeroes = superheroService.search(query);
		return ResponseEntity.ok(superHeroes);
	}

	@GetMapping("/flushcache")
	public  ResponseEntity<String> flushCache() {
		superheroService.flushCache();
		return ResponseEntity.ok("Cache Flushed!");
	}
}
