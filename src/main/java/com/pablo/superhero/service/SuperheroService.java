package com.pablo.superhero.service;

import java.util.List;

import com.pablo.superhero.model.dto.SuperheroDTO;

/**
 * 
 * @author Pablo
 *
 */
public interface SuperheroService {

	List<SuperheroDTO> findAll();

	SuperheroDTO findById(Long id);

	void update(SuperheroDTO superhero);
	
    void delete(Long id);
    
    List<SuperheroDTO> search(String criterio);

	void flushCache();

}
