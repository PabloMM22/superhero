package com.pablo.superhero;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablo.superhero.model.dto.SuperheroDTO;
import com.pablo.superhero.service.SuperheroService;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Sql(value = "classpath:import.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SuperheroIntegrationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private SuperheroService service;

	@Autowired
	private ObjectMapper mapper;

	@WithUserDetails()
	@Test
	void getAll_should_return_full_list() throws Exception {
		mockMvc.perform(get("/superhero/getAll"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isNotEmpty())
			.andExpect(jsonPath("$").isArray());
	}

	@WithUserDetails()
	@Test
	void getById_should_return_superhero() throws Exception {
		mockMvc.perform(get("/superhero/{id}", 5L)).andExpect(status().isOk());
	}

	@WithUserDetails(value = "admin")
	@Test
	void delete_should_return_not_found() throws Exception {

		mockMvc.perform(delete("/superhero/{id}", 100L))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.status", is(404)));
	}
	
	@WithUserDetails()
	@Test
	void getById_should_return_not_found() throws Exception {
		mockMvc.perform(get("/superhero/{id}", 100L)).andExpect(status().isNotFound());
	}
	
	@WithUserDetails()
	@Test
	void search_should_return_not_superhero() throws Exception {
		mockMvc.perform(get("/superhero/searchByName/{query}", "Testfail"))
		.andExpect(status().isNotFound());
	}

	@WithUserDetails()
	@Test
	void buscar_should_return_superhero() throws Exception {
		mockMvc.perform(get("/superhero/searchByName/{query}", "Spiderman"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isNotEmpty());
	}
	
	@WithUserDetails(value = "admin")
	@Test
	void update_should_return_OK() throws Exception {
		SuperheroDTO superhero = new SuperheroDTO(3L, "new ironman", "DC");

		mockMvc.perform(put("/superhero").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(superhero)))
				.andExpect(status().isOk());

		assertThat(service.findById(3L).getName()).isEqualTo("new ironman");
	}
}