package com.pablo.superhero.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Pablo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroDTO {

    private Long id;
    private String name;
    private String company;
}
