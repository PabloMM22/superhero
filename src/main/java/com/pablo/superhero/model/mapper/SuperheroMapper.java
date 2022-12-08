package com.pablo.superhero.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pablo.superhero.model.dto.SuperheroDTO;
import com.pablo.superhero.model.entity.Superhero;

/**
 * 
 * @author Pablo
 *
 */
@Mapper(componentModel = "spring")
public interface SuperheroMapper {

    List<SuperheroDTO> toSuperheroes(List<Superhero> superheroList);

	SuperheroDTO superheroToSuperheroDTO(Superhero superhero);
}
