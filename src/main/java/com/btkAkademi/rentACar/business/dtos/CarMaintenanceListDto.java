package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {
	

	private String givenInstitution;

	private LocalDate dateOfCare;

	private LocalDate dateOfArrival;

	private Boolean status;
}