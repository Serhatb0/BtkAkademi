package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromosyonCodeListDto {
	private String promosyonCode;

	private int discountRate;
	
	private LocalDate promosyonStart;
	
	private LocalDate promosyonEnd;
}
