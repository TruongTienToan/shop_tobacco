package com.cg.model.dto;

import com.cg.validates.ValidationStepOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductRequestDTO {
    private Long id;


    private String name;


    private String image;

    private Long price;

    private CategoryDTO category;
}
