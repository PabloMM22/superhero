package com.pablo.superhero.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pablo.superhero.model.dto.SuperheroDTO;
import com.pablo.superhero.model.entity.Superhero;
import com.pablo.superhero.model.mapper.SuperheroMapper;
import com.pablo.superhero.repository.SuperheroRepository;
import com.pablo.superhero.service.SuperheroService;

import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Pablo
 *
 */
@Service
@RequiredArgsConstructor
public class SuperheroServiceImpl implements SuperheroService {

	private final SuperheroRepository superheroRepository;
	private final SuperheroMapper superheroMapper;

	private static final String NOT_FOUND_MESSAGE = "The requested superhero does not exist!";

	@Cacheable(cacheNames = "headers")
	@Override
	public List<SuperheroDTO> findAll() {
		return superheroMapper.toSuperheroes(superheroRepository.findAll());
	}

	@Override
	public SuperheroDTO findById(Long id) {
		Optional<Superhero> result = superheroRepository.findById(id);
		if (result.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
		}
		return superheroMapper.superheroToSuperheroDTO(result.get());
	}

	@CachePut(cacheNames="headers", key="#superhero.id")
	@Override
	public void update(SuperheroDTO superhero) {
		Superhero dbEntity = superheroRepository.findById(superhero.getId()).orElse(null);
		if (dbEntity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
		}
		dbEntity.setName(superhero.getName());
		dbEntity.setCompany(superhero.getCompany());
		superheroRepository.save(dbEntity);
	}

	@Override
	public void delete(Long id) {
		if (!superheroRepository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
		}
		superheroRepository.deleteById(id);
	}

	@Override
	public List<SuperheroDTO> search(String query) {
		List<Superhero> superheroes = superheroRepository.findAllByNameContainingIgnoreCase(query.toUpperCase());
		if (superheroes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_MESSAGE);
		}
		return superheroMapper.toSuperheroes(superheroes);
	}

	@Override
	@CacheEvict(cacheNames = "headers", allEntries = true)
	public void flushCache() {
	}

}
