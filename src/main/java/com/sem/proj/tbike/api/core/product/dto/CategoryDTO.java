package com.sem.proj.tbike.api.core.product.dto;

import com.sem.proj.tbike.api.util.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ResponseType
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

	private long id;

	private String name;

	private String description;

}
