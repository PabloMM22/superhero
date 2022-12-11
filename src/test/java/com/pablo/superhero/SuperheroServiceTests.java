package com.pablo.superhero;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.pablo.superhero.model.dto.SuperheroDTO;
import com.pablo.superhero.model.entity.Superhero;
import com.pablo.superhero.repository.SuperheroRepository;
import com.pablo.superhero.service.impl.SuperheroServiceImpl;

@SpringBootTest
public class SuperheroServiceTests {

	@InjectMocks
	private SuperheroServiceImpl test;

	@Mock
	private SuperheroRepository superheroRepository;

	@Test
	public void testFindAll() {

		Mockito.when(superheroRepository.findAll()).thenReturn(getSuperHeroList());

		List<SuperheroDTO> list = test.findAll();
		assertNotNull(list);
		assertThat(list).doesNotContain(new SuperheroDTO(3L, "0", ""));
		assertThat(list.size() == 2);

	}

	@Test
	public void testFindAll_empty() {

		Mockito.when(superheroRepository.findAll()).thenReturn(new ArrayList<>());

		List<SuperheroDTO> list = test.findAll();
		assertNotNull(list);
		assertThat(list).isEmpty();

	}

	@Test
	public void testFindById() {

		Mockito.when(superheroRepository.findById(1L)).thenReturn(Optional.of(getSuperhero(1L, "Prueba", "Marvel")));

		SuperheroDTO superhero = test.findById(1L);
		assertNotNull(superhero);
		assertThat(superhero).isOfAnyClassIn(SuperheroDTO.class);

	}

	@Test
	public void testSearch() {

		Mockito.when(superheroRepository.findAllByNameContainingIgnoreCase("PRUE")).thenReturn(getSuperHeroList());

		List<SuperheroDTO> list = test.search("Prue");
		assertNotNull(list);
		assertThat(list.size() == 2);

	}

	@Test
	public void testSearch_KO() {
		Mockito.when(superheroRepository.findAllByNameContainingIgnoreCase("Man")).thenReturn(new ArrayList<>());
		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> test.search("Man"));

		assertEquals("404 NOT_FOUND \"The requested superhero does not exist!\"", exception.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

	}

	private List<Superhero> getSuperHeroList() {
		List<Superhero> superHeroList = Stream
				.of(getSuperhero(1L, "Prueba", "Marvel"), getSuperhero(2L, "SuperPrueba", "DC"))
				.collect(Collectors.toList());
		return superHeroList;
	}

	private Superhero getSuperhero(long id, String name, String company) {
		return new Superhero(id, name, company);
	}
}
