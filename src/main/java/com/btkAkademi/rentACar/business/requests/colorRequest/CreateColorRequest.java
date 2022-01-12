package com.btkAkademi.rentACar.business.requests.colorRequest;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {
	

	@NotBlank
	private String name;
	
}
