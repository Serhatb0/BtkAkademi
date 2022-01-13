package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;

import com.btkAkademi.rentACar.entities.concretes.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {
	


	private LocalDate dateOfCare;

	private LocalDate dateOfArrival;

}
