package com.btkAkademi.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {

	private int rentedKilometer;
	private int pickUpCityId;
	private int returnCityId;
	private LocalDate rentDate;
	private int returnedKilometer;
	private LocalDate returnDate;
	private int customerId;
	private int carId;

}
