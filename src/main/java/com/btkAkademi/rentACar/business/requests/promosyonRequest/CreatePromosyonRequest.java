package com.btkAkademi.rentACar.business.requests.promosyonRequest;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePromosyonRequest {
	private String promosyonCode;

	private int discountRate;
	
	private LocalDate promosyonStart;
	
	private LocalDate promosyonEnd;
}
